package classes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    // Método start
    @Override
    public void start(Stage stage) throws Exception {
        // Tirar as bordas padrão do Sistema operacional, deixando-as transparente
        stage.initStyle(StageStyle.TRANSPARENT);
        // Carregando o designer apartir de um fxml
        Parent root = FXMLLoader.load(getClass().getResource("/telas/TelaInicial.fxml"));
        // Impossibilitando o usuário redimensionar a tela
        stage.setResizable(false);
        // Instanciando a tela com o componente fxml
        Scene scene = new Scene(root);       
        // Adicionando tudo a esse estagio da tela
        stage.setScene(scene);
        // Exibindo a tela
        stage.show();
    }// Fim do método Start
    
    // Início main
    public static void main(String[] args) {
        launch(args);
    }// Fim do main    
}// Fim da classe principal