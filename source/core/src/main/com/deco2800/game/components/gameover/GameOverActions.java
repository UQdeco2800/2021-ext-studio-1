package com.deco2800.game.components.gameover;

import com.deco2800.game.GdxGame;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.maingame.MainGameActions;
import com.deco2800.game.components.player.KeyboardPlayerInputComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameOverActions extends Component {

    private static final Logger logger = LoggerFactory.getLogger(GameOverActions.class);
    private GdxGame game;

    /**
     * create an instance of this class
     * @param game instance of game
     */
    public GameOverActions(GdxGame game) {
        this.game = game;
    }

    @Override
    public void create() {
        entity.getEvents().addListener("exit", this::onExit);
        entity.getEvents().addListener("restarting game", this::onRestart);
        entity.getEvents().addListener("back to menu", this::onMenu);
    }

    /**
     * Exit the main game
     */
    private void onExit() {
        logger.info("Exiting the main game");
        game.exit();
    }

    /**
     * Swaps to the Main Game Screen
     */
    private void onRestart() {
        logger.info("Restart Game");
        KeyboardPlayerInputComponent.i = 0;
        game.setScreen(GdxGame.ScreenType.MAIN_GAME);
    }

    /**
     * Swaps to the Main Menu Screen
     */
    private void onMenu() {
        logger.info("Back to Menu");
        game.setScreen(GdxGame.ScreenType.MAIN_MENU);
    }
}
