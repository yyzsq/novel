/**
 * 严肃声明：
 *  版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局知识产权以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */
package com.gemframework.modules.extend.author.entity;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gemframework.model.common.BaseEntityPo;
import lombok.Data;

/**
 * @Title: NovelBookrack
 * @Date: 2020-07-06 14:29:04
 * @Version: v1.0
 * @Description: 我的书架实体
 * @Author: yuanrise
 * @Email: 1649951967@qq.com
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */

@TableName("rise_novel_bookrack")
@Data
public class NovelBookrack extends BaseEntityPo {

										/**
		 * 		 * 作品名
		 * 		 */
							private String novelName;
										/**
		 * 		 * 最新更新章节
		 * 		 */
							private String zxSection;
										/**
		 * 		 * 作者
		 * 		 */
							private String authorName;
										/**
		 * 		 * 类别
		 * 		 */
							private String category;
										/**
		 * 		 * 更新状态：0更新；1无
		 * 		 */
							private Integer updateStatus;
										/**
		 * 		 * 状态：0启用；1禁用
		 * 		 */
							private Integer status;
																															
}

