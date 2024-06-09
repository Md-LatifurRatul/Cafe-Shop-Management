/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cafeshopmanagement.system;

import java.sql.*;

/**
 *
 * @author kmrsk
 */
public class database {

    public static Connection connectDB() {

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/cafeshop", "root","");
            return connect;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

}
