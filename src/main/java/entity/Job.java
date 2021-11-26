package entity;

import java.util.Arrays;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import java.util.ArrayList;
import org.bson.Document;

public class Job {

    private String companyName;

    private String jobTitle;

    private double distance;

    private String[] skills;

    private String formattedAddress;

    private Object longitude;

    private Object latitude;

    public Job() {
        // TODO Auto-generated constructor stub
    }

    public Job(BasicDBObject obj) {
        BasicDBObject result = (BasicDBObject) obj;
        this.distance = result.getDouble("dis");
        BasicDBObject jobObj = (BasicDBObject) result.get("obj");
        this.companyName = ((BasicDBObject) jobObj.get("company"))
                .getString("companyName");
        this.jobTitle = jobObj.getString("jobTitle");
        BasicDBList locationList = (BasicDBList) jobObj.get("location");
        this.longitude = locationList.get(0);
        this.latitude = locationList.get(1);
        this.formattedAddress = jobObj.getString("formattedAddress");
        this.skills = ((BasicDBList) jobObj.get("skills")).toArray(new String[0]);
    }

    public Job(DBObject obj) {
        BasicDBObject resultCompany = (BasicDBObject) obj.get("company");
        BasicDBList locationList = (BasicDBList) obj.get("location");
        this.skills = ((BasicDBList) obj.get("skills")).toArray(new String[0]);
        this.companyName = resultCompany.get("companyName").toString();
        this.latitude = locationList.get(1);
        this.latitude = locationList.get(0);
        this.jobTitle = (String) obj.get("jobTitle");
        this.formattedAddress = obj.get("formattedAddress").toString();
    }

    public Job(Document obj) {
        Document resultCompany = (Document) obj.get("company");
        Document locationList = (Document) obj.get("location");
        ArrayList doc = (ArrayList) obj.get("skills");
        Document loc = (Document) obj.get("location");
        //////////////////////////////////////////////////////////////////////////////// خیلی بد
        ArrayList cordin = (ArrayList) loc.get("coordinates");
        this.longitude = cordin.get(0);
        this.latitude = cordin.get(1);
        this.skills = new String[doc.size()];
        this.skills = (String[]) doc.toArray(skills);
///////////////////////////////////////////////////////////////////////////////////////////////
        this.companyName = resultCompany.get("companyName").toString();

        this.jobTitle = (String) obj.get("jobTitle");
        this.formattedAddress = obj.get("formattedAddress").toString();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String[] getSkills() {
        return skills;
    }

    public void setSkills(String[] skills) {
        this.skills = skills;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public void setLongitude(Object longitude) {
        this.longitude = longitude;
    }

    public Object getLongitude() {
        return longitude;
    }

    public void setLatitude(Object latitude) {
        this.latitude = latitude;
    }

    public Object getLatitude() {
        return latitude;
    }

    @Override
    public String toString() {
        return "Job [companyName=" + companyName + ", jobTitle=" + jobTitle
                + ", distance=" + distance + ", skills="
                + Arrays.toString(skills) + ", formattedAddress="
                + formattedAddress + "]";
    }

}
