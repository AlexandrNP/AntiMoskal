/*
 To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataSourceDAOJDBCFactory extends DAOFactory {
	private String serverName;
	private Integer portNumber;
	private String databaseName;
	private String user;
	private String password;
        private Map<String, String> shards;
        private Map<String, Integer> replicas;

    public void setReplicas(Map<String, Integer> replicas) {
        this.replicas = replicas;
    }

    public Map<String, Integer> getReplicas() {
        return replicas;
    }

    public Map<String, String> getShards() {
        return shards;
    }

    public void setShards(Map<String, String> shards) {
        this.shards = shards;
    }
        private MongoClient mongo = null;
	
	public void setServerName(String serverName){
		this.serverName = serverName;
	}
	
	public void setPortNumber(Integer portNumber){
		this.portNumber = portNumber;
	}
	
	public void setDatabaseName(String databaseName){
		this.databaseName = databaseName;
	}
	
	public void setUser(String user){
		this.user = user;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
    @Override
    public DB getConnection() {
        try {
            List addrs = new ArrayList();
            addrs.add( new ServerAddress( serverName , portNumber ) );
            for(String replica : replicas.keySet()) {
                addrs.add( new ServerAddress( replica , replicas.get(replica) ) );
            }
            
            mongo = new MongoClient(addrs);
        } catch (UnknownHostException ex) {
            Logger.getLogger(DataSourceDAOJDBCFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        DB ds = null;
        
        if(mongo!=null) {
            ds = mongo.getDB(databaseName);
            ds.setReadPreference(ReadPreference.primaryPreferred());
            for(String shardName : shards.keySet()) {
                ds.command(new BasicDBObject("addShard", (shardName+":"+shards.get(shardName))));
            }
            if(shards.size() > 0) {
                ds.command(new BasicDBObject("enablesharding", "products"));
            }
        }
        
        return ds;
    }


	@Override
	public ProductDAO getProductDAO() {
            return new ProductDAOJDBC(this);
	}
}
