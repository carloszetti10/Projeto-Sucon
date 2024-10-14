/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.help;

import controller.MeuFxmlContrller;
import dao.EquipamentoDao;
import dao.RetiradaDao;
import dao.RetiradaSemDevDao;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TextField;
import model.ComboItem;
import model.Equipamento;
import model.Mensagem;
import model.Pessoa;
import model.Retirada;
import model.RetiradaSemDevolucao;

/**
 *
 * @author Carlos Zetti
 */
public class HelpRetiradas {
    
    private MeuFxmlContrller controller;

    public HelpRetiradas() {
    }

    public HelpRetiradas(MeuFxmlContrller controller) {
        this.controller = controller;
    }
    
    
    
    public void cadastrarRetiradasEmprestimo(){
       try{
            Retirada retirada = pegarCampoRetirada(); 
            ComboItem tipodeEquipamento = controller.getComboTipodeEquipamentoRetirada().getValue();
            if(tipodeEquipamento == null){
                controller.getComboTipodeEquipamentoRetirada().setStyle("-fx-border-color: red; -fx-background-color: #ffcccc;");
                Mensagem.mostrarDialogoAviso("", "", "Preencha todos os campos!"); 
                return;
            }
            
            if(tipodeEquipamento.getValor()){
                
                if(retirada == null){
                    Mensagem.mostrarDialogoAviso("", "", "Preencha os campos corretamente!");
                    return;
                } 
                if(retirada.getQuantidadeEquipamento() == 0){
                    controller.getCampoQuantidadeEquipamento().setStyle("-fx-border-color: red; -fx-background-color: #ffcccc;");
                    Mensagem.mostrarDialogoAviso("", "", "Quantidade Indisponivel!");
                    return;
                }else{
                    Equipamento e = pegarEquipamentodoCombo();
                    if(e.isComEmprestimo()){
                        RetiradaDao dao = new RetiradaDao();
                        boolean certo = dao.registrarRetirada(retirada);
                        if(certo){
                            Integer quantDisp = e.getQuantidadeDisponivel() - retirada.getQuantidadeEquipamento();
                            EquipamentoDao.atualizarQuantDisponivel(retirada.getIdEquipamento(), quantDisp);
                            Mensagem.mostrarDialogoInformacao("", "", "Emprestimo Registrado!");
                            controller.getComboTipodeEquipamentoRetirada().setValue(null);
                            return;
                        }
                    }else{
                        Mensagem.mostrarDialogoAviso("", "", "Esse Equipamento não pode ser emprestado!");
                    }
                }       
            } else {
                RetiradaSemDevolucao retiradaSemDev = pegarCampoRetiradaSemDev();
                if(retiradaSemDev == null){
                    Mensagem.mostrarDialogoAviso("", "", "Preencha os campos corretamente!");
                    return;
                }
                if(retiradaSemDev.getQuantidadeEquipamento() == 0){
                    controller.getCampoQuantidadeEquipamento().setStyle("-fx-border-color: red; -fx-background-color: #ffcccc;");
                    Mensagem.mostrarDialogoAviso("", "", "Quantidade Indisponivel!");
                }else{
                    Equipamento e = pegarEquipamentodoCombo();
                    if(!e.isComEmprestimo()){
                        RetiradaSemDevDao dao = new RetiradaSemDevDao();
                        dao.insertRetiradaSemDev(retiradaSemDev);
                        Integer quantDisp = e.getQuantidadeDisponivel() - retiradaSemDev.getQuantidadeEquipamento();
                        EquipamentoDao.atualizarQuantEntrega(retiradaSemDev.getIdEquipamento(), quantDisp);
                        Mensagem.mostrarDialogoInformacao("", "", "Retirada Registrada!");
                        controller.getComboTipodeEquipamentoRetirada().setValue(null);
                    }else{
                        Mensagem.mostrarDialogoAviso("", "", "Esse Equipamento não pode ser emprestado!");
                    }
                }
            }
        }catch(Exception e){
            Mensagem.mostrarDialogoErro("", "", "Erro ao cadastrar retiradas!");
        }
    }
    
    private Equipamento pegarEquipamentodoCombo(){
        String equipamentoSelecionado = controller.getCampoComboEquipamento().getValue();
        Equipamento e = Equipamento.buscarPorNome(equipamentoSelecionado); // Função para buscar equipamento por nome, se existir]  
        return e;
    }
    
    private Pessoa pegarPessoaCombo(){
        String pessoaSelecionada = controller.getCampoComboPessoa().getValue(); 
        Pessoa p = Pessoa.buscarPessoaPorNome(pessoaSelecionada);
        System.out.println(p);
        return  p;
    }
    
    public Retirada pegarCampoRetirada() {
        Integer quant = converterCampoNumero(controller.getCampoQuantidadeEquipamento()); // Verifica a quantidade
        Equipamento e = pegarEquipamentodoCombo();
        Pessoa p = pegarPessoaCombo();

        boolean camposValidos = true;

        // Verifica se a quantidade é válida
        if (quant == null || quant <= 0) {
            controller.getCampoQuantidadeEquipamento().setStyle("-fx-border-color: red; -fx-background-color: #ffcccc;");
            camposValidos = false;
        } else {
            controller.getCampoQuantidadeEquipamento().setStyle(""); // Remove o estilo de erro
        }

        // Verifica se o equipamento foi selecionado
        if (e == null) {
           controller.getCampoComboEquipamento().setStyle("-fx-border-color: red; -fx-background-color: #ffcccc;");
           camposValidos = false;
        } else {
            controller.getCampoComboEquipamento().setStyle(""); // Remove o estilo de erro
        }

        // Verifica se a pessoa foi selecionada (adicione esta verificação se necessário)
        if (p == null) {
            controller.getCampoComboPessoa().setStyle("-fx-border-color: red; -fx-background-color: #ffcccc;");
            camposValidos = false;
        } else {
            controller.getCampoComboPessoa().setStyle(""); // Remove o estilo de erro
        }

        // Exemplo de uso de camposValidados
        if (camposValidos) {
            
            
            if(e.getQuantidadeDisponivel() < quant){           
                return new Retirada(p.getIdPessoa(),e.getIdEquipamento() , 0);
            }else {
               return new Retirada(p.getIdPessoa(),e.getIdEquipamento() , quant);
            } 
        }
        return null;
    }
    
    public RetiradaSemDevolucao pegarCampoRetiradaSemDev() {
        Integer quant = converterCampoNumero(controller.getCampoQuantidadeEquipamento()); // Verifica a quantidade
        Equipamento e = pegarEquipamentodoCombo();
        Pessoa p = pegarPessoaCombo();

        boolean camposValidos = true;

        // Verifica se a quantidade é válida
        if (quant == null || quant <= 0) {
            controller.getCampoQuantidadeEquipamento().setStyle("-fx-border-color: red; -fx-background-color: #ffcccc;");
            camposValidos = false;
        } else {
            controller.getCampoQuantidadeEquipamento().setStyle(""); // Remove o estilo de erro
        }

        // Verifica se o equipamento foi selecionado
        if (e == null) {
           controller.getCampoComboEquipamento().setStyle("-fx-border-color: red; -fx-background-color: #ffcccc;");
           camposValidos = false;
        } else {
            controller.getCampoComboEquipamento().setStyle(""); // Remove o estilo de erro
        }

        // Verifica se a pessoa foi selecionada (adicione esta verificação se necessário)
        if (p == null) {
            controller.getCampoComboPessoa().setStyle("-fx-border-color: red; -fx-background-color: #ffcccc;");
            camposValidos = false;
        } else {
            controller.getCampoComboPessoa().setStyle(""); // Remove o estilo de erro
        }

        // Exemplo de uso de camposValidados
        if (camposValidos) {
            
            //if()
            if(e.getQuantidadeDisponivel() < quant){           
                return new RetiradaSemDevolucao(p.getIdPessoa(),e.getIdEquipamento() , 0);
            }else {
               return new RetiradaSemDevolucao(p.getIdPessoa(),e.getIdEquipamento() , quant);
            } 
        }
        return null;
    }

    public static Integer converterCampoNumero(TextField quant) {
        Integer quantCov = null; // 
       try {
           // Tenta converter o texto em número
           quantCov = Integer.parseInt(quant.getText());
        } catch (NumberFormatException e) {
            System.out.println(e);
            return null;
        }
        return quantCov; // Retorna o valor convertido
    }
   
   
   private void preencherComboboxPessoa(){
        controller.getCampoComboPessoa().setEditable(true);
        controller.getCampoComboPessoa().getEditor().clear();
        
        ArrayList<Pessoa> todasPessoas = Pessoa.todasPessoas();
        List<String> nomesPessoa = new ArrayList<>(); // Inicializando a lista
        for (Pessoa p : todasPessoas) {
            nomesPessoa.add(p.getNomePessoa());
        }
        controller.getCampoComboPessoa().getItems().addAll(nomesPessoa);
        // Adiciona o listener para filtrar conforme o usuário digita no ComboBox de Pessoa
        controller.getCampoComboPessoa().getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            filterComboBoxPessoaItems(newValue);
        });
    }
    
    public void preencherComboboxRetiradaEmprestimo(){
        //limparCombo();
        controller.getCampoComboEquipamento().setEditable(true);
        controller.getCampoComboEquipamento().getEditor().clear();
        ArrayList<Equipamento> todosEquipamentos = Equipamento.todosEquipamentos();
        List<String> nomesEquipa = new ArrayList<>(); // Inicializando a lista
        for (Equipamento e : todosEquipamentos) {
            if(e.isComEmprestimo()){
                nomesEquipa.add(e.getNomeEquipamento());
            }
        }
        // Populando os ComboBoxes
        controller.getCampoComboEquipamento().getItems().addAll(nomesEquipa);
        // Adiciona o listener para filtrar conforme o usuário digita no ComboBox de Equipamento
        controller.getCampoComboEquipamento().getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            filterComboBoxEquipamentoItems(newValue, nomesEquipa);
        });
        
        preencherComboboxPessoa();
    }
    
    public void preencherComboRetiradaSemEmprestimo(){
        //limparCombo();
        controller.getCampoComboEquipamento().setEditable(true);
        ArrayList<Equipamento> todosEquipamentos = Equipamento.todosEquipamentos();
        List<String> nomesEquipa = new ArrayList<>(); // Inicializando a lista
        for (Equipamento e : todosEquipamentos) {
            if(e.isComEmprestimo() == false){
                nomesEquipa.add(e.getNomeEquipamento());
            }
        }
        // Populando os ComboBoxes
        controller.getCampoComboEquipamento().getItems().addAll(nomesEquipa);
    
     
        
        // Adiciona o listener para filtrar conforme o usuário digita no ComboBox de Equipamento
        controller.getCampoComboEquipamento().getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            filterComboBoxEquipamentoItems(newValue, nomesEquipa);
        });
        preencherComboboxPessoa(); 
    }
    

    public void limparCombo() {
        controller.getCampoComboEquipamento().getItems().clear();
        controller.getCampoComboPessoa().getItems().clear();
   }

private void filterComboBoxPessoaItems(String filter) {
    List<String> originalItems = new ArrayList<>();
    for (Pessoa p : Pessoa.todasPessoas()) {
        originalItems.add(p.getNomePessoa());
    }

    // Limpa os itens atuais do ComboBox
    controller.getCampoComboPessoa().getItems().clear();

    // Se o campo de texto estiver vazio, mostra todos os itens
    if (filter.isEmpty()) {
        controller.getCampoComboPessoa().getItems().addAll(originalItems);
    } else {
        // Filtra os itens que começam com o texto digitado
        for (String nome : originalItems) {
            if (nome.toLowerCase().startsWith(filter.toLowerCase())) {
                controller.getCampoComboPessoa().getItems().add(nome);
            }
        }
    }

    // Mostra a lista filtrada de opções
    controller.getCampoComboPessoa().show();
}

private void filterComboBoxEquipamentoItems(String filter, List<String> nomesEquipa) {
    // Salvar o texto atual do editor antes de limpar os itens
    String editorText = controller.getCampoComboEquipamento().getEditor().getText();

    // Limpar os itens do ComboBox
    controller.getCampoComboEquipamento().getItems().clear();

    // Se o filtro for vazio, repopula todos os itens
    if (filter.isEmpty()) {
        controller.getCampoComboEquipamento().getItems().addAll(nomesEquipa);
    } else {
        // Filtrar e adicionar itens que contêm o texto digitado (ignorar maiúsculas/minúsculas)
        for (String nome : nomesEquipa) {
            if (nome.toLowerCase().contains(filter.toLowerCase())) {
                controller.getCampoComboEquipamento().getItems().add(nome);
            }
        }
    }

    // Reaplicar o texto no editor
    controller.getCampoComboEquipamento().getEditor().setText(editorText);
    // Mover o cursor para o final do texto
    controller.getCampoComboEquipamento().getEditor().positionCaret(editorText.length());

    // Se necessário, mostrar a lista de itens filtrados
    if (!controller.getCampoComboEquipamento().getItems().isEmpty()) {
        controller.getCampoComboEquipamento().show();
    }
}

}


  


