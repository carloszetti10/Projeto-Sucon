/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

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
public class DetalhesRetiradasSemDevolucao {
    private IntegerProperty idRetirada;
    private StringProperty nomePessoa;
    private StringProperty nomeEquipamento;
    private IntegerProperty quantEquipamento;
    private StringProperty diaRetirada;


    public DetalhesRetiradasSemDevolucao(Integer idEntrega, String nomePessoa, String nomeEquipamento, Integer quantEquipamento, String diaRetirada) {
        this.idRetirada = new SimpleIntegerProperty(idEntrega);
        this.nomePessoa = new SimpleStringProperty(nomePessoa);
        this.nomeEquipamento = new SimpleStringProperty(nomeEquipamento);
        this.quantEquipamento = new SimpleIntegerProperty(quantEquipamento);
        this.diaRetirada = new SimpleStringProperty(diaRetirada);
    }
    
    public Integer getIdRetirada() {
        return idRetirada.get();
    }

    public String getNomePessoa() {
        return nomePessoa.get();
    }

    public String getNomeEquipamento() {
        return nomeEquipamento.get();
    }

    public Integer getQuantEquipamento() {
        return quantEquipamento.get();
    }

    public String getDiaRetirada() {
        return diaRetirada.get();
    }

    @Override
    public String toString() {
        return "DetalhesRetiradasSemDevolucao{" + "idRetirada=" + idRetirada + ", nomePessoa=" + nomePessoa + ", nomeEquipamento=" + nomeEquipamento + ", quantEquipamento=" + quantEquipamento + ", diaRetirada=" + diaRetirada + '}';
    }

   
    
    
        
}
