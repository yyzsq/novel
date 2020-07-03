/**
 * 严肃声明：
 *  版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
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
 * @Title: NovelWrite
 * @Date: 2020-07-03 10:39:11
 * @Version: v1.0
 * @Description: 作家专栏实体
 * @Author: yuanrise
 * @Email: 1649951967@qq.com
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */

@TableName("rise_novel_write")
@Data
public class NovelWrite extends BaseEntityPo {

										/**
		 * 		 * 标题
		 * 		 */
							private String title;
										/**
		 * 		 * 内容
		 * 		 */
							private String content;
										/**
		 * 		 * 图片
		 * 		 */
							private String picture;
										/**
		 * 		 * 状态：0启用；1禁用
		 * 		 */
							private Integer status;
																															
}

