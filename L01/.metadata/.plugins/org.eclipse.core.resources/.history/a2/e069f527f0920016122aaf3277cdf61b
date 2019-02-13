/* PopulateDatabase.java
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package utilities;

import java.util.Map.Entry;

import javax.persistence.Entity;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import utilities.internal.DatabaseUtil;
import domain.DomainEntity;

public class PopulateDatabase {

	public static void main(String[] args) {
		ApplicationContext populationContext;
		DatabaseUtil databaseUtil;
		
		databaseUtil = null;
		
		try {
			System.out.printf("PopulateDatabase 1.6%n");
			System.out.printf("--------------------%n%n");
			
			System.out.printf("Initialising persistence context `%s'...%n", DatabaseConfig.PersistenceUnit);
			databaseUtil = new DatabaseUtil();
			
			System.out.printf("Creating database `%s' (%s)...%n", databaseUtil.getDatabaseName(), databaseUtil.getDatabaseDialectName());
			databaseUtil.recreateDatabase();
			
			System.out.printf("Reading configuration file `%s'...%n", "PopulateDatabase.xml");
			populationContext = new ClassPathXmlApplicationContext("classpath:populateDatabase.xml");
						
			System.out.printf("Persisting %d entities...%n%n", populationContext.getBeanDefinitionCount());
			databaseUtil.openTransaction();			
			for (Entry<String, Object> entry : populationContext.getBeansWithAnnotation(Entity.class).entrySet()) {
				String beanName;
				DomainEntity entity;

				beanName = entry.getKey();
				entity = (DomainEntity) entry.getValue();
				System.out.printf("> %s: %s", beanName, entity.getClass().getName());
				databaseUtil.persist(entity);
				System.out.printf(" -> id = %d, version = %d%n", entity.getId(), entity.getVersion());
			}
			databaseUtil.commitTransaction();
		} catch (Throwable oops) {
			System.out.flush();
			System.err.printf("%n%s%n", oops.getLocalizedMessage());
			oops.printStackTrace(System.err);			
		} finally {
			if (databaseUtil != null)
				databaseUtil.close();
		}
	}	

}
