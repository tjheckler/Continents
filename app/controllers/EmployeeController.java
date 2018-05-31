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
        DynamicForm form =formFactory.form().bindFromRequest();
        String sql = "SELECT e FROM Employee e WHERE lastName LIKE :searchCriteria";
        String searchCriteria = form.get("searchCriteria");
        if (searchCriteria == null)
        {
            searchCriteria = "";
        }
        searchCriteria += "%";
        List<Employee> employees = jpaApi.em()
                .createQuery(sql, Employee.class).setParameter("searchCriteria",searchCriteria).getResultList();


        return ok(views.html.employees.render(employees));
    }

}
