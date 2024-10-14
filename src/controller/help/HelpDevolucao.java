/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.help;

import controller.MeuFxmlContrller;
import dao.EquipamentoDao;
import dao.RetiradaDao;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DetalheRetirada;
import model.Equipamento;
import model.Mensagem;
import model.Retirada;

/**
 *
 * @author Carlos Zetti
 */
public class HelpDevolucao {
    private MeuFxmlContrller controller;  // Atributo para acessar o controlador da interface

    public HelpDevolucao(MeuFxmlContrller controller) {
        this.controller = controller;
    }

    public HelpDevolucao() {
    }
    
    
    
    public void configurarTabela(){
        // Remover a coluna de índice se já existir
        controller.getTabelaRetiradas().getColumns().removeIf(col -> col.getText().equals("Item"));

        // Criando a coluna de índice com o nome "Item"
        TableColumn<DetalheRetirada, Integer> colunaItem = new TableColumn<>("Item");
        colunaItem.setCellValueFactory(cellData -> 
           new ReadOnlyObjectWrapper<>(controller.getTabelaRetiradas().getItems().indexOf(cellData.getValue()) + 1)
        );
        controller.getTabIdRetiradas().setCellValueFactory(new PropertyValueFactory<DetalheRetirada, Integer> ("idRetirada"));
        controller.getTabEntregueRetirada().setCellValueFactory(new PropertyValueFactory<DetalheRetirada, Boolean> ("entregue"));
        controller.getTabDiaRetirada().setCellValueFactory(new PropertyValueFactory<DetalheRetirada, String> ("diaRetirada"));
        controller.getTabEquipamentoRetirado().setCellValueFactory(new PropertyValueFactory<DetalheRetirada, String> ("nomeEquipamento"));
        controller.getTabelaNomePessRetirou().setCellValueFactory(new PropertyValueFactory<DetalheRetirada, String> ("nomePessoa"));
        controller.getTabQuantRetirada().setCellValueFactory(new PropertyValueFactory<DetalheRetirada, Integer> ("quantEquipamento"));
        
        // Adicionando a coluna "Item" no início da tabela
        controller.getTabelaRetiradas().getColumns().add(0, colunaItem);
    }
    
    public void PreencherTabela() throws SQLException{
        //Obtém a lista do BancoDao e cria uma ObservableList
        try{
            RetiradaDao dao = new RetiradaDao();
            ObservableList<DetalheRetirada> detalhes = FXCollections.observableArrayList(dao.ListaRetiradaDetalhes());
            controller.getTabelaRetiradas().setItems(detalhes);
        }catch(SQLException e){
            Mensagem.mostrarDialogoErro("", "", "Não foi possivel listar as retiradas!");
        }
        
    }
    
    public DetalheRetirada selecionarRetiradaClick() {
        int i = controller.getTabelaRetiradas().getSelectionModel().getSelectedIndex();  // Obtém o índice da linha selecionada
            if (i < 0) {
                return null; 
            }
        DetalheRetirada r = (DetalheRetirada)controller.getTabelaRetiradas().getItems().get(i); // Obtém o item da linha selecionada
        
        controller.getCampoIdRetirada().setText(String.valueOf(r.getIdRetirada()));
        controller.getCampoNomePessoaRetirada().setText(r.getNomePessoa());
        controller.getCampoIEquipamentoRetirada().setText(r.getNomeEquipamento());
        controller.getCampoQuantidadedev().setText(String.valueOf(r.getQuantEquipamento()));  
        return r;
    }
    
    public void entregar() throws SQLException{
        DetalheRetirada retiradaCampos = pegarCampo(); // Obtém os dados dos campos preenchidos na interface
        if(retiradaCampos == null){
            Mensagem.mostrarDialogoAviso("", "", "Selecione um intem da tabela para fazer a entrega!"); // Exibe aviso se os campos estiverem incorretos
        }else{
            RetiradaDao dao = new RetiradaDao(); // Cria instância do DAO para interação com o banco
            Retirada retiradaBanco = dao.retiradaId(retiradaCampos.getIdRetirada()); // Obtém a retirada do banco pelo ID
            int idRetirada = retiradaBanco.getIdRetirada(); // ID da retirada
            
            Integer idEquipamentoBanco = retiradaBanco.getIdEquipamento();  // Obtém o ID do equipamento
            Equipamento equiBanco = pegarEquipamento(idEquipamentoBanco);  // Busca o equipamento no banco
            
            // Se a retirada ainda não foi entregue
            if(!retiradaBanco.isEntregue()){
                 // Se a quantidade retirada e devolvida for igual
                if(retiradaBanco.getQuantidadeEquipamento() == retiradaCampos.getQuantEquipamento()){
                    Integer quantDispo = equiBanco.getQuantidadeDisponivel() + retiradaCampos.getQuantEquipamento(); // Atualiza a quantidade disponível no banco
                    EquipamentoDao.atualizarQuantDisponivel(equiBanco.getIdEquipamento(), quantDispo);
                    dao.deletarRetirada(idRetirada); // Marca a retirada como entregue
                    Mensagem.mostrarDialogoInformacao("", "", "Entrega Registrada!");
                    limparCampos();
                }else if (retiradaBanco.getQuantidadeEquipamento() < retiradaCampos.getQuantEquipamento()){  // Se a quantidade devolvida for maior do que a retirada
                    Mensagem.mostrarDialogoAviso("", "", "A Quantidade de equipamento passada é maior do que foi retirado!");
                } else{
                    Integer quantDispo = equiBanco.getQuantidadeDisponivel() + retiradaCampos.getQuantEquipamento();
                    EquipamentoDao.atualizarQuantDisponivel(equiBanco.getIdEquipamento(), quantDispo);// Atualiza a quantidade de equipamento no banco
                    Integer quantParaAtualizarRetira = retiradaBanco.getQuantidadeEquipamento() - retiradaCampos.getQuantEquipamento();
                    RetiradaDao.atualizarQuantidadeEquiRetirada(retiradaBanco.getIdRetirada(), quantParaAtualizarRetira);  // Atualiza a quantidade retirada no banco
                    Mensagem.mostrarDialogoInformacao("", "", "Somente "+ retiradaCampos.getQuantEquipamento() + " Equipamentos foram entregues!");
                    limparCampos();
                }
            } else{
                Mensagem.mostrarDialogoAviso("", "", "A retirada ja foi entregue!");
            }
           
            PreencherTabela();
        }    
    }
 
    public DetalheRetirada pegarCampo(){
        Integer idRetirada = HelpRetiradas.converterCampoNumero(controller.getCampoIdRetirada());
        String pessoaRetirada = controller.getCampoNomePessoaRetirada().getText();
        String equiRetirada = controller.getCampoIEquipamentoRetirada().getText();
        Integer quant = HelpRetiradas.converterCampoNumero(controller.getCampoQuantidadedev());
        
        boolean camposValidos = true;
        
        if (quant == null || quant <= 0) {
            controller.getCampoQuantidadedev().setStyle("-fx-border-color: red; -fx-background-color: #ffcccc;");
            camposValidos = false;
        } else {
            controller.getCampoQuantidadedev().setStyle(""); // Remove o estilo de erro
        }
        
        if(idRetirada == null){
            controller.getCampoIdRetirada().setStyle("-fx-border-color: red; -fx-background-color: #ffcccc;");
            camposValidos = false;
        }else {
            controller.getCampoIdRetirada().setStyle("");
        }
        
        if(pessoaRetirada== null || pessoaRetirada.trim().isEmpty()){
            controller.getCampoNomePessoaRetirada().setStyle("-fx-border-color: red; -fx-background-color: #ffcccc;");
            camposValidos = false;
        }else{
            controller.getCampoNomePessoaRetirada().setStyle("");
        }
        
        if(equiRetirada == null || equiRetirada.trim().isEmpty()){
            controller.getCampoIEquipamentoRetirada().setStyle("-fx-border-color: red; -fx-background-color: #ffcccc;");
            camposValidos = false;
        }else{
            controller.getCampoIEquipamentoRetirada().setStyle("");
        }
        
        if(camposValidos){
            return new DetalheRetirada(idRetirada, pessoaRetirada, equiRetirada, quant, "", false);
        }
        
        return null;
    }
    
    public Equipamento pegarEquipamento(Integer id) throws SQLException{
        EquipamentoDao dao = new EquipamentoDao();
        Equipamento equipamento = dao.pesquisarId(id);
        return equipamento;
    }
    
    private void limparCampos(){
        controller.getCampoIdRetirada().setText("");
        controller.getCampoNomePessoaRetirada().setText("");
        controller.getCampoIEquipamentoRetirada().setText("");
        controller.getCampoQuantidadedev().setText("");
    }
    
    public void limparPesquisaDev(){
        controller.getCampoPesquisaDevolucao().setText("");
    }
    
    public void pesquisarDev() {
        String nome = controller.getCampoPesquisaDevolucao().getText().toLowerCase();
        RetiradaDao dao = new RetiradaDao();
        try {
            ArrayList<DetalheRetirada> retiradaPesq = dao.pesquisarNome(nome);
            ObservableList<DetalheRetirada> retirada = FXCollections.observableArrayList(retiradaPesq);
            controller.getTabelaRetiradas().setItems(retirada);
        } catch (SQLException e) {
            
        }  
    }

}
