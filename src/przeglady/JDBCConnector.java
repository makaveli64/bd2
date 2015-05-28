package przeglady;

import java.sql.*;
import oracle.jdbc.driver.*;

public class JDBCConnector {
	public Connection connection;

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
