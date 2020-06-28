
package com.gemframework.modules.extend.novel.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gemframework.modules.extend.novel.entity.NovelComment;
import org.springframework.stereotype.Repository;

/**
 * @Title: NovelCommentMapper
 * @Date: 2020-06-21 13:58:51
 * @Version: v1.0
 * @Description: 评论数持久层
 * @Author: yuanrise
 * @Email: 1649951967@qq.com
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
@Repository
public interface NovelCommentMapper extends BaseMapper<NovelComment> {

}