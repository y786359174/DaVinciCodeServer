package util;

import java.util.ArrayList;

public class ProcessString {
    /**
     * 将数据，分隔符|和数据长度拼接成需要发送的字符串
     * @param oldstr
     * 包头指令
     * @param addstrs
     * 将要发送的数据（们）
     * @return
     */
    public static String addstr(String oldstr, String... addstrs) {
        String newstr = oldstr;
        for (int i = 0; i < addstrs.length; i++) {
            newstr = newstr + "|" + addstrs[i].length() + "|" + addstrs[i];
        }
        return newstr;
    }
    /**
     * 解出接收到的数据
     * @param str
     * @return 返回String数组
     * @throws Exception
     */
    public static String[] splitstr(String str) throws Exception {
//        String[] splitContent = content.split("\\|");   //分割成Action |length|data ...
        ArrayList<String> newlist = new ArrayList();
        int ch = str.indexOf("|");
        if (0 > ch) {
            newlist.add(str);
        }
        else
        {
            newlist.add(str.substring(0, ch));
            str = str.substring(ch + 1);
            while (true) {
                ch = str.indexOf("|");
                int strlength = Integer.valueOf(str.substring(0, ch)).intValue();
                str = str.substring(ch + 1);
                newlist.add(str.substring(0, strlength));
                if (str.length() > strlength) {
                    str = str.substring(strlength + 1);
                } else {
                    break;
                }
            }
        }
        String[] newstr = new String[newlist.size()];
        for (int i = 0; i < newstr.length; i++)
            newstr[i] = newlist.get(i);
        return newstr;
    }
}
