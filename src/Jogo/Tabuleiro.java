package Jogo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

    public void posicionarJogador(Jogador j, int linha, int coluna) {
        j.setPosicao(linha, coluna);
        mapa[linha][coluna].setPersonagem(j);
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

    public void carregarMapa(File arquivo, Jogador jogador) throws IOException {
        BufferedReader leitor = new BufferedReader(new FileReader(arquivo));

        for (int linha = 0; linha < mapa.length; linha++) {

            String texto = leitor.readLine();

            for (int coluna = 0; coluna < mapa.length; coluna++) {

                char c = texto.charAt(coluna);

                switch (c) {
                    case '#':
                        adicionarParede(linha, coluna);
                        break;
                    case 'J':
                        posicionarJogador(jogador, linha, coluna);
                        break;
                    case 'R':
                        adicionarDinossauro(new TRex(), linha, coluna);
                        break;
                    case 'V':
                        adicionarDinossauro(new Velociraptor(), linha, coluna);
                        break;
                    case 'T':
                        adicionarDinossauro(new Troodonte(), linha, coluna);
                        break;
                    case 'C':
                        adicionarDinossauro(new Compsognato(), linha, coluna);
                        break;
                    case 'K':
                        adicionarCaixa(new Caixa(TipoCaixa.KIT_MEDICO), linha, coluna);
                        break;
                    case 'B':
                        adicionarCaixa(new Caixa(TipoCaixa.BASTAO_ELETRICO), linha, coluna);
                        break;
                    case 'D':
                        adicionarCaixa(new Caixa(TipoCaixa.DARDO_TRANQUILIZANTE), linha, coluna);
                        break;
                    case 'S':
                        adicionarCaixa(new Caixa(TipoCaixa.COMPSOGNATO_SURPRESA), linha, coluna);
                        break;
                }
            }
        }
        leitor.close();
    }

}
