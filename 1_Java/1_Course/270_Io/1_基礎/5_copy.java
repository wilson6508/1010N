import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public static void main(String[] args) throws IOException {
    long start = System.currentTimeMillis();
    FileInputStream fis = new FileInputStream("D:\\test_dir\\test.mp4");
    FileOutputStream fos = new FileOutputStream("day03-code\\copy.mp4");
    byte[] bytes = new byte[1024 * 1024* 5];
    int len;
    while ((len = fis.read(bytes)) != -1) {
        fos.write(bytes, 0, len);
    }
    fos.close();
    fis.close();
    long end = System.currentTimeMillis();
    System.out.println(end - start);
}