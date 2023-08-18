package qqserver.service;/*人生真的很短，且行且惜，且走且悟……*/

import qqcommon.Message;
import qqcommon.MessageType;
import qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class QQServer {
    private ServerSocket ss = null;
    private static HashMap<String,User> validUsers = new HashMap<>();
    //再静态代码块 初始化 validUser
    static {
        validUsers.put("蔡文姬",new User("蔡文姬","123456"));
        validUsers.put("庄周",new User("庄周","123456"));
        validUsers.put("貂蝉",new User("貂蝉","123456"));
        validUsers.put("李元芳",new User("李元芳","123456"));
        validUsers.put("狄仁杰",new User("狄仁杰","123456"));
        validUsers.put("武则天",new User("武则天","123456"));
    }
    //验证用户是否有效的方法
    private boolean checkUser(String userId,String password){
        User user = validUsers.get(userId);
        //说明userId没有存在 validUser key 中
        if (user == null){
            return false;
        }
        //userId正确 但是密码错误
        if (!user.getPassword().equals(password)){
            return false;
        }
        return true;
    }
    public QQServer(){
        System.out.println("服务器在9999端口监听...");
        try {
            ss = new ServerSocket(9999);
            while (true){//当和某个客户端连接之后 会继续监听 因此 while
                Socket socket = ss.accept();
                //得到socket关联的对象输入流
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                User u = (User)ois.readObject();
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                Message message = new Message();
                //验证
                if (checkUser(u.getUserId(), u.getPassword())){//登录通过
                    message.setMesType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    oos.writeObject(message);
                    //创建一个线程 和客户端保持通信 该线程需要持有socket对象
                    ServerConnectClientThread serverConnectClientThread = new ServerConnectClientThread(socket,u.getUserId());
                    //启动线程
                    serverConnectClientThread.start();
                    //把该线程对象 放入到一个集合中 进行管理
                    ManageServerConnectClientThread.addServerConnectClientThread(u.getUserId(), serverConnectClientThread);
                }else {//登录失败
                    message.setMesType(MessageType.MESSAGE_LOGIN_FAIL);
                    oos.writeObject(message);
                    socket.close();
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            //如果服务器退出了 while 说明服务器不在监听 因此关闭 ServerSocket
            try {
                ss.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
