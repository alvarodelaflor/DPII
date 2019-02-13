/* QueryDatabase.java
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package utilities;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import utilities.internal.ConsoleReader;
import utilities.internal.CustomToStringBuilder;
import utilities.internal.DatabaseUtil;

public class QueryDatabase {
	
	public static void main(String[] args) throws Throwable {
		DatabaseUtil databaseUtil;
		ConsoleReader reader;
		String line;
		boolean quit;
		
		databaseUtil = null;
		
		try {
			System.out.printf("QueryDatabase 1.6%n");
			System.out.printf("-----------------%n%n");
			
			System.out.printf("Initialising persistence context `%s'...%n", DatabaseConfig.PersistenceUnit);
			databaseUtil = new DatabaseUtil();
			
			reader = new ConsoleReader();
			
			do {
				line = reader.readCommand();
				quit = interpretLine(databaseUtil, line);
			} while (!quit);
		} catch (Throwable oops) {
			System.out.flush();
			System.err.printf("%n%s%n", oops.getLocalizedMessage());
			//oops.printStackTrace(System.out);			
		} finally {					
			databaseUtil.close();
		}
	}

	private static boolean interpretLine(DatabaseUtil databaseUtil, String line) {
		boolean result;
		String command;
		List<?> objects;
		int affected;
		
		result = false;		
		try {				
			command = StringUtils.substringBefore(line, " ");
			switch (command) {				
				case "quit":
					result = true;						
					break;
				case "begin": 
					databaseUtil.openTransaction();
					System.out.println("Transaction started");
					break;
				case "commit":
					databaseUtil.commitTransaction();
					System.out.println("Transaction committed");
					break;
				case "rollback": 
					databaseUtil.rollbackTransaction();
					System.out.println("Transaction rollbacked");
					break;
				case "update":
				case "delete": 
					affected = databaseUtil.executeUpdate(line);
					System.out.printf("%d objects affected%n", affected);
					break;
				case "select": 
					objects = databaseUtil.executeSelect(line);			
					System.out.printf("%d objects found%n", objects.size());	
					printResultList(objects);
					break;
				default:
					System.err.println("Command not understood");
			}
		} catch (Throwable oops) {	
			System.err.println(oops.getMessage());
			// oops.printStackTrace(System.err);
		}
		
		return result;
	}

	private static void printResultList(List<?> objects) {
		String text;		
		Object obj;
		
		for (int i = 0; i < objects.size(); i++) {
			obj = objects.get(i);
			text = CustomToStringBuilder.toString(obj);
			System.out.printf("Object #%d = %s %n", i, text);			
		}
	}

}
