package Util.OCR;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 图片base64互转
 */
public class ImageBase64Util {
    public static String bytesToBase64(byte[] bytes) {
        return org.apache.commons.codec.binary.Base64.encodeBase64String(bytes);// 返回Base64编码过的字节数组字符串
    }

    /**
     * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     */
    public static String imageToBase64(String path) throws IOException {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        // 读取图片字节数组
        InputStream in = null;
        try {
            in = new FileInputStream(path);
            data = new byte[in.available()];
            in.read(data);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return org.apache.commons.codec.binary.Base64.encodeBase64String(data);// 返回Base64编码过的字节数组字符串
    }

    /**
     * 生成随机的时间戳id
     */
    public static String createID() {
        byte[] lock = new byte[0];
        long w = 100000000;// 位数，默认是8位

        long r = 0;
        synchronized (lock) {
            r = (long) ((Math.random() + 1) * w);
        }

        return System.currentTimeMillis() + String.valueOf(r).substring(1);
    }

    /**
     * 处理Base64解码并写图片到指定位置
     */
    public static boolean base64ToImageFile(String base64, String path) throws IOException {// 对字节数组字符串进行Base64解码并生成图片
        // 生成jpeg图片
        try {
            OutputStream out = new FileOutputStream(path);
            return base64ToImageOutput(base64, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 处理Base64解码并写图片到resources/code中
     */
    public static boolean base64ToImageFile(String base64) throws IOException {// 对字节数组字符串进行Base64解码并生成图片
        //将扒到的验证码放到这个目录下，根据时间戳生成图片名
        String path = "src/main/resources/code/" + createID() + ".jpg";
        // 生成jpeg图片
        try {
            OutputStream out = new FileOutputStream(path);
            return base64ToImageOutput(base64, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 处理Base64解码转为BufferedImage
     */
    public static BufferedImage base64ToBufferedImage(String base64) throws IOException {
        if (base64 == null) { // 图像数据为空
            return null;
        }
        // Base64解码
        byte[] bytes = org.apache.commons.codec.binary.Base64.decodeBase64(base64);
        for (int i = 0; i < bytes.length; ++i) {
            if (bytes[i] < 0) {// 调整异常数据
                bytes[i] += 256;
            }
        }
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);    //将b作为输入流；
        BufferedImage image = ImageIO.read(in);
        return image;
    }

    /**
     * 处理Base64解码并输出流
     */
    public static boolean base64ToImageOutput(String base64, OutputStream out) throws IOException {
        if (base64 == null) { // 图像数据为空
            return false;
        }
        try {
            // Base64解码
            byte[] bytes = org.apache.commons.codec.binary.Base64.decodeBase64(base64);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            // 生成jpeg图片
            out.write(bytes);
            out.flush();
            return true;
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理Base64解码为byte
     */
    public static byte[] base64ToByte(String base64) throws IOException {
        // Base64解码
        byte[] bytes = org.apache.commons.codec.binary.Base64.decodeBase64(base64);
        for (int i = 0; i < bytes.length; ++i) {
            if (bytes[i] < 0) {// 调整异常数据
                bytes[i] += 256;
            }
        }
        return bytes;

    }
}