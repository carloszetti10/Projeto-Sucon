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
public class DetalheRetirada extends DetalhesRetiradasSemDevolucao{
    private BooleanProperty entregue;

    public DetalheRetirada(Integer idEntrega, String nomePessoa, String nomeEquipamento, Integer quantEquipamento, String diaRetirada, Boolean entregue) {
        super(idEntrega, nomePessoa, nomeEquipamento, quantEquipamento, diaRetirada);
        this.entregue = new SimpleBooleanProperty(entregue);
    }
    
    public String isEntregue() {
         if(entregue.get() == true){
             return "Sim";
         } else {
            return "Não";
         }
    }
        
}
