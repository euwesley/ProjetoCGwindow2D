package sample;

/**
 * Created by Wesley Anderson on 10/08/2015.
 */
public class Janela {
    private double cordXMin,cordXMax,cordYMin,cordYMax;
    private int deslocamento = 5;

    public Janela(double cordXMin, double cordXMax, double cordYMin, double cordYMax) {
        this.cordXMin = cordXMin;
        this.cordXMax = cordXMax;
        this.cordYMin = cordYMin;
        this.cordYMax = cordYMax;
    }

    public double getCordXMin() {
        return cordXMin;
    }

    public void setCordXMin(double cordXMin) {
        this.cordXMin = cordXMin;
    }

    public double getCordXMax() {
        return cordXMax;
    }

    public void setCordXMax(double cordXMax) {
        this.cordXMax = cordXMax;
    }

    public double getCordYMin() {
        return cordYMin;
    }

    public void setCordYMin(double cordYMin) {
        this.cordYMin = cordYMin;
    }

    public double getCordYMax() {
        return cordYMax;
    }

    public void setCordYMax(double cordYMax) {
        this.cordYMax = cordYMax;
    }

    public void moverDireita(){
        this.setCordXMin(this.getCordXMin() + deslocamento);
        this.setCordXMax(this.getCordXMax() + deslocamento);
    }
    public void moverEsquerda(){
        this.setCordXMin(this.getCordXMin() - deslocamento);
        this.setCordXMax(this.getCordXMax() - deslocamento);
    }
    public void moverCima(){
        this.setCordYMin(this.getCordYMin() + deslocamento);
        this.setCordYMax(this.getCordYMax() + deslocamento);
    }
    public void moverBaixo(){
        this.setCordYMin(this.getCordYMin() - deslocamento);
        this.setCordYMax(this.getCordYMax() - deslocamento);
    }
}
