package org.calculadoraSocket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

class ConnectionHandler implements Runnable{

    private Socket socket;

    public ConnectionHandler(Socket socket){
        this.socket = socket;

        Thread thread = new Thread(this);
        thread.start();
    }

    public void run() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            Calculadora calculadora = new Calculadora(socket, oos, ois);

            int ops = calculadora.menuOps();
            if(ops != -1 && ops != 0) {
                calculadora.recebeNums(ops);
            }

            oos.close();
            ois.close();
            socket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
public class ServerSocketCalculadora {

    private ServerSocket server;

    private int port = 15000;

    public ServerSocketCalculadora(){
        try {
            server = new ServerSocket(port);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void handlerConnection(){
        System.out.println("Servidor da calculadora rodando.");

        while (true){
            try {
                Socket socket = server.accept();
                new ConnectionHandler(socket);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ServerSocketCalculadora server = new ServerSocketCalculadora();
        server.handlerConnection();
    }

}
