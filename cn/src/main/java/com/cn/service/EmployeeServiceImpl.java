package com.cn.service;

import com.cn.dao.EmployeeDao;
import com.cn.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService{
    private EmployeeDao employeeDao;

    @Autowired
    public EmployeeServiceImpl(EmployeeDao employeeDao){
        this.employeeDao=employeeDao;
    }


    @Override
    public void delete(Integer id) {
        employeeDao.delete(id);
    }

    @Override
    public void update(Employee employee) {
        employeeDao.update(employee);
    }


    @Override
    public Employee findById(Integer id) {
        return employeeDao.findById(id);
    }



    @Override
    public void save(Employee employee) {
        employeeDao.save(employee);
    }



    @Override
    public List<Employee> lists() {
        return employeeDao.lists();
    }


}
