/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import auction.domain.User;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.metamodel.EntityType;

/**
 *
 * @author yanni
 */
public class DatabaseCleaner {

    private static final Class<?>[] ENTITY_TYPES = {
        User.class
    };
    private final EntityManager em;

    public DatabaseCleaner(EntityManager entityManager) {
        em = entityManager;
    }
    
    //Added a parameterless constructor to prevent closing an EntityManager in use
    public DatabaseCleaner() {
        em = Persistence.createEntityManagerFactory("nl.fhict.se42_auction_jar_1.0-SNAPSHOTPU").createEntityManager();
    }

    public void clean() throws SQLException {
        em.getTransaction().begin();

        for (Class<?> entityType : ENTITY_TYPES) {
            deleteEntities(entityType);
        }
        em.getTransaction().commit();
        em.close();
    }

    private void deleteEntities(Class<?> entityType) {
        em.createQuery("delete from " + getEntityName(entityType)).executeUpdate();
    }

    protected String getEntityName(Class<?> clazz) {
        EntityType et = em.getMetamodel().entity(clazz);
        return et.getName();
    }
}

