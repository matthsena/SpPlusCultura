package classes;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ControleTelaBuscar implements Initializable{
 
    // Variáveis para criação de Querys
    String incioQuery = "SELECT * FROM tb_locais ";
    
    // ↓ Valores ainda serão atribuidos no decorrer dos 'ifs'
    String valorQuery = "";
    String periodoQuery = "";
    String condicaoQuery = "";
    String condicaoQuery2 = "";
    
    // Variável que irá receber a query final
    public static String sql;
    
    // Criação dos objetos vindos do FXML
    @FXML private CheckBox checkGratuito;
    @FXML private CheckBox checkPago;
    @FXML private CheckBox checkDiurno;
    @FXML private CheckBox check24hrs;
    @FXML private CheckBox checkNoturno;
    @FXML private Button btnBuscar;

    // Método construtos
    public void ControleTelaBuscar() {
        
        // Focus no botão buscar
        btnBuscar.requestFocus();
        
        // Ação do botão buscar
        btnBuscar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // Zerando os valores das variaveis
                valorQuery = "";
                periodoQuery = "";
                condicaoQuery = "";
                condicaoQuery2 = "";
                
                // Ifs para definir a query final
                if(checkGratuito.isSelected() || checkPago.isSelected() || checkDiurno.isSelected() || check24hrs.isSelected() || checkNoturno.isSelected()){
                    // Caso qualquer coisa esteja selecionada                
                    condicaoQuery = "WHERE";                 
                    if(checkGratuito.isSelected() || checkPago.isSelected()){
                        // Caso gratuito ou pago esteja selecionado
                        if(checkGratuito.isSelected()){
                            // Caso gratuito esteja selecionado
                            valorQuery = " dispLocal = 'gratuito' ";                           
                        }
                        if(checkPago.isSelected()){
                            // Caso pago esteja selecionado
                            valorQuery = " dispLocal = 'pago' ";
                        }
                        
                        // Caso manhã, tarde ou noite seja selecionado
                        if(checkDiurno.isSelected() || checkNoturno.isSelected() || check24hrs.isSelected()){
                            
                            condicaoQuery2 = "AND";
                            
                            if(checkDiurno.isSelected() && checkNoturno.isSelected()){
                                
                                periodoQuery = " periodoLocal = 'diurno' AND periodoLocal = 'noturno'";
                            }
                            else if(checkDiurno.isSelected() && check24hrs.isSelected()){
                                
                                periodoQuery = " periodoLocal = 'diurno' AND periodoLocal = '24hrs'";
                            }
                            else if(check24hrs.isSelected() && checkNoturno.isSelected()){
                                
                                periodoQuery = " periodoLocal = '24hrs' AND periodoLocal = 'noturno'";
                            }
                            else if(checkDiurno.isSelected()){
                                
                                periodoQuery = " periodoLocal = 'diurno'";
                            }
                            else if(checkNoturno.isSelected()){
                                
                                periodoQuery = " periodoLocal = 'noturno'";
                            }
                            else if(check24hrs.isSelected()){
                                
                                periodoQuery = " periodoLocal = '24hrs'";
                            }
                        }
                        
                    }// Fim do if
                    
                    //Caso os precos não sejam selecionados, apenas o periodo
                    if(checkDiurno.isSelected() && checkNoturno.isSelected() && check24hrs.isSelected()){
                        
                        periodoQuery = " periodoLocal = '24hrs' AND periodoLocal = 'diurno' AND periodoLocal = 'noturno'";                                           
                    }
                    else if(checkDiurno.isSelected() && checkNoturno.isSelected()){
                        
                        periodoQuery = " periodoLocal = 'diurno' AND periodoLocal = 'noturno'";
                    }
                    else if(checkDiurno.isSelected() && check24hrs.isSelected()){
                        
                        periodoQuery = " periodoLocal = 'diurno' AND periodoLocal = '24hrs'";
                    }
                    else if(check24hrs.isSelected() && checkNoturno.isSelected()){
                        
                        periodoQuery = " periodoLocal = '24hrs' AND periodoLocal = 'noturno'";
                    }
                    else if(checkDiurno.isSelected()){
                        
                        periodoQuery = " periodoLocal = 'diurno'";
                    }
                    else if(checkNoturno.isSelected()){
                        
                        periodoQuery = " periodoLocal = 'noturno'";
                    }
                    else if(check24hrs.isSelected()){
                        
                        periodoQuery = " periodoLocal = '24hrs'";
                    }
                    
                    if(checkGratuito.isSelected() && checkPago.isSelected()){
                        
                        valorQuery = " dispLocal = 'gratuito' AND dispLocal = 'pago' ";
                    }
                     
                    if(checkGratuito.isSelected() && checkPago.isSelected() && checkDiurno.isSelected() && check24hrs.isSelected() && checkNoturno.isSelected()){
                        
                        valorQuery = "";
                        periodoQuery = "";
                        condicaoQuery = "";
                        condicaoQuery2 = "";
                    }          
                }
                else{
                    //Caso não selecione nenhum filtro irá selecionar todos os indices do banco
                    valorQuery = "";
                    periodoQuery = "";
                    condicaoQuery = "";
                    condicaoQuery2 = "";
                }
                sql = incioQuery+condicaoQuery+valorQuery+condicaoQuery2+periodoQuery+";";
                // Pega a janela atual e define como uma variável de tipo Stage
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();               
                // Fecha a janela atual
                stage.close();
                
                 // Tentar ir para a nova página após atender todas os desejos do usuário                          
            try {                
               // Carregar uma nova página vinda de um FXML
               Parent nextPage = FXMLLoader.load(getClass().getResource("/telas/TelaResultadoBusca.fxml")); 
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
            }
        });// Fim do botão buscar        
    }// Fim do método construtor
    // Método inicializador
    @Override
    public void initialize(URL location, ResourceBundle resources) {
      
        // Chamando o método construtor para o método inicializador
        this.ControleTelaBuscar();
    }// Fim do método inicializador    
}// Fim da classe