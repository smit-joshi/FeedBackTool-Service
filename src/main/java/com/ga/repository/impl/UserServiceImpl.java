package com.ga.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ga.exception.ErrorCodes;
import com.ga.exception.GAException;
import com.ga.persistance.entity.UserDetail;
import com.ga.persistance.mapper.IUserMapper;
import com.ga.repository.IUserService;

/**
 * The Class UserServiceImpl.
 *
 * @author Smit
 */
@Service
@Transactional
public class UserServiceImpl implements IUserService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    /** The user mapper. */
    @Autowired
    IUserMapper userMapper;

    /*
     * (non-Javadoc)
     * 
     * @see com.ga.repository.IUserService#userLogin(java.lang.String, java.lang.String)
     */
    @Override
    public UserDetail userLogin(String userName, String password) throws GAException {
        LOGGER.info("User Login Service called!!");
        UserDetail userDetail = userMapper.userLogin(userName, password);

        if (userDetail == null) {
            throw new GAException(ErrorCodes.GA_AUTH_FAILED, "Username and pasword not match");
        }
        System.out.println("User name :" + userDetail.getUserName());
        LOGGER.info("User Login Service complete!!");
        return userDetail;
    }
}