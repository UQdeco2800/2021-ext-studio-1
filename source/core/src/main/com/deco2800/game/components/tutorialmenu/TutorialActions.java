package com.deco2800.game.components.tutorialmenu;

import com.deco2800.game.GdxGame;
import com.deco2800.game.components.Component;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deco2800.game.GdxGame.ScreenType;
//import com.deco2800.game.components.tutorialmenu;

/**
 * This class listens to events relevant to the Main Menu Screen and does something when one of the
 * events is triggered.
 */
public class TutorialActions extends Component {
    private static final Logger logger = LoggerFactory.getLogger(TutorialActions.class);
    private GdxGame game;

    public TutorialActions(GdxGame game) {
        this.game = game;
    }

    @Override
    public void create() {
        entity.getEvents().addListener("exit", this::onExit);
        entity.getEvents().addListener("EnemyTutorial", this::onEnemyTutorial);

    }

    private void exitMenu() {
        game.setScreen(ScreenType.MAIN_MENU);
    }

    /**
     * Swaps to Enemy Tutorial Screen.
     */
    private void onEnemyTutorial() {
        logger.info("Launching enemy tutorial screen");
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

}
