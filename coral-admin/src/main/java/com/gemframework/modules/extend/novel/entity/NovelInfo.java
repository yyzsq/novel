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
 * @Title: NovelInfo
 * @Date: 2020-06-21 13:56:54
 * @Version: v1.0
 * @Description: 小说信息实体
 * @Author: yuanrise
 * @Email: 1649951967@qq.com
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */

@TableName("rise_novel_info")
@Data
public class NovelInfo extends BaseEntityPo {

    /**
     * 		 * 小说标题
     *         */
    private String xsTitle;
    /**
     * 		 * 一级分类
     *         */
    private String type1;
    /**
     * 		 * 二级分类
     *         */
    private String type2;
    /**
     * 		 * 关联作者编号
     *         */
    private String authorId;
    /**
     * 		 * 主角名称
     *         */
    private String leadName;
    /**
     * 		 * 字数
     *         */
    private String charNumber;
    /**
     * 		 * 阅读数
     *         */
    private String yueduCount;
    /**
     * 		 * 主图
     *         */
    private String imgUrl;
    /**
     * 		 * 评分
     *         */
    private String score;
    /**
     * 		 * 人气值
     *         */
    private String popularity;
    /**
     * 		 * 简介
     *         */
    private String synopsis;
    /**
     * 		 * 最新章节
     *         */
    private String newChapter;

    private Date newChapterTime;
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

