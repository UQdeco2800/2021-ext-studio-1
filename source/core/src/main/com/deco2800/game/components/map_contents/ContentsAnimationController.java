package com.deco2800.game.components.map_contents;

import com.deco2800.game.components.Component;
import com.deco2800.game.rendering.AnimationRenderComponent;

/**
 * Causes all map contents to move left automatically towards the player 
 */
public class ContentsAnimationController extends Component {
  AnimationRenderComponent animator;

  @Override
  public void create() {
    super.create();
    animator = this.entity.getComponent(AnimationRenderComponent.class);
    entity.getEvents().addListener("wanderStart", this::animateMove);
  }

  void animateMove() {
    animator.startAnimation("move_left");
  }
}
