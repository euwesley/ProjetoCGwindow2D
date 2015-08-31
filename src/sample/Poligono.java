package sample;

import javafx.scene.canvas.Canvas;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Wesley Anderson on 10/08/2015.
 */
/** Implementar DDA BRESENHAM ( replicar os metodos do ponto) **/
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

    public void desenhaCanvas(Canvas canvasFx, Janela mundo,Janela vP){
        for(int i=0;i<poligono.size()-1;i++){
            canvasFx.getGraphicsContext2D().strokeLine(poligono.get(i).xMundoVp(mundo,vP),poligono.get(i).yMundoVp(mundo,vP),poligono.get(i+1).xMundoVp(mundo,vP),
                    poligono.get(i+1).yMundoVp(mundo,vP));
        }
    }
    public void dda(){


    }
    public void bresenham(){

    }

}
