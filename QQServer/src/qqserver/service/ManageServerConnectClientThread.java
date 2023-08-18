package qqserver.service;/*人生真的很短，且行且惜，且走且悟……*/

import java.util.HashMap;
import java.util.Iterator;

//该类管理和用户通信的线程
public class ManageServerConnectClientThread {
    private static HashMap<String, ServerConnectClientThread> hm = new HashMap<>();

    //添加线程对象到 hm 集合
    public static void addServerConnectClientThread(String userId, ServerConnectClientThread serverConnectClientThread) {
        hm.put(userId, serverConnectClientThread);
    }

    //根据 userId 返回 ServerConnectClientThread 线程
    public static ServerConnectClientThread getServerConnectClientThread(String userId) {
        return hm.get(userId);
    }

    //这里编写方法 可以返回在线用户列表
    public static String getOnlineUser() {
        //集合遍历 遍历 hm 中的 key
        Iterator<String> iterator = hm.keySet().iterator();
        String onlineUserList = "";
        while (iterator.hasNext()) {
            onlineUserList += iterator.next().toString() + " ";
        }
        return onlineUserList;
    }

    //根据 userId 删除 ServerConnectClientThread 线程
    public static void removeServerConnectClientThread(String userId) {
        hm.remove(userId);
    }
}
