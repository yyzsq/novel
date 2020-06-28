package com.gemframework.modules.extend.student.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.gemframework.GemAdminApplication;
import com.gemframework.modules.extend.student.mapper.StudentMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

@SpringBootTest(classes = GemAdminApplication.class)
@Slf4j
@Service
class StudentServiceImplTest {

    @Autowired
    StudentMapper studentMapper;



    @Test
    @DS("slave_1")
    void insert1() {
        studentMapper.insert1("DS-SALVE-1测试1");
        log.info("保存成功！");
    }

    @Test
    @DS("slave_2")
    void insert2() {
        studentMapper.insert2("DS-SALVE-2测试2");
        log.info("保存成功！");
    }

    @Test
    void insert3() {
        studentMapper.insert3("DS-SALVE-3测试");
        log.info("保存成功！");
    }

}