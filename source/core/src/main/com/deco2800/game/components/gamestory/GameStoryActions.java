package com.deco2800.game.components.gamestory;

import com.deco2800.game.GdxGame;
import com.deco2800.game.components.Component;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.deco2800.game.GdxGame.ScreenType.MAIN_GAME;
import static com.deco2800.game.GdxGame.ScreenType.MAIN_MENU;

public class GameStoryActions extends Component {
    private static final Logger logger = LoggerFactory.getLogger(GameStoryActions.class);
    private GdxGame game;

    public GameStoryActions(GdxGame game) {
        this.game = game;
    }

    @Override
    public void create() {
        entity.getEvents().addListener("start", this::onStart);
        entity.getEvents().addListener("exit", this::onExit);
    }

    /**
     * Swaps to the Main Game screen.
     */
    private void onStart() {
        logger.info("Start game");
        game.setScreen(MAIN_GAME);
    }

    /**
     * Swaps to the Main Menu screen.
     */
    private void onExit() {
        logger.info("Exiting main game screen");
        game.setScreen(MAIN_MENU);
    }
}
