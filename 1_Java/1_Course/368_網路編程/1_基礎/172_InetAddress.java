import java.net.InetAddress;
import java.net.UnknownHostException;

public static void main(String[] args) throws UnknownHostException {
    // InetAddress address = InetAddress.getByName("192.168.56.1");
    InetAddress address = InetAddress.getByName("livingroom-pc");
    String hostName = address.getHostName();
    String hostAddress = address.getHostAddress();

    System.out.println(address);     // livingroom-pc/192.168.56.1
    System.out.println(hostName);    // livingroom-pc
    System.out.println(hostAddress); // 192.168.56.1
}