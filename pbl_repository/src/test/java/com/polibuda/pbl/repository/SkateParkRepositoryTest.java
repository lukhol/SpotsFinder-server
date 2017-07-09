package com.polibuda.pbl.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.polibuda.pbl.model.Place;
import com.polibuda.pbl.model.SkatePark;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SkateParkRepositoryTest {

	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private SkateParkRepository skateParkRepository;
    
    @Before
    public void exampleSetUp() {
    	// dodanie przykladowych danych testowych
    	SkatePark skatePark = SkatePark.builder().id(12345l).place(Place.builder().coordinates("360x123x25").build()).build();
    	entityManager.persist(skatePark);
    	entityManager.flush();
    }
    
    @Test
    public void exampleTest() {
    	// tu cos testujemy
    }
    // TODO: tests
}
