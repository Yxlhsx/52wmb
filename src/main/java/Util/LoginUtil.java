package Util;

import Util.TxtUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginUtil {
    public static String cookie = TxtUtil.readTxt();

    public static String login() throws IOException {
        if (cookie == null) {
            Map<String, String> paramdata = new HashMap<String, String>();
            paramdata.put("signusername", "");
            paramdata.put("signpassword", "");
            Connection.Response res = Jsoup.connect("https://www.52wmb.com/signin")
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
                    .data(paramdata)//
                    .method(Connection.Method.POST)
                    .timeout(10000).ignoreContentType(true)
                    .execute();
            Map<String, String> map = res.cookies();

            cookie = "access_token=" + map.get("access_token") + ";52BY_TOKEN=" + map.get("52BY_TOKEN") + ";_QUY=" + map.get("_QUY") + ";";
            TxtUtil.writeTxt(cookie);
        }

        return cookie;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(login());
    }
}
