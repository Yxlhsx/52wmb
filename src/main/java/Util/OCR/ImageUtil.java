package Util.OCR;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 图片处理工具类
 */
public class ImageUtil {
    /**
     * 正确读取背景透明的图片
     */
    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        //此代码可确保加载图像中的所有像素
        image = new ImageIcon(image).getImage();
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        try {
            int transparency = Transparency.OPAQUE;
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(image.getWidth(null),
                    image.getHeight(null), transparency);
        } catch (HeadlessException e) {
            //系统没有屏幕
        }
        if (bimage == null) {
            //使用默认的颜色模型创建缓冲的图像
            int type = BufferedImage.TYPE_INT_RGB;
            bimage = new BufferedImage(image.getWidth(null),
                    image.getHeight(null), type);
        }
        //将图像复制到缓冲的图像
        Graphics g = bimage.createGraphics();
        //将图像绘制到缓冲的图像上
        g.drawImage(image, 0, 0, null);
        g.dispose();

        int width = bimage.getWidth();
        int height = bimage.getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = new Color(bimage.getRGB(x, y));
                if (color.getRed() + color.getGreen() + color.getBlue() <= 100) {
                    bimage.setRGB(x, y, Color.white.getRGB());
                }
            }
        }

        return bimage;
    }

    /**
     * 图片灰度化
     */
    public static BufferedImage grayImage(BufferedImage image) throws IOException {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);//重点，技巧在这个参数BufferedImage.TYPE_BYTE_GRAY
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = image.getRGB(i, j);
                grayImage.setRGB(i, j, rgb);
            }
        }
        return grayImage;
    }

    /**
     * 图片二值化
     */
    public static BufferedImage binaryImage(BufferedImage image) throws IOException {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);//重点，技巧在这个参数BufferedImage.TYPE_BYTE_BINARY
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = image.getRGB(i, j);
                grayImage.setRGB(i, j, rgb);
            }
        }
        return grayImage;
    }

    /**
     * 去干扰线
     */
    public static BufferedImage removeLine(BufferedImage img) {
        if (img != null) {
            int width = img.getWidth();
            int height = img.getHeight();

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    boolean flag = false;
                    if (x < width - 1 && y < height - 1 && isBlack(img.getRGB(x, y))) {
                        if (isWhite(img.getRGB(x - 1, y)) && isWhite(img.getRGB(x + 1, y))) {
                            flag = true;
                        }
                        if (isWhite(img.getRGB(x, y + 1)) && isWhite(img.getRGB(x, y - 1))) {
                            flag = true;
                        }
//                        if (isWhite(img.getRGB(x - 1, y + 1)) && isWhite(img.getRGB(x + 1, y - 1))) {
//                            flag = true;
//                        }
//                        if (isWhite(img.getRGB(x + 1, y + 1)) && isWhite(img.getRGB(x - 1, y - 1))) {
//                            flag = true;
//                        }
                        if (flag) {
                            img.setRGB(x, y, -1);
                        }

                    }
                }
            }

        }
        return img;
    }

    /**
     * 去噪点
     */
    public static BufferedImage removeNoise(BufferedImage img) {
        if (img != null) {
            int width = img.getWidth();
            int height = img.getHeight();

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int num = 0;
                    if (x < width - 1 && y < height - 1 && isBlack(img.getRGB(x, y))) {
                        if (isBlack(img.getRGB(x - 1, y - 1))) {
                            num++;
                        }
                        if (isBlack(img.getRGB(x, y - 1))) {
                            num++;
                        }
                        if (isBlack(img.getRGB(x + 1, y - 1))) {
                            num++;
                        }
                        if (isBlack(img.getRGB(x + 1, y))) {
                            num++;
                        }
                        if (isBlack(img.getRGB(x + 1, y + 1))) {
                            num++;
                        }
                        if (isBlack(img.getRGB(x, y + 1))) {
                            num++;
                        }
                        if (isBlack(img.getRGB(x - 1, y + 1))) {
                            num++;
                        }
                        if (isBlack(img.getRGB(x - 1, y))) {
                            num++;
                        }
                        if (num < 4) {
                            img.setRGB(x, y, -1);
                        }
                    }
                }
            }

        }
        return img;
    }

    /**
     * 判断像素是否黑色
     */
    public static boolean isBlack(int colorInt) {
        Color color = new Color(colorInt);
        if (color.getRed() + color.getGreen() + color.getBlue() <= 300) {
            return true;
        }
        return false;
    }

    /**
     * 判断像素是否白色
     */
    public static boolean isWhite(int colorInt) {
        Color color = new Color(colorInt);
        if (color.getRed() + color.getGreen() + color.getBlue() > 300) {
            return true;
        }
        return false;
    }

    /**
     * 删除code里的图片
     */
    public static void deleteImages() {
        String path = "src/main/resources/code";
        File file = new File(path);
        //取得这个目录下的所有子文件对象
        File[] files = file.listFiles();
        for (File f : files) {
            String name = file.getName();
            f.delete();
        }
    }

}