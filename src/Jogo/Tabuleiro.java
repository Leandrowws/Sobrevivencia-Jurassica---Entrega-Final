package Jogo;

import java.util.ArrayList;
import java.util.Random;

import Personagens.Compsognato;
import Personagens.Dinossauro;
import Personagens.Troodonte;
import Personagens.Velociraptor;
import Personagens.Jogador;
import Personagens.Personagem;
import Personagens.TRex;

public class Tabuleiro {

    private Posicao[][] mapa;
    private ArrayList<Dinossauro> dinossauros;
    private Random random;

    public Tabuleiro(int tamanho, long semente) {

        random = new Random(semente);
        mapa = new Posicao[tamanho][tamanho];

        dinossauros = new ArrayList<>();

        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                mapa[i][j] = new Posicao();
            }
        }
    }

    public boolean contemEsseDinossauro(Dinossauro d) {
        return dinossauros.contains(d);
    }

    public void removerDinossauro(Dinossauro d) {
        dinossauros.remove(d);
        mapa[d.getLinha()][d.getColuna()].setPersonagem(null);
    }

    public boolean todosDinossaurosDerrotados() {
        return dinossauros.isEmpty();
    }

    public int getTamanho() {
        return mapa.length;
    }

    public Posicao getPosicao(int linha, int coluna) {
        return mapa[linha][coluna];
    }

    public Personagem getPersonagem(int linha, int coluna) {
        return mapa[linha][coluna].getPersonagem();
    }

    public void posicionarJogador(Jogador j) {
        j.setPosicao(0, 0);
        mapa[0][0].setPersonagem(j);
    }

    public void adicionarDinossauro(Dinossauro d, int l, int c) {
        dinossauros.add(d);
        mapa[l][c].setPersonagem(d);
        d.setPosicao(l, c);
    }

    public void adicionarParede(int l, int c) {
        mapa[l][c].setParede(true);
    }

    public void adicionarCaixa(Caixa caixa, int l, int c) {
        mapa[l][c].setCaixa(caixa);
    }

    public void imprimirMapaCompleto() {
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                if (mapa[i][j].isParede()) {
                    System.out.printf("# ");
                } else if (mapa[i][j].getPersonagem() != null) {
                    System.out.printf("%c ", mapa[i][j].getPersonagem().getSimbolo());
                } else if (mapa[i][j].getCaixa() != null) {
                    System.out.printf("X ");
                } else {
                    System.out.printf(". ");
                }
            }
            System.out.printf("\n");
        }
    }

    public void imprimirMapaVisivel(Jogador jogador) {
        boolean[][] visivel = new boolean[mapa.length][mapa.length];
        int linha = jogador.getLinha();
        int coluna = jogador.getColuna();
        int alcance = jogador.getPercepcao() * 2;
        visivel[linha][coluna] = true;
        for (int i = linha - 1; i >= 0 && linha - i <= alcance; i--) {
            visivel[i][coluna] = true;
            if (!mapa[i][coluna].isLivre()) { // para cima
                break;
            }
        }
        for (int i = linha + 1; i < mapa.length && i - linha <= alcance; i++) {
            visivel[i][coluna] = true;
            if (!mapa[i][coluna].isLivre()) { // para baixo
                break;
            }
        }
        for (int j = coluna - 1; j >= 0 && coluna - j <= alcance; j--) {
            visivel[linha][j] = true;
            if (!mapa[linha][j].isLivre()) { // para esquerda
                break;
            }
        }
        for (int j = coluna + 1; j < mapa.length && j - coluna <= alcance; j++) {
            visivel[linha][j] = true;
            if (!mapa[linha][j].isLivre()) { // para direita
                break;
            }
        }
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                if (!visivel[i][j]) {
                    System.out.printf("? ");
                } else if (mapa[i][j].isParede()) {
                    System.out.printf("# ");
                } else if (mapa[i][j].getPersonagem() != null) {
                    System.out.printf("%c ", mapa[i][j].getPersonagem().getSimbolo());
                } else if (mapa[i][j].getCaixa() != null) {
                    System.out.printf("X ");
                } else {
                    System.out.printf(". ");
                }
            }
            System.out.println();
        }
    }

    private int[] posicaoLivreAleatoria() {
        int linha;
        int coluna;
        do {
            linha = random.nextInt(mapa.length);
            coluna = random.nextInt(mapa.length);
        } while (mapa[linha][coluna].isLivre() == false);
        return new int[] { linha, coluna };
    }

    public void gerarParedes() {
        int porcentagem = random.nextInt(16) + 5;
        int quantidade = (mapa.length * mapa.length * porcentagem) / 100;
        for (int i = 0; i < quantidade; i++) {
            int pos[] = posicaoLivreAleatoria();
            adicionarParede(pos[0], pos[1]);
        }
    }

    private void colocarCaixaPosAleatoria(Caixa caixa) {
        int[] pos = posicaoLivreAleatoria();
        adicionarCaixa(caixa, pos[0], pos[1]);
    }

    public void gerarCaixas() {
        colocarCaixaPosAleatoria(new Caixa(TipoCaixa.KIT_MEDICO));
        colocarCaixaPosAleatoria(new Caixa(TipoCaixa.BASTAO_ELETRICO));
        colocarCaixaPosAleatoria(new Caixa(TipoCaixa.DARDO_TRANQUILIZANTE));
        colocarCaixaPosAleatoria(new Caixa(TipoCaixa.DARDO_TRANQUILIZANTE));
        colocarCaixaPosAleatoria(new Caixa(TipoCaixa.COMPSOGNATO_SURPRESA));
    }

    public void gerarDinossauros() {
        adicionarDinossauro(new TRex(), mapa.length - 1, mapa.length - 1);
        for (int i = 0; i < 2; i++) {
            int[] pos = posicaoLivreAleatoria();
            adicionarDinossauro(new Compsognato(), pos[0], pos[1]);
        }
        for (int i = 0; i < 2; i++) {
            int[] pos = posicaoLivreAleatoria();
            adicionarDinossauro(new Velociraptor(), pos[0], pos[1]);
        }
        for (int i = 0; i < 5; i++) {
            int[] pos = posicaoLivreAleatoria();
            adicionarDinossauro(new Troodonte(), pos[0], pos[1]);
        }
    }

    public void gerarMapa() {
        gerarDinossauros();
        gerarParedes();
        gerarCaixas();

    }

    public void reposicionarJogador(Personagem p, int linha, int coluna) {
        mapa[p.getLinha()][p.getColuna()].setPersonagem(null);
        p.setPosicao(linha, coluna);
        mapa[linha][coluna].setPersonagem(p);
    }

    public Jogador moverDinossauro(Dinossauro d) {
        int direcao = random.nextInt(4);
        int deltaLinha = 0;
        int deltaColuna = 0;

        switch (direcao) {
            case 0:
                deltaLinha = -1;
                break;
            case 1:
                deltaLinha = 1;
                break;
            case 2:
                deltaColuna = -1;
                break;
            case 3:
                deltaColuna = 1;
                break;
        }

        int novaLinha = d.getLinha() + deltaLinha;
        int novaColuna = d.getColuna() + deltaColuna;

        if (novaLinha < 0 || novaLinha >= mapa.length || novaColuna < 0 || novaColuna >= mapa.length) {
            return null;
        }
        if (mapa[novaLinha][novaColuna].isParede()) {
            return null;
        }

        Personagem p = mapa[novaLinha][novaColuna].getPersonagem();

        if (p instanceof Dinossauro) {
            return null;
        }

        if (p instanceof Jogador) {
            return (Jogador) p;
        }

        mapa[d.getLinha()][d.getColuna()].setPersonagem(null);
        d.setPosicao(novaLinha, novaColuna);
        mapa[novaLinha][novaColuna].setPersonagem(d);
        return null;
    }

    public Dinossauro moverTodosDinossauros() {
        for (Dinossauro d : dinossauros) {
            Jogador jogadorEncontrado = d.moverDinossauro(this);
            if (jogadorEncontrado != null) {
                return d;
            }
        }
        return null;
    }

}
