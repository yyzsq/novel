/**
 * 严肃声明：
 *  版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局知识产权以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */
package com.gemframework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gemframework.model.entity.po.UserRoles;
import com.gemframework.model.entity.vo.UserRolesVo;

import java.util.List;

public interface UserRolesService extends IService<UserRoles> {

    /**
     * 通过用户ID查找关联关系
     * @param userId
     * @return
     */
    List<UserRoles> findByUserId(Long userId);

    /**
     * 通过用户ID查找关联关系ID
     * @param userId
     * @return
     */
    List<Long> findIdsByUserId(Long userId);


    /**
     * 通过用户ID删除
     * @param userId
     * @return
     */
    boolean deleteByUserId(Long userId);


    /**
     * 保存用户的角色关联关系
     * @return
     */
    boolean save(UserRolesVo vo);

}