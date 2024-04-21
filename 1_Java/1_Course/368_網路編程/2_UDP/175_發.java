import java.io.IOException;
import java.net.*;

public static void main(String[] args) throws IOException {
    // 空參: 隨機一個可用端口
    // 有參: 指定端口
    DatagramSocket ds = new DatagramSocket();
    // 打包數據
    String str = "你好!";
    byte[] bytes = str.getBytes();
    InetAddress address = InetAddress.getByName("127.0.0.1");
    int port = 10086;
    DatagramPacket dp = new DatagramPacket(bytes, bytes.length, address, port);
    // 發送數據
    ds.send(dp);
    // 釋放資源
    ds.close();
}