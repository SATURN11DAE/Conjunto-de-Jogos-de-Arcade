public class Tabuleiro {
    public static final int COLUNAS = 10;
    public static final int LINHAS = 20;
    
    public int[][] matrizGrade = new int[COLUNAS][LINHAS];
    public int pontuacao = 0;
    public boolean isGameOver = false;

    public boolean podeMover(Peca peca, int futuroX, int futuroY) {
        for (int linha = 0; linha < peca.formato.length; linha++) {
            for (int coluna = 0; coluna < peca.formato[linha].length; coluna++) {
                if (peca.formato[linha][coluna] == 1) {
                    int posTabelaX = futuroX + coluna;
                    int posTabelaY = futuroY + linha;

                    if (posTabelaX < 0 || posTabelaX >= COLUNAS) return false; 
                    if (posTabelaY >= LINHAS) return false;
                    if (posTabelaY >= 0 && matrizGrade[posTabelaX][posTabelaY] != 0) return false; 
                }
            }
        }
        return true;
    }

    public void travarPeca(Peca peca) {
        for (int linha = 0; linha < peca.formato.length; linha++) {
            for (int coluna = 0; coluna < peca.formato[linha].length; coluna++) {
                if (peca.formato[linha][coluna] == 1) {
                    int posTabelaX = peca.x + coluna;
                    int posTabelaY = peca.y + linha;
                    if (posTabelaY >= 0) {
                        matrizGrade[posTabelaX][posTabelaY] = peca.tipo; 
                    }
                }
            }
        }
    }

    public void verificarLinhas() {
        int linhasApagadas = 0;

        for (int y = LINHAS - 1; y >= 0; y--) {
            boolean linhaCheia = true;
            for (int x = 0; x < COLUNAS; x++) {
                if (matrizGrade[x][y] == 0) {
                    linhaCheia = false; 
                    break;
                }
            }

            if (linhaCheia) {
                linhasApagadas++;
                for (int linhaAcima = y; linhaAcima > 0; linhaAcima--) {
                    for (int x = 0; x < COLUNAS; x++) {
                        matrizGrade[x][linhaAcima] = matrizGrade[x][linhaAcima - 1];
                    }
                }
                for (int x = 0; x < COLUNAS; x++) {
                    matrizGrade[x][0] = 0;
                }
                y++; 
            }
        }

        if (linhasApagadas > 0) {
            switch(linhasApagadas) {
                case 1: pontuacao += 100; break;
                case 2: pontuacao += 300; break;
                case 3: pontuacao += 600; break;
                case 4: pontuacao += 1000; break;
            }
        }
    }

    public void reiniciar() {
        for (int x = 0; x < COLUNAS; x++) {
            for (int y = 0; y < LINHAS; y++) {
                matrizGrade[x][y] = 0;
            }
        }
        pontuacao = 0;
        isGameOver = false;
    }
}
