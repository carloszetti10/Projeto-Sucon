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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Equipamento;
import model.Mensagem;
/**
 *
 * @author Carlos Zetti
 */
public class EquipamentoDao {

    public static void atualizarEquipamento(Integer idCampoEditar, Equipamento novoEqui) {
         // Obtém a conexão do banco de dados
        try (Connection conexao = new Conexao().getConexao();
             // Prepara a instrução SQL para atualização
             PreparedStatement pstmt = conexao.prepareStatement(
                "UPDATE Equipamento SET nomeEquipamento = ?, quantidade = ?, quantidadeDisponivel = ?, comEmprestimo = ? WHERE idEquipamento = ?"
            )) {
            // Define os parâmetros da instrução SQL
            pstmt.setString(1, novoEqui.getNomeEquipamento());
            pstmt.setInt(2, novoEqui.getQuantidade());
            pstmt.setInt(3, novoEqui.getQuantidadeDisponivel());
            pstmt.setBoolean(4, novoEqui.isComEmprestimo());
            pstmt.setInt(5, idCampoEditar); // Define o ID do equipamento a ser atualizado

            // Executa a instrução SQL
           pstmt.executeUpdate();
           Mensagem.mostrarDialogoInformacao("", "", "Equipamento editado com sucesso!");
        
        } catch (SQLException e) {
            
            if (e.getSQLState().equals("23505")) { // 23505 é o código de erro para violação de chave única no PostgreSQL
               Mensagem.mostrarDialogoErro("", "", "Erro: O nome '" + novoEqui.getNomeEquipamento() + "' já está cadastrado.");
            } else {
                Mensagem.mostrarDialogoErro("", "", "Erro, não foi possível Atualizar o equipamento!");
            }
        }
    }
    
    //inserir equipamento
    public void insert(Equipamento equipamento) throws SQLException {
    // Obtém a conexão do banco de dados
    try (Connection conexao = new Conexao().getConexao();
         // Prepara a instrução SQL para inserção
         PreparedStatement pstmt = conexao.prepareStatement(
            "INSERT INTO Equipamento (nomeEquipamento, quantidade, quantidadeDisponivel, comEmprestimo ) VALUES (?, ?, ?, ?)"
         )) {
        // Define os parâmetros da instrução SQL
        pstmt.setString(1, equipamento.getNomeEquipamento());
        pstmt.setInt(2, equipamento.getQuantidade());
        pstmt.setInt(3, equipamento.getQuantidadeDisponivel());
        pstmt.setBoolean(4, equipamento.isComEmprestimo());
    
        // Executa a instrução SQL
         pstmt.executeUpdate();
         Mensagem.mostrarDialogoInformacao("", "", "Equipamento Cadastrado!");
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) { // 23505 é o código de erro para violação de chave única no PostgreSQL
                Mensagem.mostrarDialogoErro("", "", "Erro: O nome '" + equipamento.getNomeEquipamento() + "' já está cadastrado.");
            } else {
                Mensagem.mostrarDialogoErro("", "", "Erro, não foi póssivel conectar com o banco para cadastro! ");
            }
        } 
    }
    
       //listar equipamentos
    public ArrayList listarTodos() throws SQLException {
            ObservableList<Equipamento> listaEquipamentos = FXCollections.observableArrayList();
            ArrayList<Equipamento> equipamentosArrayList = new ArrayList<>(listaEquipamentos);
            try (Connection conexao = new Conexao().getConexao();
                 PreparedStatement pstmt = conexao.prepareStatement("SELECT * FROM Equipamento");
                 ResultSet rs = pstmt.executeQuery()) {
            
                while (rs.next()) {
                
                    Integer id = rs.getInt("idEquipamento");
                    String nome = rs.getString("nomeEquipamento");
                    Integer quant = rs.getInt("quantidade");
                    Integer quantDisponivel = rs.getInt("quantidadeDisponivel");
                    Boolean comEmprestimo = rs.getBoolean("comEmprestimo");
                

                    Equipamento equipamento = new Equipamento(id, nome, quant, quantDisponivel,comEmprestimo);
                    equipamentosArrayList.add(equipamento);
                }
        } catch (SQLException e) {
            Mensagem.mostrarDialogoErro("", "", "Não foi possivel acessar o banco de dados!");
        }
        return equipamentosArrayList;   
    }
       
       
       
       
        public ArrayList<Equipamento> pesquisarNome(String nomeDado) throws SQLException {
            ObservableList<Equipamento> listaEquipamentos = FXCollections.observableArrayList();
            ArrayList<Equipamento> equipamentosArrayList = new ArrayList<>(listaEquipamentos);
            String sql = "SELECT * FROM Equipamento WHERE LOWER(nomeEquipamento) like LOWER(?)";
            try (Connection conexao = new Conexao().getConexao();
               PreparedStatement pstmt = conexao.prepareStatement(sql)) {
               pstmt.setString(1, "%" +nomeDado+"%");
                try (ResultSet rs = pstmt.executeQuery()) {
                     while (rs.next()) {
                                         
                    Integer id = rs.getInt("idEquipamento");
                    String nome = rs.getString("nomeEquipamento");
                    Integer quant = rs.getInt("quantidade");
                    Integer quantDisponivel = rs.getInt("quantidadeDisponivel");
                    Boolean comEmprestimo = rs.getBoolean("comEmprestimo");
                
                    Equipamento equipamento = new Equipamento(id, nome, quant, quantDisponivel,comEmprestimo);
                    equipamentosArrayList.add(equipamento);
                    }  
                }
            } catch (SQLException e) {
                Mensagem.mostrarDialogoErro("", "", "Erro ao Pesquisar!");
                System.out.println("Erro ao pesquisar equipamento: " + e.getMessage());
                throw e; // Relança a exceção para tratamento em outro lugar, se necessário
            }
            return equipamentosArrayList;
        }
        
        
    public static void atualizarQuantDisponivel(int id, int disponivel){
        try (Connection conexao = new Conexao().getConexao();
         // Prepara a instrução SQL para inserção
         PreparedStatement pstmt = conexao.prepareStatement(
            "UPDATE Equipamento SET quantidadeDisponivel = ? WHERE idEquipamento = ?"
         )) {
            
            pstmt.setInt(1, disponivel); // Atualiza a quantidadeDisponivel
            pstmt.setInt(2, id);  // Atualiza o equipamento com o id correspondente

            // Executa a atualização
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // Trata possíveis exceções de SQL
            Mensagem.mostrarDialogoErro("", "", "Erro, não foi possivel conectar com o banco!");
        } 
        
    }
    
    
    
    public Equipamento pesquisarId(Integer idDado) throws SQLException {
        String sql = "SELECT * FROM Equipamento WHERE idEquipamento = ?";
    
        try (Connection conexao = new Conexao().getConexao();
            PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            // Define o parâmetro da consulta SQL
            pstmt.setInt(1, idDado);
            // Executa a consulta
            try (ResultSet rs = pstmt.executeQuery()) {
                // Se houver resultado, cria o objeto Equipamento
                if (rs.next()) {
                    Integer id = rs.getInt("idEquipamento");
                    String nome = rs.getString("nomeEquipamento");
                    Integer quant = rs.getInt("quantidade");
                    Integer quantDisponivel = rs.getInt("quantidadeDisponivel");
                     Boolean comEmprestimo = rs.getBoolean("comEmprestimo");

                    // Cria e retorna o objeto Equipamento com os dados do ResultSet
                    return new Equipamento(id, nome, quant, quantDisponivel, comEmprestimo);
                }
            }
        } catch (SQLException e) {
           // Log da exceção para rastreamento
            e.printStackTrace();
            throw e;  // Repassa a exceção para tratamento externo
        }
    
        // Retorna null caso não encontre o equipamento
        return null;
    }
    
    
    public static void atualizarQuantEntrega(int id, int disponivel){
        try (Connection conexao = new Conexao().getConexao();
         // Prepara a instrução SQL para inserção
         PreparedStatement pstmt = conexao.prepareStatement(
            "UPDATE Equipamento SET quantidadeDisponivel = ?, quantidade = ? WHERE idEquipamento = ?"
         )) {
            
            pstmt.setInt(1, disponivel); // Atualiza a quantidadeDisponivel
            pstmt.setInt(2, disponivel);
            pstmt.setInt(3, id);  // Atualiza o equipamento com o id correspondente

            // Executa a atualização
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Não atualizei o equi"+ e);
            // Trata possíveis exceções de SQL
            Mensagem.mostrarDialogoErro("", "", "Erro, não foi possivel conectar com o banco!");
        } 
        
    }
    
    public static void deletarEquipamento(Integer idDado) throws SQLException {
        String sql = "DELETE FROM Equipamento WHERE idEquipamento = ?";

        try (Connection conexao = new Conexao().getConexao();
            PreparedStatement pstmt = conexao.prepareStatement(sql)) {
        
           // Define o parâmetro da consulta SQL
           pstmt.setInt(1, idDado);
        
           // Executa a atualização (DELETE)
           pstmt.executeUpdate();
           Mensagem.mostrarDialogoInformacao("", "", "Equipamento deletado!");
        } catch (SQLException e) {
             Mensagem.mostrarDialogoErro("", "", "Não foi possível deletar o Equipamento!");
          
        }
    }   
}
