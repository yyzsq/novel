
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

import com.gemframework.modules.extend.novel.entity.DetailChapter;
import com.gemframework.modules.extend.novel.entity.DetailChapterVo;
import com.gemframework.modules.extend.novel.service.DetailChapterService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Title: DetailChapterController
 * @Date: 2020-06-21 13:58:52
 * @Version: v1.0
 * @Description: 小说章节控制器
 * @Author: yuanrise
 * @Email: 1649951967@qq.com
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
@Slf4j
@RestController
@RequestMapping(GemModules.Extend.PATH_PRE+"/novel/detailChapter")
public class DetailChapterController extends BaseController {

    private static final String moduleName = "小说章节";

    @Autowired
    private DetailChapterService detailChapterService;

    /**
     * 获取列表分页
     * @return
     */
    @GetMapping("/page")
    @RequiresPermissions("detailChapter:page")
    public BaseResultData page(PageInfo pageInfo, DetailChapterVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        Page page = detailChapterService.page(setOrderPage(pageInfo),queryWrapper);
        return BaseResultData.SUCCESS(page.getRecords(),page.getTotal());
    }
    /**
     * 获取列表
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("detailChapter:list")
    public BaseResultData list(DetailChapterVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        List list = detailChapterService.list(queryWrapper);
        return BaseResultData.SUCCESS(list);
    }

    /**
     * 添加
     * @return
     */
    @Log(type = OperateType.ALTER,value = "保存"+moduleName)
    @PostMapping("/save")
    @RequiresPermissions("detailChapter:save")
    public BaseResultData save(@RequestBody DetailChapterVo vo) {
        GemValidate(vo, SaveValidator.class);
        DetailChapter entity = GemBeanUtils.copyProperties(vo, DetailChapter.class);
        if(!detailChapterService.save(entity)){
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
    @RequiresPermissions("detailChapter:delete")
    public BaseResultData delete(Long id,String ids) {
        if(id!=null) detailChapterService.removeById(id);
        if(StringUtils.isNotBlank(ids)){
            List<Long> listIds = Arrays.asList(ids.split(",")).stream().map(s ->Long.parseLong(s.trim())).collect(Collectors.toList());
            if(listIds!=null && !listIds.isEmpty()){
                    detailChapterService.removeByIds(listIds);
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
    @RequiresPermissions("detailChapter:update")
    public BaseResultData update(@RequestBody DetailChapterVo vo) {
        GemValidate(vo, UpdateValidator.class);
        DetailChapter entity = GemBeanUtils.copyProperties(vo, DetailChapter.class);
        if(!detailChapterService.updateById(entity)){
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
    @RequiresPermissions("detailChapter:info")
    public BaseResultData info(Long id) {
        DetailChapter info = detailChapterService.getById(id);
        return BaseResultData.SUCCESS(info);
    }

}