package com.Moczk1.controller;

import com.Moczk1.common.R;
import com.Moczk1.entity.Category;
import com.Moczk1.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody Category category) {
         categoryService.save(category);
         log.info("新增种类成功");
         return R.success("新增菜品成功");
    }


    @GetMapping("/page")
    public R<Page> page (int page, int pageSize) {
        Page<Category>  pageInfo = new Page<>(page, pageSize);

        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        lambdaQueryWrapper.orderByAsc(Category::getSort);

        categoryService.page(pageInfo, lambdaQueryWrapper);

        return R.success(pageInfo);
    }

    @DeleteMapping
    public R<String> delete (Long ids ) {
        log.info("删除分类 id={} ", ids);

        categoryService.remove(ids);
        return R.success("删除分类信息成功");
    }

    @PutMapping
    public R<String> update (@RequestBody Category category) {
        log.info("修改分类信息");
        categoryService.updateById(category);
        return R.success("修改分类信息成功");
    }
}
