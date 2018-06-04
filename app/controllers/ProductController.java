package controllers;

import models.*;
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
        String sql = "SELECT NEW models.ProductDetail(p.productId, p.productName, p.unitPrice, c.categoryName) " +
                "FROM Product p " +
                "JOIN Category c ON p.categoryId = c.categoryId "+
                "WHERE productName LIKE :searchCriteria " +
               "or categoryName LIKE :searchCriteria "    +
                "ORDER BY productName ";
        String searchCriteria = form.get("searchCriteria");
        if (searchCriteria == null)
        {
            searchCriteria = "";
        }
        String queryParameter = searchCriteria + "%";

        List<ProductDetail> products = jpaApi.em()
                .createQuery(sql, ProductDetail.class).setParameter("searchCriteria",queryParameter).getResultList();
        return ok(views.html.products.render(products,searchCriteria));
    }
    @Transactional(readOnly = true)
    public Result getProduct(Integer productId)
    {

        String sql = "SELECT p FROM Product p " +
                "WHERE productId = :productId";
        Product product = jpaApi.em().createQuery(sql,Product.class).
                setParameter("productId",productId).getSingleResult();

        String categorySql = "SELECT c FROM Category c";
        List<Category> categories = jpaApi.em()
                .createQuery(categorySql, Category.class).getResultList();

        return ok(views.html.product.render(product,categories));
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
        int categoryId = Integer.parseInt(form.get("categoryId"));
        product.setProductName(productName);
        product.setCategoryId(categoryId);
       // String lastName = form.get("lastName");
       // employee.setLastName(lastName);
        jpaApi.em().persist(product);
        return redirect(routes.ProductController.getProducts());
    }
    public Result getNewCategory()
    {
        return ok(views.html.newcategory.render());
    }

    @Transactional
    public Result postNewCategory()
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        String category1 = form.get("category");
        String category2 = form.get("description");
        Category category = new Category();
        category.setCategoryName(category1);
        category.setDescription(category2);
        jpaApi.em().persist(category);

        return ok("New ID is " + category.getCategoryId()+ " " + category.getDescription());
    }
}
