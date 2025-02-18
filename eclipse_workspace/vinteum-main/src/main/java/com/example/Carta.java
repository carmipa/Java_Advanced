package com.example;

public class Carta {

    private int numero;
    private Naipe naipe;
    
    private final String[] NAIPES = {"", "ACE", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King" };

    public Carta(int numero, Naipe naipe) {
        this.numero = numero;
        this.naipe = naipe;
    }

    public String imagePath (){
    	
//   	String prefixo = numero + "";//    	
//    	if (numero == 1) prefixo = "ACE";
//    	if (numero == 11) prefixo = "Jack";
//    	if (numero == 12) prefixo = "Queen";
//    	if (numero == 13) prefixo = "King";    	
    	
    	return "classic-cards/" + NAIPES[numero] + naipe + ".png";
    }

    public int getNumero() {
        return numero;
    }

    public Naipe getNaipe() {
        return naipe;
    }
}
