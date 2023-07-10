package org.calculadoraLocal;

import java.util.Scanner;

public class Calculadora {
    int[] nums = new int[20];
    int count;

    void recebeNums(){
        Scanner scan = new Scanner(System.in);

        System.out.print("Informe até 20 números\n-> ");
        String[] numeros = scan.nextLine().split(" ");

        count = numeros.length;

        for(int i = 0; i < count; i++){
            nums[i] = Integer.parseInt(numeros[i]);
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

}
class Main {
    public static void main(String[] args) {

        Calculadora cal = new Calculadora();

        cal.recebeNums();

        for (int i = 0; i < cal.count; i++) System.out.print(cal.nums[i] + " ");

        System.out.println("\nResultado soma: " + cal.soma());
        System.out.println("Resultado subtração: " + cal.subtrai());
        System.out.println("Resultado multiplicação: " + cal.multiplica());
        System.out.println("Resultado divisão: " + cal.divide());
    }
}