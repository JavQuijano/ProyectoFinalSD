/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JavierQ
 */
class TransaccionVenta {

    public String RFCCompania;
    public Timer timerCompania;
    public ProvinceServer server;
    public ArrayList<String> clientes = new ArrayList<String>();
    public ArrayList<Float> ofertas = new ArrayList<Float>();
    public ArrayList<Integer> cantidades = new ArrayList<Integer>();
    public HashMap<String, ClientHandler> clientesHandler = new HashMap<String, ClientHandler>();

    public TransaccionVenta(ProvinceServer server, String RFCCompania, boolean isnew) {
        if (isnew) {
            this.server = server;
            this.RFCCompania = RFCCompania;
            System.out.println("Timer Compania inicio: " + RFCCompania);
            timerCompania = new Timer();
            timerCompania.schedule(new RemindTask(this), 15 * 1000);
        }
    }

    public void addCliente(String RFCCliente, float oferta, int cantidad, ClientHandler handler) {
        clientes.add(RFCCliente);
        ofertas.add(oferta);
        cantidades.add(cantidad);
        clientesHandler.put(RFCCliente, handler);
        System.out.println(clientes.get(clientes.size() - 1));
    }

    class RemindTask extends TimerTask {
        
        TransaccionVenta timer;
        
        private RemindTask(TransaccionVenta aThis) {
            timer = aThis;
        }

        public void run() {
            float obj = Collections.min(ofertas);
            int index = ofertas.indexOf(obj);
            String ganador = clientes.get(index);

            System.out.println("Timer Compania termino: " + RFCCompania + " ganador: " + ganador);
            timerCompania.cancel(); //Terminate the timer thread
            ClientHandler han = clientesHandler.get(ganador);
            han.Gflag = true;
            try {
                LocalDateTime myDateObj = LocalDateTime.now();
                DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                String fecha = myDateObj.format(myFormatObj);
                guardar_transaccion(ganador, fecha, cantidades.get(index)*(-1), ofertas.get(index));
                server.terminarTimerVenta(timer);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TransaccionCompra.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(TransaccionCompra.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private void guardar_transaccion(String RFCUsuario, String fecha, int cantidad, float valor) throws ClassNotFoundException, SQLException {
            DBManager db = new DBManager();
            Boolean rs = db.set(
                    "INSERT INTO `Transacciones` VALUES ('" + RFCUsuario + "', '" + RFCCompania + "', '" + fecha + "', " + cantidad + ", " + valor + ")");
            if (rs) {
                System.out.println("exito");
            } else {
                System.out.println("fallo");
            }
        }
    }
}
