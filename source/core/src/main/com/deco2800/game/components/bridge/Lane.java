package com.deco2800.game.components.bridge;

/**
 * The Lane class representing a lane on the bridge in Ragnorak Racer.
 * @author  Adrian Low
 *
 * A Lane that collectively builds a bridge
 */
public class Lane implements Location {

    private int y1;
    private int y2;
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
            this.y1 = top;
            this.y2 = bot;
            this.mid = (top + bot) / 2;
        }
    }

    /**
     * returns the top y-coordinate of the lane
     * @return a y-coordinate
     */
    public int getY1() {
        return this.y1;
    }

    /**
     * returns the bottom y-coordinate of the lane
     * @return a y-coordinate
     */
    public int getY2() {
        return this.y2;
    }

    /**
     * returns the center y-coordinate of the lane from the user's perspective
     * TerrainFactory fills tiles from the bottom up
     * @return a y-coordinate
     */
    public int getMid() {
        return this.mid;
    }

    /**
     * Returns the top of the lane from the user's perspective
     * TerrainFactory fills tiles from the bottom up
     * @return a y-coordinate
     */
    public int getTop() {
        return this.y2;
    }

    /**
     * Returns the bottom of the lane from the user's perspective
     * TerrainFactory fills tiles from the bottom up
     * @return a y-coordinate
     */
    public int getBot() {
        return this.y1;
    }
}
