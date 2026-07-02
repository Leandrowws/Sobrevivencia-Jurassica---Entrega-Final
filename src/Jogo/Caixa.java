package Jogo;

import Personagens.Jogador;

public class Caixa {

    private TipoCaixa tipo;

    public Caixa(TipoCaixa t) {
        tipo = t;
    }

    public TipoCaixa getTipo() {
        return tipo;
    }

    public void abrir(Jogador jogador) {

        switch (tipo) {

            case KIT_MEDICO:
                System.out.println();
                System.out.println("Voce encontrou um kit medico!");
                jogador.receberKitMedico();
                break;

            case BASTAO_ELETRICO:
                System.out.println();
                System.out.println("Voce encontrou um bastao eletrico!");
                jogador.receberBastao();
                break;

            case DARDO_TRANQUILIZANTE:

                jogador.receberArmaDardos();

                break;

            case COMPSOGNATO_SURPRESA:
                System.out.println();
                System.out.println("VOCÊ ENCONTROU UM COMPSOGNATO DENTRO DA CAIXA!");
                break;
        }
    }

}
