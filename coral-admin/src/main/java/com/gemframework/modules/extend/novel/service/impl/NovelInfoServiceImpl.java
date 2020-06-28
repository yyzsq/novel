
package com.gemframework.modules.extend.novel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gemframework.modules.extend.novel.mapper.NovelInfoMapper;
import com.gemframework.modules.extend.novel.entity.NovelInfo;
import com.gemframework.modules.extend.novel.service.NovelInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Title: NovelInfoServiceImpl
 * @Date: 2020-06-21 13:56:54
 * @Version: v1.0
 * @Description: 小说信息服务实现类
 * @Author: yuanrise
 * @Email: 1649951967@qq.com
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
@Slf4j
@Service
public class NovelInfoServiceImpl extends ServiceImpl<NovelInfoMapper, NovelInfo> implements NovelInfoService {

}