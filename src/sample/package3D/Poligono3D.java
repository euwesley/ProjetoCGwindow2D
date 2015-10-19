package sample.package3D;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import sample.Janela;
import sample.Poligono;
import sample.Ponto2D;

import java.util.List;

/**
 * Created by Wesley Anderson on 07/10/2015.
 */
public class Poligono3D extends Poligono {
    public Poligono3D(List ponto2D) {
        super(ponto2D);
    }
    private List<Ponto3D> listaDePontos3d;

    public Poligono3D(List ponto2D, List<Ponto3D> listaDePontos3d) {
        super(ponto2D);
        this.listaDePontos3d = listaDePontos3d;
    }

    public List<Ponto3D> getListaDePontos3d() {
        return listaDePontos3d;
    }

    public void setListaDePontos3d(List<Ponto3D> listaDePontos3d) {
        this.listaDePontos3d = listaDePontos3d;
    }

    @Override
    public void clearList() {
        super.clearList();
    }


    public void desenhaCanvas(Canvas canvasFx, Janela mundo, Janela vP, String tipoReta) {
        switch (tipoReta) {
            case "rb1":
                for (int i = 0; i < listaDePontos3d.size() - 1; i++) {
                    canvasFx.getGraphicsContext2D().strokeLine(listaDePontos3d.get(i).xMundoVp(mundo, vP), listaDePontos3d.get(i).yMundoVp(mundo, vP), listaDePontos3d.get(i + 1).xMundoVp(mundo, vP),
                            listaDePontos3d.get(i + 1).yMundoVp(mundo, vP));
                }
                break;
            case "rb2":
                for (int i = 0; i < listaDePontos3d.size() - 1; i++) {
                    dda(listaDePontos3d.get(i).xMundoVp(mundo, vP), listaDePontos3d.get(i).yMundoVp(mundo, vP), listaDePontos3d.get(i + 1).xMundoVp(mundo, vP),
                            listaDePontos3d.get(i + 1).yMundoVp(mundo, vP), canvasFx.getGraphicsContext2D());
                }
                break;
            case "rb3":
                for (int i = 0; i < listaDePontos3d.size() - 1; i++) {
                    bresenham(listaDePontos3d.get(i).xMundoVp(mundo, vP), listaDePontos3d.get(i).yMundoVp(mundo, vP), listaDePontos3d.get(i + 1).xMundoVp(mundo, vP),
                            listaDePontos3d.get(i + 1).yMundoVp(mundo, vP), canvasFx.getGraphicsContext2D());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void dda(int x0, int y0, int x1, int y1, GraphicsContext draw2D) {
        super.dda(x0, y0, x1, y1, draw2D);
    }

    @Override
    public void bresenham(int x0, int y0, int x1, int y1, GraphicsContext draw2D) {
        super.bresenham(x0, y0, x1, y1, draw2D);
    }

    @Override
    public void desenhaCirculo(Ponto2D centro, Janela mundo, Janela vP, GraphicsContext draw2D) {
        super.desenhaCirculo(centro, mundo, vP, draw2D);
    }

    @Override
    public void criaPontosCirculo(double r) {
        super.criaPontosCirculo(r);
    }

    @Override
    public List<Poligono> clipping(Janela clip) {
        return super.clipping(clip);
    }

    @Override
    public Ponto2D getCentro() {
        return super.getCentro();
    }


    public void transladarPoligono3D(double dx, double dy, double dz) {
        for (int x = 0; x < listaDePontos3d.size(); x++) {
            Ponto3D vertice = listaDePontos3d.get(x);
            double matrizTranslacao[][] = matrizTranslacao(dx, dy, dz);
            double matrizPonto3D[][] = {{vertice.getCordenadaX()},
                    {vertice.getCordenadaY()},
                    {vertice.getCordenadaZ()},
                    {1}};
            matrizPonto3D = multiplicaMatriz(matrizTranslacao, matrizPonto3D);
            this.listaDePontos3d.get(x).setCordenadaX(matrizPonto3D[0][0]);
            this.listaDePontos3d.get(x).setCordenadaY(matrizPonto3D[1][0]);
            this.listaDePontos3d.get(x).setCordenadaZ(matrizPonto3D[2][0]);
        }
    }


    public void escalonarPoligono3D(double dx, double dy, double dz) {

        for (int x = 0; x < listaDePontos3d.size();x++) {
            Ponto3D vertice = listaDePontos3d.get(x);
            double matrizEscalonamento[][] = matrizEscalonamento(dx, dy, dz);
            double matrizPonto3D[][] = { {vertice.getCordenadaX()},
                    {vertice.getCordenadaY()},
                    {vertice.getCordenadaZ()},
                    {1}};
            matrizPonto3D = multiplicaMatriz(matrizEscalonamento,matrizPonto3D);
            this.listaDePontos3d.get(x).setCordenadaX(matrizPonto3D[0][0]);
            this.listaDePontos3d.get(x).setCordenadaY(matrizPonto3D[1][0]);
            this.listaDePontos3d.get(x).setCordenadaZ(matrizPonto3D[2][0]);
        }
    }

    /* ------- SISTEMAS DE COORDENADAS HOMOGÊNEAS -------*/


    private double[][] matrizTranslacao(double Dx, double Dy, double Dz){
        double translacao[][] =  {{1, 0, 0, Dx},
                {0, 1, 0, Dy},
                {0, 0, 1, Dz},
                {0, 0, 0 , 1}};
        return translacao;
    }

    private double[][] matrizEscalonamento(double Sx, double Sy, double Sz){
        double escalonamento[][] =  {{Sx, 0, 0, 0},
                {0, Sy, 0, 0},
                {0, 0, Sz, 0},
                {0, 0, 0, 1}};
        return escalonamento;
    }

    private double[][] matrizRotacao(double angulo, Ponto3D centro, String tipoEixo) {
        double antes[][] = {{1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {-centro.getCordenadaX(), -centro.getCordenadaY(), -centro.getCordenadaZ(), 1}};

        double depois[][] = {{1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {centro.getCordenadaX(), centro.getCordenadaY(), centro.getCordenadaZ(), 1}};

        double rotacao[][] = null;
        switch (tipoEixo) {
            case "rb3D1": //Eixo X
                double rotacaoX[][] = {{1, 0, 0, 0},
                        {0,  Math.cos(angulo), Math.sin(angulo), 0},
                        {0,  -Math.sin(angulo), Math.cos(angulo), 0},
                        {0, 0, 0, 1}};
                rotacao = rotacaoX;
                break;
            case "rb3D2": // Eixo Y
                double rotacaoY[][] = {{ Math.cos(angulo), 0,  -Math.sin(angulo), 0},
                        {0, 1, 0, 0},
                        {Math.sin(angulo), 0, Math.cos(angulo), 0},
                        {0, 0, 0, 1}};
                rotacao = rotacaoY;
                break;

            case "rb3D3": // Eixo Z
                double rotacaoZ[][] = {{ Math.cos(angulo),Math.sin(angulo), 0, 0},
                        {-Math.sin(angulo),Math.cos(angulo), 0, 0},
                        {0, 0, 1, 0},
                        {0, 0, 0, 1}};
                rotacao = rotacaoZ;
                break;
        }
        rotacao = multiplicaMatriz(antes, rotacao);
        rotacao = multiplicaMatriz(rotacao, depois);
        return rotacao;
    }


    private double[][] multiplicaMatriz(double a[][], double b[][]) {
        double c[][] = new double[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) { //Linha
            for (int j = 0; j < b[i].length; j++) { //Coluna
                double aux = 0;
                for (int z = 0; z < a[0].length; z++) {
                    //Aqui acontece a multiplicacao
                    aux += a[i][z] * b[z][j];
                }
                c[i][j] = aux;
            }
        }
        return c;
    }

    public void mostraMatriz(double a[][]) {
        //Aqui calcula a matriz C, que é a multiplicacao da matrizHemite X vetorGeometrico
        System.out.println("---- Matriz ----");
        for (double[] a1 : a) {
            //Linha
            for (int j = 0; j < a1.length; j++) {
                //Coluna
                System.out.println(a1[j] + "\t");
            }
            System.out.println("\n");
        }
        System.out.println("---- Fim Matriz ----");
    }
/* ------- FIM DO SISTEMAS DE COORDENADAS HOMOGÊNEAS -------*/



    private Ponto3D meioPoligono3D(){
        Ponto3D aux = new Ponto3D(0, 0, 0);

        for(int i = 0; i < this.listaDePontos3d.size(); i++){
            aux.setCordenadaX(listaDePontos3d.get(i).getCordenadaX()+aux.getCordenadaX());
            aux.setCordenadaY(listaDePontos3d.get(i).getCordenadaY()+aux.getCordenadaY());
            aux.setCordenadaZ(listaDePontos3d.get(i).getCordenadaZ()+aux.getCordenadaZ());
        }

        aux.setCordenadaX(aux.getCordenadaX()/this.listaDePontos3d.size());
        aux.setCordenadaY(aux.getCordenadaY()/this.listaDePontos3d.size());
        aux.setCordenadaZ(aux.getCordenadaZ()/this.listaDePontos3d.size());

        return aux;
    }

    public void rotacionarPoligono(double angulo,String eixo) {
        Ponto3D centro = meioPoligono3D();
        for (int x = 0; x < listaDePontos3d.size(); x++) {
            Ponto3D vertice = listaDePontos3d.get(x);
            double matrizRotacao[][] = matrizRotacao(angulo, centro, eixo);
            double matrizPonto3D[][] = {{vertice.getCordenadaX()},
                    {vertice.getCordenadaY()},
                    {vertice.getCordenadaZ()},
                    {1}};
            matrizPonto3D = multiplicaMatriz(matrizRotacao, matrizPonto3D);
            this.listaDePontos3d.get(x).setCordenadaX(matrizPonto3D[0][0]);
            this.listaDePontos3d.get(x).setCordenadaY(matrizPonto3D[1][0]);
            this.listaDePontos3d.get(x).setCordenadaZ(matrizPonto3D[2][0]);
        }
    }

    @Override
    public void refletirPontosX() {
        super.refletirPontosX();
    }

    @Override
    public void refletirPontosY() {
        super.refletirPontosY();
    }

    @Override
    public void curvaCasteljau() {
        super.curvaCasteljau();
    }

    @Override
    public Ponto2D[][] multiplicaMatriz(float[][] a, Ponto2D[][] b) {
        return super.multiplicaMatriz(a, b);
    }

    @Override
    public void curvaHermit(int t) {
        super.curvaHermit(t);
    }

    @Override
    public void curvaBezier(int t) {
        super.curvaBezier(t);
    }

    @Override
    public void curvaBsplines(int t) {
        super.curvaBsplines(t);
    }
}
