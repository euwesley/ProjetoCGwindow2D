package sample;

import java.util.List;

/**
 * Created by Wesley Anderson on 10/08/2015.
 */
public class DisplayFile {
    List<Poligono> listPoligonos;

    public DisplayFile(List<Poligono> poligonos) {
        this.listPoligonos = poligonos;
    }

    public List<Poligono> getListaPoligonos() {
        return listPoligonos;
    }
    public void gravaPoligono(Poligono poliColetado){
        listPoligonos.add(poliColetado);
    }
}
