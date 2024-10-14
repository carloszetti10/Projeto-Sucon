/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import dao.PessoaDao;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 *
 * @author Carlos Zetti
 */
public class Pessoa {
    private IntegerProperty idPessoa; 
    private StringProperty nomePessoa;
    private StringProperty setorPessoa;
    private StringProperty numeroPessoa;

    public Pessoa(String nomePessoa, String setorPessoa, String numeroPessoa) {
        this.nomePessoa =  new SimpleStringProperty(nomePessoa);
        this.setorPessoa = new SimpleStringProperty (setorPessoa);
        this.numeroPessoa = new SimpleStringProperty (numeroPessoa);
    }

    public Pessoa(Integer idPessoa, String nomePessoa, String setorPessoa, String numeroPessoa) {
        this.idPessoa = new SimpleIntegerProperty(idPessoa);
        this.nomePessoa =  new SimpleStringProperty(nomePessoa);
        this.setorPessoa = new SimpleStringProperty (setorPessoa);
        this.numeroPessoa = new SimpleStringProperty (numeroPessoa);
    }
    
    
    // public Pessoa(String nomePessoa) {
      //  this.nomePessoa =  new SimpleStringProperty(nomePessoa); 
   // }

    public Pessoa() {
    }
   
    

    public Integer getIdPessoa() {
        return idPessoa.get();
    }

    public void setIdPessoa(Integer idPessoa) {
        this.idPessoa.set(idPessoa);
    }

    public String getNomePessoa() {
        return nomePessoa.get();
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa.set(nomePessoa);
    }

    public String getSetorPessoa() {
        return setorPessoa.get();
    }

    public void setSetorPessoa(String setorPessoa) {
        this.setorPessoa.set(setorPessoa);
    }

    public String getNumeroPessoa() {
        return numeroPessoa.get();
    }

    public void setNumeroPessoa(String numeroPessoa) {
        this.numeroPessoa.set(numeroPessoa);
    }

    @Override
    public String toString() {
        return this.nomePessoa.get().toUpperCase();
    }
    
    //
   // private static List<Pessoa> listaPessoa = new ArrayList<>();
    
    public static ArrayList<Pessoa> todasPessoas(){
        try{
            PessoaDao dao = new PessoaDao();
            ArrayList<Pessoa> lista = dao.listarPessoas() ;
            return lista; 
        } catch(Exception e){
            Mensagem.mostrarDialogoErro("", "", "erro ao listar Pessoas!");
            return null; 
        }   
    }
    
    public static Pessoa buscarPessoaPorNome(String nome) {
        // Aqui você deve ter lógica para buscar a Pessoa em uma lista ou banco de dados
       ArrayList<Pessoa> todasPessoas = todasPessoas();
       for (Pessoa p : todasPessoas) {
            if (p.getNomePessoa().equalsIgnoreCase(nome)){
                return p;
            }
        }
        return null; // Retorna null se não encontrar
    }
    

}
