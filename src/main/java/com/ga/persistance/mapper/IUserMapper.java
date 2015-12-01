package com.ga.persistance.mapper;

import com.ga.exception.GAException;
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
     * @return the user detail
     * @throws GAException the GA exception
     */
    UserDetail userLogin(String userName) throws GAException;
}
