package com.stock.stockmanager.model;

import com.stock.stockmanager.forms.FormProduct;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductsDAO {

    private static Products prepareProductObject(ResultSet resultSet) throws SQLException {
        Products products = new Products();
        products.setId(resultSet.getInt("id"));
        products.setImage(resultSet.getString("image"));
        products.setName(resultSet.getString("name"));
        products.setDescription(resultSet.getString("description"));
        products.setQuantity(resultSet.getInt("quantity"));
        products.setPrice(resultSet.getDouble("price"));
        return products;
    }
    public static List<Products> getProducts(){
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/shopdb");
            Connection connection = ds.getConnection();

            String query = "SELECT * FROM products";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            List<Products> productsList = new ArrayList<>();
            while (resultSet.next()) {
                Products products = prepareProductObject(resultSet);
                productsList.add(products);
            }

            resultSet.close();
            statement.close();
            connection.close();
            return productsList;
        } catch (NamingException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Products getProductsById(int id){
        Products products = null;
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/shopdb");
            Connection connection = ds.getConnection();
            String inQuery = "SELECT* FROM products WHERE id=?";

            PreparedStatement preparedStatement = connection.prepareStatement(inQuery);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            products = prepareProductObject(resultSet);

            System.out.println("get product: " + products);
            preparedStatement.close();
            resultSet.close();
            connection.close();
            return products;
        } catch (NamingException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void setProducts(String image, String name, String description, int quantity, double price){
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/shopdb");
            Connection connection = ds.getConnection();
            String inQuery = "INSERT INTO products(image, name, description, quantity, price) " + "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(inQuery);
            preparedStatement.setString(1, image);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, description);
            preparedStatement.setInt(4, quantity);
            preparedStatement.setDouble(5, price);
            preparedStatement.executeUpdate();
            System.out.println("Product Successfully added! ");

            preparedStatement.close();
            connection.close();
        } catch (NamingException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteProducts(int id){
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/shopdb");
            Connection connection = ds.getConnection();
            String delQuery = "DELETE FROM products WHERE id=?";

            PreparedStatement preparedStatement = connection.prepareStatement(delQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Product Successfully deleted! ");

            preparedStatement.close();
            connection.close();
        } catch (NamingException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void updateProducts(FormProduct formData){
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/shopdb");
            Connection connection = ds.getConnection();
            String upQuery = "UPDATE products SET image = ?, name = ?, description = ?, quantity = ?, price = ? WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(upQuery);
            preparedStatement.setString(1, formData.getImage());
            preparedStatement.setString(2, formData.getName());
            preparedStatement.setString(3, formData.getDescription());
            preparedStatement.setInt(4, formData.getQuantity());
            preparedStatement.setDouble(5, formData.getPrice());
            preparedStatement.setInt(6, formData.getId());
            preparedStatement.executeUpdate();
            System.out.println("Product Successfully updated! ");

            preparedStatement.close();
            connection.close();
        } catch (NamingException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
