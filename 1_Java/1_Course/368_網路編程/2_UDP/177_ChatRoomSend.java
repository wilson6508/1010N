package demo6;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class ChatRoomSend {

    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket();
        InetAddress address = InetAddress.getByName("127.0.0.1");
        int port = 10086;
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter something : ");
            String str = sc.nextLine();
            if ("886".equals(str)) {
                break;
            }
            byte[] bytes = str.getBytes();
            DatagramPacket dp = new DatagramPacket(bytes, bytes.length, address, port);
            ds.send(dp);
        }
        ds.close();
    }

}
