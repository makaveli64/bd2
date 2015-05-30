package przeglady;

import javax.swing.*;
import java.awt.*;

/**
 * Created by user on 30.05.15.
 */
public class Table extends JFrame {
    JTable table;
    JScrollPane pane;

    Table(JTable table) {
        this.table = table;
        this.table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        pane = new JScrollPane(this.table);
        this.table.setFillsViewportHeight(true);
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
