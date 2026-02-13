package com.Moczk1.service.impl;

import com.Moczk1.entity.Dish;
import com.Moczk1.mapper.DishMapper;
import com.Moczk1.service.DishService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

}

