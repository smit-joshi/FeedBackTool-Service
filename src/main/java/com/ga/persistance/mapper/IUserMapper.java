package com.ga.persistance.mapper;

import com.ga.persistance.entity.UserDetail;

public interface IUserMapper {

    UserDetail userLogin(String userName, String password);
}
