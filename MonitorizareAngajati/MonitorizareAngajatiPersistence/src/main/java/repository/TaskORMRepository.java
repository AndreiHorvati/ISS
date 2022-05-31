package repository;

import model.Employee;
import model.Task;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.ORMUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TaskORMRepository implements ITaskRepository {
    @Override
    public Task findOne(Long aLong) {
        return null;
    }

    @Override
    public Iterable<Task> findAll() {
        return null;
    }

    @Override
    public void save(Task task) {
        try (Session session = ORMUtils.getSessionFactory().openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();

                session.save(task);

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

                Task task = session.createQuery("from Task where id = :id", Task.class)
                        .setParameter("id", id)
                        .setMaxResults(1)
                        .uniqueResult();

                session.delete(task);

                transaction.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la stergere " + ex);

                if (transaction != null)
                    transaction.rollback();
            }
        }
    }

    @Override
    public void update(Task task) {
        try (Session session = ORMUtils.getSessionFactory().openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();

                session.update(task);

                transaction.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la stergere " + ex);

                if (transaction != null)
                    transaction.rollback();
            }
        }
    }

    @Override
    public Iterable<Task> getTasksOfAnEmployee(Employee employee) {
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

                    if (t.getEmployee().getId().equals(employee.getId())) {
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
}
