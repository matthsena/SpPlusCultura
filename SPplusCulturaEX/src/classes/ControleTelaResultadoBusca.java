package classes;

import static classes.ControleTelaResultadoBusca.sql;
import java.io.IOException;
import java.sql.Connection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ControleTelaResultadoBusca implements Initializable {

    public static String sql;
    public static String selecionado;
    private ObservableList<LocalDetalhes> data;
    private DBConnect db; 
    @FXML private Button btnSelecionar;
    @FXML private Button btnVoltar;
    @FXML private TableView <LocalDetalhes> tableview;
    @FXML private TableColumn<LocalDetalhes, String> columnID;
    @FXML private TableColumn<LocalDetalhes, String> columnNome;  
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        db = new DBConnect();
        try {
            this.ControleTelaResultadoBusca();
        } catch (SQLException ex) {
            Logger.getLogger(ControleTelaResultadoBusca.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void ControleTelaResultadoBusca() throws SQLException {
  
        sql = ControleTelaBuscar.sql;     
         try {
             Connection c = db.connect();
             data = FXCollections.observableArrayList();        
             ResultSet rs = c.createStatement().executeQuery(sql);
            while (rs.next()) {
                data.add(new LocalDetalhes(rs.getString(1), rs.getString(2)));
            }
        } catch (SQLException ex) {
            System.err.println("Error"+ex);
        }
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNome.setCellValueFactory(new PropertyValueFactory<>("local"));
        tableview.setItems(null);
        tableview.setItems(data);
            tableview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
           
                @Override
                public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {      
                    if(tableview.getSelectionModel().getSelectedItem() != null) 
                    {                                           
                        TableViewSelectionModel selectionModel = tableview.getSelectionModel();
                        ObservableList selectedCells = selectionModel.getSelectedCells();
                        TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                        Object val = tablePosition.getTableColumn().getCellData(newValue);
                        selecionado =  val.toString();                               
                    }
                }
            });

     btnSelecionar.setOnAction((ActionEvent e)->{
         
         ControleTelaLocal.sql = selecionado;
         Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();               
         stage.close();                         
           
         try {  
             Parent nextPage = FXMLLoader.load(getClass().getResource("/telas/TelaLocal.fxml")); 
             Scene nextPageScene = new Scene(nextPage);  
             Stage stage1 = new Stage();   
             stage1.initStyle(StageStyle.TRANSPARENT);  
             stage1.setResizable(false);
             stage1.setScene(nextPageScene); 
             stage1.show();
         }   
         catch (IOException ex) {           
             Logger.getLogger(ControleTelaInicial.class.getName()).log(Level.SEVERE, null, ex);    
         }        
     });
     btnVoltar.setOnAction((ActionEvent e)->{
             
         Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();        
         stage.close();
     });      
    }    
}