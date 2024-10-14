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
import model.DetalhesRetiradasSemDevolucao;
import model.Mensagem;
import model.RetiradaSemDevolucao;

/**
 *
 * @author Carlos Zetti
 */
public class RetiradaSemDevDao {
    public boolean insertRetiradaSemDev(RetiradaSemDevolucao r){
        //inserir retirada no banco de dados
        try(Connection conexao = new Conexao().getConexao();
                // Prepara a instrução SQL para inserção
                PreparedStatement pstm = conexao.prepareStatement("insert into RetiradaSemDevolucao (idPessoa, idEquipamento, quantEquipamento, diaRetirada) values (?,?,?,?)")
                ){
            //definir paramentros
            pstm.setInt(1, r.getIdPessoa());
            pstm.setInt(2, r.getIdEquipamento());
            pstm.setInt(3, r.getQuantidadeEquipamento());
            pstm.setString(4, r.getDiaRetirada());
            
            pstm.executeUpdate();
            return true;
            
        }catch(Exception e){
            Mensagem.mostrarDialogoErro("", "", "Erro, não foi possível conectar com o banco para Registrar!");
             return true;
        }
    }
    
    
    public static ArrayList listaRetiradasSemDev() throws SQLException {
        ObservableList<RetiradaSemDevolucao> listaRetirada = FXCollections.observableArrayList();
        ArrayList<RetiradaSemDevolucao> retiradaArrayList = new ArrayList<>(listaRetirada);
        try (Connection conexao = new Conexao().getConexao();
            PreparedStatement pstmt = conexao.prepareStatement("SELECT * FROM RetiradaSemDevolucao");
            ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                
                Integer ideEntrega = rs.getInt("idEntrega");
                Integer idPessoa = rs.getInt("idPessoa");
                Integer idEquipamento = rs.getInt("idEquipamento");
                Integer quantEquipamento = rs.getInt("quantEquipamento");
                String diaRetirada = rs.getString("diaRetirada");

                RetiradaSemDevolucao r = new RetiradaSemDevolucao(ideEntrega, idPessoa, idEquipamento, quantEquipamento, diaRetirada);
                retiradaArrayList.add(r);
            }
        } catch (SQLException e) {
            Mensagem.mostrarDialogoErro("", "", "Não foi possivel conectar com o banco de dados!");  
        }
        return retiradaArrayList;  
    }
    
    
    public static void deletarRetirada(Integer idEntrega) {
       String sql = "DELETE FROM RetiradaSemDevolucao WHERE idEntrega = ?";
    
        try (Connection conexao = new Conexao().getConexao();
            PreparedStatement pstm = conexao.prepareStatement(sql)) {
            pstm.setInt(1, idEntrega);
            pstm.executeUpdate();
        } catch (SQLException e) {
        }
    } 
    
    
     public static ArrayList listaRetiradasSemDevDetalhes() throws SQLException {
        ObservableList<DetalhesRetiradasSemDevolucao> listaRetirada = FXCollections.observableArrayList();
        ArrayList<DetalhesRetiradasSemDevolucao> retiradaArrayList = new ArrayList<>(listaRetirada);
        try (Connection conexao = new Conexao().getConexao();
            PreparedStatement pstmt = conexao.prepareStatement("SELECT * FROM RetiradaSemDevDetalhes");
            ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                
                Integer ideEntrega = rs.getInt("idEntrega");
                String pessoa = rs.getString("nomePessoa");
                String equipamento = rs.getString("nomeEquipamento");
                Integer quantEquipamento = rs.getInt("quantEquipamento");
                String diaRetirada = rs.getString("diaRetirada");

                DetalhesRetiradasSemDevolucao r = new DetalhesRetiradasSemDevolucao(ideEntrega, pessoa, equipamento, quantEquipamento, diaRetirada);
                retiradaArrayList.add(r);
            }
        } catch (SQLException e) {
            Mensagem.mostrarDialogoErro("", "", "Não foi possivel conectar com o banco de dados!");  
        }
        return retiradaArrayList;  
    }
     
     public ArrayList<DetalhesRetiradasSemDevolucao> pesquisarNomeEqui (String nome) throws SQLException {
        ArrayList<DetalhesRetiradasSemDevolucao> retiradaArrayList = new ArrayList<>();
        String sql = "SELECT * FROM RetiradasemDevDetalhes WHERE LOWER(nomeEquipamento) LIKE LOWER(?)"; // Ignora caso

        try (Connection conexao = new Conexao().getConexao();
             PreparedStatement pstm = conexao.prepareStatement(sql)) {
        
            pstm.setString(1, "%" + nome + "%");
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Integer ideEntrega = rs.getInt("idEntrega");
                String pessoa = rs.getString("nomePessoa");
                String equipamento = rs.getString("nomeEquipamento");
                Integer quantEquipamento = rs.getInt("quantEquipamento");
                String diaRetirada = rs.getString("diaRetirada");

                DetalhesRetiradasSemDevolucao r = new DetalhesRetiradasSemDevolucao(ideEntrega, pessoa, equipamento, quantEquipamento, diaRetirada);
                retiradaArrayList.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Registra a exceção para depuração
            Mensagem.mostrarDialogoErro("", "", "Não foi possível conectar com o banco de dados: " + e.getMessage());
        }
        return retiradaArrayList;
    } 
    
}
