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
    private String ultimaMensagem = "";
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

        ultimaMensagem = "O combate começou!";

        if (jogo != null) {
            jogo.mensagem("Você entrou em combate contra o " + dinossauro.getEspecie() + "!");
        }
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public String getUltimaMensagem() {
        return ultimaMensagem;
    }

    public boolean combateAtivo() {
        return combateAtivo;
    }

    public boolean jogadorVenceu() {
        return dinossauro.getVida() <= 0;
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

    public Jogador getJogador() {
        return jogador;
    }

    public Dinossauro getDinossauro() {
        return dinossauro;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public Random getRandom() {
        return random;
    }

    public boolean ehTurnoJogador() {
        return turnoJogador;
    }

    public void ataqueDinossauro() {

        int dado = random.nextInt(3) + 1;

        if (dado > jogador.getPercepcao()) {
            dinossauro.morder(jogador);
            ultimaMensagem = "O " + dinossauro.getEspecie() + " te mordeu!";
        } else {
            ultimaMensagem = "Você percebeu o ataque a tempo e desviou!";
        }

        turnoJogador = true;

        verificarFimCombate();
    }

    public void atacarCorpoACorpo() {

        int dado = random.nextInt(6) + 1;

        ultimaMensagem = "Você atacou corpo a corpo! (Dado: " + dado + ")";

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
            ultimaMensagem = "Você atirou um dardo! (Dado: " + dado + ")";
            turnoJogador = false;
        } else {
            ultimaMensagem = "Você não possui dardos disponíveis!";
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

        boolean usou = jogador.usarKitMedico();
        if (usou) {
            ultimaMensagem = "Você usou o kit médico!";
        } else {
            ultimaMensagem = "Não foi possível usar o kit médico.";
        }

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
            return false;
        }

        jogador.fugir(tabuleiro);

        combateAtivo = false;

        return true;
    }

    public void acaoFugir() {
        fugir();
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

            System.out.println("Dinossauro derrotado!");

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