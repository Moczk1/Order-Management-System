package com.Moczk1.common;


import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 自定义元数据处理器
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段数据填充[inserting...]");
        log.info(metaObject.toString());
        long id = Thread.currentThread().getId();
        log.info("本次请求的线程id = {}", id);
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createUser", BaseContext.getId());
        metaObject.setValue("updateUser", BaseContext.getId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段数据填充[updating...]");
        log.info(metaObject.toString());
        long id = Thread.currentThread().getId();
        log.info("本次请求的线程id = {}", id);
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", BaseContext.getId());
    }
}
