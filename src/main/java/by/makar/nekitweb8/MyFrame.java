package by.makar.nekitweb8;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    JLabel msg;
    public MyFrame(){
        setLocationRelativeTo(null);

        setTitle("MqFileSharer");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        msg = new JLabel();


        pack();
        setMinimumSize(new Dimension(400, 400));
    }

}
