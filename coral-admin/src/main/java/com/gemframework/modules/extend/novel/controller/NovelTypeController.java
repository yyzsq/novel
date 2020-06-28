
package com.gemframework.modules.extend.novel.controller;

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

import com.gemframework.modules.extend.novel.entity.NovelType;
import com.gemframework.modules.extend.novel.entity.NovelTypeVo;
import com.gemframework.modules.extend.novel.service.NovelTypeService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Title: NovelTypeController
 * @Date: 2020-06-21 15:58:17
 * @Version: v1.0
 * @Description: 小说分类控制器
 * @Author: yuanrise
 * @Email: 1649951967@qq.com
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
@Slf4j
@RestController
@RequestMapping(GemModules.Extend.PATH_PRE+"/novel/novelType")
public class NovelTypeController extends BaseController {

    private static final String moduleName = "小说分类";

    @Autowired
    private NovelTypeService novelTypeService;

    /**
     * 获取列表分页
     * @return
     */
    @GetMapping("/page")
    @RequiresPermissions("novelType:page")
    public BaseResultData page(PageInfo pageInfo, NovelTypeVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        Page page = novelTypeService.page(setOrderPage(pageInfo),queryWrapper);
        List list=new ArrayList();
        for(Object obj:page.getRecords()){
            NovelTypeVo typeVo=GemBeanUtils.copyProperties((NovelType)obj,NovelTypeVo.class);
            if(StringUtils.equals(typeVo.getFuId(),"0")){
                typeVo.setFuName("顶级目录");
            }else {
                NovelType novelType = novelTypeService.getById(typeVo.getFuId());
                typeVo.setFuName(novelType.getTypeName());
            }
            list.add(typeVo);
        }
        return BaseResultData.SUCCESS(list,page.getTotal());
    }
    /**
     * 获取列表
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("novelType:list")
    public BaseResultData list(NovelTypeVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        List<NovelType> list = novelTypeService.list(queryWrapper);
        return BaseResultData.SUCCESS(list);
    }

    /**
     * 添加
     * @return
     */
    @Log(type = OperateType.ALTER,value = "保存"+moduleName)
    @PostMapping("/save")
    @RequiresPermissions("novelType:save")
    public BaseResultData save(@RequestBody NovelTypeVo vo) {
        GemValidate(vo, SaveValidator.class);
        NovelType entity = GemBeanUtils.copyProperties(vo, NovelType.class);
        if(!novelTypeService.save(entity)){
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
    @RequiresPermissions("novelType:delete")
    public BaseResultData delete(Long id,String ids) {
        if(id!=null) novelTypeService.removeById(id);
        if(StringUtils.isNotBlank(ids)){
            List<Long> listIds = Arrays.asList(ids.split(",")).stream().map(s ->Long.parseLong(s.trim())).collect(Collectors.toList());
            if(listIds!=null && !listIds.isEmpty()){
                    novelTypeService.removeByIds(listIds);
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
    @RequiresPermissions("novelType:update")
    public BaseResultData update(@RequestBody NovelTypeVo vo) {
        GemValidate(vo, UpdateValidator.class);
        NovelType entity = GemBeanUtils.copyProperties(vo, NovelType.class);
        if(!novelTypeService.updateById(entity)){
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
    @RequiresPermissions("novelType:info")
    public BaseResultData info(Long id) {
        NovelType info = novelTypeService.getById(id);
        return BaseResultData.SUCCESS(info);
    }

}