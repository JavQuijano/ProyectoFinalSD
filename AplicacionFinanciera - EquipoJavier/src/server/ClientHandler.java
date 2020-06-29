/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JavierQ
 */
public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private ProvinceServer provinceServer;
    private ArrayList<TransaccionCompra> timersCompra;
    private ArrayList<TransaccionVenta> timersVenta;
    public boolean Gflag;

    public ClientHandler(Socket socket, ProvinceServer server, ArrayList<TransaccionCompra> timersCompra, ArrayList<TransaccionVenta> timersVenta) {
        this.clientSocket = socket;
        this.provinceServer = server;
        this.timersCompra = timersCompra;
        this.timersVenta = timersVenta;
        this.Gflag = false;
    }

    @Override
    public void run() {
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String result = "";
            String info = in.readLine();
            String[] infoArray = info.split(":", 6);
            switch (infoArray[0]) {
                case "0":
                    result = verificarUsuario(infoArray[1]);
                    break;
                case "1":
                    result = obtenerVentasUsuario(infoArray[1]);
                    break;
                case "2":
                    result = obtenerComprasUsuario(infoArray[1]);
                    break;
                case "3":
                    result = ofertaCompra(infoArray[1], infoArray[2], infoArray[3], infoArray[4]);
                    break;
                case "4":
                    result = ofertaVenta(infoArray[1], infoArray[2], infoArray[3], infoArray[4]);
                    break;
            }
            out.println(result);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException ex) {
            Logger.getLogger(ProvinceServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProvinceServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String verificarUsuario(String usuario) throws SQLException, ClassNotFoundException {
        DBManager db = new DBManager();
        String result = "false";
        ResultSet rs = db.select("Select * from usuarios WHERE RFCUsuario = '" + usuario + "'");
        if (rs.next()) {
            do {
                result = "true";
            } while (rs.next());
        } else {
            result = "false";
        }
        return result;
    }

    private String obtenerVentasUsuario(String rfcCliente) throws ClassNotFoundException, SQLException {
        DBManager db = new DBManager();
        String result = "";
        ResultSet rs = db.select("Select SUM(Acciones) as numeroAcciones, RFC, ValorAcc from transacciones JOIN companias ON RFCCompania = RFC WHERE RFCUsuario = '" + rfcCliente + "' GROUP BY RFCCompania");
        if (rs.next()) {
            do {
                result += rs.getString("RFC") + "," + String.valueOf(rs.getInt("numeroAcciones")) + "," + String.valueOf(rs.getBigDecimal("ValorAcc") + ";");
            } while (rs.next());
        } else {
            result = "false";
        }
        result = result.substring(0, result.length() - 1);
        return result;
    }

    private String obtenerComprasUsuario(String rfcCliente) throws ClassNotFoundException, SQLException {
        DBManager db = new DBManager();
        String result = "";
        ResultSet rs = db.select("Select * from companias WHERE AccDisponibles > 0");
        if (rs.next()) {
            do {
                result += rs.getString("RFC") + "," + String.valueOf(rs.getInt("AccDisponibles")) + "," + String.valueOf(rs.getBigDecimal("ValorAcc") + ";");
            } while (rs.next());
        } else {
            result = "false";
        }
        result = result.substring(0, result.length() - 1);
        return result;
    }

    private String ofertaCompra(String RFCCliente, String RFCCompania, String cantidad, String valor) throws InterruptedException {
        TransaccionCompra timer = new TransaccionCompra(provinceServer, RFCCompania, false);
        boolean flag = false;
        if (timersCompra.size() > 0) {
            for (TransaccionCompra timerTemp : timersCompra) {
                if (timerTemp.RFCCompania.equals(RFCCompania)) {
                    flag = true;
                    timer = timerTemp;
                    timerTemp.addCliente(RFCCliente, Float.parseFloat(valor), Integer.parseInt(cantidad), this);
                }
            }
            if (!flag) {
                timer = new TransaccionCompra(provinceServer, RFCCompania, true);
                timer.addCliente(RFCCliente, Float.parseFloat(valor), Integer.parseInt(cantidad), this);
                timersCompra.add(timer);
            }

        } else {
            timer = new TransaccionCompra(provinceServer, RFCCompania, true);
            timer.addCliente(RFCCliente, Float.parseFloat(valor), Integer.parseInt(cantidad), this);
            timersCompra.add(timer);
        }
        //Thread.sleep(120000);
        
        return "true";
    }

    private String ofertaVenta(String RFCCliente, String RFCCompania, String cantidad, String valor) {
        TransaccionVenta timer = new TransaccionVenta(provinceServer, RFCCompania, false);
        boolean flag = false;
        if (timersCompra.size() > 0) {
            for (TransaccionVenta timerTemp : timersVenta) {
                if (timerTemp.RFCCompania.equals(RFCCompania)) {
                    flag = true;
                    timer = timerTemp;
                    timerTemp.addCliente(RFCCliente, Float.parseFloat(valor), Integer.parseInt(cantidad), this);
                }
            }
            if (!flag) {
                timer = new TransaccionVenta(provinceServer, RFCCompania, true);
                timer.addCliente(RFCCliente, Float.parseFloat(valor), Integer.parseInt(cantidad), this);
                timersVenta.add(timer);
            }

        } else {
            timer = new TransaccionVenta(provinceServer, RFCCompania, true);
            timer.addCliente(RFCCliente, Float.parseFloat(valor), Integer.parseInt(cantidad), this);
            timersVenta.add(timer);
        }
        //Thread.sleep(120000);
        
        return "true";
    }
    

    
}
