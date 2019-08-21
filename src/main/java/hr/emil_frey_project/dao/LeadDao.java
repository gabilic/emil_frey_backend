package hr.emil_frey_project.dao;

import hr.emil_frey_project.interfaces.QueryFunction;
import hr.emil_frey_project.model.Car;
import hr.emil_frey_project.model.Lead;
import hr.emil_frey_project.util.QueryUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.HashSet;
import java.util.List;

public class LeadDao extends GenericDao<Lead> {
    private boolean createOrUpdateLead(Lead lead, List<Integer> carIds, QueryUtil.Action action) {
        List<Car> listOfCars = new CarDao().getCarsByIds(carIds);
        lead.setCars(new HashSet<>(listOfCars));
        for (Car car : lead.getCars()) {
            car.getLeads().add(lead);
        }
        return insertOrUpdate(lead, action);
    }

    private Lead getLeadById(int id) {
        QueryFunction<Lead> getLeadByIdQuery = (Session session) -> {
            Query<Lead> query = session.createQuery("from Lead l inner join fetch l.cars where l.id = :id", Lead.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        };
        return getResult(getLeadByIdQuery);
    }

    public boolean createLead(String firstName, String lastName, List<Integer> carIds) {
        Lead newLead = new Lead(firstName, lastName);
        return createOrUpdateLead(newLead, carIds, QueryUtil.Action.CREATE);
    }

    public boolean updateLead(int leadId, String firstName, String lastName, List<Integer> carIds) {
        Lead lead = getLeadById(leadId);
        lead.setFirstName(firstName);
        lead.setLastName(lastName);
        return createOrUpdateLead(lead, carIds, QueryUtil.Action.UPDATE);
    }

    public List<Lead> getAllLeads() {
        QueryFunction<List<Lead>> getAllLeadsQuery = (Session session) -> {
            Query<Lead> query = session.createQuery("from Lead l inner join fetch l.cars", Lead.class);
            return query.getResultList();
        };
        return getResults(getAllLeadsQuery);
    }

    public List<Lead> getLeadsByName(String firstName, String lastName) {
        QueryFunction<List<Lead>> getLeadsByNameQuery = (Session session) -> {
            Query<Lead> query = session.createQuery("from Lead l inner join fetch l.cars where l.firstName = :firstName and l.lastName = :lastName", Lead.class);
            query.setParameter("firstName", firstName);
            query.setParameter("lastName", lastName);
            return query.getResultList();
        };
        return getResults(getLeadsByNameQuery);
    }
}
