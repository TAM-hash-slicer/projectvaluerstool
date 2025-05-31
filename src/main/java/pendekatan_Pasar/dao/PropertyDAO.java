package pendekatan_Pasar.dao;

import pendekatan_Pasar.model.Pembanding;
import pendekatan_Pasar.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PropertyDAO {

    public List<Pembanding> getComparables() {
        List<Pembanding> list = new ArrayList<>();
        String query = "SELECT * FROM properties";

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Pembanding prop = new Pembanding();
                prop.setId(rs.getString("id"));
                prop.setType(rs.getString("type"));
                prop.setLocation(rs.getString("location"));
                prop.setLandArea(rs.getDouble("land_area"));
                prop.setBuildingArea(rs.getDouble("building_area"));
                prop.setYearBuilt(rs.getInt("year_built"));
                prop.setTransactionPrice(rs.getDouble("transaction_price"));
                prop.setTransactionDate(rs.getString("transaction_date"));
                list.add(prop);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
