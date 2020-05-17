import Dao.BillDao;
import Pa.GetBill;
import Pa.GetCompany;
import Pa.GetContact;
import Pojo.Bill;
import Pojo.Companies;
import Util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        final int start = 100000;//自增起始数
        final int end = 99999999;//自增截止数

        for (int id = start; id <= end; id++) {
            Companies companies = GetCompany.getCompany(String.valueOf(id));
            if (companies.getIsContact().equals("1")) {
                GetContact.getContact(companies);
            }
            GetContact.addCompanies(companies);
            List<Bill> list = GetBill.getBill(Integer.parseInt(companies.getUcustomerid()));

            SqlSession sqlSession = MybatisUtil.getSqlSession();

            for (Bill bill : list) {
                System.out.println(bill.toString());
                BillDao billDao = sqlSession.getMapper(BillDao.class);
                billDao.addBill(bill);
                sqlSession.commit();
            }
        }

    }
}
