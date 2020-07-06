$(function(){
    var page=1;
    var countPage=1;
    var startIndex=1;
    var endIndex=10;
    var nanList = [];
    var nvList = [];
    var allList = [];
    var nanStr="<a href=\"javascript:;\" flag='-1' class=\"on\">全部</a>";
    var nvStr="<a href=\"javascript:;\" flag='-1' class=\"on\">全部</a>";
    var allStr="<a href=\"javascript:;\" flag='-1' class=\"on\">全部</a>";
    /*书库筛选切换on*/
    $(".sk-choose-gjc a").click(function () {
        $(this).addClass('on').siblings().removeClass('on');
        sousuo();
    });
    sousuo();
    //获取男女生分类
    $.ajax({
        url: "/extend/html5/typelist",
        dataType: "json",
        async: false,
        type: "get",
        success: function (res) {
            var htmlInfo="<a href=\"javascript:;\" flag='-1' class=\"on\">全部</a>";
            for (var i = 0; i < res.data.length; i++) {
                var info = res.data[i];
                allList.push(info);
                if(info.fuId == '0'){
                    htmlInfo+="<a href=\"javascript:;\" flag='"+info.id+"' class=\"\">"+info.typeName+"<i></i></a>";
                    allStr+="<a href=\"javascript:;\" flag='"+info.id+"' class=\"\">"+info.typeName+"<i></i></a>"
                }

                if (info.type == "0" && info.fuId == "0") {
                    nvList.push(info);
                    nvStr+="<a href=\"javascript:;\" flag='"+info.id+"' class=\"\">"+info.typeName+"<i></i></a>";
                } else if (info.type == "1" && info.fuId == "0") {
                    nanList.push(info);
                    nanStr+="<a href=\"javascript:;\" flag='"+info.id+"' class=\"\">"+info.typeName+"<i></i></a>";
                }
            }
            $(".sk-type-info").html(htmlInfo);
            loadClick();
        }
    });

    $(".pd-info-group a").click(function () {
        var falg=$(this).attr("flag");
        var htmlInfo="";
        if(falg=="0"){
            $(".sk-type-info").html(nvStr);
        }else if(falg=="1"){
            $(".sk-type-info").html(nanStr);
        }else{
            $(".sk-type-info").html(allStr);
        }
        $('.sk-gjc-ej-item').hide();
        loadClick();
    });


    function getTypeItem(tyepId) {
        $.ajax({
            url: "/extend/html5/typelist",
            data:{"fuId":tyepId},
            dataType: "json",
            async: false,
            type: "get",
            success: function (res) {
                var htmlInfo="";
                for(var i=0;i<res.data.length;i++){
                    htmlInfo+=" <a href=\"javascript:;\" flag='"+res.data[i].id+"' class=\"\">"+res.data[i].typeName+"</a>"
                }
                $(".sk-type-item").html(htmlInfo);
                $(".sk-gjc-ej-item a").click(function () {
                    $(this).addClass('on').siblings().removeClass('on');
                })

            }
        })
    }

    function sousuo() {

        var pdType=$(".pd-info-group .on").attr("flag");
        var typeFlag=$(".sk-type-info .on").attr("flag");
        var type2Flag=$(".sk-type-item .on").attr("flag");
        var zsNumber=$(".sk-zs-group .on").attr("flag");
        var gxTime=$(".gx-time-group .on").attr("flag");
        var isWj=$(".is-wj-group .on").attr("flag");
        var pxLx=$(".px-lx-group .on").attr("flag");
        $.ajax({
            url: "/extend/html5/noveInfopage",
            dataType: "json",
            data:{"type1":typeFlag=="-1"?null:typeFlag,"type2":type2Flag=="-1"?null:type2Flag,"isWanJie":isWj,
                    "channelType":pdType=="-1"?null:pdType,"orderBys":pxLx,"charNumber":zsNumber=="-1"?null:zsNumber,"gxTime":gxTime=="-1"?null:gxTime,"page":page,"limit":"15"},
            async: false,
            type: "get",
            success: function (res) {

                var twHtmlInfo="";
                var lbHtmlInfo="";
                for(var i=0;i<res.data.length;i++ ){
                    var info=res.data[i];
                    if(info==null){
                        continue;
                    }
                    twHtmlInfo+="<dd>\n" +
                        "                <div class=\"sk-cont-pic clearfix\">\n" +
                        "                    <a href=\"javascript:;\"><img src=\""+info.imgUrl+"\"></a>\n" +
                        "                </div>\n" +
                        "                <h6><a href=\"javascript:;\">"+info.xsTitle+"</a></h6>\n" +
                    "                <div class=\"sk-cont-info clearfix\">\n" +
                    "                    <i>"+info.Type2Name+"</i>\n" +
                    "                    <span>"+(info.status=="3"?"完结":"连载中")+"</span>\n" +
                    "                    <span>"+info.charNumber+"万字</span>\n" +
                    "                </div>\n" +
                    "                <h5>"+info.synopsis+"</h5>\n" +
                    "                <h4>\n" +
                    "                    <span>"+info.authorName+"<em>"+info.newChapterTimeStr+"更</em></span>\n" +
                    "                </h4>\n" +
                    "            </dd> ";
                    lbHtmlInfo+="<tr>                     \n" +
                        "                        <td class=\"cl-g\">1</td>\n" +
                        "                        <td class=\"cl-g\"><a href=\"javascript:;\">["+info.type2Name+"]</a></td>\n" +
                        "                        <td><a href=\"javascript:;\">"+info.xsTitle+"</a></td>\n" +
                        "                        <td><a href=\"javascript:;\">"+info.newChapter+"</a></td>\n" +
                        "                        <td class=\"cl-g\"><a href=\"javascript:;\">"+info.authorName+"</a></td>\n" +
                        "                        <td class=\"cl-g\">"+info.charNumber+"万字</td>\n" +
                        "                        <td class=\"cl-g\">"+(info.status=="3"?"完结":"连载中")+"</td>\n" +
                        "                        <td style=\" text-align: right;\"><span>"+info.newChapterTimeStr+"<span></td>\n" +
                        "                    </tr>";
                }
                $(".tw-list-group").html(twHtmlInfo);
                $(".wz-list-group tbody").html(lbHtmlInfo);
                countPage=Math.ceil(res.count/15);

                var pageHtml="<li flag=\"-1\" class=\"disabled up-page\">上一页</li>";
                if(countPage>11){
                    endIndex=10;
                    for (var i = 1; i <= 10; i++) {
                        pageHtml += "<li class=\"" + (i == 1 ? "on" : "") + "\" flag=\"" + i + "\">" + i + "</li>";
                    }
                    pageHtml+="<li flag=\"-5\">...</li>";
                    pageHtml+="<li flag=\""+countPage+"\">"+countPage+"</li>";
                }else {
                    endIndex=11;
                    for (var i = 1; i <= countPage; i++) {
                        pageHtml += "<li class=\"" + (i == 1 ? "on" : "") + "\" flag=\"" + i + "\">" + i + "</li>";
                    }
                }
                pageHtml+="<li flag=\"-2\" class=\"next-page\">下一页</li>\n" +
                    "                <li flag=\"-3\"><input name=\"tzNumber\" type=\"text\" /></li>\n" +
                    "                <li flag=\"-4\" class=\"tiaoz-page\">跳转</li>";

                $(".page-number-group ul").html(pageHtml);
            }
        })
    }

    function loadClick() {
        $(".sk-gjc-item-cont>a:not(:first)").click(function(){
            $(this).addClass('on').siblings().removeClass('on').parents('.sk-gjc-item').siblings().find('.sk-gjc-item-cont a').removeClass('on');
            $(this).parents('.sk-gjc-item').find('.sk-gjc-ej-item').show().parents('.sk-gjc-item').siblings().find('.sk-gjc-ej-item').hide();
            var typeId=$(this).attr("flag");
            getTypeItem(typeId);
        });

        $(".sk-gjc-item-cont a:eq(0)").click(function(){
            $(this).addClass('on').siblings().removeClass('on').parents('.sk-gjc-item').siblings().find('.sk-gjc-item-cont a').removeClass('on');
            $('.sk-gjc-ej-item').hide();
        });
    }

    //书库分页切换
    $(document).on('click',".page-number li",function(e){
        var obj=$(this).attr("flag");
        var onFlag=$(".page-number .on").attr("flag");
        if(countPage>11){
            var falgObj=parseInt(obj);
            var flag="false";
            if(obj=="-1"||obj=="-2"){
                falgObj=parseInt(onFlag);
                flag="true";
            }
            if (obj == "-5"||obj=="-3") {
                return;
            }

            var pageInfoH5="";


            if(parseInt(falgObj)-1>5){
                pageInfoH5="<li flag=\"-1\" class=\" up-page\">上一页</li>";
                pageInfoH5+="<li flag=\"1\">1</li><li flag=\"-5\">...</li>";
                if(parseInt(countPage)-falgObj>6){
                    startIndex=obj-3;
                    endIndex=obj+4;
                    for(var i=falgObj-3;i<falgObj+5;i++){
                        pageInfoH5+="<li flag='"+i+"'>"+i+"</li>"
                    }
                    pageInfoH5+="<li flag=\"-5\">...</li><li flag=\""+countPage+"\">"+countPage+"</li>";
                    pageInfoH5+="<li flag=\"-2\" class=\" next-page\">下一页</li>\n"
                }else{
                    for(var i=countPage-9;i<=countPage;i++){
                        pageInfoH5+="<li flag='"+i+"'>"+i+"</li>";
                    }
                    pageInfoH5+="<li flag=\"-2\" class=\""+(falgObj+1==countPage?"disabled":"")+" next-page\">下一页</li>\n"
                }
            }else{
                startIndex=1;
                endIndex=10;
                pageInfoH5="<li flag=\"-1\" class=\""+(falgObj-1==1?"disabled":"")+"  up-page\">上一页</li>";
                for(var i=1;i<=10;i++){
                    pageInfoH5+="<li flag='"+i+"'>"+i+"</li>";
                }
                pageInfoH5+="<li flag=\"-5\">...</li><li flag=\""+countPage+"\">"+countPage+"</li>";
                pageInfoH5+="<li flag=\"-2\" class=\" next-page\">下一页</li>\n"
            }

            pageInfoH5+="        <li flag=\"-3\"><input name=\"tzNumber\" type=\"text\" /></li>\n" +
                "                <li flag=\"-4\" class=\"tiaoz-page\">跳转</li>";
            $(".page-number-group ul").html(pageInfoH5);
            $(".page-number-group [flag='"+(flag=="true"?parseInt(onFlag):obj)+"']").addClass("on");
        }

        if (obj == "-1") {
            if ($(this).hasClass('disabled')) {
                return;
            }
            if (parseInt(onFlag) - 1 == 1) {
                $(this).addClass("disabled")
            }
            $(".page-number-group .on").prev().addClass('on').siblings().removeClass('on');
            $(".next-page").removeClass("disabled");
        } else if (obj == "-2") {
            if ($(this).hasClass('disabled')) {
                return;
            }
            if (parseInt(onFlag) + 1 == parseInt(countPage)) {
                $(this).addClass("disabled")
            }
            $(".page-number .on").next().addClass('on').siblings().removeClass('on');
            $(".up-page").removeClass("disabled");

        } else if (obj == "-2") {
            if ($(this).hasClass('disabled')) {
                return;
            }
            if (parseInt(onFlag) + 1 == parseInt(countPage)) {
                $(this).addClass("disabled")
            }
            $(".page-number .on").next().addClass('on').siblings().removeClass('on');
            $(".up-page").removeClass("disabled");

        } else if (obj == "-4") {
            $(".up-page").removeClass("disabled");
        } else if (obj == "1") {
            $(".next-page").removeClass("disabled");
            $(this).addClass('on').siblings().removeClass('on');
            $(".up-page").addClass("disabled");
        } else if (parseInt(obj) == countPage) {
            $(".up-page").removeClass("disabled");
            $(this).addClass('on').siblings().removeClass('on');
            $(".next-page").addClass("disabled");
        } else if (obj == "-5") {

        } else {
            $(this).addClass('on').siblings().removeClass('on');
            $(".up-page").removeClass("disabled");
            $(".next-page").removeClass("disabled");
        }
    });

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