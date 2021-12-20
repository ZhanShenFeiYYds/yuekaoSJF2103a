package cn.ty.service;

import cn.ty.pojo.Computer;
import com.github.pagehelper.PageInfo;

public interface ComputerService {
    PageInfo<Computer> findAll(int pageNum, int pageSize, String name);
    void add(Computer computer);
    int deleteByPrimaryKey(Integer id);
    Computer selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(Computer record);
    void updateStatus(Computer computer);
}
