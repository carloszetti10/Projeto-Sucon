package controller;

import controller.help.HelpDevolucao;
import controller.help.HelpCadastroEquipamento;
import controller.help.HelpRetiradas;
import controller.help.HelpCadastroPessoas;
import controller.help.HelpRetiradaSemDev;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.ComboItem;
import model.DetalheRetirada;
import model.DetalhesRetiradasSemDevolucao;
import model.Equipamento;
import model.Mensagem;
import model.Pessoa;
import view.Main;

public class MeuFxmlContrller implements Initializable {
    
    //var para criar os controlles em outras classe
    private HelpRetiradas helpRetiradas;
    private HelpCadastroEquipamento helpCadastroEquipamento;
    private HelpCadastroPessoas helpPessoa;
    private HelpDevolucao helpDevolucao;
    private HelpRetiradaSemDev helpSemDev;
    
    //iniciar a classe
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //iniciar na tela retirada
        retiradasTelas.setVisible(true);
        cadastrTelas.setVisible(false);
        devolTelas.setVisible(false);
        cadastrarPessoaTelas.setVisible(false);
        telaRetiradasSemDev.setVisible(false);
        bntRetirada.setStyle("-fx-background-color:linear-gradient(to bottom right, #2b303c, #0f89ca);");
        
        //iniciar minhas classes ajudante
        helpRetiradas = new HelpRetiradas(this);
        helpPessoa = new HelpCadastroPessoas(this);
        helpDevolucao = new HelpDevolucao(this);
        helpCadastroEquipamento = new HelpCadastroEquipamento(this);
        helpSemDev = new HelpRetiradaSemDev(this);
        
        //configurar Tabelas
        helpSemDev.configurarTabela();
        helpCadastroEquipamento.configurarTabelaEquipamento();
        helpPessoa.configurarTabelaPessoa();
        
       
       // Preenche o ComboBox com itens personalizados
        campoCadastroComboTipoEqui.getItems().addAll(
            podeSerEmprest, 
            naoEmprest
        );
        
        //preencher o combo da retirada 
        comboTipodeEquipamentoRetirada.getItems().addAll(
            new ComboItem("EMPRESTAR", true),
            new ComboItem("RETIRAR", false)
        );
        
        //prencher o combo visualizar Equipamento
        visializarTipodeEquipamentoCombo.getItems().addAll(
            new ComboItem("Equipamentos Emprestar", Boolean.TRUE),
            new ComboItem("Equipamentos Retiradas", Boolean.FALSE),
            new ComboItem("Todos", Boolean.FALSE)
            
        );

    }
    private ComboItem podeSerEmprest = new ComboItem("Pode ser emprestado", Boolean.TRUE);
    private ComboItem naoEmprest = new ComboItem("Não pode ser emprestado", Boolean.FALSE);
    public ComboItem getPodeSerEmprest() {
        return podeSerEmprest;
    }
    public ComboItem getNaoEmprest() {
        return naoEmprest;
    }
   

    @FXML
    private TextField CampoPesquisaEqui;

    @FXML
    private AnchorPane devolTelas;

    @FXML
    private TableView<Equipamento> TabelaCadatroEquip;

    @FXML
    private Button bntCadastr0Equipamento;

    @FXML
    private Button bntCadastrar;

    @FXML
    private Button bntDevol;

    @FXML
    private Button bntRetirada;
    
    @FXML
    private Button bntCadastrarPessoasEsc;

    @FXML
    private Button bntSair;

    @FXML
    private AnchorPane cadastrTelas;
   
    
    //equipamento
    @FXML
    void comboTipoEqui(ActionEvent event){  
    }
   
    @FXML
    private ComboBox<ComboItem> campoCadastroComboTipoEqui; // combo para cadastrar equipamento
    @FXML
    private TextField campoIdEquipamento;
    @FXML
    private TextField campoNomeEquipamento;
    @FXML
    private TextField campoQuantidade;
    @FXML
    private TextField campoQuantDispEquipamento;
  
    @FXML
    private ComboBox<ComboItem> visializarTipodeEquipamentoCombo;
    @FXML
    void visializarTipodeEquipamentoComboEvent(ActionEvent event) {
        helpCadastroEquipamento.prencherVisualizacaoTabela();
    }
    
    @FXML
    void excluirEquipamento(ActionEvent event) throws SQLException {
        helpCadastroEquipamento.excluirEquipamento();
        helpCadastroEquipamento.limpaPesquisa();
    }
    
    @FXML
    void editarEquipamento(ActionEvent event) throws SQLException {
        boolean sim = Mensagem.mostrarDialogoOpcao("", "", "Deseja realmente editar o equipamento?");
        if(sim){
           helpCadastroEquipamento.editarEquipamento(); 
        }
        helpCadastroEquipamento.limpaPesquisa();
        
    }
    
    
    
    @FXML
    private Button close;

    @FXML
    private Button minimize;

    @FXML
    private AnchorPane retiradasTelas;

    @FXML
    private TableColumn<Equipamento, Integer> tabDisponEqui;

    @FXML
    private TableColumn<Equipamento, Integer> tabIdEquipa;

    @FXML
    private TableColumn<Equipamento, Integer> tabQuantEqui;

    @FXML
    private TableColumn<Equipamento, String> tabelaNomeEqui;
    
    @FXML
    private TableColumn<Equipamento, Boolean> columTabEquiPodeEmprestar;
    
    @FXML
    private TableColumn<Equipamento, Integer> tabIdEqui;
    
    @FXML
    void selecionarEquipamentoIntemTabela(MouseEvent event) {
        helpCadastroEquipamento.selecionarEquipamentoClick();
    }


    @FXML
    void Sair(ActionEvent event)  {
        boolean sim = Mensagem.mostrarDialogoOpcao("", "", "Deseja sair do sistema?");
        if(sim){
            Main m = new Main();
            m.abrirLoginNovoStage(event);
        }
    }

    @FXML
    void buscarPorNomeEqui(KeyEvent event) {
        helpCadastroEquipamento.pesquisarNome();

    }


    @FXML
    void close(ActionEvent event) {
        System.exit(0);
    }
    
    //semdevRetiradas
    
    @FXML
    private Button btnTelaSemDevRet;
    
    
    @FXML
    private TableView<DetalhesRetiradasSemDevolucao> semDevTabelaRetirada;

    @FXML
    private TableColumn<DetalhesRetiradasSemDevolucao, String> semDevPessoa;

    @FXML
    private TextField semDevCampoPesquisa;

    @FXML
    private TableColumn<DetalhesRetiradasSemDevolucao, Integer> semDevQuant;

    @FXML
    private TableColumn<DetalhesRetiradasSemDevolucao, String> semDevEqui;

    @FXML
    private AnchorPane telaRetiradasSemDev;

    @FXML
    private TableColumn<DetalhesRetiradasSemDevolucao, Integer> semDevId;

    @FXML
    private TableColumn<DetalhesRetiradasSemDevolucao, String> semDevDia;

    @FXML
    void PesquisarRetSemdev(KeyEvent event) {
        helpSemDev.pesquisarRet();
    }

    @FXML
    void escolhaPagina(ActionEvent event) throws SQLException {
        if (event.getSource() == bntRetirada) {
            retiradasTelas.setVisible(true);
            cadastrTelas.setVisible(false);
            devolTelas.setVisible(false);
            cadastrarPessoaTelas.setVisible(false);
            telaRetiradasSemDev.setVisible(false);

            bntRetirada.setStyle("-fx-background-color:linear-gradient(to bottom right, #2b303c, #0f89ca);");
            bntCadastr0Equipamento.setStyle("-fx-background-color:transparent");
            bntDevol.setStyle("-fx-background-color:transparent");
            bntCadastrarPessoasEsc.setStyle("-fx-background-color:transparent;");
            btnTelaSemDevRet.setStyle("-fx-background-color:transparent;");
            
            campoQuantidadeEquipamento.setText("");
            comboTipodeEquipamentoRetirada.setValue(null);
            


        } else if (event.getSource() == bntCadastr0Equipamento) {
            retiradasTelas.setVisible(false);
            cadastrTelas.setVisible(true);
            devolTelas.setVisible(false);
            cadastrarPessoaTelas.setVisible(false);
            telaRetiradasSemDev.setVisible(false);

            bntCadastr0Equipamento.setStyle("-fx-background-color:linear-gradient(to bottom right, #2b303c, #0f89ca);");
            bntDevol.setStyle("-fx-background-color:transparent");
            bntRetirada.setStyle("-fx-background-color:transparent");
            bntCadastrarPessoasEsc.setStyle("-fx-background-color:transparent;");
            btnTelaSemDevRet.setStyle("-fx-background-color:transparent;");
             
            helpCadastroEquipamento.limparCampos();
            visializarTipodeEquipamentoCombo.setValue(null);
            helpCadastroEquipamento.PreencherTabela(Equipamento.todosEquipamentos());
            helpCadastroEquipamento.limpaPesquisa();
            
        } else if (event.getSource() == bntDevol) {
            retiradasTelas.setVisible(false);
            cadastrTelas.setVisible(false);
            devolTelas.setVisible(true);
            cadastrarPessoaTelas.setVisible(false);
            telaRetiradasSemDev.setVisible(false);

            bntDevol.setStyle("-fx-background-color:linear-gradient(to bottom right, #2b303c, #0f89ca);");
            bntCadastr0Equipamento.setStyle("-fx-background-color:transparent");
            bntRetirada.setStyle("-fx-background-color:transparent");
            bntCadastrarPessoasEsc.setStyle("-fx-background-color:transparent;");
            btnTelaSemDevRet.setStyle("-fx-background-color:transparent;");
            
            helpDevolucao.configurarTabela();
            helpDevolucao.PreencherTabela();
            helpDevolucao.limparPesquisaDev();
            
        } else if(event.getSource() == bntCadastrarPessoasEsc){
            retiradasTelas.setVisible(false);
            cadastrTelas.setVisible(false);
            devolTelas.setVisible(false);
            cadastrarPessoaTelas.setVisible(true);
            telaRetiradasSemDev.setVisible(false);
            
            bntRetirada.setStyle("-fx-background-color:transparent;");
            bntCadastr0Equipamento.setStyle("-fx-background-color:transparent");
            bntDevol.setStyle("-fx-background-color:transparent");
            bntCadastrarPessoasEsc.setStyle("-fx-background-color:linear-gradient(to bottom right, #2b303c, #0f89ca);");
            btnTelaSemDevRet.setStyle("-fx-background-color:transparent;");
        
            helpPessoa.preencherTabelaPessoa();
            helpPessoa.limpaPesqui();
            
        } else if(event.getSource() == btnTelaSemDevRet){
            telaRetiradasSemDev.setVisible(true);
            retiradasTelas.setVisible(false);
            cadastrTelas.setVisible(false);
            devolTelas.setVisible(false);
            cadastrarPessoaTelas.setVisible(false);
            
            bntRetirada.setStyle("-fx-background-color:transparent;");
            bntCadastr0Equipamento.setStyle("-fx-background-color:transparent");
            bntDevol.setStyle("-fx-background-color:transparent");
            bntCadastrarPessoasEsc.setStyle("-fx-background-color:transparent");
            btnTelaSemDevRet.setStyle("-fx-background-color:linear-gradient(to bottom right, #2b303c, #0f89ca);");
            
            helpSemDev.limparPesquisa();
            helpSemDev.preencherTabela();
        }
    }
    
  
    @FXML
    void eventoCadastrarEqui(ActionEvent event) throws SQLException {
        helpCadastroEquipamento.cadastrarEquipamento();
        helpCadastroEquipamento.PreencherTabela(Equipamento.todosEquipamentos());

    }

    @FXML
    void minimize(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true); // Minimiza a janela
    }
    
    
    //tela de devolução

    @FXML
    private TableColumn<DetalheRetirada, Boolean> tabEntregueRetirada;

    @FXML
    private TableColumn<DetalheRetirada, String> tabelaNomePessRetirou;

    @FXML
    private TableColumn<DetalheRetirada, String> tabEquipamentoRetirado;

    @FXML
    private TableColumn<DetalheRetirada, Integer> tabQuantRetirada;


    @FXML
    private TableColumn<DetalheRetirada, Integer> tabIdRetiradas;

    @FXML
    private TableColumn<DetalheRetirada, String> tabDiaRetirada;

    @FXML
    private TextField CampoPesquisaDevolucao;

    @FXML
    private TableView<DetalheRetirada> TabelaRetiradas;
    
    @FXML
    private TextField campoQuantidadedev;

    @FXML
    private Button btEntregar;
    
    @FXML
    private TextField campoNomePessoaRetirada;
    
    @FXML
    private TextField campoIEquipamentoRetirada;
    
    @FXML
    private TextField campoIdRetirada;
    
    
    
    @FXML
    void entregarEquipamento(ActionEvent event) throws SQLException {
        helpDevolucao.entregar();
        helpDevolucao.limparPesquisaDev();
    }
    
 
    @FXML
    void pesquisarPessoaDevolucao(KeyEvent event) {
        helpDevolucao.pesquisarDev();
    }
    
    @FXML
    void selecionarDevolTabela(MouseEvent event) {
        helpDevolucao.selecionarRetiradaClick();
    }

    
    
    //tela cadastro retiradas
    @FXML
    private TextField campoQuantidadeEquipamento;

    @FXML
    private Button registrarRetirada;

    @FXML
    private ComboBox<String> campoComboPessoa;

    @FXML
    private ComboBox<String> campoComboEquipamento;

    @FXML
    void registrarRetiradaEvent(ActionEvent event) {
        helpRetiradas.cadastrarRetiradasEmprestimo();
        //comboTipodeEquipamentoRetirada.setValue(null);
    }
    
    @FXML
    private ComboBox<ComboItem> comboTipodeEquipamentoRetirada;

    @FXML
    void comboTipodeEquipamentoRetiradaEvento(ActionEvent event) {
        ComboItem combo = comboTipodeEquipamentoRetirada.getValue();
        if (combo != null) {
            helpRetiradas.limparCombo();
            if(combo.getValor()){
                helpRetiradas.limparCombo();
                helpRetiradas.preencherComboboxRetiradaEmprestimo();
            }else{
                helpRetiradas.limparCombo();
                helpRetiradas.preencherComboRetiradaSemEmprestimo();
            }
        }else{
            campoComboPessoa.setValue(null);
            campoComboEquipamento.setValue(null);
            helpRetiradas.limparCombo();
        }
        
    }
    

    //cadastrar pessoas Tela
    
    @FXML
    private TextField campoTelefone;

    @FXML
    private TextField campoNomePessoa;

    @FXML
    private TableColumn<Pessoa, String> TelefonePessoaTab;

    @FXML
    private Button bntCadastrarPessoa;

    @FXML
    private TableColumn<Pessoa, String> setoPessoaTab;

    @FXML
    private AnchorPane cadastrarPessoaTelas;

    @FXML
    private TableView<Pessoa> TabelaPessoas;

    @FXML
    private TableColumn<Pessoa, String> NomePessoaTab;

    @FXML
    private TextField CampoSetor;

    @FXML
    private TextField CampoPesquisaPessoa;
    
    @FXML
    void PesquisarPessoaPorNome(KeyEvent event) {
        helpPessoa.pesquisarNome();
    }

    @FXML
    void cadastrarPessoaEvent(ActionEvent event) throws SQLException {
       helpPessoa.cadastrarPessoa(event);
    }

    public TableColumn<Pessoa, String> getTelefonePessoaTab() {
        return TelefonePessoaTab;
    }

    public void setTelefonePessoaTab(TableColumn<Pessoa, String> TelefonePessoaTab) {
        this.TelefonePessoaTab = TelefonePessoaTab;
    }

    public Button getBntCadastrarPessoa() {
        return bntCadastrarPessoa;
    }

    public void setBntCadastrarPessoa(Button bntCadastrarPessoa) {
        this.bntCadastrarPessoa = bntCadastrarPessoa;
    }

    public TableColumn<Pessoa, String> getNomePessoaTab() {
        return NomePessoaTab;
    }

    public void setNomePessoaTab(TableColumn<Pessoa, String> NomePessoaTab) {
        this.NomePessoaTab = NomePessoaTab;
    }

    public TextField getCampoPesquisaPessoa() {
        return CampoPesquisaPessoa;
    }

    public void setCampoPesquisaPessoa(TextField CampoPesquisaPessoa) {
        this.CampoPesquisaPessoa = CampoPesquisaPessoa;
    }

    public TableColumn<Pessoa, String> getSetoPessoaTab() {
        return setoPessoaTab;
    }

    public void setSetoPessoaTab(TableColumn<Pessoa, String> setoPessoaTab) {
        this.setoPessoaTab = setoPessoaTab;
    }

    public TableView<Pessoa> getTabelaPessoas() {
        return TabelaPessoas;
    }

    public void setTabelaPessoas(TableView<Pessoa> TabelaPessoas) {
        this.TabelaPessoas = TabelaPessoas;
    }

    public TextField getCampoSetor() {
        return CampoSetor;
    }

    public void setCampoSetor(TextField CampoSetor) {
        this.CampoSetor = CampoSetor;
    }

    public TextField getCampoTelefone() {
        return campoTelefone;
    }

    public void setCampoTelefone(TextField campoTelefone) {
        this.campoTelefone = campoTelefone;
    }

    public TextField getCampoNomePessoa() {
        return campoNomePessoa;
    }

    public void setCampoNomePessoa(TextField campoNomePessoa) {
        this.campoNomePessoa = campoNomePessoa;
    }
    
    
    
    
    //gets e sets EQUIPAMENTOS

    public TextField getCampoPesquisaEqui() {
        return CampoPesquisaEqui;
    }

    public void setCampoPesquisaEqui(TextField CampoPesquisaEqui) {
        this.CampoPesquisaEqui = CampoPesquisaEqui;
    }

    public TableView<Equipamento> getTabelaCadatroEquip() {
        return TabelaCadatroEquip;
    }

    public void setTabelaCadatroEquip(TableView<Equipamento> TabelaCadatroEquip) {
        this.TabelaCadatroEquip = TabelaCadatroEquip;
    }

    public Button getBntCadastrar() {
        return bntCadastrar;
    }

    public void setBntCadastrar(Button bntCadastrar) {
        this.bntCadastrar = bntCadastrar;
    }

    public TextField getCampoNomeEquipamento() {
        return campoNomeEquipamento;
    }

    public void setCampoNomeEquipamento(TextField campoNomeEquipamento) {
        this.campoNomeEquipamento = campoNomeEquipamento;
    }

    public TableColumn<Equipamento, Integer> getTabDisponEqui() {
        return tabDisponEqui;
    }

    public void setTabDisponEqui(TableColumn<Equipamento, Integer> tabDisponEqui) {
        this.tabDisponEqui = tabDisponEqui;
    }

    public TableColumn<Equipamento, Integer> getTabIdEquipa() {
        return tabIdEquipa;
    }

    public void setTabIdEquipa(TableColumn<Equipamento, Integer> tabIdEquipa) {
        this.tabIdEquipa = tabIdEquipa;
    }

    public TableColumn<Equipamento, Integer> getTabQuantEqui() {
        return tabQuantEqui;
    }

    public void setTabQuantEqui(TableColumn<Equipamento, Integer> tabQuantEqui) {
        this.tabQuantEqui = tabQuantEqui;
    }

    public TableColumn<Equipamento, String> getTabelaNomeEqui() {
        return tabelaNomeEqui;
    }

    public void setTabelaNomeEqui(TableColumn<Equipamento, String> tabelaNomeEqui) {
        this.tabelaNomeEqui = tabelaNomeEqui;
    }

    public TextField getCampoQuantidade() {
        return campoQuantidade;
    }

    public void setCampoQuantidade(TextField campoQuantidade) {
        this.campoQuantidade = campoQuantidade;
    }

    public ComboBox<ComboItem> getCampoCadastroComboTipoEqui() {
        return campoCadastroComboTipoEqui;
    }

    public void setCampoCadastroComboTipoEqui(ComboBox<ComboItem> campoCadastroComboTipoEqui) {
        this.campoCadastroComboTipoEqui = campoCadastroComboTipoEqui;
    }

    public ComboBox<ComboItem> getVisializarTipodeEquipamentoCombo() {
        return visializarTipodeEquipamentoCombo;
    }

    public void setVisializarTipodeEquipamentoCombo(ComboBox<ComboItem> visializarTipodeEquipamentoCombo) {
        this.visializarTipodeEquipamentoCombo = visializarTipodeEquipamentoCombo;
    }

    public TableColumn<Equipamento, Boolean> getColumTabEquiPodeEmprestar() {
        return columTabEquiPodeEmprestar;
    }

    public void setColumTabEquiPodeEmprestar(TableColumn<Equipamento, Boolean> columTabEquiPodeEmprestar) {
        this.columTabEquiPodeEmprestar = columTabEquiPodeEmprestar;
    }

    public TableColumn<Equipamento, Integer> getTabIdEqui() {
        return tabIdEqui;
    }
    
    public TextField getCampoIdEquipamento() {
       return campoIdEquipamento;
    }

    public TextField getCampoQuantDispEquipamento() {
        return campoQuantDispEquipamento;
    }
    
    
    
    
    
    
    
    
    
    
    

    
    //gets e sets retiradas 

    public TextField getCampoQuantidadeEquipamento() {
        return campoQuantidadeEquipamento;
    }

    public void setCampoQuantidadeEquipamento(TextField campoQuantidadeEquipamento) {
        this.campoQuantidadeEquipamento = campoQuantidadeEquipamento;
    }

    public Button getRegistrarRetirada() {
        return registrarRetirada;
    }

    public void setRegistrarRetirada(Button registrarRetirada) {
        this.registrarRetirada = registrarRetirada;
    }

    public ComboBox<String> getCampoComboPessoa() {
        return campoComboPessoa;
    }

    public void setCampoComboPessoa(ComboBox<String> campoComboPessoa) {
        this.campoComboPessoa = campoComboPessoa;
    }

    public ComboBox<String> getCampoComboEquipamento() {
        return campoComboEquipamento;
    }

    public void setCampoComboEquipamento(ComboBox<String> campoComboEquipamento) {
        this.campoComboEquipamento = campoComboEquipamento;
    }

    public ComboBox<ComboItem> getComboTipodeEquipamentoRetirada() {
        return comboTipodeEquipamentoRetirada;
    }

    public void setComboTipodeEquipamentoRetirada(ComboBox<ComboItem> comboTipodeEquipamentoRetirada) {
        this.comboTipodeEquipamentoRetirada = comboTipodeEquipamentoRetirada;
    }
    
    

    
    
    //get e sets devolução
    public Button getBntDevol() {
        return bntDevol;
    }

    public void setBntDevol(Button bntDevol) {
        this.bntDevol = bntDevol;
    }

    public TableColumn<DetalheRetirada, Boolean> getTabEntregueRetirada() {
        return tabEntregueRetirada;
    }

    public void setTabEntregueRetirada(TableColumn<DetalheRetirada, Boolean> tabEntregueRetirada) {
        this.tabEntregueRetirada = tabEntregueRetirada;
    }

    public TableColumn<DetalheRetirada, String> getTabelaNomePessRetirou() {
        return tabelaNomePessRetirou;
    }

    public void setTabelaNomePessRetirou(TableColumn<DetalheRetirada, String> tabelaNomePessRetirou) {
        this.tabelaNomePessRetirou = tabelaNomePessRetirou;
    }

    public TableColumn<DetalheRetirada, String> getTabEquipamentoRetirado() {
        return tabEquipamentoRetirado;
    }

    public void setTabEquipamentoRetirado(TableColumn<DetalheRetirada, String> tabEquipamentoRetirado) {
        this.tabEquipamentoRetirado = tabEquipamentoRetirado;
    }

    public TableColumn<DetalheRetirada, Integer> getTabQuantRetirada() {
        return tabQuantRetirada;
    }

    public void setTabQuantRetirada(TableColumn<DetalheRetirada, Integer> tabQuantRetirada) {
        this.tabQuantRetirada = tabQuantRetirada;
    }

    public TextField getCampoQuantidadedev() {
        return campoQuantidadedev;
    }

    public void setCampoQuantidadedev(TextField campoQuantidadedev) {
        this.campoQuantidadedev = campoQuantidadedev;
    }

    public TableColumn<DetalheRetirada, Integer> getTabIdRetiradas() {
        return tabIdRetiradas;
    }

    public void setTabIdRetiradas(TableColumn<DetalheRetirada, Integer> tabIdRetiradas) {
        this.tabIdRetiradas = tabIdRetiradas;
    }

    public TableColumn<DetalheRetirada, String> getTabDiaRetirada() {
        return tabDiaRetirada;
    }

    public void setTabDiaRetirada(TableColumn<DetalheRetirada, String> tabDiaRetirada) {
        this.tabDiaRetirada = tabDiaRetirada;
    }

    public TextField getCampoPesquisaDevolucao() {
        return CampoPesquisaDevolucao;
    }

    public void setCampoPesquisaDevolucao(TextField CampoPesquisaDevolucao) {
        this.CampoPesquisaDevolucao = CampoPesquisaDevolucao;
    }


    public TableView<DetalheRetirada> getTabelaRetiradas() {
        return TabelaRetiradas;
    }

    public void setTabelaRetiradas(TableView<DetalheRetirada> TabelaRetiradas) {
        this.TabelaRetiradas = TabelaRetiradas;
    }

    public TextField getCampoNomePessoaRetirada() {
        return campoNomePessoaRetirada;
    }

    public void setCampoNomePessoaRetirada(TextField campoNomePessoaRetirada) {
        this.campoNomePessoaRetirada = campoNomePessoaRetirada;
    }

    public TextField getCampoIEquipamentoRetirada() {
        return campoIEquipamentoRetirada;
    }

    public void setCampoIEquipamentoRetirada(TextField campoIEquipamentoRetirada) {
        this.campoIEquipamentoRetirada = campoIEquipamentoRetirada;
    }

    public TextField getCampoIdRetirada() {
        return campoIdRetirada;
    }
    
    //sem devolução

    public TableView<DetalhesRetiradasSemDevolucao> getSemDevTabelaRetirada() {
        return semDevTabelaRetirada;
    }

    public TableColumn<DetalhesRetiradasSemDevolucao, String> getSemDevPessoa() {
        return semDevPessoa;
    }

    public TextField getSemDevCampoPesquisa() {
        return semDevCampoPesquisa;
    }

    public TableColumn<DetalhesRetiradasSemDevolucao, Integer> getSemDevQuant() {
        return semDevQuant;
    }

    public TableColumn<DetalhesRetiradasSemDevolucao, String> getSemDevEqui() {
        return semDevEqui;
    }

    public TableColumn<DetalhesRetiradasSemDevolucao, Integer> getSemDevId() {
        return semDevId;
    }

    public TableColumn<DetalhesRetiradasSemDevolucao, String> getSemDevDia() {
        return semDevDia;
    }
 
}
