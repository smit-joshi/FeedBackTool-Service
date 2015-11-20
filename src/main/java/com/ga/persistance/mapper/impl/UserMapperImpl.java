package com.ga.persistance.mapper.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ga.persistance.entity.UserDetail;
import com.ga.persistance.mapper.IUserMapper;

@Repository
public class UserMapperImpl implements IUserMapper {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public UserDetail userLogin(String userName, String password) {
        return null;
    }
}
