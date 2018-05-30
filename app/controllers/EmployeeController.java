package controllers;

import play.db.jpa.JPAApi;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

public class EmployeeController extends Controller
{
    private JPAApi jpaApi;
    @Inject
    public EmployeeController(JPAApi jpaApi)
    {
        this.jpaApi = jpaApi;
    }

    public Result getEmployees()
    {
        return ok("Here are the Employees");
    }
}
