package views;

import cliente.ProvinceClient;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class CompraAcciones {
    
    private ProvinceClient provinceClient;
    private JFrame frame;
    
    public CompraAcciones(ProvinceClient provinceClient) {
        this.provinceClient = provinceClient;
    }

    public void run() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }

        this.frame = new JFrame("Testing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(new CompraAccionesPane(provinceClient));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public void dispose() {
        this.frame.dispose();
    }

    public class CompraAccionesPane extends JPanel {

        public CompraAccionesPane(ProvinceClient provinceClient) {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(2, 2, 2, 2);

            agregarAcciones("apple", "5", gbc);
            agregarAcciones("apple", "5", gbc);

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
            add(new JTextField(10), gbc);
            gbc.gridx++;
            add(new JTextField(10), gbc);
            gbc.gridx++;
            add(new JTextField(10), gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            gbc.fill = GridBagConstraints.NONE;
            gbc.gridwidth = 2;
            JButton button_CompraAcciones = new JButton("Buscar");
            add(button_CompraAcciones, gbc);
            gbc.gridx++;
            gbc.fill = GridBagConstraints.NONE;
            gbc.gridwidth = 2;
            JButton button_regresar = new JButton("Regresar al panel");
            add(button_regresar, gbc);

            button_CompraAcciones.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    System.out.println("Comprar");
                }
            });

            button_regresar.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    provinceClient.desplegarPanel(2);
                }
            });

        }

        public void agregarAcciones(String nombre, String valor, GridBagConstraints gbc) {

            add(new JLabel(nombre), gbc);
            gbc.gridx++;
            add(new JLabel(valor), gbc);

            gbc.gridx = 0;
            gbc.gridy++;

        }

    }
}
