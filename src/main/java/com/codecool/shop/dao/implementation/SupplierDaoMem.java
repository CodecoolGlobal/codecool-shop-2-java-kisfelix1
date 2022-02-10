package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoMem implements SupplierDao {
    DataSource dataSource;
    private static SupplierDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private SupplierDaoMem() {
        try {
            dataSource = DatabaseManager.connect();
        }catch(SQLException e){
            System.out.println("Couldn't connect to the database!");
        }
    }

    public static SupplierDaoMem getInstance() {
        if (instance == null) {
            instance = new SupplierDaoMem();
        }
        return instance;
    }

    @Override
    public Supplier find(int id) {
        try(Connection connection = dataSource.getConnection()){
            String sql = "SELECT id, supplier_name, description FROM supplier WHERE id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) { // first row was not found == no data was returned by the query
                return null;
            }
            Supplier supplier = new Supplier(rs.getString(2), rs.getString(3));
            supplier.setId(rs.getInt(1));
            return supplier;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Supplier> getAll() {
        List<Supplier> suppliers = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();
        try(Connection connection = dataSource.getConnection()) {
            String sql = "SELECT id FROM supplier";
            ResultSet rs = connection.createStatement().executeQuery(sql);
            while (rs.next()) {
                ids.add(rs.getInt(1));
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (Integer id : ids) {
            suppliers.add(find(id));
        }
        return suppliers;
    }
}
