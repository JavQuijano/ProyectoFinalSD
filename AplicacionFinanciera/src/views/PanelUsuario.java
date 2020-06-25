import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;



public class PanelUsuario {

    public static void main(String[] args) {

       new PanelUsuario();
  }
      public PanelUsuario() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new PanelUsuarioPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }


  public class PanelUsuarioPane extends JPanel {

    public PanelUsuarioPane() {
        setLayout(new GridBagLayout());
        GridBagConstraints position = new GridBagConstraints();

        position.gridx = 0;
        position.gridy = 0;
        position.insets = new Insets(3, 3, 3, 3);
        position.weightx = 2;
        position.ipadx = 0;
        position.ipady = 50;
        add(new JLabel("Bienvenido al Panel del usuario"),position);

        position.gridy++;
        position.gridx = 0;
        position.ipadx = 0;
        position.ipady = 0;

        JButton button_comprar = new JButton("Comprar");
        add(button_comprar, position);
        position.gridx++;
        
        JButton button_vender = new JButton("Vender");
        add(button_vender,position);
        

        button_vender.addActionListener(new ActionListener() {


            public void actionPerformed(ActionEvent e) {
                System.out.println("vender");
            }
        });
        button_comprar.addActionListener(new ActionListener() {


            public void actionPerformed(ActionEvent e) {
                System.out.println("comprar");
            }
        });


}

    }
  }