package qqclient.view;/*人生真的很短，且行且惜，且走且悟……*/


import qqclient.service.MessageClientService;
import qqclient.service.UserClientService;

import java.util.Scanner;

//客户端的菜单界面
public class QQView {
    private boolean loop = true;//控制是否显示菜单
    Scanner sc = new Scanner(System.in);
    private String key;//接收用户键盘输入
    private UserClientService userClientService = new UserClientService();//对象用于登录服务 / 注册用户

    private MessageClientService messageClientService = new MessageClientService();//对象用户群聊 / 私聊
    public static void main(String[] args) {
        new QQView().mainMenu();
        System.out.println("客户端退出系统...");
    }

    private void mainMenu() {
        while (loop) {
            System.out.println("============= 欢迎登录 =============");
            System.out.println("             1.登录系统             ");
            System.out.println("             9.退出系统             ");
            System.out.print("请输入你的选择:");
            key = sc.next();
            switch (key) {
                case "1":
                    System.out.print("请输入用户号:");
                    String userId = sc.next();
                    System.out.print("请输入密码:");
                    String pwd = sc.next();
                    if (userClientService.checkUser(userId,pwd)) {
                        System.out.println("=============欢迎(用户" + userId + ")登录成功=============");
                        //进入二级菜单
                        while (loop) {
                            System.out.println("\n========网络通讯系统二级菜单( " + userId + ")========");
                            System.out.println("             1. 显示在线用户列表             ");
                            System.out.println("             2. 群发消息                   ");
                            System.out.println("             3. 私聊消息                   ");
                            System.out.println("             4. 发送文件                   ");
                            System.out.println("             9. 退出系统                   ");
                            System.out.print("请输入你的选择:");
                            key = sc.next();
                            System.out.println();
                            switch (key) {
                                case"1":
                                    userClientService.onlineFriendList();
                                    break;
                                case "2":
                                    System.out.println("群发消息");
                                    break;
                                case "3":
                                    System.out.print("请输入想聊天的用户名(在线):");
                                    String getterId = sc.next();
                                    System.out.print("请输入想说的话:");
                                    String content = sc.next();
                                    //编写一个方法 将消息发送给服务端
                                    messageClientService.sendMessageTo(content,userId,getterId);
                                    break;
                                case "4":
                                    System.out.println("发送文件");
                                    break;
                                case "9":
                                    userClientService.logout();
                                    loop = false;
                                    break;
                            }
                        }
                    } else {
                        System.out.println("============= 登录失败 =============");
                        System.out.println();
                    }
                    break;
                case "9":
                    loop = false;
                    break;
            }
        }
    }
}
