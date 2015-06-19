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
public class Operacje extends JPanel {
    JTable table;
/*
    JButton updateButton;
    JButton deleteButton;
    JButton planSerwisowyButton;
    String[] rejestracje;
*/
    Vector<String> columnNames;
    Vector<Vector> data;

    int przebieg;

    Operacje(int przebieg) {
        super();

        this.przebieg = przebieg;

        this.table = new JTable();
        this.table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        this.table.setFillsViewportHeight(true);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JScrollPane(this.table));
/*
        updateButton = new JButton();
        deleteButton = new JButton();
        planSerwisowyButton = new JButton();

        updateButton.setText("Modyfikuj");
        deleteButton.setText("Usuń");
        planSerwisowyButton.setText("Planowanie przeglądu");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    wyswietl();
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

        planSerwisowyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    planSerwisowy();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
*/

        try {
            wyswietl();
        } catch (SQLException e) {

        };
    }

    private void wyswietl() throws SQLException {
        JDBCConnector connector = new JDBCConnector();
        Connection connection;
        PreparedStatement ps;

        String query = "SELECT \"Operacja\".\"czynnosc\"" +
                "FROM \"Operacja\" NATURAL JOIN \"Pozycja_planu_serwisowego\"" +
                "WHERE \"Pozycja_planu_serwisowego\".\"przebieg\" = ?";
//        int[] selectedRows = new int[1];
//        selectedRows[0] = przebieg;
        try {
            DriverManager.registerDriver(new OracleDriver());
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "Cannot find the proper driver");
            System.exit(-1);
        }

        try {
            connection = connector.getConnection();
            ps = connection.prepareStatement(query);
            ps.setInt(1, przebieg);
//                System.out.println("execute operation");
//                System.out.println(przebieg);
            ResultSet rs = ps.executeQuery();
//            System.out.println("after");
            rs.next();
//            System.out.println(rs.getString(1));
            Vector<String> v = new Vector<String>();
            columnNames = new Vector<String>();
            data = new Vector<Vector>();
            columnNames.add("Wartość");
            v.add(rs.getString(1));
            data.add(v);
            table = new JTable(data, columnNames);
//            showTable();
            PojazdTable.start(table);
//                System.out.println(rs.getString(1));
        } catch (Exception e) {
            throw new SQLException("ERROR\nEmpty value");
        }
    }
/*
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

    private void planSerwisowy() throws Exception {
        JDBCConnector connector = new JDBCConnector();
        Connection connection;
        PreparedStatement ps;
        ResultSet rs;
        ResultSetMetaData md;

        int[] selectedRows = table.getSelectedRows();

        if (selectedRows.length != 1)
            throw new Exception("Należy wybrać jeden pojazd");

        String query = "SELECT \"przebieg\" FROM \"Pozycja_planu_serwisowego\"" +
                "WHERE \"id_modelu\" = (SELECT \"id_modelu\" FROM \"Model\" WHERE \"nazwa\" LIKE ? AND ROWNUM = 1) " +
                "GROUP BY \"przebieg\"";

        try {
            DriverManager.registerDriver(new OracleDriver());
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "Cannot find the proper driver");
            System.exit(-1);
        }

        System.out.println(table.getValueAt(selectedRows[0], 1));
        try {
            connection = connector.getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, table.getValueAt(selectedRows[0], 1).toString());
            rs = ps.executeQuery();
            System.out.println("query");

            columnNames = new Vector<String>();
            data = new Vector<Vector>();

            if (rs != null) {
                md = rs.getMetaData();
                Vector<Object> column;

                while (rs.next()) {
                    column = new Vector<Object>();
                    column.add(rs.getString(1));
                    data.add(column);
                }

                for (int i = 1; i < md.getColumnCount() + 1; i++)
                    columnNames.add(md.getColumnName(i));

                if (!columnNames.isEmpty())
                    table = new JTable(data, columnNames);
                else
                    columnNames.add("No rows extracted");

                table = new JTable(data, columnNames);
                PrzebiegiTable.start(table);
            }
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }
*/
    private void showTable() {
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        JFrame frame = new JFrame("Operacje table");
//        Operacje tablePane = new Operacje(przebieg);
        this.setOpaque(true);

        frame.setContentPane(this);
        frame.pack();
        frame.setVisible(true);
        System.out.println("Działa?");
    }

    public static void start(final int przebieg) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
//                System.out.println(przebieg);
                Operacje o = new Operacje(przebieg);
            }
        });
    }
}
