package Itens;

import Jogo.Jogo;
import Personagens.Dinossauro;
import Personagens.Jogador;

public class ArmaDardos extends Arma {

    private int municao;

    public ArmaDardos() {
        super("Arma de Dardos e Munição");
        municao = 2;
    }

    public boolean atacar(Dinossauro alvo, int dado, Jogador jogador) {

        Jogo jogo = jogador.getJogo();

        if (municao == 0) {
            if (jogo != null) {
                jogo.mensagem("Sem munição!");
            }
            return false;
        }

        municao--;

        if (jogo != null) {
            jogo.mensagem("Dardo disparado!");
        }

        alvo.receberDardo(2, jogador);

        return true;
    }

    public void adicionarMunicao() {
        municao += 2;
    }

    public int getMunicao() {
        return municao;
    }
}