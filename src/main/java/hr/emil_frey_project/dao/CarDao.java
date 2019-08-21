package hr.emil_frey_project.dao;

import hr.emil_frey_project.interfaces.QueryFunction;
import hr.emil_frey_project.model.Car;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

class CarDao extends GenericDao<Car> {
    List<Car> getCarsByIds(List<Integer> ids) {
        QueryFunction<List<Car>> getCarsByIdsQuery = (Session session) -> {
            Query<Car> query = session.createQuery("from Car c where c.id in :ids", Car.class);
            query.setParameterList("ids", ids);
            return query.getResultList();
        };
        return getResults(getCarsByIdsQuery);
    }
}
