package com.hmdp.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.dto.Result;
import com.hmdp.dto.ScrollResult;
import com.hmdp.dto.UserDTO;
import com.hmdp.entity.Blog;
import com.hmdp.entity.Follow;
import com.hmdp.entity.User;
import com.hmdp.mapper.BlogMapper;
import com.hmdp.service.IBlogService;
import com.hmdp.service.IFollowService;
import com.hmdp.service.IUserService;
import com.hmdp.utils.RedisConstants;
import com.hmdp.utils.SystemConstants;
import com.hmdp.utils.UserHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements IBlogService {

    @Resource
    private IUserService userService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private IFollowService followService;

    @Override
    public Result queryHotBlog(Integer current) {
        // 根据用户查询
        Page<Blog> page = query().orderByDesc("liked").page(new Page<>(current, SystemConstants.MAX_PAGE_SIZE));
        // 获取当前页数据
        List<Blog> records = page.getRecords();
        records.forEach(blog -> {
            this.queryBlogUser(blog);
            this.isBlogLiked(blog);
        });
        return Result.ok(records);
    }

    @Override
    public Result queryBlogById(Long id) {
        // 查詢Blog
        Blog blog = getById(id);
        if (blog == null) {
            return Result.fail("筆記不存在");
        }
        queryBlogUser(blog);
        isBlogLiked(blog);
        return Result.ok(blog);
    }

    @Override
    public Result likeBlog(Long id) {
        Long userId = UserHolder.getUser().getId();
        String key = RedisConstants.BLOG_LIKED_KEY + id;
        Double score = stringRedisTemplate.opsForZSet().score(key, userId.toString());
        if (score == null) { // 當前用戶尚未點讚
            // 資料庫點讚數 + 1
            boolean isSuccess = update().setSql("liked = liked + 1").eq("id", id).update();
            if (isSuccess) {
                // 保存用戶到redis sorted_set集合
                stringRedisTemplate.opsForZSet().add(key, userId.toString(), System.currentTimeMillis());
            }
        } else {
            // 資料庫點讚數 - 1
            boolean isSuccess = update().setSql("liked = liked - 1").eq("id", id).update();
            if (isSuccess) {
                // redis sorted_set集合移除用戶
                stringRedisTemplate.opsForZSet().remove(key, userId.toString());
            }
        }
        return Result.ok();
    }

//    @Override
//    public Result likeBlog(Long id) {
//        Long userId = UserHolder.getUser().getId();
//        String key = RedisConstants.BLOG_LIKED_KEY + id;
//        Boolean isMember = stringRedisTemplate.opsForSet().isMember(key, userId.toString());
//        if (Boolean.FALSE.equals(isMember)) { // 當前用戶尚未點讚
//            // 資料庫點讚數 + 1
//            boolean isSuccess = update().setSql("liked = liked + 1").eq("id", id).update();
//            if (isSuccess) {
//                // 保存用戶到redis set集合
//                stringRedisTemplate.opsForSet().add(key, userId.toString());
//            }
//        } else {
//            // 資料庫點讚數 - 1
//            boolean isSuccess = update().setSql("liked = liked - 1").eq("id", id).update();
//            if (isSuccess) {
//                // redis set集合移除用戶
//                stringRedisTemplate.opsForSet().remove(key, userId.toString());
//            }
//        }
//        return Result.ok();
//    }

    // 查詢Blog的作者
    private void queryBlogUser(Blog blog) {
        Long userId = blog.getUserId();
        User user = userService.getById(userId);
        blog.setName(user.getNickName());
        blog.setIcon(user.getIcon());
    }

    // 用戶是否已對這篇Blog點讚
    private void isBlogLiked(Blog blog) {
        UserDTO user = UserHolder.getUser();
        if (user == null) {
            return;
        }
        Long userId = user.getId();
        String key = RedisConstants.BLOG_LIKED_KEY + blog.getId();
        Double score = stringRedisTemplate.opsForZSet().score(key, userId.toString());
        blog.setIsLike(score != null);
    }

    @Override
    public Result queryBlogLikes(Long id) {
        String key = RedisConstants.BLOG_LIKED_KEY + id;
        // 查詢Top 5點讚用戶
        Set<String> top5 = stringRedisTemplate.opsForZSet().range(key, 0, 4);
        if (top5 == null || top5.isEmpty()) {
            return Result.ok(Collections.emptyList());
        }
        // 解析用戶id
        List<Long> userIds = top5.stream().map(Long::valueOf).collect(Collectors.toList());
        String idStr = StrUtil.join(",", userIds);
        // 根據用戶id查詢用戶 WHERE id IN (1011 , 1) ORDER BY FIELD (id, 1011, 1)
        List<UserDTO> userDTOs = userService.query()
                .in("id", userIds).last("ORDER BY FIELD (id," + idStr + ")").list()
                .stream().map(user -> {
                    UserDTO userDTO = new UserDTO();
                    BeanUtils.copyProperties(user, userDTO);
                    return userDTO;
                }).collect(Collectors.toList());
        return Result.ok(userDTOs);
    }

    @Override
    public Result saveBlog(Blog blog) {
        UserDTO user = UserHolder.getUser();
        blog.setUserId(user.getId());
        boolean isSuccess = save(blog);
        if (!isSuccess) {
            return Result.fail("error");
        }
        // select * from tb_follow where follow_user_id = ?
        List<Follow> follows = followService.query().eq("follow_user_id", user.getId()).list();
        for (Follow follow : follows) {
            Long fansId = follow.getUserId();
            String key = RedisConstants.FEED_KEY + fansId;
            stringRedisTemplate.opsForZSet().add(key, blog.getId().toString(), System.currentTimeMillis());
        }
        return Result.ok(blog.getId());
    }

    @Override
    public Result queryBlogOfFollow(Long max, Integer offset) {
        // 找當前用戶的收件廂
        Long userId = UserHolder.getUser().getId();
        String key = RedisConstants.FEED_KEY + userId;
        // zrevrangebyscore key Max Min Limit offset count
        Set<ZSetOperations.TypedTuple<String>> typedTuples = stringRedisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, 0, max, offset, 2);
        // 非空判斷
        if (typedTuples == null || typedTuples.isEmpty()) {
            return Result.ok();
        }
        // 解析數據: blogId minTime 時間戳 offset
        List<Long> blogIds = new ArrayList<>(typedTuples.size());
        long minTime = 0;
        int calOffset = 1;
        for (ZSetOperations.TypedTuple<String> tuple : typedTuples) {
            blogIds.add(Long.valueOf(tuple.getValue()));
            long time = tuple.getScore().longValue();
            if (time == minTime) {
                calOffset++;
            } else {
                minTime = time;
                calOffset = 1;
            }
        }
        // 根據blogId查詢blog
        String join = StrUtil.join(",", blogIds);
        List<Blog> blogs = query().in("id", blogIds).last("ORDER BY FIELD(id," + join + ")").list();
        for (Blog blog : blogs) {
            queryBlogUser(blog);
            isBlogLiked(blog);
        }
        // 封裝並返回
        ScrollResult scrollResult = new ScrollResult();
        scrollResult.setList(blogs);
        scrollResult.setOffset(calOffset);
        scrollResult.setMinTime(minTime);
        return Result.ok(scrollResult);
    }

}
