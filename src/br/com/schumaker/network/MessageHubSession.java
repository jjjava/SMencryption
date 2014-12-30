package br.com.schumaker.network;

import br.com.schumaker.core.Engine;
import br.com.schumaker.model.User;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author hudson schumaker
 */
public class MessageHubSession extends Thread {

    private Socket socket = null;

    public MessageHubSession(Socket socket) {
        this.socket = socket;
    }

    private void tramsmit(String line) {
        for (User u : Engine.getInstance().getList()) {
            try {
                Socket conexao = new Socket(u.getIp(), HsCommons.TXPORT);
                DataOutputStream saida = new DataOutputStream(conexao.getOutputStream());
                saida.writeUTF(line);
                conexao.close();
            } catch (Exception exc) {
                System.out.println(exc.toString());
            }
        }
    }

    @Override
    public void run() {
        try {
            InputStream inps = socket.getInputStream();
            OutputStream outs = socket.getOutputStream();
            DataInputStream dis = new DataInputStream(inps);
            PrintWriter out = new PrintWriter(outs, true);
            //out.println("> Connected to server...");
            boolean done = false;
//            while (!done) {
            String line = dis.readUTF();
           // out.println(line);
           
            tramsmit(line);
             System.out.println("l: "+line);
//            }
            inps.close();
            outs.close();
            dis.close();
        } catch (Exception e) {
            System.err.println("MessageRxSeesion:Run:\n" + e);
        }
    }
}
