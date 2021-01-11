/**
 * 数据包 包头标识
 */
public enum Action {

    LoginReq("LoginReq"),
    /**
     * 登录请求
     * Eg.  LoginReq|Account.length()|Account|Password.length()|Password
     */
    LoginResp("LoginResp");
    /**
     * 登录响应
     * Eg.  LoginResp|1
     * 待补充
     */

    private final String ActionStr;

    private Action(String ActionStr) {
        this.ActionStr = ActionStr;
    }
}
