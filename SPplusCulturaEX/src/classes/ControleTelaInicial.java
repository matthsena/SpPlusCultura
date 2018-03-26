package classes;

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
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

// Inicio da classe
public class ControleTelaInicial implements Initializable {
    // Variável para pegar o nome de usuári que logar e jogar para outra classe
    public static String user;
    // Variaveis para mudar o fundo das telas
    int i = 2;
    
    // Strings
    String operacao;
    String operacaoConfirm;
    String senha;
    String query;
                 
    //Variavel Connection            
    Connection c; 
    
    // Instanciando classe de conexao com banco
    private DBConnect db;
  
    // Botões criados no FXML
    @FXML private Button btnSair;
    @FXML private Button btnEntrar;
    @FXML private Button btnRegistrar;
    @FXML private Button btnOcultar;
    @FXML private Button btnAcaoBotao;
    @FXML private Button btnAlterarFundo;
   
    // Anchor Pane para 'Entrar' e 'Registrar'
    @FXML private AnchorPane anchorPaneDireito;
   
    // Label
    @FXML private Label lblTitulo;
    @FXML private Label lblNome;
    @FXML private Label lblUsuario;
    @FXML private Label lblSenha;
    @FXML private Label lblConfirmSenha;
    @FXML private Label lblEmail;
    
    // Texts Fields
    @FXML private TextField txtUsuario;
    @FXML private TextField txtEmail;
    @FXML private TextField txtNome;
   
    // Password Fields
    @FXML private PasswordField txtSenha;
    @FXML private PasswordField txtConfirmarSenha;
    
    // ImageViews
    @FXML private ImageView backImg1;
    @FXML private ImageView backImg2;
    @FXML private ImageView backImg3;
  
    // Metodo de inicialização
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Chama o método construtor para o método de inicialização
        this.ControleTelaInicial();
    }// Fim do método inicializador 
    
    // Criando método de Limite de strings para TextFields 
    public void maxTextField(TextField textfield, int maxLength) {
      
        textfield.textProperty().addListener((final ObservableValue<? extends String> obs, final String old, final String novo) -> {
            if (textfield.getText().length() > maxLength) {
                textfield.setText(textfield.getText().substring(0, maxLength));
            }
        });
     }// Fim do método para limitar o text field   
    // Criando método de Limite de strings para PasswordFields    
    public  void maxPasswordField(PasswordField passwordfield, int maxLength) {
           
        passwordfield.textProperty().addListener((final ObservableValue<? extends String> obs, final String old, final String novo) -> {
            if (passwordfield.getText().length() > maxLength) {
                passwordfield.setText(passwordfield.getText().substring(0, maxLength));
            }
        });
     }// Fim do método para limitar o password field
    // Método para modificar o layout do AnchorPane para entrar
    private void layoutEntrar(){
         
        lblTitulo.setText("Entrar");
        btnAcaoBotao.setText("Entrar");
        txtConfirmarSenha.setVisible(false);
        txtEmail.setVisible(false); 
        txtNome.setVisible(false);
        lblNome.setVisible(false);
        lblEmail.setVisible(false);
        lblConfirmSenha.setVisible(false);
    }// Fim do método layout entrar
    // Método para modificar o layout do AnchorPane para registrar
    private void layoutRegistrar(){
        
        lblTitulo.setText("Registrar"); 
        btnAcaoBotao.setText("Registrar");
        txtConfirmarSenha.setVisible(true);
        txtEmail.setVisible(true);
        txtNome.setVisible(true);
        lblNome.setVisible(true);
        lblEmail.setVisible(true);
        lblConfirmSenha.setVisible(true);
    }// Fim do método layout registrar
    // Método para limpar todos os campos
    private void limparCampos(){
        
        txtNome.setText("");                            
        txtUsuario.setText("");                                                 
        txtSenha.setText("");                                             
        txtConfirmarSenha.setText("");                                              
        txtEmail.setText("");
    }// Fim do método para limpar campos
    // Método contrutor
    public void ControleTelaInicial() {
        // instanciando a classe de conexão
        db = new DBConnect();
        // Atribuindo o valor máximo de Strings aos campos
        this.maxTextField(txtNome, 80);
        this.maxTextField(txtEmail, 120);
        this.maxTextField(txtUsuario, 20);
        this.maxPasswordField(txtSenha, 20);
        this.maxPasswordField(txtConfirmarSenha, 20);       
        // Translação para chamar o AnchorPane
        // Duration(velociodade que demora para dar o efeito slide)
        TranslateTransition acaoAparecer = new TranslateTransition(new Duration(350), anchorPaneDireito); 
        // Translação para esconder o AnchorPane
        TranslateTransition acaoEsconder = new TranslateTransition(new Duration(350), anchorPaneDireito);                 
        acaoEsconder.setToX(0);   
        // Ação do botão entrar
        btnEntrar.setOnAction((ActionEvent e)->{
            
            // Chama o método de layoutEntrar
            this.layoutEntrar();
            // Dando o foco para o botão ocultar
            btnOcultar.requestFocus();
            // Caso esteja oculto vai aparecer
            if(anchorPaneDireito.getTranslateX()== 0){
              acaoAparecer.setToX(-(anchorPaneDireito.getWidth()));
              acaoAparecer.play();
            }          
        });
        // Ação do botão Registrar
        btnRegistrar.setOnAction((ActionEvent e)->{
            
            // Chama o método de layoutRegistrar
            this.layoutRegistrar();                 
            // Dando o foco para o botão ocultar
            btnOcultar.requestFocus();
            // Caso esteja oculto vai aparecer
            if(anchorPaneDireito.getTranslateX()== 0){               
               acaoAparecer.setToX(-(anchorPaneDireito.getWidth()));
               acaoAparecer.play();          
            }          
        });
        // Ação do botão ocultar
        btnOcultar.setOnAction((ActionEvent e)->{                      
            // Dando o foco para o botão ocultar
            btnOcultar.requestFocus();
            // Esconde o AnchorPane
            acaoEsconder.play();
            // Vai limpar os campos
            this.limparCampos();
        });         
         //Botão de ação, registrar ou entrar
         btnAcaoBotao.setOnAction((ActionEvent e)->{        
             
             // Pegar a operação, se é registrar ou entrar
             operacao = lblTitulo.getText();
             // Confirmar se é essa operação
             operacaoConfirm = btnAcaoBotao.getText(); 
             // Dando o foco para o botão ocultar
             btnOcultar.requestFocus();
             // Caso realmente se confirme a operação e não de algum 'bug'            
             if(operacao.equals(operacaoConfirm)){
                 // Caso a operação for de entrada  
                 if(operacao.equalsIgnoreCase("Entrar") && operacaoConfirm.equalsIgnoreCase("Entrar")){
                   
                     try {
                         // Irá pegar a conexão da classe DBConnect
                          c = db.connect();                                  
                         // Query SQL a ser executada 
                         query = "SELECT nomeUsuario,userUsuario,senhaUsuario,emailUsuario FROM tb_usuario WHERE userUsuario = '"+txtUsuario.getText()
                                 +"' AND senhaUsuario = '"+txtSenha.getText()+"';";                          
                         // Criando um statement e ligando a conexão  
                         Statement stmt =  c.createStatement();          
                         // Gerando um Result Set  
                         ResultSet rs = stmt.executeQuery(query);                            
                         
                         // Caso consiga selecionar                      
                         if (rs.next()) {         
                             user = txtUsuario.getText();
                             // Tentar ir para a nova página
                             try {                         
                                 // Irá carregar essa página de um FXML
                                 Parent nextPage = FXMLLoader.load(getClass().getResource("/telas/TelaHome.fxml"));                                                 
                                 Scene nextPageScene = new Scene(nextPage); 
                                 // Vai sobrepor a página atual
                                 Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();                                                   
                                 stage.setScene(nextPageScene);
                                 // Irá exibir a página
                                 stage.show();                                                                                                 
                             } 
                             // Caso der erro para carregar a nova página
                             catch (IOException ex) {                                                      
                                 Logger.getLogger(ControleTelaInicial.class.getName()).log(Level.SEVERE, null, ex);                                                 
                             }
                         }     
                         // Caso não consiga selecionar
                        else {
                             // Caixa de diálogo do tipo WARNING 
                             Alert alert = new Alert(Alert.AlertType.WARNING);
                             // Tira os cabeçario que o própio sistema operacional faz
                             alert.setHeaderText(null);
                             // Deixa a borda no estilo UTILITY
                             alert.initStyle(StageStyle.UTILITY);
                             // Impede o usuário redimensionar a caixa de diálogo
                             alert.setResizable(false);
                             // Coloca o seguinte titulo no topo da caixa de diálogo
                             alert.setTitle("***AVISO***");
                             // Mensagem que irá mostrar
                             alert.setContentText("Falha ao entrar, verifique se seu usuário e senha estão corretos!!!");                      
                             // show and Wait exibe a tela principal e a de diálogo ao mesmo tempo
                             Optional <ButtonType> action = alert.showAndWait(); 
                             // Caso o usuário clicke em Ok           
                              if(action.get() == ButtonType.OK){                                   
                                  // Pega a janela atual e define como uma variável de tipo Stage               
                                  Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();                               
                                  // Foca a janela atual                
                                  stage.requestFocus();
                              }// Fim do if botão ok 
                             // Limpa todos os campos com o método de limpar campos
                             this.limparCampos();
                         }// Fim do catch			        	                                   
                     }// Fim do Try                     
                     // Caso der um erro com o SQL
                     catch(SQLException se){	
                          // Caixa de diálogo do tipo ERROR                           
                          Alert alert = new Alert(Alert.AlertType.ERROR);                      
                          // Tira os cabeçario que o própio sistema operacional faz                  
                          alert.setHeaderText(null);                
                          // Deixa a borda no estilo UTILITY                         
                          alert.initStyle(StageStyle.UTILITY);                         
                          // Impede o usuário redimensionar a caixa de diálogo                         
                          alert.setResizable(false);                          
                          // Coloca o seguinte titulo no topo da caixa de diálogo            
                          alert.setTitle("***AVISO***");                
                          // Mensagem que irá mostrar               
                          alert.setContentText("Erro"+se+"\n \n ERRO COM BANCO DE DADOS");                      
                          // show and Wait exibe a tela principal e a de diálogo ao mesmo tempo              
                          Optional <ButtonType> action = alert.showAndWait();
                          // Caso o usuário clicke em Ok           
                              if(action.get() == ButtonType.OK){                                   
                                  // Pega a janela atual e define como uma variável de tipo Stage               
                                  Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();                               
                                  // Foca a janela atual                
                                  stage.requestFocus();
                              }// Fim do if botão ok 
                          // Limpa todos os campos com o método de limpar campos                  
                          this.limparCampos();            
                     }// Fim do catch              
                 }// Fim da operação caso seja Entrar         
                 // Caso a operação seja para registrar
                 if(operacao.equalsIgnoreCase("Registrar") && operacaoConfirm.equalsIgnoreCase("Registrar")){             
                    // Caso o campo de usuario ou senha não tenha algum valor
                     if("".equals(txtUsuario.getText()) || "".equals(txtSenha.getText())){
                         // Caixa de diálogo do tipo WARNING      
                         Alert alert = new Alert(Alert.AlertType.WARNING);    
                         // Tira os cabeçario que o própio sistema operacional faz  
                         alert.setHeaderText(null);
                         // Deixa a borda no estilo UTILITY 
                         alert.initStyle(StageStyle.UTILITY); 
                         // Impede o usuário redimensionar a caixa de diálogo     
                         alert.setResizable(false);      
                         // Coloca o seguinte titulo no topo da caixa de diálogo  
                         alert.setTitle("***AVISO***");   
                         // Mensagem que irá mostrar  
                         alert.setContentText("O campo Usuário e Senha não podem estar vazios!!!"); 
                         // show and Wait exibe a tela principal e a de diálogo ao mesmo tempo
                         Optional <ButtonType> action = alert.showAndWait();
                         // Caso o usuário clicke em Ok           
                              if(action.get() == ButtonType.OK){                                   
                                  // Pega a janela atual e define como uma variável de tipo Stage               
                                  Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();                               
                                  // Foca a janela atual                
                                  stage.requestFocus();
                              }// Fim do if botão ok // Caso o usuário clicke em Ok           
                              if(action.get() == ButtonType.OK){                                   
                                  // Pega a janela atual e define como uma variável de tipo Stage               
                                  Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();                               
                                  // Foca a janela atual                
                                  stage.requestFocus();
                              }// Fim do if botão ok 
                         // Limpa todos os campos com o método de limpar campos                  
                         this.limparCampos(); 
                     }
                     //caso eles não estejam vazios
                     else{                         
                          // Pegando o valor da senha
                          senha = txtSenha.getText();                    
                          // Conferindo se as senhas são iguais                     
                          if(txtConfirmarSenha.getText().equals(senha)){                                            
                         // Tentar fazer o registro                         
                         try{                          
                             // Pegando a conexão da classe DBConnect                             
                             c = db.connect();                                                      
                             // Query do SQL
                             query = "INSERT INTO tb_usuario(nomeUsuario, userUsuario, senhaUsuario, emailUsuario) VALUES ('"+txtNome.getText()+"','"
                                     +txtUsuario.getText()+"','"+txtSenha.getText()+"','"+txtEmail.getText()+"');";                          
                             // Criando o Statement
                             Statement stmt =  c.createStatement();
                             // Executando a query com o Statement
                             stmt.executeUpdate(query);  
                             c.close();
                             // Mensagem de sucesso para o registro                         
                             Alert alert = new Alert(Alert.AlertType.INFORMATION);                                                                                              
                             alert.setHeaderText(null);                                                               
                             alert.initStyle(StageStyle.UTILITY);                                                                        
                             alert.setResizable(false);                                                        
                             alert.setTitle("***AVISO***");                                                      
                             alert.setContentText("Sucesso ao cadastrar  se, aproveite o melhor da nossa querida São Paulo juntamente com nosso aplicativo!!!");                                                     
                             Optional <ButtonType> action = alert.showAndWait(); 
                             // Caso o usuário clicke em Ok           
                              if(action.get() == ButtonType.OK){
                                  // Irá enviar o usuário para a aba de login
                                  this.layoutEntrar();
                                  // Pega a janela atual e define como uma variável de tipo Stage               
                                  Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();                               
                                  // Foca a janela atual                
                                  stage.requestFocus();
                              }// Fim do if botão ok 
                         }// Fim do Try
                         // Caso tenha algum erro SQL de duplicação de chave primaria                     
                         catch(SQLException se){	                                      
                             // Caixa de diálogo e suas propiedades
                             Alert alert = new Alert(Alert.AlertType.WARNING);                                                                                           
                             alert.setHeaderText(null);                                                         
                             alert.initStyle(StageStyle.UTILITY);                                                                        
                             alert.setResizable(false);                                                               
                             alert.setTitle("***AVISO***");                                           
                             alert.setContentText("Falha ao registrar, usuário já existente!!!");                                                      
                             Optional <ButtonType> action = alert.showAndWait();
                             // Caso o usuário clicke em Ok           
                              if(action.get() == ButtonType.OK){                                   
                                  // Pega a janela atual e define como uma variável de tipo Stage               
                                  Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();                               
                                  // Foca a janela atual                
                                  stage.requestFocus();
                              }// Fim do if botão ok                              
                         }// Fim do catch                          
                     }// Fim do if as senhas serem iguais
                     // Caso as senhas não sejam iguais                    
                          else{     
                              // Caixa de diálogo e suas propiedades
                              Alert alert = new Alert(Alert.AlertType.WARNING);                                                                                
                              alert.setHeaderText(null);                                                        
                              alert.initStyle(StageStyle.UTILITY);                                                                   
                              alert.setResizable(false);                                                         
                              alert.setTitle("***AVISO***");                                    
                              alert.setContentText("Falha ao registrar, as senhas não conferem!!!");                                                      
                              Optional <ButtonType> action = alert.showAndWait();
                              // Caso o usuário clicke em Ok           
                              if(action.get() == ButtonType.OK){                                   
                                  // Pega a janela atual e define como uma variável de tipo Stage               
                                  Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();                               
                                  // Foca a janela atual                
                                  stage.requestFocus();
                              }// Fim do if botão ok 
                          }// Fim do else der erro no registro                                              
                     }         
                     // Chamando o metodo para limpar os campos
                     this.limparCampos();              
                 }// Fim do caso a operação for registrar                         
             }// Fim caso as operações se confirmem         
             else {                          
                 // Caso tenha um erro Lógico e as operações não se confirmem
                 Alert alert = new Alert(Alert.AlertType.ERROR);                                                                                
                 alert.setHeaderText(null);                                                         
                 alert.initStyle(StageStyle.UTILITY);                                                                 
                 alert.setResizable(false);                                                       
                 alert.setTitle("***AVISO***");                                    
                 alert.setContentText("Mil desculpas, aparentemente tivemos um erro lógico, tente novamente!!!");                                                  
                 Optional <ButtonType> action = alert.showAndWait(); 
                 // Caso o usuário clicke em Ok                                     
                 if(action.get() == ButtonType.OK){                                                               
                     // Pega a janela atual e define como uma variável de tipo Stage                                  
                     Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();                                                             
                     // Foca a janela atual                                         
                     stage.requestFocus();                           
                 }// Fim do if botão ok 
             }// Fim do else der algum erro inesperado na lógica             
         });// Fim do botão
         //Botão Alterar fundo
         btnAlterarFundo.setOnAction((ActionEvent e)->{        
             // A cada click o int I ira adicionar um
             i++;        
             // Caso passe do limite máximo voltará ao primeiro
             if(i > 3){
             
                 i = 1;        
             }      
             if(i == 3){
                 
                 backImg1.setVisible(false);          
                 backImg2.setVisible(false);           
                 backImg3.setVisible(true);    
             }// fim do if a imagem ser a 3°
             if(i == 2){
             
                 backImg1.setVisible(false);              
                 backImg2.setVisible(true);              
                 backImg3.setVisible(false);        
             }// fim do if a imagem ser a 2°     
             if(i == 1){
           
                 backImg1.setVisible(true);           
                 backImg2.setVisible(false);          
                 backImg3.setVisible(false);         
             }// fim do if a imagem ser a 1°         
         });// Fim do botão alterar fundo       
         //Botão Sair       
         btnSair.setOnAction((ActionEvent e)->{                                     
             // Confirmar a ação de sair
             Alert alert = new Alert(Alert.AlertType.CONFIRMATION);           
             alert.setHeaderText(null);                
             alert.initStyle(StageStyle.UTILITY);       
             alert.setResizable(false);            
             alert.setTitle("***AVISO***");         
             alert.setContentText("Ao pressionar 'OK' você ira encerrar a aplicação... "
                     + "Realmente deseja fazer isso? Perder a "         
                     + "oportunidade de conhecer novos horizontes, culturas e povos do grande "
                     + "aglomerado de tribos que é nossa "         
                     + "querida e amável São Paulo!!!");          
             Optional <ButtonType> action = alert.showAndWait(); 
             // Caso o usuário clicke em Ok
             if(action.get() == ButtonType.OK){        
                 // Irá sair do sistema
                 System.exit(0);      
             }// Fim do if botão ok             
         });// Fim do botão sair
    }// Fim do método construtor               
}// Fim da classe