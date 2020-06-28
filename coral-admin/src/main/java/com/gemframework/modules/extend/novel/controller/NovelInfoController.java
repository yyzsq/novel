
package com.gemframework.modules.extend.novel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.gemframework.common.annotation.Log;
import com.gemframework.common.utils.GemBeanUtils;
import com.gemframework.common.constant.GemModules;
import com.gemframework.model.entity.po.User;
import com.gemframework.modules.extend.novel.entity.NovelType;
import com.gemframework.modules.extend.novel.entity.NovelTypeVo;
import com.gemframework.modules.extend.novel.service.NovelTypeService;
import com.gemframework.modules.perkit.BaseController;
import com.gemframework.model.common.BaseResultData;
import com.gemframework.model.common.PageInfo;
import com.gemframework.model.common.validator.SaveValidator;
import com.gemframework.model.common.validator.UpdateValidator;
import com.gemframework.model.enums.ErrorCode;
import com.gemframework.model.enums.OperateType;
import com.gemframework.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import com.gemframework.modules.extend.novel.entity.NovelInfo;
import com.gemframework.modules.extend.novel.entity.NovelInfoVo;
import com.gemframework.modules.extend.novel.service.NovelInfoService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Title: NovelInfoController
 * @Date: 2020-06-21 13:56:54
 * @Version: v1.0
 * @Description: 小说信息控制器
 * @Author: yuanrise
 * @Email: 1649951967@qq.com
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
@Slf4j
@RestController
@RequestMapping(GemModules.Extend.PATH_PRE+"/novel/novelInfo")
public class NovelInfoController extends BaseController {

    private static final String moduleName = "小说信息";

    @Autowired
    private NovelInfoService novelInfoService;
    @Autowired
    private NovelTypeService novelTypeService;
    @Qualifier("userServiceImpl")
    @Autowired
    private UserService userService;

    /**
     * 获取列表分页
     * @return
     */
    @GetMapping("/page")
    @RequiresPermissions("novelInfo:page")
    public BaseResultData page(PageInfo pageInfo, NovelInfoVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        Page page = novelInfoService.page(setOrderPage(pageInfo),queryWrapper);
        List list=new ArrayList();
        for(Object obj:page.getRecords()){
            NovelInfoVo infoVo=GemBeanUtils.copyProperties((NovelInfo)obj,NovelInfoVo.class);
            NovelType novelType1=novelTypeService.getById(infoVo.getType1());
            infoVo.setType1Name(novelType1.getTypeName());
            NovelType novelType2=novelTypeService.getById(infoVo.getType2());
            infoVo.setType2Name(novelType2.getTypeName());
            User user=userService.getById(infoVo.getAuthorId());
            if(user!=null){
                infoVo.setAuthorName(user.getRealname());
            }
            list.add(infoVo);
        }
        return BaseResultData.SUCCESS(list,page.getTotal());
    }
    /**
     * 获取列表
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("novelInfo:list")
    public BaseResultData list(NovelInfoVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        List list = novelInfoService.list(queryWrapper);
        return BaseResultData.SUCCESS(list);
    }

    /**
     * 添加
     * @return
     */
    @Log(type = OperateType.ALTER,value = "保存"+moduleName)
    @PostMapping("/save")
    @RequiresPermissions("novelInfo:save")
    public BaseResultData save(@RequestBody NovelInfoVo vo) {
        vo.setStatus("0");
        GemValidate(vo, SaveValidator.class);
        NovelInfo entity = GemBeanUtils.copyProperties(vo, NovelInfo.class);
        if(!novelInfoService.save(entity)){
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
    @RequiresPermissions("novelInfo:delete")
    public BaseResultData delete(Long id,String ids) {
        if(id!=null) novelInfoService.removeById(id);
        if(StringUtils.isNotBlank(ids)){
            List<Long> listIds = Arrays.asList(ids.split(",")).stream().map(s ->Long.parseLong(s.trim())).collect(Collectors.toList());
            if(listIds!=null && !listIds.isEmpty()){
                    novelInfoService.removeByIds(listIds);
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
    @RequiresPermissions("novelInfo:update")
    public BaseResultData update(@RequestBody NovelInfoVo vo) {
        GemValidate(vo, UpdateValidator.class);
        NovelInfo entity = GemBeanUtils.copyProperties(vo, NovelInfo.class);
        if(!novelInfoService.updateById(entity)){
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
    @RequiresPermissions("novelInfo:info")
    public BaseResultData info(Long id) {
        NovelInfo info = novelInfoService.getById(id);
        return BaseResultData.SUCCESS(info);
    }

}