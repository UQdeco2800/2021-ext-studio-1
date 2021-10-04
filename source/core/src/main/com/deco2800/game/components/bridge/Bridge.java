package com.deco2800.game.components.bridge;
import com.deco2800.game.entities.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * The Bridge class representing the rainbow bridge in Ragnorak Racer.
 * @author  Adrian Low
 *
 * A Bridge consists of multiple lane classes and defines the bounds and location of the bridge on screen
 */
public class Bridge implements Location {

    /**
     * Offsets the location of the bridge to be
     * Used to change where the bridge is drawn on-screen
     * */
    private int offset;
    private int width;

    /**
     * Constant value for y range from the bottom of the grid to the top
     */
    private float yGridBot = 2f;
    private float yGridTop = 26f;

    /**
     * Sets a custom y range from the bottom to the top of the screen
     * Used to change where the bridge is drawn on-screen
     * */
    private float yViewBot;
    private float yViewTop;

    
    protected Entity player;

    /** A list of lanes on a bridge */
    private List<Lane> lanes;

    /**
     *
     * @param offset set a y coordinate to start drawing the bridge from.
     * @param width set the width size for a lane
     */
    public Bridge(int offset, int width) {
        this.offset = offset;
        this.lanes = new ArrayList<>();
        this.width = width;
        // Default ranges
        this.yViewBot = 2f;
        this.yViewTop = 26f;
        this.yGridBot = 2f;
        this.yGridTop = 26f;
    }

    /**
     * Returns the bridge offset
     * @return offset
     */
    public int getOffset() {
        return this.offset;
    }

    /**
     * Returns all lanes on the bridge
     * @return lanes
     */
    public List<Lane> getLanes() {
        return this.lanes;
    }

    public Lane getLastLane(){
        if (this.lanes.size() == 0) {
            throw new IllegalCallerException("Unable to get a lane from an empty list");
        } else {
            return this.lanes.get(this.lanes.size() - 1);
        }
    }

    /**
     * Get the width of lanes
     * @return
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Get the bottom y-coordinate edge of the screen window
     * @return y-coordinate
     */
    public float getYViewBot() {
        return this.yViewBot;
    }

    /**
     * Get the top y-coordinate edge of the screen window
     * @return y-coordinate
     */
    public float getYViewTop() {
        return this.yViewTop;
    }

    /**
     * Set a bottom y-coordinate edge of the screen window
     * @param y
     */
    public void setYViewBot(float y) {
        this.yViewBot = y;
    }

    /**
     * Set a top y-coordinate edge of the screen window
     * @param y
     */
    public void setYViewTop(float y) {
        this.yViewTop = y;
    }

    /**
     * Set both top edge and bottom edge of the screen window
     * @param top
     * @param bot
     */
    public void setYViews(float top, float bot) {
        setYViewTop(top);
        setYViewBot(bot);
    }

    /**
     * Get the bottom y-coordinate edge of the terrain grid
     * @return y-coordinate
     */
    public float getYGridBot() {
        return this.yGridBot;
    }

    /**
     * Get the top y-coordinate edge of the terrain grid
     * @return y-coordinate
     */
    public float getYGridTop() {
        return this.yGridTop;
    }

    /**
     * Set a bottom y-coordinate edge of the terrain grid
     * @param y
     */
    public void setYGridBot(float y) {
        this.yGridBot = y;
    }

    /**
     * Set a top y-coordinate edge of the terrain grid
     * @param y
     */
    public void setYGridTop(float y) {
        this.yGridTop = y;
    }

    /**
     * Set both top edge and bottom edge of the terrain grid
     * @param top
     * @param bot
     */
    public void setYGrids(float top, float bot) {
        setYGridTop(top);
        setYGridBot(bot);
    }

    /**
     * Adds a lane to the bridge
     */
    public void createLane() {
        Lane newLane;
        if (this.lanes.size() == 0) {
            newLane = new Lane(offset + this.yGridBot, offset + width + yGridBot, this);
        } else {
            float yCoordinate = this.lanes.get(this.lanes.size() - 1).getY2();
            newLane = new Lane(yCoordinate, yCoordinate + width, this);
        }
        lanes.add(newLane);
    }

    /**
     * removes the last lane from the bridge
     */
    public void removeLane() {
        if (this.lanes.size() > 0) {
            this.lanes.remove(this.lanes.size() - 1);
        }
    }

    /**
     * Returns the top of the bridge from the user's perspective
     * TerrainFactory fills tiles from the bottom up
     * @return a y-coordinate
     */
    public float getTop() {
        return rescaleYGridToYView(this.getLastLane().getTop());
    }

    /**
     * Returns the bottom of the bridge from the user's perspective
     * TerrainFactory fills tiles from the bottom up
     * @return a y-coordinate
     */
    public float getBot() {
        return rescaleYGridToYView(this.getLanes().get(0).getBot());
    }

    /**
     * Returns the center of the bridge from the user's perspective
     * TerrainFactory fills tiles from the bottom up
     * @return a y-coordinate
     */
    public float getMid() {
        return rescaleYGridToYView((this.getTop() + this.getBot()) / 2);
    }

    /**
     * Returns a percentage from num in range of yGridBot and yGridTop
     * @param num between start and end values
     * @return percentage between start and end values
     */
    private float gridRangeToPercentage(float num) {
        return ((num - this.yGridBot) * 100) / (this.yGridTop - this.yGridBot);
    }

    /**
     * Returns a percent for a num between yViewBot and yViewTop
     * @param percentage between start and end values
     * @return num between start and end values
     */
    private float percentageToNum(float percentage) {
        return (percentage * (this.yViewTop - this.yViewBot) / 100) + this.yViewBot;
    }

    /**
     * Rescale a y-coordinate from TerrainFactory grid into a screen view y-coordinate
     * @param y
     * @return rescaled y-coordinate
     */
    public float rescaleYGridToYView(float y) {
        return percentageToNum(gridRangeToPercentage(y));
    }

    /**
     * Returns player entity.
     *
     * @return player entity
     */
    public Entity getPlayer() {
        return player;
    }
}
