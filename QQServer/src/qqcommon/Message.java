package qqcommon;
/*人生真的很短，且行且惜，且走且悟……*/

import java.io.Serializable;

//表示客户端和服务端通信时的消息对象
public class Message implements Serializable {//继承接口 实现序列化
    private static final long serialVersionUID = 1L;//增强兼容性
    private String sender;//消息发送方
    private String getter;//消息接收方
    private String content;//消息内容
    private String sendTime;//发送时间
    private String mesType;//消息类型[可以在接口中定义消息类型]

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getMesType() {
        return mesType;
    }

    public void setMesType(String mesType) {
        this.mesType = mesType;
    }
}
