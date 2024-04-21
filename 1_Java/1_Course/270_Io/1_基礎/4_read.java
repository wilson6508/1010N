import java.io.FileInputStream;
import java.io.IOException;

String str = new String(bytes);
String str = new String(bytes, 0, len);

public static void main(String[] args) throws IOException {
    FileInputStream fis = new FileInputStream("day03-code\\b.txt");
    byte[] bytes = new byte[2];  // 1次讀取幾個字節

    int len01 = fis.read(bytes); // len01 本次讀取到幾個字節
    System.out.println(len01);
    String str01 = new String(bytes);
    System.out.println(str01);

    int len02 = fis.read(bytes);
    System.out.println(len02);
    String str02 = new String(bytes);
    System.out.println(str02);

    ///////////////////////////////////////////////////////////////////

    int len01 = fis.read(bytes); // len01 本次讀取到幾個字節
    String str01 = new String(bytes, 0, len01);
    System.out.println(str01);

    int len02 = fis.read(bytes);
    String str02 = new String(bytes, 0, len02);
    System.out.println(str02);

    fis.close();
}