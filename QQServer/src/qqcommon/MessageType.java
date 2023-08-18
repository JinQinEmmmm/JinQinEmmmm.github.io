package qqcommon;
/*人生真的很短，且行且惜，且走且悟……*/

//表示消息类型
public interface MessageType {
    //不同的常量表示不同的消息类型
    String MESSAGE_LOGIN_SUCCEED = "1";//表示登录成功
    String MESSAGE_LOGIN_FAIL = "2";//表示登录失败
    String MESSAGE_COMM_MES = "3";//普通信息包
    String MSEEAGE_GET_ONLINE_FRIEND = "4";//要求返回在线用户列表
    String MESSAGE_RET_ONLINE_FRIEND = "5";//返回在线用户列表
    String MESSAGE_CLIOENT_EXIT = "6";//客户端请求退出
}
