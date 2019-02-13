/*
 * PopulateDatabase.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package utilities;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Entity;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import utilities.internal.DatabaseUtil;
import utilities.internal.EclipseConsole;
import utilities.internal.ThrowablePrinter;
import domain.DomainEntity;

public class PopulateDatabase {

	public static void main(final String[] args) {
		DatabaseUtil databaseUtil;
		ApplicationContext populationContext;
		Map<String, Object> entityMap;
		List<Entry<String, Object>> sortedEntities;

		EclipseConsole.fix();
		LogManager.getLogger("org.hibernate").setLevel(Level.OFF);
		databaseUtil = null;

		try {
			System.out.println("PopulateDatabase 1.9");
			System.out.println("--------------------");
			System.out.println();

			System.out.printf("Initialising persistence context `%s'.%n", DatabaseConfig.PersistenceUnit);
			databaseUtil = new DatabaseUtil();
			databaseUtil.open();

			System.out.printf("Creating database `%s' (%s).%n", databaseUtil.getDatabaseName(), databaseUtil.getDatabaseDialectName());
			databaseUtil.recreateDatabase();

			System.out.print("Reading web of entities from `PopulateDatabase.xml'");
			populationContext = new ClassPathXmlApplicationContext("classpath:PopulateDatabase.xml");
			entityMap = populationContext.getBeansWithAnnotation(Entity.class);
			System.out.printf(" (%d entities found).%n", entityMap.size());

			System.out.println("Computing a topological order for your entities.");
			sortedEntities = PopulateDatabase.sort(databaseUtil, entityMap);

			System.out.println("Trying to save the best order found.");
			PopulateDatabase.persist(databaseUtil, sortedEntities);
		} catch (final Throwable oops) {
			ThrowablePrinter.print(oops);
		} finally {
			if (databaseUtil != null) {
				System.out.println("Closing persistence context.");
				databaseUtil.close();
			}
		}
	}

	protected static List<Entry<String, Object>> sort(final DatabaseUtil databaseUtil, final Map<String, Object> entityMap) {
		LinkedList<Entry<String, Object>> result;
		LinkedList<Entry<String, Object>> cache;
		Entry<String, Object> entry;
		DomainEntity entity;
		int passCounter;
		boolean done;

		result = new LinkedList<Entry<String, Object>>();
		result.addAll(entityMap.entrySet());
		cache = new LinkedList<Entry<String, Object>>();
		passCounter = 0;

		do {
			try {
				databaseUtil.openTransaction();
				PopulateDatabase.cleanEntities(result);

				while (!result.isEmpty()) {
					entry = result.getFirst();
					entity = (DomainEntity) entry.getValue();
					databaseUtil.persist(entity);
					result.removeFirst();
					cache.addLast(entry);
				}
				databaseUtil.undoTransaction();
				done = true;
				result.addAll(cache);
				cache.clear();
			} catch (final Throwable oops) {
				databaseUtil.undoTransaction();
				done = (passCounter >= entityMap.size() - 1);
				entry = result.removeFirst();
				cache.addAll(result);
				cache.addLast(entry);
				result.clear();
				result.addAll(cache);
				cache.clear();
			}
			passCounter++;
		} while (!done);

		PopulateDatabase.cleanEntities(result);

		return result;
	}

	protected static void persist(final DatabaseUtil databaseUtil, final List<Entry<String, Object>> sortedEntities) {
		String name;
		DomainEntity entity;

		System.out.println();
		databaseUtil.openTransaction();
		for (final Entry<String, Object> entry : sortedEntities) {
			name = entry.getKey();
			entity = (DomainEntity) entry.getValue();

			System.out.printf("> %s", name);
			databaseUtil.persist(entity);
			System.out.printf(": %s%n", entity.toString());
			// TODO: print the entity using SchemaPrinter.  This should get a map in which 
			// every persisted entity is mapped onto the corresponding bean name in the 
			// PopulateDatabase.xml file; otherwise traceability will be a nightmare.
		}
		databaseUtil.closeTransaction();
		System.out.println();
	}

	protected static void cleanEntities(final LinkedList<Entry<String, Object>> result) {
		for (final Entry<String, Object> entry : result) {
			DomainEntity entity;

			entity = (DomainEntity) entry.getValue();
			entity.setId(0);
			entity.setVersion(0);
		}
	}

}
