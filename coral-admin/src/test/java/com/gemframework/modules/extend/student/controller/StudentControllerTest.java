package com.gemframework.modules.extend.student.controller;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.gemframework.GemAdminApplication;
import com.gemframework.modules.extend.student.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(classes = GemAdminApplication.class)
class StudentControllerTest {


    @Autowired
    StudentService studentService;


    @Test
    void insert1() {
        studentService.insert1("DS-SALVE-1测试1");
        log.info("保存成功！");
    }

    @Test
    void insert2() {
        studentService.insert2("DS-SALVE-2测试2");
        log.info("保存成功！");
    }

    @Test
    void insert3() {
        studentService.insert3("DS-SALVE-3测试");
        log.info("保存成功！");
    }
}