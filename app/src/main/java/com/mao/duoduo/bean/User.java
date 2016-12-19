package com.mao.duoduo.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by Mao on 2016/11/2.
 */
public class User extends BmobUser {

    // 姓名
    private String name;

    // 年龄
    private int age;

    // 头像
    private String avatar;

    public User() {

    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
