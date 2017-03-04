package Data;

import java.sql.*;

public class Database {

   private final Connection _connection;
   
   public Database() throws ClassNotFoundException, SQLException {
	    _connection = ConnectionsFactory.getConnection();
   }
   
   public void ExecuteReader(String statement, IRowReader reader) throws SQLException {
	   Statement stm = _connection.createStatement();
	   try{
		   ResultSet set = stm.executeQuery(statement);
		   try {
			   while(set.next()) {
				   	reader.Read(set);
			   }
			   
		   } finally {
			   set.close();
		   }
		   _connection.commit();
	   } catch(Exception e){
		   _connection.rollback();
		   throw new SQLException(e.getMessage(), e);
	   } finally {
		   stm.close();
	   }
   }
   
   public void RunNonQuery(String statement) throws SQLException {
	   RunNonQuery(statement, new Object[0]);
   }
   
   public void RunNonQuery(String statement, Object[] args) throws SQLException {
	   PreparedStatement stm = _connection.prepareStatement(statement);
	   for(int i = 1; i <= args.length; i++) {
		   Object value = args[i-1];
		   if(value instanceof String) {
			   stm.setString(i, (String)value);
		   } else if(value instanceof Long) {
			   stm.setLong(i, (long)value);
		   } else {
			   throw new RuntimeException("Unexpected type: " + value.getClass().getSimpleName());
		   }
	   }
	   try {
		   int rowsUpdated = stm.executeUpdate();
		   if(rowsUpdated <= 0) {
			   throw new SQLException("updated rows: " + rowsUpdated);
		   }
		   _connection.commit();
	   } catch(Exception e) {
		   _connection.rollback();
		   throw new SQLException(e.getMessage(), e);
	   } finally {
		   stm.close();
	   }
   }

	public void Close() throws SQLException {
		_connection.close();
	}
}