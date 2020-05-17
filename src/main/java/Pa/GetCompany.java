package Pa;

import Pojo.Companies;
import Util.ConnectionUtil;
import Util.ProxyPool;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetCompany {
    public static Companies getCompany(String id) {
        Connection connection = null;
        Document doc = null;
        Element body = null;
        while (true) {
            try {
                connection = ConnectionUtil.getConnection("https://www.52wmb.com/buyer/" + id);
                if (ProxyPool.proxy == null) {
                    ProxyPool.getProxy();
                }
                connection.proxy(ProxyPool.proxy.get("ip"), Integer.parseInt(ProxyPool.proxy.get("port")));
                doc = connection.get();
                body = doc.body();
                //请求太繁忙
                if (body.select("h1").text().equals("503 Service Temporarily Unavailable")) {
                    Thread.sleep(5000);
                    continue;
                }
                //页面不存在
                String is404 = body.select("#err-moudle > div > div.right-content > div.top > span").text();
                if (is404.equals("网页被删除了哦！")) {
                    break;
                }
            } catch (Exception e) {
                ProxyPool.proxy = null;
                continue;
            }

            String isContact = isContact(body);

            String country = body.select("#company_name").attr("data-country");
            String name = body.select("#company_name").attr("data-company");
            String date = body.select("#company_name").attr("data-end-time");
            String num = body.select("#company_name").attr("data-total-count");
            Companies companies = new Companies(id, '0', country, name, date, num, isContact);
            System.out.println(companies.toString());
            return companies;
        }

        while (true) {
            try {
                connection = ConnectionUtil.getConnection("https://www.52wmb.com/supplier/" + id);
                if (ProxyPool.proxy == null) {
                    ProxyPool.getProxy();
                }
                connection.proxy(ProxyPool.proxy.get("ip"), Integer.parseInt(ProxyPool.proxy.get("port")));
                doc = connection.get();
                body = doc.body();
                //请求太繁忙
                if (body.select("h1").text().equals("503 Service Temporarily Unavailable")) {
                    Thread.sleep(5000);
                    continue;
                }
                //页面不存在
                String is404 = body.select("#err-moudle > div > div.right-content > div.top > span").text();
                if (is404.equals("网页被删除了哦！")) {
                    break;
                }
            } catch (Exception e) {
                ProxyPool.proxy = null;
                continue;
            }

            String isContact = isContact(body);

            String country = body.select("#company_name").attr("data-country");
            String name = body.select("#company_name").attr("data-company");
            String date = body.select("#company_name").attr("data-end-time");
            String num = body.select("#company_name").attr("data-total-count");
            Companies companies = new Companies(id, '1', country, name, date, num, isContact);
            System.out.println(companies.toString());
            return companies;
        }

        return null;
    }

    private static String isContact(Element body) {
        String str = body.getElementsByClass("stab-contact").get(0).attr("style");
        if (str == null || str.equals("display: none")) {
            return "0";
        } else {
            return "1";
        }
    }
}
