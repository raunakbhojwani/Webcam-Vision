import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.*;
import java.util.*;

import javax.swing.AbstractAction;
import javax.swing.Timer;

/**
 * Universe for PS-1 Catch game.
 * Holds the fliers and the background image.
 * Also finds and holds the regions in the background image.
 * Each region is a list of contiguous points with colors similar to a target color.
 * Sample solution to Lab 1, Dartmouth CS 10, Winter 2015
 * 
 * @author Chris Bailey-Kellogg, Winter 2014 (based on a very different structure from Fall 2012)
 * @author Travis W. Peters, Dartmouth CS 10, Updated Winter 2015
 */
public class Universe {
    private static final int maxColorDiff = 20;				// how similar a pixel color must be to the target color, to belong to a region
    private static final int minRegion = 50; 				// how many points in a region to be worth considering

    private BufferedImage image;                            // a reference to the background image for the universe
    private Color trackColor;                               // color of regions of interest (set by mouse press)

    private ArrayList<ArrayList<Point>> regions;			// a region is a list of points
    // so the identified regions are in a list of lists of points
    //private ArrayList<Point> region;
    //private Stack toBeVisited;

    private ArrayList<Flier> fliers;                        // all of the fliers

    /**
     * New universe with a background image and an empty list of fliers
     * @param image
     */
    public Universe(BufferedImage image) {
        this.image = image;		
        fliers = new ArrayList<Flier>();
    }

    /**
     * Set the image (from the webcam) that makes up the universe's background
     * @param image
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * Allow others to ask about the state of the trackColor in the universe
     * @return
     */
    public Color getTrackingColor() {
        return trackColor;
    }

    /**
     * Setting the color from an explicitly defined Color object 
     * as opposed to getting input from the player.
     * @param color
     */
    public void setTrackingColor(Color color) {
        trackColor = color;
    }

    /**
     * Allow others to ask about the size of the universe (width)
     * @return
     */
    public int getWidth() {
        return image.getWidth();
    }

    /**
     * Allow others to ask about the size of the universe (height)
     * @return
     */
    public int getHeight() {
        return image.getHeight();
    }

    /**
     * Accesses the currently-identified regions.
     * @return
     */
    public ArrayList<ArrayList<Point>> getRegions() {
        return regions;
    }

    /**
     * Set the universe's regions.
     * @return
     */
    public void setRegions(ArrayList<ArrayList<Point>> regions) {
        this.regions = regions;
    }

    /**
     * Adds the flier to the universe
     * @param f
     */
    public void addFlier(Flier f) {
        fliers.add(f);
    }

    /**
     *  Move the flier and detect catches and misses
     */
    public void moveFliers() {
    	//TODO: YOUR CODE HERE
    	for(Flier flier: fliers) {
            flier.move();
            flier.checkLose();
            flier.checkWin(); 
    	}
    		
    }

    /**
     * Draw the fliers
     */
    public void drawFliers(Graphics g) {
    	//TODO: YOUR CODE HERE
    	for(Flier f: fliers) {
    		f.draw(g);
    	}
    	
    }

    /**
     * Sets regions to the flood-fill regions in the image, similar enough to the trackColor.
     */
    public void findRegions() {
    //TODO: YOUR CODE HERE
    	/*
    	 * Loop over all the pixels
    			If a pixel is unvisited and of the correct color
    			Start a new region
    			Keep track of which pixels need to be visited, initially just that one
    			As long as there's some pixel that needs to be visited
        			Get one to visit
        			Add it to the region
        			Mark it as visited
        			Loop over all its neighbors
            			If the neighbor is of the correct color
                			Add it to the list of pixels to be visited
    		If the region is big enough to be worth keeping, do so
    	 */
    	
    	boolean[][] visited = new boolean [image.getWidth()][image.getHeight()];
    	
    	regions = new ArrayList<ArrayList<Point>>();
    	
    	for (int x = 0; x < image.getWidth(); x++) {
    		for (int y = 0; y < image.getHeight(); y++) {
    			Color currentColor = new Color(image.getRGB(x,y));
    			if(!visited[x][y] && colorMatch(trackColor, currentColor)) {
    				Point point = new Point (x,y);
    				ArrayList<Point> region = new ArrayList<Point>();
    				Stack toBeVisited = new Stack<Point>();
    				toBeVisited.push(point);
    				
    				while(!toBeVisited.isEmpty()) {
    					Point current = (Point) toBeVisited.pop();
    					region.add(new Point((int) current.x, (int) current.y));
    					visited[(int) current.x][(int) current.y] = true;
    					
    					for(int x1 = current.x - 1; x1 <= current.x + 1; x1++) {
    						for(int y1 = current.y - 1; y1 <= current.y + 1; y1++) {
    							if(x1>=0 && x1<image.getWidth() && y1>=0 && y1<image.getHeight()) {
    								Color newcolor = new Color((image.getRGB(x1, y1)));
    								if(colorMatch(trackColor, newcolor) && !visited[x1][y1]) {
    									toBeVisited.push(new Point(x1,y1));
    								}
    							}
    						}
    					}
    					System.out.println(toBeVisited.size());
    				}
    				if(region.size() >= minRegion) {
						regions.add(region);
    				}
    				
    				
    				
    				
    			}
    		}
    	}
			
    }

    /**
     * Tests whether the two colors are "similar enough" (your definition, subject to the static threshold).
     * @param c1
     * @param c2
     * @return
     */
    private static boolean colorMatch(Color c1, Color c2) {
    	//TODO: YOUR CODE HERE
   
    	int red = Math.abs(c1.getRed() - c2.getRed());
    	int green = Math.abs(c1.getGreen() - c2.getGreen());
    	int blue = Math.abs(c1.getBlue() - c2.getBlue());
    	
    	if(red <= maxColorDiff && green <= maxColorDiff && blue <= maxColorDiff) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }

    /**
     * Recolors image so that each region is a random uniform color, so we can see where they are
     */
    public void recolorRegions() {
    	//TODO: YOUR CODE HERE
    	for(ArrayList<Point> region: regions) {
    			int red1 = (int) (Math.random() * 255);
    			int green1 = (int) (Math.random() * 255);
    			int blue1 = (int) (Math.random() * 255);
    			
    			Color color1 = new Color(red1, green1, blue1);
    			
    			for(Point point: region) {
    				image.setRGB((int) point.getX(),(int) point.getY(), color1.getRGB());
    		}
    	}	
    }
}
