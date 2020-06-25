package province;

import java.sql.*;
 
/**
 * DBManager: Singleton pattern
 *
 *
 **/
public final class DBManager {
 
  private static DBManager _instance = null;
  private Connection _con = null;
 private String server = "localhost";
    private int port = 8889;
	private String user = "root";
    private String password = "root";    
    private static Connection cn  = null;
    private String url = "";
    private String base= "SistemasDistribuidos";


    public DBManager() {
        _con = getMySQLConnection();
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
  
 
  //Thread safe instatiate method
  public static synchronized DBManager getInstance() {
    if (_instance == null) {
      _instance = new DBManager();
    }
    return _instance;
  }
 
  public Connection getConnection() {
    return _con;
  }
 
  /**
   * Connection to MySQL Database
   */
  private static Connection getMySQLConnection() {
    Connection con = null;
 
    try {
 
      String strCon = "jdbc:mysql://127.0.0.1/test?user=rtuser&password=123";
      con = DriverManager.getConnection(strCon);
    } catch (SQLException se) {
      System.out.println(se);
    }
    return con;
  }
  
}
