package controllers;
import models.Employee;
import models.Product;
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

    @Inject
    public ProductController(JPAApi jpaApi)
    {
        this.jpaApi = jpaApi;
    }

    @Transactional(readOnly = true)
    public Result getProducts()
    {
        String sql = "SELECT p FROM Product p";
        List<Product> products = jpaApi.em().createQuery(sql, Product.class).getResultList();


        return ok(views.html.products.render(products));
    }

}
