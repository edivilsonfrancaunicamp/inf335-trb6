package br.unicamp.ic.inf335.database;

import java.sql.*;

public class JDBC {
    public static Connection connectJDBC(String user, String password, String url) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("Connection Error: " + e);
            e.printStackTrace();
        }
        return conn;
    }

    public void getAllProductsJDBC(Connection conn) {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Produto;");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String idProduto = rs.getString("idProduto");
                String nome = rs.getString("nome");
                String descricao = rs.getString("descricao");
                String valor = rs.getString("valor");
                String estado =  rs.getString("estado");
                System.out.println(idProduto + " -- " + nome + " -- " + descricao + " -- " + valor + " -- " + estado);
            }
        } catch (SQLException e) {
            System.out.println("Select query error:" + e);
            e.printStackTrace();
        }
    }

    public void insertProductJDBC(Connection conn, String idProduto, String nome, String descricao, String valor, String estado) {
        Statement stmt;
        try {
            stmt = conn.createStatement();
            String insertQuery = "INSERT INTO Produto VALUES ('"
                    + idProduto + "','"
                    + nome + "','"
                    + descricao + "','"
                    + valor + "','"
                    + estado + "');";
            stmt.executeUpdate(insertQuery);
        } catch (SQLException e) {
            System.out.println("Insert query error:" + e);
            e.printStackTrace();
        }
    }

    public void updateProductPriceJDBC(Connection conn, String idProduto, String valor) {
        Statement stmt;
        try {
            stmt = conn.createStatement();
            String updateQuery = "UPDATE Produto SET valor = '" + valor + "' WHERE idProduto = '" + idProduto + "';";
            stmt.executeUpdate(updateQuery);
        } catch (SQLException e) {
            System.out.println("Update query error:" + e);
            e.printStackTrace();
        }
    }

    public void deleteProductJDBC(Connection conn, String idProduto) {
        Statement stmt;
        try {
            stmt = conn.createStatement();
            String queryDelete = "DELETE FROM Produto WHERE idProduto = '" + idProduto + "';";
            stmt.executeUpdate(queryDelete);
        } catch (SQLException e) {
            System.out.println("Delete query error:" + e);
            e.printStackTrace();
        }
    }
}
