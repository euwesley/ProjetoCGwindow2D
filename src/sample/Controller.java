package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import sun.rmi.server.InactiveGroupException;

import java.util.LinkedList;

public class Controller {
    @FXML
    protected Canvas canvasFx;
    @FXML
    protected Label lblX,lblY,lblX2,lblY2;
    @FXML
    protected TextField txtXmin,txtXmax,txtYmin,txtYmax,txtXc,txtYc,txtR,txtAngulo,txtEscY,txtEscX;
    @FXML
    protected TextField txtMovY,txtMovX;
    @FXML
    protected StackPane plStack;
    @FXML
    protected ToggleGroup rbGroup;

    Janela mundo = null,Vp = null;
    Poligono poligono = null;
    DisplayFile displayFile = null;
    TreeView<String> tree ;
    RadioButton radioButton;

    public void inicioPrograma(){
        canvasFx.getGraphicsContext2D().strokeLine(250, 0, 250, 500);
        canvasFx.getGraphicsContext2D().strokeLine(0, 250, 500, 250);
    }
    public String algoritimoDesenho(){
        radioButton = (RadioButton)rbGroup.getSelectedToggle();
        return radioButton.getId();
    }
    public Integer poligonoSelecionado(){
        String id[]= String.valueOf(tree.getSelectionModel().getSelectedItem().getValue()).split(" ");
        return Integer.valueOf(id[1]);
    }
    public void desenhaBorda() {
        canvasFx.getGraphicsContext2D().clearRect(0,0,500,500);
        canvasFx.getGraphicsContext2D().strokeLine(250, 0, 250, 500);
        canvasFx.getGraphicsContext2D().strokeLine(0, 250, 500, 250);

    }
    public void desenhaPoligono(){
        displayFile.getListaPoligonos().get(poligonoSelecionado()).desenhaCanvas(canvasFx, mundo, Vp, algoritimoDesenho());
    }

    public void monitoraMouse() {
        canvasFx.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (mundo != null && Vp != null) {
                    lblX.setText(String.valueOf(event.getX()));
                    lblY.setText(String.valueOf(event.getY()));
                    lblX2.setText(String.valueOf(xVpMundo((int) event.getX())));
                    lblY2.setText(String.valueOf(yVpMundo((int) event.getY())));
                }
            }
        });
    }
    public void iniciaPoligono(){
        if(mundo!=null && Vp!=null) {
            //canvasFx.getGraphicsContext2D().clearRect(0, 0, canvasFx.getWidth(), canvasFx.getHeight());
            this.poligono = new Poligono(new LinkedList<Ponto2D>());
            this.displayFile = new DisplayFile(new LinkedList<Poligono>());
            coletaPontos2D();
        }else{
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
            dialogoInfo.setTitle("Alerta de Erro");
            dialogoInfo.setHeaderText("Criar Janela do mundo Primeiro!");
            dialogoInfo.showAndWait();
        }

    }
    public void coletaPontos2D(){
        canvasFx.setOnMouseReleased(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {

              if(event.getButton()== MouseButton.SECONDARY){
                  poligono.gravaPonto2D(new Ponto2D(poligono.getListaPontos().get(0).getCordenadaX(),poligono.getListaPontos().get(0).getCordenadaY()));
                  displayFile.gravaPoligono(poligono);
                  mostraPoligonos();
                  poligono.desenhaCanvas(canvasFx,mundo,Vp,algoritimoDesenho());
                  poligono = new Poligono(new LinkedList<Ponto2D>());
              }else{
                  poligono.gravaPonto2D(new Ponto2D(xVpMundo((int) event.getX()), yVpMundo((int) event.getY())));
                  poligono.desenhaCanvas(canvasFx, mundo, Vp,algoritimoDesenho());
              }
           }
       });
    }
    public void iniciaCirculo(){
        if(mundo!=null && Vp!=null) {
            this.poligono = new Poligono(new LinkedList<Ponto2D>());
            this.displayFile = new DisplayFile(new LinkedList<Poligono>());
            desenhaCirculo();
        }else{
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
            dialogoInfo.setTitle("Alerta de Erro");
            dialogoInfo.setHeaderText("Criar Janela do mundo Primeiro!");
            dialogoInfo.showAndWait();
        }

    }
    public void desenhaCirculo(){
            this.poligono.criaPontosCirculo(Double.valueOf(txtR.getText()));
            this.displayFile.gravaPoligono(poligono);
            this.poligono.desenhaCirculo(new Ponto2D(Double.valueOf(txtXc.getText()), Double.valueOf(txtYc.getText())), mundo, Vp, canvasFx.getGraphicsContext2D());
            mostraPoligonos();
            this.poligono = new Poligono(new LinkedList<Ponto2D>());
    }
    public void mostraPoligonos(){
        Image folder = new Image(getClass().getResourceAsStream("img\\folder_16.png"));
        ImageView rootIcon = new ImageView(folder);
        TreeItem<String> rootItem = new TreeItem<String>("Poligonos",rootIcon);
        rootItem.setExpanded(true);

        for (int i = 0; i < displayFile.getListaPoligonos().size(); i++) {
            TreeItem<String> item = new TreeItem<String>("Poligono " + i);
            rootItem.getChildren().add(item);
            int o = displayFile.getListaPoligonos().get(i).getListaPontos().size();
            for (int j = 0; j < o; j++) {
                TreeItem<String> folhas = new TreeItem<String>("X " + displayFile.getListaPoligonos().get(i).getListaPontos().get(j).getCordenadaX() + " Y " + displayFile.getListaPoligonos().get(i).getListaPontos().get(j).getCordenadaY());
                rootItem.getChildren().get(i).getChildren().add(folhas);
            }
        }
        tree = new TreeView<String> (rootItem);
        plStack.getChildren().add(tree);

    }

    public double xVpMundo(int x){
        return ((x-Vp.getCordXMin())/(Vp.getCordXMax()-Vp.getCordXMin()))*(mundo.getCordXMax() - mundo.getCordXMin())+ mundo.getCordXMin();
    }
    public double yVpMundo(int y){
        return (1-(y-Vp.getCordYMin())/ (Vp.getCordYMax()-Vp.getCordYMin()))*(mundo.getCordYMax() - mundo.getCordYMin())+ mundo.getCordYMin();
    }

    public void outCanvas(){
        lblX.setText("Saiu!");
        lblY.setText("Saiu!");
    }
    public void createMundo(){
         this.mundo = new Janela(Double.valueOf(txtXmin.getText()),Double.valueOf(txtXmax.getText()),Double.valueOf(txtYmin.getText()),Double.valueOf(txtYmax.getText()));
         this.Vp = new Janela(0,Double.valueOf(canvasFx.getWidth()),0,Double.valueOf(canvasFx.getHeight()));
        // this.desenhaBorda();
    }
    public void refletirX(){

        displayFile.getListaPoligonos().get(poligonoSelecionado()).refletirPontosX();
        desenhaBorda();
        desenhaPoligono();
    }
    public void refletirY(){
        displayFile.getListaPoligonos().get(poligonoSelecionado()).refletirPontosY();
        desenhaBorda();
        desenhaPoligono();
    }
    public void rotacionar(){
        displayFile.getListaPoligonos().get(poligonoSelecionado()).rotacionarPoligono(Double.valueOf(txtAngulo.getText()));
        desenhaBorda();
        desenhaPoligono();
    }
    public void escalonar(){
        displayFile.getListaPoligonos().get(poligonoSelecionado()).escalonarPoligono(Double.valueOf(txtEscX.getText()), Double.valueOf(txtEscY.getText()));
        desenhaBorda();
        desenhaPoligono();
    }
    public void mover(){
        displayFile.getListaPoligonos().get(poligonoSelecionado()).transladarPoligono(Double.valueOf(txtMovX.getText()), Double.valueOf(txtMovY.getText()));
        desenhaBorda();
        desenhaPoligono();
    }
}