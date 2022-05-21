package br.unicamp.ic.inf335.database;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

public class MongoDB {

    private static MongoClient mongoClient = null;
    public void closeMongoDBConnection() {
        if (mongoClient != null) mongoClient.close();
    }
    public static MongoCollection<Document> connectMongoDB(String dataBaseName, String collectionName, String dataBaseUrl) {
        MongoCollection<Document> colection = null;
        try {
            mongoClient = MongoClients.create(dataBaseUrl);
            MongoDatabase db = mongoClient.getDatabase(dataBaseName);
            colection = db.getCollection(collectionName);
        } catch (Exception e) {
            System.out.println("Connection Error: " + e);
            e.printStackTrace();
        }
        return colection;
    }

    public void getAllProductsMongoDB(MongoCollection<Document> dbCollection) {
        Iterable<Document> products = dbCollection.find();
        for (Document product : products) {
            String name = product.getString("nome");
            String description = product.getString("descricao");
            String price = product.getString("valor");
            String state =  product.getString("estado");
            System.out.println(name + " | " + description + " | " + price + " | " + state);
        }
    }

    public void insertProductMongoDB(MongoCollection<Document> dbCollection, String productId, String name, String description, String price, String state) {
        Document products = new Document();
        products.append("idProduto", productId);
        products.append("nome", name);
        products.append("descricao", description);
        products.append("valor", price);
        products.append("estado", state);
        dbCollection.insertOne(products);
    }

    public void updateProductPriceMongoDB(MongoCollection<Document> dbCollection, String productId, String price) {
        BasicDBObject productObj = new BasicDBObject();
        productObj.append("idProduto", productId);
        BasicDBObject priceObj = new BasicDBObject();
        priceObj.append("$set", new BasicDBObject().append("valor", price));
        dbCollection.updateMany(productObj, priceObj);
    }

    public void deleteProductMongoDB(MongoCollection<Document> dbCollection, String name) {
        dbCollection.deleteOne(Filters.eq("nome", name));
    }
}
