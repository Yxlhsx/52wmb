package Dao;

import Pojo.Bill;
import Pojo.Companies;
import Pojo.Contact;
import Util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

public class Test {
    public static void main(String[] args) {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        try {
//            CompanieDao companieDao = sqlSession.getMapper(CompanieDao.class);//通过sqlSession得到mapper
//            Companies companies = new Companies("1", '0', "美国", "肚子里", "1999-99-99", "666666", "10000");
//            companieDao.addCompanies(companies);
//            sqlSession.commit();

//            ContactDao contactDao = sqlSession.getMapper(ContactDao.class);//通过sqlSession得到mapper
//            Contact contact = new Contact("2316529", "张三", "13556565656", "86-9696551", "13515@sada.com");
//            contactDao.addContact(contact);
//            sqlSession.commit();

            BillDao billDao = sqlSession.getMapper(BillDao.class);//通过sqlSession得到mapper
            Bill bill = new Bill("1654615", "asda65165165161", "进货的", "卖货的", "1999-99-99", "进货的地方", "卖货的地方", "货物", "18kg", "5k", "1555刀", "15刀", "666", "出货的口子", "进货的口子", "船");
            billDao.addBill(bill);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }
}
