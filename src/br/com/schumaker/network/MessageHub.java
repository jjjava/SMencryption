package br.com.schumaker.network;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author hudson schumaker
 */
public class MessageHub {

    private boolean run;
    public static ServerSocket server;
    public static Socket incoming;

    public MessageHub() {
        try {
            server = new ServerSocket(HsCommons.RXPORT);
            run = true;
            System.out.println("MesssageHub service running...");
            System.out.println("MesssageHub listening on port: " + HsCommons.RXPORT);
        } catch (Exception e) {
            System.err.println("MessageHub:\n" + e);
        }
        while (run) {
            try {
                incoming = server.accept();
//                System.out.println("Client IPx: " + incoming.getInetAddress());
//                System.out.println("Client Port: " + incoming.getPort());
                new MessageHubSession(incoming).start();
            } catch (Exception e) {
                System.err.println("MessageHub2:\n" + e);
            }
        }
    }

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }
}
