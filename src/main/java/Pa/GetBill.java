package Pa;

import Pojo.Bill;
import Util.ConnectionUtil;
import Util.OCR.Ocr;
import Util.ProxyPool;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class GetBill {
    private static int companyID = 0;

    public static List<Bill> getBill(int ID) throws IOException, InterruptedException {
        companyID = ID;
        Connection connection = null;
        Document body = null;

        boolean flag = true;
        while (flag) {
            try {
                connection = ConnectionUtil.getConnection("https://www.52wmb.com/async/all/bill?id=" + ID);
                connection.header("referer", "https://www.52wmb.com/");

                if (ProxyPool.proxy == null) {
                    ProxyPool.getProxy();
                }
                connection.proxy(ProxyPool.proxy.get("ip"), Integer.parseInt(ProxyPool.proxy.get("port")));
                body = connection.get();
            } catch (Exception e) {
                ProxyPool.proxy = null;
                continue;
            }
            flag = false;
        }

        String str = body.select(".tdsjtop-title h3 b").text();
        int num = Integer.parseInt(str);
        System.out.println("该公司提单数：" + num);
        if (num < 30) {
            System.out.println("该公司提单数只有一页");
            Elements as = body.select(".tdsj-con .sjtable #bill_page_box_tbody tr td:last-child a");
            return onePageGetaBill(as, ID);
        } else {
            int page = num % 30 == 0 ? num / 30 : num / 30 + 1;
            System.out.println("该公司提单页数：" + page);
            return MorePageGetaBill(page, ID);
        }

    }

    /**
     * 公司提单多页
     */
    public static List<Bill> MorePageGetaBill(int page, int ID) throws IOException, InterruptedException {
        List<Bill> list = new LinkedList<Bill>();
        for (int i = 1; i <= page; i++) {
            Connection _connection = null;
            Element _body = null;
            try {
                _connection = ConnectionUtil.getConnection("https://www.52wmb.com/async/bill/page");
                _connection.data("id", ID + "");
                _connection.data("page", i + "");
                if (ProxyPool.proxy == null) {
                    ProxyPool.getProxy();
                }
                _connection.proxy(ProxyPool.proxy.get("ip"), Integer.parseInt(ProxyPool.proxy.get("port")));
                _body = _connection.post();
            } catch (Exception e) {
                ProxyPool.proxy = null;
                i--;
                continue;
            }

            if (_body.text().equals("{\"fn\":\"robot_verify\",\"result\":\"CONTACT\",\"type\":0}")) {
                System.out.println("反扒了");
                Ocr.getOkCode();
                continue;
            }

            Elements _trs = _body.select("#bill_page_box_tbody tr");
            for (Element _tr : _trs) {
                Elements as = _tr.select("td:last-child a");
                for (Element a : as) {
                    Connection connection = null;
                    Document doc = null;
                    boolean flag = true;
                    while (flag) {
                        try {
                            connection = ConnectionUtil.getConnection("https://www.52wmb.com/async/bill/detail?bill_id=" + a.attr("data-id") + "&companyid=" + ID);
                            if (ProxyPool.proxy == null) {
                                ProxyPool.getProxy();
                            }
                            connection.proxy(ProxyPool.proxy.get("ip"), Integer.parseInt(ProxyPool.proxy.get("port")));
                            doc = connection.get();
                        } catch (Exception e) {
                            ProxyPool.proxy = null;
                            continue;
                        }
                        flag = false;
                    }
                    Element body = doc.body();

                    if (_body.text().equals("{\"fn\":\"robot_verify\",\"result\":\"CONTACT\",\"type\":0}")) {
                        System.out.println("反扒了");
                        Ocr.getOkCode();
                        continue;
                    }

                    Elements trs = body.select("#bill_data_box > tbody > tr");

                    Bill bill = new Bill();
                    bill.setUcustomerid(String.valueOf(companyID));
                    for (Element tr : trs) {
                        String id = tr.select("td h1").text();
                        if (id.length() > 4) {
                            bill.setUblno(id.substring(4));
                        }
                        String th = tr.select("td:nth-child(1)").text();
                        if ("提单编号".equals(th)) {

                        } else if ("采购商".equals(th)) {
                            bill.setUpurchaser(tr.select("td:nth-child(2)").text());
                        } else if ("供应商".equals(th)) {
                            bill.setUsupplier(tr.select("td:nth-child(2)").text());
                        } else if ("日期".equals(th)) {
                            bill.setUtradedate(tr.select("td:nth-child(2)").text());
                        } else if ("采购区域".equals(th)) {
                            bill.setUpurchasingarea(tr.select("td:nth-child(2)").text());
                        } else if ("供应区域".equals(th)) {
                            bill.setUsupplyarea(tr.select("td:nth-child(2)").text());
                        } else if ("产品描述".equals(th) && tr.select("td").size() > 1) {
                            bill.setUproductdesc(tr.select("td:nth-child(2) > b").text());
                        } else if ("重量".equals(th)) {
                            bill.setUweight(tr.select("td:nth-child(2)").text());
                        } else if ("数量".equals(th)) {
                            bill.setUnumber(tr.select("td:nth-child(2)").text());
                        } else if ("总金额".equals(th)) {
                            bill.setUtotalamount(tr.select("td:nth-child(2)").text());
                        } else if ("单价".equals(th)) {
                            bill.setUprice(tr.select("td:nth-child(2)").text());
                        } else if ("箱/Unit".equals(th)) {
                            bill.setUbxunit(tr.select("td:nth-child(2)").text());
                        } else if ("出货港口".equals(th)) {
                            bill.setUshipmentport(tr.select("td:nth-child(2)").text());
                        } else if ("进货港口".equals(th)) {
                            bill.setUpurchaseport(tr.select("td:nth-child(2)").text());
                        } else if ("运输方式".equals(th)) {
                            bill.setUshippingtype(tr.select("td:nth-child(2)").text());
                        }
                    }
                    list.add(bill);
                    System.out.println(bill.toString());

                }
            }
        }
        System.out.println(list.size());
        return list;

    }

    /**
     * 公司提单只有一页
     */
    public static List<Bill> onePageGetaBill(Elements as, int ID) throws InterruptedException, IOException {
        List<Bill> list = new LinkedList<Bill>();
        for (Element a : as) {
            Connection connection = null;
            Document doc = null;
            boolean flag = true;
            while (flag) {
                try {
                    connection = ConnectionUtil.getConnection("https://www.52wmb.com/async/bill/detail?bill_id=" + a.attr("data-id") + "&companyid=" + ID);
                    if (ProxyPool.proxy == null) {
                        ProxyPool.getProxy();
                    }
                    connection.proxy(ProxyPool.proxy.get("ip"), Integer.parseInt(ProxyPool.proxy.get("port")));
                    doc = connection.get();
                } catch (Exception e) {
                    ProxyPool.proxy = null;
                    continue;
                }
                flag = false;
            }
            Element body = doc.body();
            if (body.text().equals("{\"fn\":\"robot_verify\",\"result\":\"CONTACT\",\"type\":0}")) {
                System.out.println("反扒了");
                Ocr.getOkCode();
                continue;
            }
            Elements trs = body.select("#bill_data_box > tbody > tr");

            Bill bill = new Bill();
            bill.setUcustomerid(String.valueOf(companyID));
            for (Element tr : trs) {
                String id = tr.select("td h1").text();
                if (id.length() > 4) {
                    bill.setUblno(id.substring(4));
                }
                String th = tr.select("td:nth-child(1)").text();
                if ("提单编号".equals(th)) {

                } else if ("采购商".equals(th)) {
                    bill.setUpurchaser(tr.select("td:nth-child(2)").text());
                } else if ("供应商".equals(th)) {
                    bill.setUsupplier(tr.select("td:nth-child(2)").text());
                } else if ("日期".equals(th)) {
                    bill.setUtradedate(tr.select("td:nth-child(2)").text());
                } else if ("采购区域".equals(th)) {
                    bill.setUpurchasingarea(tr.select("td:nth-child(2)").text());
                } else if ("供应区域".equals(th)) {
                    bill.setUsupplyarea(tr.select("td:nth-child(2)").text());
                } else if ("产品描述".equals(th) && tr.select("td").size() > 1) {
                    bill.setUproductdesc(tr.select("td:nth-child(2) > b").text());
                } else if ("重量".equals(th)) {
                    bill.setUweight(tr.select("td:nth-child(2)").text());
                } else if ("数量".equals(th)) {
                    bill.setUnumber(tr.select("td:nth-child(2)").text());
                } else if ("总金额".equals(th)) {
                    bill.setUtotalamount(tr.select("td:nth-child(2)").text());
                } else if ("单价".equals(th)) {
                    bill.setUprice(tr.select("td:nth-child(2)").text());
                } else if ("箱/Unit".equals(th)) {
                    bill.setUbxunit(tr.select("td:nth-child(2)").text());
                } else if ("出货港口".equals(th)) {
                    bill.setUshipmentport(tr.select("td:nth-child(2)").text());
                } else if ("进货港口".equals(th)) {
                    bill.setUpurchaseport(tr.select("td:nth-child(2)").text());
                } else if ("运输方式".equals(th)) {
                    bill.setUshippingtype(tr.select("td:nth-child(2)").text());
                }
            }
            list.add(bill);
            System.out.println(bill.toString());

        }
        System.out.println(list.size());
        return list;
    }

}
