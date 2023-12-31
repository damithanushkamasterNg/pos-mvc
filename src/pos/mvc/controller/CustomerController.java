/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos.mvc.controller;

import pos.mvc.model.CustomerModel;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import pos.mvc.db.DBConnection;

/**
 *
 * @author Damith
 */
public class CustomerController {

    public String saveCustomer(CustomerModel customerModel) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String query = "INSERT INTO Customer VALUES(?,?,?,?,?,?,?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, customerModel.getCustId());
        preparedStatement.setString(2, customerModel.getTitle());
        preparedStatement.setString(3, customerModel.getName());
        preparedStatement.setString(4, customerModel.getDob());
        preparedStatement.setDouble(5, customerModel.getSalary());
        preparedStatement.setString(6, customerModel.getAddress());
        preparedStatement.setString(7, customerModel.getCity());
        preparedStatement.setString(8, customerModel.getProvince());
        preparedStatement.setString(9, customerModel.getZip());

        if (preparedStatement.executeUpdate() > 0) {
            return "Success";
        } else {
            return "Fail";
        }
    }

    /**
     * get all customers
     *
     * @return customer list
     * @throws SQLException to handle SQl exceptions
     */
    public ArrayList<CustomerModel> getAllCustomers() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String query = "Select * FROM Customer";

        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet rst = statement.executeQuery();

        ArrayList<CustomerModel> customerModels = new ArrayList<>();

        while (rst.next()) {
            CustomerModel cm = new CustomerModel(rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getString(9));
            customerModels.add(cm);
        }
        return customerModels;
    }

    /**
     * get specific customer by id
     *
     * @param cutomerId to customer id
     * @return CustomerModel object
     */
    public CustomerModel getCustomerById(String cutomerId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String query = "Select * FROM Customer WHERE CustID = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, cutomerId);

        ResultSet rst = statement.executeQuery();

        while (rst.next()) {
            CustomerModel cm = new CustomerModel(rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getString(9));

            return cm;
        }

        return null;
    }

    /**
     * update customer
     *
     * @param customerModel to customer object
     * @return string
     * @throws SQLException to manage exception
     */
    public String updateCustomer(CustomerModel customerModel) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String query = "UPDATE Customer SET CustTitle =?, CustName=?, DOB=?, salary = ?, CustAddress=?, City=?, Province=?, PostalCode=? WHERE CustID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, customerModel.getTitle());
        preparedStatement.setString(2, customerModel.getName());
        preparedStatement.setString(3, customerModel.getDob());
        preparedStatement.setDouble(4, customerModel.getSalary());
        preparedStatement.setString(5, customerModel.getAddress());
        preparedStatement.setString(6, customerModel.getCity());
        preparedStatement.setString(7, customerModel.getProvince());
        preparedStatement.setString(8, customerModel.getZip());
        preparedStatement.setString(9, customerModel.getCustId());

        if (preparedStatement.executeUpdate() > 0) {
            return "Success";
        } else {
            return "Fail";
        }

    }

    /**
     * delete customer by id
     *
     * @param customerId to customer id
     * @return a string
     * @throws SQLException to exception
     */
    public String deleteCustomer(String customerId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "DELETE FROM Customer WHERE CustID = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, customerId);

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            return "Customer Deleted Successfully.";
        } else {
            return "Customer with ID " + customerId + " not found or delete operation failed.";
        }
    }

}
