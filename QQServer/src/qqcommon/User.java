package qqcommon;

import java.io.Serializable;

public class User implements Serializable {//继承接口 实现序列化
    private static final long serialVersionUID = 1L;//增强兼容性
    private String userId;//用户名
    private String password;//密码

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
