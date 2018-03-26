// This class acts as a model class,holding getters,setters and properties
package classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EventoDetalhes {

    private final StringProperty evento;
    private final StringProperty data;
    private final StringProperty id;


    //Default constructor
    public EventoDetalhes(String evento, String data, String id) {
        this.evento = new SimpleStringProperty(evento);
        this.data = new SimpleStringProperty(data);
        this.id = new SimpleStringProperty(id);

    }

    //Getters
    public String getEvento() {
        return evento.get();
    }

    public String getData() {
        return data.get();
    }
    
    public String getId() {
        return id.get();
    }

 

    //Setters
    public void setEvento(String value) {
        evento.set(value);
    }

    public void setData(String value) {
        data.set(value);
    }
    
    public void setId(String value) {
        id.set(value);
    }

  

    //Property values
    public StringProperty eventoProperty() {
        return evento;
    }

    public StringProperty dataProperty() {
        return data;
    }
    
    public StringProperty idProperty() {
        return id;
    }
}