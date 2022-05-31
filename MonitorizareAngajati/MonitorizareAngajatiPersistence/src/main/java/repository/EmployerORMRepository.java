package repository;

import model.Employer;
import model.Task;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.ORMUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EmployerORMRepository implements IEmployerRepository {

    public EmployerORMRepository() {
    }

    @Override
    public Employer getEmployerByUsername(String username) {
        Employer employer = null;

        try (Session session = ORMUtils.getSessionFactory().openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();

                Query<Employer> query = session.createQuery("from Employer e where e.username = :username ");
                query.setParameter("username", username);
                List list = query.list();

                if (list.size() > 0) {
                    employer = (Employer) list.get(0);
                }

                transaction.commit();
            } catch (Exception ex) {
                System.err.println("Eroare la cautare " + ex);

                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }

        return employer;
    }

    @Override
    public Iterable<Task> getTasksOfAnEmployer(Employer employer) {
        List<Task> tasks = new ArrayList<>();
        List<Task> tasks2 = new ArrayList<>();

        try(Session session = ORMUtils.getSessionFactory().openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();

                Query<Task> query = session.createQuery("from Task t");

                tasks = query.list();

                Iterator it = tasks.iterator();

                while (it.hasNext()) {
                    Object o = (Object) it.next();
                    Task t = (Task) o;

                    if (t.getEmployee().getEmployer().getId().equals(employer.getId())) {
                        tasks2.add(t);
                    }
                }

                transaction.commit();
            } catch (Exception ex) {
                System.err.println("Eroare la cautare " + ex);

                if (transaction != null) {
                    transaction.rollback();
                }
            }

            for (Task task : tasks) {
                System.out.println(task.getEmployee().getEmployer().getId());
            }
        }

        return tasks2;
    }

    @Override
    public Employer findOne(Long aLong) {
        return null;
    }

    @Override
    public Iterable<Employer> findAll() {
        return null;
    }

    @Override
    public void save(Employer employer) {
        try (Session session = ORMUtils.getSessionFactory().openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();

                session.save(employer);

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
    public void delete(Long aLong) {

    }

    @Override
    public void update(Employer entity) {

    }
}
