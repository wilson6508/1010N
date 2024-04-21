import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public static void main(String[] args) throws FileNotFoundException {
    FileInputStream fis = new FileInputStream("D:\\test_dir\\test.mp4");
    FileOutputStream fos = new FileOutputStream("day03-code\\copy.mp4");
    try (fis; fos) {
        int len;
        byte[] bytes = new byte[1024 * 1024 * 5];
        while ((len = fis.read(bytes)) != -1) {
            fos.write(bytes, 0, len);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}