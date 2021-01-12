import java.sql.*;

class MySqlUtil{
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
                userBean.setId(rs.getLong("n_id"));//rs.getInt(1)，获取表第一列
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