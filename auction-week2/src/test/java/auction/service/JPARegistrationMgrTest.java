/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auction.service;

import auction.dao.UserDAOJPAImpl;
import auction.domain.User;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import util.DatabaseCleaner;

/**
 *
 * @author Yannick
 */
public class JPARegistrationMgrTest {
    
    EntityManagerFactory emf;
    EntityManager em;
    UserDAOJPAImpl DAO;
    RegistrationMgr registrationMgr;
    DatabaseCleaner clean;
    
    public JPARegistrationMgrTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("nl.fhict.se42_auction_jar_1.0-SNAPSHOTPU");
        em = emf.createEntityManager();
        DAO = new UserDAOJPAImpl(em);
        clean = new DatabaseCleaner(em);
        registrationMgr = new RegistrationMgr();
    }
    
    @After
    public void tearDown() {
        try {
            clean.clean();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void registerUser() {
        
        User user1 = registrationMgr.registerUser("xxx1@yyy");
        em.getTransaction().begin();
        em.persist(user1);
        em.getTransaction().commit();
        assertTrue(user1.getEmail().equals("xxx1@yyy"));
        User user2 = registrationMgr.registerUser("xxx2@yyy2");
        em.getTransaction().begin();
        em.persist(user2);
        em.getTransaction().commit();
        assertTrue(user2.getEmail().equals("xxx2@yyy2"));
        User user2bis = registrationMgr.registerUser("xxx2@yyy2");
        em.getTransaction().begin();
        em.persist(user2bis);
        em.getTransaction().commit();
        assertSame(user2bis, user2);
        //geen @ in het adres
        assertNull(registrationMgr.registerUser("abc"));
    }

    @Test
    public void getUser() {
        User user1 = registrationMgr.registerUser("xxx5@yyy5");
        em.getTransaction().begin();
        em.persist(user1);
        em.getTransaction().commit();
        User userGet = registrationMgr.getUser("xxx5@yyy5");
        em.getTransaction().begin();
        em.persist(userGet);
        em.getTransaction().commit();
        assertSame(userGet, user1);
        assertNull(registrationMgr.getUser("aaa4@bb5"));
        User abc = registrationMgr.registerUser("abc");
        assertNull(registrationMgr.getUser("abc"));
    }

    @Test
    public void getUsers() {
        List<User> users = registrationMgr.getUsers();
        assertEquals(0, users.size());

        User user1 = registrationMgr.registerUser("xxx8@yyy");
        em.getTransaction().begin();
        em.persist(user1);
        em.getTransaction().commit();
        users = registrationMgr.getUsers();
        assertEquals(1, users.size());
        assertSame(users.get(0), user1);


        User user2 = registrationMgr.registerUser("xxx9@yyy");
        em.getTransaction().begin();
        em.persist(user2);
        em.getTransaction().commit();
        users = registrationMgr.getUsers();
        assertEquals(2, users.size());

        registrationMgr.registerUser("abc");
        //geen nieuwe user toegevoegd, dus gedrag hetzelfde als hiervoor
        users = registrationMgr.getUsers();
        assertEquals(2, users.size());
    }
}
