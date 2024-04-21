package demo7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {
        // 1. 創建ServerSocket對象
        ServerSocket ss = new ServerSocket(10000);
        // 2. 監聽客戶端的連接(阻塞等待)
        Socket socket = ss.accept();
        // 3. 從連接通道中獲取輸入流 讀取數據
        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        int b;
//        while ((b = is.read()) != -1)  { // 1個byte 1個byte 讀
//        while ((b = isr.read()) != -1) {
        while ((b = br.read()) != -1) {
            System.out.println((char) b);
        }
        // 4. 釋放資源
        is.close();
        socket.close();
        ss.close();
    }

}
