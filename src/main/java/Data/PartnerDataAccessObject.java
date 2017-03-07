package Data;

import java.sql.*;
import java.util.*;

public class PartnerDataAccessObject {
	private static final String TableName = "Partners";
	private static final String IdCol = "id"; 
	private static final String TrainerCol = "trainerId"; 
	private static final String PkmnCol = "pkmnId";
	private static final String LocCol = "location"; 
	

	public Collection<PartnerObject> ReadAllPartners() throws Exception {
		List<PartnerObject> partners = new ArrayList<PartnerObject>();
		Database db = new Database();
		try{
			db.ExecuteReader("Select * from " + TableName, (ResultSet row)->{
				long id = row.getLong(row.findColumn(IdCol));
				long trainerId = row.getLong(row.findColumn(TrainerCol)); 
				long pkmnId = row.getLong(row.findColumn(PkmnCol));
				String location           = row.getString(row.findColumn(LocCol)); 
				PartnerObject partner = new PartnerObject(id, trainerId, pkmnId, location);
				partners.add(partner);
			});
		} finally {
			db.Close();
		}
		return partners;
	}
	public void Insert(PartnerObject newObj) throws Exception {
		Database db = new Database();
		try {
			String stm = "insert into " + TableName + 
					" (" + IdCol + ", " + TrainerCol + ", " + PkmnCol + ", " + LocCol +") " + 
					"values (?, ?, ?, ?)";
			Object[] parameters = new Object[]{ newObj.Id, newObj.trainerId, newObj.pkmnId, newObj.Location };
			db.RunNonQuery(stm, parameters);
		} finally {
			db.Close();
		}
	}
	
	public void Update(PartnerObject partner) throws Exception {
		Database db = new Database();
		try {
			String stm = "update " + TableName + " " +
					"set " + TrainerCol + " = ?, " 
						   + PkmnCol + " = ?, " 
						   + LocCol + " = ? " +
				    "where " + IdCol + " = ?";
			Object[] parameters = new Object[]{partner.trainerId, partner.pkmnId, partner.Location, partner.Id};
			db.RunNonQuery(stm, parameters);
		} finally {
			db.Close();
		}
	}
	

	public void Delete(long id) throws Exception {
		Database db = new Database();
		try {
			String stm = "delete from " + TableName + " where " + IdCol + " = ?";
			Object[] parameters = new Object[]{id};
			db.RunNonQuery(stm, parameters);
		} finally {
			db.Close();
		}
		
	}
}
