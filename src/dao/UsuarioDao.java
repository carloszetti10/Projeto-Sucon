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
import model.Mensagem;
import model.Usuario;

/**
 *
 * @author SUCOM
 */
public class UsuarioDao {
    
    
    
    public Usuario selectUsuario() {
        String sql = "SELECT * FROM Usuario;"; // Limitando a seleção a um único usuário
        Usuario usuario = null; // Inicializando como nulo
    
        try (Connection conexao = new Conexao().getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
        
            if (rs.next()) { // Verificando se existe pelo menos um resultado
                String usuarioNome = rs.getString("usuario"); // Renomeado para evitar conflito
                String senha = rs.getString("senha");
            
                usuario = new Usuario(usuarioNome, senha); // Criando o objeto Usuario
            }
        
        } catch (SQLException e) {
            Mensagem.mostrarDialogoErro("", "", "Não foi possível recuperar o usuário do banco de dados.");
        }
       
        return usuario; // Retornando o usuário ou null se não encontrado
}
    
}
