package Data;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IRowReader {
	void Read(ResultSet set) throws SQLException;
}


