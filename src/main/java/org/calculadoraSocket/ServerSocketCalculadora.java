package org.calculadoraSocket;

import com.sun.security.ntlm.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

class Calculadora {
    int[] nums = new int[20];
    int count;
    Socket socket;
    ObjectOutputStream oos;
    ObjectInputStream ois;

    Calculadora(Socket socket, ObjectOutputStream oos,
    ObjectInputStream ois){
        this.socket = socket;
        this.oos = oos;
        this.ois = ois;
    }

    int menuOps() {

        try {

            oos.writeObject("|******** Escolha uma operação ********|");
            oos.writeObject("|--------------------------------------|");
            oos.writeObject("| Adição - Digite \"Somar\"              |");
            oos.writeObject("| Subtração - Digite \"Subtrair\"        |");
            oos.writeObject("| Multiplicação - Digite \"Multiplicar\" |");
            oos.writeObject("| Divisão - Digite \"Dividir\"           |");
            oos.writeObject("| Finalizar calculadora - Digite \"Fim\" |");
            oos.writeObject("|--------------------------------------|");
            oos.writeObject("Informe a operação desejada: ");

            String ops = (String) ois.readObject();

            //ois.close();
            //oos.close();

            switch (ops) {
                case "Somar":
                    return 1;
                case "Subtrair":
                    return 2;
                case "Multiplicar":
                    return 3;
                case "Dividir":
                    return 4;
                case "Fim":
                    return -1;
                default:
                    System.out.println("Opção inválida.");
                    return 0;
            }

        } catch (IOException e){
            e.printStackTrace();
            return 0;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    int soma(){
        int result = nums[0];
        for (int i = 1; i < count; i++){
            result += nums[i];
        }
        return result;
    }

    int subtrai(){
        int result = nums[0];
        for (int i = 1; i < count; i++){
            result -= nums[i];
        }
        return result;
    }

    int multiplica(){
        int result = nums[0];
        for (int i = 1; i < count; i++){
            result *= nums[i];
        }
        return result;
    }

    String divide(){
        try {

            for (int i = 1; i < count; i++) {
                if (nums[i] == 0) {
                    oos.writeObject("Não pode ser feita divisão por 0.");
                    oos.close();
                    return "Divisão inválida.";
                }
            }

            int result = nums[0];

            for (int i = 1; i < count; i++) {
                result /= nums[i];
            }

            return Integer.toString(result);

        } catch (IOException e){
            e.printStackTrace();
            return "ERRO!";
        }
    }

    void recebeNums(int idOps){
        try {

            oos.writeObject("Informe até 20 números -> ");

            String string = (String) ois.readObject();

            String[] numeros = string.trim().split("\\s+");

            if (numeros.length > 20) {
                oos.writeObject("A quantidade de números excedeu 20. Apenas os 20 primeiros números serão calculados.");
                count = 20;
            } else {
                count = numeros.length;
            }

            for (int i = 0; i < count; i++) {
                nums[i] = Integer.parseInt(numeros[i]);
            }

            switch (idOps) {
                case 1:
                    oos.writeObject("\nResultado adição: " + soma());
                    oos.close();
                    break;
                case 2:
                    oos.writeObject("\nResultado subtração: " + subtrai());
                    oos.close();
                    break;
                case 3:
                    oos.writeObject("\nResultado multiplicação: " + multiplica());
                    oos.close();
                    break;
                case 4:
                    oos.writeObject("\nResultado divisão: " + divide());
                    oos.close();
                    break;
            }
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}

class ConnectionHandler implements Runnable{

    private Socket socket;

    public ConnectionHandler(Socket socket){
        this.socket = socket;

        Thread thread = new Thread(this);
        thread.start();
    }
    @Override
    public void run() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            Calculadora calculadora = new Calculadora(socket, oos, ois);
            int ops;
            ops = calculadora.menuOps();
            calculadora.recebeNums(ops);


            //socket.close();
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
