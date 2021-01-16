package util;

import java.sql.*;
import java.util.ArrayList;

public class MySqlUtil{
    private String dbDriver="com.mysql.cj.jdbc.Driver";
    private String dbUrl="jdbc:mysql://101.200.125.165:3306/davinci?useSSL = false&serverTimezone=UTC";//根据实际情况变化
    private String dbUser="root";
    private String dbPass="991225";
    private java.sql.Connection conn=null;
    public java.sql.Connection getConn()
    {
        try
        {
            Class.forName(dbDriver);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        try
        {
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);//注意是三个参数
            System.out.print("mysqlConnection Succeed\n");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.print("mysqlConnection Failed\n");
        }
        return conn;
    }

    /**
     * user表中添加，用于注册
     * @param userBean
     * @return
     */
    public int insertUser(UserBean userBean)
    {
        int i=0;
        String sql="insert into user values(?,?,?,?)";

        try{
            PreparedStatement preStmt =conn.prepareStatement(sql);
            preStmt.setInt(1,0);
            preStmt.setString(2,userBean.getUserName());
            preStmt.setString(3,userBean.getPassWord());
            preStmt.setString(4,userBean.getNickName());
            i=preStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return i;//返回影响的行数，1为执行成功
    }

    /**
     * user表中搜索username寻找player
     * @param userName
     * @return
     */
    public UserBean selectUser(String userName)
    {
        UserBean userBean = null;
        String sql = "select * from user where c_username = '" + userName+"';";
        try
        {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if(rs.next())
            {
                userBean =new UserBean();
                userBean.setId(rs.getInt("n_id"));//rs.getInt(1)，获取表第一列
                userBean.setUserName(rs.getString("c_username"));
                userBean.setNickName(rs.getString("c_nickname"));
                userBean.setPassWord(rs.getString("c_password"));
            }
            //可以将查找到的值写入类，然后返回相应的对象
        }
        catch (SQLException e)
        {
            System.out.println("selectUser wrong");
            e.printStackTrace();
        }
        return userBean;
    }

    /**
     * user表中搜索userid寻找player
     * @param userId
     * @return
     */
    public UserBean selectUser(int userId)
    {
        if(userId<10000000)userId=userId+10000000;
        UserBean userBean = null;
        String sql = "select * from user where n_id = " + userId;
        try
        {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if(rs.next())
            {
                userBean =new UserBean();
                userBean.setId(rs.getInt("n_id"));//rs.getInt(1)，获取表第一列
                userBean.setUserName(rs.getString("c_username"));
                userBean.setNickName(rs.getString("c_nickname"));
                userBean.setPassWord(rs.getString("c_password"));
            }
            //可以将查找到的值写入类，然后返回相应的对象
        }
        catch (SQLException e)
        {
            System.out.println("selectUser wrong");
            e.printStackTrace();
        }
        return userBean;
    }

    /**
     * 添加好友成功，user1<user2因此要内部排序
     * @param user1
     * @param user2
     * @return
     */
    public int insertUserUser(int user1,int user2)
    {
        int useridmin ,useridmax;

        if(user2 < user1 ) {
            useridmin = user2;
            useridmax = user1;
        }
        else if(user1 < user2)
        {
            useridmin = user1;
            useridmax = user2;
        }
        else return 0;
        int i=0;
        String sql="insert into user_user values(?,?,?)";

        try{
            PreparedStatement preStmt =conn.prepareStatement(sql);
            preStmt.setInt(1,0);
            preStmt.setString(2,Integer.toString(useridmin));
            preStmt.setString(3,Integer.toString(useridmax));
            i=preStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return i;//返回影响的行数，1为执行成功
    }

    /**
     * user_user表中搜索userid寻找好友
     * @param userId
     * @return
     */
    public ArrayList<UserBean> selectUserUser(int userId)
    {
        ArrayList<UserBean> userBeanList = new ArrayList();
        String sql1 = "select b.n_id,b.c_nickname from user_user a inner join user b on a.n_user2_id = b.n_id where n_user1_id = "+userId+";";
        String sql2 = "select b.n_id,b.c_nickname from user_user a inner join user b on a.n_user1_id = b.n_id where n_user2_id = "+userId+";";
        try
        {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql1);
            while(rs.next())
            {
                UserBean userBean =new UserBean();
                userBean.setId(rs.getInt("n_id"));//rs.getInt(1)，获取表第一列
                userBean.setNickName(rs.getString("c_nickname"));
                userBeanList.add(userBean);
            }

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql2);
            while(rs.next())
            {
                UserBean userBean =new UserBean();
                userBean.setId(rs.getInt("n_id"));//rs.getInt(1)，获取表第一列
                userBean.setNickName(rs.getString("c_nickname"));
                userBeanList.add(userBean);
            }
            //可以将查找到的值写入类，然后返回相应的对象
        }
        catch (SQLException e)
        {
            System.out.println("selectUserUser1 wrong");
            e.printStackTrace();
        }
        return userBeanList;
    }

    /**
     * 搜索好友表是否有这个好友
     * @param user1
     * @param user2
     * @return 0有，1没有，2未知的错误
     */
    public int selectUserUser(int user1,int user2)
    {
        int useridmin ,useridmax;

        if(user2 < user1 ) {
            useridmin = user2;
            useridmax = user1;
        }
        else if(user1 < user2)
        {
            useridmin = user1;
            useridmax = user2;
        }
        else return 2;
        ArrayList<UserBean> userBeanList = new ArrayList();
        String sql = "select * from user_user where n_user1_id = "+user1+" and n_user2_id = "+user2+";";
        try
        {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next())
                return 0;
            else
                return 1;
            //可以将查找到的值写入类，然后返回相应的对象
        }
        catch (SQLException e)
        {
            System.out.println("selectUserUser2 wrong");
            e.printStackTrace();
            return 1;
        }
    }
    /**
     * user_user表中删除包含user1,user2的值，删除好友，由于好友表user1<user2,所以需要内部排序
     * @param user1
     * @param user2
     * @return
     */
    public int deleteUserUser(int user1,int user2)
    {
        int useridmin ,useridmax;

        if(user2 < user1 ) {
            useridmin = user2;
            useridmax = user1;
        }
        else if(user1 < user2)
        {
            useridmin = user1;
            useridmax = user2;
        }
        else return 0;
        String sql = "delete from user_user where n_user1_id = " + useridmin  +   " and n_user2_id = " + useridmax ;
        System.out.println(sql);
        int i=0;
        Connection conn = getConn();//此处为通过自己写的方法getConn()获得连接
        try
        {
            Statement stmt = conn.createStatement();
            i = stmt.executeUpdate(sql);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return i;//如果返回的是1，则执行成功;
    }

    /**
     * user_user_agree表中添加useradd_id申请userwait_id
     * @param useradd_id
     * @param userwait_id
     * @return
     */
    public int insertUserUserAgree(int useradd_id,int userwait_id)
    {
        int i=0;
        String sql="insert into user_user_agree values(?,?,?)";

        try{
            PreparedStatement preStmt =conn.prepareStatement(sql);
            preStmt.setInt(1,0);
            preStmt.setString(2,Integer.toString(useradd_id));
            preStmt.setString(3,Integer.toString(userwait_id));
            i=preStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return i;//返回影响的行数，1为执行成功
    }

    /**
     * 获取申请列表因为我永远是被申请者，因此我的ID是第二个
     * @param userId
     * @return
     */
    public ArrayList<UserBean> selectUserUserAgree(int userId)
    {
        ArrayList<UserBean> userBeanList = new ArrayList();
        String sql = "select b.n_id,b.c_nickname from user_user_agree a inner join user b on a.n_user1_id = b.n_id where n_user2_id = "+userId+";";
        try
        {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                UserBean userBean =new UserBean();
                userBean.setId(rs.getInt("n_id"));//rs.getInt(1)，获取表第一列
                userBean.setNickName(rs.getString("c_nickname"));
                userBeanList.add(userBean);
            }
            //可以将查找到的值写入类，然后返回相应的对象
        }
        catch (SQLException e)
        {
            System.out.println("selectUser wrong");
            e.printStackTrace();
        }
        return userBeanList;
    }

    /**
     * 检索userother是否申请我为好友
     * 因为我永远是被申请者，因此我的ID是第二个
     * @param userme
     * @param userother
     * @return 0申请过，1没有
     */
    public int selectUserUserAgree(int userme,int userother)
    {
        String sql = "select * from user_user_agree where n_user1_id = "+userother+" and n_user2_id = "+userme+";";
        try
        {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next())
                return 0;
            else
                return 1;
            //可以将查找到的值写入类，然后返回相应的对象
        }
        catch (SQLException e)
        {
            System.out.println("selectUserUser2 wrong");
            e.printStackTrace();
            return 1;
        }
    }
    /**
     * 清除申请好友
     * @param userme
     * @param userother
     * @return
     */
    public int deleteUserUserAgree(int userme,int userother)
    {
        String sql = "delete from user_user_agree where n_user1_id = " + userother +   " and n_user2_id = " + userme ;
        System.out.println(sql);
        int i=0;
        Connection conn = getConn();//此处为通过自己写的方法getConn()获得连接
        try
        {
            Statement stmt = conn.createStatement();
            i = stmt.executeUpdate(sql);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return i;//如果返回的是1，则执行成功;
    }
    /*
    public int insert()
    {
        int i=0;
        String sql="insert into (表名)(列名1,列明2) values(?,?)";
        Connection cnn=getConn();

        try{
            PreparedStatement preStmt =cnn.prepareStement(sql);
            preStmt.setString(1,值);
            preStmt.setString(2,值);//或者：preStmt.setInt(1,值);
            i=preStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return i;//返回影响的行数，1为执行成功
    }
    public int update
    {
        int i=0;
        String sql="update (表名) set  (列名1)=?,列明2=? where (列名)=？";//注意要有where条件
        Connection cnn=getConn();


        try{
            PreparedStatement preStmt =cnn.prepareStatement(sql);
            preStmt.setString(1,(值));
            preStmt.setString(2,(值));//或者：preStmt.setInt(1,值);
            preStmt.setInt(3,(值));
            i=preStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return i;//返回影响的行数，1为执行成功
    }

    public String select
    {
        String sql = "select * from (表名) where (列名)=(值)";
        Connection cnn = getConn();//此处为通过自己写的方法getConn()获得连接
        try
        {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if(rs.next())
            {
                int m1 = rs.getInt(1);//或者为rs.getString(1)，根据数据库中列的值类型确定，参数为第一列
                String m2 = rs.getString(2);
            }
            //可以将查找到的值写入类，然后返回相应的对象
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return (相应的值的变量);
    }

    public int delete()
    {
        String sql = "delete from (表名) where (列名)=(值)";
        int i=0;
        Connection conn = getConn();//此处为通过自己写的方法getConn()获得连接
        try
        {
            Statement stmt = conn.createStatement();
            i = stmt.executeUpdate(sql);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return i;//如果返回的是1，则执行成功;
    }
     */
}