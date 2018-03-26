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
import javafx.scene.control.Button;
import javafx.scene.control.TablePosition;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ControleTelaFavoritos implements Initializable {
    
    public static String selecionado;
    private ObservableList<LocalDetalhes> data;
    // Classe conexão
    private DBConnect db;   
    Connection c;

    @FXML private Button btnExcluir;
    @FXML private Button btnVoltar;
    @FXML private Button btnSelecionar;
    @FXML private TableView <LocalDetalhes> tableview;
    @FXML private TableColumn<LocalDetalhes, String> columnID;
    @FXML private TableColumn<LocalDetalhes, String> columnNome;  
    // Método inicializador
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Instanciando a classe de conexão
        db = new DBConnect();
        // Tente pegar os comandos do método construtor
        try {
            this.ControleTelaFavoritos();
        } catch (SQLException ex) {
            Logger.getLogger(ControleTelaResultadoBusca.class.getName()).log(Level.SEVERE, null, ex);
        }// Fim do catch erro na estrutura sql
    }// Fim do método inicializador   
    // Método construtor
    public void ControleTelaFavoritos() throws SQLException {

         // Tente         
         try {
           // Pegando 
           c = db.connect();
            data = FXCollections.observableArrayList();
            // Executa a query e armazena os resultados no result set         
             ResultSet rs = c.createStatement().executeQuery("SELECT tb_locais.idLocal, tb_locais.nomeLocal FROM tb_favoritos, tb_locais WHERE tb_favoritos.userUsuario = '"+ControleTelaInicial.user+"' AND tb_favoritos.idLocal = tb_locais.idLocal;");
            while (rs.next()) {
                //get string from db,whichever way 
                data.add(new LocalDetalhes(rs.getString(1), rs.getString(2)));
            }// Fim do try
        } catch (SQLException ex) {
            System.err.println("Error"+ex);
        }// Fim do catch
        // Seta os valores nas colunas
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNome.setCellValueFactory(new PropertyValueFactory<>("local"));
        // Limpa a tableview
        tableview.setItems(null);
        // Adiciona os valores no tableview
        tableview.setItems(data);   
        
          // Evento para pegar a linha selecionada
            tableview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
           
                @Override
                public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {      
                //Checa se realmente há algum item selecionado  
                    if(tableview.getSelectionModel().getSelectedItem() != null) 
                    {                          
                        // Caso tiver vai pegar o valor da célula selecionada                     
                        TableView.TableViewSelectionModel selectionModel = tableview.getSelectionModel();
                        ObservableList selectedCells = selectionModel.getSelectedCells();
                        TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                        Object val = tablePosition.getTableColumn().getCellData(newValue);
                        // Joga esse valor numa váriavel
                        selecionado =  val.toString();       
                        ControleTelaLocal.sql = selecionado;
                    }// Fim do if      
                }// Fim do método changed   
            });// Fim da ação na table view    
            
        btnVoltar.setOnAction((ActionEvent e)->{
            
            // Pega a janela atual e define como uma variável de tipo Stage            
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();                           
            // Fecha a janela atual           
            stage.close();
        });
        // Botão selecionar
        btnSelecionar.setOnAction((ActionEvent e)->{
            
            // Pega a janela atual e define como uma variável de tipo Stage            
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();                           
            // Fecha a janela atual           
            stage.close();
            
             try {                
               // Carregar uma nova página vinda de um FXML
               Parent nextPage = FXMLLoader.load(getClass().getResource("/telas/TelaLocal.fxml")); 
               // Aplicando os detalhes antes dessa tela ser exibida
               Scene nextPageScene = new Scene(nextPage);                                                  
               Stage stage1 = new Stage();   
               stage1.initStyle(StageStyle.TRANSPARENT);            
               stage1.setResizable(false);
               stage1.setScene(nextPageScene);   
               // Exibe a tela gerada apartir de um FXML
               stage1.show();
            }// Fim do try                   
            // Caso der erro para carregar a nova página                  
            catch (IOException ex) {                                                                        
                Logger.getLogger(ControleTelaInicial.class.getName()).log(Level.SEVERE, null, ex);                                                                           
            }// Fim do catch                  
        });
        
        btnExcluir.setOnAction((ActionEvent e)->{
            
            Alert alert = new Alert(Alert.AlertType.WARNING);                                                                                                                      
            alert.setHeaderText(null);                                                                                   
            alert.initStyle(StageStyle.UTILITY);                                                                                                 
            alert.setResizable(false);                                                                                      
            alert.setTitle("***AVISO***");                                                  
            alert.setContentText("Você realmente deseja excluir o local da sua lista de favoritos?");                                                                
            Optional <ButtonType> action = alert.showAndWait();
            
            // Caso o usuário clicke em Ok                     
            if(action.get() == ButtonType.OK){                                   
            
                try {
                 // Pegando a conexão da classe DBConnect
                 c = db.connect();
                 // Query do SQL
                 // Criando o Statement
                 Statement stmt =  c.createStatement();
                 // Executando a query com o Statement
                 stmt.executeUpdate("DELETE FROM tb_favoritos WHERE tb_favoritos.idLocal = "+selecionado+";");
                 c.close();
                 
                 // Pega a janela atual e define como uma variável de tipo Stage                      
                 Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();                           
                 // Fecha a janela atual           
                 stage.close();
                 
                 try {          
                     Parent nextPage = FXMLLoader.load(getClass().getResource("/telas/TelaFavoritos.fxml"));     
                     Scene nextPageScene = new Scene(nextPage);    
                     Stage stage1 = new Stage();   
                     stage1.initStyle(StageStyle.TRANSPARENT);  
                     stage1.setResizable(false);
                     stage1.setScene(nextPageScene);          
                     // Exibe a tela gerada apartir de um FXML              
                     stage1.show();          
                 }// Fim do try                   
            // Caso der erro para carregar a nova página                  
            catch (IOException ex) {                                                                        
                Logger.getLogger(ControleTelaInicial.class.getName()).log(Level.SEVERE, null, ex);                                                                           
            }// Fim do catch                  
             } catch (SQLException ex) {
                 Logger.getLogger(ControleTelaFavoritos.class.getName()).log(Level.SEVERE, null, ex);
             }                 
            }// Fim do if botão ok             
        });// Fim do botão excluir
    }// Fim do método           
}// Fim da classe