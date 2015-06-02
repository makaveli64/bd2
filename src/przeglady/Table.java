package przeglady;

import javax.swing.*;
import java.awt.*;

/**
 * Created by user on 30.05.15.
 */
public class Table extends JFrame {
    JTable table;

    Table(JTable table) {
        setTitle("Table");
        this.table = table;
//        this.table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        this.table.setFillsViewportHeight(true);
        add(new JScrollPane(this.table));
        pack();
    }

    public static void start(final JTable table) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Table(table).setVisible(true);
            }
        });
    }
}
