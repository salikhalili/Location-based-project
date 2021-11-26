/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import Dao.FindJobs;
import entity.Job;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author acer
 */
@Path("/jobs")
public class JobService {
    @GET
    @Path("/{skills}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Job> allJobsNearToLocation(
            @PathParam("skills") String skills,
            @QueryParam("longitude") double longitude,
            @QueryParam("latitude") double latitude){
        
        String[] skillArr = skills.split(",");
        double[] latlong = {79.0,6.0};
        
        FindJobs fj = new FindJobs();
        List<Job> jobs = fj.getJobs(skillArr, latlong);
        
        return jobs;
        
        
        
        
    }
    
}
