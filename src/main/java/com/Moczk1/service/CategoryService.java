package com.Moczk1.service;

import com.Moczk1.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

public interface CategoryService extends IService<Category> {
    public void remove(Long ids);
}
