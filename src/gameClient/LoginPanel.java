package gameClient;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LoginPanel extends JPanel implements ActionListener {
    private JButton button;
    private JTextField fieldID, fieldLevel;
    private JLabel labelID, labelLevel;
    private BufferedImage img;

    /**
     * This function configures the login panel of this game.
     */
    public LoginPanel(){
        super();
        this.setLayout(null);
        button=new JButton("Login");
        button.setBounds(100, 100, 100, 50);
        this.add(button);
        button.addActionListener(this);
        labelLevel=new JLabel("Level");
        labelLevel.setBounds(310, 130, 130, 80);
        this.add(labelLevel);
        fieldLevel=new JTextField();
        fieldLevel.setBounds(300, 100, 100, 50);
        fieldLevel.setBackground(Color.white);
        fieldLevel.setCaretColor(Color.black);
        this.add(fieldLevel);
        labelID=new JLabel("ID");
        labelID.setBounds(570, 130, 130, 80);
        this.add(labelID);
        fieldID=new JTextField();
        fieldID.setBounds(530, 100, 100, 50);
        fieldLevel.setBackground(Color.white);
        fieldLevel.setCaretColor(Color.black);
        this.add(fieldID);
        try {
            img = ImageIO.read(new File("Pictures\\pokemonsAsh.png"));
        } catch (IOException ex) {
            // do none
        }
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==button){
            Ex2.level=Integer.parseInt(fieldLevel.getText());
            Ex2.loginID=Integer.parseInt(fieldID.getText());
            Ex2.client.start();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this);
    }
}
