package com.selvesperer.knoeien.security;


import javax.inject.Named;

import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.core.userdetails.UserDetails;

import com.selvesperer.knoeien.data.domain.User;

@Named(value = "saltSource")
public class SaltSourceImpl implements SaltSource {

    @Override
    public Object getSalt(UserDetails _user) {
	String salt = ((User)_user).salt();
	return salt;
    }

}
