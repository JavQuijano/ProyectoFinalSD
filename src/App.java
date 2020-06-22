import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class App {
  public static void probarSelect() throws SQLException {
    DBManager db = new DBManager();
    ResultSet rs= db.select("Select * from Companias");
    while (rs.next())
    {
      System.out.println("entro main");
      String RFC = rs.getString("RFC");
      int total = rs.getInt("TotalAcc");
      int disponibles = rs.getInt("AccDisponibles");
      BigDecimal myValue = rs.getBigDecimal("ValorAcc");
      float myFloatValue = myValue.floatValue();
      // print the results
      System.out.format("%s, %s, %s, %s\n", RFC, total, disponibles, myFloatValue);
    }
  }
    public static void probarInsert() throws SQLException {
      DBManager db = new DBManager();
      Boolean rs = db.set(
          "INSERT INTO `Companias`(`RFC`, `TotalAcc`, `AccDisponibles`, `ValorAcc`) VALUES ('MBYLC4EVER',10000,2300,69.12)");
      if (rs) {
        System.out.println("exito");
      } else {
        System.out.println("fallo");
      }
    }

    public static void main(String[] args) throws Exception {
      probarInsert();

    
			
		} 
}
