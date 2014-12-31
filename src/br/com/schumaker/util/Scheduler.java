package br.com.schumaker.util;

import br.com.schumaker.network.Hunter;

/**
 *
 * @author hudson schumaker
 */
public class Scheduler implements Runnable {

    private boolean run;

    @Override
    public void run() {
        while (run) {
            try {
                Thread.sleep(1000 * 60);
                new Hunter().start();
            } catch (InterruptedException ex) {
                System.err.println("Scheduler:run: " + ex);
            }
        }
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.setName("Scheduler");
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
