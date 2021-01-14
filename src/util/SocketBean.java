package util;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.net.Socket;

public class SocketBean {
    private Socket socket;
    private String threadId;             //线程ID，不显示，不知道有啥用
    private int Id;                      //用户ID，不显示
    private String userName;             //用户名，仅登录
    private String nickName;             //昵称，显示用
    private String loginTime;

    public Socket getSocket() {
        return socket;
    }


    public String getThreadId() {
        return threadId;
    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public SocketBean(Socket socket, String threadId) {
        this.socket = socket;
        this.threadId = threadId;
        this.Id = Integer.valueOf(0);    //0代表空
        this.userName = "";
        this.nickName = "";
        this.loginTime = "";
    }
}
