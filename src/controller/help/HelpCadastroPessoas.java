/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.help;

import controller.MeuFxmlContrller;
import dao.PessoaDao;
import dao.RetiradaDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DetalheRetirada;
import model.Mensagem;
import model.Pessoa;

/**
 *
 * @author Carlos Zetti
 */
public class HelpCadastroPessoas {
    private MeuFxmlContrller controller;

    public HelpCadastroPessoas(MeuFxmlContrller controller) {
        this.controller = controller;
    }

    public HelpCadastroPessoas() {
    }
    PessoaDao dao = new PessoaDao();
    //cadastrar 
    public void cadastrarPessoa(ActionEvent event) throws SQLException{
        if (pegarCamposDoCadPessoa() == null){
            Mensagem.mostrarDialogoAviso("Campos obrigatórios", "", "Preencha todos os campos obrigatórios!");
        }else{
          
            dao.insert(pegarCamposDoCadPessoa());
            preencherTabelaPessoa();
            limparCampos();
            limpaPesqui();
        }
    }
    
    
    public Pessoa pegarCamposDoCadPessoa() {
        String nome = controller.getCampoNomePessoa().getText();
        String setor = controller.getCampoSetor().getText();
        String tel = controller.getCampoTelefone().getText();

        boolean camposValidos = true;
        // Verifica o campo "nome"
        if (nome == null || nome.trim().isEmpty() || nome.length()<=4) {
            controller.getCampoNomePessoa().setStyle("-fx-border-color: red; -fx-background-color: #ffcccc;");
            camposValidos = false;
        } else {
           controller.getCampoNomePessoa().setStyle("");  // Remove o estilo customizado e volta ao padrão
        }
        // Verifica o campo "setor"
        if (setor == null || setor.trim().isEmpty()) {
            controller.getCampoSetor().setStyle("-fx-border-color: red; -fx-background-color: #ffcccc;");
            camposValidos = false;
        } else {
            controller.getCampoSetor().setStyle("");  // Remove o estilo customizado e volta ao padrão
        }
       // Se algum campo estiver inválido, retorna null
        if (!camposValidos) {
            return null;
        }
        // Todos os campos estão preenchidos corretamente, retorna uma nova instância de Pessoa
        return new Pessoa(nome, setor, tel);
    }
    
    
    public void limparCampos(){
        controller.getCampoNomePessoa().setText("");
        controller.getCampoSetor().setText("");
        controller.getCampoTelefone().setText("");
    }
    
    //mechendo com a tabela pessoas
    public void configurarTabelaPessoa(){
        // Remover a coluna de índice se já existir
        controller.getTabelaPessoas().getColumns().removeIf(col -> col.getText().equals("Item"));

        // Criando a coluna de índice com o nome "Item"
        TableColumn<Pessoa, Integer> colunaItem = new TableColumn<>("Item");
        colunaItem.setCellValueFactory(cellData -> 
           new ReadOnlyObjectWrapper<>(controller.getTabelaPessoas().getItems().indexOf(cellData.getValue()) + 1)
        );
        
        controller.getNomePessoaTab().setCellValueFactory(new PropertyValueFactory<Pessoa,String>("nomePessoa"));
        controller.getSetoPessoaTab().setCellValueFactory(new PropertyValueFactory<Pessoa,String>("setorPessoa"));
        controller.getTelefonePessoaTab().setCellValueFactory(new PropertyValueFactory<Pessoa,String>("numeroPessoa"));
        
        // Adicionando a coluna "Item" no início da tabela
        controller.getTabelaPessoas().getColumns().add(0, colunaItem);
    }
    
    public void preencherTabelaPessoa(){
        // Obtém a lista de Pessoa do BancoDao e cria uma ObservableList
        ObservableList<Pessoa> pessoas = FXCollections.observableArrayList(Pessoa.todasPessoas());
        controller.getTabelaPessoas().setItems(pessoas);
    }
    
    
    public void pesquisarNome() {
        String nome = controller.getCampoPesquisaPessoa().getText().toLowerCase();
        PessoaDao dao = new PessoaDao();
        try {
            ArrayList<Pessoa> pessoaPesquisado = dao.pesquisarNome(nome);
            ObservableList<Pessoa> pessoa = FXCollections.observableArrayList(pessoaPesquisado);
            controller.getTabelaPessoas().setItems(pessoa);
            
        } catch (SQLException ex) {
            Logger.getLogger(MeuFxmlContrller.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    public void limpaPesqui(){
        controller.getCampoPesquisaPessoa().setText("");
    }

}
