package org.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;


public class Vista extends BorderPane {

    private int raquetaDerechaDesplazamiento;
    private int raquetaIzquierdaDesplazamiento;
    private Timeline animacion;
    private Rectangle raquetaIzquierda;
    private Rectangle raquetaDerecha;
    private StackPane pista;
    private Rectangle bordeSuperior;
    private Rectangle bordeInferior;
    private double desplazamientoX;
    private double desplazamientoY;
    private Circle bola;

    private Rectangle bordeIzquierdo;
    private Rectangle bordeDerecho;

    private Text jugador1;
    private Text jugador2;

    private Text marcador1;
    private Text marcador2;

    private Button empezar;

    private int gol1;
    private int gol2;


    public Vista() {
        crearVista();
        juego();

    }
    private void crearVista(){
        desplazamientoX = 2;
        desplazamientoY = 2;
        this.pista = new StackPane();
        this.raquetaIzquierda = new Rectangle();
        this.raquetaDerecha = new Rectangle();
        this.bola = new Circle(10);
        this.bordeInferior = new Rectangle();
        this.bordeSuperior = new Rectangle();
        this.bordeIzquierdo = new Rectangle();
        this.bordeDerecho = new Rectangle();

        this.gol1 = 0;
        this.jugador1 = new Text("Jugador 1: Marcador: "+ gol1);
        this.jugador1.setFill(Color.BLACK);
        this.jugador1.setStyle("-fx-font-size: 12");


        this.gol2 = 0;
        this.jugador2 = new Text("Jugador 2: Marcador: "+ gol2);
        this.jugador2.setFill(Color.BLACK);
        this.jugador2.setStyle("-fx-font-size: 12");


        this.bola.setFill(Color.BLACK);
        this.raquetaIzquierda.setFill(Color.BLACK);
        this.raquetaDerecha.setFill(Color.BLACK);
        this.bordeSuperior.setFill(Color.BLACK);
        this.bordeInferior.setFill(Color.BLACK);
        this.bordeIzquierdo.setFill(Color.WHITE);
        this.bordeDerecho.setFill(Color.WHITE);

        this.raquetaIzquierda.translateXProperty().bind(pista.widthProperty().divide(2.7));
        this.raquetaIzquierda.heightProperty().bind(pista.widthProperty().divide(5));
        this.raquetaIzquierda.widthProperty().bind(pista.widthProperty().divide(25));

        this.raquetaDerecha.translateXProperty().bind(pista.widthProperty().divide(-2.7));
        this.raquetaDerecha.heightProperty().bind(pista.widthProperty().divide(5));
        this.raquetaDerecha.widthProperty().bind(pista.widthProperty().divide(25));



        this.raquetaDerecha.translateXProperty().bind(pista.widthProperty().divide(-2.7));
        this.raquetaDerecha.heightProperty().bind(pista.widthProperty().divide(5));
        this.raquetaDerecha.widthProperty().bind(pista.widthProperty().divide(25));

        this.bordeIzquierdo.translateXProperty().bind(pista.widthProperty().divide(2.1));
        this.bordeIzquierdo.heightProperty().bind(pista.widthProperty().divide(1.1));
        this.bordeIzquierdo.widthProperty().bind(pista.widthProperty().divide(25));

        this.bordeDerecho.translateXProperty().bind(pista.widthProperty().divide(-2.1));
        this.bordeDerecho.heightProperty().bind(pista.widthProperty().divide(1.1));
        this.bordeDerecho.widthProperty().bind(pista.widthProperty().divide(25));

        this.bordeSuperior.translateYProperty().bind(pista.heightProperty().divide(2.1));
        this.bordeSuperior.heightProperty().bind(pista.widthProperty().divide(25));
        this.bordeSuperior.widthProperty().bind(pista.widthProperty());

        this.bordeInferior.translateYProperty().bind(pista.heightProperty().divide(-2.1));
        this.bordeInferior.heightProperty().bind(pista.widthProperty().divide(25));
        this.bordeInferior.widthProperty().bind(pista.widthProperty());

        this.jugador1.translateYProperty().bind(pista.widthProperty().divide(2.4));
        this.jugador1.translateXProperty().bind(pista.heightProperty().divide(-4.2));

        this.jugador2.translateYProperty().bind(pista.widthProperty().divide(2.4));
        this.jugador2.translateXProperty().bind(pista.heightProperty().divide(4.2));


        pista.prefWidthProperty().bindBidirectional(pista.prefHeightProperty());

        pista.setFocusTraversable(true);
        pista.requestFocus();
        pista.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.Q)
                raquetaDerechaDesplazamiento = -2;
            else if (e.getCode() == KeyCode.A)
                raquetaDerechaDesplazamiento = 2;
            else if (e.getCode() == KeyCode.P)
                raquetaIzquierdaDesplazamiento = -2;
            else if (e.getCode() == KeyCode.L)
                raquetaIzquierdaDesplazamiento = +2;
        });
        pista.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.Q)
                raquetaDerechaDesplazamiento = 0;
            else if (e.getCode() == KeyCode.A)
                raquetaDerechaDesplazamiento = 0;
            else if (e.getCode() == KeyCode.P)
                raquetaIzquierdaDesplazamiento = 0;
            else if (e.getCode() == KeyCode.L)
                raquetaIzquierdaDesplazamiento = 0;
        });

        pista.getChildren().addAll(raquetaDerecha,raquetaIzquierda,bordeInferior,bordeSuperior,bola,jugador1,bordeIzquierdo, bordeDerecho,jugador2);
        this.setCenter(pista);

    }

    private void juego(){
        this.animacion = new Timeline(new KeyFrame(Duration.millis(17),k->{
            moverPelota();
            colision();
            moverPala();


        }

        ));

        animacion.setCycleCount(Timeline.INDEFINITE);
        animacion.play();

    }

    private void colision() {
        //Colision con la raqueta izquierda
        if(bola.getBoundsInParent().intersects(raquetaIzquierda.getBoundsInParent())){
            desplazamientoX = -desplazamientoX;
        }
        //Colision con la raqueta derecha
        if(bola.getBoundsInParent().intersects(raquetaDerecha.getBoundsInParent())){
            desplazamientoX = -desplazamientoX;
        }
        //Colision con el borde superior
        if(bola.getBoundsInParent().intersects(bordeSuperior.getBoundsInParent())){
            desplazamientoY = -desplazamientoY;
        }
        //Colision con el borde inferior
        if(bola.getBoundsInParent().intersects(bordeInferior.getBoundsInParent())){
            desplazamientoY = -desplazamientoY;
        }

        if (raquetaIzquierda.getBoundsInParent().intersects(bordeInferior.getBoundsInParent())) {
            raquetaIzquierda.setTranslateY(raquetaIzquierda.getTranslateY() + 4);
        }
        if (raquetaIzquierda.getBoundsInParent().intersects(bordeSuperior.getBoundsInParent())) {
            raquetaIzquierda.setTranslateY(raquetaIzquierda.getTranslateY() - 4);
        }

        if (raquetaDerecha.getBoundsInParent().intersects(bordeInferior.getBoundsInParent())) {
            raquetaDerecha.setTranslateY(raquetaDerecha.getTranslateY() + 4);
        }
        if (raquetaDerecha.getBoundsInParent().intersects(bordeSuperior.getBoundsInParent())) {
            raquetaDerecha.setTranslateY(raquetaDerecha.getTranslateY() - 4);
        }

        if(bola.getBoundsInParent().intersects(bordeDerecho.getBoundsInParent())){
            this.bola.setTranslateX(0);
            this.bola.setTranslateY(0);
            this.gol2 = this.gol2 +1;
            this.jugador2.setText("Jugador 2: Marcador: "+ gol2);

        }
        if(bola.getBoundsInParent().intersects(bordeIzquierdo.getBoundsInParent())){
            this.bola.setTranslateX(0);
            this.bola.setTranslateY(0);
            this.gol1 = this.gol1 +1;
            this.jugador1.setText("Jugador 1: Marcador: "+ gol1);

        }
    }



    private void moverPelota(){
        bola.setTranslateX(bola.getTranslateX()+desplazamientoX);
        bola.setTranslateY(bola.getTranslateY()+desplazamientoY);

    }

    private void moverPala(){
        raquetaDerecha.setTranslateY(raquetaDerecha.getTranslateY()+raquetaDerechaDesplazamiento);
        raquetaIzquierda.setTranslateY(raquetaIzquierda.getTranslateY()+raquetaIzquierdaDesplazamiento);
    }





}
