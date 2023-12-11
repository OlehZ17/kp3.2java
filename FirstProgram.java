import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class FirstProgram {
    public static void main(String[] args) {
        InitComponent();
    }

    public static void InitComponent() {
        JFrame jFrame = getFrame();
        JPanel jPanel = new JPanel();
        jFrame.add(jPanel);

        JPanel checkBoxPanel = new JPanel();
        jPanel.add(checkBoxPanel);

        JCheckBox checkBox1 = new JCheckBox("Button not available");
        JCheckBox checkBox2 = new JCheckBox("Panel visible");

        checkBoxPanel.add(checkBox1);
        checkBoxPanel.add(checkBox2);

        JPanel radioButtonPanel = new JPanel();
        jPanel.add(radioButtonPanel);

        ButtonGroup buttonGroup = new ButtonGroup();

        JRadioButton radioButton1 = new JRadioButton("41");
        JRadioButton radioButton2 = new JRadioButton("62");
        JRadioButton radioButton3 = new JRadioButton("83");

        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);
        buttonGroup.add(radioButton3);

        radioButtonPanel.add(radioButton1);
        radioButtonPanel.add(radioButton2);
        radioButtonPanel.add(radioButton3);

        checkBox1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                try {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        SendMessage("Button", "enabled");
                    } else {
                        SendMessage("Button", "disabled");
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        checkBox2.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                try {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        SendMessage("Panel", "visible");
                    } else {
                        SendMessage("Panel", "invisible");
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        ActionListener radioListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (radioButton1.isSelected()) {
                        SendMessage("Panel", "41");
                    } else if (radioButton2.isSelected()) {
                        SendMessage("Panel", "62");
                    } else if (radioButton3.isSelected()) {
                        SendMessage("Panel", "83");
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };

        radioButton1.addActionListener(radioListener);
        radioButton2.addActionListener(radioListener);
        radioButton3.addActionListener(radioListener);
        checkBox2.setSelected(true);
        radioButton1.setSelected(true);
    }

    private static JFrame getFrame() {
        JFrame jFrame = new JFrame();
        jFrame.setTitle("Client");
        jFrame.setVisible(true);
        jFrame.setSize(500, 200);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        return jFrame;
    }

    public static void SendMessage(String object, String state) throws IOException {
        try (Socket s = new Socket("localhost", 4000);
             ObjectOutputStream outputStream = new ObjectOutputStream(s.getOutputStream())) {

            outputStream.writeObject(object);
            outputStream.writeObject(state);
            outputStream.flush();
        }
    }
}

