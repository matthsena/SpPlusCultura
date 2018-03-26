package classes;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

public class ControleTelaReclamacoes implements Initializable {
    // Objetos do fxml
    @FXML Button btnEnviar;
    @FXML TextField txtAssunto;
    @FXML TextArea txtReclamacao;
   
    // Método inicializador
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            this.ControleTelaReclamacoes();
        } catch (EmailException ex) {
            Logger.getLogger(ControleTelaReclamacoes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// Fim do método inicializador    

    public void ControleTelaReclamacoes() throws EmailException{
        
        btnEnviar.setOnAction((ActionEvent e)->{
           
                try {
                SimpleEmail email = new SimpleEmail();
                //Utilize o hostname do seu provedor de email
                System.out.println("alterando hostname...");
                email.setHostName("smtp.gmail.com");
                //Quando a porta utilizada não é a padrão (gmail = 465)
                email.setSmtpPort(465);
                //Adicione os dexx@xxxstinatários
                email.addTo("matheusalexandre.sena99@gmail.com", "Jose");
                //Configure o seu email do qual enviará
                email.setFrom("alexandre.m.senna@gmail.com", "matheus");
                //Adicione um assunto
                email.setSubject(txtAssunto.getText());
                //Adicione a mensagem do email
                email.setMsg(txtReclamacao.getText());
                //Para autenticar no servidor é necessário chamar os dois métodos abaixo
                System.out.println("autenticando...");
                email.setSSL(true);
                email.setAuthentication("alexandre.m.senna@gmail.com", "1597532012");
                System.out.println("enviando...");
                email.send();
                System.out.println("Email enviado!");
            } catch (EmailException ex) {
                Logger.getLogger(ControleTelaReclamacoes.class.getName()).log(Level.SEVERE, null, ex);
            }      
        });// Fim do botão     
    }// Fim do método construtor
}// Fim da classe
