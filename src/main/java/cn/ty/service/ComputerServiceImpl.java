package cn.ty.service;

import cn.ty.mapper.ComputerMapper;
import cn.ty.pojo.Computer;
import cn.ty.pojo.ComputerExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ComputerServiceImpl implements ComputerService{
    @Autowired
    private ComputerMapper computerMapper;

    @Override
    public PageInfo<Computer> findAll(int pageNum, int pageSize, String name) {
        PageHelper.startPage(pageNum,pageSize);//配置分页
        ComputerExample example = new ComputerExample();
        ComputerExample.Criteria cri = example.createCriteria();
        if(!StringUtils.isEmpty(name)){//名字不为空 参与查询
            cri.andNameLike("%"+name+"%");
        }
        List<Computer> list = computerMapper.selectByExample(example);
        PageInfo<Computer> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public void add(Computer computer) {
        computerMapper.insertSelective(computer);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return computerMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Computer selectByPrimaryKey(Integer id) {
        return computerMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Computer record) {
        return computerMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public void updateStatus(Computer computer) {
        computerMapper.updateByPrimaryKeySelective(computer);
    }
}
