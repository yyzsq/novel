
package com.gemframework.modules.extend.student.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.gemframework.common.annotation.Log;
import com.gemframework.common.utils.GemBeanUtils;
import com.gemframework.common.constant.GemModules;
import com.gemframework.modules.extend.student.entity.Student;
import com.gemframework.modules.extend.student.entity.StudentVo;
import com.gemframework.modules.extend.student.service.StudentService;
import com.gemframework.modules.perkit.BaseController;
import com.gemframework.model.common.BaseResultData;
import com.gemframework.model.common.PageInfo;
import com.gemframework.model.common.validator.SaveValidator;
import com.gemframework.model.common.validator.UpdateValidator;
import com.gemframework.model.enums.ErrorCode;
import com.gemframework.model.enums.OperateType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Title: StudentController
 * @Date: 2020-05-24 23:10:09
 * @Version: v1.0
 * @Description: 学生信息控制器
 * @Author: gem
 * @Email: gemframe@163.com
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
@Slf4j
@RestController
@RequestMapping(GemModules.Extend.PATH_PRE+"/student/student")
public class StudentController extends BaseController {

    private static final String moduleName = "学生信息";

    @Autowired
    private StudentService studentService;

    /**
     * 获取列表分页
     * @return
     */
    @GetMapping("/page")
    @RequiresPermissions("student:page")
    public BaseResultData page(PageInfo pageInfo, StudentVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        Page page = studentService.page(setOrderPage(pageInfo),queryWrapper);
        return BaseResultData.SUCCESS(page.getRecords(),page.getTotal());
    }
    /**
     * 获取列表
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("student:list")
    public BaseResultData list(StudentVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        List list = studentService.list(queryWrapper);
        return BaseResultData.SUCCESS(list);
    }

    /**
     * 添加
     * @return
     */
    @Log(type = OperateType.ALTER,value = "保存"+moduleName)
    @PostMapping("/save")
    @RequiresPermissions("student:save")
    public BaseResultData save(@RequestBody StudentVo vo) {
        GemValidate(vo, SaveValidator.class);
        Student entity = GemBeanUtils.copyProperties(vo, Student.class);
        if(!studentService.save(entity)){
            return BaseResultData.ERROR(ErrorCode.SAVE_OR_UPDATE_FAIL);
        }
        return BaseResultData.SUCCESS(entity);
    }


    /**
     * 删除 & 批量刪除
     * @return
     */
    @Log(type = OperateType.ALTER,value = "删除"+moduleName)
    @PostMapping("/delete")
    @RequiresPermissions("student:delete")
    public BaseResultData delete(Long id,String ids) {
        if(id!=null) studentService.removeById(id);
        if(StringUtils.isNotBlank(ids)){
            List<Long> listIds = Arrays.asList(ids.split(",")).stream().map(s ->Long.parseLong(s.trim())).collect(Collectors.toList());
            if(listIds!=null && !listIds.isEmpty()){
                    studentService.removeByIds(listIds);
            }
        }
        return BaseResultData.SUCCESS();
    }


    /**
     * 编辑
     * @return
     */
    @Log(type = OperateType.ALTER,value = "编辑"+moduleName)
    @PostMapping("/update")
    @RequiresPermissions("student:update")
    public BaseResultData update(@RequestBody StudentVo vo) {
        GemValidate(vo, UpdateValidator.class);
        Student entity = GemBeanUtils.copyProperties(vo, Student.class);
        if(!studentService.updateById(entity)){
            return BaseResultData.ERROR(ErrorCode.SAVE_OR_UPDATE_FAIL);
        }
        return BaseResultData.SUCCESS(entity);
    }


    /**
     * 获取用户信息ById
     * @return
     */
    @Log(type = OperateType.NORMAL,value = "查看"+moduleName)
    @GetMapping("/info")
    @RequiresPermissions("student:info")
    public BaseResultData info(Long id) {
        Student info = studentService.getById(id);
        return BaseResultData.SUCCESS(info);
    }

}