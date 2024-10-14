package model;


import dao.EquipamentoDao;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 *
 * @author Carlos Zetti
 */
public class Equipamento {

    
    private IntegerProperty idEquipamento;
    private StringProperty nomeEquipamento;
    private IntegerProperty quantidade;
    private IntegerProperty quantidadeDisponivel;
    private BooleanProperty comEmprestimo;
  

    // Construtor
    public Equipamento(Integer idEquipamento, String nomeEquipamento, Integer quantidade, Integer quantDisponivel, Boolean comEmprestimo) {
        this.idEquipamento = new SimpleIntegerProperty(idEquipamento);
        this.nomeEquipamento = new SimpleStringProperty(nomeEquipamento);
        this.quantidade = new SimpleIntegerProperty(quantidade);
        this.quantidadeDisponivel = new SimpleIntegerProperty(quantDisponivel);
        this.comEmprestimo = new SimpleBooleanProperty(comEmprestimo);
    }

    public Equipamento(String nomeEquipamento, Integer quantidade,Boolean podeEmprestar) {
        this.nomeEquipamento = new  SimpleStringProperty (nomeEquipamento);
        this.quantidade = new SimpleIntegerProperty (quantidade);
        this.quantidadeDisponivel = new SimpleIntegerProperty (quantidade);
        this.comEmprestimo = new SimpleBooleanProperty(podeEmprestar);
    }
    
    public Equipamento(String nomeEquipamento, Integer quantidade,Integer disponivel, Boolean podeEmprestar) {
        this.nomeEquipamento = new  SimpleStringProperty (nomeEquipamento);
        this.quantidade = new SimpleIntegerProperty (quantidade);
        this.quantidadeDisponivel = new SimpleIntegerProperty (disponivel);
        this.comEmprestimo = new SimpleBooleanProperty(podeEmprestar);
    }
    
   
    public Equipamento() {
    }
    
    
    
    // Getters e Setters
    public IntegerProperty idEquipamentoProperty() {
        return idEquipamento;
    }

    public Integer getIdEquipamento() {
        return idEquipamento.get();
    }

    public void setIdEquipamento(Integer idEquipamento) {
        this.idEquipamento.set(idEquipamento);
    }

    public StringProperty nomeEquipamentoProperty() {
        return nomeEquipamento;
    }

    public String getNomeEquipamento() {
        return nomeEquipamento.get();
    }

    public void setNomeEquipamento(String nomeEquipamento) {
        this.nomeEquipamento.set(nomeEquipamento);
    }

    public IntegerProperty quantidadeProperty() {
        return quantidade;
    }

    public int getQuantidade() {
        return quantidade.get();
    }

    public void setQuantidade(int quantidade) {
        this.quantidade.set(quantidade);
    }

    public Integer getQuantidadeDisponivel() {
        return quantidadeDisponivel.get();
    }

    public void setQuantidadeDisponivel(Integer quantidadeDisponivel) {
        this.quantidadeDisponivel.set(quantidadeDisponivel);
    }

    public String getComEmprestimo() {
        if(comEmprestimo.get()){
            return "Sim";
        }else{
            return "Não";
        }
    }
    
    public Boolean isComEmprestimo(){
        return comEmprestimo.get();
    }

    public void setComEmprestimo(Boolean comEmprestimo) {
        this.comEmprestimo.set(comEmprestimo);
    }
    
    

    @Override
    public String toString() {
        return this.nomeEquipamento.get().toUpperCase();
    }


    public static ArrayList<Equipamento> todosEquipamentos(){
        try{
            EquipamentoDao dao = new EquipamentoDao();
            ArrayList<Equipamento> lista = dao.listarTodos();
            return lista; 
        }catch(SQLException e){
            Mensagem.mostrarDialogoErro("", "", "Erro ao Listar Equipamentos!");
            return null;
        }
    }
    
    public static ArrayList<Equipamento> equipamentosEmprestar(){
        ArrayList<Equipamento> todosEquipamentos = todosEquipamentos();
        ArrayList<Equipamento> lista = new ArrayList<>();
        for(Equipamento e : todosEquipamentos){
            if(e.isComEmprestimo()){
                lista.add(e);
            }
        }
        return lista;
    }
    
    
        public static ArrayList<Equipamento> equipamentosRetirar(){
        ArrayList<Equipamento> todosEquipamentos = todosEquipamentos();
        ArrayList<Equipamento> lista = new ArrayList<>();
        for(Equipamento e : todosEquipamentos){
            if(!e.isComEmprestimo()){
                lista.add(e);
            }
        }
        return lista;
    }
    
    public static Equipamento buscarPorNome(String equipamentoNome) {
        ArrayList<Equipamento> todosEquipamentos = todosEquipamentos();
        for(Equipamento e : todosEquipamentos){
            if (e.getNomeEquipamento().equalsIgnoreCase(equipamentoNome)) {
                return e;  // Retorna o equipamento encontrado
            }
        }
        return null;
    }
    
    
 
    
  
}
    

