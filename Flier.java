import java.util.*;
import java.awt.*;

/**
 * An animated object flying across the scene in a fixed direction
 * Sample solution to Lab 1, Dartmouth CS 10, Winter 2015
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012; revised Winter 2014
 * @author Travis W. Peters, Dartmouth CS 10, Updated Winter 2015
 */
public class Flier extends Agent {

    /* 
     * TODO: YOUR CODE HERE
     * 
     * The Flier class should have AT LEAST the following attributes 
     * in addition to what we've defined for you:
     *  - speed
     *  - direction of flight
     */

    private Universe universe;         // the universe that a flier exists within
    private double dx = 5, dy = 5;
 
    
    public Flier(Universe universe) {
        super(0,0);

        this.universe = universe;
        universe.addFlier(this);
    }

    /**
     * Overrides Agent.move() to step by dx, dy
     */
    public void move() {
        // TODO: YOUR CODE HERE
    	x = x + dx;
    	y = y + dy;
    }

    /**
     * Detect hitting the region (and restart)
     */
    public void checkWin() {
        // TODO: YOUR CODE HERE
    	if(universe != null) {
    		if(universe.getRegions() != null){
    			for(ArrayList<Point> region: universe.getRegions()) {
    				for(Point point: region) {
        				if(x == point.getX() && y == point.getY()) {
        					toss();
        			}
        		}
    	}
    		}
    	}
    	}
    	


    /**
     * Detect exiting the window (and restart)
     */
    public void checkLose() {
        // TODO: YOUR CODE HERE
    	if(x > universe.getWidth() || x < 0 || y > universe.getHeight() || y < 0) {
    		toss();
    	}
    }

    /**
     * Puts the object at a random point on one of the four borders, 
     * flying in to the window at a random speed.
     */
    public void toss() {
        // TODO: YOUR CODE HERE	  
    	double random = Math.random();
    	
    	if(random <= 0.25) {
    		x = 0;
    		y = (int) (Math.random() * universe.getHeight());
    		dx = 8;
    		if(y <= universe.getHeight()/2) {
    			dy = 6;
    		}
    		else {
    			dy = -6;
    		}
    		
    	}
    	else if(random <= 0.5) {
    		x = universe.getWidth();
    		y = (int) (Math.random() * universe.getHeight());
    		dx = -8;
    		if(y <= universe.getHeight()/2) {
    			dy = 6;
    		}
    		else {
    			dy = -6;
    		}
    		}
    	else if(random <= 0.75) {
    		y = 0;
    		x = (int) (Math.random() * universe.getWidth());
    		dy = 8;
    		if(x <= universe.getWidth()) {
    			dx = 6;
    		}
    		else {
    			dx = -6;
    		}
    		}
    	else if(random <= 1.0) {
    		y = universe.getHeight();
    		x = (int) (Math.random() * universe.getWidth());
    		dy = -8;
    		if(x <= universe.getWidth()) {
    			dx = 6;
    		}
    		else {
    			dx = -6;
    		}
    		}
    }
}
