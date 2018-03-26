package classes;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ControleTelaLocal implements Initializable {
      //getters e setters da classe LocalDetalhes
    private ObservableList<EventoDetalhes> data;
    @FXML private TableView <EventoDetalhes> tableview;
    @FXML private TableColumn<EventoDetalhes, String> columnEvento;
    @FXML private TableColumn<EventoDetalhes, String> columnData; 
    @FXML private TableColumn<EventoDetalhes, String> ColumnId;
    // Variaveis
    Connection c;
    public static String sql;   
    // Classe conexão
    private DBConnect db;
    public static String nome, endereco, contato, disp, periodo,desc, selecionado;
    // Criação de objetos
    @FXML Button btnSair;
    @FXML Button btnRelatorio;
    @FXML Button btnAddEvento;
    @FXML Button btnAddFavorito;
    @FXML Label lblNomeLocal;
    @FXML Label lblEndereco;
    @FXML Label lblContato;
    @FXML Label lblDisp;
    @FXML Label lblPeriodo;
    @FXML TextArea txtArea;
    @FXML Label lblTitulo;
    // Método inicializador
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Chama o método construtor
        this.ControleTelaLocal();
    }    
    // Método construtor
    public void ControleTelaLocal() {
        //sql = ControleTelaResultadoBusca.selecionado;
        // Instanciando a classe conexão
        db = new DBConnect();
        try {     
            // Irá pegar a conexão da classe DBConnect
            c = db.connect();
            // Query SQL a ser executada
            String query = "SELECT * FROM tb_locais WHERE idLocal = "+sql+";";
            // Criando um statement e ligando a conexão
            Statement stmt =  c.createStatement();
            // Gerando um Result Set
            ResultSet rs = stmt.executeQuery(query);
            // Caso consiga selecionar
            if (rs.next()) {
                nome = rs.getString("nomeLocal");
                endereco = rs.getString("endLocal");
                contato = rs.getString("contatoLocal");
                disp = rs.getString("dispLocal");
                periodo = rs.getString("periodoLocal");
                desc =  rs.getString("descLocal");
                    
            }// Fim do if
            lblNomeLocal.setText(nome);
            lblEndereco.setText(lblEndereco.getText()+": "+endereco);
            lblContato.setText(lblContato.getText()+": "+contato);
            lblDisp.setText(lblDisp.getText()+": "+disp);
            lblPeriodo.setText(lblPeriodo.getText()+": "+periodo);
            txtArea.setText(desc);
        } catch (SQLException ex) {
            Logger.getLogger(ControleTelaLocal.class.getName()).log(Level.SEVERE, null, ex);
        }// Fim do cathc                 
        // Tente         
        try {        
            // Pegando          
            c = db.connect();            
            data = FXCollections.observableArrayList();
            // Executa a query e armazena os resultados no result set                    
            ResultSet rs = c.createStatement().executeQuery("SELECT idEvento, nomeEvento, diaEvento from tb_eventos WHERE idLocal = '"+sql+"';");
            while (rs.next()) {
                //get string from db,whichever way 
                data.add(new EventoDetalhes(rs.getString(2), rs.getString(3), rs.getString(1)));
            }// Fim do try
        } catch (SQLException ex) {
            System.err.println("Error"+ex);        
        }// Fim do catch
        // Seta os valores nas colunas        
        columnEvento.setCellValueFactory(new PropertyValueFactory<>("evento"));
        columnData.setCellValueFactory(new PropertyValueFactory<>("data"));
        ColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
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
                    }// Fim do if        
                }// Fim do método changed  
            });// Fim da ação na table view
        
        btnAddEvento.setOnAction((ActionEvent e)->{
            
            try {
                 // Pegando a conexão da classe DBConnect
                 c = db.connect();
                 // Query do SQL
                 // Criando o Statement
                 Statement stmt =  c.createStatement();
                 // Executando a query com o Statement
                 stmt.executeUpdate("INSERT INTO tb_agenda(idEvento, usuarioEvento) VALUES ("+selecionado+",'"+ControleTelaInicial.user+"');");
                 c.close();
             } catch (SQLException ex) {
                 Logger.getLogger(ControleTelaFavoritos.class.getName()).log(Level.SEVERE, null, ex);
             }                 
            
        });
        
         // Botão para gerar relatório
        
        btnRelatorio.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ActionEvent) {
                //criamos um documento vazio
                Document documentoPDF = new Document();
                try{  
                    //cria uma instancia do documento e da o nome do pdf
                    PdfWriter.getInstance(documentoPDF, new FileOutputStream("I:\\"+nome+".pdf"));                   
                    //abrir o documento
                    documentoPDF.open();       
                    documentoPDF.newPage();
                    //setar o tamnho da página
                    documentoPDF.setPageSize(PageSize.A4);   
                    Font f = new Font(Font.FontFamily.HELVETICA,50.0f,Font.BOLD,BaseColor.RED);
                    Font g = new Font(Font.FontFamily.HELVETICA,18.0f,Font.NORMAL,BaseColor.BLACK);
                    documentoPDF.add(new Phrase("\n Relatório do local \n", f));                 
                    documentoPDF.add(new Phrase(" \n \n Nome: "+nome +"\n Endereço: "+endereco+"\n Contato: "+contato+"\n Disponibilidade: "+disp+"\n Periodo: "+periodo, g));                    
                    documentoPDF.close();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);                                                                                                            
                    alert.setHeaderText(null);                                                                          
                    alert.initStyle(StageStyle.UTILITY);                                                                                        
                    alert.setResizable(false);                                                                                
                    alert.setTitle("***AVISO***");                                                           
                    alert.setContentText("Relatório gerado com sucesso!!!");                                                      
                    // Mostra uma caixa de diálogo mas a tela home continua ao fundo
                    Optional <ButtonType> action = alert.showAndWait();
                }catch(DocumentException | IOException de){
                    System.out.print("erro");
                }
            }
        });// Fim do botão
        
        btnSair.setOnAction((ActionEvent e)->{
            
            // Pega a janela atual e define como uma variável de tipo Stage                     
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            // Fecha a janela atual         
            stage.close();
        });
        btnAddFavorito.setOnAction((ActionEvent e)->{
                
                 // Tentativa de alterar os dados
             try{                                                  
                 // Pegando a conexão da classe DBConnect                                                 
                 c = db.connect();                                                                           
                 // Query do SQL             
                 String query = "INSERT INTO tb_favoritos(idLocal, userUsuario) values ("+ControleTelaResultadoBusca.selecionado+", '"+ControleTelaInicial.user+"');";                                              
                 // Criando o Statement                       
                 Statement stmt =  c.createStatement();                   
                 // Executando a query com o Statement                    
                 stmt.executeUpdate(query);  
                 // Fecha conexao
                 c.close();   
                 // Caixa de diálogo
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);                                                                                                            
                 alert.setHeaderText(null);                                                                          
                 alert.initStyle(StageStyle.UTILITY);                                                                                        
                 alert.setResizable(false);                                                                                
                 alert.setTitle("***AVISO***");                                                           
                 alert.setContentText("Local adicionado aos favoritos com sucesso!!!");                                                      
                 // Mostra uma caixa de diálogo mas a tela home continua ao fundo
                 Optional <ButtonType> action = alert.showAndWait();
             }// Fim do try             
             // Caso tenha algum erro SQL a                                  
             catch(SQLException se){	                                      
                 // Caixa de diálogo
                 Alert alert = new Alert(Alert.AlertType.WARNING);                                                                                                            
                 alert.setHeaderText(null);                                                                          
                 alert.initStyle(StageStyle.UTILITY);                                                                                        
                 alert.setResizable(false);                                                                                
                 alert.setTitle("***AVISO***");                                                           
                 alert.setContentText("Falha com o banco de dados!!!");                                                      
                 // Mostra uma caixa de diálogo mas a tela home continua ao fundo
                 Optional <ButtonType> action = alert.showAndWait();                  
             }// Fim do catch              
            });
    }// Fim do método construtor 
}// Fim da classe