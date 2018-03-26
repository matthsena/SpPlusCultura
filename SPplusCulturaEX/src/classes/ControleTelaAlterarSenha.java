package classes;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ControleTelaAlterarSenha implements Initializable{
   
    String query;
    String senha;
    String usuario;
    // Variavel de conexao
    Connection c;
    // Pega os objetos PasswordField Criados no FXML
    @FXML PasswordField txtSenhaAntiga;
    @FXML PasswordField txtSenhaNova;
    @FXML PasswordField txtConfirmaSenha;
    // Pega os objetos Button Criados no FXML
    @FXML Button btnConfirmar;
    // Instancia a classe conexao
    DBConnect db = new DBConnect();
    // Método Contrutor
    public void ControleTelaAlterarSenha() {
        // Botão confirmar
        btnConfirmar.setOnAction((ActionEvent e)->{
            senha = txtSenhaNova.getText();
            usuario = ControleTelaInicial.user;
            // Caso as senhas se confirmem       
            if(senha.equals(txtConfirmaSenha.getText())){     
                // tentar confirmar se a senha antiga era a digitada
                //tente              
                try{                
                    // query do sql                 
                    query = "SELECT * FROM tb_usuario WHERE userUsuario = '"+usuario+"' AND senhaUsuario = '"+txtSenhaAntiga.getText()+"';";                
                    c = db.connect();              
                    Statement stmt =  c.createStatement();  
                    ResultSet rs = stmt.executeQuery(query);
                    // Caso consiga selecionar
                    if(rs.next()){
                         // Tente      
               
                        
                         try {              
                            
                             c = db.connect();
                             // Query do SQL             
                             query = "UPDATE tb_usuario SET senhaUsuario = '"+txtSenhaNova.getText()+"' WHERE userUsuario = '"+usuario+"' AND senhaUsuario = '"+txtSenhaAntiga.getText()+"';";                                              
                             // Criando o Statement                       
                             //Statement stmt =  c.createStatement();                   
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
                             alert.setContentText("Sucesso ao mudar a senha do Usuário!!!");                                                     
                             // Mostra uma caixa de diálogo mas a tela home continua ao fundo
                             Optional <ButtonType> action = alert.showAndWait();  
                             // Caso o usuário clicke em Ok
                             if(action.get() == ButtonType.OK){        
                                // Irá pegar a tela atual                                
                                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();  
                                // Irá focar nessa tela
                                stage.requestFocus();
                            }// fim do if   
                         }// Fim do try       
                         // Caso de erro no sql       
                         catch (SQLException ex) {
                             Logger.getLogger(ControleTelaAlterarSenha.class.getName()).log(Level.SEVERE, null, ex);          
                         }// Fim do catch                 
                    }
                    else{
                        // Mensagem de sucesso para a alteração de registro                                          
                            Alert alert = new Alert(Alert.AlertType.ERROR);                                                                                                               
                            alert.setHeaderText(null);                                                                               
                            alert.initStyle(StageStyle.UTILITY);                                                                                         
                            alert.setResizable(false);                                                                         
                            alert.setTitle("***AVISO***");                                                                       
                            alert.setContentText("Falha ao mudar a senha do usuário, a senha antiga não foi digitada corretamente!!!");                                                     
                            // Mostra uma caixa de diálogo mas a tela home continua ao fundo
                            Optional <ButtonType> action = alert.showAndWait();       
                             // Caso o usuário clicke em Ok
                            if(action.get() == ButtonType.OK){        
                                // Irá pegar a tela atual
                                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();  
                                // Irá focar nessa tela
                                stage.requestFocus();
                            }// fim do if             
                    }
                }catch(SQLException ex){
                    Logger.getLogger(ControleTelaAlterarSenha.class.getName()).log(Level.SEVERE, null, ex);
                }// fim do catch             
            }// fim do if senha equals    
        });// Fim do botão confirmar
        
    }// Fim do método construtor
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Chama o método construtor
        this.ControleTelaAlterarSenha();
    }// Fim do método de inicialização 
}// Fim da classe