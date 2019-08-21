package hr.emil_frey_project.interfaces;

import org.hibernate.Session;

public interface QueryFunction<T> {
    T apply(Session session);
}
