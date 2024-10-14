/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.help;

import controller.MeuFxmlContrller;
import dao.RetiradaSemDevDao;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DetalheRetirada;
import model.DetalhesRetiradasSemDevolucao;

/**
 *
 * @author devmat
 */
public class HelpRetiradaSemDev {
    private MeuFxmlContrller controller;

    public HelpRetiradaSemDev(MeuFxmlContrller controller) {
        this.controller = controller;
    }
    
    public void configurarTabela(){
        
        // Remover a coluna de índice se já existir
        controller.getSemDevTabelaRetirada().getColumns().removeIf(col -> col.getText().equals("Item"));

        // Criando a coluna de índice com o nome "Item"
        TableColumn<DetalhesRetiradasSemDevolucao, Integer> colunaItem = new TableColumn<>("Item");
        colunaItem.setCellValueFactory(cellData -> 
           new ReadOnlyObjectWrapper<>(controller.getSemDevTabelaRetirada().getItems().indexOf(cellData.getValue()) + 1)
        );
        controller.getSemDevEqui().setCellValueFactory(new PropertyValueFactory<DetalhesRetiradasSemDevolucao, String>("nomeEquipamento"));
        controller.getSemDevPessoa().setCellValueFactory(new PropertyValueFactory<DetalhesRetiradasSemDevolucao, String>("nomePessoa"));
        controller.getSemDevDia().setCellValueFactory(new PropertyValueFactory<DetalhesRetiradasSemDevolucao, String>("diaRetirada"));
        controller.getSemDevQuant().setCellValueFactory(new PropertyValueFactory<DetalhesRetiradasSemDevolucao,Integer >("quantEquipamento"));
        controller.getSemDevId().setCellValueFactory(new PropertyValueFactory<DetalhesRetiradasSemDevolucao, Integer>("idRetirada"));
        
        // Adicionando a coluna "Item" no início da tabela
        controller.getSemDevTabelaRetirada().getColumns().add(0, colunaItem);
    }
    
    public void preencherTabela() throws SQLException{
        try{
            ArrayList lista = RetiradaSemDevDao.listaRetiradasSemDevDetalhes();
            ObservableList<DetalhesRetiradasSemDevolucao> detalhes = FXCollections.observableArrayList(lista);
            controller.getSemDevTabelaRetirada().setItems(detalhes);
            
        }catch(SQLException e){
            
        }
    }
    
    
    public void limparPesquisa(){
        controller.getSemDevCampoPesquisa().setText("");
    }
    
    public void pesquisarRet() {
        String nome = controller.getSemDevCampoPesquisa().getText().toLowerCase();
        RetiradaSemDevDao dao = new RetiradaSemDevDao();
        try {
            ArrayList<DetalhesRetiradasSemDevolucao> retiradaPesq = dao.pesquisarNomeEqui(nome);
            ObservableList<DetalhesRetiradasSemDevolucao> retirada = FXCollections.observableArrayList(retiradaPesq);
            controller.getSemDevTabelaRetirada().setItems(retirada);
        } catch (SQLException e) {
            
        }  
    }
    
}
