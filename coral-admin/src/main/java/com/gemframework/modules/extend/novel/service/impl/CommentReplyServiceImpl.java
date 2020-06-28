
package com.gemframework.modules.extend.novel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gemframework.modules.extend.novel.mapper.CommentReplyMapper;
import com.gemframework.modules.extend.novel.entity.CommentReply;
import com.gemframework.modules.extend.novel.service.CommentReplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Title: CommentReplyServiceImpl
 * @Date: 2020-06-21 13:58:51
 * @Version: v1.0
 * @Description: 回复评论信息服务实现类
 * @Author: yuanrise
 * @Email: 1649951967@qq.com
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
@Slf4j
@Service
public class CommentReplyServiceImpl extends ServiceImpl<CommentReplyMapper, CommentReply> implements CommentReplyService {

}