//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.net.Socket;

public class SocketBean {
    public Socket socket;
    public String threadId;           //线程ID，不显示，不知道有啥用
    public int userId;       //用户ID，不显示
    public String userName;     //用户名，仅登录
    public String nickName;     //昵称，显示用
    public String loginTime;

    public SocketBean(Socket socket,String threadId) {
        this.socket = socket;
        this.threadId = threadId;
        this.userId = 0;    //0代表空
        this.userName = "";
        this.nickName = "";
        this.loginTime = "";
    }
}
