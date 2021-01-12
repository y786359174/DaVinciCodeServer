import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

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
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();  //获取本机ip
            SOCKET_IP=inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            ServerSocket server = new ServerSocket(SOCKET_PORT);
            System.out.println("Socket Server Open\nip="+SOCKET_IP+"\nport="+SOCKET_PORT);
            while (true) {
                SocketBean socketBean = new SocketBean(server.accept(),DateUtil.getTimeId());      //阻塞等待客户端申请链接，并同意
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

        for(int i = 0; i < mSocketList.size(); ++i) {
            SocketBean socketBean = (SocketBean)(mSocketList.get(i));
            if (otheruserId == socketBean.userId) {
                try {
                    OutputStream mWriter = null;
                    mWriter = socketBean.socket.getOutputStream();      //创建数据发送器
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

        /**
         * ServerThread类构造器
         * 创建该线程时执行，视为初始化线程
         */
        public ServerThread(SocketBean socketBean) throws IOException {
            mSocket = socketBean.socket;
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
                    if (rcvstrs[0].equals("LoginReq") )                        //登录请求
                    {
                        int loginstate = 0;
                        UserBean userBean = sqlconn.selectUser(rcvstrs[1]);         //获取登录名的userbean
                        if(userBean!=null)                                          //存在该username
                        {
                            if(userBean.getPassWord().equals(rcvstrs[2]))                //密码相等
                            {
                                loginstate = 0;
                                msgstr = ProcessString.addstr("LoginResp",Integer.toString(loginstate),Long.toString(userBean.getId()),userBean.getNickName());
                            }else{                                                  //密码不等
                                loginstate = 2;
                                msgstr = ProcessString.addstr("LoginResp",Integer.toString(loginstate));
                            }
                        }
                        else
                        {
                            loginstate = 1;
                            msgstr = ProcessString.addstr("LoginResp",Integer.toString(loginstate));
                        }
                            sendMsg(msgstr);
                    }


                    /******************************数据处理完毕，等待下次接收数据*************************/
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("receive message failed");
            }
        }
    }
}