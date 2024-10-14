/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Carlos Zetti
 * Subscribe our Channel --> https://www.youtube.com/channel/UCPgcmw0LXToDn49akUEJBkQ
 * Thanks for the support guys! <3
 */
public class Main extends Application {
    
     private Stage stage; 
    private double x = 0;
    private double y = 0;
    
    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("meuFxmlNovo.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }
    
    
    public void abrirMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/telamaior.fxml"));
            Scene scene = new Scene(loader.load());
        
            // Pegue a janela atual (stage)
            stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
           
        
            // Adiciona os manipuladores para mover a janela
            scene.setOnMousePressed(mouseEvent -> {
               x = mouseEvent.getSceneX();
               y = mouseEvent.getSceneY();
            });

            scene.setOnMouseDragged(mouseEvent -> {
               stage.setX(mouseEvent.getScreenX() - x);
               stage.setY(mouseEvent.getScreenY() - y);
            });

            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
   public void abrirLoginNovoStage(ActionEvent e) {
    try {
        // Carrega o FXML da tela de login
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
        Parent root = loader.load();
        
        // Cria uma nova janela (Stage) para a tela de login
        Stage loginStage = new Stage();
        loginStage.initStyle(StageStyle.TRANSPARENT); // Define o estilo da janela, se necessário
        
        // Adiciona a cena ao novo stage
        Scene scene = new Scene(root);
        loginStage.setScene(scene);
        
        // Exibe a nova janela de login
        loginStage.show();
        
        // Fecha o stage atual (janela do menu)
        Stage menuStage = (Stage) ((javafx.scene.Node) e.getSource()).getScene().getWindow();
        menuStage.close();
        
    } catch (IOException ex) {
        ex.printStackTrace();
    }
    }
    
}
