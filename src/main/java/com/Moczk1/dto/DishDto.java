package com.Moczk1.dto;

import com.Moczk1.entity.Dish;
import com.Moczk1.entity.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class DishDto extends Dish {         // 这里继承了Dish实体类，所以DishDto对象中拥有Dish实体类中的所有属性

    /**
     * 风味
     */
    private List<DishFlavor> flavors = new ArrayList<>(); // 同一个食材可以有多个风味选项，故这里使用List

    /**
     * 分类名
     */
    private String categoryName;

    private Integer copies;
}
