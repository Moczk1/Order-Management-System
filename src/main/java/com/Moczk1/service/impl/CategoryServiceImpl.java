package com.Moczk1.service.impl;

import com.Moczk1.common.CustomException;
import com.Moczk1.entity.Category;
import com.Moczk1.entity.Dish;
import com.Moczk1.entity.Setmeal;
import com.Moczk1.mapper.CategoryMapper;
import com.Moczk1.service.CategoryService;
import com.Moczk1.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishServiceImpl dishService;

    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long ids){
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, ids);

        int count = dishService.count();

        if (count > 0 ) {
            throw new CustomException("当前分类下，关联了菜品");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();

        setmealLambdaQueryWrapper.eq(Setmeal::getId, ids);

        int count2 = setmealService.count();
        if (count2 > 0) {
            throw new CustomException("当前分类下，关联了套餐");
        }

        super.removeById(ids);
    }

}
