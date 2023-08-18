package qqclient.service;/*人生真的很短，且行且惜，且走且悟……*/

import qqcommon.Message;
import qqcommon.MessageType;

import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConnectServerThread extends Thread {
    private Socket socket;

    //构造器接收一个socket对象
    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }

    //
    @Override
    public void run() {
        while (true) {
            System.out.println("客户端线程 等待读取服务端发送的消息...");
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                //如果服务端没有发送消息 线程会阻塞在这里
                Message message = (Message) ois.readObject();
                //判断 message 的类型 然后做相应的业务处理
                //如果读取到的是 MessageType.MESSAGE_RET_ONLINE_FRIEND 为服务端返回的在线用户列表
                if (message.getMesType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)) {
                    //取出在线列表信息 并显示
                    String[] onlineUser = message.getContent().split(" ");
                    System.out.println("\n=============当前在线用户列表=============");
                    for (int i = 0; i < onlineUser.length; i++) {
                        System.out.println("用户:" + onlineUser[i]);
                    }

                } else if (message.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {
                    //把从服务器转发的消息 显示到控制台即可
                    System.out.println("\n" + message.getSender() + " 对 你 " + "说: " + message.getContent());
                } else {
                    System.out.println("其他类型 不做处理...");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    //为了更方便的得到socket对象
    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
