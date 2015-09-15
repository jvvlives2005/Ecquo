package abassawo.c4q.nyc.ecquo;


/**
 * Rating algorithm
 *   1.closes due date
 *   2.priority
 *   3.location
 *
 *   User
 *      1.Current Location
 *      2.Current day
 *
 *   Weighting sort
 *   (0.5) (Date Score)
 *   (0.4) (Priority Score)
 *   (0.1) (Location Score)
 */

public class SortingAlg {

    private double mTime, mLocation, m;
    private String mDate;
    private int mGreatest, mPriority;


    // Need a method to sort out the days

    public SortingAlg(){

    }

    public void sorting(int[] array){
        for(int i = 1; i < array.length; i++){
            mGreatest = 0;
            if(mGreatest < array[i]){
                mGreatest = array[i];
            }
        }
    }

    public double rankingLocation(double currentLocation , double setPlace){
        double rank;
        rank = (0.1) * (setPlace - currentLocation);
        return rank;
    }

    public double rankingTime(double currentTime, double dueTime){
        double rank;
        rank = (0.5) * (dueTime - currentTime);
        return rank;
    }

    public double rankingPriority(int priority){
        double rank;
        rank = (0.4) * priority;
        return rank;
    }

    public void convertLocationIntoMeter(double lan, double lag){

    }

    public void getGreatet(){
        this.mGreatest = mGreatest;
    }



}
