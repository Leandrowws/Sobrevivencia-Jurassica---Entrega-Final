package Jogo;

import Personagens.Jogador;
import Personagens.Dinossauro;
import Personagens.DinossauroMovel;

public class MovimentoDinossauro implements Runnable {

    private Dinossauro dinossauro;
    private DinossauroMovel dinossauroMovel;
    private Tabuleiro tabuleiro;
    private long intervalo;
    private Jogo jogo;

    public MovimentoDinossauro(Dinossauro dinossauro, Tabuleiro tabuleiro, long intervalo, Jogo jogo) {
        this.dinossauro = dinossauro;
        this.dinossauroMovel = (DinossauroMovel) dinossauro;
        this.tabuleiro = tabuleiro;
        this.intervalo = intervalo;
        this.jogo = jogo;
    }

    public void run() {

        while (dinossauro.estaVivo() && !Thread.currentThread().isInterrupted()) {

            Jogador jogadorEncontrado = dinossauroMovel.mover(tabuleiro);

            if (jogadorEncontrado != null) {

                jogo.iniciarCombateDinossauro(dinossauro);

                break;
            }

            jogo.atualizarTelaPrincipal();

            try {
                Thread.sleep(intervalo);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}