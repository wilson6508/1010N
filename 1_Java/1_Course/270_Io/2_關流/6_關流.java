import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public static void main(String[] args) {
    FileInputStream fis = null;
    FileOutputStream fos = null;
    try {
        fis = new FileInputStream("D:\\test_dir\\test.mp4");
        fos = new FileOutputStream("day03-code\\copy.mp4");
        int len;
        byte[] bytes = new byte[1024 * 1024* 5];
        while ((len = fis.read(bytes)) != -1) {
            fos.write(bytes, 0, len);
        }
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}