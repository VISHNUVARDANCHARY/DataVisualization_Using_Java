package datavisualPackage;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PieChart {
    private JTextField item;
    private JTextField amount;
    private JButton ADDDATAButton;
    private JPanel mainFrame;
    private JPanel panel2;
    private JButton PIECHARTButton;
    private JButton BARCHARTButton;
    private JPanel chartPanel;
    private JButton RESETButton;
    private DefaultPieDataset pieDataset;
    private DefaultCategoryDataset barDataset;
    private JFreeChart pieChart;
    private JFreeChart barChart;
    DefaultTableModel model;
    JTable table;
    JFrame frame;

    public PieChart() {
        initializeUI();
        addEventHandlers();
    }

    private void initializeUI() {
        frame = new JFrame("Pie Chart Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);

        mainFrame = new JPanel(new BorderLayout());
        panel2 = new JPanel(new BorderLayout());
        chartPanel = new JPanel();
        chartPanel.setLayout(new BoxLayout(chartPanel, BoxLayout.Y_AXIS));
        chartPanel = new JPanel(new BorderLayout());
        item = new JTextField(10);
        amount = new JTextField(10);
        ADDDATAButton = new JButton("Add Data");
        PIECHARTButton = new JButton("Show Pie Chart");
        BARCHARTButton = new JButton("Show Bar Chart");
        RESETButton = new JButton("Reset");

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.add(new JLabel("Item:"));
        inputPanel.add(item);
        inputPanel.add(new JLabel("Amount:"));
        inputPanel.add(amount);
        inputPanel.add(ADDDATAButton);
        inputPanel.add(PIECHARTButton);
        inputPanel.add(BARCHARTButton);
        inputPanel.add(RESETButton);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(inputPanel, BorderLayout.NORTH);
        leftPanel.add(panel2, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, chartPanel);
        splitPane.setDividerLocation(500);
        mainFrame.add(splitPane, BorderLayout.CENTER);

        frame.setContentPane(mainFrame);
        frame.setVisible(true);

        displayTable();
    }

    private void addEventHandlers() {
        ADDDATAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDataToTable();
            }
        });

        PIECHARTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPieChart();
            }
        });

        BARCHARTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBarChart();
            }
        });

        RESETButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
    }

    private void addDataToTable() {
        String itemName = item.getText();
        String amountData = amount.getText();
        try {
            Double.parseDouble(amountData); // Validate amount input
            Object[] data = {itemName, amountData};
            model.addRow(data);
            item.setText("");
            amount.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid amount.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void reset() {
        chartPanel.removeAll();
        panel2.removeAll();
        displayTable();
        frame.validate();
    }

    private void displayTable() {
        String[] columns = {"ITEMS", "AMOUNT"};
        model = new DefaultTableModel(null, columns);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel2.add(scrollPane, BorderLayout.CENTER);
    }

    private void showPieChart()
    {
        /*
        pieDataset = new DefaultPieDataset();
        int rowCount = table.getRowCount();
        int threshold = 15; // Example threshold
        double otherAmount = 0.0;

        for (int i = 0; i < rowCount; i++) {
            String name = table.getValueAt(i, 0).toString();
            Double amt = Double.valueOf(table.getValueAt(i, 1).toString());
            if (i < threshold) {
                pieDataset.setValue(name, amt);
            } else {
                otherAmount += amt;
            }
        }
        if (rowCount > threshold) {
            pieDataset.setValue("Other", otherAmount);
        }
        pieChart = ChartFactory.createPieChart("PIE CHART", pieDataset, true, true, true);
        pieChart.getPlot();
        chartPanel = new ChartPanel(pieChart);
        chartPanel.removeAll();
        chartPanel.add(chartPanel, BorderLayout.CENTER);
        frame.validate();
    */

        pieDataset = new DefaultPieDataset();
        for (int i = 0; i < table.getRowCount(); i++) {
            String name = table.getValueAt(i, 0).toString();
            Double amt = Double.valueOf(table.getValueAt(i, 1).toString());
            pieDataset.setValue(name, amt);
        }
        pieChart = ChartFactory.createPieChart("PIE CHART", pieDataset, true, true, true);
        ChartPanel pieChartPanel = new ChartPanel(pieChart);
        chartPanel.removeAll();
        chartPanel.add(pieChartPanel);
        frame.validate();

    }
    private void showBarChart() {
        barDataset = new DefaultCategoryDataset();
        for (int i = 0; i < table.getRowCount(); i++) {
            String name = table.getValueAt(i, 0).toString();
            Double amt = Double.valueOf(table.getValueAt(i, 1).toString());
            barDataset.addValue(amt, "Amount", name);
        }
        barChart = ChartFactory.createBarChart("BAR CHART", "Items", "Amount", barDataset);
        ChartPanel barChartPanel = new ChartPanel(barChart);
        chartPanel.add(barChartPanel);
        frame.validate();
    }

}

