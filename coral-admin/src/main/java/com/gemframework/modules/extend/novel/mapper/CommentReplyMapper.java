
package com.gemframework.modules.extend.novel.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gemframework.modules.extend.novel.entity.CommentReply;
import org.springframework.stereotype.Repository;

/**
 * @Title: CommentReplyMapper
 * @Date: 2020-06-21 13:58:51
 * @Version: v1.0
 * @Description: 回复评论信息持久层
 * @Author: yuanrise
 * @Email: 1649951967@qq.com
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
@Repository
public interface CommentReplyMapper extends BaseMapper<CommentReply> {

}