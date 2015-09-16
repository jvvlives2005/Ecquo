package abassawo.c4q.nyc.ecquo.Model;

/**
 * Created by c4q-Abass on 8/18/15.
 */
public class User {
    public static int pointTally;
    public static String points  = pointTally + " " + "Points "; ;

    public User(){
        this.pointTally = 100;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public int getPointTally() {
        return pointTally;
    }

    public void setPointTally(int pointTally) {
        this.pointTally = pointTally;
    }

    public void addtoPoints(int numToAddBy){
        this.pointTally = pointTally + numToAddBy;
    }
}
