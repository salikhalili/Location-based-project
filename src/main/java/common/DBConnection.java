package common;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

@Named
@ApplicationScoped
public class DBConnection {

    private MongoDatabase mongoDB = null;
    private String server = "localhost";
    private int port = 27017;
    private MongoClient mongoclient = null;

    @PostConstruct
    public void afterCreate() {

        if (mongoDB == null) {

            try {
                this.mongoclient = new MongoClient(server, port);
            } catch (Exception e) {
                System.out.println("Could not connect to Mongo on Localhost: "
                        + e.getMessage());
            }

        }
        this.mongoDB = mongoclient.getDatabase("admin");
    }

    @Produces
    public MongoCollection<Document> getCollection() {
        return mongoDB.getCollection("jobs");
    }

}
