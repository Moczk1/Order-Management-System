package com.Moczk1.service;

import com.Moczk1.dto.DishDto;
import com.Moczk1.entity.Dish;
import com.Moczk1.mapper.DishMapper;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DishService extends IService<Dish> {
    public void saveWithFlavors(DishDto dishDto);

    // 根据id查询菜品信息和口味信息
    public DishDto getByIdWithFlavor(Long id);


    public void updateWithFlavor(DishDto dishDto);
}
