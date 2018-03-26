// This class acts as a model class,holding getters,setters and properties
package classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author admin
 */
public class AgendaDetalhes {

    private final StringProperty id;
    private final StringProperty local;
    private final StringProperty data;

    //Default constructor
    public AgendaDetalhes(String id, String local, String data) {
        this.id = new SimpleStringProperty(id);
        this.local = new SimpleStringProperty(local);
        this.data =  new SimpleStringProperty(data);

    }

    //Getters
    public String getId() {
        return id.get();
    }

    public String getLocal() {
        return local.get();
    }
    
    public String getData() {
        return data.get();
    }

 

    //Setters
    public void setId(String value) {
        id.set(value);
    }

    public void setLocal(String value) {
        local.set(value);
    }
    
    public void setData(String value) {
        data.set(value);
    }
  

    //Property values
    public StringProperty idProperty() {
        return id;
    }

    public StringProperty localProperty() {
        return local;
    }
    
    public StringProperty dataProperty() {
        return data;
    }

}
