/*
 * Copyright (C) 2018 José Gabriel Gruber
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package control;

import model.ModeloArvore;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import static control.ControleArvore.*;

/**
 *
 * @author gruber
 */
public class ControleExibicaoArvore extends Application {

    private final int comprimentoQuadrado = 60, alturaQuadrado = 25, comprimento = 1200, altura = 700;
    ModeloArvore raiz = new ModeloArvore();
    ArrayList<ModeloArvore> listaObjetos = new ArrayList();
    ArrayList<Integer> listaValores = new ArrayList();
    TextField txtAdd = new TextField(), txtDel = new TextField();

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Desenho da árvore");

        listaObjetos = obter(raiz, listaObjetos);

        Group root = new Group();
        Canvas canvas = new Canvas(comprimento, altura);
        GraphicsContext contextoGrafico = canvas.getGraphicsContext2D();
        drawShapes(contextoGrafico, listaObjetos);
        root.getChildren().add(canvas);

        txtAdd.setMaxWidth(100);
        txtAdd.setText("");
        txtAdd.setStyle("-fx-accent: #FF5722;"
                + "        -fx-highlight-text-fill: white;"
                + "        -fx-prompt-text-fill: #616161;"
                + "        -fx-font-size: 15;"
                + "        -fx-font-style: normal;"
                + "        -fx-font-family: Noto Sans;"
                + "        -fx-font-weight: normal;"
                + "        -fx-text-fill: #FFF;"
                + "        -fx-highlight: #FF5722;"
                + "        -fx-focus-color: #FF5722;"
                + "        -fx-background-color: #212121;"
                + "        -fx-border-color: transparent transparent #FFF transparent;"
                + "        -fx-border-width: 3;"
                + "        -fx-border-insets: 0;");
        txtAdd.setTranslateX(comprimento - 220);
        txtAdd.setTranslateY(40);
        txtAdd.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue,
                String newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        txtAdd.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });
        txtAdd.setOnAction((ActionEvent event) -> {  
            listaObjetos = obter(raiz, listaObjetos);
            listaValores = obterValoresLista(listaObjetos, listaValores);

            int temp = Integer.parseInt(txtAdd.getText());

            raiz = inserirNodo(raiz, temp, 0, 1);
            raiz = chegaRaiz(raiz);
            listaObjetos = obter(raiz, listaObjetos);
            contextoGrafico.clearRect(0, 0, 1280, 720);
            GraphicsContext gct = canvas.getGraphicsContext2D();
            drawShapes(gct, listaObjetos);
        });

        root.getChildren().add(txtAdd);

        Button btnAdd = new Button();
        btnAdd.setText("Adicionar Valor");
        btnAdd.setTextFill(Color.WHITE);
        btnAdd.setStyle("-fx-font-size: 15;"
                + "             -fx-background-color: #212121;"
                + "             -fx-border-color: #FFFFFF;"
                + "             -fx-border-width: 3");
        btnAdd.setTranslateX(comprimento - 80);
        btnAdd.setTranslateY(40);
        btnAdd.setOnAction((ActionEvent event) -> {
            listaObjetos = obter(raiz, listaObjetos);
            listaValores = obterValoresLista(listaObjetos, listaValores);

            int temp = Integer.parseInt(txtAdd.getText());

            raiz = inserirNodo(raiz, temp, 0, 1);
            raiz = chegaRaiz(raiz);
            listaObjetos = obter(raiz, listaObjetos);
            contextoGrafico.clearRect(0, 0, 1280, 720);
            GraphicsContext gct = canvas.getGraphicsContext2D();
            drawShapes(gct, listaObjetos);
        });
        root.getChildren().add(btnAdd);

        txtDel.setMaxWidth(100);
        txtDel.setText("");
        txtDel.setStyle("-fx-accent: #FF5722;"
                + "        -fx-highlight-text-fill: white;"
                + "        -fx-prompt-text-fill: #616161;"
                + "        -fx-font-size: 15;"
                + "        -fx-font-style: normal;"
                + "        -fx-font-family: Noto Sans;"
                + "        -fx-font-weight: normal;"
                + "        -fx-text-fill: #FFF;"
                + "        -fx-highlight: #FF5722;"
                + "        -fx-focus-color: #FF5722;"
                + "        -fx-background-color: #212121;"
                + "        -fx-border-color: transparent transparent #FFF transparent;"
                + "        -fx-border-width: 3;"
                + "        -fx-border-insets: 0;");
        txtDel.setTranslateX(comprimento - 220);
        txtDel.setTranslateY(100);
        txtDel.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue,
                String newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        txtDel.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });
        txtDel.setOnAction((ActionEvent event) -> {
            listaObjetos = obter(raiz, listaObjetos);
            listaValores = obterValoresLista(listaObjetos, listaValores);

            int temp = Integer.parseInt(txtDel.getText());

            if (listaValores.contains(temp)) {
                raiz = excluir(raiz, temp);
                raiz = chegaRaiz(raiz);
                listaObjetos = obter(raiz, listaObjetos);
                contextoGrafico.clearRect(0, 0, 1280, 720);
                drawShapes(contextoGrafico, listaObjetos);
            } else {
                JOptionPane.showMessageDialog(null, "Informe um valor valido....");
            }
        });

        root.getChildren().add(txtDel);

        Button btnDel = new Button();
        btnDel.setText("Excluir Valor");
        btnDel.setTextFill(Color.WHITE);
        btnDel.setStyle("-fx-font-size: 15;"
                + "             -fx-background-color: #212121;"
                + "             -fx-border-color: #FFFFFF;"
                + "             -fx-border-width: 3");
        btnDel.setTranslateX(comprimento - 80);
        btnDel.setTranslateY(100);
        btnDel.setOnAction((ActionEvent event) -> {
            listaObjetos = obter(raiz, listaObjetos);
            listaValores = obterValoresLista(listaObjetos, listaValores);

            int temp = Integer.parseInt(txtDel.getText());

            if (listaValores.contains(temp)) {
                raiz = excluir(raiz, temp);
                raiz = chegaRaiz(raiz);
                listaObjetos = obter(raiz, listaObjetos);
                contextoGrafico.clearRect(0, 0, 1280, 720);
                drawShapes(contextoGrafico, listaObjetos);
            } else {
                JOptionPane.showMessageDialog(null, "Informe um valor valido....");
            }
        });
        root.getChildren().add(btnDel);
        Scene scene = new Scene(root, comprimento + 80, altura);
        scene.setFill(Paint.valueOf("#212121"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void drawShapes(GraphicsContext gc, List<ModeloArvore> lista) {
        gc.setFont(Font.font(STYLESHEET_MODENA, 20));
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.WHITESMOKE);
        gc.setLineWidth(5);

        lista.stream().map((listaTemp) -> {
            listaTemp.setX(0);
            return listaTemp;
        }).forEach((listaTemp) -> {
            listaTemp.setY(0);
        });

        int linhasArvore = tamanho(lista);

        for (double y = 1; y <= linhasArvore; y++) {
            ModeloArvore nodo = new ModeloArvore();
            double tam = Math.pow(2, y - 1);
            for (double x = 1; x <= tam; x++) {

                for (ModeloArvore listaTemp : lista) {

                    if (listaTemp.getCamada() == y && nodo.getValor() != listaTemp.getValor() 
                            && listaTemp.getColuna() == x && listaTemp.getY() == 0 && listaTemp.getX() == 0) {
                        nodo = listaTemp;
                        break;
                    }
                }

                String value = "" + nodo.getValor();
                double colunasNodo = Math.pow(2, y - 1), linhas = linhasArvore, linha = y, coluna = x,
                        tempX = ((comprimento - (comprimentoQuadrado)) / (colunasNodo + 1)) * coluna,
                        tempY = ((altura - alturaQuadrado) / linhas) * linha;

                if (nodo.getColuna() == x && nodo.getCamada() == y) {

                    nodo.setY(tempY);
                    nodo.setX(tempX);
                    if (nodo.getPai() != null) {
                        gc.strokeLine(nodo.getPai().getX() + (comprimentoQuadrado / 2), nodo.getPai().getY() 
                                + alturaQuadrado, nodo.getX() + (comprimentoQuadrado / 2), nodo.getY());
                    }

                    gc.strokeRoundRect(tempX, tempY, comprimentoQuadrado, alturaQuadrado, 10, 10);
                    gc.fillText(value, tempX + 10, tempY + alturaQuadrado - 5);
                }
            }
        }
    }
}
