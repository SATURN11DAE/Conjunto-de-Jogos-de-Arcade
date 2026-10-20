import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Tetris extends Application {
    private static final int TAMANHO_BLOCO = 30;
    private static final int LARGURA_INFO = 200;

    private Pane painelJogo = new Pane();
    private Pane painelProxima = new Pane();
    private Timeline gameLoop;
    
    private Peca pecaAtual;
    private Peca proximaPeca;
    private Text textoPontuacao = new Text("Score: 0");

    private Tabuleiro tabuleiro = new Tabuleiro();

    @Override
    public void start(Stage palcoPrincipal) {
        painelJogo.setMinSize(Tabuleiro.COLUNAS * TAMANHO_BLOCO, Tabuleiro.LINHAS * TAMANHO_BLOCO);
        painelJogo.setStyle("-fx-background-color: #222222;");
        
        VBox painelInfo = new VBox(20); 
        painelInfo.setMinSize(LARGURA_INFO, Tabuleiro.LINHAS * TAMANHO_BLOCO);
        painelInfo.setStyle("-fx-background-color: #444444; -fx-padding: 20px;");

        textoPontuacao.setFill(Color.WHITE);
        textoPontuacao.setFont(new Font(16));

        Text textoProxima = new Text("Próxima:");
        textoProxima.setFill(Color.WHITE);
        textoProxima.setFont(new Font(16));

        painelProxima.setPrefSize(160, 160);
        painelProxima.setStyle("-fx-background-color: #333333;");

        painelInfo.getChildren().addAll(textoPontuacao, textoProxima, painelProxima);
        HBox layoutPrincipal = new HBox(painelJogo, painelInfo);
        Scene cena = new Scene(layoutPrincipal, (Tabuleiro.COLUNAS * TAMANHO_BLOCO) + LARGURA_INFO,
        Tabuleiro.LINHAS * TAMANHO_BLOCO);

        cena.setOnKeyPressed(evento -> {
            if (tabuleiro.isGameOver) {
                if (evento.getCode() == javafx.scene.input.KeyCode.ENTER) {
                    reiniciarInterface();
                }
                return; 
            }

            switch (evento.getCode()) {
                case LEFT:
                    if (tabuleiro.podeMover(pecaAtual, pecaAtual.x - 1, pecaAtual.y)) pecaAtual.x--; 
                    break;
                case RIGHT:
                    if (tabuleiro.podeMover(pecaAtual, pecaAtual.x + 1, pecaAtual.y)) pecaAtual.x++; 
                    break;
                case DOWN:
                    if (tabuleiro.podeMover(pecaAtual, pecaAtual.x, pecaAtual.y + 1)) pecaAtual.y++; 
                    break;
                case UP:
                    pecaAtual.rotacionar();
                    if (!tabuleiro.podeMover(pecaAtual, pecaAtual.x, pecaAtual.y)) {
                        pecaAtual.desfazerRotacao();
                    }
                    break;
                default: break;
            }
            atualizarTela();
        });
    
        iniciarPecas();

        palcoPrincipal.setTitle("TETRIS");
        palcoPrincipal.setScene(cena);
        palcoPrincipal.centerOnScreen();
        palcoPrincipal.setResizable(false);
        
        KeyFrame frame = new KeyFrame(Duration.millis(800), evento -> { 
            if (tabuleiro.podeMover(pecaAtual, pecaAtual.x, pecaAtual.y + 1)) {
                pecaAtual.y++; 
            } else {
                tabuleiro.travarPeca(pecaAtual);
                tabuleiro.verificarLinhas(); 
                textoPontuacao.setText("Score: " + tabuleiro.pontuacao);
                
                pecaAtual = proximaPeca;
                proximaPeca = new Peca((int)(Math.random() * 7) + 1);
                
                if (!tabuleiro.podeMover(pecaAtual, pecaAtual.x, pecaAtual.y)) {
                    gameLoop.stop(); 
                    tabuleiro.isGameOver = true; 
                    textoPontuacao.setText("GAME OVER!\nScore: " + tabuleiro.pontuacao + "\n\nAperte ENTER\npara reiniciar");
                    textoPontuacao.setFill(Color.RED);
                }
            }
            atualizarTela();
        });

        gameLoop = new Timeline(frame);
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();

        palcoPrincipal.show();
    }

    private void iniciarPecas() {
        pecaAtual = new Peca((int)(Math.random() * 7) + 1);
        proximaPeca = new Peca((int)(Math.random() * 7) + 1);
        atualizarTela();
    }

    private void reiniciarInterface() {
        tabuleiro.reiniciar();
        textoPontuacao.setText("Score: " + tabuleiro.pontuacao);
        textoPontuacao.setFill(Color.WHITE);
        iniciarPecas();
        gameLoop.play();
    }

    private Color obterCorPorTipo(int tipo) {
        switch (tipo) {
            case 1: return Color.web("#DDA0DD");
            case 2: return Color.web("#DA70D6");
            case 3: return Color.web("#BA55D3");
            case 4: return Color.web("#9932CC");
            case 5: return Color.web("#8A2BE2");
            case 6: return Color.web("#9370DB");
            case 7: return Color.web("#4B0082");
            default: return Color.WHITE;
        }
    }

    private void atualizarTela() {
        painelJogo.getChildren().clear();
        
        for (int x = 0; x < Tabuleiro.COLUNAS; x++) {
            for (int y = 0; y < Tabuleiro.LINHAS; y++) {
                Rectangle bloco = new Rectangle(TAMANHO_BLOCO - 1, TAMANHO_BLOCO - 1);
                bloco.setX(x * TAMANHO_BLOCO);
                bloco.setY(y * TAMANHO_BLOCO);
                
                int valorNaMatriz = tabuleiro.matrizGrade[x][y];
                if (valorNaMatriz != 0) {
                    bloco.setFill(obterCorPorTipo(valorNaMatriz));
                } else {
                    bloco.setFill(Color.web("#333333"));
                }
                painelJogo.getChildren().add(bloco);
            }
        }

        for (int l = 0; l < pecaAtual.formato.length; l++) {
            for (int c = 0; c < pecaAtual.formato[l].length; c++) {
                if (pecaAtual.formato[l][c] == 1) {
                    Rectangle bloco = new Rectangle(TAMANHO_BLOCO - 1, TAMANHO_BLOCO - 1);
                    bloco.setFill(pecaAtual.cor);
                    bloco.setX((pecaAtual.x + c) * TAMANHO_BLOCO);
                    bloco.setY((pecaAtual.y + l) * TAMANHO_BLOCO);
                    painelJogo.getChildren().add(bloco);
                }
            }
        }

        painelProxima.getChildren().clear();
        for (int l = 0; l < proximaPeca.formato.length; l++) {
            for (int c = 0; c < proximaPeca.formato[l].length; c++) {
                if (proximaPeca.formato[l][c] == 1) {
                    Rectangle bloco = new Rectangle(TAMANHO_BLOCO - 1, TAMANHO_BLOCO - 1);
                    bloco.setFill(proximaPeca.cor);
                    bloco.setX((c * TAMANHO_BLOCO) + 20);
                    bloco.setY((l * TAMANHO_BLOCO) + 20);
                    painelProxima.getChildren().add(bloco);
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
