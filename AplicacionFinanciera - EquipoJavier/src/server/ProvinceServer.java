package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import server.DBManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Timer;

/**
 * Server
 *
 */
public class ProvinceServer {

    private ArrayList<ClientHandler> handlerConectedUsers = new ArrayList<ClientHandler>();
    private ArrayList<String> RFCConectedUsers = new ArrayList<String>();
    private static ArrayList<String[]> CompaniasDisponibles = new ArrayList<String[]>();
    private ArrayList<TransaccionCompra> timersCompra = new ArrayList<TransaccionCompra>();
    private ArrayList<TransaccionVenta> timersVenta = new ArrayList<TransaccionVenta>();

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ProvinceServer provinceServer = new ProvinceServer();
    }

    public ProvinceServer() throws ClassNotFoundException, SQLException {

        CompaniasDisponibles = obtenerCompanias();
        ServerSocket server = null;
        try {

            server = new ServerSocket(32000);
            server.setReuseAddress(true);
            // The main thread is just accepting new connections
            while (true) {
                Socket client = server.accept();
                System.out.println("New client connected " + client.getInetAddress().getHostAddress());
                ClientHandler clientSock = new ClientHandler(client, this, timersCompra, timersVenta);
                handlerConectedUsers.add(clientSock);
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

    public static ArrayList<String[]> obtenerCompanias() throws ClassNotFoundException, SQLException {
        DBManager db = new DBManager();
        ArrayList<String[]> result = new ArrayList<String[]>();
        ResultSet rs = db.select("Select * from companias");
        if (rs.next()) {
            do {
                String[] temp = new String[4];
                temp[0] = rs.getString("RFC");
                temp[1] = rs.getString("TotalAcc");
                temp[2] = rs.getString("AccDisponibles");
                temp[3] = rs.getString("ValorAcc");
                result.add(temp);
            } while (rs.next());
        }
        return result;
    }

    void terminarTimerCompra(TransaccionCompra timer) {
        timersCompra.remove(timer);
    }

    void terminarTimerVenta(TransaccionVenta timer) {
        timersVenta.remove(timer);
    }
}
