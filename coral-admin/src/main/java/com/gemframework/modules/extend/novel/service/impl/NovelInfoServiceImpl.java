
package com.gemframework.modules.extend.novel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gemframework.modules.extend.novel.entity.NovelRanked;
import com.gemframework.modules.extend.novel.mapper.NovelInfoMapper;
import com.gemframework.modules.extend.novel.entity.NovelInfo;
import com.gemframework.modules.extend.novel.service.NovelInfoService;
import com.gemframework.modules.extend.novel.service.NovelRankedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Title: NovelInfoServiceImpl
 * @Date: 2020-06-21 13:56:54
 * @Version: v1.0
 * @Description: 小说信息服务实现类
 * @Author: yuanrise
 * @Email: 1649951967@qq.com
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
@Slf4j
@Service
public class NovelInfoServiceImpl extends ServiceImpl<NovelInfoMapper, NovelInfo> implements NovelInfoService {

    @Autowired
    private NovelRankedService novelRankedService;

    @Scheduled(cron = "0 0 1 * * ?")
    //@Scheduled(cron = "0 */1 * * * ?")
    public void cunChuNoveInfo() {
        //清空榜单数据
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("deleted", "0");
        novelRankedService.remove(queryWrapper);

        //获取人气榜前10
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("deleted", "0");
        wrapper.in("status", "1","3");
        wrapper.orderByDesc("popularity");
        List<NovelInfo> rqList = this.list(wrapper);
        saveRanked(0,rqList);


        //获取30天内新书榜前10
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        QueryWrapper xswrapper = new QueryWrapper();
        xswrapper.eq("deleted", "0");
        wrapper.in("status", "1","3");
        xswrapper.between("create_time",df.format(stepMonth(new Date(),-1)) , df.format(new Date()));
        xswrapper.orderByDesc("popularity");
        List<NovelInfo> xsList = this.list(xswrapper);
        saveRanked(1,xsList);

        //获取完结榜前10
        QueryWrapper wjwrapper = new QueryWrapper();
        wjwrapper.eq("deleted", "0");
        wjwrapper.eq("status", "3");
        wjwrapper.between("create_time",df.format(stepMonth(new Date(),-1)) , df.format(new Date()));
        wjwrapper.orderByDesc("popularity");
        List<NovelInfo> wjList = this.list(wjwrapper);
        saveRanked(2,wjList);


    }

    public void saveRanked(Integer type,List<NovelInfo> list){
        int nan = 0;
        int nv = 0;
        NovelRanked ranked = null;
        for (NovelInfo info : list) {

            ranked = new NovelRanked();
            ranked.setNovelId(info.getId() + "");
            ranked.setRankedMiddle(type);
            if (info.getChannelType() == 0 && nv < 10) {
                ranked.setRankedType(0);
                ranked.setCreateTime(new Date());
                novelRankedService.save(ranked);
                nv += 1;
            } else if (info.getChannelType() == 1 && nan < 10) {
                ranked.setRankedType(1);
                ranked.setCreateTime(new Date());
                novelRankedService.save(ranked);
                nan += 1;
            } else if(nan==10&&nv==10){
                break;
            }
        }
    }

    /**
     *      * 在给定的日期加上或减去指定月份后的日期
     *      *
     *      * @param sourceDate 原始时间
     *      * @param month      要调整的月份，向前为负数，向后为正数
     *      * @return
     *      
     */
    public static Date stepMonth(Date sourceDate, int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(sourceDate);
        c.add(Calendar.MONTH, month);
        return c.getTime();
    }

}