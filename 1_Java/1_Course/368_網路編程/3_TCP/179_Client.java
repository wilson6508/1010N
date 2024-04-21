package demo7;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException {
        // 1. 創建Socket對象連接服務端 (3次握手協議保證連接建立)
        Socket socket = new Socket("127.0.0.1", 10000);
        // 2. 從連接通道中獲取輸出流 寫出數據
        OutputStream os = socket.getOutputStream();
        os.write("你好你好".getBytes()); // 1個中文字=3個byte
        // 3. 釋放資源
        os.close();
        socket.close(); // 4次揮手協議斷開連接 保證連接通道中的數據已經處理完畢
    }

}
