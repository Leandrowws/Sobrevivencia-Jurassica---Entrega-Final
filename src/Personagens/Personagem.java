package Personagens;

public abstract class Personagem {

    private int linha;
    private int coluna;
    private int vida;

    public Personagem(int v) {
        vida = v;
    }

    public int getVida() {
        return vida;
    }

    public void perderVida(int dano) {

        vida -= dano;

        if (vida < 0) {
            vida = 0;
        }
    }

    public void recuperarVida() {
        vida++;
    }

    public void setPosicao(int l, int c) {
        linha = l;
        coluna = c;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    public abstract char getSimbolo();
}