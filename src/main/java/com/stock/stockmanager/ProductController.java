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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@WebServlet("/product")
public class ProductController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Products> productsList = ProductsDAO.getProducts();
        request.setAttribute("products", productsList);
        System.out.println("Products List " + productsList);
        // response
        getServletContext()
                .getRequestDispatcher("/WEB-INF/templates/product.jsp")
                .forward(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Products> productsList = new ArrayList<>();
        if (request.getParameter("action") != null) {
            if (request.getParameter("action").equals("add")) {
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                Integer quantity  = request.getParameter("quantity").equals("")?
                        null: Integer.valueOf(request.getParameter("quantity"));
                Double price = request.getParameter("price").equals("")?
                        null: Double.valueOf(request.getParameter("price"));
                String image = request.getParameter("image");

                System.out.println("input Data: " + name + " " + description + " " + quantity + " " + price + " " + image);

                FormProduct formData = new FormProduct(image, name, description, quantity, price);
                ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                Validator validator = factory.getValidator();
                Set<ConstraintViolation<FormProduct>> errors = validator.validate(formData);
                if (errors.isEmpty()) { // no errors
                    System.out.println("Insert data: "
                            + "name: " + name
                            + "description: " + description
                            + "quantity: " + quantity
                            + "price: " + price
                            + "image: " + image);

                    ProductsDAO.setProducts(formData.getImage(), formData.getName(), formData.getDescription(), formData.getQuantity(), formData.getPrice());
                    productsList = ProductsDAO.getProducts();
                    request.setAttribute("products", productsList);
                    System.out.println("Products List " + productsList);
                    // response
                    request.setAttribute("success", true);
                    getServletContext()
                            .getRequestDispatcher("/WEB-INF/templates/product.jsp")
                            .forward(request, response);


                } else {
                    StringBuilder errorMessage = new StringBuilder(" " +
                            "<p>The form has the following errors:</p>" + "<ul>");
                    for (var error: errors) {
                        errorMessage.append("<li>" + error.getMessage() + "</li>");
                    }
                    errorMessage.append("</ul>");
                    productsList = ProductsDAO.getProducts();
                    request.setAttribute("products", productsList);
                    request.setAttribute("errors", errorMessage);
                    request.setAttribute("formData", formData);

                    getServletContext()
                            .getRequestDispatcher("/WEB-INF/templates/product.jsp")
                            .forward(request, response);
                }
            } else if (request.getParameter("action").equals("delete")) {
                System.out.println(request.getParameter("action").equals("delete"));
                int id = Integer.parseInt(request.getParameter("id"));
                System.out.println("id for delete " + id);
                ProductsDAO.deleteProducts(id);

                productsList = ProductsDAO.getProducts();
                request.setAttribute("products", productsList);
                System.out.println(request.getAttribute("products"));
                System.out.println("remaining products: " + productsList);
                getServletContext()
                        .getRequestDispatcher("/WEB-INF/templates/product.jsp")
                        .forward(request, response);
            }
        }
    }
}
