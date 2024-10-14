/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.help;

import dao.UsuarioDao;
import controller.LoginViewController;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Mensagem;
import model.Usuario;
import view.Main;

/**
 *
 * @author SUCOM
 */
public class HelpLogin {
    
    private LoginViewController controller;
   

    public HelpLogin(LoginViewController controller) {
        this.controller = controller;
    }

    public void verificarUsuario(ActionEvent event) {
        //pegar dados da view 
        String usuarioDado = controller.getCampoUsuario().getText();
        String senhaDada = controller.getCompoSenha().getText(); 
        //verivicar se tem no banco
        UsuarioDao dao = new UsuarioDao();
        Usuario usuarioBanco = dao.selectUsuario();
        System.out.println(usuarioDado);
        if (usuarioDado == null || senhaDada == null || usuarioDado.isEmpty() || senhaDada.isEmpty()){
            Mensagem.mostrarDialogoAviso("", "", "Preencha todos os campos!");
        } else if(usuarioDado.equals(usuarioBanco.getUsuario()) && senhaDada.equals(usuarioBanco.getSenha())){
            Main m = new Main();
            m.abrirMenu(event);
        } else{
            Mensagem.mostrarDialogoErro("", "", "Usuario inválido!");
        }
    } 
}
