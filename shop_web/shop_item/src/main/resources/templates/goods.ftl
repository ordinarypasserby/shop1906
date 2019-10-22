<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <base href="/resources/"/>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
    <title>商品详细页面</title>
    <link rel="stylesheet" href="css/base.css" type="text/css" />
    <link rel="stylesheet" href="css/shop_common.css" type="text/css" />
    <link rel="stylesheet" href="css/shop_header.css" type="text/css" />
    <link rel="stylesheet" href="css/shop_list.css" type="text/css" />
    <link rel="stylesheet" href="css/shop_goods.css" type="text/css" />
    <script type="text/javascript" src="js/jquery.js" ></script>
    <script type="text/javascript" src="js/topNav.js" ></script>
    <script type="text/javascript" src="js/shop_goods.js" ></script>
    <script type="text/javascript" src="js/shopcart.js" ></script>


    <script type="text/javascript" src="js/login.js" ></script>
</head>
<body>
<!-- Header  -wll-2013/03/24 -->
<div class="shop_hd">
    <!-- Header TopNav -->
    <div class="shop_hd_topNav">
        <div class="shop_hd_topNav_all">
            <!-- Header TopNav Left -->
            <div class="shop_hd_topNav_all_left">
                <p id="pid"></p>
            </div>
            <!-- Header TopNav Left End -->

            <!-- Header TopNav Right -->
            <div class="shop_hd_topNav_all_right">
                <ul class="topNav_quick_menu">

                    <li>
                        <div class="topNav_menu">
                            <a href="#" class="topNavHover">我的商城<i></i></a>
                            <div class="topNav_menu_bd" style="display:none;" >
                                <ul>
                                    <li><a title="已买到的商品" target="_top" href="#">已买到的商品</a></li>
                                    <li><a title="个人主页" target="_top" href="#">个人主页</a></li>
                                    <li><a title="我的好友" target="_top" href="#">我的好友</a></li>
                                </ul>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="topNav_menu">
                            <a href="#" class="topNavHover">卖家中心<i></i></a>
                            <div class="topNav_menu_bd" style="display:none;">
                                <ul>
                                    <li><a title="已售出的商品" target="_top" href="#">已售出的商品</a></li>
                                    <li><a title="销售中的商品" target="_top" href="#">销售中的商品</a></li>
                                </ul>
                            </div>
                        </div>
                    </li>

                    <li>
                        <!-- TODO 购物车展示列表 -->
                        <div class="topNav_menu">
                            <a href="#" class="topNavHover">购物车<b id="cartnumberid">0</b>种商品<i></i></a>
                            <div id="cartid" class="topNav_menu_bd" style="display:none;width: 300px; height: 400px">
                            </div>
                        </div>
                    </li>

                    <li>
                        <div class="topNav_menu">
                            <a href="#" class="topNavHover">我的收藏<i></i></a>
                            <div class="topNav_menu_bd" style="display:none;">
                                <ul>
                                    <li><a title="收藏的商品" target="_top" href="#">收藏的商品</a></li>
                                    <li><a title="收藏的店铺" target="_top" href="#">收藏的店铺</a></li>
                                </ul>
                            </div>
                        </div>
                    </li>

                    <li>
                        <div class="topNav_menu">
                            <a href="#">站内消息</a>
                        </div>
                    </li>

                </ul>
            </div>
            <!-- Header TopNav Right End -->
        </div>
        <div class="clear"></div>
    </div>
    <div class="clear"></div>
    <!-- Header TopNav End -->

    <!-- TopHeader Center -->
    <div class="shop_hd_header">
        <div class="shop_hd_header_logo"><h1 class="logo"><a href="/"><img src="images/logo.png" alt="ShopCZ" /></a><span>ShopCZ</span></h1></div>
        <div class="shop_hd_header_search">
            <ul class="shop_hd_header_search_tab">
                <li id="search" class="current">商品</li>
                <li id="shop_search">店铺</li>
            </ul>
            <div class="clear"></div>
            <div class="search_form">
                <form method="post" action="index.php">
                    <div class="search_formstyle">
                        <input type="text" class="search_form_text" name="search_content" value="搜索其实很简单！" />
                        <input type="submit" class="search_form_sub" name="secrch_submit" value="" title="搜索" />
                    </div>
                </form>
            </div>
            <div class="clear"></div>
            <div class="search_tag">
                <a >李宁</a>
                <a >耐克</a>
                <a >Kappa</a>
                <a >双肩包</a>
                <a >手提包</a>
            </div>

        </div>
    </div>
    <div class="clear"></div>
    <!-- TopHeader Center End -->

    <!-- Header Menu -->
    <div class="shop_hd_menu">
        <!-- 所有商品菜单 -->

        <div id="shop_hd_menu_all_category" class="shop_hd_menu_all_category"><!-- 首页去掉 id="shop_hd_menu_all_category" 加上clsss shop_hd_menu_hover -->
            <div class="shop_hd_menu_all_category_title"><h2 title="所有商品分类"><a href="javascript:void(0);">所有商品分类</a></h2><i></i></div>
            <div id="shop_hd_menu_all_category_hd" class="shop_hd_menu_all_category_hd">
                <ul class="shop_hd_menu_all_category_hd_menu clearfix">
                    <!-- 单个菜单项 -->
                    <li id="cat_1" class="">
                        <h3><a  title="男女服装">男女服装</a></h3>
                        <div id="cat_1_menu" class="cat_menu clearfix" style="">
                            <dl class="clearfix">
                                <dt><a href="女装" >女装</a></dt>
                                <dd>
                                    <a >风衣</a>
                                    <a >长袖连衣裙</a>
                                    <a >毛呢连衣裙</a>
                                    <a >半身裙</a>
                                    <a >小脚裤</a>
                                    <a >加绒打底裤</a>
                                    <a >牛仔裤</a>
                                    <a >打底衫</a>
                                    <a >情侣装</a>
                                    <a >棉衣</a>
                                    <a >毛呢大衣</a>
                                    <a >毛呢短裤</a>
                                </dd>
                            </dl>

                            <dl class="clearfix">
                                <dt><a href="男装" >男装</a></dt>
                                <dd>
                                    <a >风衣</a>
                                    <a >长袖连衣裙</a>
                                    <a >毛呢连衣裙</a>
                                    <a >半身裙</a>
                                    <a >小脚裤</a>
                                    <a >加绒打底裤</a>
                                    <a >牛仔裤</a>
                                    <a >打底衫</a>
                                    <a >情侣装</a>
                                    <a >棉衣</a>
                                    <a >毛呢大衣</a>
                                    <a >毛呢短裤</a>
                                </dd>
                            </dl>
                        </div>
                    </li>
                    <!-- 单个菜单项 End -->
                    <li id="cat_2" class="">
                        <h3><a  title="鞋包配饰">鞋包配饰</a></h3>
                        <div id="cat_1_menu" class="cat_menu clearfix" style="">
                            <dl class="clearfix">
                                <dt><a href="鞋子" >鞋子</a></dt>
                                <dd>
                                    <a >风衣</a>
                                    <a >长袖连衣裙</a>
                                    <a >毛呢连衣裙</a>
                                    <a >半身裙</a>
                                    <a >小脚裤</a>
                                    <a >加绒打底裤</a>
                                    <a >牛仔裤</a>
                                    <a >打底衫</a>
                                    <a >情侣装</a>
                                    <a >棉衣</a>
                                    <a >毛呢大衣</a>
                                    <a >毛呢短裤</a>
                                </dd>
                            </dl>

                            <dl class="clearfix">
                                <dt><a href="包包" >包包</a></dt>
                                <dd>
                                    <a >风衣</a>
                                    <a >长袖连衣裙</a>
                                    <a >毛呢连衣裙</a>
                                    <a >半身裙</a>
                                    <a >小脚裤</a>
                                    <a >加绒打底裤</a>
                                    <a >牛仔裤</a>
                                    <a >打底衫</a>
                                    <a >情侣装</a>
                                    <a >棉衣</a>
                                    <a >毛呢大衣</a>
                                    <a >毛呢短裤</a>
                                </dd>
                            </dl>
                        </div>
                    </li>

                    <li id="cat_3" class="">
                        <h3><a  title="美容美妆">美容美妆</a></h3>
                        <div id="cat_1_menu" class="cat_menu clearfix" style="">
                            <dl class="clearfix">
                                <dt><a href="美容" >美容</a></dt>
                                <dd>
                                    <a >风衣</a>
                                    <a >长袖连衣裙</a>
                                    <a >毛呢连衣裙</a>
                                    <a >半身裙</a>
                                    <a >小脚裤</a>
                                    <a >加绒打底裤</a>
                                    <a >牛仔裤</a>
                                    <a >打底衫</a>
                                    <a >情侣装</a>
                                    <a >棉衣</a>
                                    <a >毛呢大衣</a>
                                    <a >毛呢短裤</a>
                                </dd>
                            </dl>

                            <dl class="clearfix">
                                <dt><a href="美妆" >美妆</a></dt>
                                <dd>
                                    <a >风衣</a>
                                    <a >长袖连衣裙</a>
                                    <a >毛呢连衣裙</a>
                                    <a >半身裙</a>
                                    <a >小脚裤</a>
                                    <a >加绒打底裤</a>
                                    <a >牛仔裤</a>
                                    <a >打底衫</a>
                                    <a >情侣装</a>
                                    <a >棉衣</a>
                                    <a >毛呢大衣</a>
                                    <a >毛呢短裤</a>
                                </dd>
                            </dl>
                        </div>
                    </li>

                    <li id="cat_4" class="">
                        <h3><a  title="美容美妆">美容美妆</a></h3>
                        <div id="cat_1_menu" class="cat_menu clearfix" style="">
                            <dl class="clearfix">
                                <dt><a href="美容" >美容</a></dt>
                                <dd>
                                    <a >风衣</a>
                                    <a >长袖连衣裙</a>
                                    <a >毛呢连衣裙</a>
                                    <a >半身裙</a>
                                    <a >小脚裤</a>
                                    <a >加绒打底裤</a>
                                    <a >牛仔裤</a>
                                    <a >打底衫</a>
                                    <a >情侣装</a>
                                    <a >棉衣</a>
                                    <a >毛呢大衣</a>
                                    <a >毛呢短裤</a>
                                </dd>
                            </dl>

                            <dl class="clearfix">
                                <dt><a href="美妆" >美妆</a></dt>
                                <dd>
                                    <a >风衣</a>
                                    <a >长袖连衣裙</a>
                                    <a >毛呢连衣裙</a>
                                    <a >半身裙</a>
                                    <a >小脚裤</a>
                                    <a >加绒打底裤</a>
                                    <a >牛仔裤</a>
                                    <a >打底衫</a>
                                    <a >情侣装</a>
                                    <a >棉衣</a>
                                    <a >毛呢大衣</a>
                                    <a >毛呢短裤</a>
                                </dd>
                            </dl>
                        </div>
                    </li>

                    <li id="cat_5" class="">
                        <h3><a  title="美容美妆">美容美妆</a></h3>
                        <div id="cat_1_menu" class="cat_menu clearfix" style="">
                            <dl class="clearfix">
                                <dt><a href="美容" >美容</a></dt>
                                <dd>
                                    <a >风衣</a>
                                    <a >长袖连衣裙</a>
                                    <a >毛呢连衣裙</a>
                                    <a >半身裙</a>
                                    <a >小脚裤</a>
                                    <a >加绒打底裤</a>
                                    <a >牛仔裤</a>
                                    <a >打底衫</a>
                                    <a >情侣装</a>
                                    <a >棉衣</a>
                                    <a >毛呢大衣</a>
                                    <a >毛呢短裤</a>
                                </dd>
                            </dl>

                            <dl class="clearfix">
                                <dt><a href="美妆" >美妆</a></dt>
                                <dd>
                                    <a >风衣</a>
                                    <a >长袖连衣裙</a>
                                    <a >毛呢连衣裙</a>
                                    <a >半身裙</a>
                                    <a >小脚裤</a>
                                    <a >加绒打底裤</a>
                                    <a >牛仔裤</a>
                                    <a >打底衫</a>
                                    <a >情侣装</a>
                                    <a >棉衣</a>
                                    <a >毛呢大衣</a>
                                    <a >毛呢短裤</a>
                                </dd>
                            </dl>
                        </div>
                    </li>

                    <li id="cat_6" class="">
                        <h3><a  title="美容美妆">美容美妆</a></h3>
                        <div id="cat_1_menu" class="cat_menu clearfix" style="">
                            <dl class="clearfix">
                                <dt><a href="美容" >美容</a></dt>
                                <dd>
                                    <a >风衣</a>
                                    <a >长袖连衣裙</a>
                                    <a >毛呢连衣裙</a>
                                    <a >半身裙</a>
                                    <a >小脚裤</a>
                                    <a >加绒打底裤</a>
                                    <a >牛仔裤</a>
                                    <a >打底衫</a>
                                    <a >情侣装</a>
                                    <a >棉衣</a>
                                    <a >毛呢大衣</a>
                                    <a >毛呢短裤</a>
                                </dd>
                            </dl>

                            <dl class="clearfix">
                                <dt><a href="美妆" >美妆</a></dt>
                                <dd>
                                    <a >风衣</a>
                                    <a >长袖连衣裙</a>
                                    <a >毛呢连衣裙</a>
                                    <a >半身裙</a>
                                    <a >小脚裤</a>
                                    <a >加绒打底裤</a>
                                    <a >牛仔裤</a>
                                    <a >打底衫</a>
                                    <a >情侣装</a>
                                    <a >棉衣</a>
                                    <a >毛呢大衣</a>
                                    <a >毛呢短裤</a>
                                </dd>
                            </dl>
                        </div>
                    </li>
                    <li id="cat_7" class="">
                        <h3><a  title="美容美妆">美容美妆</a></h3>
                        <div id="cat_1_menu" class="cat_menu clearfix" style="">
                            <dl class="clearfix">
                                <dt><a href="美容" >美容</a></dt>
                                <dd>
                                    <a >风衣</a>
                                    <a >长袖连衣裙</a>
                                    <a >毛呢连衣裙</a>
                                    <a >半身裙</a>
                                    <a >小脚裤</a>
                                    <a >加绒打底裤</a>
                                    <a >牛仔裤</a>
                                    <a >打底衫</a>
                                    <a >情侣装</a>
                                    <a >棉衣</a>
                                    <a >毛呢大衣</a>
                                    <a >毛呢短裤</a>
                                </dd>
                            </dl>

                            <dl class="clearfix">
                                <dt><a href="美妆" >美妆</a></dt>
                                <dd>
                                    <a >风衣</a>
                                    <a >长袖连衣裙</a>
                                    <a >毛呢连衣裙</a>
                                    <a >半身裙</a>
                                    <a >小脚裤</a>
                                    <a >加绒打底裤</a>
                                    <a >牛仔裤</a>
                                    <a >打底衫</a>
                                    <a >情侣装</a>
                                    <a >棉衣</a>
                                    <a >毛呢大衣</a>
                                    <a >毛呢短裤</a>
                                </dd>
                            </dl>
                        </div>
                    </li>
                    <li id="cat_8" class="">
                        <h3><a  title="美容美妆">美容美妆</a></h3>
                        <div id="cat_1_menu" class="cat_menu clearfix" style="">
                            <dl class="clearfix">
                                <dt><a href="美容" >美容</a></dt>
                                <dd>
                                    <a >风衣</a>
                                    <a >长袖连衣裙</a>
                                    <a >毛呢连衣裙</a>
                                    <a >半身裙</a>
                                    <a >小脚裤</a>
                                    <a >加绒打底裤</a>
                                    <a >牛仔裤</a>
                                    <a >打底衫</a>
                                    <a >情侣装</a>
                                    <a >棉衣</a>
                                    <a >毛呢大衣</a>
                                    <a >毛呢短裤</a>
                                </dd>
                            </dl>

                            <dl class="clearfix">
                                <dt><a href="美妆" >美妆</a></dt>
                                <dd>
                                    <a >风衣</a>
                                    <a >长袖连衣裙</a>
                                    <a >毛呢连衣裙</a>
                                    <a >半身裙</a>
                                    <a >小脚裤</a>
                                    <a >加绒打底裤</a>
                                    <a >牛仔裤</a>
                                    <a >打底衫</a>
                                    <a >情侣装</a>
                                    <a >棉衣</a>
                                    <a >毛呢大衣</a>
                                    <a >毛呢短裤</a>
                                </dd>
                            </dl>
                        </div>
                    </li>
                    <li class="more"><a >查看更多分类</a></li>
                </ul>
            </div>
        </div>
        <!-- 所有商品菜单 END -->

        <!-- 普通导航菜单 -->
        <ul class="shop_hd_menu_nav">
            <li class="current_link"><a ><span>首页</span></a></li>
            <li class="link"><a ><span>团购</span></a></li>
            <li class="link"><a ><span>品牌</span></a></li>
            <li class="link"><a ><span>优惠卷</span></a></li>
            <li class="link"><a ><span>积分中心</span></a></li>
            <li class="link"><a ><span>运动专场</span></a></li>
            <li class="link"><a ><span>微商城</span></a></li>
        </ul>
        <!-- 普通导航菜单 End -->
    </div>
    <!-- Header Menu End -->

</div>
<div class="clear"></div>
<!-- 面包屑 注意首页没有 -->
<div class="shop_hd_breadcrumb">
    <strong>当前位置：</strong>
    <span>
			<a >首页</a>&nbsp;›&nbsp;
			<a >商品分类</a>&nbsp;›&nbsp;
			<a >男装女装</a>&nbsp;›&nbsp;
			<a >男装</a>
		</span>
</div>
<div class="clear"></div>
<!-- 面包屑 End -->

<!-- Header End -->

<!-- Goods Body -->
<div class="shop_goods_bd clear">

    <!-- 商品展示 -->
    <div class="shop_goods_show">
        <div class="shop_goods_show_left">
            <!-- 京东商品展示 -->
            <link rel="stylesheet" href="css/shop_goodPic.css" type="text/css" />
            <script type="text/javascript" src="js/shop_goodPic_base.js"></script>
            <script type="text/javascript" src="js/lib.js"></script>
            <script type="text/javascript" src="js/163css.js"></script>
            <div id="preview">
                <div class=jqzoom id="spec-n1" onClick="window.open('/')">
                    <!-- 显示的大图 -->
                    <IMG height="350" src="${goods.fengmian}" jqimg="${goods.fengmian}" width="350">
                </div>
                <div id="spec-n5">
                    <div class=control id="spec-left">
                        <img src="images/left.gif" />
                    </div>
                    <div id="spec-list">
                        <ul class="list-h">
                            <!-- 底部的一组小图 -->
                            <li><img src="${goods.fengmian}"> </li>

                            <#list goods.otherImg as img>
                                <li><img src="${img}"> </li>
                            </#list>

                        </ul>
                    </div>
                    <div class=control id="spec-right">
                        <img src="images/right.gif" />
                    </div>

                </div>
            </div>

            <SCRIPT type=text/javascript>
                $(function(){
                    $(".jqzoom").jqueryzoom({
                        xzoom:400,
                        yzoom:400,
                        offset:10,
                        position:"right",
                        preload:1,
                        lens:1
                    });
                    $("#spec-list").jdMarquee({
                        deriction:"left",
                        width:350,
                        height:56,
                        step:2,
                        speed:4,
                        delay:10,
                        control:true,
                        _front:"#spec-right",
                        _back:"#spec-left"
                    });
                    $("#spec-list img").bind("mouseover",function(){
                        var src=$(this).attr("src");
                        $("#spec-n1 img").eq(0).attr({
                            src:src.replace("\/n5\/","\/n1\/"),
                            jqimg:src.replace("\/n5\/","\/n0\/")
                        });
                        $(this).css({
                            "border":"2px solid #ff6600",
                            "padding":"1px"
                        });
                    }).bind("mouseout",function(){
                        $(this).css({
                            "border":"1px solid #ccc",
                            "padding":"2px"
                        });
                    });
                })
            </SCRIPT>
            <!-- 京东商品展示 End -->

        </div>
        <div class="shop_goods_show_right">
            <ul>
                <li>
                    <strong style="font-size:14px; font-weight:bold;">${goods.subject}</strong>
                </li>
                <li>
                    <label>价格：</label>
                    <span><strong>${goods.price?string("￥#,###.##")}</strong>元</span>
                </li>
                <li>
                    <label>运费：</label>
                    <span>卖家承担运费</span>
                </li>
                <li>
                    <label>剩余库存：</label>
                    <span>${goods.save}件</span>
                </li>
                <li>
                    <label>评价：</label>
                    <span>0条评论</span>
                </li>
                <li class="goods_num">
                    <label>购买数量：</label>
                    <span>
                        <!-- 减号 -->
                        <a class="good_num_jian" id="good_num_jian" href="javascript:void(0);"></a>
                        <!-- 数量的输入框 -->
                        <input type="text" value="1" id="good_nums" class="good_nums" />
                        <!-- 加号 -->
                        <a href="javascript:void(0);" id="good_num_jia" class="good_num_jia"></a>(当前库存0件)
                    </span>
                </li>
                <li style="padding:20px 0;">
                    <label>&nbsp;</label>
                    <span><a class="goods_sub goods_sub_gou" onclick="addCart();">加入购物车</a></span>
                </li>
            </ul>
        </div>
    </div>
    <!-- 商品展示 End -->
    <#--注释-->
    <script type="text/javascript">
        //添加购物车
        function addCart(){
            //获得商品id
            var gid = ${goods.id};
            //获得商品数量
            var gnumber = $("#good_nums").val();
            //获得商品单价
            var price = ${goods.price}
            //加入购物车
            location.href = "http://localhost:16666/cart/insert?gid=" + gid + "&gnumber=" + gnumber + "&price="+ price;
        }
    </script>

    <div class="clear mt15"></div>
    <!-- Goods Left -->
    <div class="shop_bd_list_left clearfix">
        <!-- 左边商品分类 -->
        <div class="shop_bd_list_bk clearfix">
            <div class="title">商品分类</div>
            <div class="contents clearfix">
                <dl class="shop_bd_list_type_links clearfix">
                    <dt><strong>女装</strong></dt>
                    <dd>
                        <span><a >风衣</a></span>
                        <span><a >长袖连衣裙</a></span>
                        <span><a >毛呢连衣裙</a></span>
                        <span><a >半身裙</a></span>
                        <span><a >小脚裤</a></span>
                        <span><a >加绒打底裤</a></span>
                        <span><a >牛仔裤</a></span>
                        <span><a >打底衫</a></span>
                        <span><a >情侣装</a></span>
                        <span><a >棉衣</a></span>
                        <span><a >毛呢大衣</a></span>
                        <span><a >毛呢短裤</a></span>
                    </dd>
                </dl>
            </div>
        </div>
        <!-- 左边商品分类 End -->

        <!-- 热卖推荐商品 -->
        <div class="shop_bd_list_bk clearfix">
            <div class="title">热卖推荐商品</div>
            <div class="contents clearfix">
                <ul class="clearfix">

                    <li class="clearfix">
                        <div class="goods_name"><a >Gap经典弹力纯色长袖T恤|000891347|原价149元</a></div>
                        <div class="goods_pic"><span class="goods_price">¥ 279.00 </span><a ><img src="images/89a6d6466b00ae32d3c826b9ec639084.jpg_small.jpg" /></a></div>
                        <div class="goods_xiaoliang">
                            <span class="goods_xiaoliang_link"><a >去看看</a></span>
                            <span class="goods_xiaoliang_nums">已销售<strong>99</strong>笔</span>
                        </div>
                    </li>

                    <li class="clearfix">
                        <div class="goods_name"><a >Gap经典弹力纯色长袖T恤|000891347|原价149元</a></div>
                        <div class="goods_pic"><span class="goods_price">¥ 279.00 </span><a ><img src="images/89a6d6466b00ae32d3c826b9ec639084.jpg_small.jpg" /></a></div>
                        <div class="goods_xiaoliang">
                            <span class="goods_xiaoliang_link"><a >去看看</a></span>
                            <span class="goods_xiaoliang_nums">已销售<strong>99</strong>笔</span>
                        </div>
                    </li>

                    <li class="clearfix">
                        <div class="goods_name"><a >Gap经典弹力纯色长袖T恤|000891347|原价149元</a></div>
                        <div class="goods_pic"><span class="goods_price">¥ 279.00 </span><a ><img src="images/89a6d6466b00ae32d3c826b9ec639084.jpg_small.jpg" /></a></div>
                        <div class="goods_xiaoliang">
                            <span class="goods_xiaoliang_link"><a >去看看</a></span>
                            <span class="goods_xiaoliang_nums">已销售<strong>99</strong>笔</span>
                        </div>
                    </li>

                </ul>
            </div>
        </div>
        <!-- 热卖推荐商品 -->
        <div class="clear"></div>

        <!-- 浏览过的商品 -->
        <div class="shop_bd_list_bk clearfix">
            <div class="title">浏览过的商品</div>
            <div class="contents clearfix">
                <ul class="clearfix">

                    <li class="clearfix">
                        <div class="goods_name"><a >Gap经典弹力纯色长袖T恤|000891347|原价149元</a></div>
                        <div class="goods_pic"><span class="goods_price">¥ 279.00 </span><a ><img src="images/89a6d6466b00ae32d3c826b9ec639084.jpg_small.jpg" /></a></div>
                        <div class="goods_xiaoliang">
                            <span class="goods_xiaoliang_link"><a >去看看</a></span>
                            <span class="goods_xiaoliang_nums">已销售<strong>99</strong>笔</span>
                        </div>
                    </li>

                    <li class="clearfix">
                        <div class="goods_name"><a >Gap经典弹力纯色长袖T恤|000891347|原价149元</a></div>
                        <div class="goods_pic"><span class="goods_price">¥ 279.00 </span><a ><img src="images/89a6d6466b00ae32d3c826b9ec639084.jpg_small.jpg" /></a></div>
                        <div class="goods_xiaoliang">
                            <span class="goods_xiaoliang_link"><a >去看看</a></span>
                            <span class="goods_xiaoliang_nums">已销售<strong>99</strong>笔</span>
                        </div>
                    </li>

                    <li class="clearfix">
                        <div class="goods_name"><a >Gap经典弹力纯色长袖T恤|000891347|原价149元</a></div>
                        <div class="goods_pic"><span class="goods_price">¥ 279.00 </span><a ><img src="images/89a6d6466b00ae32d3c826b9ec639084.jpg_small.jpg" /></a></div>
                        <div class="goods_xiaoliang">
                            <span class="goods_xiaoliang_link"><a >去看看</a></span>
                            <span class="goods_xiaoliang_nums">已销售<strong>99</strong>笔</span>
                        </div>
                    </li>

                </ul>
            </div>
        </div>
        <!-- 浏览过的商品 -->

    </div>
    <!-- Goods Left End -->

    <!-- 商品详情 -->
    <script type="text/javascript" src="js/shop_goods_tab.js"></script>
    <div class="shop_goods_bd_xiangqing clearfix">
        <div class="shop_goods_bd_xiangqing_tab">
            <ul>
                <li id="xiangqing_tab_1" onmouseover="shop_goods_easytabs('1', '1');" onfocus="shop_goods_easytabs('1', '1');" onclick="return false;"><a ><span>商品详情</span></a></li>
                <li id="xiangqing_tab_2" onmouseover="shop_goods_easytabs('1', '2');" onfocus="shop_goods_easytabs('1', '2');" onclick="return false;"><a ><span>商品评论</span></a></li>
                <li id="xiangqing_tab_3" onmouseover="shop_goods_easytabs('1', '3');" onfocus="shop_goods_easytabs('1', '3');" onclick="return false;"><a ><span>商品咨询</span></a></li>
            </ul>
        </div>
        <div class="shop_goods_bd_xiangqing_content clearfix">
            <div id="xiangqing_content_1" class="xiangqing_contents clearfix">
                <p>${goods.info}</p>
            </div>
            <div id="xiangqing_content_2" class="xiangqing_contents clearfix">
                <p>商品评论----22222</p>
            </div>

            <div id="xiangqing_content_3" class="xiangqing_contents clearfix">
                <p>商品自诩---3333</p>
            </div>
        </div>
    </div>
    <!-- 商品详情 End -->

</div>
<!-- Goods Body End -->

<!-- Footer - wll - 2013/3/24 -->
<div class="clear"></div>
<div class="shop_footer">
    <div class="shop_footer_link">
        <p>
            <a >首页</a>|
            <a >招聘英才</a>|
            <a >广告合作</a>|
            <a >关于ShopCZ</a>|
            <a >关于我们</a>
        </p>
    </div>
    <div class="shop_footer_copy">
        <p>Copyright 2004-2013 itcast Inc.,All rights reserved.</p>
    </div>
</div>
<!-- Footer End -->

</body>
</html>