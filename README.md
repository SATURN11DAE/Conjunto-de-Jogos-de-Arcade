# Tetris em JavaFX

Implementação do Tetris em Java com JavaFX, pré-visualização da próxima peça e sistema de pontuação.

## Funcionalidades

- Tabuleiro de 10 colunas × 20 linhas
- 7 tipos de peças: Quadrado, T, Reta (I), S, Z, L e J
- Rotação de peças com verificação de colisão (desfaz a rotação se não couber)
- Painel lateral com pontuação e próxima peça
- Pontuação por linhas eliminadas de uma vez:

| Linhas | Pontos |
|:------:|:------:|
| 1 | 100 |
| 2 | 300 |
| 3 | 600 |
| 4 | 1000 |

- Tela de Game Over com reinício pela tecla `ENTER`

## Controles

| Tecla | Ação |
|:-----:|------|
| `←` | Mover para a esquerda |
| `→` | Mover para a direita |
| `↓` | Descer a peça mais rápido |
| `↑` | Rotacionar |
| `ENTER` | Reiniciar (após o Game Over) |

## Estrutura do projeto

```
.
├── .vscode/
│   ├── launch.json      # Configuração para rodar no VS Code
│   └── settings.json    # Referência às bibliotecas do OpenJFX
├── Peca.java            # Formatos, cores, posição e rotação das peças
├── Tabuleiro.java       # Matriz do jogo, colisões, travamento e pontuação
└── Tetris.java          # Interface JavaFX, game loop e entrada do teclado
```

### Como cada classe funciona

- **`Peca`**: guarda o formato da peça (matriz `int[][]`), a cor, a posição (`x`, `y`) e o tipo. Possui `rotacionar()` e `desfazerRotacao()`.
- **`Tabuleiro`**: mantém a grade (`matrizGrade`), valida movimentos com `podeMover()`, fixa peças com `travarPeca()`, remove linhas completas em `verificarLinhas()` e controla pontuação e estado de Game Over.
- **`Tetris`**: classe principal (`extends Application`). Monta a interface, trata as teclas e executa o game loop com um `Timeline` que faz a peça descer a cada **800 ms**.

## Requisitos

- **JDK 11** ou superior
- **OpenJFX** (JavaFX SDK)

No Ubuntu/Debian (ou WSL), a instalação pode ser feita com:

```bash
sudo apt update
sudo apt install openjdk-17-jdk openjfx
```

Os arquivos do JavaFX ficam em `/usr/share/openjfx/lib`.

## Como executar

### Pelo terminal

Dentro da pasta do projeto:

```bash
# Compilar
javac --module-path /usr/share/openjfx/lib --add-modules javafx.controls *.java

# Executar
java --module-path /usr/share/openjfx/lib --add-modules javafx.controls Tetris
```

### Pelo VS Code

1. Instale o **Extension Pack for Java**.
2. Abra a pasta do projeto.
3. Confira se o `.vscode/settings.json` aponta para as bibliotecas do JavaFX:

```json
{
    "java.project.referencedLibraries": [
        "/usr/share/openjfx/lib/*.jar"
    ]
}
```

4. No `.vscode/launch.json`, use `Tetris` como `mainClass`:

```json
{
    "version": "0.2.0",
    "configurations": [
        {
            "type": "java",
            "name": "Rodar Tetris",
            "request": "launch",
            "mainClass": "Tetris",
            "vmArgs": "--module-path /usr/share/openjfx/lib --add-modules javafx.controls"
        }
    ]
}
```

5. Pressione `F5` para rodar.

> **Rodando no WSL?** É necessário ter suporte a interface gráfica (WSLg no Windows 11, ou um servidor X configurado).

## Ideias para melhorar

- Aumentar a velocidade conforme o nível/pontuação
- Queda instantânea (*hard drop*) com a tecla `ESPACO`
- Sistema de níveis e recorde (*high score*)
- Pausa com a tecla `P`
- Efeitos sonoros e música
- Sombra (*ghost piece*) mostrando onde a peça vai cair

## Licença

Projeto de uso livre para fins de estudo.
