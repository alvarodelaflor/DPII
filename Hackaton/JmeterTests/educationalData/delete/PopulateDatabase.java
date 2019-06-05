/*
 * PopulateDatabase.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package utilities;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import utilities.internal.DatabaseUtil;
import utilities.internal.EclipseConsole;
import utilities.internal.SchemaPrinter;
import utilities.internal.ThrowablePrinter;
import domain.DomainEntity;

public class PopulateDatabase {

	public static void main(final String[] args) {
		DatabaseUtil databaseUtil;
		ApplicationContext populationContext;
		Map<String, DomainEntity> entityMap;
		List<Entry<String, DomainEntity>> sortedEntities;

		EclipseConsole.fix();
		LogManager.getLogger("org.hibernate").setLevel(Level.OFF);
		databaseUtil = null;

		try {
			System.out.println("PopulateDatabase 1.18");
			System.out.println("---------------------");
			System.out.println();

			System.out.printf("Initialising persistence context `%s'.%n", DatabaseConfig.PersistenceUnit);
			databaseUtil = new DatabaseUtil();
			databaseUtil.initialise();

			System.out.printf("Creating database `%s' (%s).%n", databaseUtil.getDatabaseName(), databaseUtil.getDatabaseDialectName());
			databaseUtil.recreateDatabase();

			System.out.printf("Reading web of entities from `%s'.", DatabaseConfig.entitySpecificationFilename);
			populationContext = new ClassPathXmlApplicationContext("classpath:PopulateDatabase.xml");
			entityMap = populationContext.getBeansOfType(DomainEntity.class);
			System.out.printf(" (%d entities found).%n", entityMap.size());

			System.out.printf("Computing a topological order for your entities.%n");
			sortedEntities = PopulateDatabase.sort(databaseUtil, entityMap);

			System.out.printf("Trying to save the best order found.  Watch out for exceptions!%n");
			PopulateDatabase.persist(databaseUtil, sortedEntities);

			System.out.printf("Saving entity map to `%s'.%n", DatabaseConfig.entityMapFilename);
			PopulateDatabase.saveEntityMap(databaseUtil, sortedEntities);
		} catch (final Throwable oops) {
			ThrowablePrinter.print(oops);
		} finally {
			if (databaseUtil != null) {
				System.out.printf("Shutting persistence context `%s' down.%n", DatabaseConfig.PersistenceUnit);
				databaseUtil.shutdown();
			}
		}
	}

	protected static List<Entry<String, DomainEntity>> sort(final DatabaseUtil databaseUtil, final Map<String, DomainEntity> entityMap) {
		LinkedList<Entry<String, DomainEntity>> result;
		LinkedList<Entry<String, DomainEntity>> cache;
		Entry<String, DomainEntity> entry;
		DomainEntity entity;
		int passCounter;
		boolean done;

		result = new LinkedList<Entry<String, DomainEntity>>();
		result.addAll(entityMap.entrySet());
		cache = new LinkedList<Entry<String, DomainEntity>>();
		passCounter = 0;

		do {
			try {
				databaseUtil.startTransaction();
				PopulateDatabase.cleanEntities(result);

				while (!result.isEmpty()) {
					entry = result.getFirst();
					entity = entry.getValue();
					databaseUtil.persist(entity);
					result.removeFirst();
					cache.addLast(entry);
				}
				databaseUtil.rollbackTransaction();
				done = true;
				result.addAll(cache);
				cache.clear();
			} catch (final Throwable oops) {
				databaseUtil.rollbackTransaction();
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

	protected static void persist(final DatabaseUtil databaseUtil, final List<Entry<String, DomainEntity>> sortedEntities) {
		String name;
		DomainEntity entity;

		System.out.println();
		databaseUtil.startTransaction();
		for (final Entry<String, DomainEntity> entry : sortedEntities) {
			name = entry.getKey();
			entity = entry.getValue();

			System.out.printf("> %s = ", name);
			databaseUtil.persist(entity);
			SchemaPrinter.print(entity);
		}
		databaseUtil.commitTransaction();
		System.out.println();
	}

	private static void saveEntityMap(final DatabaseUtil databaseUtil, final List<Entry<String, DomainEntity>> sortedEntities) {
		Properties map;

		map = new Properties();
		for (final Entry<String, DomainEntity> entry : sortedEntities) {
			String key, value;

			key = entry.getKey();
			value = Integer.toString(entry.getValue().getId());

			map.put(key, value);
		}

		try (OutputStream stream = new FileOutputStream(DatabaseConfig.entityMapFilename)) {
			map.store(stream, DatabaseConfig.entityMapFilename);
		} catch (final Throwable oops) {
			throw new RuntimeException(oops);
		}
	}

	protected static void cleanEntities(final LinkedList<Entry<String, DomainEntity>> result) {
		for (final Entry<String, DomainEntity> entry : result) {
			DomainEntity entity;

			entity = entry.getValue();
			entity.setId(0);
			entity.setVersion(0);
		}
	}

}
