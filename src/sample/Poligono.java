package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Wesley Anderson on 10/08/2015.
 */
/** relação entre dois pontos com cohen da classe ponto para verificar o quadrante**/
public class Poligono {
    List<Ponto2D> listaDePontos;

    public Poligono(List ponto2D) {
        this.listaDePontos = ponto2D;
    }


    public void gravaPonto2D(Ponto2D pColetado){
        listaDePontos.add(pColetado);
    }

    public List<Ponto2D> getListaPontos() {
        return listaDePontos;
    }
    public void clearList(){
        this.listaDePontos.clear();
    }

    public void desenhaCanvas(Canvas canvasFx, Janela mundo,Janela vP,int tipoReta) {
        switch (tipoReta) {
            case 1:
                for (int i = 0; i < listaDePontos.size() - 1; i++) {
                    canvasFx.getGraphicsContext2D().strokeLine(listaDePontos.get(i).xMundoVp(mundo, vP), listaDePontos.get(i).yMundoVp(mundo, vP), listaDePontos.get(i + 1).xMundoVp(mundo, vP),
                            listaDePontos.get(i + 1).yMundoVp(mundo, vP));
                }
                break;
            case 2:
                for (int i = 0; i < listaDePontos.size() - 1; i++) {
                    dda(listaDePontos.get(i).xMundoVp(mundo, vP), listaDePontos.get(i).yMundoVp(mundo, vP), listaDePontos.get(i + 1).xMundoVp(mundo, vP),
                            listaDePontos.get(i + 1).yMundoVp(mundo, vP), canvasFx.getGraphicsContext2D());
                }
                break;
            case 3:
                for (int i = 0; i < listaDePontos.size() - 1; i++) {
                    bresenham(listaDePontos.get(i).xMundoVp(mundo, vP), listaDePontos.get(i).yMundoVp(mundo, vP), listaDePontos.get(i + 1).xMundoVp(mundo, vP),
                            listaDePontos.get(i + 1).yMundoVp(mundo, vP), canvasFx.getGraphicsContext2D());
                }
                break;
            default:
                break;
        }
    }

    public void dda(int x0, int y0, int x1, int y1, GraphicsContext draw2D)
    {
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

    public void desenhaCirculo(double Xc,double Xy, double r,GraphicsContext draw2D){
        double x=0,y=r,p;
        this.gravaPonto2D(new Ponto2D(Xc,Xy));
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
        }

    }

    public Ponto2D getCentro() {
        double cX = 0, cY = 0;
        for (int i=0;i < this.listaDePontos.size();i++) {
            cX += this.listaDePontos.get(i).getCordenadaX();
            cY += this.listaDePontos.get(i).getCordenadaY();
        }
        cX /= this.listaDePontos.size();
        cY /= this.listaDePontos.size();
        return new Ponto2D(cX, cY);
    }



}
