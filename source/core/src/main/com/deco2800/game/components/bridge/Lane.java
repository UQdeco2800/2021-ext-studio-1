package com.deco2800.game.components.bridge;

/**
 * The Lane class representing a lane on the bridge in Ragnorak Racer.
 * @author  Adrian Low
 *
 * A Lane that collectively builds a bridge
 */
public class Lane {

    private int top;
    private int bot;
    private int mid;

    /**
     * Set 2 y-coordinate that defines the bounds of a lane
     * @param top top y coordinate
     * @param bot bottom y coordinate
     */
    public Lane(int top, int bot) {
        if (top >= bot) {
            throw new IllegalArgumentException("top y-coordinate cannot be bigger or equal to bottom y-coordinate");
        } else {
            this.top = top;
            this.bot = bot;
            this.mid = (top + bot) / 2;
        }
    }

    /**
     * returns the top y-coordinate of the lane
     * @return a y-coordinate
     */
    public int getTop() {
        return this.top;
    }

    /**
     * returns the bottom y-coordinate of the lane
     * @return a y-coordinate
     */
    public int getBot() {
        return this.bot;
    }

    /**
     * returns the center y-coordinate of the lane
     * @return a y-coordinate
     */
    public int getMid() {
        return this.mid;
    }
}
