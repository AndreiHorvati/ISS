package controller;

import model.Employer;

public interface IController {
    void loginEmployer(Employer employer, IObserver client) throws Exception;

    Employer getEmployerByUsername(String username);
}
