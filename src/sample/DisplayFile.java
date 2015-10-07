package sample;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Wesley Anderson on 10/08/2015.
 */
public class DisplayFile {
    List<Poligono> listPoligonos;


    public DisplayFile(List<Poligono> poligonos) {
        this.listPoligonos = poligonos;
    }

    public List<Poligono> clipping(Janela clip){
        List<Poligono> listaAuxiliar = new LinkedList<Poligono>();
        for (int i = 0; i < listPoligonos.size(); i++) {
            listaAuxiliar.addAll(listPoligonos.get(i).clipping(clip));
        }
        return listaAuxiliar;
    }
    public void setListPoligonos(List<Poligono> listPoligonos) {
        this.listPoligonos = listPoligonos;
    }
    public List<Poligono> getListaPoligonos() {
        return listPoligonos;
    }
    public void gravaPoligono(Poligono poliColetado){
        listPoligonos.add(poliColetado);
    }
}
