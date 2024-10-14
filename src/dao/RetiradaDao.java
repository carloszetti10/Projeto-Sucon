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
import model.DetalheRetirada;
import model.Mensagem;
import model.Retirada;


/**
 *
 * @author Carlos Zetti
 */
public class RetiradaDao {

    public static void atualizarQuantidadeEquiRetirada(Integer idRetirada, Integer quantAtualizar)  {
        String sql = "UPDATE Retirada SET quantEquipamento  = ? WHERE idRetirada = ?";
    
        try (Connection conexao = new Conexao().getConexao();
            PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            
            pstmt.setInt(1, quantAtualizar);
            pstmt.setInt(2, idRetirada); // Define o valor do parâmetro
            int linhasAfetadas = pstmt.executeUpdate(); // Executa a atualização

            
        } catch (SQLException e) {
            System.err.println("erro O Atualizar Retirada "+e);
        }
    }
    
    public boolean registrarRetirada(Retirada r){
        //inserir retirada no banco de dados
        try(Connection conexao = new Conexao().getConexao();
                // Prepara a instrução SQL para inserção
                PreparedStatement pstm = conexao.prepareStatement("insert into Retirada (idPessoa, idEquipamento, quantEquipamento, diaRetirada, entregue) values (?,?,?,?,?)")
                ){
            //definir paramentros
            pstm.setInt(1, r.getIdPessoa());
            pstm.setInt(2, r.getIdEquipamento());
            pstm.setInt(3, r.getQuantidadeEquipamento());
            pstm.setString(4, r.getDiaRetirada());
            pstm.setBoolean(5, r.isEntregue());
            
            pstm.executeUpdate();
            return true;
        }catch(Exception e){
            Mensagem.mostrarDialogoErro("", "", "Erro, não foi possível conectar com o banco para cadastro!");
            return false;
        }
    }
    
    
    
       //listar Retirada
    public static ArrayList listarRetiradas() throws SQLException {
        ObservableList<Retirada> listaRetirada = FXCollections.observableArrayList();
        ArrayList<Retirada> retiradaArrayList = new ArrayList<>(listaRetirada);
        try (Connection conexao = new Conexao().getConexao();
            PreparedStatement pstmt = conexao.prepareStatement("SELECT * FROM Retirada");
            ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                
                Integer idRetirada = rs.getInt("idRetirada");
                Integer idPessoa = rs.getInt("idPessoa");
                Integer idEquipamento = rs.getInt("idEquipamento");
                Integer quantEquipamento = rs.getInt("quantEquipamento");
                String diaRetirada = rs.getString("diaRetirada");
                boolean entregue = rs.getBoolean("entregue");


                Retirada r = new Retirada(idRetirada, idPessoa, idEquipamento, quantEquipamento, diaRetirada, entregue);
                retiradaArrayList.add(r);
            }
        } catch (SQLException e) {
            Mensagem.mostrarDialogoErro("", "", "Não foi possivel conectar com o banco de dados!");  
        }
        return retiradaArrayList;  
    }
    
    public ArrayList ListaRetiradaDetalhes() throws SQLException {
        ObservableList<DetalheRetirada> listaRetiradaDetalhes = FXCollections.observableArrayList();
        ArrayList<DetalheRetirada> retiradaArrayList = new ArrayList<>(listaRetiradaDetalhes);
        try (Connection conexao = new Conexao().getConexao();
            PreparedStatement pstmt = conexao.prepareStatement("SELECT * FROM RetiradaDetalhes");
            ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Integer idRetirada = rs.getInt("idRetirada");
                String nomePessoa = rs.getString("nomePessoa");
                String nomeEquipamento = rs.getString("nomeEquipamento");
                Integer quantEquipamento = rs.getInt("quantEquipamento");
                String diaRetirada = rs.getString("diaRetirada");
                boolean entregue = rs.getBoolean("entregue");

                if(entregue == false){
                    DetalheRetirada detalheRetirada = new DetalheRetirada(idRetirada, nomePessoa, nomeEquipamento, quantEquipamento, diaRetirada, entregue);
                    retiradaArrayList.add(detalheRetirada);
                }
                // Cria um novo objeto DetalheRetirada com os dados retornados
            }
        } catch (SQLException e) {
            Mensagem.mostrarDialogoErro("", "", "Não foi possivel conectar com o banco de dados!");
        }
        return  retiradaArrayList;  
   }
    
    
    
    public Retirada retiradaId(Integer id) throws SQLException {
        Retirada retirada = null; // Inicializa a variável

        try (Connection conexao = new Conexao().getConexao();
             PreparedStatement pstmt = conexao.prepareStatement("SELECT * FROM Retirada WHERE idRetirada = ?")) {
        
            pstmt.setInt(1, id); // Define o valor do parâmetro

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) { //i` para obter a primeira linha, se existir
                
                    Integer idRetirada = rs.getInt("idRetirada");
                    Integer idPessoa = rs.getInt("idPessoa");
                    Integer idEquipamento = rs.getInt("idEquipamento");
                    String diaRetirada = rs.getString("diaRetirada");
                    Integer quantEquipamento = rs.getInt("quantEquipamento");
                    boolean entregue = rs.getBoolean("entregue");

                    retirada = new Retirada(idRetirada, idPessoa, idEquipamento, quantEquipamento, diaRetirada, entregue);
                }
            }
        } catch (SQLException e) {
            Mensagem.mostrarDialogoErro("", "", "Não foi possível conectar com o banco de dados!!");
        }

        return retirada; // Retorna a retirada ou null se não encontrada
    }
    
    
    public void deletarRetirada(Integer idRetirada) {
       String sql = "DELETE FROM Retirada WHERE idRetirada = ?";
    
        try (Connection conexao = new Conexao().getConexao();
             PreparedStatement pstm = conexao.prepareStatement(sql)) {
         
            pstm.setInt(1, idRetirada);
            pstm.executeUpdate();
        } catch (SQLException e) {
        }
    }  

    public ArrayList<DetalheRetirada> pesquisarNome(String nome) throws SQLException {
        ArrayList<DetalheRetirada> retiradaArrayList = new ArrayList<>();
        String sql = "SELECT * FROM RetiradaDetalhes WHERE LOWER(nomePessoa) LIKE LOWER(?)"; // Ignora caso

        try (Connection conexao = new Conexao().getConexao();
             PreparedStatement pstm = conexao.prepareStatement(sql)) {
        
            pstm.setString(1, "%" + nome + "%");
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Integer idRetirada = rs.getInt("idRetirada");
                String nomePessoa = rs.getString("nomePessoa");
                String nomeEquipamento = rs.getString("nomeEquipamento");
                Integer quantEquipamento = rs.getInt("quantEquipamento");
                String diaRetirada = rs.getString("diaRetirada");
                boolean entregue = rs.getBoolean("entregue");

                if (!entregue) {
                    DetalheRetirada detalheRetirada = new DetalheRetirada(idRetirada, nomePessoa, nomeEquipamento, quantEquipamento, diaRetirada, entregue);
                    retiradaArrayList.add(detalheRetirada);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Registra a exceção para depuração
            Mensagem.mostrarDialogoErro("", "", "Não foi possível conectar com o banco de dados: " + e.getMessage());
        }
        return retiradaArrayList;
    } 
    



}
