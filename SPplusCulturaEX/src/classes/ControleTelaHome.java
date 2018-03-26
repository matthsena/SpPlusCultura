package classes;
// Importações do Java
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
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.swing.JOptionPane;

// Inicio da classe
public class ControleTelaHome implements Initializable {

    // Variaveis
    Connection c;
    String usuario;
    
    // Instanciação da classe de conexão
    private DBConnect db = new DBConnect();
    
    // Botões criados no FXML
    @FXML private Button btnOpções;
    @FXML private Button btnSair;
    @FXML private Button btnBuscar;
    @FXML private Button btnAlterarDados;
    @FXML private Button btnAlterarSenha;
    @FXML private Button btnOcultar;
    @FXML private Button btnLogout;
    @FXML private Button btnReclamacoes;
    @FXML private Button btnFavoritos;
    @FXML private Button btnAgenda;
    // Texts Fields
    @FXML private TextField txtNome;
    @FXML private TextField txtUsuario;
    @FXML private TextField txtEmail;
    
     // Anchor Pane opções
    @FXML private AnchorPane anchorPaneDireito;
    
    // Metodo construtor
    public void ControleTelaHome() {
        
        // Translação para chamar o AnchorPane
        TranslateTransition acaoAparecer = new TranslateTransition(new Duration(350), anchorPaneDireito); 
        // Translação para esconder o AnchorPane
        TranslateTransition acaoEsconder = new TranslateTransition(new Duration(350), anchorPaneDireito);                 
        acaoEsconder.setToX(0);   
        // Usuário vai ser o mesmo que entrou com sucesso na tela home
        usuario =  ControleTelaInicial.user;
         // botão configurações
        btnOpções.setOnAction((ActionEvent e)->{
            // Tente
            try {                    
                // Irá pegar a conexão da classe DBConnect                   
                c = db.connect();
                // Query SQL a ser executada                
                String query = "SELECT nomeUsuario,userUsuario,emailUsuario FROM tb_usuario WHERE userUsuario = '"+usuario+"';";                                        
                // Criando um statement e ligando a conexão                
                Statement stmt =  c.createStatement();                         
                // Gerando um Result Set                 
                ResultSet rs = stmt.executeQuery(query);                                          
                // Caso consiga selecionar                                    
                if (rs.next()) { 
                    // Vai adicionar os valores pegos no banco ao TextField
                    txtNome.setText(rs.getString("nomeUsuario"));
                    txtEmail.setText(rs.getString("emailUsuario"));
                    txtUsuario.setText(rs.getString("userUsuario"));           
                }               
            }// Fim do try                                 
            // Caso der um erro com o SQL            
            catch(SQLException se){		                     
            
                JOptionPane.showMessageDialog(null,"Erro"+se);	                             
            }  
           if(anchorPaneDireito.getTranslateX()== 0){
              acaoAparecer.setToX(-(anchorPaneDireito.getWidth()));
              acaoAparecer.play();
            }         
        });// Fim do catch
        
        // Ação do botão ocultar
        btnOcultar.setOnAction((ActionEvent e)->{                      
            // Dando o foco para o botão ocultar
            btnOcultar.requestFocus();
            acaoEsconder.play();
        });// Fim do botão ocultar  
        // Botão logout
        btnLogout.setOnAction((ActionEvent e)->{                      
            
            // Mensagem para confirmação
             Alert alert = new Alert(Alert.AlertType.CONFIRMATION);           
             alert.setHeaderText(null);                
             alert.initStyle(StageStyle.UTILITY);       
             alert.setResizable(false);            
             alert.setTitle("***AVISO***");         
             alert.setContentText("Realmente deseja fazer logout?");          
             Optional <ButtonType> action = alert.showAndWait();         
             if(action.get() == ButtonType.OK){        
                 // Caso confirme a ação voltará para tela inicial
                 try {                         
                    // Carragar esse fxml como a página          
                     Parent nextPage = FXMLLoader.load(getClass().getResource("/telas/TelaInicial.fxml"));                                                                             
                     Scene nextPageScene = new Scene(nextPage); 
                     // Pega as caracteristicas da pagina atual e utiliza elas -- EX: página sem borda padrão do sistema operacional 
                     Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();                                                                                
                     stage.setScene(nextPageScene); 
                     // Exibir a página
                     stage.show();                                                                                                                       
                 }// Fim do try 
                        
                 // Caso der erro para carregar a nova página                         
                 catch (IOException ex) {                                                      
                         
                     Logger.getLogger(ControleTelaInicial.class.getName()).log(Level.SEVERE, null, ex);                                                                    
                 }// Fim do catch    
             }// Fim do if confirmar a operação        
        });// Fim do botão logout 
        // Botão alterar dados 
        btnAlterarDados.setOnAction((ActionEvent e)->{                      
            
            // Tentativa de alterar os dados
             try{                                                  
                 // Pegando a conexão da classe DBConnect                                                 
                 c = db.connect();                                                                           
                 // Query do SQL             
                 String query = "UPDATE tb_usuario SET nomeUsuario = '"+txtNome.getText()+"', emailUsuario = '"+txtEmail.getText()
                         +"' WHERE userUsuario = '"+txtUsuario.getText()+"';";                                              
                 // Criando o Statement                       
                 Statement stmt =  c.createStatement();                   
                 // Executando a query com o Statement                    
                 stmt.executeUpdate(query);  
                 // Fecha conexao
                 c.close();                          
                 // Mensagem de sucesso para a alteração de registro                                          
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);                                                                                                               
                 alert.setHeaderText(null);                                                                               
                 alert.initStyle(StageStyle.UTILITY);                                                                                         
                 alert.setResizable(false);                                                                         
                 alert.setTitle("***AVISO***");                                                                       
                 alert.setContentText("Sucesso ao mudar os dados do Usuário!!!");                                                     
                 // Mostra uma caixa de diálogo mas a tela home continua ao fundo
                 Optional <ButtonType> action = alert.showAndWait();                                           
             }// Fim do try             
             // Caso tenha algum erro SQL de duplicação de chave primaria                                  
             catch(SQLException se){	                                      
                 // Caixa de diálogo
                 Alert alert = new Alert(Alert.AlertType.WARNING);                                                                                                            
                 alert.setHeaderText(null);                                                                          
                 alert.initStyle(StageStyle.UTILITY);                                                                                        
                 alert.setResizable(false);                                                                                
                 alert.setTitle("***AVISO***");                                                           
                 alert.setContentText("Falha ao mudar os dados do Usuário!!!");                                                      
                 // Mostra uma caixa de diálogo mas a tela home continua ao fundo
                 Optional <ButtonType> action = alert.showAndWait(); 	                                        
             }// Fim do catch                   
        });// Fim do botão Alterar dados 
        // Buscar
        btnBuscar.setOnAction((ActionEvent e)->{
            
            // Tentar ir para a nova página                            
            try {  
               // Carrega a tela apartir de um fxml
               Parent nextPage = FXMLLoader.load(getClass().getResource("/telas/TelaBuscar.fxml"));                                                 
               Scene nextPageScene = new Scene(nextPage);                                                  
               Stage stage = new Stage();   
               stage.initStyle(StageStyle.UTILITY);
               stage.setTitle("Buscar");              
               stage.setResizable(false);
               stage.setScene(nextPageScene);
               // Vai mostrar em primeiro plano mas irá continuar mostrando a tela home no fundo
               stage.showAndWait();
            }                   
            // Caso der erro para carregar a nova página                  
            catch (IOException ex) {                                                                        
                Logger.getLogger(ControleTelaInicial.class.getName()).log(Level.SEVERE, null, ex);                                                                 
            }
        });// Fim do botão buscar
     
        // Botão alterar senha
        btnAlterarSenha.setOnAction((ActionEvent e)->{
            // Tentar ir para a nova página                            
            try {  
               // Carrega a tela apartir de um fxml
               Parent nextPage = FXMLLoader.load(getClass().getResource("/telas/TelaAlterarSenha.fxml"));                                                 
               Scene nextPageScene = new Scene(nextPage);                                                  
               Stage stage = new Stage();   
               stage.initStyle(StageStyle.UTILITY);
               stage.setTitle("Buscar");              
               stage.setResizable(false);
               stage.setScene(nextPageScene);
               // Vai mostrar em primeiro plano mas irá continuar mostrando a tela home no fundo
               stage.showAndWait();
            }                   
            // Caso der erro para carregar a nova página                  
            catch (IOException ex) {                                                                        
                Logger.getLogger(ControleTelaInicial.class.getName()).log(Level.SEVERE, null, ex);                                                                 
            }
            
        });// Fim do botão alterar senha
        
        //Botão reclamaçoes
        btnReclamacoes.setOnAction((ActionEvent e)->{
            
            // Abrir a tela reclamações
            try {  
               // Carrega a tela apartir de um fxml
               Parent nextPage = FXMLLoader.load(getClass().getResource("/telas/TelaReclamacoes.fxml"));                                                 
               Scene nextPageScene = new Scene(nextPage);                                                  
               Stage stage = new Stage();   
               stage.initStyle(StageStyle.UTILITY);
               stage.setTitle("Reclamações");              
               stage.setResizable(false);
               stage.setScene(nextPageScene);
               // Vai mostrar em primeiro plano mas irá continuar mostrando a tela home no fundo
               stage.showAndWait();
            }                   
            // Caso der erro para carregar a nova página                  
            catch (IOException ex) {                                                                        
                Logger.getLogger(ControleTelaInicial.class.getName()).log(Level.SEVERE, null, ex);                                                                 
            }
        });
        
        //Botão favoritos
        btnFavoritos.setOnAction((ActionEvent e)->{
            try {                
               // Carregar uma nova página vinda de um FXML
               Parent nextPage = FXMLLoader.load(getClass().getResource("/telas/TelaFavoritos.fxml")); 
               // Aplicando os detalhes antes dessa tela ser exibida
               Scene nextPageScene = new Scene(nextPage);                                                  
               Stage stage1 = new Stage();   
               stage1.initStyle(StageStyle.TRANSPARENT);            
               stage1.setResizable(false);
               stage1.setScene(nextPageScene);   
               // Exibe a tela gerada apartir de um FXML
               stage1.show();
            }                   
            // Caso der erro para carregar a nova página                  
            catch (IOException ex) {                                                                        
                Logger.getLogger(ControleTelaInicial.class.getName()).log(Level.SEVERE, null, ex);                                                                           
            }           
        });
        //Botão btnAgenda
        btnAgenda.setOnAction((ActionEvent e)->{
            try {                
               // Carregar uma nova página vinda de um FXML
               Parent nextPage = FXMLLoader.load(getClass().getResource("/telas/TelaAgenda.fxml")); 
               // Aplicando os detalhes antes dessa tela ser exibida
               Scene nextPageScene = new Scene(nextPage);                                                  
               Stage stage1 = new Stage();   
               stage1.initStyle(StageStyle.TRANSPARENT);            
               stage1.setResizable(false);
               stage1.setScene(nextPageScene);   
               // Exibe a tela gerada apartir de um FXML
               stage1.show();
            }                   
            // Caso der erro para carregar a nova página                  
            catch (IOException ex) {                                                                        
                Logger.getLogger(ControleTelaInicial.class.getName()).log(Level.SEVERE, null, ex);                                                                           
            }           
        });
        //Botão Sair
        btnSair.setOnAction((ActionEvent e)->{
                                      
            // Caixa de diálogo
             Alert alert = new Alert(Alert.AlertType.CONFIRMATION);           
             alert.setHeaderText(null);                
             alert.initStyle(StageStyle.UTILITY);       
             alert.setResizable(false);            
             alert.setTitle("***AVISO***");         
             alert.setContentText("Ao pressionar 'OK' você ira encerrar a aplicação... Realmente deseja fazer isso? Perder a "         
                     + "oportunidade de conhecer novos horizontes, culturas e povos do grande aglomerado de tribos que é nossa "         
                     + "querida e amável São Paulo!!!");          
             Optional <ButtonType> action = alert.showAndWait();  
             // Caso aperte o botão OK do diálogo
             if(action.get() == ButtonType.OK){        
                 // Sair do sistema
                 System.exit(0);      
             } 
        });// Fim do botão sair
    }// Fim do método construtor    
    // Método de inicialização
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Chama o método construtor
        this.ControleTelaHome();
    }// Fim do método de inicialização         
}