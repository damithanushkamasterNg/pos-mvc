package pos.mvc.controller;

import pos.mvc.model.Item;
import java.sql.*;
import java.util.ArrayList;
import pos.mvc.db.DBConnection;

public class ItemController {

    /**
     * Saves an item to the database.
     *
     * @param item The item to be saved.
     * @return "Success" if the item is successfully saved, "Fail" otherwise.
     * @throws SQLException if a database access error occurs.
     */
    public String saveItem(Item item) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String query = "INSERT INTO Item (itemCode, description, packSize, unitPrice, qtyOnHand) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, item.getItemCode());
        preparedStatement.setString(2, item.getDescription());
        preparedStatement.setString(3, item.getPackSize());
        preparedStatement.setDouble(4, item.getUnitPrice());
        preparedStatement.setInt(5, item.getQtyOnHand());

        if (preparedStatement.executeUpdate() > 0) {
            return "Success";
        } else {
            return "Fail";
        }
    }

    /**
     * Retrieves all items from the database.
     *
     * @return An ArrayList containing all the items in the database.
     * @throws SQLException if a database access error occurs.
     */
    public ArrayList<Item> getAllItems() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String query = "SELECT * FROM Item";

        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet rst = statement.executeQuery();

        ArrayList<Item> itemList = new ArrayList<>();

        while (rst.next()) {
            Item item = new Item(
                rst.getString("itemCode"),
                rst.getString("description"),
                rst.getString("packSize"),
                rst.getDouble("unitPrice"),
                rst.getInt("qtyOnHand")
            );
            itemList.add(item);
        }
        return itemList;
    }

    /**
     * Retrieves an item from the database based on the item code.
     *
     * @param itemCode The item code of the item to retrieve.
     * @return The Item object if found, or null if not found.
     * @throws SQLException if a database access error occurs.
     */
    public Item getItemByItemCode(String itemCode) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String query = "SELECT * FROM Item WHERE itemCode = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, itemCode);

        ResultSet rst = statement.executeQuery();

        if (rst.next()) {
            Item item = new Item(
                rst.getString("itemCode"),
                rst.getString("description"),
                rst.getString("packSize"),
                rst.getDouble("unitPrice"),
                rst.getInt("qtyOnHand")
            );
            return item;
        }

        return null;
    }

    /**
     * Updates an item in the database.
     *
     * @param item The updated item object.
     * @return "Success" if the item is successfully updated, "Fail" otherwise.
     * @throws SQLException if a database access error occurs.
     */
    public String updateItem(Item item) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String query = "UPDATE Item SET description=?, packSize=?, unitPrice=?, qtyOnHand=? WHERE itemCode=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, item.getDescription());
        preparedStatement.setString(2, item.getPackSize());
        preparedStatement.setDouble(3, item.getUnitPrice());
        preparedStatement.setInt(4, item.getQtyOnHand());
        preparedStatement.setString(5, item.getItemCode());

        if (preparedStatement.executeUpdate() > 0) {
            return "Success";
        } else {
            return "Fail";
        }
    }

    /**
     * Deletes an item from the database based on the item code.
     *
     * @param itemCode The item code of the item to be deleted.
     * @return "Item Deleted Successfully." if the item is successfully deleted,
     *         or an error message if the item is not found or the delete operation fails.
     * @throws SQLException if a database access error occurs.
     */
    public String deleteItem(String itemCode) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "DELETE FROM Item WHERE itemCode = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, itemCode);

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            return "Item Deleted Successfully.";
        } else {
            return "Item with item code " + itemCode + " not found or delete operation failed.";
        }
    }
}
