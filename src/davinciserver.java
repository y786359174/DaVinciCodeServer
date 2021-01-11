import java.net.InetAddress;
import java.net.UnknownHostException;

public class davinciserver {

    public static void main(String[] args) {  //主函数下
        /**待加入共享参数初始化*/
        TransmitMsg transmitMsg=new TransmitMsg();
        transmitMsg.openServer();        //阻塞接收，最后启动
    }
}
