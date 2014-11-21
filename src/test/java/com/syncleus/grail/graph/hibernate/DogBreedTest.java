package com.syncleus.grail.graph.hibernate;

import com.syncleus.grail.graph.hibernate.domain.Breed;
import com.syncleus.grail.graph.hibernate.domain.Dog;
import junit.framework.Assert;
import org.hibernate.ogm.util.impl.Log;
import org.hibernate.ogm.util.impl.LoggerFactory;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class DogBreedTest {

	private static final Log logger = LoggerFactory.make();

	@Test
	public void testMain() {
		//build the EntityManagerFactory as you would build in in Hibernate Core
		EntityManagerFactory emf = Persistence.createEntityManagerFactory( "ogm-jpa-tutorial" );

		//Persist entities the way you are used to in plain JPA
		EntityManager em = emf.createEntityManager();
		EntityTransaction tm = em.getTransaction();
		tm.begin();
		Breed collie = new Breed();
		collie.setName( "Collie" );
		em.persist( collie );
		Dog dina = new Dog();
		dina.setName( "Dina" );
		dina.setBreed( collie );
		em.persist( dina );
		Long dinaId = dina.getId();
		em.flush();
		em.close();
		tm.commit();

		//Retrieve your entities the way you are used to in plain JPA
		em = emf.createEntityManager();
		tm = em.getTransaction();
		tm.begin();
		dina = em.find( Dog.class, dinaId );
		Assert.assertEquals("Dina", dina.getName());
		Assert.assertEquals("Collie", dina.getBreed().getName());
		em.flush();
		em.close();
		tm.commit();

		emf.close();
	}
}
