package przeglady;

import oracle.jdbc.OracleDriver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

/**
 * Created by user
 */
public class PrzegladyTable extends JPanel {
    JTable table;
//    JButton insertButton;
//    JButton deleteButton;
    JButton nextButton;

    String[] rejestracje;
//    Vector<String> columnNames;
//    Vector<Vector> data;

    PrzegladyTable(JTable table) {
        super();

        this.table = table;
        this.table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        this.table.setFillsViewportHeight(true);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JScrollPane(this.table));
//        insertButton = new JButton();
//        deleteButton = new JButton();
        nextButton = new JButton();
//        insertButton.setText("Dodaj");
//        deleteButton.setText("Usuń");
        nextButton.setText("Dalej");

        JPanel buttonPanel = new JPanel();
//        buttonPanel.add(insertButton);
//        buttonPanel.add(deleteButton);
        buttonPanel.add(nextButton);
        add(buttonPanel);
        System.out.println(table.getSelectedRow());
//        System.out.println(table.getValueAt(table.getSelectedRow(), 1));

//        rejestracje = new String[this.table.getRowCount()];

//        for (int i = 0; i < rejestracje.length; i++)
//            rejestracje[i] = this.table.getValueAt(i, 0).toString();

/*
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    update();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    delete();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
*/
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

/*
    private void update() throws SQLException {
        JDBCConnector connector = new JDBCConnector();
        Connection connection;
        PreparedStatement ps;

        int[] selectedRows = table.getSelectedRows();

        for (int i = 0; i < selectedRows.length; i++) {
            String query = "UPDATE \"Pojazd\" SET \"rejestracja\" = ?, \"id_typu_pojazdu\" = ?, \"id_modelu\" = ?, \"data_produkcji\" = ? WHERE \"rejestracja\" = ?";
            try {
                DriverManager.registerDriver(new OracleDriver());
            } catch (SQLException e) {
                System.out.println(e.getMessage() + "Cannot find the proper driver");
                System.exit(-1);
            }

            try {
                connection = connector.getConnection();
                ps = connection.prepareStatement(query);
                ps.setString(1, table.getValueAt(selectedRows[i], 0).toString());
                ps.setString(2, table.getValueAt(selectedRows[i], 1).toString());
                ps.setString(3, table.getValueAt(selectedRows[i], 2).toString());
                ps.setString(4, rejestracje[selectedRows[i]]);
                ps.executeQuery();
            } catch (Exception e) {
                throw new SQLException(e);
            }
        }
    }

    private void delete() throws SQLException {
        JDBCConnector connector = new JDBCConnector();
        Connection connection;
        PreparedStatement ps;

        int[] selectedRows = table.getSelectedRows();

        for (int i = 0; i < selectedRows.length; i++) {
            String query = "DELETE FROM \"Pojazd\" WHERE \"rejestracja\" = ?";
            try {
                DriverManager.registerDriver(new OracleDriver());
            } catch (SQLException e) {
                System.out.println(e.getMessage() + "Cannot find the proper driver");
                System.exit(-1);
            }

            try {
                connection = connector.getConnection();
                ps = connection.prepareStatement(query);
                ps.setString(1, rejestracje[selectedRows[i]]);
                ps.executeQuery();
            } catch (Exception e) {
                throw new SQLException(e);
            }
        }
    }
*/

    private void przebieg() throws Exception {
//        Operacje.start(Integer.parseInt(table.getValueAt(table.getSelectedRow(), 1).toString()));
        Operacje.start(10);
//        System.out.println(table.getValueAt(table.getSelectedRow(), 1));
/*
        JDBCConnector connector = new JDBCConnector();
        Connection connection;
        PreparedStatement ps;
        ResultSet rs;
        ResultSetMetaData md;

        int[] selectedRows = table.getSelectedRows();
        System.out.println(selectedRows.length);

        if (selectedRows.length != 1)
            throw new Exception("Należy wybrać jeden pojazd");

        String query = "SELECT \"przebieg\", \"nazwa\" FROM \"Pozycja_planu_serwisowego\" PPS " +
                "JOIN \"Pojazd\" P ON PPS.\"id_modelu\" = P.\"id_modelu\" " +
                "JOIN \"Model\" M ON P.\"id_modelu\" = M.\"id_modelu\" " +
                "WHERE \"rejestracja\" LIKE ?";

        try {
            DriverManager.registerDriver(new OracleDriver());
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "Cannot find the proper driver");
            System.exit(-1);
        }
//        System.out.println(rejestracje[selectedRows[0]]);
        try {
            connection = connector.getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, rejestracje[selectedRows[0]]);
            rs = ps.executeQuery();

            columnNames = new Vector<String>();
            data = new Vector<Vector>();

            if (rs != null) {
                md = rs.getMetaData();
                Vector<Object> column;

                while (rs.next()) {
                    column = new Vector<Object>();
                    for (int i = 1; i < md.getColumnCount() + 1; i++)
                        column.add(rs.getString(i));
                    data.add(column);
                }

                for (int i = 1; i < md.getColumnCount() + 1; i++)
                    columnNames.add(md.getColumnName(i));

                if (!columnNames.isEmpty())
                    table = new JTable(data, columnNames);
                else
                    columnNames.add("No rows extracted");

                table = new JTable(data, columnNames);
                PrzegladyTable.start(table);
            }
        } catch (Exception e) {
            throw new SQLException(e);
        }
*/
    }

    private static void showTable(JTable table) {
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        JFrame frame = new JFrame("Przeglądy table");

        PrzegladyTable tablePane = new PrzegladyTable(table);
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
