package sample;

/**
 * Created by Wesley Anderson on 10/08/2015.
 * Implementar ( escalonamento, rota��o, transla��o, reflexao )
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
        //angulo = (angulo/180)*Math.PI;
        this.setCordenadaX((getCordenadaX()*Math.cos(Math.toRadians(angulo)))-(getCordenadaY()*Math.sin(Math.toRadians(angulo))));
        this.setCordenadaY((getCordenadaX()*Math.sin(Math.toRadians(angulo)))+(getCordenadaY()*Math.cos(Math.toRadians(angulo))));
    }
    public void pontoRefletidoX(){
        this.setCordenadaX(-getCordenadaX());
    }
    public void pontoRefletidoY(){
        this.setCordenadaY(-getCordenadaY());
    }

    public void pontoRefMatrizX(){
        int matrizX[][] = new int[2][2];
        matrizX[0][0] = -1;
        matrizX[0][1] = 0;
        matrizX[1][0] = 0;
        matrizX[1][1] = 1;

        for (int i = 0; i < 2 ; i++) {
            for (int j = 0; j < 2 ; j++) {

            }
        }

    }

}
