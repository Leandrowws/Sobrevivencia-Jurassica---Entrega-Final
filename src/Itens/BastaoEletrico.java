package Itens;

import Jogo.Jogo;
import Personagens.Dinossauro;
import Personagens.Jogador;

public class BastaoEletrico extends Arma {

    public BastaoEletrico() {
        super("Bastão Elétrico");
    }

    public boolean atacar(Dinossauro alvo, int dado, Jogador jogador) {

        Jogo jogo = jogador.getJogo();

        if (dado <= 1) {
            if (jogo != null) {
                jogo.mensagem("Você errou o golpe com o bastão!");
            }
            return false;
        }
        if (dado <= 4) {
            alvo.receberBastaoEletrico(1);
            if (jogo != null) {
                jogo.mensagem("O bastão acertou! Causou 1 de dano!");
            }
        } else {
            alvo.receberBastaoEletrico(2);
            if (jogo != null) {
                jogo.mensagem("O bastão acertou criticamente! Causou 2 de dano!");
            }
        }

        return true;
    }
}