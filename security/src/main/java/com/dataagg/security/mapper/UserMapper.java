package com.dataagg.security.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.dataagg.security.domain.User;

/**
 * Created by watano on 2017/2/9.
 */
public interface UserMapper extends BaseMapper<User> {
	User getByName(String username);
}
