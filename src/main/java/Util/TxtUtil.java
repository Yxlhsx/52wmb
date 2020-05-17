package Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TxtUtil {
    //txt文件路径
    private static String txtPath = "src/main/resources/token.txt";

    /**
     * 读取txt中的token
     *
     * @return 返回读取到的内容
     */
    public static String readTxt() {
        File file = new File(txtPath);
        if (file.isFile() && file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuffer sb = new StringBuffer();
                String text = null;
                while ((text = bufferedReader.readLine()) != null) {
                    sb.append(text);
                }
                //判断txt的token是否超过十二个小时
                Date now = new Date();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String str = sb.substring(sb.indexOf("_time_") + 6);
                Date tokentime = df.parse(str);
                if (now.getTime() - tokentime.getTime() > 1000 * 60 * 60 * 12) {
                    System.out.println("token已过期");
                    return null;
                }

                return sb.toString();
            } catch (Exception e) {
                System.out.println("token为空");
            }
        }
        return null;
    }

    /**
     * 将获取到的token写入txt
     *
     * @param content 需要写入的文本
     */
    public static void writeTxt(String content) {
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        content += "_time_" + df.format(d);
        FileOutputStream fileOutputStream = null;
        File file = new File(txtPath);
        try {
            if (file.exists()) {
                //判断文件是否存在，如果不存在就新建一个txt
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(content.getBytes());
            System.out.println("token写入完成");
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TxtUtil txtUtil = new TxtUtil();
        txtUtil.writeTxt("asd");

        System.out.println("写入完成");
        System.out.println("读取：" + txtUtil.readTxt());
    }

}
