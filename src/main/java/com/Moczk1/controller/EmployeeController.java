package com.Moczk1.controller;

import com.Moczk1.common.R;
import com.Moczk1.entity.Employee;
import com.Moczk1.service.EmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.schema.Server;
import org.apache.ibatis.javassist.LoaderClassPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * employee 登录
     * @param request
     * @param e
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee e) {

        String password = e.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        LambdaQueryWrapper<Employee> quaryWapper = new LambdaQueryWrapper<>();
        quaryWapper.eq(Employee::getUsername, e.getUsername());
        Employee emp = employeeService.getOne(quaryWapper);
//        System.out.println(emp.getPassword());
        if (emp == null) {
            return R.error("登陆失败");
        }

        if (!emp.getPassword().equals(password))
            return R.error("密码错误");

        if (emp.getStatus() == 0)
            return R.error("账号已禁用");

        request.getSession().setAttribute("employee", emp.getId());

        return R.success(emp);
    }
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    @PostMapping
    public R<String> save (HttpServletRequest request,@RequestBody Employee employee) {
        log.info("新增员工信息");
        long id = Thread.currentThread().getId();
        log.info("本次请求的线程id = {}", id);
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
        Long empId = (Long)request.getSession().getAttribute("employee");
//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);

        employeeService.save(employee);
        log.info("新增员工成功");
        return R.success("新增员工成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        log.info("page = {}, pagesize = {}, name = {}", page, pageSize, name);
        // 分页构造器
        Page pageInfo = new Page(page, pageSize);

        // 构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();

        //过滤器
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        // 添加排序条件
        employeeService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }
    @PutMapping
    public R<String> update (HttpServletRequest request, @RequestBody Employee employee) {
        log.info(employee.toString());
        Long empId = (Long) request.getSession().getAttribute("employee");
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(empId);
        employeeService.updateById(employee);
        return R.success("员工信息修改成功");
    }

    /**
     * 查询员工id信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        log.info("查询员工id");
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return R.success(employee);
        }
        return R.error("没有查询到员工ID");
    }

}
