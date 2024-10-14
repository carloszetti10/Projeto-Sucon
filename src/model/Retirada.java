package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Carlos Zetti
 */
public class Retirada extends RetiradaSemDevolucao{
    private BooleanProperty entregue;
    
        // Construtor que usa o construtor da classe pai com idRetirada
    public Retirada(int idRetirada, int idPessoa, int idEquipamento, int quantidadeEquipamento, String diaRetirada, Boolean entregue) {
        super(idRetirada, idPessoa, idEquipamento, quantidadeEquipamento, diaRetirada);
        this.entregue = new SimpleBooleanProperty(entregue);
    }

    // Construtor que usa o construtor da classe pai sem idRetirada
    public Retirada(int idPessoa, int idEquipamento, int quantidadeEquipamento) {
        super(idPessoa, idEquipamento, quantidadeEquipamento);
        this.entregue = new SimpleBooleanProperty(false); // Valor padrão para entregue
    }

    public boolean isEntregue() {
        return entregue.get();
    }

    public void setEntregue(boolean entregue) {
        this.entregue.set(entregue);
    }

    @Override
    public String toString() {
        return "Retirada{" + "entregue=" + entregue + '}';
    }

    

}
