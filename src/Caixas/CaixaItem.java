package Caixas;

import Itens.Item;
import Personagens.Jogador;
import Personagens.Dinossauro;

public class CaixaItem extends Caixa {

    private Item item;

    public CaixaItem(Item item) {
        this.item = item;
    }

    public Dinossauro abrir(Jogador jogador) {
        jogador.adicionarItem(item);
        return null;
    }
}
