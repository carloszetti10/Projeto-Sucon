/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Mensagem;
import model.Pessoa;



/**
 *
 * @author Carlos Zetti
 */
public class PessoaDao {
    
 //inserir Pessoas
    public void insert(Pessoa pessoa) throws SQLException {
        // Obtém a conexão do banco de dados
        try (Connection conexao = new Conexao().getConexao();
             // Prepara a instrução SQL para inserção
             PreparedStatement pstmt = conexao.prepareStatement(
                "INSERT INTO Pessoa (nomePessoa, setorPessoa, numeroPessoa) VALUES (?, ?, ?)"
             )) {
            // Define os parâmetros da instrução SQL
            pstmt.setString(1, pessoa.getNomePessoa());
            pstmt.setString(2, pessoa.getSetorPessoa());
            pstmt.setString(3, pessoa.getNumeroPessoa());
    
            // Executa a instrução SQL
            pstmt.executeUpdate();
            Mensagem.mostrarDialogoInformacao("", "", "Pessoa Cadastrada!");
        } catch (SQLException e) {
             // Trata possíveis exceções de SQL
            if (e.getSQLState().equals("23505")) { // 23505 é o código de erro para violação de chave única no PostgreSQL
                   Mensagem.mostrarDialogoErro("", "", "Erro: O nome '" + pessoa.getNomePessoa() + "' já está cadastrado.");
            } else {
                Mensagem.mostrarDialogoErro("", "", "Erro, não foi possível conectar com o banco para cadastro!");
            }
        }
    }
    
    
    
    
    public ArrayList listarPessoas() throws SQLException {
        ObservableList<Pessoa> listaPessoa = FXCollections.observableArrayList();
        ArrayList<Pessoa> pessoaArrayList = new ArrayList<>(listaPessoa);
        try (Connection conexao = new Conexao().getConexao();
            PreparedStatement pstmt = conexao.prepareStatement("SELECT * FROM Pessoa");
            ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                
                Integer idPessoa = rs.getInt("idPessoa");
                String nomePessoa = rs.getString("nomePessoa");
                String setorPessoa = rs.getString("setorPessoa");
                String numeroPessoa = rs.getString("numeroPessoa");
                

                Pessoa p = new Pessoa(idPessoa, nomePessoa, setorPessoa, numeroPessoa); 
                pessoaArrayList.add(p);
            }
        } catch (SQLException e) {
            Mensagem.mostrarDialogoErro("", "", "Não foi possivel acessar o banco de dados!");
        }
        return pessoaArrayList;  
    }
    
    
    
    
    public ArrayList<Pessoa> pesquisarNome(String nomeDado) throws SQLException {
        ObservableList<Pessoa> listaPessoa = FXCollections.observableArrayList();
        ArrayList<Pessoa> pessoasArrayList = new ArrayList<>(listaPessoa);
        String sql = "SELECT * FROM Pessoa WHERE LOWER(nomePessoa) like LOWER(?)";
        try (Connection conexao = new Conexao().getConexao();
            PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, "%"+nomeDado+"%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Integer idPessoa = rs.getInt("idPessoa");
                    String nomePessoa = rs.getString("nomePessoa");
                    String setorPessoa = rs.getString("setorPessoa");
                    String numeroPessoa = rs.getString("numeroPessoa");
                    Pessoa p = new Pessoa(idPessoa, nomePessoa, setorPessoa, numeroPessoa); 
                    pessoasArrayList.add(p);
                }  
            }
        } catch (SQLException e) {
            Mensagem.mostrarDialogoErro("", "", "Erro ao Pesquisar!");
            throw e; // Relança a exceção para tratamento em outro lugar, se necessário
        }
        return pessoasArrayList;
    } 
    
    
    
    
    
     public static List nomesPessoas() throws SQLException {
        ObservableList<String> listaPessoaNomes = FXCollections.observableArrayList();
        List<String> pessoaList = new ArrayList<>(listaPessoaNomes);
        try (Connection conexao = new Conexao().getConexao();
            PreparedStatement pstmt = conexao.prepareStatement("SELECT nomePessoa FROM Pessoa");
            ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String nomePessoa = rs.getString("nomePessoa");
                pessoaList.add(nomePessoa);
            }
        } catch (SQLException e) {
            Mensagem.mostrarDialogoErro("", "", "Não foi possivel acessar o banco de dados!");
        }
        return pessoaList;  
    }
}
