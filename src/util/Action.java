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

    public static final String DeleteFriendReq = "DeleteFriendReq";
    /**
     * 删除好友请求
     * Eg.  DeleteFriendReq|ID
     */
    public static final String DeleteFriendResp = "DeleteFriendResp";
    /**
     * 删除好友响应
     * Eg.  DeleteFriendResp|deleteState
     */

    public static final String AddFriendReq = "AddFriendReq";
    /**
     * 添加好友请求
     * Eg.  AddFriendReq|ID/NickName
     */
    public static final String AddFriendResp = "AddFriendResp";
    /**
     * 添加好友响应
     * Eg.  AddFriendResp|addFriendState|NickName
     * 0申请成功，1没有这个人，2已经是好友了，3直接添加上了，4未知的错误
     */

    public static final String GetApplyListReq = "GetApplyListReq";
    /**
     * 获取申请列表请求
     * Eg.  GetApplyListReq
     */
    public static final String GetApplyListResp = "GetApplyListResp";
    /**
     * 获取申请列表响应
     * Eg.  GetApplyListResp|ID|NickName|...
     */

    public static final String ApplyFriendReq = "ApplyFriendReq";
    /**
     * 操作申请好友请求（同意/拒绝）
     * Eg.  ApplyFriendReq|ID|0/1
     */
    public static final String ApplyFriendResp = "ApplyFriendResp";
    /**
     * 操作申请好友响应（同意/拒绝）
     * Eg.  ApplyFriendResp|applyfriendstate

     */
}
