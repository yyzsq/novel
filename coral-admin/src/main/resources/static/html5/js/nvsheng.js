$(function () {
    $.ajax({
        url: "/extend/html5/toplist",
        dataType: "json",
        data: {"type": "3"},
        type: "get",
        success: function (res) {
            var htmlInfo="";
            for(var i=0;i<res.data.length;i++){
                var info=res.data[i]
                htmlInfo+="<li><a href=\""+info.link+"\" target='_blank'><img src=\""+info.picture+"\" /></a></li>"
            }
            $(".nv-lunbo-info").html(htmlInfo);
            $(".focus").flexslider({
                animation: "slider",
                slideDirection: "horizontal",
                slideshowSpeed: 4000, //展示时间间隔ms
                animationSpeed: 400, //滚动时间ms
                touch: true ,//是否支持触屏滑动
                pauseOnAction: false,
                directionNav: true,  //是否显示左右控制按钮
                controlNav: true
            });
        }
    });
    $.ajax({
        url: "/extend/html5/toplist",
        dataType: "json",
        data: {"type": "4"},
        type: "get",
        success: function (res) {
            if(res.data.length>0) {
                $(".nv-guanggw-info").html("<a href=\""+res.data[0].link+"\"><img src=\""+res.data[0].picture+"\" /></a>");
            }
        }
    });

    var nanList=new Array();
    var nvList=new Array();

    //获取男女生分类
    $.ajax({
        url: "/extend/html5/typelist",
        dataType: "json",
        async: false,
        type: "get",
        success: function (res) {
            for (var i = 0; i < res.data.length; i++) {
                var info = res.data[i];
                if (info.type == "0" ) {
                    nvList.push(info.id);
                } else if (info.type == "1") {
                    nanList.push(info.id);
                }
            }
        }
    })

    $.ajax({
        url:"/extend/html5/chapterpage",
        dataType:"json",
        data:{"page":1,"limit":15,"sort":"create_time","order":"desc","types":nvList.join(",")},
        type:"get",
        success:function (res) {
            var htmlInfo="";
            for(var i=0;i<res.data.length;i++){
                var info=res.data[i];
                htmlInfo+="<tr>\n" +
                    "                    <td class=\"cl-g\"><a href=\"javascript:;\">["+info.typeName2+"]</a></td>\n" +
                    "                    <td><a href=\"javascript:;\">"+info.xsName+"</a></td>\n" +
                    "                    <td><a href=\"javascript:;\">"+info.name+"</a></td>\n" +
                    "                    <td class=\"cl-g\"><a href=\"javascript:;\">"+info.userName+"</a></td>\n" +
                    "                    <td style=\" text-align: right;\"><span>"+info.createTimeStr+"<span></td>\n" +
                    "                </tr>";
            }
            $(".nv-zxgx-info tbody").html(htmlInfo);
        }
    });

    //现代言情
    $.ajax({
        url: "/extend/html5/novepage",
        dataType: "json",
        data: {"page": 1, "limit": 5, "sort": "popularity", "order": "desc", "type1": 2},
        type: "get",
        success: function (res) {
            var htmlInfo="";
            for(var i=0;i<res.data.length;i++){
                var info=res.data[i];
                if(i==0){
                    $(".nv-xdyq1-info").html("<div class=\"row-num-pic clearfix\">\n" +
                        "                <a href=\"javascript:;\">\n" +
                        "                    <img src=\""+info.imgUrl+"\" />\n" +
                        "                    <i>1</i>\n" +
                        "                </a>\n" +
                        "            </div>\n" +
                        "            <h6><a href=\"javascript:;\">"+info.xsTitle+"</a></h6>\n" +
                        "            <h5>"+info.synopsis+"</h5>\n" +
                        "            <h4>\n" +
                        "                <p><a href=\"javascript:;\">"+info.authorName+"</a></p>\n" +
                        "                <span>26.1万人气</span>\n" +
                        "            </h4>");
                }else{
                    htmlInfo+="<h5 class=\""+((i+1)>3?"":"red ")+"clearfix\">\n" +
                        "                <i>"+(i+1)+"</i>\n" +
                        "                <a href=\"javascript:;\" class=\"num-xm\">["+info.type2Name+"]</a>\n" +
                        "                <a href=\"javascript:;\" class=\"num-bt\">"+info.xsTitle+"</a>\n" +
                        "            </h5>"
                }
            }
            $(".nv-xdyq2-info").html(htmlInfo);
        }
    });

    //古代言情
    $.ajax({
        url: "/extend/html5/novepage",
        dataType: "json",
        data: {"page": 1, "limit": 5, "sort": "popularity", "order": "desc", "type1": 3},
        type: "get",
        success: function (res) {
            var htmlInfo="";
            for(var i=0;i<res.data.length;i++){
                var info=res.data[i];
                if(i==0){
                    $(".nv-gdyq1-info").html("<div class=\"row-num-pic clearfix\">\n" +
                        "                <a href=\"javascript:;\">\n" +
                        "                    <img src=\""+info.imgUrl+"\" />\n" +
                        "                    <i>1</i>\n" +
                        "                </a>\n" +
                        "            </div>\n" +
                        "            <h6><a href=\"javascript:;\">"+info.xsTitle+"</a></h6>\n" +
                        "            <h5>"+info.synopsis+"</h5>\n" +
                        "            <h4>\n" +
                        "                <p><a href=\"javascript:;\">"+info.authorName+"</a></p>\n" +
                        "                <span>26.1万人气</span>\n" +
                        "            </h4>");
                }else{
                    htmlInfo+="<h5 class=\""+((i+1)>3?"":"red ")+"clearfix\">\n" +
                        "                <i>"+(i+1)+"</i>\n" +
                        "                <a href=\"javascript:;\" class=\"num-xm\">["+info.type2Name+"]</a>\n" +
                        "                <a href=\"javascript:;\" class=\"num-bt\">"+info.xsTitle+"</a>\n" +
                        "            </h5>"
                }
            }
            $(".nv-gdyq2-info").html(htmlInfo);
        }
    });

    //幻想言情
    $.ajax({
        url: "/extend/html5/novepage",
        dataType: "json",
        data: {"page": 1, "limit": 5, "sort": "popularity", "order": "desc", "type1": 4},
        type: "get",
        success: function (res) {
            var htmlInfo="";
            for(var i=0;i<res.data.length;i++){
                var info=res.data[i];
                if(i==0){
                    $(".nv-cyjk1-info").html("<div class=\"row-num-pic clearfix\">\n" +
                        "                <a href=\"javascript:;\">\n" +
                        "                    <img src=\""+info.imgUrl+"\" />\n" +
                        "                    <i>1</i>\n" +
                        "                </a>\n" +
                        "            </div>\n" +
                        "            <h6><a href=\"javascript:;\">"+info.xsTitle+"</a></h6>\n" +
                        "            <h5>"+info.synopsis+"</h5>\n" +
                        "            <h4>\n" +
                        "                <p><a href=\"javascript:;\">"+info.authorName+"</a></p>\n" +
                        "                <span>26.1万人气</span>\n" +
                        "            </h4>");
                }else{
                    htmlInfo+="<h5 class=\""+((i+1)>3?"":"red ")+"clearfix\">\n" +
                        "                <i>"+(i+1)+"</i>\n" +
                        "                <a href=\"javascript:;\" class=\"num-xm\">["+info.type2Name+"]</a>\n" +
                        "                <a href=\"javascript:;\" class=\"num-bt\">"+info.xsTitle+"</a>\n" +
                        "            </h5>"
                }
            }
            $(".nv-cyjk2-info").html(htmlInfo);
        }
    });

    //青春校园
    $.ajax({
        url: "/extend/html5/novepage",
        dataType: "json",
        data: {"page": 1, "limit": 5, "sort": "popularity", "order": "desc", "type1": 5},
        type: "get",
        success: function (res) {
            var htmlInfo="";
            for(var i=0;i<res.data.length;i++){
                var info=res.data[i];
                if(i==0){
                    $(".nv-xhxx1-info").html("<div class=\"row-num-pic clearfix\">\n" +
                        "                <a href=\"javascript:;\">\n" +
                        "                    <img src=\""+info.imgUrl+"\" />\n" +
                        "                    <i>1</i>\n" +
                        "                </a>\n" +
                        "            </div>\n" +
                        "            <h6><a href=\"javascript:;\">"+info.xsTitle+"</a></h6>\n" +
                        "            <h5>"+info.synopsis+"</h5>\n" +
                        "            <h4>\n" +
                        "                <p><a href=\"javascript:;\">"+info.authorName+"</a></p>\n" +
                        "                <span>26.1万人气</span>\n" +
                        "            </h4>");
                }else{
                    htmlInfo+="<h5 class=\""+((i+1)>3?"":"red ")+"clearfix\">\n" +
                        "                <i>"+(i+1)+"</i>\n" +
                        "                <a href=\"javascript:;\" class=\"num-xm\">["+info.type2Name+"]</a>\n" +
                        "                <a href=\"javascript:;\" class=\"num-bt\">"+info.xsTitle+"</a>\n" +
                        "            </h5>"
                }
            }
            $(".nv-xhxx2-info").html(htmlInfo);
        }
    });


    $.ajax({
        url: "/extend/html5/contactlist",
        dataType: "json",
        data:{"channel":0},
        type: "get",
        success: function (res) {
            var nvHtml = "";
            var nanHtml = "";
            for (var i = 0; i < res.data.length; i++) {
                var info = res.data[i];
                nvHtml += "<h4>\n" +
                    "        <a href=\"https://wpa.qq.com/msgrd?v=3&amp;uin=" + info.qq + "&amp;site=qq&amp;menu=yes\">\n" +
                    "                 <i></i>" + info.editorName + "\n" +
                    "         </a> " + info.qq_email + "\n" +
                    "         </h4>";

            }
            $(".nv-bj-info").html(nvHtml);
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
})