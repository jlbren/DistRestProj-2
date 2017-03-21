package Data;

import Chaos.ChaosSource;

import java.sql.*;
import java.util.*;

public class PokemonDataAccessObject {
	private static final String TableName = "Pokemon";
	private static final String IdCol = "id"; 
	private static final String NameCol = "name" ; 
	private static final String DescCol = "description";
	private static final String NatureCol = "nature"; 
	

	public Collection<PokemonObject> ReadAllPokemon() throws Exception {
		List<PokemonObject> pokemons = new ArrayList<PokemonObject>();
		Database db = new Database();
		try{
			ChaosSource.ForDataAccess("Pokemon").ForMethod("ReadAllPokemon").run();
			db.ExecuteReader("Select * from " + TableName, (ResultSet row)->{
				long id = row.getLong(row.findColumn(IdCol));
				String name = row.getString(row.findColumn(NameCol)); 
				String description = row.getString(row.findColumn(DescCol));
				String nature = row.getString(row.findColumn(NatureCol)); 
				PokemonObject pokemon = new PokemonObject(id, name, description, nature);
				pokemons.add(pokemon);
			});
		} finally {
			db.Close();
		}
		return pokemons;
	}
	public void Insert(PokemonObject newObj) throws Exception {
		Database db = new Database();
		try {
			ChaosSource.ForDataAccess("Pokemon").ForMethod("Insert").run();
			String stm = "insert into " + TableName + 
					" (" + IdCol + ", " + NameCol + ", " + DescCol + ", " + NatureCol +") " + 
					"values (?, ?, ?, ?)";
			Object[] parameters = new Object[]{ newObj.Id, newObj.Name, newObj.Description, newObj.Nature };
			db.RunNonQuery(stm, parameters);
		} finally {
			db.Close();
		}
	}
	
	public void Update(PokemonObject pokemon) throws Exception {
		Database db = new Database();
		try {
			ChaosSource.ForDataAccess("Pokemon").ForMethod("Update").run();
			String stm = "update " + TableName + " " +
					"set " + NameCol + " = ?, " 
						   + DescCol + " = ?, " 
						   + NatureCol + " = ? " +
				    "where " + IdCol + " = ?";
			Object[] parameters = new Object[]{pokemon.Name, pokemon.Description, pokemon.Nature, pokemon.Id};
			db.RunNonQuery(stm, parameters);
		} finally {
			db.Close();
		}
	}
	

	public void Delete(long id) throws Exception {
		Database db = new Database();
		try {
			ChaosSource.ForDataAccess("Pokemon").ForMethod("Delete").run();
			String stm = "delete from " + TableName + " where " + IdCol + " = ?";
			Object[] parameters = new Object[]{id};
			db.RunNonQuery(stm, parameters);
		} finally {
			db.Close();
		}
		
	}
}
