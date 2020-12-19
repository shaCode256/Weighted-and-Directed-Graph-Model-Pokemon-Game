package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.module.ModuleFinder;

public class LoginPanel extends JPanel implements ActionListener {
    private JButton button;
    private JTextField fieldID, fieldLevel;
    private JLabel labelID, labelLevel;

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
        labelLevel=new JLabel("level");
        labelLevel.setBounds(330, 130, 130, 80);
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
}
