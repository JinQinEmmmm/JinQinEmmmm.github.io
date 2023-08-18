package qqserver.service;/*人生真的很短，且行且惜，且走且悟……*/

import qqcommon.Message;
import qqcommon.MessageType;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

//该类的一个对象和某个客户端保持通信
public class ServerConnectClientThread extends Thread{
    private Socket socket;
    private String userId;//连接到服务端的Id

    public ServerConnectClientThread(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {//这里线程处于run的状态 可以发送 / 接受消息
        while (true){
            try {
                System.out.println("服务器端正在和客户端(" + userId + ")保持通信...");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message)ois.readObject();
                //后面会使用message
                //客户端要在线列表
                if (message.getMesType().equals(MessageType.MSEEAGE_GET_ONLINE_FRIEND)){
                    System.out.println(message.getSender() + "请求一个在线用户列表");
                    String onlineUser = ManageServerConnectClientThread.getOnlineUser();
                    //构建一个 Message 对象返回给客户端
                    Message message2 = new Message();
                    message2.setMesType(MessageType.MESSAGE_RET_ONLINE_FRIEND);
                    message2.setContent(onlineUser);
                    message2.setGetter(message.getSender());
                    //返回给客户端
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(message2);
                } else if (message.getMesType().equals(MessageType.MESSAGE_CLIOENT_EXIT)) {
                    System.out.println(message.getSender() + "退出");
                    ManageServerConnectClientThread.removeServerConnectClientThread(message.getSender());
                    socket.close();//关闭连接
                    //退出线程
                    break;
                } else if (message.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {
                    //根据 message 获取 getterId 然后再得到对应的线程
                    ServerConnectClientThread serverConnectClientThread = ManageServerConnectClientThread.getServerConnectClientThread(message.getGetter());
                    //得到对应的 socket 对象输出流 将 Message 发送给指定的客户端
                    ObjectOutputStream oos = new ObjectOutputStream(serverConnectClientThread.socket.getOutputStream());
                    oos.writeObject(message);//转发 提示 如果用户不在线 可以保存在数据库 这样就可以实现离线留言
                } else {
                    System.out.println("其他类型 message 不做处理");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
