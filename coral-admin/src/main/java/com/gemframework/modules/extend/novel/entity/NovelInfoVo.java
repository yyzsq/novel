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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gemframework.model.common.BaseEntityVo;
import lombok.Data;

/**
 * @Title: NovelInfoVo
 * @Date: 2020-06-21 13:56:54
 * @Version: v1.0
 * @Description: 小说信息VO对象
 * @Author: yuanrise
 * @Email: 1649951967@qq.com
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
@Data
public class NovelInfoVo extends BaseEntityVo {

    /**
     * 			 * 小说标题
     *             */

    private String xsTitle;

    /**
     * 			 * 一级分类
     *             */

    private String type1;

    private String type1Name;

    /**
     * 			 * 二级分类
     *             */

    private String type2;

    private String type2Name;

    /**
     * 			 * 关联作者编号
     *             */

    private String authorId;

    private String authorName;

    /**
     * 			 * 主角名称
     *             */

    private String leadName;

    /**
     * 			 * 字数
     *             */

    private String charNumber;

    private Date newChapterTime;
    /**
     * 			 * 阅读数
     *             */

    private String yueduCount;

    /**
     * 			 * 主图
     *             */

    private String imgUrl;

    /**
     * 			 * 评分
     *             */

    private String score;

    /**
     * 			 * 人气值
     *             */

    private String popularity;

    /**
     * 			 * 简介
     *             */

    private String synopsis;

    /**
     * 			 * 最新章节
     *             */

    private String newChapter;

    /**
     * 			 * 状态
     *             */

    private String status;

    /**
     * 			 * 请补充注释
     *             */

    private String other1;

    /**
     * 			 * 请补充注释
     *             */

    private String other2;


}
