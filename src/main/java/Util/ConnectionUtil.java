package Util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Map;

/**
 * 封装Connection头部信息工具类
 */
public class ConnectionUtil {
    public static Connection getConnection(String url) throws IOException {
        Connection connection = Jsoup.connect(url);
        connection.timeout(8000);
        connection.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        connection.header("Accept-Encoding", "gzip, deflate, br");
        connection.header("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
        connection.header("Cache-Control", "max-age=0");
        connection.header("Connection", "keep-alive");
        connection.header("Sec-Fetch-Dest", "document");
        connection.header("Sec-Fetch-Mode", "navigate");
        connection.header("Sec-Fetch-Site", "same-origin");
        connection.header("User-Agent", UserAgentPool.getUserAgent());
        connection.header("Cookie", LoginUtil.login());
        connection.ignoreContentType(true);//忽略ContextType的检查
        connection.ignoreHttpErrors(true);//忽略错误

        return connection;
    }
}
