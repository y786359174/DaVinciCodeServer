package util;

/**
 * 各种标识
 * 枚举不会用，就存一下数据格式了
 */
public class Action {
    /*************************LoginActivity****************************/
    public static final String SOCKETRCV_login="Socketrcv_login";
    /**
     * 广播Action
     * 发送给LoginActivity
     */

    public static final String LoginReq = "LoginReq";
    /**
     * 登录请求
     * Eg.  LoginReq|Account|Password
     */
    public static final String LoginResp = "LoginResp";
    /**
     * 登录响应
     * Eg.  LoginResp|loginState|id|nickname
     * Eg.  LoginResp|0|id|nickname     //登陆成功
     * Eg.  LoginResp|1                 //账号不存在
     * Eg.  LoginResp|2                 //密码错误
     */
    /*************************RegisterActivity****************************/
    public static final String SOCKETRCV_register="SOCKETRCV_register";
    /**
     * 广播Action
     * 发送给LoginActivity
     */

    public static final String RegisterReq = "RegisterReq";
    /**
     * 注册请求
     * Eg.  RegisterReq|Nicknamestr|Account|Password
     */
    public static final String RegisterResp = "RegisterResp";
    /**
     * 注册响应
     * Eg.  RegisterResp|registerState
     * Eg.  LoginResp|0                 //注册成功
     * Eg.  LoginResp|1                 //账号重复
     * Eg.  LoginResp|2                 //未知的错误
     */
    /*************************GameActivity****************************/
    public static final String SOCKETRCV_game="SOCKETRCV_game";
    /**
     * 广播Action
     * 发送给GameActivity
     */

    public static final String SpeakOutReq = "SpeakOutReq";
    /**
     * 大厅发送消息请求
     * Eg.  SpeakOutReq|NickName|SpeakOutMsg
     */
    public static final String SpeakOutResp = "SpeakOutResp";
    /**
     * 大厅接收消息响应
     * Eg.  SpeakOutResp|NickName|SpeakOutMsg
     */
    /*************************FriendActivity****************************/
    public static final String SOCKETRCV_friend="SOCKETRCV_friend";
    /**
     * 广播Action
     * 发送给FriendActivity
     */

    public static final String GetFriendListReq = "GetFriendListReq";
    /**
     * 获取好友列表请求
     * Eg.  GetFriendListReq
     */
    public static final String GetFriendListResp = "GetFriendListResp";
    /**
     * 获取好友列表响应
     * Eg.  GetFriendListResp|ID|NickName|...
     */

    public static final String DeleteFriendListReq = "DeleteFriendListReq";
    /**
     * 获取好友列表请求
     * Eg.  GetFriendListReq
     */
    public static final String DeleteFriendListResp = "DeleteFriendListResp";
    /**
     * 获取好友列表响应
     * Eg.  GetFriendListResp|ID|NickName|...
     */



}
