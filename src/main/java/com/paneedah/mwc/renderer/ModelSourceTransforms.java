package com.paneedah.mwc.renderer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.model.ModelBase;
import org.lwjgl.opengl.GL11;

import java.util.function.Consumer;

/**
 * Represents transformations for different rendering positions of a model.
 * <p>
 * This class contains various positioning callbacks that can be configured to modify the positioning of a model in different scenarios like entity, inventory, third-person view, and more.
 *
 * @author Luna Lage (Desoroxxx)
 * @since 0.1
 */
@Getter
@Setter
@Builder
public final class ModelSourceTransforms {

    // We use empty defaults values to not have null pointers exceptions thrown
    @Builder.Default private final Runnable entityPositioning = () -> {};
    @Builder.Default private final Runnable inventoryPositioning = () -> {};
    @Builder.Default private final Runnable thirdPersonPositioning = () -> {};
    @Builder.Default private final Runnable firstPersonPositioning = () -> {};
    @Builder.Default private final Runnable customEquippedPositioning = () -> {};
    @Builder.Default private final Consumer<ModelBase> firstPersonModelPositioning = model -> {};
    @Builder.Default private final Consumer<ModelBase> thirdPersonModelPositioning = model -> {};
    @Builder.Default private final Consumer<ModelBase> inventoryModelPositioning = model -> {};
    @Builder.Default private final Consumer<ModelBase> entityModelPositioning = model -> {};

    // Todo: Make this empty by making hand rendering a boolean somewhere than weapons and everything can call instead of spending time doing useless OGL calls
    // Unlike the rest, these two aren't empty it's because by default hands shouldn't be rendered
    @Builder.Default private final Runnable firstPersonLeftHandPositioning = () -> GL11.glScalef(0, 0, 0);
    @Builder.Default private final Runnable firstPersonRightHandPositioning = () -> GL11.glScalef(0, 0, 0);
}
