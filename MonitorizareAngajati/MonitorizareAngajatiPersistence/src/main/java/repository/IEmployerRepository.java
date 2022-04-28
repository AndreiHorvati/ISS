package repository;

import model.Employer;

public interface IEmployerRepository extends IRepository<Long, Employer> {
    Employer getEmployerByUsername(String username);
}
