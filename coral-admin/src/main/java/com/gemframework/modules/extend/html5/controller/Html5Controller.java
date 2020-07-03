package com.gemframework.modules.extend.html5.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gemframework.common.constant.GemModules;
import com.gemframework.common.utils.GemBeanUtils;
import com.gemframework.model.common.BaseResultData;
import com.gemframework.model.common.PageInfo;
import com.gemframework.model.entity.po.User;
import com.gemframework.modules.extend.novel.entity.*;
import com.gemframework.modules.extend.novel.service.*;
import com.gemframework.modules.extend.set.entity.NovelContactVo;
import com.gemframework.modules.extend.set.service.NovelContactService;
import com.gemframework.modules.perkit.BaseController;
import com.gemframework.service.UserService;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.soap.Detail;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(GemModules.Extend.PATH_PRE+"/html5")
public class Html5Controller extends BaseController {
    @Autowired
    private NovelTypeService novelTypeService;
    @Autowired
    private NovelInfoService novelInfoService;
    @Qualifier("userServiceImpl")
    @Autowired
    private UserService userService;
    @Autowired
    private DetailChapterService detailChapterService;
    @Autowired
    private NovelTopService novelTopService;
    @Autowired
    private NovelContactService novelContactService;
    @Autowired
    private NovelRankedService novelRankedService;
    @Autowired
    private NovelWriteService novelWriteService;
    /**
     * 获取类型
     * @param vo
     * @return
     */
    @GetMapping("/typelist")
    public BaseResultData list(NovelTypeVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        queryWrapper.orderByAsc("id");
        List<NovelType> list = novelTypeService.list(queryWrapper);
        return BaseResultData.SUCCESS(list);
    }



    /**
     * 获取小说
     * @param pageInfo
     * @param vo
     * @return
     */
    @GetMapping("/novepage")
    public BaseResultData novepage(PageInfo pageInfo, NovelInfoVo vo) {
        String status=vo.getStatus();
        vo.setStatus(null);
        String[] typelist=null;
        if(StringUtils.isNotBlank(vo.getTypes())){
            typelist=vo.getTypes().split(",");
            vo.setTypes(null);
        }
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        if(typelist!=null){
            queryWrapper.in("type1",typelist);
        }
        if(StringUtils.isNotBlank(status)){
            queryWrapper.in("status",status.split(","));
        }
        Page page = novelInfoService.page(setOrderPage(pageInfo),queryWrapper);
        List list=new ArrayList();
        for(Object obj:page.getRecords()){
            NovelInfoVo infoVo= GemBeanUtils.copyProperties((NovelInfo)obj,NovelInfoVo.class);
            NovelType novelType1=novelTypeService.getById(infoVo.getType1());
            infoVo.setType1Name(novelType1.getTypeName());
            NovelType novelType2=novelTypeService.getById(infoVo.getType2());
            infoVo.setType2Name(novelType2.getTypeName());
            User user=userService.getById(infoVo.getAuthorId());
            if(user!=null){
                infoVo.setAuthorName(user.getRealname());
            }
            list.add(infoVo);
        }
        return BaseResultData.SUCCESS(list,page.getTotal());
    }

    /**
     * 获取最新更新章节
     * @param pageInfo
     * @param vo
     * @return
     */
    @GetMapping("/chapterpage")
    public BaseResultData chapterpage(PageInfo pageInfo, DetailChapterVo vo) {
        String[] types=null;
        if(StringUtils.isNotBlank(vo.getTypes())){
            types=vo.getTypes().split(",");
            vo.setTypes(null);
        }
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        if(types!=null){
            queryWrapper.in("type2",types);
        }
        Page page = detailChapterService.page(setOrderPage(pageInfo),queryWrapper);
        List list=new ArrayList();
        for(Object obj:page.getRecords()){
            DetailChapterVo infoVo=GemBeanUtils.copyProperties((DetailChapter)obj,DetailChapterVo.class);
            NovelInfo novelInfo=novelInfoService.getById(infoVo.getXsId());
            infoVo.setXsName(novelInfo.getXsTitle());
            NovelType type1=novelTypeService.getById(novelInfo.getType1());
            infoVo.setTypeName1(type1.getTypeName());
            NovelType type2=novelTypeService.getById(novelInfo.getType2());
            infoVo.setTypeName2(type2.getTypeName());
            User user=userService.getById(infoVo.getZzId());
            if(user!=null){
                infoVo.setUserName(user.getRealname());
            }
            infoVo.setCreateTimeStr(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(infoVo.getCreateTime()));
            list.add(infoVo);
        }

        return BaseResultData.SUCCESS(list,page.getTotal());
    }

    //获取24小时更新小说数
    @GetMapping("/countchapter")
    public BaseResultData countchapter(DetailChapterVo vo) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.between("create_time",getOneDayBefore(new Date()),new Date());
        int count =detailChapterService.count(wrapper);
        return BaseResultData.SUCCESS(count);
    }
    //获取某时刻过去的24小时
    public static Date getOneDayBefore(Date dateEnd){
        Calendar date = Calendar.getInstance();
        date.setTime(dateEnd);
        date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
        return date.getTime();
    }

    //获取广告位
    @GetMapping("/toplist")
    public BaseResultData list(NovelTopVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        List list = novelTopService.list(queryWrapper);
        return BaseResultData.SUCCESS(list);
    }

    @GetMapping("/qqAuth")
    public String qqAuth(HttpServletRequest request) {
        try {
            String authorizeURL = new Oauth().getAuthorizeURL(request);
            log.info("authorizeURL:{}", authorizeURL);
            return "redirect:" + authorizeURL;
        } catch (Exception e) {
            return null;
        }
    }

    @RequestMapping("/qqLoginBack")
    public String qqLoginBack(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
        /*try {
            AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);
            if (accessTokenObj == null) {
                return null;
            }
            String accessToken = accessTokenObj.getAccessToken();
            if (StringUtils.isEmpty(accessToken)) {
                return null;
            }
            *//* 获取用户openid *//*
            OpenID openIDObj = new OpenID(accessToken);

            String openId = openIDObj.getUserOpenID();
            if (StringUtils.isEmpty(openId)) {
                return null;
            }
            BaseResponse<JSONObject> findByOpenId = qqAuthoriFeign.findByOpenId(openId);
            if (!isSuccess(findByOpenId)) {
                return null;
            }
            Integer resultCode = findByOpenId.getCode();
            *//* 如果使用openid没有查询到用户信息，则跳转到绑定用户信息页面 *//*
            if (resultCode.equals(Constants.HTTP_RES_CODE_NOTUSER_203)) {
                *//* 使用openid获取用户信息 *//*
                UserInfo qzoneUserInfo = new UserInfo(accessToken, openId);
                UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
                if (userInfoBean == null) {
                    return null;
                }
                String avatarURL100 = userInfoBean.getAvatar().getAvatarURL100();
                *//* 返回用户头像页面展示 *//*
                request.setAttribute("avatarURL100", avatarURL100);
                httpSession.setAttribute(WebConstants.LOGIN_QQ_OPENID, openId);
                return MB_QQ_QQLOGIN;
            }
            *//* 自动实现登陆 *//*
            JSONObject data = findByOpenId.getData();
            String token = data.getString("token");
            CookieUtils.setCookie(request, response, WebConstants.LOGIN_TOKEN_COOKIENAME, token);
            return REDIRECT_INDEX;
        } catch (Exception e) {
            return null;
        }*/
        return null;
    }

    //获取客服联系
    @GetMapping("/contactlist")
    public BaseResultData contactlist(NovelContactVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        List list = novelContactService.list(queryWrapper);
        return BaseResultData.SUCCESS(list);
    }

    /**
     * 书库小说搜索
     * @param pageInfo
     * @param vo
     * @return
     */
    @GetMapping("/noveInfopage")
    public BaseResultData noveInfopage(PageInfo pageInfo, NovelInfoVo vo) {
        String gxTime=vo.getGxTime();
        vo.setGxTime(null);
        String isWanJie=vo.getIsWanJie();
        vo.setIsWanJie(null);
        String orderBys=vo.getOrderBys();
        vo.setOrderBys(null);
        String charNumber=vo.getCharNumber();
        vo.setCharNumber(null);

        QueryWrapper queryWrapper = makeQueryMaps(vo);
        //更新时间
        if(StringUtils.isNotBlank(gxTime)){
            Calendar cal = Calendar.getInstance();
            Date time = cal.getTime();
            cal.add(Calendar.DAY_OF_MONTH,-Integer.parseInt(gxTime));
            cal.getTime();
            queryWrapper.lt("new_chapter_time",time);
        }
        //是否完结
        if(StringUtils.isNotBlank(isWanJie)){
            if(StringUtils.equals(isWanJie,"1")){
                queryWrapper.eq("status","3");
            }else if(StringUtils.equals(isWanJie,"0")){
                queryWrapper.notIn("status","3");
            }
        }
        //排序字段
        if(StringUtils.isNotBlank(orderBys)){
            queryWrapper.orderByDesc(orderBys);
        }
        //字数
        if(StringUtils.isNotBlank(charNumber)){
            String[] zs=charNumber.split(",");
            if(zs.length==2){
                queryWrapper.between("char_number",zs[0],zs[1]);
            }else{
                if(zs[0].equals("30")){
                    queryWrapper.gt("char_number",zs[0]);
                }else{
                    queryWrapper.lt("char_number",zs[0]);
                }
            }
        }

        Page page = novelInfoService.page(setOrderPage(pageInfo),queryWrapper);
        List list=new ArrayList();
        for(Object obj:page.getRecords()){
            NovelInfoVo infoVo= GemBeanUtils.copyProperties((NovelInfo)obj,NovelInfoVo.class);
            NovelType novelType1=novelTypeService.getById(infoVo.getType1());
            infoVo.setType1Name(novelType1.getTypeName());
            NovelType novelType2=novelTypeService.getById(infoVo.getType2());
            infoVo.setType2Name(novelType2.getTypeName());
            User user=userService.getById(infoVo.getAuthorId());
            if(user!=null){
                infoVo.setAuthorName(user.getRealname());
            }
            if(vo.getNewChapterTime()!=null)
            infoVo.setNewChapterTimeStr(new SimpleDateFormat("yyyy-MM-dd").format(vo.getNewChapterTime()));
            list.add(infoVo);
        }
        return BaseResultData.SUCCESS(list,page.getTotal());
    }

    /**
     * 获取类型
     * @param vo
     * @return
     */
    @GetMapping("/rankedlist")
    public BaseResultData rankedlist(NovelRankedVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        queryWrapper.orderByAsc("create_time");
        List<NovelRanked> list = novelRankedService.list(queryWrapper);
        List novelList=new ArrayList();

        for(NovelRanked ranked:list){
            NovelInfo info=novelInfoService.getById(ranked.getNovelId());
            if(info==null)
                continue;
            NovelInfoVo infoVo=GemBeanUtils.copyProperties(info,NovelInfoVo.class);
            User user=userService.getById(infoVo.getAuthorId());
            if(user!=null){
                infoVo.setAuthorName(user.getRealname());
            }
            if(infoVo!=null)
            novelList.add(infoVo);
        }
        return BaseResultData.SUCCESS(novelList);
    }

    @GetMapping("/writelist")
    public BaseResultData list(NovelWriteVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        List list = novelWriteService.list(queryWrapper);
        return BaseResultData.SUCCESS(list);
    }
}
