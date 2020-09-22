package com.soft1851.cloud.mapper;

import com.soft1851.cloud.entity.TestUser;
import org.apache.ibatis.annotations.Select;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/16
 * @Version 1.0
 */
public interface TestUserMapper {

    /**
     * 通过id获取用户信息
     * @param id
     * @return
     */
    @Select("SELECT * FROM test_user WHERE id = #{id}")
    public TestUser getUserById(int id);
}
