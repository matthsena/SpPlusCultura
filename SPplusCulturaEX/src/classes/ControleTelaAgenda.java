package classes;

import java.io.IOException;
import java.sql.Connection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TablePosition;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ControleTelaAgenda implements Initializable {
   
    public static String selecionado;
    private ObservableList<AgendaDetalhes> data;
    private DBConnect db;   
    Connection c;
    @FXML private Button btnExcluir;
    @FXML private Button btnVoltar;
    @FXML private TableView <AgendaDetalhes> tableview;
    @FXML private TableColumn<AgendaDetalhes, String> columnID;
    @FXML private TableColumn<AgendaDetalhes, String> columnNome;  
    @FXML private TableColumn<AgendaDetalhes, String> columnData;  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        db = new DBConnect();
        try {
            this.ControleTelaAgenda();
        } catch (SQLException ex) {
            Logger.getLogger(ControleTelaResultadoBusca.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void ControleTelaAgenda() throws SQLException {
                
         
        try {
            c = db.connect();
            data = FXCollections.observableArrayList();                   
            ResultSet rs = c.createStatement().executeQuery("SELECT tb_agenda.idAgenda, tb_eventos.nomeEvento, tb_eventos.diaEvento FROM tb_agenda, tb_eventos WHERE tb_agenda.usuarioEvento = '"+ControleTelaInicial.user+"';");
            
            while (rs.next()) {
                data.add(new AgendaDetalhes(rs.getString(1), rs.getString(2), rs.getString(3)));
            }      
        } catch (SQLException ex) {
            System.err.println("Error"+ex);
        }
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNome.setCellValueFactory(new PropertyValueFactory<>("local"));
        columnData.setCellValueFactory(new PropertyValueFactory<>("data"));
        tableview.setItems(null);
        tableview.setItems(data); 
            tableview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
           
                @Override
                public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {  
                    if(tableview.getSelectionModel().getSelectedItem() != null) 
                    {                                     
                        TableView.TableViewSelectionModel selectionModel = tableview.getSelectionModel();
                        ObservableList selectedCells = selectionModel.getSelectedCells();
                        TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                        Object val = tablePosition.getTableColumn().getCellData(newValue);
                        selecionado =  val.toString();                               
                    }         
                }
            });
        
        btnVoltar.setOnAction((ActionEvent e)->{
                      
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();         
            stage.close();
        });
        btnExcluir.setOnAction((ActionEvent e)->{
            
            Alert alert = new Alert(Alert.AlertType.WARNING);                                                                                                                      
            alert.setHeaderText(null);                                                                                   
            alert.initStyle(StageStyle.UTILITY);                                                                                                 
            alert.setResizable(false);                                                                                      
            alert.setTitle("***AVISO***");                                                  
            alert.setContentText("Você realmente deseja excluir esse evento de sua agenda?");                                                                
            Optional <ButtonType> action = alert.showAndWait();
            
            // Caso o usuário clicke em Ok                     
            if(action.get() == ButtonType.OK){                                   
            
                try {              
                    c = db.connect();               
                    Statement stmt =  c.createStatement();              
                    stmt.executeUpdate("DELETE FROM tb_agenda WHERE idAgenda = "+selecionado+";");                
                    c.close();                                      
                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();                                                      
                    stage.close(); 
                    
                    try {                         
                        Parent nextPage = FXMLLoader.load(getClass().getResource("/telas/TelaAgenda.fxml"));                       
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
                } catch (SQLException ex) {             
                    Logger.getLogger(ControleTelaFavoritos.class.getName()).log(Level.SEVERE, null, ex);           
                } 
            }
        });
    }          
}