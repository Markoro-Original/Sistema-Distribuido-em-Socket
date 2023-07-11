package org.calculadoraSocket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientSocketCalculadora {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        try {
            InetAddress host = InetAddress.getLocalHost();
            Socket socket = new Socket(host.getHostName(), 15000);

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            while(true){
                String string = (String) ois.readObject();
                System.out.println(string);
                if(string.equals("Informe a operação desejada: ")){
                    break;
                }
            }

            oos.writeObject(scan.nextLine());

            String string = (String) ois.readObject();
            System.out.print(string);

            oos.writeObject(scan.nextLine());

            System.out.print((String) ois.readObject());

        } catch (UnknownHostException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
