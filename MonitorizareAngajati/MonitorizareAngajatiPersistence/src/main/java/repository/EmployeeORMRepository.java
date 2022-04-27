package repository;

import model.Employee;
import org.hibernate.*;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import utils.ORMUtils;

public class EmployeeORMRepository implements IEmployeeRepository {

    public EmployeeORMRepository() {
    }

    @Override
    public Employee findOne(Long aLong) {
        return null;
    }

    @Override
    public Iterable findAll() {
        return null;
    }

    public void save(Employee employee) {
        try (Session session = ORMUtils.getSessionFactory().openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();

                session.save(employee);

                transaction.commit();
            } catch (Exception ex) {
                System.err.println("Eroare la inserare " + ex);

                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = ORMUtils.getSessionFactory().openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();

                Employee employee = session.createQuery("from Employee where id = :id", Employee.class)
                        .setParameter("id", id)
                        .setMaxResults(1)
                        .uniqueResult();

                session.delete(employee);

                transaction.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la stergere " + ex);

                if (transaction != null)
                    transaction.rollback();
            }
        }
    }

    @Override
    public void update(Employee entity) {

    }
}
