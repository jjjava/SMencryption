package br.com.schumaker.network;

import br.com.schumaker.core.Engine;
import br.com.schumaker.io.WelcomeSmsCreator;
import br.com.schumaker.model.User;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author hudson schumaker
 */
public class ServiceLocator implements Runnable {

    private boolean run;

    public void start() {
        Thread thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        setRun(true);
        thread.start();
    }

    private void receiverClient() {
        try {
            DatagramSocket dsocket = new DatagramSocket(HsCommons.LOCATORPORT);
            byte[] buffer = new byte[HsCommons.BUFFER];
            System.out.println("Locator system started...");
            System.out.println("Locator system listening on port: " + HsCommons.LOCATORPORT);
            while (isRun()) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                dsocket.receive(packet);
                String msg = new String(buffer, 0, packet.getLength());
                if (msg.startsWith(HsCommons.IDSERVICE)) {
                    String aux[] = msg.split(";");
                    InetAddress clientIP = packet.getAddress();
                    int clientPort = packet.getPort();
                    byte[] response = WelcomeSmsCreator.getMyMessageID().getBytes();
                    packet = new DatagramPacket(response, response.length, clientIP, clientPort);
                    dsocket.send(packet);
                    Engine.getInstance().addUser(new User(aux[1], aux[2]));
                }
            }
        } catch (Exception e) {
            System.err.println("ServiceLocator:receiverClient:\n" + e);
        }
    }

    @Override
    public void run() {
        receiverClient();
    }

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }
}
