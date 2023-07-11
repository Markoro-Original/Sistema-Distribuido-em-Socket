package org.clienteServidor;
 
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
 
public class ClientSocketExample {
    public static void main(String[] args) {
        try {
            //
            // Create a connection to the server socket on the server application
            //
            InetAddress host = InetAddress.getLocalHost();
            Socket socket = new Socket(host.getHostName(), 15000);
 
            //
            // Send a message to the client application
            //
            String message = "Hello there...";
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Cliente: " + message);
            oos.writeObject(message);
 
            //
            // Read and display the response message sent by server application
            //
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            String resposta = (String) ois.readObject();
            System.out.println("Servidor: " + resposta);

            System.out.println("Cliente: " + message);
            oos.writeObject(message);

            resposta = (String) ois.readObject();
            System.out.println("Servidor: " + resposta);
 
            ois.close();
            oos.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
