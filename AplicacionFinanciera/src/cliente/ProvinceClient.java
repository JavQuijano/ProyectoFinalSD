package cliente;

import views.*;
import cliente.Province;
import cliente.IRemoteProvince;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * ProvinceClient: client application
 *
 */
public class ProvinceClient {

    private Socket socket;
    private Login vistaLogin;
    private PanelUsuario vistaPanel;
    private VentaAcciones ventaAcciones;
    private CompraAcciones compraAcciones;
    private String host;
    private int port;
    public String rfcCliente;

    public ProvinceClient() throws IOException {
        host = "127.0.0.1";
        port = 32000;
        vistaLogin = new Login(this);
        
        vistaLogin.run();
        ventaAcciones = new VentaAcciones(this);
        compraAcciones = new CompraAcciones(this);
        vistaPanel = new PanelUsuario(this);
    }

    public static void main(String[] args) throws IOException {
        ProvinceClient provinceClient = new ProvinceClient();
    }

    public void auth(String user) throws IOException {
        socket = new Socket(this.host, this.port);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line = "0:"+user;
        out.println(line);
        out.flush();
        String result = in.readLine();
        System.out.println(result);
        if (result.equals("true")) {
            rfcCliente = user;
            vistaLogin.dispose();
            vistaPanel.run();
        } else {
            System.out.println("usuario no existe");
        }
        socket.close();
    }

    public void desplegarVender() throws IOException {
        ventaAcciones.run();
        vistaPanel.dispose();
    }

    public void desplegarComprar() throws IOException {
        compraAcciones.run();
        vistaPanel.dispose();
    }

    public void desplegarPanel(int ventana) {
        switch(ventana){
            case 1:
                ventaAcciones.dispose();
                break;
            case 2:
                compraAcciones.dispose();
                break;
        }
        vistaPanel.run();
    }

    public ArrayList<String[]> obtenerAccionesVenta() throws IOException {
        socket = new Socket(this.host, this.port);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String parameter = "1:" + this.rfcCliente;
        out.println(parameter);
        String result = in.readLine();
        System.out.println(result);
        ArrayList<String[]> acciones = new ArrayList<>();
        String[] stringRow = result.split(";",50);
        for(String stringCol : stringRow){
            acciones.add(stringCol.split(",",5));
        }
        socket.close();
        return acciones;
    }

    public ArrayList<String[]> obtenerAccionesCompra() throws IOException {
        socket = new Socket(this.host, this.port);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String parameter = "2:" + this.rfcCliente;
        out.println(parameter);
        String result = in.readLine();
        System.out.println(result);
        ArrayList<String[]> acciones = new ArrayList<>();
        String[] stringRow = result.split(";",50);
        for(String stringCol : stringRow){
            acciones.add(stringCol.split(",",5));
        }
        socket.close();
        return acciones;
    }

    public void ofertarCompraAccion(String RFCCompania, String cantidadCompra, String valorCompra) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void ofertarVentaAccion(String RFCCompania, String cantidadVenta, String valorVenta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
