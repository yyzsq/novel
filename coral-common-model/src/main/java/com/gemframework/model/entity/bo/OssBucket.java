/**
 * 严肃声明：
 *  版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局知识产权以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */
package com.gemframework.model.entity.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gemframework.model.enums.OssStorageType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;


@Data
@Builder
public class OssBucket{
	//名称
	private String name;
	private String location;
	private OssStorageType storageType;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createDate;
}
