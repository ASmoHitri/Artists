package beans;

import javax.persistence.EntityManager;

class TransactionsHandler {
    static void beginTx(EntityManager entityManager) {
        if (!entityManager.getTransaction().isActive())
            entityManager.getTransaction().begin();
    }

    static void commitTx(EntityManager entityManager) {
        if (entityManager.getTransaction().isActive())
            entityManager.getTransaction().commit();
    }

    static void rollbackTx(EntityManager entityManager) {
        if (entityManager.getTransaction().isActive())
            entityManager.getTransaction().rollback();
    }

    static boolean removeObject(EntityManager entityManager, Object o) {
        if (o == null) {
            return false;
        }
        try{
            beginTx(entityManager);
            entityManager.remove(o);
            commitTx(entityManager);
        } catch (Exception e) {
            rollbackTx(entityManager);
            return false;
        }
        return true;
    }
}
