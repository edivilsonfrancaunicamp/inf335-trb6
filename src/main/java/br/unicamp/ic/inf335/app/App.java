package br.unicamp.ic.inf335.app;

import br.unicamp.ic.inf335.database.JDBC;
import br.unicamp.ic.inf335.database.MongoDB;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Project 6
 */
public class App 
{
    public static void main( String[] args )
    {
        execJDBCProcess();
        System.out.println("process finished.");
    }

    private static void execJDBCProcess() {
        System.out.println("Init JDBC");
        JDBC jdbcClass = new JDBC();

        System.out.println("Open JDBC connection");
        Connection conn = JDBC.connectJDBC("userloja","INF335UNICAMP","jdbc:mysql://localhost/loja");
        try {
            if (conn != null) {
                String productId = "5";
                System.out.println("initial - get all products.");
                jdbcClass.getAllProductsJDBC(conn);
                System.out.println("inserting new product.");
                jdbcClass.insertProductJDBC(conn,
                        productId,
                        "Product Five",
                        "Default description of product",
                        "550.0",
                        "great product");

                System.out.println("after insert - get all products.");
                jdbcClass.getAllProductsJDBC(conn);
                System.out.println("update new product.");
                jdbcClass.updateProductPriceJDBC(conn, productId, "500.0");

                System.out.println("after update - get all products.");
                jdbcClass.getAllProductsJDBC(conn);
                System.out.println("delete specific product.");
                jdbcClass.deleteProductJDBC(conn, productId);

                System.out.println("after delete - get all products.");
                jdbcClass.getAllProductsJDBC(conn);
            }
            System.out.println("Closing JDBC connection");
            assert conn != null;
            conn.close();
        } catch (SQLException e) {
            System.out.println("Process JDBC error: " + e);
            e.printStackTrace();
        }
    }

    private static void execMongoDBProcess() {
        System.out.println("Initializing MongoDB");
        MongoDB mongoDBClass = new MongoDB();

        System.out.println("Open MongoDB connection");
        MongoCollection<Document> dbCollection = MongoDB.connectMongoDB("loja", "produto", "mongodb://localhost");
        try {
            if (dbCollection != null) {
                String productId = "1";
                System.out.println("initial - get all products.");
                mongoDBClass.getAllProductsMongoDB(dbCollection);
                System.out.println("inserting new product.");
                mongoDBClass.insertProductMongoDB(dbCollection,
                        productId,
                        "Product One",
                        "Default description of product",
                        "150.0",
                        "great product");

                System.out.println("after insert - get all products.");
                mongoDBClass.getAllProductsMongoDB(dbCollection);
                System.out.println("update new product.");
                mongoDBClass.updateProductPriceMongoDB(dbCollection, productId, "300.0");

                System.out.println("after update - get all products.");
                mongoDBClass.getAllProductsMongoDB(dbCollection);
                System.out.println("delete specific product.");
                //jdbcClass.deleteProductJDBC(conn, productId);

                System.out.println("after delete - get all products.");
                mongoDBClass.getAllProductsMongoDB(dbCollection);
            }
            System.out.println("Closing MongoDB connection");
            mongoDBClass.closeMongoDBConnection();
        } catch (Exception e) {
            System.out.println("Process MongoDB error: " + e);
            e.printStackTrace();
        }
    }
}
