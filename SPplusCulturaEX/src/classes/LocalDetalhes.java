// This class acts as a model class,holding getters,setters and properties
package classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author admin
 */
public class LocalDetalhes {

    private final StringProperty id;
    private final StringProperty local;


    //Default constructor
    public LocalDetalhes(String id, String local) {
        this.id = new SimpleStringProperty(id);
        this.local = new SimpleStringProperty(local);

    }

    //Getters
    public String getId() {
        return id.get();
    }

    public String getLocal() {
        return local.get();
    }

 

    //Setters
    public void setId(String value) {
        id.set(value);
    }

    public void setLocal(String value) {
        local.set(value);
    }

  

    //Property values
    public StringProperty idProperty() {
        return id;
    }

    public StringProperty localProperty() {
        return local;
    }

}
