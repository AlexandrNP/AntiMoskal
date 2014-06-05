package dao;

import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.ParallelScanOptions;

import static dao.DAOJDBCUtil.*;
import java.sql.Statement;
import model.Product;


public class ProductDAOJDBC implements ProductDAO {

    private DAOFactory daoFactory;
    private String table;

    public ProductDAOJDBC(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
        table = "products";
    }


    public void setTable(String table){
    	this.table = table;
    }
    

    public boolean createProduct(Product product) {
        DB connection = connection = daoFactory.getConnection();
        connection.requestStart();
        try {
            DBCollection coll = connection.getCollection("products");
            BasicDBObject prod = new BasicDBObject("barcode", product.getBarcode())
                    .append("information", product.getInformation());
            coll.insert(prod);
            
            
        } catch (MongoException ex) {
            ex.printStackTrace();
        } finally {
            connection.requestDone();
        }
        return false;
    }

    private Product map(DBObject obj) {
        Product product = new Product();
        product.setInformation(obj.get("information").toString());
        product.setBarcode(obj.get("barcode").toString());
        return product;
    }


    
    public Product getByBarcode(String barCode) {
        DB connection = connection = daoFactory.getConnection();
        connection.requestStart();
        Product product = null;
        
                 
        try {
            DBCollection coll = connection.getCollection("products");
            DBObject doc = null;
            BasicDBObject whereQuery = new BasicDBObject();
            whereQuery.put("barcode", barCode);
            
              
        
            //coll.find
            doc = coll.findOne(whereQuery);
            
                 System.out.println();
                System.out.println();
                System.out.println(doc);
                System.out.println();
                System.out.println();
            
            if(doc != null) {                
                product = map(doc);                
            }
            
            
        } catch (MongoException ex) {
            ex.printStackTrace();
        } finally {
            connection.requestDone();
        }
      
        if(product == null) {
            product = new Product();
            product.setBarcode(barCode);
            product.setInformation("No info found!");
        }
        
        return product;
    }
    
    public boolean update(String barCode, String information) {
        DB connection = daoFactory.getConnection();
        connection.requestStart();
        Product product = null;
        try {
            DBCollection coll = connection.getCollection("products");
            DBObject doc = null;
            BasicDBObject whereQuery = new BasicDBObject();
            whereQuery.put("barcode", barCode);
            DBObject oldDoc = coll.findOne(whereQuery);
            
            BasicDBObject newDoc = new BasicDBObject("barcode", barCode)
                    .append("information", information);
            
            if (oldDoc == null) {
                coll.insert(newDoc);
                return true;
            }
            
            doc = coll.findAndModify(oldDoc, newDoc);
            if(doc != null) {
                return true;
            }
            
            
        } catch (MongoException ex) {
            ex.printStackTrace();
        } finally {
            connection.requestDone();
        }

    	return false;
    }
    
    public boolean delete(String barCode){
        DB connection = daoFactory.getConnection();
        connection.requestStart();
        Product product = null;
        try {
            DBCollection coll = connection.getCollection("products");
            DBObject doc = null;
            BasicDBObject whereQuery = new BasicDBObject();
            whereQuery.put("barcode", barCode);
            DBObject oldDoc = coll.findAndRemove(whereQuery);
            if(oldDoc != null) {
                return true;
            }
            
            
        } catch (MongoException ex) {
            ex.printStackTrace();
        } finally {
            connection.requestDone();
        }

    	return false;
    }


}
