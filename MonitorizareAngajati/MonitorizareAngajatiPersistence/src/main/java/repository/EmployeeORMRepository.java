package repository;

import model.Employee;
import model.Employer;
import org.hibernate.*;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import utils.ORMUtils;

import java.util.ArrayList;
import java.util.List;

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

    public Iterable<Employee> getEmployeesOfAnEmployer(Employer employer) {
        List<Employee> employees = new ArrayList<>();

        try (Session session = ORMUtils.getSessionFactory().openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();

                Query<Employee> query = session.createQuery("from Employee e where e.employer = :employer ");
                query.setParameter("employer", employer);

                employees = query.list();

                transaction.commit();
            } catch (Exception ex) {
                System.err.println("Eroare la cautare " + ex);

                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }

        return employees;
    }

    @Override
    public void update(Employee entity) {

    }
}
