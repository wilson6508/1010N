import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public static void main(String[] args) throws IOException {
    // 接收要綁定端口
    DatagramSocket ds = new DatagramSocket(10086);
    // 接收數據包
    byte[] bytes = new byte[1024];
    DatagramPacket dp = new DatagramPacket(bytes, bytes.length);
    System.out.println("阻塞中...");
    ds.receive(dp);
    System.out.println("收到數據");
    // 解析數據包
    byte[] data = dp.getData();
    int len = dp.getLength();
    InetAddress address = dp.getAddress();
    int port = dp.getPort();
    String info = new String(data, 0, len);
    System.out.println(info);
    System.out.println(address);
    System.out.println(port);
    // 釋放資源
    ds.close();
}