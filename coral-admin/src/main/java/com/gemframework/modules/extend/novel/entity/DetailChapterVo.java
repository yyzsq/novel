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
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gemframework.model.common.BaseEntityVo;
import lombok.Data;

/**
 * @Title: DetailChapterVo
 * @Date: 2020-06-21 13:58:52
 * @Version: v1.0
 * @Description: 小说章节VO对象
 * @Author: yuanrise
 * @Email: 1649951967@qq.com
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
@Data
public class DetailChapterVo extends BaseEntityVo {

    /**
     * 			 * 关联小说编号
     *             */

    private String xsId;

    /**
     * 			 * 章节名称
     *             */

    private String name;

    /**
     * 			 * 字数
     *             */

    private String zs;

    /**
     * 			 * 文章地址
     *             */

    private String url;

    /**
     * 			 * 作者编号
     *             */

    private String zzId;

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

    private String typeName1;

    private String typeName2;

    private String userName;

    private String xsName;

    private String createTimeStr;

    private Integer type2;

    private String types;

}
