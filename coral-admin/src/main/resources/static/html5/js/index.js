$(function () {


    // 小头悬浮功能
    var $fixedHeader = $('.js-sheader-fixed'),
        $xfSide = $('.xf-side'),
        $xfFhdb = $('.xf-fhdb'),
        $window = $(window);
    hideShow();
    $window.scroll(function () {
        hideShow();
    });

    function hideShow() {
        if ($window.scrollTop() >= 450) {
            $fixedHeader.fadeIn(500);
            $xfSide.css({'top': 128});
            $xfFhdb.css({'display': 'block'});
        } else {
            $fixedHeader.fadeOut(500);
            $xfSide.css({'top': 321});
            $xfFhdb.css({'display': 'none'});
        }
    }


    /*返回顶部效果*/

    $('.xf-fhdb').click(function (e) {
        $('html,body').animate({scrollTop: 0}, 500)
    });


    // 侧边栏
    $(".xf-lxbj").hover(function () {
        $('.xf-menu-tc').toggle();
    });

    $(".ewm-show").mouseleave(function () {
        $('.ewm-show').hide();
    });




    $(".sk-tab-left li").click(function(){
        $(this).addClass('on').siblings().removeClass('on');
    });


    $(".sk-tab-right a").click(function(){
        $(this).addClass('on').siblings().removeClass('on');
        var index = $(this).index();
        $('.sk-cont').children().eq(index).show().siblings().hide();
    });


    $(".sk-gjc-item-cont>a:not(:first)").click(function(){
        $(this).addClass('on').siblings().removeClass('on').parents('.sk-gjc-item').siblings().find('.sk-gjc-item-cont a').removeClass('on');
        $(this).parents('.sk-gjc-item').find('.sk-gjc-ej-item').show().parents('.sk-gjc-item').siblings().find('.sk-gjc-ej-item').hide();
    });

    $(".sk-gjc-item-cont a:eq(0)").click(function(){
        $(this).addClass('on').siblings().removeClass('on').parents('.sk-gjc-item').siblings().find('.sk-gjc-item-cont a').removeClass('on');
        $('.sk-gjc-ej-item').hide();
    });



    // 书详情页
    $(".ewm-qb").mouseenter(function () {
        $('.ewm-show').show();
    });

    $(".ewm-show").mouseleave(function () {
        $('.ewm-show').hide();
    });

    $(".book-pl-info span,.spqq-tips em").click(function(){
        $(this).toggleClass('on');
    });



    // 阅读页面
    $(".mulu-cont li").click(function(){
        $(this).addClass('on').siblings().removeClass('on');
    });


    $(".read-mulu").click(function(){

        $(this).toggleClass('on').siblings().removeClass('on');
        $('.mulu-box').toggle();
        $('.setting-box').hide();
    });

    $(".mulu-close").click(function(){
        $('.read-mulu').removeClass('on');
        $('.mulu-box').toggle();
    });


    $(".read-setting").click(function(){
        $(this).toggleClass('on').siblings().removeClass('on');
        $('.setting-box').toggle();
        $('.mulu-box').hide();
    });

    $(".setting-close").click(function(){
        $('.read-setting').removeClass('on');
        $('.setting-box').toggle();
    });


    $(".setting-pf-choose i").click(function(){
        $(this).addClass('on').siblings().removeClass('on');
    });


    // 投票弹窗
    $("#tp-btn").click(function(){
        $(".toupiao-tc").toggle();
        $(".toupiao-cont").toggle();
    })

    $(".tp-close").click(function(){
        $(".toupiao-tc").toggle();
        $(".toupiao-cont").toggle();
    })


    $(".ds-tc-cont-je li").click(function(){
        $(this).addClass('on').siblings().removeClass('on');
    });




    // 打赏弹窗
    $("#ds-btn").click(function(){
        $(".ds-tc").toggle();
        $(".ds-tc-cont").toggle();
    })

    $(".ds-close").click(function(){
        $(".ds-tc").toggle();
        $(".ds-tc-cont").toggle();
    })


    $(".book-pl-title a,.reply-btn,.zj-dt-btn").click(function(){
        $(".pl-tc").toggle();
        $(".pl-tc-cont").toggle();
    })


    /*频道左侧3D图切换*/
    var $swiper3dCurItem = '', _swiper3dIndex = '', $swiper3dCurTxt = '';
    $('.js-swiper-3d').waterwheelCarousel({
        flankingItems: 2,
        separation: 55,
        sizeMultiplier: .7,
        autoPlay: 3000,
        movedToCenter: function (item) {
            $swiper3dCurItem = $(item.context);
            _swiper3dIndex = $swiper3dCurItem.attr('index');
            $swiper3dCurTxt = $swiper3dCurItem.parents('.js-swiper-3d').siblings('.js-swiper-3d-txt');
            $swiper3dCurTxt.css('display', 'none');
            $swiper3dCurTxt.eq(_swiper3dIndex).css('display', 'block');
        }
    });

    var nanList = new Array();
    var nvList = new Array();

    //获取男女生分类
    $.ajax({
        url: "/extend/html5/typelist",
        dataType: "json",
        async: false,
        type: "get",
        success: function (res) {
            var nanInfo = "";
            var nvInfo = "";
            var nanIndex = 0;
            var nvIndex = 0;
            for (var i = 0; i < res.data.length; i++) {
                var info = res.data[i];
                if (info.type == "0" && info.fuId == "0") {
                    nvList.push(info.id);
                } else if (info.type == "1" && info.fuId == "0") {
                    nanList.push(info.id);
                }
            }
            for (var i = 0; i < res.data.length; i++) {
                if (nanIndex == 7) {
                    break;
                }
                var info = res.data[i];
                if (info.type == "0" && info.fuId == "0") {
                    nvInfo += "<a href=\"javascript:;\">" + info.typeName + "</a>";
                    nvIndex += 1;
                }
            }
            $(".nv-type").html(nvInfo);
            for (var i = 0; i < res.data.length; i++) {
                var info = res.data[i];
                if (nvIndex == 7) {
                    break;
                }
                if (info.type == "1" && info.fuId == "0") {
                    nanInfo += "<a href=\"javascript:;\">" + info.typeName + "</a>";
                    nanIndex += 1;
                }
            }
            $(".nan-type").html(nanInfo);
        }
    });

    $.ajax({
        url: "/extend/html5/novepage",
        dataType: "json",
        data: {"page": 1, "limit": 7, "sort": "create_time", "order": "desc"},
        type: "get",
        success: function (res) {
            var htmlInfo = "";
            for (var i = 0; i < res.data.length; i++) {
                var info = res.data[i];
                htmlInfo += "<dd>\n" +
                    "                <div class=\"new-book-pic clearfix\">\n" +
                    "                    <a href=\"javascript:;\">\n" +
                    "                        <img src=\"" + info.imgUrl + "\" />\n" +
                    "                    </a>\n" +
                    "                </div>\n" +
                    "                <h4><a href=\"javascript:;\">" + info.xsTitle + "</a></h4>\n" +
                    "                <p><a href=\"javascript:;\">" + info.authorName + "</a></p>\n" +
                    "            </dd>";
            }
            $(".xsdd-info").html(htmlInfo);
        }
    });

    $.ajax({
        url: "/extend/html5/chapterpage",
        dataType: "json",
        data: {"page": 1, "limit": 15, "sort": "create_time", "order": "desc"},
        type: "get",
        success: function (res) {
            var htmlInfo = "";
            for (var i = 0; i < res.data.length; i++) {
                var info = res.data[i];
                htmlInfo += "<tr>\n" +
                    "                    <td class=\"cl-g\"><a href=\"javascript:;\">[" + info.typeName2 + "]</a></td>\n" +
                    "                    <td><a href=\"javascript:;\">" + info.xsName + "</a></td>\n" +
                    "                    <td><a href=\"javascript:;\">" + info.name + "</a></td>\n" +
                    "                    <td class=\"cl-g\"><a href=\"javascript:;\">" + info.userName + "</a></td>\n" +
                    "                    <td style=\" text-align: right;\"><span>" + info.createTimeStr + "<span></td>\n" +
                    "                </tr>";
            }
            $(".sy-zxgx-info tbody").html(htmlInfo);
        }
    });

    $.ajax({
        url: "/extend/html5/countchapter",
        dataType: "json",
        type: "get",
        success: function (res) {
            $(".count-info").html(res.data);
        }
    });

    var nvTypes = nvList.join(",");
    $.ajax({
        url: "/extend/html5/novepage",
        dataType: "json",
        data: {"page": 1, "limit": 11, "sort": "popularity", "order": "desc", "types": nvTypes},
        type: "get",
        success: function (res) {
            if (res.data != null && res.data.length != 0) {
                $(".nv-lunbo-1img").attr("src", res.data[0].imgUrl);
                $(".nv-lunbo-2img").attr("src", res.data[1].imgUrl);
                $(".nv-lunbo-3img").attr("src", res.data[2].imgUrl);

                $(".nv-lunbo-1title").text(res.data[0].xsTitle);
                $(".nv-lunbo-2title").text(res.data[1].xsTitle);
                $(".nv-lunbo-3title").text(res.data[2].xsTitle);

                $(".nv-lunbo-1author").text(res.data[0].authorName);
                $(".nv-lunbo-2author").text(res.data[1].authorName);
                $(".nv-lunbo-3author").text(res.data[2].authorName);

                $(".nv-lunbo-1renqi").text(res.data[0].popularity);
                $(".nv-lunbo-2renqi").text(res.data[1].popularity);
                $(".nv-lunbo-3renqi").text(res.data[2].popularity);

                $(".nv-lunbo-1jj").text(res.data[0].synopsis);
                $(".nv-lunbo-2jj").text(res.data[1].synopsis);
                $(".nv-lunbo-3jj").text(res.data[2].synopsis);
                var htmlInfo = "";
                for (var i = 3; i < 7; i++) {
                    var info = res.data[i];
                    if (info == null) {
                        continue;
                    }
                    htmlInfo += " <li>\n" +
                        "                        <div class=\"zblt-list-pic clearfix\">\n" +
                        "                            <a href=\"javascript:;\"><img src=\"" + info.imgUrl + "\"></a>\n" +
                        "                        </div>\n" +
                        "                        <h6><a href=\"javascript:;\">" + info.xsTitle + "</a></h6>\n" +
                        "                        <h5>" + info.synopsis + "</h5>\n" +
                        "                        <h4>\n" +
                        "                            <p>" + info.authorName + "</p>\n" +
                        "                            <span><em>"+info.popularity+"</em>人在追</span>\n" +
                        "                        </h4>\n" +
                        "                    </li>";
                }
                $(".nspd-sige").html(htmlInfo);
                var objHtml = "";
                for (var i = 7; i < 11; i++) {
                    var info = res.data[i];
                    if (info == null) {
                        continue;
                    }
                    objHtml += "<dd><a href=\"javascript:;\"><em>[" + info.type2Name + "]&nbsp;</em>" + info.xsTitle + "</a></dd>";
                }
                $(".nv-pd-ddinfo").html(objHtml);
            }
        }
    });

    var nanTypes = nanList.join(",");
    $.ajax({
        url: "/extend/html5/novepage",
        dataType: "json",
        data: {"page": 1, "limit": 14, "sort": "popularity", "order": "desc", "types": nanTypes},
        type: "get",
        success: function (res) {
            if (res.data != null && res.data.length != 0) {
                $(".nan-lunbo-1img").attr("src", res.data[0].imgUrl);
                $(".nan-lunbo-2img").attr("src", res.data[1].imgUrl);
                $(".nan-lunbo-3img").attr("src", res.data[2].imgUrl);

                $(".nan-lunbo-1title").text(res.data[0].xsTitle);
                $(".nan-lunbo-2title").text(res.data[1].xsTitle);
                $(".nan-lunbo-3title").text(res.data[2].xsTitle);

                $(".nan-lunbo-1author").text(res.data[0].authorName);
                $(".nan-lunbo-2author").text(res.data[1].authorName);
                $(".nan-lunbo-3author").text(res.data[2].authorName);


                $(".nan-lunbo-1renqi").text(res.data[0].popularity);
                $(".nan-lunbo-2renqi").text(res.data[1].popularity);
                $(".nan-lunbo-3renqi").text(res.data[2].popularity);

                $(".nan-lunbo-1jj").text(res.data[0].synopsis);
                $(".nan-lunbo-2jj").text(res.data[1].synopsis);
                $(".nan-lunbo-3jj").text(res.data[2].synopsis);

                $(".nan-yige-info").html("<div class=\"zy-show-pic\">\n" +
                    "                        <a href=\"javascript:;\">\n" +
                    "                            <img src=\"" + res.data[3].imgUrl + "\" />\n" +
                    "                        </a>\n" +
                    "                    </div>\n" +
                    "                    <div class=\"zy-show-info clearfix\">\n" +
                    "                        <h5><a href=\"javascript:;\">" + res.data[3].xsTitle + "</a></h5>\n" +
                    "                        <div class=\"zy-show-bq clearfix\">\n" +
                    "                            <i>" + res.data[3].type1Name + "</i>\n" +
                    "                            <i>" + res.data[3].type2Name + "</i>\n" +
                    "                        </div>\n" +
                    "                        <h4>" + res.data[3].synopsis + "</h4>\n" +
                    "                        <h3><a href=\"javascript:;\">" + res.data[3].authorName + "</a><span>"+res.data[3].popularity+"人在追</span></h3>\n" +
                    "                    </div>");
                var htmlInfo = "";
                for (var i = 4; i < 6; i++) {
                    if (res.data[i] == null) {
                        continue;
                    }
                    htmlInfo += "<div class=\"zy-show-item clearfix\">\n" +
                        "                        <h5><a href=\"javascript:;\"><em>[" + res.data[i].type2Name + "]&nbsp;</em>" + res.data[i].xsTitle + "</a></h5>\n" +
                        "                        <h4>" + res.data[3].synopsis + "</h4>\n" +
                        "                        <h3><a href=\"javascript:;\">" + res.data[3].authorName + "</a><span>"+res.data[i].popularity+"人在追</span></h3>\n" +
                        "                    </div>";
                }
                var objInfo = "";
                $(".nan-lainge-info").html(htmlInfo);
                for (var i = 7; i < 14; i++) {
                    if (res.data[i] == null) {
                        continue;
                    }
                    objInfo += "<dd><a href=\"javascript:;\"><em>[" + res.data[i].type2Name + "]&nbsp;</em>" + res.data[i].xsTitle + "</a></dd>";
                }
                $(".nan-bage-info").html(objInfo);
            }
        }
    });

    $.ajax({
        url: "/extend/html5/toplist",
        dataType: "json",
        data: {"type": "0"},
        type: "get",
        success: function (res) {
            var htmlInfo = "";
            for (var i = 0; i < res.data.length; i++) {
                var info = res.data[i]
                htmlInfo += "<li><a href=\"" + info.link + "\" target='_blank'><img src=\"" + info.picture + "\" /></a></li>"
            }
            $(".sy-lunbo-info").html(htmlInfo);
            $(".focus").flexslider({
                animation: "slider",
                slideDirection: "horizontal",
                slideshowSpeed: 4000, //展示时间间隔ms
                animationSpeed: 400, //滚动时间ms
                touch: true,//是否支持触屏滑动
                pauseOnAction: false,
                directionNav: true,  //是否显示左右控制按钮
                controlNav: true
            });
        }
    });

    $.ajax({
        url: "/extend/html5/toplist",
        dataType: "json",
        data: {"type": "1"},
        type: "get",
        success: function (res) {
            var htmlInfo = "";
            for (var i = 0; i < res.data.length; i++) {
                var info = res.data[i]
                htmlInfo += "<li><a href=\"" + info.link + "\"><i></i>" + info.title + "</a></li>";
            }
            $(".sy-dongtai-info").html(htmlInfo);
        }
    });
    $.ajax({
        url: "/extend/html5/toplist",
        dataType: "json",
        data: {"type": "2"},
        type: "get",
        success: function (res) {
            if (res.data.length > 0) {
                $(".sy-guanggw-info").html("<a href=\"" + res.data[0].link + "\"><img src=\"" + res.data[0].picture + "\" /></a>");
            }
        }
    });

    $(".sy-dt-time").html(getCurrentDate());


    function getCurrentDate() {

        var myDate = new Date();

        var year = myDate.getFullYear(); //年

        var month = myDate.getMonth() + 1; //月

        var day = myDate.getDate(); //日

        var days = myDate.getDay();

        switch (days) {

            case 1:

                days = '星期一';

                break;

            case 2:

                days = '星期二';

                break;

            case 3:

                days = '星期三';

                break;

            case 4:

                days = '星期四';

                break;

            case 5:

                days = '星期五';

                break;

            case 6:

                days = '星期六';

                break;

            case 0:

                days = '星期日';

                break;


        }

        var str = year + "年" + month + "月" + day + "日  " + days;

        return str;

    }

    $.ajax({
        url: "/extend/html5/novepage",
        dataType: "json",
        data: {"page": 1, "limit": 14, "sort": "popularity", "order": "desc", "status": 3},
        type: "get",
        success: function (res) {
            var htmlInfo = "";
            var xzbg = "";
            for (var i = 0; i < res.data.length; i++) {
                if (i == 0) {
                    xzbg = "bj-xh on";
                } else if (i < 3) {
                    xzbg = "bj-xh";
                }
                htmlInfo += "<li class='" + xzbg + "'>\n" +
                    "                    <div class=\"side-box-xh clearfix\">" + (i + 1) + "</div>\n" +
                    "                    <div class=\"side-box-cont clearfix\">\n" +
                    "                        <div class=\"side-box-pic clearfix\">\n" +
                    "                            <a href=\"javascript:;\"><img src=\"" + res.data[i].imgUrl + "\" /></a>\n" +
                    "                        </div>\n" +
                    "                        <div class=\"side-box-info clearfix\">\n" +
                    "                            <h6><a href=\"javascript:;\">" + res.data[i].xsTitle + "</a></h6>\n" +
                    "                            <h5>" + res.data[i].authorName + "</h5>\n" +
                    "                            <h4>"+res.data[i].popularity+"人气</h4>\n" +
                    "                        </div>\n" +
                    "                    </div>\n" +
                    "                </li>";

            }

            $(".wb-ydb-info").html(htmlInfo);
            $(".side-box li").hover(function () {
                $(this).addClass('on').siblings().removeClass('on');
            });
        }
    })


    $.ajax({
        url: "/extend/html5/contactlist",
        dataType: "json",
        type: "get",
        success: function (res) {
            var nvHtml = "";
            var nanHtml = "";
            for (var i = 0; i < res.data.length; i++) {
                var info = res.data[i];
                if (info.channel == "0") {
                    nvHtml += "<h4>\n" +
                        "        <a href=\"https://wpa.qq.com/msgrd?v=3&amp;uin="+info.qq+"&amp;site=qq&amp;menu=yes\">\n" +
                        "                 <i></i>"+info.editorName+"\n" +
                        "         </a> "+info.qq+"\n" +
                        "         </h4>";
                } else {
                    nanHtml += "<h4>\n" +
                        "        <a href=\"https://wpa.qq.com/msgrd?v=3&amp;uin="+info.qq+"&amp;site=qq&amp;menu=yes\">\n" +
                        "                 <i></i>"+info.editorName+"\n" +
                        "         </a> "+info.qq+"\n" +
                        "         </h4>";
                }

            }

            $(".nv-bianji-info").html(nvHtml);
            $(".nan-bianji-info").html(nanHtml);
        }
    })

    //获取搜索框下方广告位
    $.ajax({
        url: "/extend/html5/toplist",
        dataType: "json",
        data: {"type": "7"},
        type: "get",
        success: function (res) {
            var htmlInfo = "";
            for (var i = 0; i < res.data.length; i++) {
                var info = res.data[i]
                htmlInfo += "<a href=\""+info.link+"\" class='"+(i==0?"cl-r":"")+"' >"+info.title+"</a>";
            }
            $(".sous-ggao-group").html(htmlInfo);
        }
    });

    //首页人气榜
    $.ajax({
        url:"/extend/html5/novepage",
        dataType:"json",
        data:{"page":1,"limit":10,"sort":"popularity","order":"desc","status":"1,3"},
        async:false,
        type:"get",
        success:function (res) {
            var htmlInfo="";
            for(var i=0;i<res.data.length;i++){
                var info=res.data[i];
                htmlInfo+="<li class=\""+(i<3?"bj-xh":"")+"  "+(i==0?"on":"")+"\">\n" +
                    "                    <div class=\"side-box-xh clearfix\">"+(i+1)+"</div>\n" +
                    "                    <div class=\"side-box-cont clearfix\">\n" +
                    "                        <div class=\"side-box-pic clearfix\">\n" +
                    "                            <a href=\"javascript:;\"><img src=\""+info.imgUrl+"\" /></a>\n" +
                    "                        </div>\n" +
                    "                        <div class=\"side-box-info clearfix\">\n" +
                    "                            <h6><a href=\"javascript:;\">"+info.xsTitle+"</a></h6>\n" +
                    "                            <h5>"+info.authorName+"</h5>\n" +
                    "                            <h4>"+info.popularity+"万人气</h4>\n" +
                    "                        </div>\n" +
                    "                    </div>\n" +
                    "                </li>"
            }

            $(".sy-renqib-group ul").html(htmlInfo);
        }
    })
})

