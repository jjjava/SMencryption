package br.com.schumaker.core;

import br.com.schumaker.model.User;
import br.com.schumaker.network.HsCommons;
import br.com.schumaker.network.MessageHub;
import br.com.schumaker.network.ServiceLocator;
import br.com.schumaker.util.Scheduler;
import java.util.ArrayList;

/**
 *
 * @author hudson schumaker Server
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
        //printList();
    }

    public synchronized void removeUser(User u) {
        list.remove(u);
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
        new Scheduler().start();
        new MessageHub();
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.setName("Engine");
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
    }
}
