package br.com.schumaker.network;

import br.com.schumaker.core.Engine;
import br.com.schumaker.model.User;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author hudson schumaker
 */
public class Hunter implements Runnable {

    private boolean run;
    private ArrayList<User> listToRemove;
    private User u;

    public Hunter() {
        listToRemove = new ArrayList<>();
    }

    private void hunterClients() {
        try {
            for (Iterator<User> it = Engine.getInstance().getList().iterator(); it.hasNext();) {
                u = it.next();
                byte[] message = HsCommons.ALIVE.getBytes();
                InetAddress address = InetAddress.getByName(u.getIp());
                DatagramPacket packet = new DatagramPacket(message, message.length, address, HsCommons.KPORT);
                DatagramSocket dsocket = new DatagramSocket();
                dsocket.setSoTimeout(8000);
                dsocket.send(packet);
                byte[] response = new byte[HsCommons.BUFFER];
                DatagramPacket back = new DatagramPacket(response, response.length);
                dsocket.receive(back);
                byte[] received = back.getData();
                String server = new String(received);
                server = server.trim();
                System.out.println(server);
                if (!server.equalsIgnoreCase(HsCommons.OK)) {
                    listToRemove.add(u);
                }
                dsocket.close();
            }
        } catch (Exception e) {
            listToRemove.add(u);
            System.err.println("Hunter:hunterClients: " + e);
        }
    }

    private void refreshUsers() {
        for (User u : listToRemove) {
            Engine.getInstance().removeUser(u);
        }
        listToRemove.clear();
    }

    @Override
    public void run() {
        hunterClients();
        refreshUsers();
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.setName("Hunter");
        thread.setPriority(Thread.MIN_PRIORITY);
        run = true;
        thread.start();
    }

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }
}
