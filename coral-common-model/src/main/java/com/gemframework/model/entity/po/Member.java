/**
 * 严肃声明：
 *  版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局知识产权以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */
package com.gemframework.model.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gemframework.model.common.BaseEntityPo;
import lombok.Data;

import java.util.Date;

/**
 * @Title: Member
 * @Package: com.gemframework.model.entity.po
 * @Date: 2020-04-11 13:31:43
 * @Version: v1.0
 * @Description: 会员表
 * @Author: nine QQ 769990999
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
@TableName("gem_member")
@Data
public class Member extends BaseEntityPo {

    private String account;
    private String password;
    private String salt;
    private String avatarUrl;
    private String nickname;
    private String phone;
    private String email;
    private String wechat;
    // 博客
    private String blog;
    //用户状态 0 正常 1禁用
    private Integer status;
    //注册渠道 1 手动添加 2 自助注册 3 oauth
    private Integer channel;
    //是否第三方登录 0 否 1是
    private Integer isOauth;
    //第三方平台 1 gitee 2github 3 微信 4 QQ 5 微博 6支付宝
    private Integer thirdParty;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastLoginTime;
}