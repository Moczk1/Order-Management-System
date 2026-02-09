package com.Moczk1.service.impl;

import com.Moczk1.entity.Employee;
import com.Moczk1.mapper.EmployeeMapper;
import com.Moczk1.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
