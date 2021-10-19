package com.deco2800.game.components.tutorialmenu;

import com.deco2800.game.GdxGame;
import com.deco2800.game.components.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deco2800.game.GdxGame.ScreenType;

/**
 * This class listens to events relevant to the Map content Screen and does something when one of the
 * events is triggered.
 */
public class MapContentTutorialActions extends Component {
    private static final Logger logger = LoggerFactory.getLogger(MapContentTutorialActions.class);
    private GdxGame game;

    public MapContentTutorialActions(GdxGame game) {
        this.game = game;
    }

    @Override
    public void create() {
        entity.getEvents().addListener("exit", this::onExit);
        entity.getEvents().addListener("EnemyTutorial", this::onEnemyTutorial);
        entity.getEvents().addListener("tutorial", this::onTutorial);

    }

    private void exitMenu() {
        game.setScreen(ScreenType.MAIN_MENU);
    }

    /**
     * Swaps to Enemy Tutorial Screen.
     */
    private void onEnemyTutorial() {
        logger.info("Launching map content tutorial screen");
        game.setScreen(GdxGame.ScreenType.ENEMY_TUTORIAL);
    }

    /**
     * Exits the game.
     */
    private void onExit() {
        logger.info("Exit game");
        logger.info("Launch enemy tutorial screen");
        exitMenu();
    }

    /**
     * Swaps to Tutorial Screen.
     */
    private void onTutorial() {
        logger.info("Launching tutorial screen");
        game.setScreen(GdxGame.ScreenType.TUTORIAL);
    }

}
