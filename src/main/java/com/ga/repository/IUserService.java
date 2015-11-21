package com.ga.repository;

import com.ga.exception.GAException;
import com.ga.persistance.entity.UserDetail;

/**
 * The Interface IUserService.
 *
 * @author Smit
 */
public interface IUserService {

    /**
     * User login.
     *
     * @param username the username
     * @param password the password
     * @return the user detail
     * @throws GAException the GA exception
     */
    UserDetail userLogin(String username, String password) throws GAException;

}
