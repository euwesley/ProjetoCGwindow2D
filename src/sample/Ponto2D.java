package sample;

/**
 * Created by Wesley Anderson on 10/08/2015.
 * Implementar ( escalonamento, rotação, translação, reflexao )
 * cluping cohen-sutherland
 */
public class Ponto2D {
  private double cordenadaX,cordenadaY;

    public Ponto2D(double cordenadaX, double cordenadaY) {
        this.cordenadaX = cordenadaX;
        this.cordenadaY = cordenadaY;
    }

    public double getCordenadaX() {
        return cordenadaX;
    }

    public void setCordenadaX(double cordenadaX) {
        this.cordenadaX = cordenadaX;
    }

    public double getCordenadaY() {
        return cordenadaY;
    }

    public void setCordenadaY(double cordenadaY) {
        this.cordenadaY = cordenadaY;
    }

    public int xMundoVp(Janela mundo,Janela Vp){

        return (int) (((getCordenadaX()-mundo.getCordXMin())/(mundo.getCordXMax()-mundo.getCordXMin()))*(Vp.getCordXMax() -
                        Vp.getCordXMin())+ Vp.getCordXMin());
    }
    public int yMundoVp(Janela mundo,Janela Vp){

        return (int) ((1-(getCordenadaY()-mundo.getCordYMin())/(mundo.getCordYMax()-mundo.getCordYMin()))*(Vp.getCordYMax() -
                Vp.getCordYMin())+ Vp.getCordYMin());
    }
    public void pontoTransladado(double dx,double dy){
        this.setCordenadaX(getCordenadaX()+dx);
        this.setCordenadaY(getCordenadaY()+dy);
    }
    public void pontoEscalonado(double sx,double sy){
        this.setCordenadaX(getCordenadaX()*sx);
        this.setCordenadaY(getCordenadaY()*sy);
    }
    public void pontoRotacionado(double angulo){
        this.setCordenadaX((getCordenadaX()*Math.cos(angulo))-(getCordenadaY()*Math.sin(angulo)));
        this.setCordenadaY((getCordenadaX()*Math.cos(angulo))+(getCordenadaY()*Math.sin(angulo)));
    }
    public void pontoRefletidoX(){
        this.setCordenadaX(-getCordenadaX());
    }
    public void pontoRefletidoY(){
        this.setCordenadaY(-getCordenadaY());
    }

}
