package sample;

import java.util.*;

/**
 * Created by Wesley Anderson on 23/09/2015.
 */
public class Casteljau {
    private List<Ponto2D> listaPontos = new LinkedList<Ponto2D>();
    private Ponto2D A,B,C;

    public Casteljau(List<Ponto2D> x) {
        A = x.get(0);
        B = x.get(1);
        C = x.get(2);
    }

    public List<Ponto2D> getListaPontos() {
        return listaPontos;
    }
    public Ponto2D getMeioReta(Ponto2D p1,Ponto2D p2){
        Ponto2D centro = new Ponto2D(0,0);
        centro.setCordenadaX((p1.getCordenadaX() + p2.getCordenadaX())/2);
        centro.setCordenadaY((p1.getCordenadaY() + p2.getCordenadaY()) / 2);
        return centro;
    }

    public void montaCurva(Ponto2D p1,Ponto2D p2,Ponto2D p3){
        getListaPontos().add(p1);
        Ponto2D aux1,aux2;
        aux1 = getMeioReta(p1,p2);
        aux2 = getMeioReta(p2,p3);
        double d=Math.sqrt(Math.pow((p2.getCordenadaX() - p1.getCordenadaX()),2) + Math.pow((p2.getCordenadaY() - p1.getCordenadaY()),2));
        if(d <= 1){
            getListaPontos().add(p1);
        }else {
            montaCurva(p1, aux1, getMeioReta(aux1, aux2));
            montaCurva(getMeioReta(aux1,aux2),aux2,p3);
        }

    }
    public List<Ponto2D> geraListaCurva() {

        montaCurva(this.A, this.B,this.C);


        return getListaPontos();
    }
}
