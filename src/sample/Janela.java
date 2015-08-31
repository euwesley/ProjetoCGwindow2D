package sample;

/**
 * Created by Wesley Anderson on 10/08/2015.
 */
public class Janela {
    double cordXMin,cordXMax,cordYMin,cordYMax;

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
}
