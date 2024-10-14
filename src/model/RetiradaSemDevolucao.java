/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author devmat
 */
public class RetiradaSemDevolucao {
    
    private IntegerProperty idRetirada;
    private IntegerProperty idPessoa; 
    private IntegerProperty idEquipamento; 
    private IntegerProperty quantidadeEquipamento;
    private StringProperty diaRetirada;

    // Construtor
    public RetiradaSemDevolucao(int idPessoa, int idEquipamento, int quantidadeEquipamento) {
        this.idRetirada = new SimpleIntegerProperty();
        this.idPessoa = new SimpleIntegerProperty(idPessoa);
        this.idEquipamento = new SimpleIntegerProperty(idEquipamento);
        this.quantidadeEquipamento = new SimpleIntegerProperty(quantidadeEquipamento);
        LocalDate dataAtual = LocalDate.now();
        this.diaRetirada = new SimpleStringProperty(dataAtual.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }
    
    
     public RetiradaSemDevolucao(int idRetirada, int idPessoa, int idEquipamento, int quantidadeEquipamento, String diaRetirada) {
        this.idRetirada = new SimpleIntegerProperty(idRetirada);
        this.idPessoa = new SimpleIntegerProperty(idPessoa);
        this.idEquipamento = new SimpleIntegerProperty(idEquipamento);
        this.quantidadeEquipamento = new SimpleIntegerProperty(quantidadeEquipamento);
        this.diaRetirada = new SimpleStringProperty(diaRetirada);
    }
     

    // Getters e Setters
    public int getIdRetirada() {
        return idRetirada.get();
    }

    public void setIdEntrega(int id) {
        this.idRetirada.set(id);
    }

    public int getIdPessoa() {
        return idPessoa.get();
    }

    public void setIdPessoa(int idPessoa) {
        this.idPessoa.set(idPessoa);
    }

    public int getIdEquipamento() {
        return idEquipamento.get();
    }

    public void setIdEquipamento(int idEquipamento) {
        this.idEquipamento.set(idEquipamento);
    }

    public int getQuantidadeEquipamento() {
        return quantidadeEquipamento.get();
    }

    public void setQuantidadeEquipamento(int quantidadeEquipamento) {
        this.quantidadeEquipamento.set(quantidadeEquipamento);
    }

    public String getDiaRetirada() {
        return diaRetirada.get();
    }

    public void setDiaRetirada(String dia) {
        this.diaRetirada.set(dia);
    }

    @Override
    public String toString() {
        return "RetiradaSemDevolucao{" + "idRetirada=" + idRetirada + ", idPessoa=" + idPessoa + ", idEquipamento=" + idEquipamento + ", quantidadeEquipamento=" + quantidadeEquipamento + ", diaRetirada=" + diaRetirada + '}';
    }

    
    

   
    
    
    
}
