package demo6;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ChatRoomReceive {

    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket(10086);
        byte[] bytes = new byte[1024];
        DatagramPacket dp = new DatagramPacket(bytes, bytes.length);
        while (true) {
            ds.receive(dp);
            byte[] data = dp.getData();
            int len = dp.getLength();
            String ip = dp.getAddress().getHostAddress();
            String hostname = dp.getAddress().getHostName();
            String msg = String.format("ip=%s  主機名=%s  數據=%s", ip, hostname, new String(data, 0, len));
            System.out.println(msg);
        }
    }

}
