
package com.gemframework.modules.extend.author.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.gemframework.common.annotation.Log;
import com.gemframework.common.utils.GemBeanUtils;
import com.gemframework.common.constant.GemModules;
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

import com.gemframework.modules.extend.author.entity.NovelVisited;
import com.gemframework.modules.extend.author.entity.NovelVisitedVo;
import com.gemframework.modules.extend.author.service.NovelVisitedService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Title: NovelVisitedController
 * @Date: 2020-07-06 14:29:04
 * @Version: v1.0
 * @Description: 浏览历史控制器
 * @Author: yuanrise
 * @Email: 1649951967@qq.com
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
@Slf4j
@RestController
@RequestMapping(GemModules.Extend.PATH_PRE+"/author/novelVisited")
public class NovelVisitedController extends BaseController {

    private static final String moduleName = "浏览历史";

    @Autowired
    private NovelVisitedService novelVisitedService;

    /**
     * 获取列表分页
     * @return
     */
    @GetMapping("/page")
    @RequiresPermissions("novelVisited:page")
    public BaseResultData page(PageInfo pageInfo, NovelVisitedVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        Page page = novelVisitedService.page(setOrderPage(pageInfo),queryWrapper);
        return BaseResultData.SUCCESS(page.getRecords(),page.getTotal());
    }
    /**
     * 获取列表
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("novelVisited:list")
    public BaseResultData list(NovelVisitedVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        List list = novelVisitedService.list(queryWrapper);
        return BaseResultData.SUCCESS(list);
    }

    /**
     * 添加
     * @return
     */
    @Log(type = OperateType.ALTER,value = "保存"+moduleName)
    @PostMapping("/save")
    @RequiresPermissions("novelVisited:save")
    public BaseResultData save(@RequestBody NovelVisitedVo vo) {
        GemValidate(vo, SaveValidator.class);
        NovelVisited entity = GemBeanUtils.copyProperties(vo, NovelVisited.class);
        if(!novelVisitedService.save(entity)){
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
    @RequiresPermissions("novelVisited:delete")
    public BaseResultData delete(Long id,String ids) {
        if(id!=null) novelVisitedService.removeById(id);
        if(StringUtils.isNotBlank(ids)){
            List<Long> listIds = Arrays.asList(ids.split(",")).stream().map(s ->Long.parseLong(s.trim())).collect(Collectors.toList());
            if(listIds!=null && !listIds.isEmpty()){
                    novelVisitedService.removeByIds(listIds);
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
    @RequiresPermissions("novelVisited:update")
    public BaseResultData update(@RequestBody NovelVisitedVo vo) {
        GemValidate(vo, UpdateValidator.class);
        NovelVisited entity = GemBeanUtils.copyProperties(vo, NovelVisited.class);
        if(!novelVisitedService.updateById(entity)){
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
    @RequiresPermissions("novelVisited:info")
    public BaseResultData info(Long id) {
        NovelVisited info = novelVisitedService.getById(id);
        return BaseResultData.SUCCESS(info);
    }

}