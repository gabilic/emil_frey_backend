package hr.emil_frey_project.dao;

import hr.emil_frey_project.interfaces.InsertOrUpdateFunction;
import hr.emil_frey_project.interfaces.QueryFunction;
import hr.emil_frey_project.util.HibernateUtil;
import hr.emil_frey_project.util.QueryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

abstract class GenericDao<T> {
    private <G> G commitTransaction(QueryFunction<G> function, G result) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            result = function.apply(session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return result;
    }

    private QueryFunction<Boolean> performQuery(InsertOrUpdateFunction function) {
        return (Session session) -> {
            try {
                function.apply(session);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        };
    }

    Boolean insertOrUpdate(T entity, QueryUtil.Action action) {
        InsertOrUpdateFunction function = (Session session) -> {
            if (action == QueryUtil.Action.CREATE) {
                session.save(entity);
            } else if (action == QueryUtil.Action.UPDATE) {
                session.update(entity);
            }
        };
        return commitTransaction(performQuery(function), false);
    }

    T getResult(QueryFunction<T> function) {
        return commitTransaction(function, null);
    }

    List<T> getResults(QueryFunction<List<T>> function) {
        return commitTransaction(function, null);
    }
}
