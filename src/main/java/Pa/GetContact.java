package Pa;

import Dao.CompanieDao;
import Dao.ContactDao;
import Pojo.Companies;
import Pojo.Contact;
import Util.ConnectionUtil;
import Util.MybatisUtil;
import Util.OCR.Ocr;
import Util.ProxyPool;
import org.apache.ibatis.session.SqlSession;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class GetContact {
    /**
     * 根据公司的ID与类型获取该公司的联系方式
     *
     * @param companies
     */
    public static Companies getContact(Companies companies) throws IOException, InterruptedException {
        String id = companies.getUcustomerid();
        String type = companies.getUtype() == '0' ? "buyer" : "supplier";
        Contact contact = new Contact();
        boolean flag = true;
        while (flag) {
            Connection connection = null;
            Document doc = null;
            try {
                connection = ConnectionUtil.getConnection("https://www.52wmb.com/async/contact");
                connection.header("referer", "https://www.52wmb.com/" + type + "/" + id);
                connection.data("company_id", id);
                if (ProxyPool.proxy == null) {
                    ProxyPool.getProxy();
                }
                connection.proxy(ProxyPool.proxy.get("ip"), Integer.parseInt(ProxyPool.proxy.get("port")));
                doc = connection.post();
            } catch (Exception e) {
                ProxyPool.proxy = null;
                continue;
            }
            Element body = doc.body();
            //请求太繁忙
            if (body.select("h1").text().equals("503 Service Temporarily Unavailable")) {
                Thread.sleep(5000);
                continue;
            }
            //被反扒了
            if (body.text().equals("{\"fn\":\"robot_verify\",\"result\":\"CONTACT\",\"type\":0}")) {
                System.out.println("反扒了");
                Ocr.getOkCode();
                continue;
            }

            Elements tds = body.select("#contact-detail table tbody tr td");
            contact.setUcustomerid(id);
            for (int i = 0; i < tds.size(); i++) {
                String td = tds.get(i).text();
                switch (i) {
                    case 1:
                        contact.setUcontactfax(td.substring(td.indexOf("传真号码 ") + 5));
                        break;
                    case 2:
                        contact.setUcontactperson(td.substring(td.indexOf("联 系 人  ") + 7));
                        break;
                    case 3:
                        contact.setUcontactmail(td.substring(td.indexOf("邮箱地址 获取精准邮箱→ ") + 13));
                        break;
                    case 4:
                        contact.setUcontactphone(td.substring(td.indexOf("联系电话 ") + 5));
                        break;
                    case 5:
                        companies.setUcompaddress(td.substring(td.indexOf("公司地址 ") + 5));
                        break;
                    case 7:
                        companies.setUcompwebsite(td.substring(td.indexOf("官方网站 ") + 5));
                        break;
                    default:
                        break;
                }

            }
            flag = false;
        }
        System.out.println(contact.toString());
        addContact(contact);
        addCompanies(companies);
        return companies;
    }

    public static void addCompanies(Companies companies) {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        CompanieDao companieDao = sqlSession.getMapper(CompanieDao.class);
        companieDao.addCompanies(companies);
        sqlSession.commit();
    }

    private static void addContact(Contact contact) {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        ContactDao contactDao = sqlSession.getMapper(ContactDao.class);
        contactDao.addContact(contact);
        sqlSession.commit();
    }

}
