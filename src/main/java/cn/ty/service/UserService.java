package cn.ty.service;

import cn.ty.pojo.User;

public interface UserService {
    User login(User user);
    void regist(User user);
}
