package Itens;

import Personagens.Dinossauro;

public class ArmaDardos extends Arma {

    private int municao;

    public ArmaDardos() {
        super("Arma de Dardos e Munição");
        municao = 2;
    }

    public boolean atacar(Dinossauro alvo, int dado) {

        if (municao == 0) {
            System.out.println("Sem munição!");
            return false;
        }

        municao--;

        System.out.println("Dardo disparado!");
        alvo.receberDardo(2);

        return true;
    }

    public void adicionarMunicao() {
        municao += 2;
    }

    public int getMunicao() {
        return municao;
    }
}