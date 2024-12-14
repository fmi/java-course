import java.io.IOException;
import java.net.InetAddress;

public class InetAddressExample {

    public static void main(String... args) throws IOException {

        InetAddress address = InetAddress.getByName("www.google.com");
        System.out.println(address.getHostAddress()); // 142.250.185.164

        address = InetAddress.getByName("62.44.101.151");
        System.out.println(address.getHostName()); // learn.fmi.uni-sofia.bg

        System.out.println(address.isReachable(5_000)); // true

        InetAddress localhost = InetAddress.getLocalHost();
        System.out.println(localhost.getHostAddress());

    }

}
