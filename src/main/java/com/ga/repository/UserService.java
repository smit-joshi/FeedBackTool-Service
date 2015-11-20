package com.ga.repository;

import com.ga.exception.GAException;
import com.ga.persistance.entity.UserDetail;

public interface UserService {

    UserDetail userLogin(String username, String password) throws GAException;

}
