# Sobrevivência Jurássica

---

## Como jogar

Ao abrir o jogo, uma tela inicial permite escolher a dificuldade clicando em um dos três botões:

- **Fácil** — percepção 3
- **Médio** — percepção 2
- **Difícil** — percepção 1

Quanto maior a percepção, maiores as chances de desviar dos ataques dos dinossauros e maior a distância que o jogador consegue enxergar.

---

## Mapa

Cada célula do tabuleiro é exibida com um ícone correspondente:

| Elemento               | Representação       |
| ---------------------- | ------------------- |
| Jogador                | Cavaleiro           |
| T-Rex                  | Dinossauro azul     |
| Velociraptor           | Dinossauro marrom   |
| Troodonte              | Dinossauro verde    |
| Compsognato            | Dinossauro vermelho |
| Caixa                  | Baú do tesouro      |
| Parede                 | Parede de Tijolo    |
| Chão                   | Grama               |
| Fora do campo de visão | Quadrado preto      |

---

## Controles

| Tecla | Ação                                               |
| ----- | -------------------------------------------------- |
| W     | Mover para cima                                    |
| A     | Mover para a esquerda                              |
| S     | Mover para baixo                                   |
| D     | Mover para a direita                               |
| B     | Alternar modo de depuração (revela o mapa inteiro) |

O Kit Médico é utilizado através do botão **"Kit Médico"** durante um combate.

---

## Interface

Ao lado do tabuleiro fica um painel com:

- **Status**: dificuldade escolhida, vida atual e munição de dardos.
- **Botão "Fechar Jogo"**: encerra a partida atual e leva à tela de fim de jogo.
- **Mensagens**: um histórico com tudo que acontece durante a exploração — itens encontrados, caixas abertas, início e resultado de combates, entre outros eventos.

---

## Visão do jogador

O jogador só consegue enxergar nas quatro direções (cima, baixo, esquerda e direita).

A distância máxima depende da dificuldade:

- Percepção 3: até 6 casas.
- Percepção 2: até 4 casas.
- Percepção 1: até 2 casas.

A visão é interrompida ao encontrar uma parede, caixa ou personagem.

O modo de depuração (tecla `B`) ignora essa limitação e revela o mapa inteiro, útil para testes.

---

## Caixas

Durante a exploração é possível encontrar caixas contendo itens úteis.

Os itens disponíveis são:

- **Kit Médico**
  - Recupera até 2 pontos de vida.
  - Só pode ser usado se a vida não estiver cheia.

- **Bastão Elétrico**
  - Substitui o ataque com as mãos, causando mais dano corpo a corpo.

- **Arma de Dardos**
  - Permite atacar à distância.
  - Cada arma encontrada fornece duas munições.
  - Encontrar uma segunda arma de dardos não duplica o item — apenas adiciona mais duas munições à arma já existente.

- **Compsognato Surpresa**
  - Libera imediatamente um Compsognato para combate.

---

## Movimentação dos dinossauros

Cada espécie tem um comportamento diferente ao se mover automaticamente pelo mapa:

- **Compsognato**: movimenta-se de forma aleatória.
- **Velociraptor**: movimenta-se de forma aleatória, podendo andar até duas casas por turno.
- **Troodonte**: movimenta-se estrategicamente em direção ao jogador.
- **T-Rex**: não se movimenta.

---

## Combate

Quando o jogador encontra um dinossauro, ou quando um dinossauro encontra o jogador, inicia-se um combate em turnos.

Quem ataca primeiro depende de quem encontrou quem: se o jogador foi até o dinossauro, o jogador ataca primeiro; se o dinossauro foi até o jogador, o dinossauro ataca primeiro (emboscada).

Em cada turno do jogador é possível:

1. Atacar corpo a corpo (com o Bastão Elétrico, se disponível, ou com as próprias mãos).
2. Utilizar a arma de dardos, caso possua munição.
3. Usar o Kit Médico, caso possua um e a vida não esteja cheia.
4. Tentar fugir do combate.

Todas as ações e seus resultados aparecem tanto na tela de combate quanto no histórico de mensagens da tela principal.

### Exceções por espécie

- O **Compsognato** sempre ataca primeiro, independente de quem encontrou quem, e não permite fuga.
- O **T-Rex** é imune a ataques corpo a corpo desarmado (é necessário usar o Bastão Elétrico).
- O **Velociraptor** desvia automaticamente de ataques com dardos.

---

## Vida

O jogador começa com **5 pontos de vida**.

Se a vida chegar a zero durante um combate, o jogo termina em derrota.

---

## Vitória e derrota

O objetivo é eliminar todos os dinossauros presentes no mapa.

- Ao derrotar o último inimigo, o jogador vence a partida.
- Se a vida do jogador chegar a zero, a partida termina em derrota.
- Também é possível desistir a qualquer momento clicando em "Fechar Jogo" no painel lateral, o que encerra a partida como derrota.

---

## Tela de fim de jogo

Ao final da partida (vitória ou derrota), é exibida uma tela com duas opções:

- **Novo Jogo**: volta à tela de escolha de dificuldade e gera um novo mapa aleatório.
- **Reiniciar**: reinicia a partida mantendo a mesma dificuldade e o mesmo mapa.

Fechar essa janela sem escolher uma opção encerra o programa.
