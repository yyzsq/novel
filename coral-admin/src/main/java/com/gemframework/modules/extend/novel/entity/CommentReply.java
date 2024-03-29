/**
 * 严肃声明：
 * 版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局知识产权以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */
package com.gemframework.modules.extend.novel.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gemframework.model.common.BaseEntityPo;
import lombok.Data;

/**
 * @Title: CommentReply
 * @Date: 2020-06-21 13:58:51
 * @Version: v1.0
 * @Description: 回复评论信息实体
 * @Author: yuanrise
 * @Email: 1649951967@qq.com
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */

@TableName("rise_comment_reply")
@Data
public class CommentReply extends BaseEntityPo {

    /**
     * 		 * 关联评论编号
     *         */
    private String commentId;
    /**
     * 		 * 关联用户编号
     *         */
    private String userId;
    /**
     * 		 * 内容
     *         */
    private String content;
    /**
     * 		 * 点赞数
     *         */
    private Integer zan;
    /**
     * 		 * 状态
     *         */
    private String status;
    /**
     * 		 * 请补充注释
     *         */
    private String other1;
    /**
     * 		 * 请补充注释
     *         */
    private String other2;

}

