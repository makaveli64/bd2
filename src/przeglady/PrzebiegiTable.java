package przeglady;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by user
 */
public class PrzebiegiTable extends JPanel {
    JTable table;
    JButton nextButton;
//    String[] rejestracje;

    PrzebiegiTable(JTable table) {
        super();

        this.table = table;
        this.table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        this.table.setFillsViewportHeight(true);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JScrollPane(this.table));
        nextButton = new JButton();
        nextButton.setText("Dalej");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(nextButton);
        add(buttonPanel);

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    przebieg();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    private void przebieg() throws Exception {
        int[] selectedRows = table.getSelectedRows();
/*
        System.out.println(selectedRows[0]);
        System.out.println(table.getValueAt(selectedRows[0], 0).toString());
        Operacje.start(Integer.parseInt(table.getValueAt(table.getSelectedRow(), 1).toString()));
*/
        Operacje.start(Integer.parseInt(table.getValueAt(selectedRows[0], 0).toString()));
    }

    private static void showTable(JTable table) {
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        JFrame frame = new JFrame("Przeglądy table");

        PrzebiegiTable tablePane = new PrzebiegiTable(table);
        tablePane.setOpaque(true);

        frame.setContentPane(tablePane);
        frame.pack();
        frame.setVisible(true);
    }

    public static void start(final JTable table) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                showTable(table);
            }
        });
    }
}
