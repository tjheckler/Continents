package controllers;

import models.Employee;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

public class EmployeeController extends Controller
{
    private JPAApi jpaApi;
    private FormFactory formFactory;

    @Inject
    public EmployeeController(JPAApi jpaApi, FormFactory formFactory)
    {
        this.formFactory = formFactory;
        this.jpaApi = jpaApi;
    }

    @Transactional(readOnly = true)
    public Result getEmployees()
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        String sql = "SELECT e FROM Employee e " +
                " WHERE lastName LIKE :searchCriteria " +
                "or firstName LIKE :searchCriteria " +
                "ORDER BY lastName";
        String searchCriteria = form.get("searchCriteria");
        if (searchCriteria == null)
        {
            searchCriteria = "";
        }
        String queryParameter = searchCriteria + "%";

        List<Employee> employees = jpaApi.em()
                .createQuery(sql, Employee.class).setParameter("searchCriteria", queryParameter).getResultList();


        return ok(views.html.employees.render(employees, searchCriteria));
    }
    @Transactional(readOnly = true)
    public Result getEmployee(Integer employeeId)
    {

        String sql = "SELECT e FROM Employee e " +
                "WHERE employeeId = :employeeId";
        Employee employee = jpaApi.em().createQuery(sql,Employee.class).
                setParameter("employeeId",employeeId).getSingleResult();

        return ok(views.html.employee.render(employee));
    }

}
