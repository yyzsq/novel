$(function () {
    $.ajax({
        url: "/extend/html5/writelist",
        dataType: "json",
        data: {"type": "0"},
        type: "get",
        success: function (res) {
            var htmlInfo = "";
            for (var i = 0; i < res.data.length; i++) {
                var info = res.data[i]
                htmlInfo+=" <a href=\"javascript:;\" class=\"clearfix\">\n" +
                    "                    <img src=\"images/zj-pic.jpg\" class=\"box-show-pic\">\n" +
                    "                    <div class=\"box-left-list\">\n" +
                    "                        <h2>"+info.title+"</h2>\n" +
                    "                        <p>"+info.content+"</p>\n" +
                    "                        <div class=\"box-left-edit\">\n" +
                    "                            <span><i></i>传火编辑部</span>\n" +
                    "                            <span>2020-06-17 11:18</span>\n" +
                    "                        </div>\n" +
                    "                    </div> \n" +
                    "              </a>"
             }
            $(".box-left1").html(htmlInfo);
        }
    });


    $.ajax({
        url: "/extend/html5/toplist",
        dataType: "json",
        data: {"type": "8"},
        type: "get",
        success: function (res) {
            var htmlInfo = "";
            for (var i = 0; i < res.data.length; i++) {
                var info = res.data[i]
                htmlInfo += "<li><a href="+info.link+"><img src="+info.picture+" /></a></li>";
            }
            $(".dt-banner ul").html(htmlInfo);
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


 /*   $.ajax({
        url: "/extend/html5/contactlist",
        dataType: "json",
        type: "get",
        success: function (res) {
            var tpHtml="";
            var nvHtml = "";
            var nanHtml = "";
            for (var i = 0; i < res.data.length; i++) {
                var info = res.data[i];
                tpHtml +="<h3><img src="+info.picture+"/></h3>";
                if (info.channel == "0") {
                    nvHtml += "<h4>\n" +
                        "        <a href="+info.link+">\n" +
                        "                 <i></i>"+info.editorName+"\n" +
                        "         </a> "+info.qq+"\n" +
                        "         </h4>";
                } else {
                    nanHtml += "<h4>\n" +
                        "        <a href="+info.link+">\n" +
                        "                 <i></i>"+info.editorName+"\n" +
                        "         </a> "+info.qq+"\n" +
                        "         </h4>";
                }

            }
            $(".xf-menu-tc-cont").html(tpHtml);
            $(".xf-menu-tc-cont").html(nvHtml);
            $(".xf-menu-tc-cont").html(nanHtml);
        }
    })*/

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

})