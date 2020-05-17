package Util.OCR;

import net.sourceforge.tess4j.Tesseract;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 *Tessrecat—ocr工具类
 */
public class Tess4J {
    /**
     * 识别BufferedImage
     */
    public static String ocr(BufferedImage image) {
        Tesseract instance = new Tesseract();
        instance.setDatapath("src/main/resources/tessdata");
        instance.setLanguage("num");
        String result = null;
        try {

            result = instance.doOCR(image);

        } catch (Exception e) {

            System.err.println(e.getCause());

        }
        return result;
    }

    /**
     * 识别File
     */
    public static String ocr(File imageFile) {
        Tesseract instance = new Tesseract();
        instance.setDatapath("src/main/resources/tessdata");
        instance.setLanguage("num");
        String result = null;
        try {

            result = instance.doOCR(imageFile);

        } catch (Exception e) {

            System.err.println(e.getCause());

        }
        return result;
    }

}
