package br.com.schumaker.network;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author hudson schumaker
 */
public class MessageRX {

    private boolean run;
    public static ServerSocket server;
    public static Socket incoming;

    public MessageRX() {
        try {
            server = new ServerSocket(HsCommons.RXPORT);
            run = true;
            System.out.println("> MesssageRX service running...");
            System.out.println("> MesssageRX listening on port: " + HsCommons.RXPORT);
        } catch (Exception e) {
            System.err.println("MessageRX:\n" + e);
        }
        while (isRun()) {
            try {
                incoming = server.accept();
                System.out.println("Client IP: " + incoming.getInetAddress());
                new MessageRxSession(incoming).start();
            } catch (Exception e) {
                System.err.println("MessageRX2:\n" + e);
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
