package com.ga.persistance.mapper;

import com.ga.persistance.entity.UserDetail;

/**
 * The Interface IUserMapper.
 *
 * @author Smit
 */
public interface IUserMapper {

    /**
     * User login.
     *
     * @param userName the user name
     * @param password the password
     * @return the user detail
     */
    UserDetail userLogin(String userName, String password);
}
