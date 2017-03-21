package Data;

import Chaos.ChaosSource;

import java.sql.*;
import java.util.*;

public class TrainerDataAccessObject {
	private static final String TableName = "Trainer";
	private static final String IdCol = "id"; 
	private static final String NameCol = "name" ; 
	private static final String BadgeCol = "badges"; 
	

	public Collection<TrainerObject> ReadAllTrainers() throws Exception {
		List<TrainerObject> trainers = new ArrayList<TrainerObject>();
		Database db = new Database();
		try{
			ChaosSource.ForDataAccess("Trainer").ForMethod("ReadAllTrainers").run();
			db.ExecuteReader("Select * from " + TableName, (ResultSet row)->{
				long id = row.getLong(row.findColumn(IdCol));
				String name = row.getString(row.findColumn(NameCol)); 
				int badges = row.getInt(row.findColumn(BadgeCol)); 
				TrainerObject trainer = new TrainerObject(id, name, badges);
				trainers.add(trainer);
			});
		} finally {
			db.Close();
		}
		return trainers;
	}
	
	public void Insert(TrainerObject newObj) throws Exception {
		Database db = new Database();
		try {
			ChaosSource.ForDataAccess("Trainer").ForMethod("Insert").run();
			String stm = "insert into " + TableName + 
					" (" + IdCol + ", " + NameCol + ", " + BadgeCol +") " + 
					"values (?, ?, ?)";
			Object[] parameters = new Object[]{ newObj.Id, newObj.Name, newObj.Badges };
			db.RunNonQuery(stm, parameters);
		} finally {
			db.Close();
		}
	}
	
	public void Update(TrainerObject trainer) throws Exception {
		Database db = new Database();
		try {
			ChaosSource.ForDataAccess("Trainer").ForMethod("Update").run();
			String stm = "update " + TableName + " " +
					"set " + NameCol + " = ?, " 
						   + BadgeCol+ " = ? " +
				    "where " + IdCol + " = ?";
			Object[] parameters = new Object[]{trainer.Name, trainer.Badges, trainer.Id};
			db.RunNonQuery(stm, parameters);
		} finally {
			db.Close();
		}
	}
	

	public void Delete(long id) throws Exception {
		Database db = new Database();
		try {
			ChaosSource.ForDataAccess("Trainer").ForMethod("Delete").run();
			String stm = "delete from " + TableName + " where " + IdCol + " = ?";
			Object[] parameters = new Object[]{id};
			db.RunNonQuery(stm, parameters);
		} finally {
			db.Close();
		}
		
	}


}
