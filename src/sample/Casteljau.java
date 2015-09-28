package sample;

import java.util.*;

/**
 * Created by Wesley Anderson on 23/09/2015.
 */
public class Casteljau {
    private List<Ponto2D> listaPontos = new LinkedList<Ponto2D>();
    private Ponto2D A,B,C;

    public List<Ponto2D> getListaPontos() {
        return listaPontos;
    }
    public Ponto2D getMeioReta(Ponto2D p1,Ponto2D p2){
        Ponto2D centro = null;
        centro.setCordenadaX((p1.getCordenadaX()+p2.getCordenadaX())/2);
        centro.setCordenadaY((p1.getCordenadaY() + p2.getCordenadaY()) / 2);
        return centro;
    }
    public void montaEsquerda(Ponto2D p1,Ponto2D p2,Ponto2D p3){
        getListaPontos().add(p1);
        if((p3.getCordenadaX()-p2.getCordenadaX()<0.05)&&(p3.getCordenadaY()-p2.getCordenadaY()<0.05)){

        }
    }
    public void montaDireita(Ponto2D p1,Ponto2D p2,Ponto2D p3){

    }
    public List<Ponto2D> geraListaCurva(Ponto2D a1,Ponto2D a2,Ponto2D a3){
        Ponto2D aux1,aux2;
        aux1 = getMeioReta(a1,a2);
        aux2 = getMeioReta(a2,a3);
        montaEsquerda(a1,aux1,getMeioReta(aux1,aux2));
        montaDireita(a3,aux2,getMeioReta(aux1,aux2));

        return getListaPontos();
    }
}
