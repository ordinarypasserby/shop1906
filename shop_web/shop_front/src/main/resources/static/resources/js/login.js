$.ajax({
    type: "POST",
    url: "http://localhost:16666/sso/isLogin",
    success: function(data){
        if(data.code == "0000"){
            //已经登录
            $("#pid").html(data.data.nickname + "您好，欢迎来到<b>ShopCZ商城</b>  <a href='/sso/logout'>注销</a>");
            //连接websocket
            wsInit(data.data);

        } else {
            //未登录
            $("#pid").html(
                "[<a onclick=\"mylogin();\">登录</a>]" +
                "[<a href=\"http://localhost:16666/sso/toRegister\">注册</a>]");
        }
    },
    dataType: "json"
});

function mylogin(){

    //获得当前页面的请求路径
    var returnUrl = location.href;

    //对URL进行编码
    returnUrl = encodeURIComponent(returnUrl);

    //跳转到登录页
    location.href = "http://localhost:16666/sso/toLogin?returnUrl=" + returnUrl;
}

/**
 * 初始化websocket连接
 */
var ws;
function wsInit(user){
    //通过js连接WebSocket服务器
    if (window.WebSocket){
        //连接WebSocket服务器
        ws = new WebSocket("ws://127.0.0.1:26666/");
        //初始化
        ws.onopen = function () {
            console.log("已经连接上服务器"+user.id)
            //将当前的客户端信息发送给netty
            ws.send("{'msgType':1, 'data':" + user.id + "}");

            //开启心跳
            heart();

            //定时关闭连接
            closeConn();
        }

        //断开连接
        ws.onclose = function () {
            console.log("断开了和服务器的连接")
            //关闭心跳
            clearTimeout(heartTimeout);

            //进行重连
            reConn(user);
        }

        //接收消息
        ws.onmessage = function (msg) {
            console.log("接收到服务器的消息"+msg.data);
            //将json字符串转换成json对象
            var msgObj = JSON.parse(msg.data);
            //判断类型
            if (msgObj.msgType == 2){
                //说明是一个心跳回复
                //关闭定时器
                clearTimeout(closeTimeout);
                //重新打开定时器
                closeConn();
            }
        }
    }else {
        alert("浏览器版本过低，消息提示功能可能受限")
    }
}

/**
 * 心跳机制
 */
var heartTimeout;
function heart() {
    ws.send("{'msgType':2}");// - 表示心跳信息

    heartTimeout = setTimeout(function () {
        //每过10秒发送一个心跳
        heart();
    },10000)
}

/**
 * 关闭连接
 */
var closeTimeout;
function closeConn() {
    closeTimeout = setTimeout(function () {
        //断开和服务器的连接
        ws.close();
    },30000)
}

/**
 * 重连
 */
function reConn(user) {
    setTimeout(function () {
        console.log("开始进行重连")
        //进行重连
        wsInit(user);
    },10000)

}