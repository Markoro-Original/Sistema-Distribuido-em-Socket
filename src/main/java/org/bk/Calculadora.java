package org.bk;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Calculadora {
    int[] nums = new int[20];
    int count;
    Socket socket;

    Calculadora(Socket socket){
        this.socket = socket;
    }

    int menuOps() {

        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            oos.writeObject("|******** Escolha uma operação ********|");
            oos.writeObject("|--------------------------------------|");
            oos.writeObject("| Adição - Digite \"Somar\"              |");
            oos.writeObject("| Subtração - Digite \"Subtrair\"        |");
            oos.writeObject("| Multiplicação - Digite \"Multiplicar\" |");
            oos.writeObject("| Divisão - Digite \"Dividir\"           |");
            oos.writeObject("| Finalizar calculadora - Digite \"Fim\" |");
            oos.writeObject("|--------------------------------------|");
            oos.writeObject("Informe a operação desejada: ");

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            String ops = (String) ois.readObject();

            ois.close();
            oos.close();

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
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

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
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            oos.writeObject("Informe até 20 números\n-> ");

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
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
