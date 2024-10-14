/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import dao.Conexao;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;


/**
 *
 * @author Carlos Zetti
 */
public class Mensagem {
     public static void mostrarDialogoInformacao(String titulo, String cabecalho, String conteudo) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(conteudo);
        alert.showAndWait();
    }

    public static void mostrarDialogoAviso(String titulo, String cabecalho, String conteudo) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(conteudo);
        alert.showAndWait();
    }

    public static void mostrarDialogoErro(String titulo, String cabecalho, String conteudo) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(conteudo);
        alert.showAndWait();
    }
    
    public static boolean mostrarDialogoOpcao(String titulo, String cabecalho, String conteudo){ 
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(conteudo);

        // Personalizando os botões
        ButtonType butConfirmar = new ButtonType("Confirmar");
        ButtonType buttonTypeCancel = new ButtonType("Cancelar");

        alert.getButtonTypes().setAll(butConfirmar, buttonTypeCancel);

        // Obtendo a resposta do usuário
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == butConfirmar) {
            return true;  // Usuário confirmou
        } else {
            return false;  // Usuário cancelou
        }
    }
    
    public static void mostrarDialogoSucesso(String titulo, String cabecalho, String conteudo) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle(titulo);
    alert.setHeaderText(cabecalho);
    alert.setContentText(conteudo);
    alert.showAndWait();
}

    public static void simOuNao(boolean sair) {
        if (sair){
        }
    }
    
 
}
