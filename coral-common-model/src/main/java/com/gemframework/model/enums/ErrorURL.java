/**
 * 严肃声明：
 *  版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局知识产权以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */
package com.gemframework.model.enums;

import lombok.Getter;

/**
 * @Title: ResultCode.java
 * @Package: com.gemframework.enum
 * @Date: 2019/11/27 22:28
 * @Version: v1.0
 * @Description: 错误码枚举类

 * @Author: zhangysh
 * @Copyright: Copyright (c) 2019 GemStudio
 * @Company: www.gemframework.com
 */
@Getter
public enum ErrorURL {

    SUCCESS("succ","成功页面"),
    ERROR("error","错误页面"),
    INDEX("index","首页"),
    LOGIN("login","登录页"),
    EXCEPTION("500","服务异常"),
    NOT_FOUND("404","找不到页面"),
    REFUSE("403","拒绝访问"),

    UNKNOWN("404","页面不存在"),
    ;


    private String url;
    private String msg;

    ErrorURL(String url, String msg){
        this.url = url;
        this.msg = msg;
    }
}
