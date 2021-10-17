package com.deco2800.game.components.Enemy;
import static org.mockito.Mockito.verify;
import com.deco2800.game.ai.tasks.AITaskComponent;
import com.deco2800.game.components.tasks.WanderTask;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.events.listeners.EventListener0;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.physics.components.PhysicsMovementComponent;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.utils.math.Vector2Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.mock;

@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
class EnemyWanderTaskTest {
        @Mock
        GameTime gameTime;

        @BeforeEach
        void beforeEach() {
            ServiceLocator.registerTimeSource(gameTime);
        }

        @Test
        void shouldTriggerEvent() {
            WanderTask wanderTask = new WanderTask(Vector2Utils.ONE, 0f);

            AITaskComponent aiTaskComponent = new AITaskComponent().addTask(wanderTask);
            Entity entity = new Entity().addComponent(aiTaskComponent).addComponent(new PhysicsMovementComponent());
            entity.create();

            // Register callbacks
            EventListener0 callback = mock(EventListener0.class);
            entity.getEvents().addListener("dragon", callback);

            wanderTask.start();

          verify(callback).handle();
        }
    }

