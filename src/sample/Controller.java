package sample;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import sample.package3D.DisplayFile3D;
import sample.package3D.Poligono3D;
import sample.package3D.Ponto3D;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Controller {
    @FXML
    protected Pane pnlCurva,pnlClip,pnl3D;
    @FXML
    protected Canvas canvasFx;
    @FXML
    protected ComboBox comBox3D;
    @FXML
    protected CheckBox ckb3DX,ckb3DY,ckb3DZ;
    @FXML
    protected Label lblX,lblY,lblX2,lblY2;
    @FXML
    protected TextField txtXmin,txtXmax,txtYmin,txtYmax,txtXc,txtYc,txtR,txtAngulo,txtEscY,txtEscX;
    @FXML
    protected TextField txtMovY,txtMovX,txtYreta,txtXreta,txtNPontos,txtAngulo3D,txtEscala3D;
    @FXML
    protected TextField txtMov3DX,txtMov3DY,txtMov3DZ;
    @FXML
    protected TreeView treeTeste;
    @FXML
    protected ImageView imgViewRoot;
    @FXML
    protected StackPane plStack;
    @FXML
    protected ToggleGroup rbGroup,rbPoligono,rbRotacao3D;
    @FXML
    protected Slider brScalaMundo;

    Janela mundo = new Janela(-250,250,-250,250);
    Janela Vp = new Janela(0,500,0,500);
    Janela janelaClip = new Janela(-100,100,-100,100);
    Poligono poligono = null;
    Poligono3D poligono3D = null;
    DisplayFile displayFile = null;
    DisplayFile displayClip = null;
    DisplayFile3D displayFile3D = null;
    RadioButton radioButton;
    public void reinicia(){
        this.poligono.clearList();
        this.displayFile.getListaPoligonos().clear();
        if(this.displayClip != null) {
            this.displayClip.getListaPoligonos().clear();
        }
        this.desenhaBorda();
        this.mostraPoligonos("Poligonos ");
    }
    public void mostraPainelClip(){
        this.pnlCurva.setVisible(false);
        this.pnl3D.setVisible(false);
        this.pnlClip.setVisible(true);
        this.desenhaBorda();
    }
    public void mostraPainelCurva(){
        this.pnlClip.setVisible(false);
        this.pnl3D.setVisible(false);
        this.pnlCurva.setVisible(true);
        this.desenhaBorda();

    }
    public void mostraPainel3D(){
        this.pnlClip.setVisible(false);
        this.pnl3D.setVisible(true);
        this.pnlCurva.setVisible(false);
        this.desenhaBorda();

    }
    public void inicioPrograma(){
        this.desenhaBorda();
    }
    public String algoritimoDesenho(){
        radioButton = (RadioButton)rbGroup.getSelectedToggle();
        return radioButton.getId();
    }
    public String eixoDeRotacao3D(){
        radioButton = (RadioButton)rbRotacao3D.getSelectedToggle();
        return radioButton.getId();
    }
    public String tipoPoligono(){
        radioButton = (RadioButton)rbPoligono.getSelectedToggle();
        return radioButton.getId();
    }
    public Integer poligonoSelecionado(){

        return (treeTeste.getFocusModel().getFocusedIndex()-1);
    }
    public void desenhaBorda() {

        canvasFx.getGraphicsContext2D().clearRect(0,0,this.Vp.getCordXMax(),this.Vp.getCordYMax());
        Poligono retaX=new Poligono(new LinkedList<Ponto2D>());
        Poligono retaY=new Poligono(new LinkedList<Ponto2D>());
        Ponto2D xMin=new Ponto2D(this.mundo.getCordXMin(),0),xMax=new Ponto2D(this.mundo.getCordXMax(),0),
                yMin=new Ponto2D(0,this.mundo.getCordYMin()),yMax=new Ponto2D(0,this.mundo.getCordYMax());

        retaX.getListaPontos().add(xMin);
        retaX.getListaPontos().add(xMax);
        retaY.getListaPontos().add(yMin);
        retaY.getListaPontos().add(yMax);
        if(!this.pnl3D.isVisible()) {
            retaX.desenhaCanvas(canvasFx, this.mundo, this.Vp, algoritimoDesenho());
            retaY.desenhaCanvas(canvasFx, this.mundo, this.Vp, algoritimoDesenho());
        }
        if(displayFile != null && !pnl3D.isVisible()){
            displayPoligonos();
        }
        if(displayFile3D !=null && this.pnl3D.isVisible()){
            displayPoligono3D();
        }
        if(this.pnlClip.isVisible()){
            this.desenhaJanela(janelaClip);
        }
        if(displayClip != null && this.pnlClip.isVisible()){
            canvasFx.getGraphicsContext2D().setStroke(Color.RED);
            canvasFx.getGraphicsContext2D().setLineWidth(2);
            for (int i = 0; i < displayClip.getListaPoligonos().size() ; i++) {
                displayClip.getListaPoligonos().get(i).desenhaCanvas(canvasFx,mundo,Vp,algoritimoDesenho());
            }
            canvasFx.getGraphicsContext2D().setStroke(Color.BLACK);
            canvasFx.getGraphicsContext2D().setLineWidth(1);
        }


    }
    public void desenhaPoligono(){
        displayFile.getListaPoligonos().get(poligonoSelecionado()).desenhaCanvas(canvasFx, mundo, Vp, algoritimoDesenho());
    }
    public void displayPoligonos(){
        for (int i = 0; i <displayFile.getListaPoligonos().size() ; i++) {
            displayFile.getListaPoligonos().get(i).desenhaCanvas(canvasFx,mundo,Vp,algoritimoDesenho());
        }
    }
    public void displayPoligono3D(){
        for (int i = 0; i < displayFile3D.getListaPoligono3D().size() ; i++) {
            displayFile3D.getListaPoligono3D().get(i).desenhaCanvas(canvasFx,mundo,Vp,algoritimoDesenho());
        }
    }
    public void desenhaJanela(Janela janela){
        List<Ponto2D> pontosJanela = new LinkedList<>();
        pontosJanela.add(new Ponto2D(janela.getCordXMin(),janela.getCordYMin()));
        pontosJanela.add(new Ponto2D(janela.getCordXMin(),janela.getCordYMax()));
        pontosJanela.add(new Ponto2D(janela.getCordXMax(),janela.getCordYMax()));
        pontosJanela.add(new Ponto2D(janela.getCordXMax(),janela.getCordYMin()));
        pontosJanela.add(new Ponto2D(janela.getCordXMin(),janela.getCordYMin()));
        Poligono janelaDesenha = new Poligono(pontosJanela);
        janelaDesenha.desenhaCanvas(canvasFx,mundo,Vp,algoritimoDesenho());
    }
    public void monitoraMouse() {
        canvasFx.setOnMouseMoved(event -> {
            if (mundo != null && Vp != null) {
                lblX.setText(String.valueOf(event.getX()));
                lblY.setText(String.valueOf(event.getY()));
                lblX2.setText(String.valueOf(xVpMundo((int) event.getX())));
                lblY2.setText(String.valueOf(yVpMundo((int) event.getY())));
            }
        });
    }
    public void iniciaPoligono(){
        if(mundo!=null && Vp!=null) {
            //canvasFx.getGraphicsContext2D().clearRect(0, 0, canvasFx.getWidth(), canvasFx.getHeight());
            this.poligono = new Poligono(new LinkedList<Ponto2D>());
            this.displayFile = new DisplayFile(new LinkedList<>());
            coletaPontos2D();

        }else{
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
            dialogoInfo.setTitle("Alerta de Erro");
            dialogoInfo.setHeaderText("Criar Janela do mundo Primeiro!");
            dialogoInfo.showAndWait();
        }

    }
    public void pontosReta(){
        if(this.poligono == null) {
            //canvasFx.getGraphicsContext2D().clearRect(0, 0, canvasFx.getWidth(), canvasFx.getHeight());
            this.poligono = new Poligono(new LinkedList<Ponto2D>());
            this.displayFile = new DisplayFile(new LinkedList<>());
            this.poligono.gravaPonto2D(new Ponto2D(Double.valueOf(txtXreta.getText()),Double.valueOf(txtYreta.getText())));
            displayFile.gravaPoligono(poligono);
            mostraPoligonos("Curva ");
        }else{
            this.poligono.gravaPonto2D(new Ponto2D(Double.valueOf(txtXreta.getText()),Double.valueOf(txtYreta.getText())));
            mostraPoligonos("Curva ");
        }
        if(poligono.getListaPontos().size() == 4){
            //displayFile.gravaPoligono(poligono);
            mostraPoligonos("Curva ");
            poligono.desenhaCanvas(canvasFx,mundo,Vp,algoritimoDesenho());
            poligono = new Poligono(new LinkedList<Ponto2D>());
        }
    }
    public void finalizaPoligono(){
        if(this.poligono.getListaPontos().size()>=2) {
            String bt = tipoPoligono();
            if (bt.contentEquals("rbFechado")) {
                poligono.gravaPonto2D(new Ponto2D(poligono.getListaPontos().get(0).getCordenadaX(), poligono.getListaPontos().get(0).getCordenadaY()));
            }
            displayFile.gravaPoligono(poligono);
            mostraPoligonos("Poligono ");
            poligono.desenhaCanvas(canvasFx, mundo, Vp, algoritimoDesenho());
            poligono = new Poligono(new LinkedList<Ponto2D>());
            desenhaBorda();
        }
    }
    public void coletaPontos2D(){

        canvasFx.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> {

            if (event.getButton() == MouseButton.SECONDARY && poligono.getListaPontos().size() >= 2) {
                String bt = tipoPoligono();
                if (bt.contentEquals("rbFechado")) {
                    poligono.gravaPonto2D(new Ponto2D(poligono.getListaPontos().get(0).getCordenadaX(), poligono.getListaPontos().get(0).getCordenadaY()));
                }
                displayFile.gravaPoligono(poligono);
                mostraPoligonos("Poligono ");
                poligono.desenhaCanvas(canvasFx, mundo, Vp, algoritimoDesenho());
                poligono = new Poligono(new LinkedList<Ponto2D>());
                desenhaBorda();

            } else if (event.getButton() == MouseButton.PRIMARY) {
                poligono.gravaPonto2D(new Ponto2D((int) xVpMundo((int) event.getX()), (int) yVpMundo((int) event.getY())));
                poligono.desenhaCanvas(canvasFx, mundo, Vp, algoritimoDesenho());
                //mostraPoligonos("Poligono ");
            }
            // capMouse.setText(String.valueOf(event.getButton()));
        });

    }

    public void iniciaCirculo(){
        if(mundo!=null && Vp!=null) {
            this.poligono = new Poligono(new LinkedList<Ponto2D>());
            this.displayFile = new DisplayFile(new LinkedList<>());
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
            mostraPoligonos("Curva ");
            this.poligono = new Poligono(new LinkedList<Ponto2D>());
    }
    public void mostraPoligonos(String nome){

        TreeItem<String> rootItem = new TreeItem<>("Poligonos",imgViewRoot);
        rootItem.setExpanded(true);

        for (int i = 0; i < displayFile.getListaPoligonos().size(); i++) {
            TreeItem<String> item = new TreeItem<>(nome + i);
            rootItem.getChildren().add(item);
            int o = displayFile.getListaPoligonos().get(i).getListaPontos().size();
            for (int j = 0; j < o; j++) {
                TreeItem<String> folhas = new TreeItem<>("X " + displayFile.getListaPoligonos().get(i).getListaPontos().get(j).getCordenadaX() + " Y " + displayFile.getListaPoligonos().get(i).getListaPontos().get(j).getCordenadaY());
                rootItem.getChildren().get(i).getChildren().add(folhas);
            }
            //item.setExpanded(true);
        }
        treeTeste.setRoot(rootItem);
        rootItem.setExpanded(true);
        //plStack.getChildren().add(treeTeste);


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
         //this.mundo = new Janela(Double.valueOf(txtXmin.getText()),Double.valueOf(txtXmax.getText()),Double.valueOf(txtYmin.getText()),Double.valueOf(txtYmax.getText()));
        this.mundo.setCordXMax(Double.valueOf(txtXmax.getText()));
        this.mundo.setCordXMin(Double.valueOf(txtXmin.getText()));
        this.mundo.setCordYMax(Double.valueOf(txtYmax.getText()));
        this.mundo.setCordYMin(Double.valueOf(txtYmin.getText()));
        this.Vp.setCordXMax(Double.valueOf(canvasFx.getWidth()));
        this.Vp.setCordXMin(0);
        this.Vp.setCordYMax(Double.valueOf(canvasFx.getHeight()));
        this.Vp.setCordYMin(0);
        //this.Vp = new Janela(0,Double.valueOf(canvasFx.getWidth()),0,Double.valueOf(canvasFx.getHeight()));
        desenhaBorda();
    }
    public void moveMundoDireita(){
        this.mundo.moverDireita();
        desenhaBorda();
    }
    public void moveMundoEsquerda(){
        this.mundo.moverEsquerda();
        desenhaBorda();
    }
    public void moveMundoSubir(){
        this.mundo.moverCima();
        desenhaBorda();
    }
    public void moveMundoBaixo(){
        this.mundo.moverBaixo();
        desenhaBorda();
    }
    public void scalaMundo(){
        this.mundo.setCordXMax((Double.valueOf(txtXmax.getText()) - brScalaMundo.getValue()));
        this.mundo.setCordXMin((Double.valueOf(txtXmin.getText()) + brScalaMundo.getValue()));
        this.mundo.setCordYMax((Double.valueOf(txtYmax.getText()) - brScalaMundo.getValue()));
        this.mundo.setCordYMin((Double.valueOf(txtYmin.getText()) + brScalaMundo.getValue()));
        desenhaBorda();

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
    public void casteljau(){

        if( displayFile.getListaPoligonos().get(poligonoSelecionado()).getListaPontos().size()==3) {
            displayFile.getListaPoligonos().get(poligonoSelecionado()).curvaCasteljau();
            mostraPoligonos("Curva Casteljau ");
            desenhaBorda();
        }else{
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
            dialogoInfo.setTitle("Alerta de Erro");
            dialogoInfo.setHeaderText("O poligono Selecionado de ter apenas 3 pontos");
            dialogoInfo.showAndWait();
        }
    }
    public void hermit(){
        displayFile.getListaPoligonos().get(poligonoSelecionado()).curvaHermit(Integer.valueOf(txtNPontos.getText()));
        mostraPoligonos("Curva Hermit ");
        desenhaBorda();
    }
    public void bezier(){
        displayFile.getListaPoligonos().get(poligonoSelecionado()).curvaBezier(Integer.valueOf(txtNPontos.getText()));
        mostraPoligonos("Curva Bezier ");
        desenhaBorda();
    }
    public void bSplines(){
        displayFile.getListaPoligonos().get(poligonoSelecionado()).curvaBsplines(Integer.valueOf(txtNPontos.getText()));
        mostraPoligonos("Curva bSplines ");
        desenhaBorda();
    }
    public void cliping(){

        if( displayFile!= null) {
            displayClip = new DisplayFile(displayFile.clipping(janelaClip));
            canvasFx.getGraphicsContext2D().clearRect(0,0,this.Vp.getCordXMax(),this.Vp.getCordYMax());
            desenhaJanela(janelaClip);
            desenhaBorda();
            canvasFx.getGraphicsContext2D().setStroke(Color.RED);
            canvasFx.getGraphicsContext2D().setLineWidth(2);
            for (int i = 0; i < displayClip.getListaPoligonos().size() ; i++) {
                displayClip.getListaPoligonos().get(i).desenhaCanvas(canvasFx,mundo,Vp,algoritimoDesenho());
            }
            canvasFx.getGraphicsContext2D().setStroke(Color.BLACK);
            canvasFx.getGraphicsContext2D().setLineWidth(1);

        }else{
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
            dialogoInfo.setTitle("Alerta de Erro");
            dialogoInfo.setHeaderText("Criar um Poligono Primeiro!");
            dialogoInfo.showAndWait();
        }

    }
    public void gravaPoligonos3D(){
        poligono3D = new Poligono3D(new LinkedList<Ponto2D>(),new LinkedList<>());
        displayFile3D = new DisplayFile3D(new LinkedList<>(),new LinkedList<>());
        String arquivo = comBox3D.getSelectionModel().getSelectedItem().toString();
        System.out.println(arquivo);
        File file = new File(System.getProperty("user.dir")+"\\src\\sample\\txt\\"+arquivo+".txt");
        try {
            FileReader reader = new FileReader(file);
            BufferedReader input = new BufferedReader(reader);
            String linha,texto = arquivo;
            String valor[];
            while ((linha = input.readLine()) != null) {
               // System.out.println(linha.length());
                if(linha!=null && linha.length()>0){
                    valor = linha.split(" ");
                    poligono3D.getListaDePontos3d().add(new Ponto3D(Double.valueOf(valor[0]),Double.valueOf(valor[1]),Double.valueOf(valor[2])));
                }

               // texto += linha+"\n";
            }
            input.close();
            displayFile3D.gravaPoligono(poligono3D);
            //txtAreaPontos3D.setText(texto);

            poligono3D.desenhaCanvas(canvasFx,mundo,Vp,algoritimoDesenho());

        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }
    public void rotacionar3D(){
        poligono3D.rotacionarPoligono(Double.valueOf(this.txtAngulo3D.getText()),eixoDeRotacao3D());

       desenhaBorda();
    }
    public void escalonar3D(){
        double dX=0,dY=0,dZ=0;
        if(this.ckb3DX.isSelected()){
            dX = Double.valueOf(txtEscala3D.getText());
        }else {
            dX=1;
        }
        if(this.ckb3DY.isSelected()){
            dY=Double.valueOf(txtEscala3D.getText());
        }else{
            dY=1;
        }
        if(this.ckb3DZ.isSelected()){
            dZ=Double.valueOf(txtEscala3D.getText());
        }else {
            dZ=1;
        }
        poligono3D.escalonarPoligono3D(dX,dY,dZ);
        desenhaBorda();
    }
    public void transladar3D(){
        double mdx,mdy,mdz;
        if(!this.txtMov3DX.getText().isEmpty()){
            mdx = Double.valueOf(this.txtMov3DX.getText());
        }else{
            mdx = 0;
        }
        if(!this.txtMov3DY.getText().isEmpty()){
            mdy = Double.valueOf(this.txtMov3DY.getText());
        }else{
            mdy = 0;
        }
        if(!this.txtMov3DZ.getText().isEmpty()){
            mdz = Double.valueOf(this.txtMov3DZ.getText());
        }else{
            mdz = 0;
        }
        poligono3D.transladarPoligono3D(mdx,mdy,mdz);
        desenhaBorda();
    }
    public void montaComboBox3d(){
        ObservableList<String> lista = FXCollections.observableArrayList();
        lista.add("Piramide");
        lista.add("Cubo");
        comBox3D.setItems(lista);
    }

}