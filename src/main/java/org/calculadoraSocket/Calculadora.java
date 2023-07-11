package org.calculadoraSocket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Calculadora {
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

            oos.writeObject("\n|******** Escolha uma operação ********|");
            oos.writeObject("|--------------------------------------|");
            oos.writeObject("| Adição - Digite \"Somar\"              |");
            oos.writeObject("| Subtração - Digite \"Subtrair\"        |");
            oos.writeObject("| Multiplicação - Digite \"Multiplicar\" |");
            oos.writeObject("| Divisão - Digite \"Dividir\"           |");
            oos.writeObject("| Finalizar calculadora - Digite \"Fim\" |");
            oos.writeObject("|--------------------------------------|");
            oos.writeObject("Informe a operação desejada: ");

            String string = (String) ois.readObject();
            String ops = string.trim();

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

        for (int i = 1; i < count; i++) {
            if (nums[i] == 0) {
                return "ERROR! Não pode ser feita divisão por 0.";
            }
        }

        int result = nums[0];

        for (int i = 1; i < count; i++) {
            result /= nums[i];
        }

        return Integer.toString(result);
    }

    void recebeNums(int idOps){
        try {

            oos.writeObject("Informe até 20 números -> ");

            String string = (String) ois.readObject();

            String[] numeros = string.trim().split("\\s+");

            if (numeros.length > 20) {
                oos.writeObject("A quantidade de números excedeu 20.");
                //count = 20;
            } else {
                count = numeros.length;
            }

            for (int i = 0; i < count; i++) {
                nums[i] = Integer.parseInt(numeros[i]);
            }

            switch (idOps) {
                case 1:
                    oos.writeObject("\nResultado adição: " + soma());
                    break;
                case 2:
                    oos.writeObject("\nResultado subtração: " + subtrai());
                    break;
                case 3:
                    oos.writeObject("\nResultado multiplicação: " + multiplica());
                    break;
                case 4:
                    oos.writeObject("\nResultado divisão: " + divide());
                    break;
            }
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
