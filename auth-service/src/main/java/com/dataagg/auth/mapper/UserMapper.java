package com.dataagg.auth.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.dataagg.auth.domain.User;

/**
 * Created by watano on 2017/2/9.
 */
public interface UserMapper extends BaseMapper<User> {
	User getByName(String username);
}
