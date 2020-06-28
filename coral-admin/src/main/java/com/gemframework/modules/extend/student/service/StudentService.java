
package com.gemframework.modules.extend.student.service;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gemframework.modules.extend.student.entity.Student;

/**
 * @Title: StudentService
 * @Date: 2020-05-24 23:10:09
 * @Version: v1.0
 * @Description: 学生信息服务接口
 * @Author: gem
 * @Email: gemframe@163.com
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
public interface StudentService extends IService<Student> {

    void insert1(String name) ;

    void insert2(String name) ;

    void insert3(String name) ;
}