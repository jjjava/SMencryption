package br.com.schumaker.io;

import br.com.schumaker.network.HsCommons;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author hudson schumaker
 * Server
 */
public class WelcomeSmsCreator {

    //idService;ipAddress
    public static String getMyMessageID() {
        InetAddress ip;
        String message = "?;?";
        try {
            ip = InetAddress.getLocalHost();
            message = HsCommons.IDSERVICE + ";" + ip.getHostAddress();
        } catch (UnknownHostException ex) {
            System.err.println("WelcomeSmsCreator:getMyMessageID:\n"+ex);
        }
        return message;
    }
}
