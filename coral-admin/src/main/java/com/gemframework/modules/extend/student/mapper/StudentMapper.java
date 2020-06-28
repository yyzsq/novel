
package com.gemframework.modules.extend.student.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gemframework.modules.extend.student.entity.Student;
import org.springframework.stereotype.Repository;

/**
 * @Title: StudentMapper
 * @Date: 2020-05-24 23:10:09
 * @Version: v1.0
 * @Description: 学生信息持久层
 * @Author: gem
 * @Email: gemframe@163.com
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
@Repository
public interface StudentMapper extends BaseMapper<Student> {

    void insert1(String name);

    void insert2(String name);

    void insert3(String name);
}