package com.deco2800.game.entities.factories;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.ai.tasks.AITaskComponent;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.npc.GhostAnimationController;
import com.deco2800.game.components.TouchAttackComponent;
import com.deco2800.game.components.tasks.ChaseTask;
import com.deco2800.game.components.tasks.WanderTask;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.configs.BaseEntityConfig;
import com.deco2800.game.entities.configs.GhostKingConfig;
import com.deco2800.game.entities.configs.NPCConfigs;
import com.deco2800.game.files.FileLoader;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsUtils;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.physics.components.PhysicsMovementComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.services.ServiceLocator;

/**
 * Factory to create non-playable character (NPC) entities with predefined components.
 *
 * <p>Each NPC entity type should have a creation method that returns a corresponding entity.
 * Predefined entity properties can be loaded from configs stored as json files which are defined in
 * "NPCConfigs".
 *
 * <p>If needed, this factory can be separated into more specific factories for entities with
 * similar characteristics.
 */
public class NPCFactory {
  private static final NPCConfigs configs =
      FileLoader.readClass(NPCConfigs.class, "configs/NPCs.json");

  /**
   * Creates a ghost entity.
   *
   * @param target entity to chase
   * @return entity
   */
  public static Entity createGhost(Entity target) {
    Entity ghost = createBaseNPC(target);
    BaseEntityConfig config = configs.ghost;

    AnimationRenderComponent animator =
        new AnimationRenderComponent(
            ServiceLocator.getResourceService().getAsset("images/ghost1.atlas",
                    TextureAtlas.class));
    animator.addAnimation("dragon", 0.1f, Animation.PlayMode.LOOP);

    ghost
        .addComponent(new CombatStatsComponent(config.health, config.baseAttack))
        .addComponent(animator)
        .addComponent(new GhostAnimationController());

    //ghost.getComponent(AnimationRenderComponent.class).scaleEntity();//

    return ghost;
  }

  /**
   * Creates a littleGreen entity.
   *
   * @param target entity to chase
   * @return entity
   */
  public static Entity createLittleGreen(Entity target) {
    Entity littleGreen = createBaseNPC(target);
    BaseEntityConfig config = configs.littleGreen;

    AnimationRenderComponent animator =
            new AnimationRenderComponent(
                    ServiceLocator.getResourceService().getAsset("images/littlegreen1.atlas", TextureAtlas.class));
    animator.addAnimation("dragon", 0.1f, Animation.PlayMode.LOOP);

    littleGreen
            .addComponent(new CombatStatsComponent(config.health, config.baseAttack))
            .addComponent(animator)
            .addComponent(new GhostAnimationController());

    //littleGreen.getComponent(AnimationRenderComponent.class).scaleEntity();//

    return littleGreen;
  }

  public static Entity createDemon(Entity target) {
    Entity Demon = createBaseNPC(target);
    BaseEntityConfig config = configs.Demon;

    AnimationRenderComponent animator =
            new AnimationRenderComponent(
                    ServiceLocator.getResourceService().getAsset("images/demon1.atlas", TextureAtlas.class));
    animator.addAnimation("dragon", 0.1f, Animation.PlayMode.LOOP);

    Demon
            .addComponent(new CombatStatsComponent(config.health, config.baseAttack))
            .addComponent(animator)
            .addComponent(new GhostAnimationController());

    //littleGreen.getComponent(AnimationRenderComponent.class).scaleEntity();//

    return Demon;
  }
  /**
   * Creates a ghost king entity.
   *
   * @param target entity to chase
   * @return entity
   */
  public static Entity createGhostKing(Entity target) {
    Entity ghostKing = createBaseNPC1(target);
    GhostKingConfig config = configs.ghostKing;

    AnimationRenderComponent animator =
        new AnimationRenderComponent(
            ServiceLocator.getResourceService()
                .getAsset("images/dragon1.atlas", TextureAtlas.class));
    animator.addAnimation("dragon", 0.1f, Animation.PlayMode.LOOP);



    ghostKing
        .addComponent(new CombatStatsComponent(config.health, config.baseAttack))
        .addComponent(animator)
        .addComponent(new GhostAnimationController());

    //ghostKing.getComponent(AnimationRenderComponent.class).scaleEntity();//
    return ghostKing;
  }


  /**
   * Creates a generic NPC to be used as a base entity by more specific NPC creation methods.
   *
   * @return entity
   */
  private static Entity createBaseNPC(Entity target) {
    AITaskComponent aiComponent =
        new AITaskComponent()
            .addTask(new WanderTask(new Vector2(50, 0), 0f))
            .addTask(new ChaseTask(target, 0, 0, 0));
    Entity npc =
        new Entity(Entity.Type.GHOST)
            .addComponent(new PhysicsComponent())
            .addComponent(new PhysicsMovementComponent())
            //.addComponent(new ColliderComponent())
            .addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
            .addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 0f))
            .addComponent(aiComponent);

//    PhysicsUtils.setScaledCollider(npc, 0.9f, 0.4f);
    return npc;
  }

  private static Entity createBaseNPC1(Entity target) {
    AITaskComponent aiComponent =
            new AITaskComponent()
                    .addTask(new WanderTask(new Vector2(50f, 0f), 0f))
                    .addTask(new ChaseTask(target, 0, 0f, 0f));
    Entity npc1 =
            new Entity(Entity.Type.GHOSTKING)
                    .addComponent(new PhysicsComponent())
                    .addComponent(new PhysicsMovementComponent())
                    //.addComponent(new ColliderComponent())
                    .addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
                    .addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 0f))
                    .addComponent(aiComponent);

    //PhysicsUtils.setScaledCollider(npc, 0.9f, 0.4f);
    return npc1;
  }

  private NPCFactory() {
    throw new IllegalStateException("Instantiating static util class");
  }
}
