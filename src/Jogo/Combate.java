package Jogo;

import java.util.Scanner;
import java.util.Random;
import Personagens.Dinossauro;
import Personagens.Jogador;

public class Combate {
    private Scanner teclado;
    private Random random;

    public Combate(long semente, Scanner t) {
        teclado = t;
        random = new Random(semente);
    }

    private int lerInteiro() {
        while (!teclado.hasNextInt()) {
            System.out.println("Digite um número válido!");
            teclado.next();
        }
        return teclado.nextInt();
    }

    public boolean iniciar(Jogador jogador, Dinossauro dinossauro, Tabuleiro tabuleiro, boolean jogadorEncontrou) {
        int opcao;
        boolean turnoJogador = dinossauro.emboscaJogador(jogadorEncontrou);
        System.out.println();
        System.out.println("ENTROU EM COMBATE CONTRA O " + dinossauro.getEspecie().toUpperCase());
        while (jogador.getVida() > 0 && dinossauro.getVida() > 0) {

            if (turnoJogador) {

                ataqueJogador(jogador, dinossauro);

                if (dinossauro.getVida() <= 0) {
                    System.out.println("Dinossauro derrotado!");

                    if (tabuleiro.contemEsseDinossauro(dinossauro)) {
                        tabuleiro.removerDinossauro(dinossauro);
                    }

                    break;
                }

            } else {

                ataqueDinossauro(jogador, dinossauro);

                if (jogador.getVida() <= 0) {
                    return false;
                }

                System.out.println();
                System.out.println("1 - Continuar Combate");
                System.out.println("2 - Fugir");

                opcao = lerInteiro();

                while (opcao != 1 && opcao != 2) {
                    System.out.println("Digite 1 ou 2:");
                    opcao = lerInteiro();
                }

                if (opcao == 2) {
                    if (dinossauro.permiteFuga()) {
                        jogador.fugir(tabuleiro);
                        return true;
                    } else {
                        System.out.println();
                        System.out.println("Não é possível fugir do pequeno dinossauro!");
                    }
                }

            }
            turnoJogador = !turnoJogador;
        }
        return true;
    }

    public void ataqueDinossauro(Jogador jogador, Dinossauro dinossauro) {

        int dado = random.nextInt(3) + 1;

        System.out.println();
        System.out.println("=====TURNO DO " + dinossauro.getEspecie().toUpperCase() + "!");
        System.out.println("O dinossauro atacou!");
        System.out.println("Teste de percepcao: " + dado);

        if (dado <= jogador.getPercepcao()) {
            System.out.println("Você desviou do ataque!");
            System.out.println();
        } else {
            System.out.println("Você foi mordido!");
            System.out.println();
            dinossauro.morder(jogador);
        }
    }

    public void ataqueJogador(Jogador jogador, Dinossauro dinossauro) {
        int opcao;
        System.out.println();
        System.out.println("=====SEU TURNO");
        System.out.println("Vida do jogador: " + jogador.getVida());
        while (true) {
            int dado = random.nextInt(6) + 1;

            jogador.mostrarAcoesDisponiveis();

            opcao = lerInteiro();

            if (jogador.possuiArmaDardos()) {
                while (opcao != 1 && opcao != 2) {
                    System.out.println("Digite 1 ou 2:");
                    opcao = lerInteiro();
                }
            } else {
                while (opcao != 1) {
                    System.out.println("Digite 1:");
                    opcao = lerInteiro();
                }
            }

            System.out.println("=====ROLANDO DADOS");
            System.out.println("Você atacou!");
            System.out.println("Dado " + dado);
            System.out.println("=================");

            switch (opcao) {
                case 1:
                    jogador.atacarCorpoACorpo(dinossauro, dado);
                    return;
                case 2:
                    if (jogador.atirarDardo(dinossauro))
                        return;
                    continue;
            }
        }
    }
}