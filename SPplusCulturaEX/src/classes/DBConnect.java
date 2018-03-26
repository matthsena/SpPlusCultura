package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
// Classe de conexão com banco
public class DBConnect {
    // Variável de conexao
    private static Connection conn;
    // Local onde se encontra o banco e seu nome
    private static  String endereco = "jdbc:mysql://localhost/db_spcultura";
    // Usuário do banco
    private static  String usuario = "root"; 
    // Senha do banco
    private static  String senha = "vertrigo";
    // Método para conectar
    // throws SQLExecption para caso der um erro na estrutura SQL
    public  Connection connect() throws SQLException{
       
        try{
            // Drive de conexão
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }catch(ClassNotFoundException | InstantiationException | IllegalAccessException e){
            // Caso de erro irá exibir uma mensagem de erro
            JOptionPane.showMessageDialog(null, e.getMessage());
        }// Fim do catch
        // Valor da variavel de conexao
        conn = DriverManager.getConnection(endereco,usuario,senha);
        // Retornando a conexao
        return conn;
    }// Fim do método connect
}// Fim da classe