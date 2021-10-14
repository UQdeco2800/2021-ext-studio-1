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
public class EnemyTutorialActions extends Component {
    private static final Logger logger = LoggerFactory.getLogger(EnemyTutorialActions.class);
    private GdxGame game;

    public EnemyTutorialActions(GdxGame game) {
        this.game = game;
    }

    @Override
    public void create() {
        entity.getEvents().addListener("exit", this::onExit);
        entity.getEvents().addListener("tutorial", this::onTutorial);

    }

    private void exitMenu() {
        game.setScreen(ScreenType.MAIN_MENU);
    }

    /**
     * Exits the game.
     */
    private void onExit() {
        logger.info("Exit game");
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
