package com.deco2800.game.components.bridge;

/**
 * The Lane class representing a lane on the bridge in Ragnorak Racer.
 * @author  Adrian Low
 *
 * A Lane that collectively builds a bridge
 */
public class Lane implements Location {

    private float y1;
    private float y2;
    private float mid;
    private Bridge bridge;

    /**
     * Set 2 y-coordinate that defines the bounds of a lane
     * @param top top y coordinate
     * @param bot bottom y coordinate
     */
    public Lane(float top, float bot, Bridge bridge) {
        if (top >= bot) {
            throw new IllegalArgumentException("top y-coordinate cannot be bigger or equal to bottom y-coordinate");
        } else {
            this.y1 = top;
            this.y2 = bot;
            this.mid = (top + bot) / 2;
            this.bridge = bridge;
        }
    }

    /**
     * returns the top y-coordinate of the lane
     * @return a y-coordinate
     */
    public float getY1() {
        return this.y1;
    }

    /**
     * returns the bottom y-coordinate of the lane
     * @return a y-coordinate
     */
    public float getY2() {
        return this.y2;
    }

    /**
     * returns the center y-coordinate of the lane from the user's perspective
     * TerrainFactory fills tiles from the bottom up
     * @return a y-coordinate
     */
    public float getMid() {
        return this.bridge.rescaleYGridToYView(this.mid);
    }

    /**
     * Returns the top of the lane from the user's perspective
     * TerrainFactory fills tiles from the bottom up
     * @return a y-coordinate
     */
    public float getTop() {
        return this.bridge.rescaleYGridToYView(this.y2);
    }

    /**
     * Returns the bottom of the lane from the user's perspective
     * TerrainFactory fills tiles from the bottom up
     * @return a y-coordinate
     */
    public float getBot() {
        return this.bridge.rescaleYGridToYView(this.y1);
    }
}
