package com.cn.service;

import com.cn.entity.Employee;

import java.util.List;

public interface EmployeeService {
    //员工列表方法
    List<Employee> lists();

    //保存员工信息
    void save(Employee employee);

    //根据ID查询一个
    Employee findById(Integer id);

    //更新员工信息
    void update(Employee employee);

    //删除员工信息
    void delete(Integer id);
}
