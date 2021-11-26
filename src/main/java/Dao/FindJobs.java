package Dao;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.all;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.near;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import common.DBConnection;
import entity.Job;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;

public class FindJobs {

    private Job job;
    DBConnection dbc;
    MongoCollection mongocollection;
    MongoCollection<Document> collection;

    public FindJobs() {
        MongoClient mongoclient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoclient.getDatabase("admin");
         this.collection = database.getCollection("jobs");
        
    }

    public List<Job> getJobs(String[] skills, double[] latlong) {
        double lon = latlong[0];
        double lat = latlong[1];
        Point o = new Point(new Position(lon, lat));
        Bson filter = and(all("skills", Arrays.asList(skills)), near("location", o, 50000000000000.0, 1.0));
        MongoCursor<Document> it = collection.find(filter).iterator();
        List<Job> jobs = new ArrayList<Job>();
        while (it.hasNext()) {
            Job job = new Job(it.next());
            jobs.add(job);
        }
        return jobs;

    }

}
