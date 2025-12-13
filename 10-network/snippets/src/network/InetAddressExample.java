void main() throws IOException {

    InetAddress address = InetAddress.getByName("www.google.com");
    IO.println(address.getHostAddress()); // 172.217.17.132

    address = InetAddress.getByName("62.44.101.151");
    IO.println(address.getHostName()); // learn.fmi.uni-sofia.bg

    IO.println(address.isReachable(5_000)); // true

    InetAddress localhost = InetAddress.getLocalHost();
    IO.println(localhost.getHostAddress()); // IP associated with your hostname,
                                            // typically LAN IP (192.168.x.x)

    // Think of:
    //   - LAN IP = your apartment number
    //   - Public IP = the street address for the whole building
    //   - 127.0.0.1 = your own room inside your apartment (only you can reach it)

}
