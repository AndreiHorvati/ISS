package repository;

import model.Employer;

public interface IEmployerRepository extends IRepository<Long, Employer> {
    public Employer getEmployerByUsername(String username);
}
