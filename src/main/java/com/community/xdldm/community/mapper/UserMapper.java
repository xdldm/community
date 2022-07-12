package com.community.xdldm.community.mapper;

import com.community.xdldm.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    public void insert(User user);

    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);
    @Select("select * from user where account_id=#{id}")
    User findByAccountId(@Param("id") Long id);
}
