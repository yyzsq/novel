
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

import com.gemframework.modules.extend.author.entity.NovelBookrack;
import com.gemframework.modules.extend.author.entity.NovelBookrackVo;
import com.gemframework.modules.extend.author.service.NovelBookrackService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Title: NovelBookrackController
 * @Date: 2020-07-06 14:29:04
 * @Version: v1.0
 * @Description: 我的书架控制器
 * @Author: yuanrise
 * @Email: 1649951967@qq.com
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
@Slf4j
@RestController
@RequestMapping(GemModules.Extend.PATH_PRE+"/author/novelBookrack")
public class NovelBookrackController extends BaseController {

    private static final String moduleName = "我的书架";

    @Autowired
    private NovelBookrackService novelBookrackService;

    /**
     * 获取列表分页
     * @return
     */
    @GetMapping("/page")
    @RequiresPermissions("novelBookrack:page")
    public BaseResultData page(PageInfo pageInfo, NovelBookrackVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        Page page = novelBookrackService.page(setOrderPage(pageInfo),queryWrapper);
        return BaseResultData.SUCCESS(page.getRecords(),page.getTotal());
    }
    /**
     * 获取列表
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("novelBookrack:list")
    public BaseResultData list(NovelBookrackVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        List list = novelBookrackService.list(queryWrapper);
        return BaseResultData.SUCCESS(list);
    }

    /**
     * 添加
     * @return
     */
    @Log(type = OperateType.ALTER,value = "保存"+moduleName)
    @PostMapping("/save")
    @RequiresPermissions("novelBookrack:save")
    public BaseResultData save(@RequestBody NovelBookrackVo vo) {
        GemValidate(vo, SaveValidator.class);
        NovelBookrack entity = GemBeanUtils.copyProperties(vo, NovelBookrack.class);
        if(!novelBookrackService.save(entity)){
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
    @RequiresPermissions("novelBookrack:delete")
    public BaseResultData delete(Long id,String ids) {
        if(id!=null) novelBookrackService.removeById(id);
        if(StringUtils.isNotBlank(ids)){
            List<Long> listIds = Arrays.asList(ids.split(",")).stream().map(s ->Long.parseLong(s.trim())).collect(Collectors.toList());
            if(listIds!=null && !listIds.isEmpty()){
                    novelBookrackService.removeByIds(listIds);
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
    @RequiresPermissions("novelBookrack:update")
    public BaseResultData update(@RequestBody NovelBookrackVo vo) {
        GemValidate(vo, UpdateValidator.class);
        NovelBookrack entity = GemBeanUtils.copyProperties(vo, NovelBookrack.class);
        if(!novelBookrackService.updateById(entity)){
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
    @RequiresPermissions("novelBookrack:info")
    public BaseResultData info(Long id) {
        NovelBookrack info = novelBookrackService.getById(id);
        return BaseResultData.SUCCESS(info);
    }

}