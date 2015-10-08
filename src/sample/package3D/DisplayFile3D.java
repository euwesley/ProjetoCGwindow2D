package sample.package3D;

import sample.DisplayFile;
import sample.Janela;
import sample.Poligono;

import java.util.List;

/**
 * Created by Wesley Anderson on 07/10/2015.
 */
public class DisplayFile3D extends DisplayFile{
    private List<Poligono3D> listaPoligono3D;

    public DisplayFile3D(List<Poligono> poligonos, List<Poligono3D> listaPoligono3D) {
        super(poligonos);
        this.listaPoligono3D = listaPoligono3D;
    }

    public List<Poligono3D> getListaPoligono3D() {
        return listaPoligono3D;
    }

    public void setListaPoligono3D(List<Poligono3D> listaPoligono3D) {
        this.listaPoligono3D = listaPoligono3D;
    }

    @Override
    public List<Poligono> clipping(Janela clip) {
        return super.clipping(clip);
    }

    public void gravaPoligono(Poligono3D poliColetado) {
        this.listaPoligono3D.add(poliColetado);
    }
}
