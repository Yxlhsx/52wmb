package Util.OCR;

import Util.ConnectionUtil;
import Util.ProxyPool;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;

/**
 * 验证码工具类
 */
public class CodeUtil {
    /**
     * 获取多个验证码的base64
     */
    public static String[] getArrBase64(int count) throws IOException {
        String[] codeSrr = new String[count];
        for (int i = 0; i < codeSrr.length; i++) {
            Connection connection = ConnectionUtil.getConnection("https://www.52wmb.com/async/verify/robot?mark_code=COMPANY-LIST");
            Document doc = connection.get();
            Element body = doc.body();
            Element img = body.getElementById("picture");
            String base64 = img.attr("src").substring(22);
            codeSrr[i] = base64;

        }
        return codeSrr;
    }

    /**
     * 获取验证码Base64。
     */
    public static String getBase64() throws IOException {
        Connection connection = null;
        Document doc = null;
        boolean flag = true;
        while (flag) {
            try {
                connection = ConnectionUtil.getConnection("https://www.52wmb.com/async/verify/robot?mark_code=COMPANY-LIST");
                doc = connection.get();
                if (ProxyPool.proxy == null) {
                    ProxyPool.getProxy();
                }
                connection.proxy(ProxyPool.proxy.get("ip"), Integer.parseInt(ProxyPool.proxy.get("port")));
            } catch (Exception e) {
                ProxyPool.proxy = null;
                continue;
            }
        }
        Element body = doc.body();
        Element img = body.getElementById("picture");
        return img.attr("src").substring(22);
    }

    /**
     * 提交验证码
     */
    public static boolean submitCode(String code) throws InterruptedException, IOException {
        Connection connection = null;
        Document doc = null;
        Element body = null;
        JSONObject object = new JSONObject();
        boolean flag = true;
        while (flag) {
            try {
                connection = ConnectionUtil.getConnection("https://www.52wmb.com/async/verify/robot/submit");
                connection.data("code", code);
                connection.data("mark_code", "COMPANY-LIST");
                if (ProxyPool.proxy == null) {
                    ProxyPool.getProxy();
                }
                connection.proxy(ProxyPool.proxy.get("ip"), Integer.parseInt(ProxyPool.proxy.get("port")));
                doc = connection.post();
                body = doc.body();
                if (body.select("h1").text().equals("503 Service Temporarily Unavailable")) {
                    System.out.println("提交验证码过于频繁");
                    Thread.sleep(1000);
                    continue;
                }
            } catch (Exception e) {
                ProxyPool.proxy = null;
                continue;
            }
            flag = false;
        }
        object = JSONObject.parseObject(body.text());
        if (object.get("state") == (Object) 0) {
            return true;
        }
        return false;
    }

}
