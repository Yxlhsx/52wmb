package Util.OCR;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Ocr {
    /**
     * 输入验证码
     */
    public static void inputCode() throws IOException, InterruptedException {
        ImageUtil imageUtil = new ImageUtil();
        boolean isSuccess = false;//成功标识
        int num = 0;//计数
        while (!isSuccess) {
            String[] base64 = CodeUtil.getArrBase64(3);//获取验证码base64数组
            for (int i = 0; i < base64.length; i++) {
                num++;
                byte[] bytes = ImageBase64Util.base64ToByte(base64[i]);
                Image src = Toolkit.getDefaultToolkit().createImage(bytes);//补全图片（不变色）
                BufferedImage bfi = imageUtil.toBufferedImage(src);
                bfi = imageUtil.grayImage(bfi);//灰度化
                bfi = imageUtil.binaryImage(bfi);//二值化
                bfi = imageUtil.removeLine(bfi);//去干扰线
                bfi = imageUtil.removeNoise(bfi);//去噪点
                //识别处理后的验证码
                String code = Tess4J.ocr(bfi).replaceAll(" ", "");//去空格
                if (code.length() > 4) {//取code前四位
                    code = code.substring(0, 4);
                }
                //提交
                if (CodeUtil.submitCode(code)) {
                    isSuccess = true;
                    System.out.println("验证码通过，共执行" + num + "次");
                    break;
                }
            }
        }
    }

    /**
     * 获取可用的验证码
     */
    public static String getOkCode() throws IOException, InterruptedException {
        ImageUtil imageUtil = new ImageUtil();
        boolean isSuccess = false;//成功标识
        int num = 0;//计数
        while (!isSuccess) {
            String base64 = CodeUtil.getBase64();//获取验证码base64
            num++;
            byte[] bytes = ImageBase64Util.base64ToByte(base64);
            Image src = Toolkit.getDefaultToolkit().createImage(bytes);//补全图片（不变色）
            BufferedImage bfi = imageUtil.toBufferedImage(src);
            bfi = imageUtil.grayImage(bfi);//灰度化
            bfi = imageUtil.binaryImage(bfi);//二值化
            bfi = imageUtil.removeLine(bfi);//去干扰线
            bfi = imageUtil.removeNoise(bfi);//去噪点
            //识别处理后的验证码
            String code = Tess4J.ocr(bfi).replaceAll(" ", "");//去空格
            if (code.length() > 4) {//取code前四位
                code = code.substring(0, 4);
            }
            //提交
            if (CodeUtil.submitCode(code)) {
                //删除不需要的图片
                isSuccess = true;
                return code;
            }
        }
        return null;
    }

    /**
     * 测试
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        Ocr.inputCode();
    }
}
