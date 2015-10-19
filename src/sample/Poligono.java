package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.clipping.Cohen;
import sample.package3D.Ponto3D;

import java.util.*;

/**
 * Created by Wesley Anderson on 10/08/2015.
 */
/** rela��o entre dois pontos com cohen da classe ponto para verificar o quadrante**/
public class Poligono {
   private List<Ponto2D> listaDePontos;

    public Poligono(List ponto2D) {
        this.listaDePontos = ponto2D;
    }


    public void gravaPonto2D(Ponto2D pColetado){
        listaDePontos.add(pColetado);
    }

    public List<Ponto2D> getListaPontos() {
        return listaDePontos;
    }

    public void setListaDePontos(List<Ponto2D> listaDePontos) {
        this.listaDePontos = listaDePontos;
    }

    public void clearList(){
        this.listaDePontos.clear();
    }

    public void desenhaCanvas(Canvas canvasFx, Janela mundo,Janela vP,String tipoReta) {
        switch (tipoReta) {
            case "rb1":
                for (int i = 0; i < listaDePontos.size() - 1; i++) {
                    canvasFx.getGraphicsContext2D().strokeLine(listaDePontos.get(i).xMundoVp(mundo, vP), listaDePontos.get(i).yMundoVp(mundo, vP), listaDePontos.get(i + 1).xMundoVp(mundo, vP),
                                    listaDePontos.get(i + 1).yMundoVp(mundo, vP));
                }
                break;
            case "rb2":
                for (int i = 0; i < listaDePontos.size() - 1; i++) {
                    dda(listaDePontos.get(i).xMundoVp(mundo, vP), listaDePontos.get(i).yMundoVp(mundo, vP), listaDePontos.get(i + 1).xMundoVp(mundo, vP),
                            listaDePontos.get(i + 1).yMundoVp(mundo, vP), canvasFx.getGraphicsContext2D());
                }
                break;
            case "rb3":
                for (int i = 0; i < listaDePontos.size() - 1; i++) {
                    bresenham(listaDePontos.get(i).xMundoVp(mundo, vP), listaDePontos.get(i).yMundoVp(mundo, vP), listaDePontos.get(i + 1).xMundoVp(mundo, vP),
                            listaDePontos.get(i + 1).yMundoVp(mundo, vP), canvasFx.getGraphicsContext2D());
                }
                break;
            default:
                break;
        }
    }

    public void dda(int x0, int y0, int x1, int y1, GraphicsContext draw2D){
        int deltaX = x1 - x0;
        int deltaY = y1 - y0;

        draw2D.strokeLine(x0, y0, x1, y1);
        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            float m = (float) deltaY / (float) deltaX;
            float b = y0 - m*x0;
            if(deltaX<0)
                deltaX =  -1;
            else
                deltaX =  1;
            while (x0 != x1) {
                x0 += deltaX;
                y0 = Math.round(m*x0 + b);
                draw2D.strokeLine(x0, y0, x1, y1);
            }
        } else
        if (deltaY != 0) {
            float m = (float) deltaX / (float) deltaY;
            float b = x0 - m*y0;
            if(deltaY<0)
                deltaY =  -1;
            else
                deltaY =  1;
            while (y0 != y1) {
                y0 += deltaY;
                x0 = Math.round(m*y0 + b);
                draw2D.strokeLine( x0, y0, x0, y0);
            }
        }
    }

    public void bresenham(int x0, int y0, int x1, int y1,GraphicsContext draw2D) {
        int x, y, deltaX, deltaY, p, incE, incNE, stepx, stepy;
        deltaX = (x1 - x0);
        deltaY = (y1 - y0);

         if (deltaY < 0) {
            deltaY = -deltaY;
            stepy = -1;
        }
        else {
            stepy = 1;
        }

        if (deltaX < 0) {
            deltaX = -deltaX;
            stepx = -1;
        }
        else {
            stepx = 1;
        }

        x = x0;
        y = y0;
        draw2D.strokeLine(x0, y0, x0, y0);
        if(deltaX>deltaY){
            p = 2*deltaY - deltaX;
            incE = 2*deltaY;
            incNE = 2*(deltaY-deltaX);
            while (x != x1){
                x = x + stepx;
                if (p < 0){
                    p = p + incE;
                }
                else {
                    y = y + stepy;
                    p = p + incNE;
                }
                draw2D.strokeLine(x, y, x, y);
            }
        }
        else{
            p = 2*deltaX - deltaY;
            incE = 2*deltaX;
            incNE = 2*(deltaX-deltaY);
            while (y != y1){
                y = y + stepy;
                if (p < 0){
                    p = p + incE;
                }
                else {
                    x = x + stepx;
                    p = p + incNE;
                }
                draw2D.strokeLine(x, y, x, y);
            }
        }
    }
    public void desenhaCirculo(Ponto2D centro,Janela mundo,Janela vP,GraphicsContext draw2D){
        double x , y,xc=centro.getCordenadaX(),yc=centro.getCordenadaY();
        for(int i=0;i<this.listaDePontos.size();i++){
            x=this.listaDePontos.get(i).getCordenadaX();
            y=this.listaDePontos.get(i).getCordenadaY();
            this.listaDePontos.get(i).setCordenadaX((x+xc));
            this.listaDePontos.get(i).setCordenadaY((y+yc));
            draw2D.strokeOval(this.listaDePontos.get(i).xMundoVp(mundo,vP),this.listaDePontos.get(i).yMundoVp(mundo,vP),1,1);
        }
    }
    public void criaPontosCirculo(double r){
        double x=0,y=r,p;
        //this.gravaPonto2D(new Ponto2D(Xc,Yc));
        this.gravaPonto2D(new Ponto2D(x,y));
        p = 1 - r;
        while(x<y){
            if(p<0){
                x++;
            }else {
                x++;
                y--;
            }
            if(p<0){
                p += 2*x+1;
            }else{
                p += 2*(x-y)+1;
            }
            this.gravaPonto2D(new Ponto2D(x,y));
            this.gravaPonto2D(new Ponto2D(-x,y));
            this.gravaPonto2D(new Ponto2D(-y,x));
            this.gravaPonto2D(new Ponto2D(-y,-x));
            this.gravaPonto2D(new Ponto2D(-x,-y));
            this.gravaPonto2D(new Ponto2D(x,-y));
            this.gravaPonto2D(new Ponto2D(y,x));
            this.gravaPonto2D(new Ponto2D(y,-x));
        }

    }

    public  List<Poligono> clipping(Janela clip){
        List<Poligono> listaPontosAuxiliar = new LinkedList<>();
        Cohen cohen = new Cohen(clip);
        for (int i = 0; i < (this.listaDePontos.size()-1) ; i++) {
            listaPontosAuxiliar.add(new Poligono(cohen.CohenSutherlandLineClip(listaDePontos.get(i), listaDePontos.get(i + 1))));
        }
        return listaPontosAuxiliar;
    }

    public Ponto2D getCentro() {
        //teste ro
        double cX = 0, cY = 0;
        for (int i=0;i < this.listaDePontos.size();i++) {
            cX += this.listaDePontos.get(i).getCordenadaX();
            cY += this.listaDePontos.get(i).getCordenadaY();
        }
        cX /= this.listaDePontos.size();
        cY /= this.listaDePontos.size();
        return new Ponto2D(cX, cY);
    }

       /* ------- SISTEMAS DE COORDENADAS HOMOGÊNEAS -------*/


    private double[][] matrizTranslacao(double Dx, double Dy){
        double translacao[][] =  {{1, 0, 0},
                {0, 1, 0},
                {Dx, Dy, 1}};
        return translacao;
    }

    private double[][] matrizEscalonamento(double Sx, double Sy){
        double escalonamento[][] =  {{Sx, 0, 0},
                {0, Sy, 0},
                {0, 0, 1}};
        return escalonamento;
    }

    private double[][] matrizRotacao(double angulo, Ponto2D centro){
        double antes[][] = {{1, 0, 0},
                {0, 1, 0},
                {-centro.getCordenadaX(), -centro.getCordenadaY(), 1}};

        double depois[][] = {{1, 0, 0},
                {0, 1, 0},
                {centro.getCordenadaX(), centro.getCordenadaY(), 1}};

        double rotacao[][] =  {{(double)Math.cos(angulo), (double)Math.sin(angulo), 0},
                {(double)-Math.sin(angulo), (double)Math.cos(angulo), 0},
                {0, 0, 1}};
        rotacao = multiplicaMatriz(antes,rotacao);
        rotacao = multiplicaMatriz(rotacao, depois);
        return rotacao;
    }

    private double[][] matrizReflecao(int tipo){
        switch(tipo){
            case 1:
                //No eixo X
                double reflexaoX[][] = {{1, 0, 0},
                        {0, -1, 0},
                        {0, 0, 1}};

                return reflexaoX;
            case 2:
                // No eixo Y
                double reflexaoY[][] = {{-1, 0, 0},
                        {0, 1, 0},
                        {0, 0, 1}};
                return reflexaoY;
            case 3:
                // No eixo XY
                double reflexaoXY[][] = {{-1, 0, 0},
                        {0, -1, 0},
                        {0, 0, 1}};
                return reflexaoXY;
        }
        return null;
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

    public void transladarPoligono(double dx,double dy){
        for(int i=0;i<this.listaDePontos.size();i++){
            this.listaDePontos.get(i).pontoTransladado(dx,dy);
        }
    }
    public void escalonarPoligono(double dx,double dy){
        for(int i=0;i<this.listaDePontos.size();i++){
            this.listaDePontos.get(i).pontoEscalonado(dx, dy);
        }
    }
    public void rotacionarPoligono(double angulo){
        Ponto2D medio;
        medio = getCentro();
        for(int i=0;i<this.listaDePontos.size();i++){
            this.listaDePontos.get(i).setCordenadaX(this.listaDePontos.get(i).getCordenadaX() - medio.getCordenadaX());
            this.listaDePontos.get(i).setCordenadaY(this.listaDePontos.get(i).getCordenadaY() - medio.getCordenadaY());
            this.listaDePontos.get(i).pontoRotacionado(angulo);
            this.listaDePontos.get(i).setCordenadaX(this.listaDePontos.get(i).getCordenadaX() + medio.getCordenadaX());
            this.listaDePontos.get(i).setCordenadaY(this.listaDePontos.get(i).getCordenadaY() + medio.getCordenadaY());
        }
    }
    public void refletirPontosX(){
        for(int i=0;i<this.listaDePontos.size();i++){
            this.listaDePontos.get(i).pontoRefletidoX();
        }
    }
    public void refletirPontosY(){
        for(int i=0;i<this.listaDePontos.size();i++){
            this.listaDePontos.get(i).pontoRefletidoY();
        }
    }
    public void curvaCasteljau(){
        Casteljau curva = new Casteljau(getListaPontos());
        this.listaDePontos = curva.geraListaCurva();
    }
    public Ponto2D[][] multiplicaMatriz(double a[][], Ponto2D b[][]) {
        //Aqui calcula a matriz C, que é a multiplicacao da matrizHemite X vetorGeometrico
        Ponto2D c[][] = new Ponto2D[a.length][b[0].length];

        for (int i = 0; i < a.length; i++) { //Linha
            Ponto2D aux = new Ponto2D(0, 0);
            for (int j = 0; j < b[i].length; j++) { //Coluna
                int ax = 0, ay = 0;
                for (int x = 0; x < a[0].length; x++) {
                    //Aqui acontece a multiplicacao
                    ax = (int) (ax + a[i][x] * b[x][j].getCordenadaX());
                    ay = (int) (ay + a[i][x] * b[x][j].getCordenadaY());
                }
                aux.setCordenadaX(ax);
                aux.setCordenadaY(ay);
                c[i][j] = aux;
            }
        }
        return c;
    }
    public void curvaHermit(int t){
      //  Ponto2D aux = new Ponto2D(0,0);
        List<Ponto2D> listaPontosAux = new LinkedList<Ponto2D>();
        Ponto2D p0 = getListaPontos().get(0);
        Ponto2D p1 = getListaPontos().get(1);
        Ponto2D p2 = getListaPontos().get(2);
        Ponto2D p3 = getListaPontos().get(3);
        Ponto2D r1 = new Ponto2D( (p1.getCordenadaX() - p0.getCordenadaX()) , (p1.getCordenadaY() - p0.getCordenadaY()) );
        Ponto2D r4 = new Ponto2D( (p3.getCordenadaX() - p2.getCordenadaX()) , (p3.getCordenadaY() - p2.getCordenadaY()) );



        final double matrizHemite[][] = {{2, -2, 1, 1},
                {-3, 3, -2, -1},
                {0, 0, 1, 0},
                {1, 0, 0, 0}};
        Ponto2D vetorGeometrico[][] = {{p0},
                {p3},
                {r1},
                {r4}};
        Ponto2D c[][] = multiplicaMatriz(matrizHemite, vetorGeometrico);
        //listaPontosAux().clear();
        double soma = (double)1/t;
        for (double x = 0; x <= 1;x += soma) {
            double T[][] = {{(double) Math.pow(x, 3) ,(double)  Math.pow(x, 2) , x, 1}};
            Ponto2D pd[][] = multiplicaMatriz(T, c);
            for (int i = 0; i < pd.length; i++) { //Linha
                for (int j = 0; j < pd[i].length; j++) { //Coluna
                    listaPontosAux.add(pd[i][j]);
                }
            }
        }
        this.setListaDePontos(listaPontosAux);
    }
    public void curvaBezier(int t){
     //   Ponto2D aux = new Ponto2D(0,0);
        List<Ponto2D> listaPontosAux = new LinkedList<Ponto2D>() ;
        Ponto2D p0 = getListaPontos().get(0);
        Ponto2D p1 = getListaPontos().get(1);
        Ponto2D p2 = getListaPontos().get(2);
        Ponto2D p3 = getListaPontos().get(3);

        final double[][] matrizBezier = {{-1, 3, -3, 1},
                {3, -6, 3, 0},
                {-3, 3, 0, 0},
                {1, 0, 0, 0}};
        Ponto2D vetorGeometrico[][] = {{p0},
                {p1},
                {p2},
                {p3}};
        Ponto2D c[][] = multiplicaMatriz(matrizBezier, vetorGeometrico);

        double soma = (double)1/t;
        for (double x = 0; x <= 1;x += soma) {
            double T[][] = {{(double) Math.pow(x, 3) ,(double)  Math.pow(x, 2) , x, 1}};
            Ponto2D pd[][] = multiplicaMatriz(T, c);
            for (int i = 0; i < pd.length; i++) { //Linha
                for (int j = 0; j < pd[i].length; j++) { //Coluna
                    listaPontosAux.add(pd[i][j]);
                }
            }
        }
        this.setListaDePontos(listaPontosAux);
    }
    public void curvaBsplines(int t){
        List<Ponto2D> listaPontosAux = new LinkedList<Ponto2D>() ;
        Ponto2D p0 = getListaPontos().get(0);
        Ponto2D p1 = getListaPontos().get(1);
        Ponto2D p2 = getListaPontos().get(2);
        Ponto2D p3 = getListaPontos().get(3);
        final double[][] matrizBsplines = {{(double)-1/6, (double)3/6, (double)-3/6, (double)1/6},
                {(double)3/6, (double)-6/6, (double)3/6, 0},
                {(double)-3/6, 0, (double)3/6, 0},
                {(double)1/6, (double)4/6, (double)1/6, 0}};
        Ponto2D vetorGeometrico[][] = {{p0},
                {p1},
                {p2},
                {p3}};
        Ponto2D c[][] = multiplicaMatriz(matrizBsplines, vetorGeometrico);
        double soma = (double)1/t;
        for (double x = 0; x <= 1;x += soma) {
            double T[][] = {{(double) Math.pow(x, 3) ,(double)  Math.pow(x, 2) , x, 1}};
            Ponto2D pd[][] = multiplicaMatriz(T, c);
            for (int i = 0; i < pd.length; i++) { //Linha
                for (int j = 0; j < pd[i].length; j++) { //Coluna
                    listaPontosAux.add(pd[i][j]);
                }
            }
        }
        this.setListaDePontos(listaPontosAux);
    }

}
