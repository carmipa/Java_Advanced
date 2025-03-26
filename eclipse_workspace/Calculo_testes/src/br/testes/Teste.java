package br.testes;

import java.util.Scanner;

public class Teste {

	public static void main(String[] args) {
		
		Scanner inteiros = new Scanner(System.in);

        System.out.println("Valor do triângulo X: ");
        int xA = inteiros.nextInt();
        int xB = inteiros.nextInt();
        int xC = inteiros.nextInt();

        // Optionally, you can print the value to confirm it was read correctly.
        System.out.println("Você digitou: " + xA + ", " + xB + ", " + xC);

        // Close the Scanner to free up resources
        inteiros.close();
    }
	

}
