package Caixas;

import Personagens.Dinossauro;
import Personagens.Compsognato;
import Personagens.Jogador;

public class CaixaCompsognato extends Caixa {

    public Dinossauro abrir(Jogador jogador) {
        return new Compsognato();
    }
}