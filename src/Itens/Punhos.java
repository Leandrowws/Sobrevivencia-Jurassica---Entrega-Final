package Itens;

import Jogo.Jogo;
import Personagens.Dinossauro;
import Personagens.Jogador;

public class Punhos extends Arma {

    public Punhos() {
        super("Punhos");
    }

    public boolean atacar(Dinossauro alvo, int dado, Jogador jogador) {

        Jogo jogo = jogador.getJogo();

        if (dado <= 2) {
            if (jogo != null) {
                jogo.mensagem("Você errou o soco!");
            }
            return false;
        }
        if (dado <= 5) {
            if (alvo.receberAtaqueBasico(1, jogador) && jogo != null) {
                jogo.mensagem("O soco acertou! Causou 1 de dano!");
            }
        } else {
            if (alvo.receberAtaqueBasico(2, jogador) && jogo != null) {
                jogo.mensagem("O soco acertou criticamente! Causou 2 de dano!");
            }
        }

        return true;
    }
}