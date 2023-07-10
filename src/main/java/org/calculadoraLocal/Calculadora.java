package org.calculadoraLocal;

import java.util.Scanner;

public class Calculadora {
    int[] nums = new int[20];
    int count;

    int menuOps() {
        Scanner menu = new Scanner(System.in);

        System.out.println("|******** Escolha uma operação ********|");
        System.out.println("|--------------------------------------|");
        System.out.println("| Adição - Digite \"Somar\"              |");
        System.out.println("| Subtração - Digite \"Subtrair\"        |");
        System.out.println("| Multiplicação - Digite \"Multiplicar\" |");
        System.out.println("| Divisão - Digite \"Dividir\"           |");
        System.out.println("| Finalizar calculadora - Digite \"Fim\" |");
        System.out.println("|--------------------------------------|");
        System.out.print("Informe a operação desejada: ");

        String ops = menu.next();

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
        for(int i = 1; i < count; i++){
            if(nums[i] == 0){
                System.out.println("Não pode ser feita divisão por 0.");
                return "ERRO!";
            }
        }
        int result = nums[0];
        for (int i = 1; i < count; i++){
            result /= nums[i];
        }
        return Integer.toString(result);
    }

    void recebeNums(int idOps){
        Scanner scan = new Scanner(System.in);

        System.out.print("Informe até 20 números\n-> ");
        String[] numeros = scan.nextLine().trim().split("\\s+");

        if(numeros.length > 20){
            System.out.println("A quantidade de números excedeu 20. Apenas os 20 primeiros números serão calculados.");
            count = 20;
        }else{
            count = numeros.length;
        }

        for(int i = 0; i < count; i++){
            nums[i] = Integer.parseInt(numeros[i]);
        }

        switch (idOps) {
            case 1:
                System.out.println("\nResultado adição: " + soma());
                break;
            case 2:
                System.out.println("\nResultado subtração: " + subtrai());
                break;
            case 3:
                System.out.println("\nResultado multiplicação: " + multiplica());
                break;
            case 4:
                System.out.println("\nResultado divisão: " + divide());
                break;
        }
    }

}
class Main {
    public static void main(String[] args) {

        Calculadora cal = new Calculadora();
        int idOps;

        while (true) {
            idOps = cal.menuOps();

            if(idOps == -1){
                System.out.println("Finalizando calculadora.");
                break;
            }

            if(idOps != 0){
                cal.recebeNums(idOps);
            }
        }
    }
}