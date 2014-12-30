package br.com.schumaker.core;

import br.com.schumaker.model.User;
import br.com.schumaker.network.HsCommons;
import br.com.schumaker.network.MessageRX;
import br.com.schumaker.network.ServiceLocator;
import java.util.ArrayList;

/**
 *
 * @author hudson schumaker
 */
public class Engine implements Runnable {

    private static final Engine INSTANCE = new Engine();
    private ArrayList<User> list;

    private Engine() {
        list = new ArrayList<>();
    }

    public static Engine getInstance() {
        return INSTANCE;
    }

    public synchronized void addUser(User u) {
        list.add(u);
        printList();
    }

    public ArrayList<User> getList() {
        return list;
    }

    public void printList() {
        for (User u : list) {
            System.out.println(u.getName() + " : " + u.getIp());
        }
    }

    @Override
    public void run() {
        System.out.println("Starting server...");
        System.out.println("Buffer size: " + HsCommons.BUFFER);
        new ServiceLocator().start();
        new MessageRX();
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
    }
}