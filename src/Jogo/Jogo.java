package Jogo;

import java.util.Scanner;
import Personagens.Dinossauro;
import Personagens.Jogador;

public class Jogo {

    private Tabuleiro tabuleiro;
    private Jogador jogador;
    private long semente;
    private Scanner teclado;
    private Combate combate;

    public Jogo() {
        teclado = new Scanner(System.in);
    }

    private int lerInteiro() {
        while (!teclado.hasNextInt()) {
            System.out.println("Digite um número válido!");
            teclado.next();
        }
        return teclado.nextInt();
    }

    public void iniciar() {
        semente = System.currentTimeMillis();
        int percepcao = escolherDificuldade();
        jogador = new Jogador(percepcao);
        tabuleiro = new Tabuleiro(10, semente);
        combate = new Combate(semente, teclado);
        tabuleiro.posicionarJogador(jogador);
        tabuleiro.gerarMapa();
    }

    private int escolherDificuldade() {

        int opcao;
        System.out.println("=== SOBREVIVENCIA JURASSICA ===");
        System.out.println("Escolha a dificuldade:");
        System.out.println("1 - Fácil");
        System.out.println("2 - Médio");
        System.out.println("3 - Difícil");

        opcao = lerInteiro();

        while (opcao != 1 && opcao != 2 && opcao != 3) {
            System.out.println("Digite 1, 2 ou 3");
            opcao = lerInteiro();
        }

        switch (opcao) {
            case 1:
                return 3;
            case 2:
                return 2;
            default:
                return 1;
        }
    }

    public void reiniciarJogo() {
        int percepcao = jogador.getPercepcao();
        jogador = new Jogador(percepcao);
        tabuleiro = new Tabuleiro(10, semente);
        combate = new Combate(semente, teclado);
        tabuleiro.posicionarJogador(jogador);
        tabuleiro.gerarMapa();
    }

    public boolean menuPosJogo() {
        while (true) {
            System.out.println("Reiniciar (R)");
            System.out.println("Novo Jogo (N)");
            System.out.println("Fechar Jogo (Q)");

            char comando = teclado.next().toUpperCase().charAt(0);

            switch (comando) {
                case 'R':
                    reiniciarJogo();
                    return true;
                case 'N':
                    iniciar();
                    return true;
                case 'Q':
                    return false;
                default:
                    System.out.println("Opção inválida!");
                    System.out.println();
            }
        }
    }

    private boolean acaoGastaTurno(char comando) {
        return (comando == 'W' ||
                comando == 'A' ||
                comando == 'S' ||
                comando == 'D' ||
                comando == 'H');
    }

    public void executarTurnos() {
        boolean debug = false;
        while (true) {
            Dinossauro inimigo = null;
            if (debug == true) {
                tabuleiro.imprimirMapaCompleto();
            } else {
                tabuleiro.imprimirMapaVisivel(jogador);
            }

            System.out.println();
            System.out.println("Vida: " + jogador.getVida());
            System.out.println("Percepção: " + jogador.getPercepcao());
            System.out.println("Movimento (W A S D)");
            System.out.println("Usar Kit Médico (H)");
            System.out.println("Alternar Modo de Debug (B)");
            System.out.println("Sair (Q)");

            char comando = teclado.next().toUpperCase().charAt(0);

            switch (comando) {
                case 'H':
                    jogador.usarKitMedico();
                    break;
                case 'Q':
                    if (!menuPosJogo()) {
                        return;
                    }
                    break;
                case 'B':
                    debug = !debug;
                    break;

                case 'W':
                    inimigo = jogador.mover(tabuleiro, -1, 0);
                    break;
                case 'S':
                    inimigo = jogador.mover(tabuleiro, 1, 0);
                    break;
                case 'A':
                    inimigo = jogador.mover(tabuleiro, 0, -1);
                    break;
                case 'D':
                    inimigo = jogador.mover(tabuleiro, 0, 1);
                    break;

                default:
                    System.out.println("Comando inválido!");
            }

            if (acaoGastaTurno(comando)) {
                if (inimigo != null) {
                    if (!processarCombate(inimigo, true))
                        return;
                } else {
                    inimigo = tabuleiro.moverTodosDinossauros();
                    if (inimigo != null) {
                        if (!processarCombate(inimigo, false))
                            return;
                    }
                }
            }

        }
    }

    private boolean processarCombate(Dinossauro inimigo, boolean jogadorEncontrou) {
        boolean sobreviveu = combate.iniciar(jogador, inimigo, tabuleiro, jogadorEncontrou);

        if (!sobreviveu) {
            System.out.println("Você morreu!");
            if (!menuPosJogo()) {
                return false;
            }
            return true;
        }
        if (tabuleiro.todosDinossaurosDerrotados()) {
            System.out.println("PARABÉNS!");
            System.out.println("Você sobreviveu à ilha jurássica!");
            if (!menuPosJogo()) {
                return false;
            }
        }
        return true;
    }
}