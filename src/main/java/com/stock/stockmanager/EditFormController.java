package com.stock.stockmanager;

import com.stock.stockmanager.forms.FormProduct;
import com.stock.stockmanager.model.ProductsDAO;
import com.stock.stockmanager.model.Products;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@WebServlet("/edit-product")
public class EditFormController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        Products products = ProductsDAO.getProductsById(id);

        request.setAttribute("product", products);
        //response
        getServletContext()
                .getRequestDispatcher("/WEB-INF/templates/edit-form.jsp")
                .forward(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if (request.getParameter("action") != null) {
            if (request.getParameter("action").equals("update")) {;
                Integer id  = request.getParameter("id").equals("")?
                        null: Integer.valueOf(request.getParameter("id"));
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                Integer quantity  = request.getParameter("quantity").equals("")?
                        null: Integer.valueOf(request.getParameter("quantity"));
                Double price = request.getParameter("price").equals("")?
                        null: Double.valueOf(request.getParameter("price"));
                String image = request.getParameter("image");


                FormProduct formData = new FormProduct(id, image, name, description, quantity, price);
                ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                Validator validator = factory.getValidator();
                Set<ConstraintViolation<FormProduct>> errors = validator.validate(formData);
                if (errors.isEmpty()) { // no errors
                    System.out.println("Edit from data: "
                            + "name: " + name
                            + "description: " + description
                            + "quantity: " + quantity
                            + "price: " + price
                            + "image: " + image);

                    ProductsDAO.updateProducts(formData);

                    List<Products> productsList = ProductsDAO.getProducts();
                    request.setAttribute("products", productsList);
                    System.out.println("Products List " + productsList);
                    // response
                    request.setAttribute("success", true);
                    getServletContext()
                            .getRequestDispatcher("/WEB-INF/templates/edit-form.jsp")
                            .forward(request, response);
                } else {// errors
                    StringBuilder errorMessage = new StringBuilder(" " +
                            "<p>The form has the following errors:</p>" + "<ul>");
                    for (var error: errors) {
                        errorMessage.append("<li>" + error.getMessage() + "</li>");
                    }
                    errorMessage.append("</ul>");
                    id = Integer.parseInt(request.getParameter("id"));
                    Products products = ProductsDAO.getProductsById(id);

                    // set attributes
                    request.setAttribute("product", products);
                    request.setAttribute("errors", errorMessage);
                    request.setAttribute("formData", formData);

                    // dispatcher
                    getServletContext()
                            .getRequestDispatcher("/WEB-INF/templates/edit-form.jsp")
                            .forward(request, response);
                }
            }
        }
    }
}
