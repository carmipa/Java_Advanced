package com.example;

public class Jogo {

    protected Monte monte = new Monte();

    protected Jogador jogador = new Jogador();

    protected Computador computador = new Computador();

    public Jogo() {
        this.monte.embaralhar();
    }

    public Carta distribuirCartaParaJogador(Jogador jogador){

        if (jogador.parou())
            return null;
          Carta carta = monte.virar();
          jogador.receberCarta(carta);
          return carta;
    }

    public boolean acabou(){
        if (jogador.getPontos() > 21)
            return true;
        if (computador.getPontos() > 21)
            return true;
        if (computador.parou() && jogador.parou())
            return true;
        return false;
    }

    public String resulata(){
        return "Descubra qual é o resultado...";
    }
}

// arlie return