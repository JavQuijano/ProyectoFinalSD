package views;

import cliente.ProvinceClient;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class CompraAcciones {
    
    private ProvinceClient provinceClient;
    private JFrame frame;
    
    public CompraAcciones(ProvinceClient provinceClient) {
        this.provinceClient = provinceClient;
    }

    public void run() throws IOException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }

        this.frame = new JFrame("Testing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        CompraAccionesPane acciones_pane = new CompraAccionesPane(provinceClient);
        acciones_pane.setBackground(Color.BLACK);
        frame.add(acciones_pane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public void dispose() {
        this.frame.dispose();
    }

    public class CompraAccionesPane extends JPanel {

        public CompraAccionesPane(ProvinceClient provinceClient) throws IOException {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(2, 2, 2, 2);
            
            gbc.gridx++;
            JLabel titulo = new JLabel("COMPRA DE ACCIONES");
            titulo.setForeground(Color.WHITE);
            add(titulo, gbc);
            
            gbc.gridy++;
            gbc.gridx = 0;
            
            JLabel nombre = new JLabel("Nombre Accion");
            nombre.setForeground(Color.WHITE);
            add(nombre, gbc);
            gbc.gridx++;
            
            JLabel precio = new JLabel("Precio Accion");
            precio.setForeground(Color.WHITE);
            add(precio, gbc);
            gbc.gridx++;
            
            JLabel cantidad = new JLabel("Acciones Disponibles");
            cantidad.setForeground(Color.WHITE);
            add(cantidad, gbc);
            gbc.gridx++;

            gbc.gridx = 0;
            gbc.gridy++;
            
            ArrayList<String[]> accionesCompra = provinceClient.obtenerAccionesCompra();
            accionesCompra.forEach(accion -> {
                agregarAcciones(accion[0], accion[2], accion[1], gbc);
            });

            gbc.ipady = 50;

            gbc.gridx = 1;
            add(new JLabel("Comprar Acciones"), gbc);
            gbc.gridx = 0;
            gbc.gridy++;
            
            JLabel nom = new JLabel("RFC Compañia");
            nom.setForeground(Color.white);
            JLabel pre = new JLabel("Precio oferta");
            pre.setForeground(Color.white);
            JLabel can = new JLabel("Cantidad a comprar");
            can.setForeground(Color.white);
            gbc.ipady = 0;
            gbc.gridx = 0;
            add(nom, gbc);
            gbc.gridx++;
            add(pre, gbc);
            gbc.gridx++;
            add(can, gbc);
            
            gbc.ipady = 0;
            gbc.gridx = 0;

            gbc.gridy++;

            gbc.fill = GridBagConstraints.HORIZONTAL;
            JTextField fieldNombre = new JTextField(10);
            add(fieldNombre, gbc);
            gbc.gridx++;
            JTextField fieldValor = new JTextField(10);
            add(fieldValor, gbc);
            gbc.gridx++;
            JTextField fieldCantidad = new JTextField(10);
            add(fieldCantidad, gbc);
            

            gbc.gridx = 0;
            gbc.gridy++;
            gbc.fill = GridBagConstraints.NONE;
            gbc.gridwidth = 2;
            JButton button_CompraAcciones = new JButton("Comprar");
            button_CompraAcciones.setFocusPainted(false);
            button_CompraAcciones.setForeground(Color.BLACK);
            button_CompraAcciones.setBackground(Color.GREEN); // Purple
            button_CompraAcciones.setContentAreaFilled(false);
            button_CompraAcciones.setOpaque(true);
            add(button_CompraAcciones, gbc);
            gbc.gridx++;
            gbc.fill = GridBagConstraints.NONE;
            gbc.gridwidth = 2;
            JButton button_regresar = new JButton("Regresar");
            add(button_regresar, gbc);

            button_CompraAcciones.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    String RFCCompania = fieldNombre.getText();
                    String cantidadCompra = fieldCantidad.getText();
                    String valorCompra = fieldValor.getText();
                    try {
                        provinceClient.ofertarCompraAccion(RFCCompania, cantidadCompra, valorCompra);
                    } catch (IOException ex) {
                        Logger.getLogger(CompraAcciones.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            button_regresar.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    provinceClient.desplegarPanel(2);
                }
            });

        }

        public void agregarAcciones(String nombre, String valor, String cantidad, GridBagConstraints gbc) {
            JLabel l_nombre = new JLabel(nombre);
            l_nombre.setForeground(Color.white);
            JLabel l_valor = new JLabel("$"+valor);
            l_valor.setForeground(Color.white);
            JLabel l_cantidad = new JLabel(cantidad);
            l_cantidad.setForeground(Color.white);
            add(l_nombre, gbc);
            gbc.gridx++;
            add(l_valor, gbc);
            gbc.gridx++;
            add(l_cantidad, gbc);
            
            

            gbc.gridx = 0;
            gbc.gridy++;

        }

    }
}
