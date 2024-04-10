package com.itheima;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class ApplicationTests {

	@Autowired
	private UserDao userDao;

	@Test
	void testSelectAll01() {
		QueryWrapper queryWrapper = new QueryWrapper<>();
		queryWrapper.lt("age", 30);
		List<User> users = userDao.selectList(queryWrapper);
		System.out.println(users);
	}

	@Test
	void testSelectAll02() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().lt(User::getAge, 26);
		List<User> users = userDao.selectList(queryWrapper);
		System.out.println(users);
	}

	@Test
	void testSelectAll03() {
		LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();

//		lambdaQueryWrapper.lt(User::getAge, 30);
//		lambdaQueryWrapper.gt(User::getAge, 25);

//		lambdaQueryWrapper.lt(User::getAge, 26);
//		lambdaQueryWrapper.or();
//		lambdaQueryWrapper.gt(User::getAge, 34);

//		lambdaQueryWrapper.lt(User::getAge, 30).gt(User::getAge, 25);
//		lambdaQueryWrapper.lt(User::getAge, 26).or().gt(User::getAge, 34);

		List<User> users = userDao.selectList(lambdaQueryWrapper);
		System.out.println(users);
	}

	@Test
	void testSelectAll04() {
		Integer i1 = 30;
		Integer i2 = null;
		LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//		lambdaQueryWrapper.lt(null != i1, User::getAge, i1)
//				          .gt(null != i2, User::getAge, i2);
		lambdaQueryWrapper.lt(null != i1, User::getAge, i1);
		lambdaQueryWrapper.gt(null != i2, User::getAge, i2);
		List<User> users = userDao.selectList(lambdaQueryWrapper);
		System.out.println(users);
	}

	@Test
	void testSelectAll05() {
		LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.select(User::getId, User::getName, User::getAge);
		List<User> users = userDao.selectList(lambdaQueryWrapper);
		System.out.println(users);
	}

	@Test
	void testSelectAll06() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.select("count(*) as count, tel");
		queryWrapper.groupBy("tel");
		List<Map<String, Object>> maps = userDao.selectMaps(queryWrapper);
		System.out.println(maps);
	}

    @Test
    void testSelectAll07() {
        String name = "Emma";
        String password = "123456";
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getName, name).eq(User::getPassword, password);
        User user = userDao.selectOne(lambdaQueryWrapper);
        System.out.println(user);
    }

    @Test
    void testSelectAll08() {
        Integer a = 25;
        Integer b = 29;
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // lt le gt ge eq between
        lambdaQueryWrapper.between(User::getAge, a, b);
        List<User> userList = userDao.selectList(lambdaQueryWrapper);
        System.out.println(userList);
    }

    @Test
    void testSelectAll09() {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(User::getName, "E"); // WHERE name LIKE %E%
        // lambdaQueryWrapper.likeRight(User::getName, "E"); // WHERE name LIKE E%
        // lambdaQueryWrapper.likeLeft(User::getName, "E"); // WHERE name LIKE %E
        List<User> userList = userDao.selectList(lambdaQueryWrapper);
        System.out.println(userList);
        // https://baomidou.com/pages/10c804/#abstractwrapper
    }

}
