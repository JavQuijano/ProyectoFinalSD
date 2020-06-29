package views;

import cliente.ProvinceClient;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Login {

    private ProvinceClient provinceClient;
    private JFrame frame;

    public Login(ProvinceClient provinceClient) {
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
        LoginPane login = new LoginPane(this.provinceClient);
        login.setBackground(Color.black);
        frame.add(login);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void dispose() {
        this.frame.dispose();
    }

    public class LoginPane extends JPanel {

        public LoginPane(ProvinceClient provinceClient) {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(20, 20, 20, 20);
            
            JLabel user = new JLabel("RFC de Usuario");
            user.setForeground(Color.white);
            add(user, gbc);
            gbc.gridx++;

            gbc.gridx = 0;
            gbc.gridy++;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            JTextField usernameField = new JTextField("CMMR080397");
            add(usernameField, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            gbc.fill = GridBagConstraints.NONE;
            gbc.gridwidth = 2;
            JButton button_login = new JButton("Iniciar Sesión");
            button_login.setFocusPainted(false);
            button_login.setForeground(Color.WHITE);
            button_login.setBackground(new Color(102, 0, 153)); // Purple
            button_login.setContentAreaFilled(false);
            button_login.setOpaque(true);
            add(button_login, gbc);

            button_login.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    try {
                        provinceClient.auth(usernameField.getText());
                    } catch (IOException ex) {
                        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

        }

    }
}
