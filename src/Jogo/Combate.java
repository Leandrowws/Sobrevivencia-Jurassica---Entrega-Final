package Jogo;

import java.util.Random;

import Personagens.Dinossauro;
import Personagens.Jogador;

public class Combate {

    private Random random;

    private Jogador jogador;
    private Dinossauro dinossauro;
    private Tabuleiro tabuleiro;
    private Jogo jogo;
    private boolean turnoJogador;
    private boolean combateAtivo;

    public Combate(long semente) {
        random = new Random(semente);
    }

    public void iniciar(Jogador jogador, Dinossauro dinossauro, Tabuleiro tabuleiro, boolean jogadorEncontrou) {

        this.jogador = jogador;
        this.dinossauro = dinossauro;
        this.tabuleiro = tabuleiro;

        combateAtivo = true;
        turnoJogador = dinossauro.emboscaJogador(jogadorEncontrou);

        if (jogo != null) {
            jogo.mensagem("Você entrou em combate contra o " + dinossauro.getEspecie() + "!");
        }
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public boolean combateAtivo() {
        return combateAtivo;
    }

    public int getNumeroAcoes() {
        return jogador.getNumeroAcoesAtaque();
    }

    public int getVidaJogador() {
        return jogador.getVida();
    }

    public String getNomeDinossauro() {
        return dinossauro.getEspecie();
    }

    public int getVidaDinossauro() {
        return dinossauro.getVida();
    }

    public boolean podeFugir() {
        return dinossauro.permiteFuga();
    }

    public boolean jogadorMorreu() {
        return jogador.getVida() <= 0;
    }

    public boolean ehTurnoJogador() {
        return turnoJogador;
    }

    public void ataqueDinossauro() {

        int dado = random.nextInt(3) + 1;

        if (dado > jogador.getPercepcao()) {
            dinossauro.morder(jogador);
            jogo.mensagem("O " + dinossauro.getEspecie() + " te mordeu!");
        } else {
            jogo.mensagem("Você percebeu o ataque a tempo e desviou!");
        }

        turnoJogador = true;

        verificarFimCombate();
    }

    public void atacarCorpoACorpo() {

        int dado = random.nextInt(6) + 1;

        if (jogo != null) {
            jogo.mensagem("Você atacou corpo a corpo! (Dado: " + dado + ")");
        }

        jogador.atacarCorpoACorpo(dinossauro, dado);

        turnoJogador = false;

        verificarFimCombate();
    }

    public void acaoAtacarCorpoACorpo() {

        atacarCorpoACorpo();

        if (!combateAtivo()) {
            return;
        }

        executarTurnoDinossauro();
    }

    public boolean atacarComDardo() {

        int dado = random.nextInt(6) + 1;

        boolean atacou = jogador.atirarDardo(dinossauro);

        if (atacou) {
            if (jogo != null) {
                jogo.mensagem("Você atirou um dardo! (Dado: " + dado + ")");
            }
            turnoJogador = false;
        }

        verificarFimCombate();

        return atacou;
    }

    public void acaoDardo() {

        boolean atacou = atacarComDardo();

        if (!atacou) {
            return;
        }

        if (!combateAtivo()) {
            return;
        }

        executarTurnoDinossauro();
    }

    public void usarKitMedico() {

        jogador.usarKitMedico();

        turnoJogador = false;

    }

    public void acaoKitMedico() {

        usarKitMedico();

        if (!combateAtivo()) {
            return;
        }

        executarTurnoDinossauro();
    }

    public boolean fugir() {

        if (!dinossauro.permiteFuga()) {
            if (jogo != null) {
                jogo.mensagem("Não é possível fugir deste dinossauro!");
            }
            return false;
        }

        boolean conseguiuFugir = jogador.fugir(tabuleiro);

        if (!conseguiuFugir) {
            if (jogo != null) {
                jogo.mensagem("Você tentou fugir, mas o caminho estava bloqueado!");
            }
            return false;
        }

        combateAtivo = false;

        if (jogo != null) {
            jogo.mensagem("Você fugiu do combate!");
        }

        return true;
    }

    public void executarTurnoDinossauro() {

        if (!combateAtivo) {
            return;
        }

        if (!turnoJogador) {
            ataqueDinossauro();
        }

    }

    private void verificarFimCombate() {

        if (dinossauro.getVida() <= 0) {

            jogador.getJogo().mensagem("Dinossauro derrotado!");

            if (tabuleiro.contemEsseDinossauro(dinossauro)) {
                tabuleiro.removerDinossauro(dinossauro);
            }

            combateAtivo = false;
        }

        if (jogador.getVida() <= 0) {
            combateAtivo = false;
        }
    }

}