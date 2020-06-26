package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import server.DBManager;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Server
 *
 */
public class ProvinceServer {

    public static void main(String[] args) {
        ServerSocket server = null;
        try {
            server = new ServerSocket(32000);
            server.setReuseAddress(true);
            // The main thread is just accepting new connections
            while (true) {
                Socket client = server.accept();
                System.out.println("New client connected " + client.getInetAddress().getHostAddress());
                ClientHandler clientSock = new ClientHandler(client);

                // The background thread will handle each client separately
                new Thread(clientSock).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class ClientHandler implements Runnable {

        private final Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            PrintWriter out = null;
            BufferedReader in = null;
            String[] userPass;
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String result = "hola";
                String info = in.readLine();
                String[] infoArray = info.split(":", 2);
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
                }
                out.println(result);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException ex) {
                Logger.getLogger(ProvinceServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ProvinceServer.class.getName()).log(Level.SEVERE, null, ex);
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

        public static String verificarUsuario(String usuario) throws SQLException, ClassNotFoundException {
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
            ResultSet rs = db.select("Select SUM(Acciones) as numeroAcciones, RFC, ValorAcc from transacciones JOIN companias ON RFCCompania = RFC WHERE RFCUsuario = '" + rfcCliente + "' AND Acciones > 0 GROUP BY RFCCompania");
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
    }
}
