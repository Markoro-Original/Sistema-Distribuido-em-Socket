	
package org.clienteServidor;
 
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.lang.Runnable;
import java.lang.Thread;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketExample {
    private ServerSocket server;
    private int port = 15000;
 
    public ServerSocketExample() {
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    public static void main(String[] args) {
        ServerSocketExample example = new ServerSocketExample();
        example.handleConnection();
    }
 
    public void handleConnection() {
        System.out.println("Waiting for client message...");
 
        //
        // The server do a loop here to accept all connection initiated by the
        // client application.
        //
        while (true) {
            try {
                Socket socket = server.accept();
                new ConnectionHandler(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
 
class ConnectionHandler implements Runnable {
    private Socket socket;
 
    public ConnectionHandler(Socket socket) {
        this.socket = socket;
 
        Thread t = new Thread(this);
        t.start();
    }
 
    public void run() {
        try
        {
            //
            // Read a message sent by client application
            //
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            String message = (String) ois.readObject();
            System.out.println("Cliente: " + message);
 
            //
            // Send a response information to the client application
            //
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject("General Kenobi!");

            message = (String) ois.readObject();
            System.out.println("Cliente: " + message);

            oos.writeObject("General Kenobi!");
 
            ois.close();
            oos.close();
            socket.close();
 
            System.out.println("Waiting for client message...");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
