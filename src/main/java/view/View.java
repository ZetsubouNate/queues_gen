package view;

import businessLogic.SelectionPolicy;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.ActionListener;

public class View extends JFrame {

    private JPanel contentPane;
    private JTextField clientsNo;
    private JTextField queuesNo;
    private JTextField simInt;
    private JTextField minArrival;
    private JTextField minService;
    private JTextField maxService;
    private JTextField maxArrival;
    private JButton startSimButton;
    private JComboBox strategyPick;

    public View() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 538, 555);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("QUEUES MANAGEMENT SIMULATOR");
        lblNewLabel.setBounds(86, 11, 351, 30);
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Clients No.");
        lblNewLabel_1.setBounds(10, 91, 130, 23);
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Queues No.");
        lblNewLabel_2.setBounds(183, 91, 130, 23);
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Simulation Interval");
        lblNewLabel_3.setBounds(358, 91, 130, 22);
        lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(lblNewLabel_3);

        clientsNo = new JTextField();
        clientsNo.setBounds(32, 132, 86, 20);
        clientsNo.setFont(new Font("Tahoma", Font.PLAIN, 14));
        clientsNo.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(clientsNo);
        clientsNo.setColumns(10);

        queuesNo = new JTextField();
        queuesNo.setBounds(204, 132, 86, 20);
        queuesNo.setHorizontalAlignment(SwingConstants.CENTER);
        queuesNo.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(queuesNo);
        queuesNo.setColumns(10);

        simInt = new JTextField();
        simInt.setBounds(382, 132, 86, 20);
        simInt.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(simInt);
        simInt.setColumns(10);

        JLabel lblNewLabel_4 = new JLabel("Strategy");
        lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_4.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_4.setBounds(41, 205, 99, 23);
        contentPane.add(lblNewLabel_4);

        strategyPick = new JComboBox();
        strategyPick.setModel(new DefaultComboBoxModel(new String[] {"Time Priority", "Queue Priority"}));
        strategyPick.setFont(new Font("Tahoma", Font.PLAIN, 14));
        strategyPick.setBounds(150, 203, 140, 30);
        strategyPick.setSelectedIndex(-1);
        contentPane.add(strategyPick);

        JLabel lblNewLabel_5 = new JLabel("Min. Arrival Time");
        lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_5.setBounds(10, 297, 130, 23);
        contentPane.add(lblNewLabel_5);

        JLabel lblNewLabel_6 = new JLabel("Min. Service Time");
        lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_6.setBounds(10, 357, 130, 23);
        contentPane.add(lblNewLabel_6);

        minArrival = new JTextField();
        minArrival.setFont(new Font("Tahoma", Font.PLAIN, 14));
        minArrival.setBounds(151, 297, 86, 23);
        contentPane.add(minArrival);
        minArrival.setColumns(10);

        minService = new JTextField();
        minService.setFont(new Font("Tahoma", Font.PLAIN, 14));
        minService.setColumns(10);
        minService.setBounds(150, 357, 86, 23);
        contentPane.add(minService);

        JLabel lblNewLabel_5_1 = new JLabel("Max. Arrival Time");
        lblNewLabel_5_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_5_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_5_1.setBounds(261, 297, 130, 23);
        contentPane.add(lblNewLabel_5_1);

        maxService = new JTextField();
        maxService.setFont(new Font("Tahoma", Font.PLAIN, 14));
        maxService.setColumns(10);
        maxService.setBounds(401, 357, 86, 23);
        contentPane.add(maxService);

        maxArrival = new JTextField();
        maxArrival.setFont(new Font("Tahoma", Font.PLAIN, 14));
        maxArrival.setColumns(10);
        maxArrival.setBounds(402, 297, 86, 23);
        contentPane.add(maxArrival);

        JLabel lblNewLabel_6_1 = new JLabel("Max. Service Time");
        lblNewLabel_6_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_6_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_6_1.setBounds(261, 357, 130, 23);
        contentPane.add(lblNewLabel_6_1);

        startSimButton = new JButton("Start Simulation");
        startSimButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        startSimButton.setBounds(167, 429, 183, 47);
        contentPane.add(startSimButton);
    }

    public String getClientsNo() {
        return this.clientsNo.getText();
    }

    public String getQueuesNo() {
        return queuesNo.getText();
    }

    public String getSimInt() {
        return simInt.getText();
    }

    public String getMinArrival() {
        return minArrival.getText();
    }

    public String getMinService() {
        return minService.getText();
    }

    public String getMaxService() {
        return maxService.getText();
    }

    public String getMaxArrival() {
        return maxArrival.getText();
    }

    public void startSimActionListener(ActionListener actionListener) {
        this.startSimButton.addActionListener(actionListener);
    }

    public SelectionPolicy getStrategyPick() {
        if(this.strategyPick.getSelectedIndex() == 0) {
            return SelectionPolicy.SHORTEST_TIME;
        }
        else
            return SelectionPolicy.SHORTEST_QUEUE;
    }

    public void errorResult(String result) {
        JOptionPane.showMessageDialog(this, result, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
