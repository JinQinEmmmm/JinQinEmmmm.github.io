package qqclient.service;/*人生真的很短，且行且惜，且走且悟……*/

import qqcommon.Message;
import qqcommon.MessageType;
import qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

//该类完成用户登录验证和用户注册等功能
public class UserClientService {
    //因为我们可能在其他地方使用user信息，因此做出成员属性
    private User u = new User();
    //因为我们可能在其他地方使用socket信息，因此做出成员属性
    Socket socket;

    public boolean checkUser(String userId, String pwd) {
        boolean b = false;
        //创建User对象
        u.setUserId(userId);
        u.setPassword(pwd);

        //连接到服务端 发送u对象
        try {
            socket = new Socket(InetAddress.getByName("192.168.0.102"), 9999);
            //得到ObjectOutputStream对象
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(u);//发送User对象

            //读取从服务器返回的Message对象
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message ms = (Message)ois.readObject();

            if (ms.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)){//登录成功
                //创建一个和服务器端保持通信的线程 -> ClientConnectServerThread
                ClientConnectServerThread clientConnectServerThread = new ClientConnectServerThread(socket);
                //启动线程
                clientConnectServerThread.start();

                //为了方便后面客户端的扩展 我们将线程放入集合管理
                ManageClientConnectServerThread.addClientConnectServerThread(userId,clientConnectServerThread);

                b = true;
            }else {
                //如果登录失败 我们就不能启动和服务器通信的线程 关闭socket
                socket.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return b;
    }

    //向服务器端请求在线用户列表
    public void onlineFriendList(){
        //发送一个Message 类型 MSEEAGE_GET_ONLINE_FRIEND
        Message message = new Message();
        message.setMesType(MessageType.MSEEAGE_GET_ONLINE_FRIEND);
        message.setSender(u.getUserId());
        //发送给服务器
        try {
            //得到当前线程的 socket 对应的 ObjectOutputStream
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(u.getUserId()).getSocket().getOutputStream());
            //发送一个Message对象 向服务器端请求在线用户列表
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //编写方法 退出客户端程序
    public void logout(){
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_CLIOENT_EXIT);
        message.setSender(u.getUserId());//一定要指定是哪个客户端Id

        //发送message
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
            System.out.println(u.getUserId() + "退出系统");
            System.exit(0);//结束进程
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
