package sample;

import javafx.scene.canvas.Canvas;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Wesley Anderson on 10/08/2015.
 */
/** Implementar DDA BRESENHAM ( replicar os metodos do ponto)
 * relação entre dois pontos com cohen da classe ponto para verificar o quadrante**/
public class Poligono {
    List<Ponto2D> poligono;

    public Poligono(List ponto2D) {
        this.poligono = ponto2D;
    }


    public void gravaPonto2D(Ponto2D pColetado){
        poligono.add(pColetado);
    }

    public List<Ponto2D> getListaPontos() {
        return poligono;
    }
    public void clearList(){
        this.poligono.clear();
    }

    public void desenhaCanvas(Canvas canvasFx, Janela mundo,Janela vP,int tipoReta) {
        switch (tipoReta) {
            case 1:
                for (int i = 0; i < poligono.size() - 1; i++) {
                    canvasFx.getGraphicsContext2D().strokeLine(poligono.get(i).xMundoVp(mundo, vP), poligono.get(i).yMundoVp(mundo, vP), poligono.get(i + 1).xMundoVp(mundo, vP),
                            poligono.get(i + 1).yMundoVp(mundo, vP));
                }
                break;
            case 2:
                for (int i = 0; i < poligono.size() - 1; i++) {
                    dda(poligono.get(i).xMundoVp(mundo, vP), poligono.get(i).yMundoVp(mundo, vP), poligono.get(i + 1).xMundoVp(mundo, vP),
                            poligono.get(i + 1).yMundoVp(mundo, vP),canvasFx);
                }
                break;
            default:
                break;
        }
    }


    public void dda(double x1,double y1,double x2,double y2,Canvas desenho){
            double  x,y,erro=0, deltaX, deltaY;
            x=x1;
            y=y1;
            deltaX = x2 - x1;
            deltaY = y2 - y1;

            if((Math.abs(deltaY))>=(Math.abs(deltaX)) && y1>y2 || (Math.abs(deltaY)) < (Math.abs(deltaX))&& deltaY<0){
                x=x2;
                y=y2;
                deltaX = x1-x2;
                deltaY = y1-y2;
            }
            desenho.getGraphicsContext2D().strokeLine(x1,y1,x1,y1);
            if(deltaX>=0){
                if(Math.abs(deltaX)>=Math.abs(deltaY)){
                    for(int i=1;i<Math.abs(deltaX);i++){
                        if(erro<0){
                            x++;
                            desenho.getGraphicsContext2D().strokeLine(x,y,x,y);
                            erro += deltaY;
                        }else {
                            x++;
                            y++;
                            desenho.getGraphicsContext2D().strokeLine(x,y,x,y);
                           erro += deltaY-deltaX;
                        }
                    }
                }else{
                    for(int i=1;i<Math.abs(deltaY);i++){
                        if(erro<0){
                            x++;
                            y++;
                            desenho.getGraphicsContext2D().strokeLine(x,y,x,y);
                            erro += deltaY-deltaX;
                        }else {
                            y++;
                            desenho.getGraphicsContext2D().strokeLine(x,y,x,y);
                            erro += deltaX;
                        }
                    }
                }
            }else if (deltaX == Math.abs(deltaY)){
                for(int i=1;i<Math.abs(deltaX);i++){
                    if(erro<0){
                        x--;
                        desenho.getGraphicsContext2D().strokeLine(x,y,x,y);
                        erro += deltaY;
                    }else {
                        x--;
                        y++;
                        desenho.getGraphicsContext2D().strokeLine(x,y,x,y);
                        erro += deltaY+deltaX;
                    }
                }
            }else{
                for(int i=1;i<Math.abs(deltaY);i++){
                    if(erro<0){
                        x--;
                        y++;
                        desenho.getGraphicsContext2D().strokeLine(x,y,x,y);
                        erro += deltaY+deltaX;
                    }else {
                        y++;
                        desenho.getGraphicsContext2D().strokeLine(x,y,x,y);
                        erro += deltaX;
                    }
                }
            }
            desenho.getGraphicsContext2D().strokeLine(x2, y2, x2, y2);
    }
    public void bresenham(){

    }

}
