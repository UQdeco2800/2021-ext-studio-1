package com.deco2800.game.entities.factories;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.TouchAttackComponent;
import com.deco2800.game.components.player.InventoryComponent;
import com.deco2800.game.components.player.PlayerActions;
import com.deco2800.game.components.player.PlayerStatsDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.configs.PlayerConfig;
import com.deco2800.game.files.FileLoader;
import com.deco2800.game.input.InputComponent;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsUtils;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.*;
import com.deco2800.game.services.ServiceLocator;

/**
 * Factory to create a player entity.
 *
 * <p>Predefined player properties are loaded from a config stored as a json file and should have
 * the properties stores in 'PlayerConfig'.
 */
public class PlayerFactory {
  private static final PlayerConfig stats =
      FileLoader.readClass(PlayerConfig.class, "configs/player.json");

  /**
   * Create a player entity.
   * @return entity
   */
  public static Entity createPlayer() {
    InputComponent inputComponent =
        ServiceLocator.getInputService().getInputFactory().createForPlayer();


    AnimationRenderComponent2 animator2 =
            new AnimationRenderComponent2(
                    ServiceLocator.getResourceService()
                            .getAsset("images/touch.atlas",
                                    TextureAtlas.class));
    animator2.addAnimation("touch", 0.1f, Animation.PlayMode.NORMAL);


    AnimationRenderComponent animator =
            new AnimationRenderComponent(
                    ServiceLocator.getResourceService()
                            .getAsset("images/attack.atlas",
                                    TextureAtlas.class));
    animator.addAnimation("attack", 0.03f, Animation.PlayMode.NORMAL);

    AnimationRenderComponent5 animator5 =
            new AnimationRenderComponent5(
                    ServiceLocator.getResourceService()
                            .getAsset("images/run.atlas",
                                    TextureAtlas.class));
    animator5.addAnimation("run", 0.15f, Animation.PlayMode.LOOP);

    AnimationRenderComponent3 animator3 =
            new AnimationRenderComponent3(
                    ServiceLocator.getResourceService()
                            .getAsset("images/posipuff.atlas",
                                    TextureAtlas.class));
    animator3.addAnimation("buff", 0.15f, Animation.PlayMode.NORMAL);

    AnimationRenderComponent4 animator4 =
            new AnimationRenderComponent4(
                    ServiceLocator.getResourceService()
                            .getAsset("images/negbuff.atlas",
                                    TextureAtlas.class));
    animator4.addAnimation("deBuff", 0.15f, Animation.PlayMode.NORMAL);

    AnimationRenderComponent6 animator6 =
            new AnimationRenderComponent6(
                    ServiceLocator.getResourceService()
                            .getAsset("images/playercoin.atlas",
                                    TextureAtlas.class));
    animator6.addAnimation("coin", 0.1f, Animation.PlayMode.NORMAL);

    AnimationRenderComponent7 animator7 =
            new AnimationRenderComponent7(
                    ServiceLocator.getResourceService()
                            .getAsset("images/death.atlas",
                                    TextureAtlas.class));
    animator7.addAnimation("death", 0.1f, Animation.PlayMode.NORMAL);

//    AnimationRenderComponent animator1 =
//            new AnimationRenderComponent(
//                    ServiceLocator.getResourceService()
//                            .getAsset("images/posipuff.atlas",
//                                    TextureAtlas.class));
//    animator1.addAnimation("buff", 0.1f, Animation.PlayMode.NORMAL);
//    AnimationRenderComponent animator3 =
//            new AnimationRenderComponent(
//                    ServiceLocator.getResourceService()
//                            .getAsset("images/player.atlas",
//                                    TextureAtlas.class));
//    animator.addAnimation("attack", 0.03f, Animation.PlayMode.NORMAL);
//    animator.addAnimation("TouchObstacle", 0.03f, Animation.PlayMode.NORMAL);
//    animator.addAnimation("positiveBuff", 0.03f, Animation.PlayMode.NORMAL);
//    animator.addAnimation("negativeBuff", 0.03f, Animation.PlayMode.NORMAL);


    Entity player =
        new Entity(Entity.Type.PLAYER)
            .addComponent(new TextureRenderComponent("images/blank.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent())
            .addComponent(new HitboxComponent().setLayer(PhysicsLayer.PLAYER))
            .addComponent(new PlayerActions())
            .addComponent(new CombatStatsComponent(stats.health, stats.baseAttack, stats.armour))
            .addComponent(new InventoryComponent(stats.gold))
            .addComponent(inputComponent)
                .addComponent(new TouchAttackComponent(PhysicsLayer.NPC, PhysicsLayer.OBSTACLE, 1.5f))
                .addComponent(animator)
            .addComponent(new PlayerStatsDisplay())
                .addComponent(animator2)
                .addComponent(animator3)
                .addComponent(animator4)
                .addComponent(animator5)
            .addComponent((animator6));

    PhysicsUtils.setScaledCollider(player, 0.6f, 0.3f);
    player.getComponent(ColliderComponent.class).setDensity(1.5f);
    player.getComponent(TextureRenderComponent.class).scaleEntity();
    //player.getComponent(AnimationRenderComponent.class).scaleEntity();
    return player;
  }

  private PlayerFactory() {
    throw new IllegalStateException("Instantiating static util class");
  }
}
