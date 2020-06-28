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

import com.gemframework.model.enums.OssStorageType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;


@Data
@Builder
public class OssObject {

	//对象（文件）名称
	private String name;
	//存储桶名称
	private String bucketName;
	//对象（文件）ContentType格式
	private String contentType;
	//对象（文件）路径
	private String fileUrl;
	//对象（文件）体积
	private String fileSize;
	//存储路径（key）
	private String keyPath;
	//存储类型
	private OssStorageType storageType;
	//最后编辑时间
	private Date lastModified;
}
