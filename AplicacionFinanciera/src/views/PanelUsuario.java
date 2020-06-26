package views;

import cliente.ProvinceClient;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class PanelUsuario {

    public ProvinceClient provinceClient;
    private JFrame frame;
    
    public PanelUsuario(ProvinceClient provinceClient) {
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
        frame.add(new PanelUsuarioPane(this.provinceClient));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public void dispose(){
        this.frame.dispose();
    }

    public class PanelUsuarioPane extends JPanel {

        public PanelUsuarioPane(ProvinceClient provinceClient) {

            setLayout(new GridBagLayout());
            GridBagConstraints position = new GridBagConstraints();

            position.gridx = 0;
            position.gridy = 0;
            position.insets = new Insets(3, 3, 3, 3);
            position.weightx = 2;
            position.ipadx = 0;
            position.ipady = 50;
            add(new JLabel("Bienvenido al Panel del usuario"), position);

            position.gridy++;
            position.gridx = 0;
            position.ipadx = 0;
            position.ipady = 0;

            JButton button_comprar = new JButton("Comprar");
            add(button_comprar, position);
            position.gridx++;

            JButton button_vender = new JButton("Vender");
            add(button_vender, position);

            button_vender.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        provinceClient.desplegarVender();
                    } catch (IOException ex) {
                        Logger.getLogger(PanelUsuario.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            button_comprar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        provinceClient.desplegarComprar();
                    } catch (IOException ex) {
                        Logger.getLogger(PanelUsuario.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

        }

    }
}
