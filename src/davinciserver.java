import java.net.InetAddress;
import java.net.UnknownHostException;

public class davinciserver {

    public static void main(String[] args) {  //主函数下
        TransmitMsg transmitMsg=new TransmitMsg();

        /**待加入共享参数初始化*/

        MySqlUtil conn = new MySqlUtil();
        conn.getConn();
        transmitMsg.setSqlcon(conn);

        transmitMsg.openServer();        //阻塞接收，最后启动
    }
}
