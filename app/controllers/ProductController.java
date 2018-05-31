package controllers;

import models.Employee;
import models.Product;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Controller;

import javax.inject.Inject;
import java.util.List;

public class ProductController extends Controller
{
    private JPAApi jpaApi;
    private FormFactory formFactory;

    @Inject
    public ProductController(JPAApi jpaApi, FormFactory formFactory)
    {
        this.jpaApi = jpaApi;
        this.formFactory = formFactory;
    }

    @Transactional(readOnly = true)
    public Result getProducts()
    {
        DynamicForm form =formFactory.form().bindFromRequest();
        String sql = "SELECT p FROM Product p " +
                "WHERE productName LIKE :searchCriteria " +
                "ORDER BY productName ";
        String searchCriteria = form.get("searchCriteria");
        if (searchCriteria == null)
        {
            searchCriteria = "";
        }
        String queryParameter = searchCriteria + "%";

        List<Product> products = jpaApi.em()
                .createQuery(sql, Product.class).setParameter("searchCriteria",queryParameter).getResultList();
        return ok(views.html.products.render(products,searchCriteria));
    }

}
