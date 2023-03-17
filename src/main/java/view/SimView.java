package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;

public class SimView extends JFrame {

    private JPanel contentPane;
    private JTextPane resultPane;

    public SimView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 495, 541);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("QUEUES MANAGEMENT SIMULATOR");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblNewLabel.setBounds(10, 11, 459, 25);
        contentPane.add(lblNewLabel);

        resultPane = new JTextPane();
        resultPane.setEditable(false);
        resultPane.setFont(new Font("Tahoma", Font.PLAIN, 14));
        resultPane.setBounds(10, 47, 459, 444);
        contentPane.add(resultPane);
    }

    public void setResultPane(String result) {
        resultPane.setText(result);
    }

    public void finalResult(String result) {
        JOptionPane.showMessageDialog(this, result, "Final Results", JOptionPane.INFORMATION_MESSAGE);
    }

}
