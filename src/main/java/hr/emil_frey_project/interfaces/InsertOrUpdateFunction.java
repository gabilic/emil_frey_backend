package hr.emil_frey_project.interfaces;

import org.hibernate.Session;

public interface InsertOrUpdateFunction {
    void apply(Session session);
}
