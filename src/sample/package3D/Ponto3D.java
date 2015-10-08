package sample.package3D;

import sample.Ponto2D;

/**
 * Created by Wesley Anderson on 07/10/2015.
 */
public class Ponto3D extends Ponto2D {
    private double cordenadaZ;

    public Ponto3D(double cordenadaX, double cordenadaY, double cordenadaZ) {
        super(cordenadaX, cordenadaY);
        this.cordenadaZ = cordenadaZ;
    }

    public double getCordenadaZ() {
        return cordenadaZ;
    }

    public void setCordenadaZ(double cordenadaZ) {
        this.cordenadaZ = cordenadaZ;
    }

    @Override
    public void pontoTransladado(double dx, double dy) {
        super.pontoTransladado(dx, dy);
    }

    @Override
    public void pontoEscalonado(double sx, double sy) {
        super.pontoEscalonado(sx, sy);
    }

    @Override
    public void pontoRotacionado(double angulo) {
        super.pontoRotacionado(angulo);
    }

    @Override
    public void pontoRefletidoX() {
        super.pontoRefletidoX();
    }

    @Override
    public void pontoRefletidoY() {
        super.pontoRefletidoY();
    }

    @Override
    public void pontoRefMatrizX() {
        super.pontoRefMatrizX();
    }
}
