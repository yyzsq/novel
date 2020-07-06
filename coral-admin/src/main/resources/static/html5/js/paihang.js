$(function (res) {
    //男生人气
    $.ajax({
        url:"/extend/html5/rankedlist",
        dataType:"json",
        data:{"rankedType":1,"rankedMiddle":0},
        async:false,
        type:"get",
        success:function (res) {
            var htmlInfo="";
            for(var i=0;i<res.data.length;i++){
                var info=res.data[i];
                htmlInfo+=addInfo(i,info);
            }

            $(".nan-reqi-group ul").html(htmlInfo);
        }
    });

    //男生新书
    $.ajax({
        url:"/extend/html5/rankedlist",
        dataType:"json",
        data:{"rankedType":1,"rankedMiddle":1},
        type:"get",
        async:false,
        success:function (res) {
            var htmlInfo="";
            for(var i=0;i<res.data.length;i++){
                var info=res.data[i];
                htmlInfo+=addInfo(i,info);
            }

            $(".nan-xinshu-group ul").html(htmlInfo);
        }
    });

    //男生完结
    $.ajax({
        url:"/extend/html5/rankedlist",
        dataType:"json",
        data:{"rankedType":1,"rankedMiddle":2},
        type:"get",
        async:false,
        success:function (res) {
            var htmlInfo="";
            for(var i=0;i<res.data.length;i++){
                var info=res.data[i];
                htmlInfo+=addInfo(i,info);
            }

            $(".nan-wanjie-group ul").html(htmlInfo);
        }
    });

    //女生人气
    $.ajax({
        url:"/extend/html5/rankedlist",
        dataType:"json",
        data:{"rankedType":0,"rankedMiddle":0},
        type:"get",
        async:false,
        success:function (res) {
            var htmlInfo="";
            for(var i=0;i<res.data.length;i++){
                var info=res.data[i];
                htmlInfo+=addInfo(i,info);
            }

            $(".nv-reqi-group ul").html(htmlInfo);
        }
    });

    //女生新书
    $.ajax({
        url:"/extend/html5/rankedlist",
        dataType:"json",
        data:{"rankedType":0,"rankedMiddle":1},
        type:"get",
        async:false,
        success:function (res) {
            var htmlInfo="";
            for(var i=0;i<res.data.length;i++){
                var info=res.data[i];
                htmlInfo+=addInfo(i,info);
            }

            $(".nv-xinshu-group ul").html(htmlInfo);
        }
    });

    //女生完结
    $.ajax({
        url:"/extend/html5/rankedlist",
        dataType:"json",
        data:{"rankedType":0,"rankedMiddle":2},
        type:"get",
        async:false,
        success:function (res) {
            var htmlInfo="";
            for(var i=0;i<res.data.length;i++){
                var info=res.data[i];
                htmlInfo+=addInfo(i,info);
            }

            $(".nv-wanjie-group ul").html(htmlInfo);

        }
    });
    // 排行榜
    $(".phb-tab-cont a").click(function () {
        $(this).addClass('on').siblings().removeClass('on');
        var index = $(this).index();
        $('.phb-cont').children().eq(index).show().siblings().hide();
    });
    /*人气榜单  切换*/
    $(".side-box li").hover(function () {
        $(this).addClass('on').siblings().removeClass('on');
    });
    function addInfo(number,obj) {
        return "<li class=\" "+(number+1>2?"":"bj-xh")+" "+(number==0?"on":"")+"\">\n" +
            "                            <div class=\"side-box-xh clearfix\">"+(number+1)+"</div>\n" +
            "                            <div class=\"side-box-cont clearfix\">\n" +
            "                                <div class=\"side-box-pic clearfix\">\n" +
            "                                    <a href=\"javascript:;\"><img src=\""+obj.imgUrl+"\" /></a>\n" +
            "                                </div>\n" +
            "                                <div class=\"side-box-info clearfix\">\n" +
            "                                    <h6><a href=\"javascript:;\">"+obj.xsTitle+"</a></h6>\n" +
            "                                    <h5><a href=\"javascript:;\">"+obj.authorName+"</a></h5>\n" +
            "                                    <h4>"+obj.popularity+"万人气</h4>\n" +
            "                                </div>\n" +
            "                            </div>\n" +
            "                        </li>";

    }

    //获取搜索框下方广告位
    $.ajax({
        url: "/extend/html5/toplist",
        dataType: "json",
        data: {"type": "7"},
        type: "get",
        success: function (res) {
            var htmlInfo = "";
            for (var i = 0; i < res.data.length; i++) {
                var info = res.data[i];
                htmlInfo += "<a href=\""+info.link+"\" class='"+(i==0?"cl-r":"")+"' >"+info.title+"</a>";
            }
            $(".sous-ggao-group").html(htmlInfo);
        }
    });
});