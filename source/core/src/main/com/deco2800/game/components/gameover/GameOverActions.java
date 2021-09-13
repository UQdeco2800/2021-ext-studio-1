package com.deco2800.game.components.gameover;

import com.deco2800.game.GdxGame;
import com.deco2800.game.components.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameOverActions extends Component {
    private static final Logger logger = LoggerFactory.getLogger(GameOverActions.class);
    private GdxGame game;

    /**
     * create an instance of this class
     *
     * @param game instance of game
     */
    public GameOverActions(GdxGame game) {
        this.game = game;
    }

    @Override
    public void create() {
        entity.getEvents().addListener("restart", this::onStart);
        entity.getEvents().addListener("exit", this::onExit);
    }

    private void onStart() {
        logger.info("Restart game");
        game.setScreen(GdxGame.ScreenType.MAIN_GAME);
    }

    private void onExit() {
        logger.info("Exit game");
        game.exit();
    }

}
