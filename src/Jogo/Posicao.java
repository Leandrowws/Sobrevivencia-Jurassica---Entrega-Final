package Jogo;

import Caixas.Caixa;
import Personagens.*;

public class Posicao {
    private Personagem personagem;
    private Caixa caixa;
    private boolean parede;

    public Personagem getPersonagem() {
        return personagem;
    }

    public void setPersonagem(Personagem p) {
        personagem = p;
    }

    public Caixa getCaixa() {
        return caixa;
    }

    public void setCaixa(Caixa c) {
        caixa = c;
    }

    public boolean isParede() {
        return parede;
    }

    public void setParede(boolean p) {
        parede = p;
    }

    public boolean isLivre() {
        return personagem == null && caixa == null && !parede;
    }

}
