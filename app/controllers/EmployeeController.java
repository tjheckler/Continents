package controllers;

import models.Employee;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

public class EmployeeController extends Controller
{
    private JPAApi jpaApi;

    @Inject
    public EmployeeController(JPAApi jpaApi)
    {
        this.jpaApi = jpaApi;
    }

    @Transactional(readOnly = true)
    public Result getEmployees()
    {
        String sql = "SELECT e FROM Employee e";
        List<Employee> employees = jpaApi.em().createQuery(sql, Employee.class).getResultList();

        //return ok("Here be the " + employees.size()+" Employees");
        return ok(views.html.employees.render(employees));
    }

}
