package br.com.schumaker.network;

import java.io.DataInputStream;
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
        this.setName("MessageHubSession");
    }

    private void tramsmit(String line) {
        new CourrierMoyeu(line).start();
    }

    @Override
    public void run() {
        try {
            InputStream inps = socket.getInputStream();
            OutputStream outs = socket.getOutputStream();
            DataInputStream dis = new DataInputStream(inps);
            PrintWriter out = new PrintWriter(outs, true);
            String line = dis.readUTF();
            tramsmit(line);
            inps.close();
            outs.close();
            dis.close();
        } catch (Exception e) {
            System.err.println("MessageRxSeesion:Run:\n" + e);
        }
    }
}
