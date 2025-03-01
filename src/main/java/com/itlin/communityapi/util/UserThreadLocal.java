package com.itlin.communityapi.util;

import com.itlin.communityapi.dao.pojo.User;

public class UserThreadLocal {
    private UserThreadLocal(){}

    private static final ThreadLocal<User> LOCAL = new ThreadLocal<>();

    public static void put(User user){
        LOCAL.set(user);
    }

    public static User get(){
        return LOCAL.get();
    }
    public static void remove(){
        LOCAL.remove();
    }
}
