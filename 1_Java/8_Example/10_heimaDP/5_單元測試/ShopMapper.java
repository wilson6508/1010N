package com.hmdp.mapper;

import com.hmdp.entity.Shop;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
//@Mapper
public interface ShopMapper extends BaseMapper<Shop> {

//    @Update("UPDATE tb_shop SET " +
//            "name = #{name}, type_id = #{typeId}, area = #{area}, address = #{address}, " +
//            "avg_price = #{avgPrice}, sold = #{sold}, comments = #{comments}, score = #{score}, " +
//            "open_hours = #{openHours} " +
//            "WHERE id = #{id}")
//    void updateEmp(Shop shop);

}
