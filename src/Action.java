/**
 * 数据包 包头标识
 */
public enum Action {

    LoginReq("LoginReq"),
    /**
     * 登录请求
     * Eg.  LoginReq|Account|Password
     */
    LoginResp("LoginResp");
    /**
     * 登录响应
     * Eg.  LoginResp|1                 //账号不存在
     * Eg.  LoginResp|2                 //密码错误
     * Eg.  LoginResp|0|id|nickname     //登陆成功
     * 待补充
     */

    private final String ActionStr;

    Action(String ActionStr) {
        this.ActionStr = ActionStr;
    }
}
