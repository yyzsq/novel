
package com.gemframework.modules.extend.novel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gemframework.modules.extend.novel.mapper.NovelCommentMapper;
import com.gemframework.modules.extend.novel.entity.NovelComment;
import com.gemframework.modules.extend.novel.service.NovelCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Title: NovelCommentServiceImpl
 * @Date: 2020-06-21 13:58:51
 * @Version: v1.0
 * @Description: 评论数服务实现类
 * @Author: yuanrise
 * @Email: 1649951967@qq.com
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
@Slf4j
@Service
public class NovelCommentServiceImpl extends ServiceImpl<NovelCommentMapper, NovelComment> implements NovelCommentService {

}