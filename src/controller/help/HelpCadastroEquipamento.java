/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.help;

import controller.MeuFxmlContrller;
import dao.EquipamentoDao;
import dao.RetiradaDao;
import dao.RetiradaSemDevDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ComboItem;
import model.Equipamento;
import model.Mensagem;
import model.Retirada;
import model.RetiradaSemDevolucao;

/**
 *
 * @author Carlos Zetti
 */
public class HelpCadastroEquipamento {
    
    //inicializar meu controller
    private MeuFxmlContrller controller;

    public HelpCadastroEquipamento(MeuFxmlContrller controller) {
        this.controller = controller;
    }

    public HelpCadastroEquipamento() {
    }
    
    
    
    
    //cadastrar 
    public void cadastrarEquipamento() throws SQLException {
        System.out.println(pegarCombo());
        TextField campoNome = controller.getCampoNomeEquipamento();
        if (verificarNome(campoNome) == null) {
            Mensagem.mostrarDialogoAviso("", "", "Preencha os campos corretamente!");  
            return;  // Interrompe a execução se o nome estiver incorreto
        } 
    
        String nomeCerto = campoNome.getText();
    
        TextField campoQuantidadeEquipamento = controller.getCampoQuantidade();
        Integer quantCerta = verificaQuantidade(campoQuantidadeEquipamento);
        if (quantCerta == null) {
            Mensagem.mostrarDialogoAviso("", "", "O campo quantidade deve ser preenchido com um número!");
            return;  // Interrompe a execução se a quantidade estiver incorreta
        }

        Boolean comboValor = pegarCombo();
        if (comboValor == null) {
            Mensagem.mostrarDialogoAviso("", "", "Preencha o tipo de equipamento corretamente!");
            return;  // Interrompe a execução se o combo estiver incorreto
        }

        // Se todas as verificações estiverem corretas, insere no banco de dados
        EquipamentoDao dao = new EquipamentoDao();
        dao.insert(new Equipamento(nomeCerto, quantCerta, comboValor));
        

        limparCampos();  // Limpa os campos após o cadastro
    }
   
    public String verificarNome(TextField nomeRec) {
        // Obtém o nome do equipamento
        String nome = nomeRec.getText(); // Recupera o texto do TextField

        // Verifica se o nome está vazio
        if (nome == null || nome.trim().isEmpty() || nome.length() <= 4 ) {
           controller.getCampoNomeEquipamento().setStyle("-fx-border-color: red; -fx-background-color: #ffcccc;");
           return null;
        }

        // Reseta o estilo se o nome for válido
        controller.getCampoNomeEquipamento().setStyle("");  // Remove o estilo customizado e volta ao padrão
        return nome;
    }
   
    
    public Integer verificaQuantidade(TextField quant) {
        Integer quantCov = null; // Inicializa a variável para o valor convertido

       try {
           // Tenta converter o texto em número
           quantCov = Integer.parseInt(quant.getText());
        } catch (NumberFormatException e) {
            // Trata a exceção se a conversão falhar
            controller.getCampoQuantidade().setStyle("-fx-border-color: red; -fx-background-color: #ffcccc;");
            return null;
        }

        // Reseta o estilo se a conversão for bem-sucedida
        controller.getCampoQuantidade().setStyle(""); 
        return quantCov; // Retorna o valor convertido
    }
    
    public Boolean pegarCombo(){
        ComboItem combo = controller.getCampoCadastroComboTipoEqui().getValue();
        if(combo == null){
            controller.getCampoCadastroComboTipoEqui().setStyle("-fx-border-color: red; -fx-background-color: #ffcccc;");
            return null;
        } else{
            controller.getCampoCadastroComboTipoEqui().setStyle("");
            return combo.getValor();
        }
    }

    
    
    public void limparCampos(){
        controller.getCampoNomeEquipamento().setText("");
        controller.getCampoQuantidade().setText("");
        controller.getCampoIdEquipamento().setText("");
        controller.getCampoQuantDispEquipamento().setText("");
        controller.getCampoCadastroComboTipoEqui().setValue(null);
    }
    
   
    public void configurarTabelaEquipamento() {
        // Remover a coluna de índice se já existir
        controller.getTabelaCadatroEquip().getColumns().removeIf(col -> col.getText().equals("Item"));

        // Criando a coluna de índice com o nome "Item"
        TableColumn<Equipamento, Integer> colunaItem = new TableColumn<>("Item");
        colunaItem.setCellValueFactory(cellData -> 
           new ReadOnlyObjectWrapper<>(controller.getTabelaCadatroEquip().getItems().indexOf(cellData.getValue()) + 1)
        );

        // Configurando as demais colunas
        controller.getTabIdEqui().setCellValueFactory(new PropertyValueFactory<Equipamento, Integer>("idEquipamento"));
        controller.getTabelaNomeEqui().setCellValueFactory(new PropertyValueFactory<Equipamento, String>("nomeEquipamento"));
        controller.getTabQuantEqui().setCellValueFactory(new PropertyValueFactory<Equipamento, Integer>("quantidade"));
        controller.getTabDisponEqui().setCellValueFactory(new PropertyValueFactory<Equipamento, Integer>("quantidadeDisponivel"));
        controller.getColumTabEquiPodeEmprestar().setCellValueFactory(new PropertyValueFactory<Equipamento, Boolean>("comEmprestimo"));

        // Adicionando a coluna "Item" no início da tabela
        controller.getTabelaCadatroEquip().getColumns().add(0, colunaItem);
    }

    
    public void PreencherTabela(ArrayList Equipamentos){
        //Obtém a lista do BancoDao e cria uma ObservableList
        ObservableList<Equipamento> equipamentos = FXCollections.observableArrayList(Equipamentos);
        controller.getTabelaCadatroEquip().setItems(equipamentos);
    }
    
    public void prencherVisualizacaoTabela(){
        ComboItem combo = controller.getVisializarTipodeEquipamentoCombo().getValue();
        // Verifica se o valor de combo é nulo
        String nome = combo.getNome();
        if (nome.equalsIgnoreCase("Todos") || nome == null) {
            limpaPesquisa();
            PreencherTabela(Equipamento.todosEquipamentos());
        } else if (nome.equalsIgnoreCase("Equipamentos Emprestar") ) {
            limpaPesquisa();
            PreencherTabela(Equipamento.equipamentosEmprestar());
        } else if(nome.equalsIgnoreCase("Equipamentos Retiradas") ) {
            limpaPesquisa();
            PreencherTabela(Equipamento.equipamentosRetirar());
        }
    }
    
    
    public void pesquisarNome() {
        String nome = controller.getCampoPesquisaEqui().getText().toLowerCase();
        EquipamentoDao dao = new EquipamentoDao();
        try {
            ArrayList<Equipamento> equipamentoPesquisado = dao.pesquisarNome(nome);
            ObservableList<Equipamento> equipamentos = FXCollections.observableArrayList(equipamentoPesquisado);
            controller.getTabelaCadatroEquip().setItems(equipamentos);
        } catch (SQLException e) {
        }  
    }
    
    
    
    public Equipamento selecionarEquipamentoClick() {
        int i = controller.getTabelaCadatroEquip().getSelectionModel().getSelectedIndex();  // Obtém o índice da linha selecionada
        if (i < 0) {
            System.out.println("Nenhum equipamento selecionado.");
            return null; 
        }
        Equipamento e = (Equipamento)controller.getTabelaCadatroEquip().getItems().get(i); // Obtém o item da linha selecionada
        controller.getCampoIdEquipamento().setText(String.valueOf(e.getIdEquipamento()));
        controller.getCampoNomeEquipamento().setText(e.getNomeEquipamento());
        controller.getCampoQuantidade().setText(String.valueOf(e.getQuantidade()));
        controller.getCampoQuantDispEquipamento().setText(String.valueOf(e.getQuantidadeDisponivel()));
        if(e.isComEmprestimo()){
            controller.getCampoCadastroComboTipoEqui().setValue(controller.getPodeSerEmprest());
        }else{
            controller.getCampoCadastroComboTipoEqui().setValue(controller.getNaoEmprest());
        }
        limpaPesquisa();
        return e;
    }
    
    
    public Equipamento pegarCampos(){
        String nomeVerificado = verificarNome(controller.getCampoNomeEquipamento());
        Integer quantVerificada = verificaQuantidade(controller.getCampoQuantidade());
        Boolean valorCombo = pegarCombo();
        String campoQuantDispEquipamento = controller.getCampoQuantDispEquipamento().getText();
        TextField campoIdEquipamento = controller.getCampoIdEquipamento();
        Integer quantDispo = null;
        
        boolean camposValidos = true;
        if (!campoQuantDispEquipamento.isEmpty()) {
           try {
               quantDispo = Integer.parseInt(campoQuantDispEquipamento);
            } catch (NumberFormatException e) {
               camposValidos = false;
            }
        } else {
            camposValidos = false;
        }

        
        if(nomeVerificado == null || nomeVerificado.trim().isEmpty()){
            camposValidos = false;  
        }
        if(quantVerificada == null || valorCombo == null || campoIdEquipamento.getText() == null){
            camposValidos = false;  
        }
        
        if(camposValidos){
            return new Equipamento (nomeVerificado, quantVerificada , quantDispo, valorCombo);
        } else{
            return null;
        }
    }
    
    public void editarEquipamento() throws SQLException{
        Equipamento equiEditar = pegarCampos();
        
        if(equiEditar == null){
            Mensagem.mostrarDialogoAviso("", "", "Selecione um Equipamento da tabela para Editar!");
            return;
        }else{
            Integer idCampoEditar = pegarIdCampo();
            ArrayList<Equipamento> todosEquipamentos = Equipamento.todosEquipamentos();
            
            for(Equipamento e : todosEquipamentos){
                if(e.getIdEquipamento().equals(idCampoEditar)){
                    if(e.getNomeEquipamento().equals(equiEditar.getNomeEquipamento()) && e.getQuantidade() == equiEditar.getQuantidade() && Objects.equals(e.isComEmprestimo(), equiEditar.isComEmprestimo())){
                       Mensagem.mostrarDialogoAviso("", "", "Nenhum dado do Equipamento foi alterado.");
                       break;
                    } else{
                        //editar
                        
                        if(!Objects.equals(e.getComEmprestimo(), equiEditar.getComEmprestimo())){
                            Retirada existe = verificarRetiradaExisteId(idCampoEditar);
                            if(existe != null){
                                Mensagem.mostrarDialogoAviso("", "", "Não foi possivel editar o tipo de equipamento, pois há emprestimo com ele.");
                                break;
                            }else{
                                Integer quantAtualizar = pegarQuantAtualizar(e.getQuantidade(), equiEditar.getQuantidade(), e.getQuantidadeDisponivel(), idCampoEditar, equiEditar.isComEmprestimo());
                                if(quantAtualizar == null){
                                    Mensagem.mostrarDialogoAviso("", "", "Insira uma quantidade valida ou verifique se ha emprestimos com esse euipamento.");
                                    break;
                                }else{
                                    Equipamento novoEqui = new Equipamento(equiEditar.getNomeEquipamento(), equiEditar.getQuantidade(), quantAtualizar, equiEditar.isComEmprestimo());
                                    EquipamentoDao.atualizarEquipamento(idCampoEditar, novoEqui);
                                    limparCampos();
                                    PreencherTabela(Equipamento.todosEquipamentos()); 
                                    break;
                                }
                            }
                        } else{
                            Integer quantAtualizar = pegarQuantAtualizar(e.getQuantidade(), equiEditar.getQuantidade(), e.getQuantidadeDisponivel(), idCampoEditar, equiEditar.isComEmprestimo());
                            if(quantAtualizar == null){
                                Mensagem.mostrarDialogoAviso("", "", "Insira uma quantidade valida ou verifique se ha emprestimos com esse euipamento.");
                                break;
                            }else{
                                Equipamento novoEqui = new Equipamento(equiEditar.getNomeEquipamento(), equiEditar.getQuantidade(), quantAtualizar, equiEditar.isComEmprestimo());
                                EquipamentoDao.atualizarEquipamento(idCampoEditar, novoEqui);
                                limparCampos();
                                PreencherTabela(Equipamento.todosEquipamentos()); 
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    
    public Retirada verificarRetiradaExisteId(Integer id) throws SQLException{
        ArrayList<Retirada> listarRetiradas = RetiradaDao.listarRetiradas();
        //Retirada r = null;
        for(Retirada r : listarRetiradas){
            if(Objects.equals(r.getIdEquipamento(),id)){
                return r;
            }
        }
        return null;
    }
    
    private Integer pegarQuantAtualizar(Integer quantBanco, Integer quantPassada, Integer quantDispBanco, Integer id, Boolean tipo ) throws SQLException{
      
        Integer quantAtualizar = null;
        if(quantPassada == 0){
            return null;
        }
        if(tipo){
            //equipamento de emprestar
            if(Objects.equals(quantPassada, quantBanco)){
                quantAtualizar = quantDispBanco;
            }else{
                if(quantPassada > quantBanco){
                    Integer adicionarQuant = quantPassada - quantBanco;
                    quantAtualizar = quantDispBanco + adicionarQuant;
                }else{
                    if(Objects.equals(quantPassada, quantDispBanco)){
                        quantAtualizar = 0;
                    }else{
                        if(quantPassada < quantDispBanco){
                            Retirada existe = verificarRetiradaExisteId(id);     
                            if(existe != null){
                                quantAtualizar = null;
                                if(quantPassada>existe.getQuantidadeEquipamento()){
                                    quantAtualizar = quantPassada - existe.getQuantidadeEquipamento();
                                }
                            }else{
                                quantAtualizar = quantPassada;
                            }
                        }else{
                            Retirada r = verificarRetiradaExisteId(id);
                            if(Objects.equals(r.getQuantidadeEquipamento(), quantPassada)){
                                quantAtualizar=0;
                            }else{
                                if(r.getQuantidadeEquipamento()>quantPassada){
                                    quantAtualizar = null;
                                }else{
                                   Integer diminuirQuant = quantPassada - r.getQuantidadeEquipamento();
                                   quantAtualizar = diminuirQuant; //erro de logica  
                                }
                            }                          
                        }
                    }
                }
            }   
        }else{
            quantAtualizar = quantPassada;
        }   
        return quantAtualizar;
    }

    public void excluirEquipamento() throws SQLException{
        Equipamento e = pegarCampos();
        if(e == null){
            Mensagem.mostrarDialogoAviso("", "", "Selecione um Equipamento da tabela para excluir!");
            return;
        }else{
            if(e.isComEmprestimo()){
                Integer idInt = pegarIdCampo();
                
                boolean sim = Mensagem.mostrarDialogoOpcao("", "", "Deseja realmente Excluir o equipamento: " + e.getNomeEquipamento()+ "?");
                if(sim){
                    ArrayList<Retirada> listaRetiradas = RetiradaDao.listarRetiradas();
                    boolean mesmoId = false;
                    for(Retirada r : listaRetiradas){
                        if(r.getIdEquipamento() == idInt){
                            mesmoId = true;
                            break;
                        }
                    }
                    if(mesmoId){
                       Mensagem.mostrarDialogoAviso("", "", "Não foi possível apagar o equipamento. Por favor, verifique se há devoluções pendentes antes de tentar novamente.");
                       return;
                    }else{
                        EquipamentoDao.deletarEquipamento(idInt);
                        PreencherTabela(Equipamento.todosEquipamentos()); 
                        limparCampos();
                        return;
                    }
                    
                }
            }else{
                //deletar o equipamento De Retirada sem devolução
                String textId = controller.getCampoIdEquipamento().getText();
                Integer idInt = Integer.parseInt(textId);
                boolean sim = Mensagem.mostrarDialogoOpcao("", "", "Você realmente deseja excluir o equipamento: "+ e.getNomeEquipamento() + "?, A exclusão deste equipamento resultará na remoção de todas as retiradas associadas a ele.");
                if(sim){
                    ArrayList<RetiradaSemDevolucao> listaRetiradas = RetiradaSemDevDao.listaRetiradasSemDev();
                    for(RetiradaSemDevolucao r : listaRetiradas){
                        if(r.getIdEquipamento() == idInt){
                            RetiradaSemDevDao.deletarRetirada(r.getIdRetirada());
                        }
                    }
                    EquipamentoDao.deletarEquipamento(idInt);
                    PreencherTabela(Equipamento.todosEquipamentos()); 
                    limparCampos();
                }
            }
        }  
    } 
    
    private Integer pegarIdCampo(){
        String textId = controller.getCampoIdEquipamento().getText();
        Integer idInt = Integer.parseInt(textId);
        return idInt;
    }
    
    public void limpaPesquisa(){
        controller.getCampoPesquisaEqui().setText("");
    }
}
