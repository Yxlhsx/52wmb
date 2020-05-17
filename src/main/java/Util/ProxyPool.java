package Util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

/**
 * 代理ip池
 */
public class ProxyPool {
    private static List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    public static Map<String, String> proxy = null;
    private static int num = 0;//计数标识

    public static void getProxy() throws IOException {
        //如果list为空，或者ip池使用了30次，就更新一次ip池
        if (list.size() == 0 || num >= 14) {
            list.clear();
            Connection connection = Jsoup.connect("https://ip.jiangxianli.com/");
            Document doc = connection.get();
            Element body = doc.body();
            Elements trs = body.select("body > div.layui-layout.layui-layout-admin > div.layui-row > div.layui-col-md9.ip-tables > div.layui-form > table > tbody > tr");
            for (Element tr : trs) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("ip", tr.select("td:nth-child(1)").text());
                map.put("port", tr.select("td:nth-child(2)").text());
                list.add(map);
            }
            System.out.println("更新ip代理池！");
            num = 0;
        }

        proxy = list.get(num++);
    }

    public static void main(String[] args) throws IOException {
        getProxy();
        System.out.println(proxy);
    }

}
