package controllers;

import com.google.common.io.Files;
import models.Employee;
import models.EmployeeDetail;
import models.TitleOfCourtesy;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;

import java.io.File;
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
        String sql = "SELECT NEW models.EmployeeDetail(e.employeeId, e.firstName, e.lastName, e.city, t.titleOfCourtesyName) " +
                "FROM Employee e " +
                "JOIN TitleOfCourtesy t ON e.titleOfCourtesyId = t.titleOfCourtesyId " +
                " WHERE lastName LIKE :searchCriteria " +
                "or firstName LIKE :searchCriteria " +
                "ORDER BY lastName";
        String searchCriteria = form.get("searchCriteria");
        if (searchCriteria == null)
        {
            searchCriteria = "";
        }
        String queryParameter = searchCriteria + "%";

        List<EmployeeDetail> employees = jpaApi.em()
                .createQuery(sql, EmployeeDetail.class).setParameter("searchCriteria", queryParameter).getResultList();


        return ok(views.html.employees.render(employees, searchCriteria));
    }

    @Transactional(readOnly = true)
    public Result getEmployee(Integer employeeId)
    {

        String sql = "SELECT e FROM Employee e " +
                "WHERE employeeId = :employeeId";
        Employee employee = jpaApi.em().createQuery(sql, Employee.class).
                setParameter("employeeId", employeeId).getSingleResult();

        String titleSql = "SELECT t FROM TitleOfCourtesy t ";
        List<TitleOfCourtesy> titleOfCourtesies = jpaApi.em()
                .createQuery(titleSql, TitleOfCourtesy.class).getResultList();

        return ok(views.html.employee.render(employee, titleOfCourtesies));
    }

    @Transactional
    public Result postEmployee(Integer employeeId)
    {

        Http.MultipartFormData<File> formData = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> filePart = formData.getFile("employeephoto");
        File file = filePart.getFile();

        String sql = "SELECT e FROM Employee e " +
                "WHERE employeeId = :employeeId";

        Employee employee = jpaApi.em().createQuery(sql, Employee.class)
                .setParameter("employeeId", employeeId).getSingleResult();
        DynamicForm form = formFactory.form().bindFromRequest();

        String firstName = form.get("firstName");
        employee.setFirstName(firstName);
        int titleOfCourtesyId = Integer.parseInt(form.get("titleOfCourtesyId"));
        String lastName = form.get("lastName");
        employee.setLastName(lastName);
        employee.setTitleOfCourtesyId(titleOfCourtesyId);

        if(file !=null)
        {
            try
            {
                employee.setPicture(Files.toByteArray(file));
            }
            catch (Exception e)
            {
                //do nothing
            }
        }

        jpaApi.em().persist(employee);

        return redirect(routes.EmployeeController.getEmployees());
    }

    @Transactional
    public Result deleteEmployee(int employeeId)
    {
        String sql = "SELECT e FROM Employee e " +
                "WHERE employeeId = :EmployeeId";
        Employee employee = jpaApi.em().createQuery(sql, Employee.class).
                setParameter("employeeId", employeeId).getSingleResult();
        jpaApi.em().remove(employee);
        return ok();
    }


    public Result getNewTitleOfCourtesy()
    {
        return ok(views.html.newtitleofcourtesy.render());
    }

    @Transactional
    public Result postNewTitleOfCourtesy()
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        String title = form.get("title");

        TitleOfCourtesy titleOfCourtesy = new TitleOfCourtesy();
        titleOfCourtesy.setTitleOfCourtesyName(title);
        jpaApi.em().persist(titleOfCourtesy);

        return ok("New ID is " + titleOfCourtesy.getTitleOfCourtesyId());
    }

    @Transactional(readOnly = true)
    public Result getSalaries()
    {
        String sql = "SELECT e FROM Employee e ORDER BY lastName, firstName ";
        List<Employee> employees = jpaApi.em().createQuery(sql, Employee.class).getResultList();
        return ok(views.html.salaries.render(employees));
    }

    @Transactional(readOnly = true)
    public Result getEmployeePicture(int employeeId)
    {
        String sql = "SELECT e FROM Employee e " +
                "WHERE employeeId = :employeeId";
        Employee employee = jpaApi.em().createQuery(sql, Employee.class).
                setParameter("employeeId", employeeId).getSingleResult();
        return ok(employee.getPicture()).as("image/jpg");
    }


}
