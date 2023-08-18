package qqclient.service;/*人生真的很短，且行且惜，且走且悟……*/

import qqcommon.Message;
import qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

//该类提供和消息相关的服务方法
public class MessageClientService {
    public void sendMessageTo(String content, String senderId, String getterId) {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_COMM_MES);//普通的聊天消息
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setContent(content);
        message.setSendTime(new Date().toString());
        System.out.println("你 对 " + getterId + " 说: " + content);
        //发送给服务端
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
