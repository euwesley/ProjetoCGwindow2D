package sample.clipping;

import sample.DisplayFile;
import sample.Janela;
import sample.Poligono;
import sample.Ponto2D;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Wesley Anderson on 05/10/2015.
 */
public class Cohen {

    private int INSIDE = 0; // 0000
    private int LEFT = 1;   // 0001
    private int RIGHT = 2;  // 0010
    private int BOTTOM = 4; // 0100
    private int TOP = 8;    // 1000
    double xmin,xmax,ymin,ymax;

    DisplayFile display;
// Compute the bit code for a point (x, y) using the clip rectangle
// bounded diagonally by (xmin, ymin), and (xmax, ymax)

// ASSUME THAT xmax, xmin, ymax and ymin are global constants.

    public Cohen(Janela janelaClip) {
        this.xmin = janelaClip.getCordXMin();
        this.xmax = janelaClip.getCordXMax();
        this.ymin = janelaClip.getCordYMin();
        this.ymax = janelaClip.getCordYMax();
    }

    public int ComputeOutCode(double x, double y)
    {
        int code;

        code = INSIDE;          // initialised as being inside of clip window

        if (x < xmin)           // to the left of clip window
            code |= LEFT;
        else if (x > xmax)      // to the right of clip window
            code |= RIGHT;
        if (y < ymin)           // below the clip window
            code |= BOTTOM;
        else if (y > ymax)      // above the clip window
            code |= TOP;

        return code;
    }

    public int andLogico(int a,int b){
        return a & b;
    }
    public void calculaPontoCliping(Ponto2D ponto,double m,List<Ponto2D> auxPontos){
        double yE,yD,xT,xB;
        Ponto2D direita,esquerda,topo,fundo;
        yE = (m*(xmin - ponto.getCordenadaX()))+ponto.getCordenadaY();
        yD = (m*(xmax - ponto.getCordenadaX()))+ponto.getCordenadaY();
        xT = (ponto.getCordenadaX()+((1/m)*(ymax - ponto.getCordenadaY())));
        xB = (ponto.getCordenadaX()+((1/m)*(ymin - ponto.getCordenadaY())));

        if(ymin <= yE && yE <= ymax){
            esquerda = new Ponto2D(xmin,yE);
            auxPontos.add(esquerda);
        }
        if(ymin <= yD && yD <= ymax){
            direita = new Ponto2D(xmax,yD);
            auxPontos.add(direita);
        }
        if(xmin <= xT && xT <= xmax){
            topo = new Ponto2D(xT,ymax);
            auxPontos.add(topo);
        }
        if(xmin <= xB && xB <= xmax) {
            fundo = new Ponto2D(xB, ymin);
            auxPontos.add(fundo);
        }
    }
    public void calculaPontoSozinho(Ponto2D pontoS,double m,List<Ponto2D> auxPontos){
        double yE,yD,xT,xB,x,y;
        Ponto2D direita,esquerda,topo,fundo;
        boolean areaX,areaY;
        areaX = (xmin < pontoS.getCordenadaX() && pontoS.getCordenadaX() < xmax);
        areaY = (ymin < pontoS.getCordenadaY() && pontoS.getCordenadaY() < ymax);

        if( areaX && !areaY ){
            xT = (pontoS.getCordenadaX()+((1/m)*(ymax - pontoS.getCordenadaY())));
            xB = (pontoS.getCordenadaX()+((1/m)*(ymin - pontoS.getCordenadaY())));
            if((pontoS.getCordenadaY()>ymax)){
                topo = new Ponto2D(xT,ymax);
                auxPontos.add(topo);
            }
            if((pontoS.getCordenadaY()<ymin)) {
                fundo = new Ponto2D(xB, ymin);
                auxPontos.add(fundo);
            }
        }
        if(!areaX && areaY){
            yE = (m*(xmin - pontoS.getCordenadaX()))+pontoS.getCordenadaY();
            yD = (m*(xmax - pontoS.getCordenadaX()))+pontoS.getCordenadaY();
            if((pontoS.getCordenadaX() < xmin)){
                esquerda = new Ponto2D(xmin,yE);
                auxPontos.add(esquerda);
            }
            if(pontoS.getCordenadaX()>xmax){
                direita = new Ponto2D(xmax,yD);
                auxPontos.add(direita);
            }
        }
        if(!areaX && !areaY){
            xT = (pontoS.getCordenadaX()+((1/m)*(ymax - pontoS.getCordenadaY())));
            xB = (pontoS.getCordenadaX()+((1/m)*(ymin - pontoS.getCordenadaY())));
            yE = (m*(xmin - pontoS.getCordenadaX()))+pontoS.getCordenadaY();
            yD = (m*(xmax - pontoS.getCordenadaX()))+pontoS.getCordenadaY();

            if(ymin <= yE && yE <= ymax && pontoS.getCordenadaX() < xmin){
                esquerda = new Ponto2D(xmin,yE);
                auxPontos.add(esquerda);
            }
            if(ymin <= yD && yD <= ymax && pontoS.getCordenadaX() > xmax){
                direita = new Ponto2D(xmax,yD);
                auxPontos.add(direita);
            }
            if(xmin <= xT && xT <= xmax && pontoS.getCordenadaY() > ymax){
                topo = new Ponto2D(xT,ymax);
                auxPontos.add(topo);
            }
            if(xmin <= xB && xB <= xmax && pontoS.getCordenadaY() < ymin) {
                fundo = new Ponto2D(xB, ymin);
                auxPontos.add(fundo);
            }
        }
    }
    public List<Ponto2D> CohenSutherlandLineClip(Ponto2D p1, Ponto2D p2)
    {
        List<Ponto2D> auxPontos = new LinkedList<Ponto2D>();
        int outcode0 = ComputeOutCode(p1.getCordenadaX(), p1.getCordenadaY());
        int outcode1 = ComputeOutCode(p2.getCordenadaX(), p2.getCordenadaY());
        boolean accept = false;
        String bin1 = Integer.toString(outcode0, 2);
        String bin2= Integer.toString(outcode1, 2);
        System.out.println("code 0: "+ bin1);
        System.out.println("code 1: "+ bin2);
        double m=((p2.getCordenadaY() - p1.getCordenadaY())/(p2.getCordenadaX()-p1.getCordenadaX()));

        if ( outcode0 == INSIDE && outcode1 == INSIDE){
            auxPontos.add(p1);
            auxPontos.add(p2);
        } else if(andLogico(outcode0,outcode1)==INSIDE){
            if(outcode0==INSIDE){
                auxPontos.add(p1);
                this.calculaPontoSozinho(p2,m,auxPontos);
            }else if(outcode1==INSIDE){
                this.calculaPontoSozinho(p1,m,auxPontos);
                auxPontos.add(p2);
            }else {
                this.calculaPontoCliping(p1, m,auxPontos);
            }
        }
       return  auxPontos;
    }
}
