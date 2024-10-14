package controller;


import controller.help.HelpLogin;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;



public class LoginViewController implements Initializable {
    private HelpLogin help;
    

    public LoginViewController() {
    }

    
    @FXML
    public void close() {
        System.exit(0);
    }

    @FXML
    private TextField campoUsuario;

    @FXML
    private PasswordField compoSenha;

    @FXML
    void exit(ActionEvent event) {
       
    }
    
    @FXML
    public void fazerLogin(ActionEvent event) throws Exception {
        help.verificarUsuario(event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        help = new HelpLogin(this);
    }
    
   
    public TextField getCampoUsuario() {
        return campoUsuario;
    }

    public void setCampoUsuario(TextField campoUsuario) {
        this.campoUsuario = campoUsuario;
    }

    public PasswordField getCompoSenha() {
        return compoSenha;
    }

    public void setCompoSenha(PasswordField compoSenha) {
        this.compoSenha = compoSenha;
    }  

    public HelpLogin getHelp() {
        return help;
    }


   
    
    
}