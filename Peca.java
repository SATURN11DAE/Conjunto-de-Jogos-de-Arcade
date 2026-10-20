import javafx.scene.paint.Color;

public class Peca {
    public int[][] formato;
    public Color cor;
    public int x;
    public int y;
    
    public int tipo; 

    public Peca(int tipo) {
        this.y = 0;
        this.x = 4;
        this.tipo = tipo;

        switch (tipo) {
            case 1: // Quadrado
                this.formato = new int[][]{{1, 1}, {1, 1}};
                this.cor = Color.web("#DDA0DD");
                break;
            case 2: // T
                this.formato = new int[][]{{0, 1, 0}, {1, 1, 1}};
                this.cor = Color.web("#DA70D6");
                break;
            case 3: // Reta (I)
                this.formato = new int[][]{{1, 1, 1, 1}};
                this.cor = Color.web("#BA55D3");
                break;
            case 4: // S
                this.formato = new int[][]{{0, 1, 1}, {1, 1, 0}};
                this.cor = Color.web("#9932CC");
                break;
            case 5: // Z
                this.formato = new int[][]{{1, 1, 0}, {0, 1, 1}};
                this.cor = Color.web("#8A2BE2");
                break;
            case 6: // L
                this.formato = new int[][]{{1, 0}, {1, 0}, {1, 1}};
                this.cor = Color.web("#9370DB");
                break;
            case 7: // J
                this.formato = new int[][]{{0, 1}, {0, 1}, {1, 1}};
                this.cor = Color.web("#4B0082");
                break;
            default:
                this.formato = new int[][]{{1}};
                this.cor = Color.web("#E6E6FA");
        }
    }

    public void rotacionar() {
        int linhas = formato.length;
        int colunas = formato[0].length;
        int[][] novoFormato = new int[colunas][linhas];

        for (int l = 0; l < linhas; l++) {
            for (int c = 0; c < colunas; c++) {
                novoFormato[c][linhas - 1 - l] = formato[l][c];
            }
        }
        this.formato = novoFormato;
    }

    public void desfazerRotacao() {
        int linhas = formato.length;
        int colunas = formato[0].length;
        int[][] novoFormato = new int[colunas][linhas];

        for (int l = 0; l < linhas; l++) {
            for (int c = 0; c < colunas; c++) {
                novoFormato[colunas - 1 - c][l] = formato[l][c];
            }
        }
        this.formato = novoFormato;
    }
}
