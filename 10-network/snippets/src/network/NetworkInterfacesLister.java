void main() throws Exception {

    Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

    while (interfaces.hasMoreElements()) {
        NetworkInterface nic = interfaces.nextElement();

        IO.println("----------------------------------------------------");
        IO.println("Name         : " + nic.getName());
        IO.println("Up           : " + nic.isUp());
        IO.println("Loopback     : " + nic.isLoopback());
        IO.println("Virtual      : " + nic.isVirtual());
        IO.println("PointToPoint : " + nic.isPointToPoint());
        IO.println("MTU          : " + nic.getMTU());

        byte[] mac = nic.getHardwareAddress();
        IO.print("MAC Address  : ");
        if (mac != null) {
            for (int i = 0; i < mac.length; i++) {
                System.out.printf("%02X%s", mac[i], (i < mac.length - 1) ? "-" : "");
            }
            IO.println();
        } else {
            IO.println("N/A");
        }

        // List all IPv4/IPv6 addresses
        IO.println("IP Addresses :");
        Enumeration<InetAddress> addrs = nic.getInetAddresses();
        while (addrs.hasMoreElements()) {
            InetAddress addr = addrs.nextElement();
            IO.println("   - " + addr.getHostAddress());
        }
        IO.println();
    }

}
