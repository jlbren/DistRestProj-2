package Data;

import java.sql.*;

public class ConnectionsFactory {
	static final String JDBC_DRIVER = "org.sqlite.JDBC";
	static final String DB_URL = "jdbc:sqlite:Pokedex.db";

//	static final String USER = "root";
//	static final String PASS = "class";

	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName(JDBC_DRIVER);
		Connection connection = DriverManager.getConnection(DB_URL);
		SetCommit(connection);
		SetIsolationLevel(connection, Connection.TRANSACTION_SERIALIZABLE);
		return connection;
	}

	private static void SetCommit(Connection connection) throws SQLException {
		connection.setAutoCommit(false);
		boolean mode = connection.getAutoCommit();
		if (mode) {
			throw new RuntimeException("Failed to set autocommit");
		}
	}

	private static void SetIsolationLevel(Connection connection, int level) throws SQLException {
		connection.setTransactionIsolation(level);
		int setLevel = connection.getTransactionIsolation();
		if (setLevel != level) {
			throw new RuntimeException("failed to set isolation level");
		}
	}
}