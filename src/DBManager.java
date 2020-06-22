import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
    private String server = "localhost";
    private int port = 8889;
	private String user = "root";
    private String password = "root";    
    private static Connection cn  = null;
    private String url = "";
    private String base= "SistemasDistribuidos";


    public DBManager() {
        this.url="jdbc:mysql://"+ this.server+ ":"+this.port+"/"+this.base;
        try {
            cn = DriverManager.getConnection(this.url,this.user,this.password);
        } catch (SQLException e) {
            System.err.println("ERROR AL CONECTAR CON EL SERVIDOR");
            System.exit(0); //para la ejecución
        }
        System.out.println("Conectado a "+ this.base);
    }

    public static Connection getConexion() {
        return cn;
    }

    public ResultSet select(String query) throws SQLException {
        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
                return rs;
            
        } catch (SQLException e) {
            //hubo error
            System.out.println("Error en el query");
            return null;
        }
    }

    //SE PUEDE USAR PARA INSERT, UPDATE Y DELETE
    public Boolean set(String query){
        try{
            Statement st = cn.createStatement();
           int numberRows= st.executeUpdate(query);
            if(numberRows>0){
                st.close();
                return true;
            }
            else{
                System.out.println("No se completo la operacion");
                st.close();
                return false;
            }
            
        } catch (SQLException e) {
            //hubo error
            System.out.println("Error en el query");
            System.err.println(e.getMessage()); 
            return null;
        }
    }

}