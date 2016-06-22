package ru.nsu.ccfit.rivanov.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.ccfit.rivanov.entities.Node;
import ru.nsu.ccfit.rivanov.services.NodeDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Repository
@Primary
public class JpaNodeDao implements NodeDao {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Override
    @Transactional
    public void createNode(Node node) {
        EntityManager entityManager = EntityManagerFactoryUtils.getTransactionalEntityManager(entityManagerFactory);
        entityManager.persist(node);
        EntityManagerFactoryUtils.closeEntityManager(entityManager);
    }
}
