/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Carlos Zetti
 */
public class ComboItem {
    
    private String nome;
    private Boolean valor;

    public ComboItem(String nome, Boolean value) {
        this.nome = nome;
        this.valor = value;
    }

    @Override
    public String toString() {
        return nome.toUpperCase(); // Exibe o nome no ComboBox
    }

    public Boolean getValor() {
        return valor; // Retorna o valor booleano
    }

    public String getNome() {
        return nome;
    }

}
