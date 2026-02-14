package com.Moczk1.controller;

import com.Moczk1.common.R;
import com.Moczk1.dto.DishDto;
import com.Moczk1.entity.Category;
import com.Moczk1.entity.Dish;
import com.Moczk1.entity.Employee;
import com.Moczk1.mapper.CategoryMapper;
import com.Moczk1.service.CategoryService;
import com.Moczk1.service.DishFlavorService;
import com.Moczk1.service.DishService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save (@RequestBody  DishDto dishDto) {
        log.info("传输数据为 = {}", dishDto);
        dishService.saveWithFlavors(dishDto);

        return R.success("新增菜品成功！");
    }

    @GetMapping("/page")
    public R<Page> page (int page, int pageSize, String name) {
        log.info("page = {}, pagesize = {}, name = {}", page, pageSize, name);
        Page<Dish> pageInfo = new Page(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>(page, pageSize);

        LambdaQueryWrapper<Dish> queryWrapper =  new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        queryWrapper.like(StringUtils.isNotEmpty(name),Dish::getName, name);

        dishService.page(pageInfo, queryWrapper);
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");
        List<Dish> records = pageInfo.getRecords();

//        List<DishDto> list = records.stream().map((item) -> {
//            DishDto dishDto = new DishDto();
//
//            BeanUtils.copyProperties(item, dishDto);
//
//            Long categoryId = item.getCategoryId();                     // 获取page里面records的每一个dish对象的categoryId
//
//            Category category = categoryService.getById(categoryId);    // 根据categoryId查询category对象
//            if (category != null) {                                     // 防止category为空
//                String categoryName = category.getName();               // 通过查询的category对象获取categoryName
//                dishDto.setCategoryName(categoryName);                  // 将categoryName赋值给dishDto对象的categoryName
//            }
//
//            return dishDto;
//        }).collect(Collectors.toList());

        Set<Long> categoryIds = records.stream()
                .map(Dish::getCategoryId)
                .collect(Collectors.toSet());

        Map<Long, String> categoryMap = categoryService.listByIds(categoryIds).stream()
                .collect(Collectors.toMap(Category::getId, Category::getName));
        List<DishDto> list = records.stream().map(item -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            dishDto.setCategoryName(categoryMap.get(item.getCategoryId()));
            return dishDto;
        }).collect(Collectors.toList());


        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }

    @GetMapping("/{id}")
    public R<DishDto> show(@PathVariable  Long id) {
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        log.info(dishDto.toString());
        dishService.updateWithFlavor(dishDto);
        return R.success("修改成功");
    }

}
