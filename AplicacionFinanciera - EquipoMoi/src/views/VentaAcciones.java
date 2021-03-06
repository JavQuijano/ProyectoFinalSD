package views;

import cliente.ProvinceClient;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class VentaAcciones {

    private ProvinceClient provinceClient;
    private JFrame frame;

    public VentaAcciones(ProvinceClient provinceClient) {
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
        frame.add(new VentaAccionesPane(provinceClient));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void dispose() {
        this.frame.dispose();
    }

    public class VentaAccionesPane extends JPanel {

        public VentaAccionesPane(ProvinceClient provinceClient) throws IOException {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(2, 2, 2, 2);

            add(new JLabel("Nombre Accion"), gbc);
            gbc.gridx++;
            
            add(new JLabel("Precio Accion"), gbc);
            gbc.gridx++;
            
            add(new JLabel("Cantidad"), gbc);
            gbc.gridx++;

            gbc.gridx = 0;
            gbc.gridy++;
            
            ArrayList<String[]> accionesVenta = provinceClient.obtenerAccionesVenta();
            accionesVenta.forEach(accion -> {
                agregarAcciones(accion[0], accion[1], accion[2], gbc);
            });

            gbc.ipady = 50;

            gbc.gridx = 1;
            add(new JLabel("Comprar Acciones"), gbc);
            gbc.gridx = 0;
            gbc.gridy++;

            gbc.ipady = 0;
            gbc.gridx = 0;
            add(new JLabel("Nombre Accion"), gbc);
            gbc.gridx++;
            add(new JLabel("Cantidad a vender"), gbc);
            gbc.gridx++;
            add(new JLabel("Precio a vender"), gbc);

            gbc.ipady = 0;
            gbc.gridx = 0;
            gbc.gridy++;

            gbc.fill = GridBagConstraints.HORIZONTAL;
            JTextField fieldNombre = new JTextField(10);
            add(fieldNombre, gbc);
            gbc.gridx++;
            JTextField fieldCantidad = new JTextField(10);
            add(fieldCantidad, gbc);
            gbc.gridx++;
            JTextField fieldValor = new JTextField(10);
            add(fieldValor, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            gbc.fill = GridBagConstraints.NONE;
            gbc.gridwidth = 2;
            JButton button_VentaAcciones = new JButton("Vender");
            add(button_VentaAcciones, gbc);
            gbc.gridx++;
            gbc.fill = GridBagConstraints.NONE;
            gbc.gridwidth = 2;
            JButton button_regresar = new JButton("Regresar al panel");
            add(button_regresar, gbc);

            button_VentaAcciones.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    String RFCCompania = fieldNombre.getText();
                    String cantidadVenta = fieldCantidad.getText();
                    String valorVenta = fieldValor.getText();
                    try {
                        provinceClient.ofertarVentaAccion(RFCCompania, cantidadVenta, valorVenta);
                    } catch (IOException ex) {
                        Logger.getLogger(VentaAcciones.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            button_regresar.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    provinceClient.desplegarPanel(1);
                }
            });

        }

        public void agregarAcciones(String nombre, String precio, String cantidad, GridBagConstraints gbc) {

            add(new JLabel(nombre), gbc);
            gbc.gridx++;
            add(new JLabel(cantidad), gbc);
            gbc.gridx++;
            add(new JLabel(precio), gbc);
            gbc.gridx = 0;
            gbc.gridy++;

        }

    }
}
