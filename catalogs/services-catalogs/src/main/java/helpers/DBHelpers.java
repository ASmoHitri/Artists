package helpers;

import javax.persistence.EntityManager;

public class DBHelpers {
    public static boolean addObject(EntityManager entityManager, Object o) {
        if (o != null) {
            try{
                TransactionsHandler.beginTx(entityManager);
                entityManager.persist(o);
                TransactionsHandler.commitTx(entityManager);
            } catch (Exception e) {
                TransactionsHandler.rollbackTx(entityManager);
                return false;
            }
        }
        return true;
    }

    public static boolean removeObject(EntityManager entityManager, Object o) {
        if (o == null) {
            return false;
        }
        try{
            TransactionsHandler.beginTx(entityManager);
            entityManager.remove(o);
            TransactionsHandler.commitTx(entityManager);
        } catch (Exception e) {
            TransactionsHandler.rollbackTx(entityManager);
            return false;
        }
        return true;
    }

}
