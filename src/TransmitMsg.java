import util.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.util.ArrayList;
import util.*;
import static util.Action.*;

/** TransmitMsg
 *  用于连接Socket及处理接收信息
 *  void openserver 打开服务器socket，阻塞接受连接申请
 *
 *  待实现
 *  指定id后使用指定线程发送msgstr
 *
 *
 */


public class TransmitMsg {
    private static String SOCKET_IP = null;
    private static final int SOCKET_PORT = 826;
    public static ArrayList<SocketBean> mSocketList = new ArrayList();
    private MySqlUtil sqlconn = null;

    /**
     * openServer
     * 打开服务器并阻塞检测申请
     */
    public void openServer() {
//        try {
//            InetAddress inetAddress = InetAddress.getLocalHost();  //获取本机ip
//            SOCKET_IP=inetAddress.getHostAddress();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
        SOCKET_IP = CustomSystemUtil.INTERNET_IP;
        try {
            ServerSocket server = new ServerSocket(SOCKET_PORT);
            System.out.println("Socket Server Open\nip="+SOCKET_IP+"\nport="+SOCKET_PORT);
            while (true) {
                SocketBean socketBean = new SocketBean(server.accept(), DateUtil.getTimeId());      //阻塞等待客户端申请链接，并同意
                mSocketList.add(socketBean);
                new Thread(new TransmitMsg.ServerThread(socketBean)).start();                       //新建线程用于该信号
                System.out.println("server connection succeed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("server connection failed");
        }
    }

    /**
     * CrossThreadSendMsg 跨线程调用socket发送信息
     * @param otheruserId
     * 调用的socketbean的id号
     * @param msgstr
     * 需要发送的数据
     */
    private void CrossThreadSendMsg(int otheruserId,String msgstr)
    {
        msgstr=msgstr+"\n";
        for(int i = 0; i < mSocketList.size(); ++i) {
            SocketBean socketBean = (SocketBean)(mSocketList.get(i));
            if (otheruserId == socketBean.getId()) {
                try {
                    OutputStream mWriter = null;
                    mWriter = socketBean.getSocket().getOutputStream();      //创建数据发送器
                    mWriter.write(msgstr.getBytes("utf8"));
                    System.out.println("send message:"+msgstr);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("send message failed");
                }
                break;
            }
        }
    }

    /**
     * CrossThreadAllSendMsg 跨线程调用socket给所有在线发送信息
     * @param msgstr
     * 需要发送的数据
     */
    private void CrossThreadSendMsg(String msgstr)
    {
        msgstr=msgstr+"\n";
        for(int i = 0; i < mSocketList.size(); ++i) {
            SocketBean socketBean = (SocketBean)(mSocketList.get(i));
            try {
                OutputStream mWriter = null;
                mWriter = socketBean.getSocket().getOutputStream();      //创建数据发送器
                mWriter.write(msgstr.getBytes("utf8"));

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("send message failed");
            }
        }
        System.out.println("send message:"+msgstr);
    }

    /**
     * 设置连接的mysql connection
     * @param conn
     * 自定义MySqlUtil类
     */
    public void setSqlcon(MySqlUtil conn){sqlconn=conn;}

    /**
     * serverThread类
     * Socket成功连接后新建该类并放入线程中运行
     */

    private class ServerThread implements Runnable {
        private Socket mSocket;
        private BufferedReader mReader;
        private SocketBean socketBean = null;
        /**
         * ServerThread类构造器
         * 创建该线程时执行，视为初始化线程
         */
        public ServerThread(SocketBean socketBean) throws IOException {
            this.socketBean = socketBean;
            mSocket = socketBean.getSocket();
            mReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream(),"utf-8"));  //创建数据流接收器
        }

        /**
         * ServerThread类sendMsg
         * sendMsg 调用socket发送信息
         * @param msgstr
         * 需要发送的数据
         */
        private void sendMsg(String msgstr)
        {
            msgstr=msgstr+"\n";
            try{
                OutputStream mWriter = null;
                mWriter = mSocket.getOutputStream();      //创建数据发送器
                mWriter.write(msgstr.getBytes("utf8"));
                System.out.println("send message:"+msgstr);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("send message failed");
            }
        }

        /**
         * ServerThread类run
         * 该线程.start时调用
         * 在openserver中创建后直接调用且仅调用一次。
         */
        @Override
        public void run() {
            try {
                String content = null;
                String msgstr = null;
                while ((content = mReader.readLine()) != null) {
                    /******************************提取数据串*********************************/
                    String[] rcvstrs;
                    try{
                        rcvstrs = ProcessString.splitstr(content);
                        for(String str : rcvstrs)
                        {
                            System.out.println(str);
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                        System.out.println("receive wrong");
                        continue;
                    }

                    /******************************接收到数据后进行处理*********************************/
                    /******************************登录**********************************************/
                    if (rcvstrs[0].equals(LoginReq) )                        //登录请求
                    {
                        String loginstate = "0";
                        UserBean userBean = sqlconn.selectUser(rcvstrs[1]);         //获取登录名的userbean
                        if(userBean!=null)                                          //存在该username
                        {
                            if(userBean.getPassWord().equals(rcvstrs[2]))                //密码相等
                            {
                                loginstate = "0";
                                msgstr = ProcessString.addstr(LoginResp,loginstate,Integer.toString(userBean.getId()),userBean.getNickName());
                                socketBean.setId(userBean.getId());
                                socketBean.setNickName(userBean.getNickName());
                                socketBean.setUserName(userBean.getUserName());
                                socketBean.setLoginTime(DateUtil.getNowTime());
                            }else{                                                  //密码不等
                                loginstate = "2";
                                msgstr = ProcessString.addstr(LoginResp,loginstate);
                            }
                        }
                        else
                        {
                            loginstate = "1";
                            msgstr = ProcessString.addstr(LoginResp,loginstate);
                        }
                            sendMsg(msgstr);
                    }
                    /******************************注册**********************************************/
                    if(rcvstrs[0].equals(RegisterReq))
                    {
                        String registerstate = "0";
                        UserBean userBean1 = sqlconn.selectUser(rcvstrs[2]);         //获取登录名的userbean
                        if(userBean1!=null)                                          //存在该username
                        {
                            registerstate = "1";
                        }else{
                            UserBean userBean = new UserBean();
                            userBean.setNickName(rcvstrs[1]);
                            userBean.setUserName(rcvstrs[2]);
                            userBean.setPassWord(rcvstrs[3]);
                            if(1==sqlconn.insertUser(userBean))
                            {
                                registerstate = "0";                                //成功
                            }else
                            {
                                registerstate = "2";                                    //未知的错误
                            }

                        }
                        msgstr = ProcessString.addstr(RegisterResp,registerstate);
                        sendMsg(msgstr);
                    }
                    /******************************大厅发送消息*****************************************/
                    if(rcvstrs[0].equals(SpeakOutReq))
                    {
                        msgstr = ProcessString.addstr(SpeakOutResp,rcvstrs[1],rcvstrs[2]);
                        CrossThreadSendMsg(msgstr);
                    }
                    /******************************大厅发送消息*****************************************/
                    if(rcvstrs[0].equals(GetFriendListReq))
                    {
                        ArrayList<UserBean> userBeanList = sqlconn.selectUserUser(socketBean.getId());
                        msgstr = GetFriendListResp;
                        for(int i = 0;i<userBeanList.size();i++)
                        {
                            msgstr = ProcessString.addstr(msgstr,Integer.toString(userBeanList.get(i).getId()),userBeanList.get(i).getNickName());
                        }
                        sendMsg(msgstr);
                    }
                    /******************************数据处理完毕，等待下次接收数据*************************/
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("receive message failed,close socket thread");
            }
        }
    }
}