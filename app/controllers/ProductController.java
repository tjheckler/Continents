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
    @Transactional(readOnly = true)
    public Result getProduct(Integer productId)
    {

        String sql = "SELECT p FROM Product p " +
                "WHERE productId = :productId";
        Product product = jpaApi.em().createQuery(sql,Product.class).
                setParameter("productId",productId).getSingleResult();

        return ok(views.html.product.render(product));
    }
    @Transactional
    public Result postProduct(Integer productId)
    {
        String sql = "SELECT e FROM Product e " +
                "WHERE productId = :productId";

        Product product = jpaApi.em().createQuery(sql, Product.class)
                .setParameter("productId", productId).getSingleResult();
        DynamicForm form = formFactory.form().bindFromRequest();

        String productName = form.get("productName");
        product.setProductName(productName);
       // String lastName = form.get("lastName");
       // employee.setLastName(lastName);
        jpaApi.em().persist(product);
        return redirect(routes.ProductController.getProducts());
    }
}
