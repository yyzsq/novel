
package com.gemframework.modules.extend.student.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gemframework.modules.extend.student.entity.Student;
import com.gemframework.modules.extend.student.mapper.StudentMapper;
import com.gemframework.modules.extend.student.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Title: StudentServiceImpl
 * @Date: 2020-05-24 23:10:09
 * @Version: v1.0
 * @Description: 学生信息服务实现类
 * @Author: gem
 * @Email: gemframe@163.com
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
@Slf4j
@Service
@DS("master")
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    StudentMapper studentMapper;

    @DS("slave_1")
    public void insert1(String name) {
        studentMapper.insert1("DS-SALVE-1测试1");
        log.info("保存成功！");
    }

    @DS("slave_2")
    public void insert2(String name) {
        studentMapper.insert2("DS-SALVE-2测试2");
        log.info("保存成功！");
    }

    public void insert3(String name) {
        studentMapper.insert3("DS-SALVE-3测试");
        log.info("保存成功！");
    }
}