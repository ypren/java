package hello.java.network;

import java.net.Inet4Address;
import java.net.UnknownHostException;

public class IpAddress {

    private int processors = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {
        try {
            System.out.println(Inet4Address.getLocalHost().getHostAddress());
            System.out.println(Integer.toBinaryString(10));
            System.out.println(Long.parseLong("abc", 16));
            System.out.println(f("0101"));
            IpAddress ipAddress = new IpAddress();
            System.out.println(ipAddress.processors);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static int f(String binary) {
        return Integer.parseInt(binary, 2);
    }
}
