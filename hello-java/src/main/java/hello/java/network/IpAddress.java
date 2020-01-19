package hello.java.network;

import java.net.Inet4Address;
import java.net.UnknownHostException;

public class IpAddress {
    public static void main(String[] args) {
        try {
            System.out.println(Inet4Address.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
