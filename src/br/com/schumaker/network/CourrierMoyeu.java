package br.com.schumaker.network;

import br.com.schumaker.core.Engine;
import br.com.schumaker.model.User;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 *
 * @author hudson schumaker
 */
public class CourrierMoyeu implements Runnable {

    private String esms;

    public CourrierMoyeu(String esms) {
        this.esms = esms;
    }

    private void loopMessage(String message) {
        for (User u : Engine.getInstance().getList()) {
            try {
                Socket conexao = new Socket(u.getIp(), HsCommons.TXPORT);
                DataOutputStream saida = new DataOutputStream(conexao.getOutputStream());
                saida.writeUTF(message);
                conexao.close();
            } catch (Exception exc) {
                System.out.println(exc.toString());
            }
        }
    }

    @Override
    public void run() {
        loopMessage(esms);
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
    }
}
