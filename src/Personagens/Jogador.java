package Personagens;

import Jogo.Jogo;
import Itens.Inventario;
import Itens.Arma;
import Itens.ArmaDardos;
import Itens.Item;
import Itens.KitMedico;
import Jogo.Tabuleiro;

public class Jogador extends Personagem {

    private int percepcao;
    private Inventario inventario;
    private Jogo jogo;

    public Jogador(int percepcao) {
        super(5);
        this.percepcao = percepcao;
        inventario = new Inventario();
    }

    public String getNomeIcone() {
        return "jogador";
    }

    public int getPercepcao() {
        return percepcao;
    }

    public int getMunicaoDardos() {

        ArmaDardos arma = inventario.getArmaDardos();

        if (arma == null)
            return 0;

        return arma.getMunicao();

    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void adicionarItem(Item item) {
        if (item instanceof ArmaDardos) {

            ArmaDardos armaExistente = inventario.getArmaDardos();

            if (armaExistente != null) {
                armaExistente.adicionarMunicao();
                if (jogo != null) {
                    jogo.mensagem("Você encontrou munição extra para a Arma de Dardos!");
                }
                return;
            }
        }

        inventario.adicionar(item);
        if (jogo != null) {
            jogo.mensagem("Você encontrou um(a) " + item.getNome());
        }
    }

    public boolean usarKitMedico() {

        KitMedico kit = inventario.getKitMedico();

        if (kit == null) {
            if (jogo != null) {
                jogo.mensagem("Você não possui kit médico!");
            }
            return false;
        }

        if (getVida() == 5) {
            if (jogo != null) {
                jogo.mensagem("Sua vida já está cheia!");
            }
            return false;
        }

        kit.usar(this);
        inventario.remover(kit);

        if (jogo != null) {
            jogo.mensagem("Vida recuperada!");
        }

        return true;
    }

    public int getNumeroAcoesAtaque() {
        return inventario.quantidadeArmas();
    }

    public boolean atacarCorpoACorpo(Dinossauro alvo, int dado) {
        Arma arma = inventario.getArmaCorpoACorpo();
        return arma.atacar(alvo, dado, this);
    }

    public boolean atirarDardo(Dinossauro alvo) {
        ArmaDardos arma = inventario.getArmaDardos();

        if (arma == null) {
            if (jogo != null) {
                jogo.mensagem("Você não possui arma de dardos.");
            }
            return false;
        }

        return arma.atacar(alvo, 0, this);
    }

    public boolean fugir(Tabuleiro tabuleiro) {
        return tabuleiro.fugirParaCelulaLivre(this);
    }
}