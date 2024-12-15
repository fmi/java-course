import java.io.IOException;
import java.net.NetworkInterface;
import java.util.Collections;

public class NetworkInterfaceExample {

    public static void main(String... args) throws IOException {

        Collections.list(NetworkInterface.getNetworkInterfaces())
            .stream()
            .forEach(n -> {
                System.out.println("Disp. name: " + n.getDisplayName());
            });

    }

}
