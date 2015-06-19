package przeglady;

import java.sql.*;

public class JDBCConnector {
    private static Connection connection;

    public Connection getConnection() throws SQLException {
        String url = "jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf";
        String username = "idzemian";
        String password = "idzemian";

        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                throw new SQLException(e.getMessage());
            }
        }

        return connection;
    }

	public void close() {
		if (connection != null) {
			try {
				connection.close();
			} catch (Exception e) {
				System.out.println(e.getMessage() + "Cannot close the connection");
			}
		}
	}

	protected void finalize() throws Throwable {
		close();
		super.finalize();
	}
}
