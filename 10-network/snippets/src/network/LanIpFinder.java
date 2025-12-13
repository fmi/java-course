void main() throws Exception {
    InetAddress lanIp = getLanIp();
    IO.println(lanIp);
}

public static InetAddress getLanIp() throws SocketException {
    Enumeration<NetworkInterface> nics = NetworkInterface.getNetworkInterfaces();

    while (nics.hasMoreElements()) {
        NetworkInterface nic = nics.nextElement();

        // Skip loopback, down, or virtual interfaces
        if (!nic.isUp() || nic.isLoopback() || nic.isVirtual()) {
            continue;
        }

        Enumeration<InetAddress> addrs = nic.getInetAddresses();
        while (addrs.hasMoreElements()) {
            InetAddress addr = addrs.nextElement();

            // We want only IPv4 addresses and exclude loopback
            if (addr instanceof Inet4Address && !addr.isLoopbackAddress()) {
                return addr;
            }
        }
    }
    return null; // or throw exception if required
}
