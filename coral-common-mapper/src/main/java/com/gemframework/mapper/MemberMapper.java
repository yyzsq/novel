
package com.gemframework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gemframework.model.entity.po.Demo;
import com.gemframework.model.entity.po.Member;
import org.springframework.stereotype.Repository;

/**
 * @Title: DemoMapper
 * @Package: com.gemframework.mapper
 * @Date: 2020-03-09 15:31:33
 * @Version: v1.0
 * @Description: Mapper接口
 * @Author: nine QQ 769990999
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */

@Repository
public interface MemberMapper extends BaseMapper<Member> {

}