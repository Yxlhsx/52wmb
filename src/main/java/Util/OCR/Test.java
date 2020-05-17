package Util.OCR;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException, InterruptedException {
        String code = "e3f2";
        if (CodeUtil.submitCode(code)) {
            System.out.println("通过");
        } else {
            System.out.println("没有通过");
        }
    }
}
