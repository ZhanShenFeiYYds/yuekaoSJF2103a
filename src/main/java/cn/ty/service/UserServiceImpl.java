package cn.ty.service;

import cn.ty.mapper.UserMapper;
import cn.ty.pojo.User;
import cn.ty.pojo.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;
    @Override
    public User login(User user) {
        UserExample example = new UserExample();
        UserExample.Criteria cri = example.createCriteria();
        cri.andUnameEqualTo(user.getUname()).andUpassEqualTo(user.getUpass());
        List<User> users = userMapper.selectByExample(example);
        if(users.size()>0){
            return users.get(0);
        }else {
            return null;
        }
    }

    @Override
    public void regist(User user) {
        userMapper.insertSelective(user);
    }
}
