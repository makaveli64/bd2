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

    JButton updateButton;
    JButton deleteButton;
    JButton planSerwisowyButton;

    String[] rejestracje;
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

        updateButton = new JButton();
        deleteButton = new JButton();
        planSerwisowyButton = new JButton();

        updateButton.setText("Modyfikuj");
        deleteButton.setText("Usuń");
        planSerwisowyButton.setText("Planowanie przeglądu");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        JPanel buttonPanel2 = new JPanel();
        buttonPanel2.add(planSerwisowyButton);

        add(buttonPanel);
        add(buttonPanel2);


//        rejestracje = new String[this.table.getRowCount()];

//        for (int i = 0; i < rejestracje.length; i++)
//            rejestracje[i] = this.table.getValueAt(i, 0).toString();

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

        int[] selectedRows = new int[1];
        selectedRows[0] = przebieg;

        for (int i = 0; i < selectedRows.length; i++) {
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
//                ps.setString(2, "%" + table.getValueAt(selectedRows[i], 1).toString() + "%");
//                ps.setString(3, "%" + table.getValueAt(selectedRows[i], 2).toString() + "%");

/*
                if (table.getValueAt(selectedRows[i], 3) != null)
                    ps.setString(4, table.getValueAt(selectedRows[i], 3).toString());
                else
                    ps.setString(4, "");

                ps.setString(5, rejestracje[selectedRows[i]]);
*/
                System.out.println("execute operation");
                System.out.println(przebieg);
                ResultSet rs = ps.executeQuery();
                System.out.println("after");
                rs.next();
                columnNames.add("Wartość");
                Vector<String> v = new Vector<String>();
                v.add(rs.getString(1));
                data.add(v);
                this.table = new JTable(data, columnNames);
                PojazdTable.start(this.table);
//                System.out.println(rs.getString(1));
            } catch (Exception e) {
                throw new SQLException("ERROR\nEmpty value");
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

    private void planSerwisowy() throws Exception {
        JDBCConnector connector = new JDBCConnector();
        Connection connection;
        PreparedStatement ps;
        ResultSet rs;
        ResultSetMetaData md;

        int[] selectedRows = table.getSelectedRows();
//        System.out.println(selectedRows.length);

        if (selectedRows.length != 1)
            throw new Exception("Należy wybrać jeden pojazd");

/*
        String query = "SELECT \"przebieg\" FROM \"Pozycja_planu_serwisowego\" PPS " +
                "JOIN \"Pojazd\" P ON PPS.\"id_modelu\" = P.\"id_modelu\" " +
                "JOIN \"Model\" M ON P.\"id_modelu\" = M.\"id_modelu\" " +
                "WHERE \"rejestracja\" LIKE ?";
*/
        String query = "SELECT \"przebieg\" FROM \"Pozycja_planu_serwisowego\"" +
                "WHERE \"id_modelu\" = (SELECT \"id_modelu\" FROM \"Model\" WHERE \"nazwa\" LIKE ? AND ROWNUM = 1) " +
                "GROUP BY \"przebieg\"";

        try {
            DriverManager.registerDriver(new OracleDriver());
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "Cannot find the proper driver");
            System.exit(-1);
        }

//        for (int i = 0; i < selectedRows.length; i++)
//            System.out.println(String.valueOf(selectedRows[i]));

        System.out.println(table.getValueAt(selectedRows[0], 1));
//        System.out.println(rejestracje[selectedRows[0]]);
        try {
            connection = connector.getConnection();
            ps = connection.prepareStatement(query);
//            ps.setString(1, rejestracje[selectedRows[0]]);
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
//                    data.add(rs.getString(4));
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
    }

    private static void showTable(int przebieg) {
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        JFrame frame = new JFrame("Operacje table");

        Operacje tablePane = new Operacje(przebieg);
        tablePane.setOpaque(true);

        frame.setContentPane(tablePane);
        frame.pack();
        frame.setVisible(true);
    }

    public static void start(final int przebieg) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                System.out.println(przebieg);
                showTable(przebieg);
            }
        });
    }
}
