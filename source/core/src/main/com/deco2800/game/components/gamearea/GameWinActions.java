package com.deco2800.game.components.gamearea;

import com.deco2800.game.GdxGame;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.maingame.MainGameActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameWinActions  extends Component {
    private static final Logger logger = LoggerFactory.getLogger(MainGameActions.class);
    private final GdxGame game;

    public GameWinActions(GdxGame game) {
        this.game = game;
    }

    @Override
    public void create() {
        entity.getEvents().addListener("replay game", this::onReplay);
        entity.getEvents().addListener("exit", this::onExit);
    }

    /**
     * Swaps to the Main Menu screen.
     */
    private void onExit() {
        logger.info("Exiting main game screen");
        game.setScreen(GdxGame.ScreenType.MAIN_MENU);
    }


    /**
     * Swaps to the Main Game Screen when the character dies.
     */
    private void onReplay() {
        logger.info("Restart Game");
        game.setScreen(GdxGame.ScreenType.MAIN_GAME);
    }
}
