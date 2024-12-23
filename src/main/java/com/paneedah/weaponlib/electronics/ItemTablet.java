package com.paneedah.weaponlib.electronics;

import com.paneedah.mwc.instancing.PlayerItemInstanceFactory;
import com.paneedah.mwc.instancing.PlayerTabletInstance;
import com.paneedah.mwc.renderer.ModelSourceTransforms;
import com.paneedah.weaponlib.*;
import com.paneedah.mwc.rendering.Transform;
import com.paneedah.weaponlib.perspective.PerspectiveRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class ItemTablet<T> extends ItemAttachment<T> implements PlayerItemInstanceFactory<PlayerTabletInstance, TabletState>, Updatable {

    private final int DEFAULT_MAX_STACK_SIZE = 1;

    public static final class Builder<T> extends AttachmentBuilder<T> {

        {
            transforms = ModelSourceTransforms.builder()
                    .entityPositioning(() -> new Transform()
                            .withScale(0.3F, 0.3F, 0.3F)
                            .withPosition(-0.5F, -0.5F, 0.5F)
                            .applyTransformations())
                    .inventoryPositioning(() -> new Transform()
                            .withScale(1, 1, 1)
                            .withPosition(-0.24F, 0.24F, 0)
                            .applyTransformations())
                    .build();
        }

        private Runnable viewfinderPositioning;

        public Builder<T> withViewfinderPositioning(Runnable viewfinderPositioning) {
            this.viewfinderPositioning = viewfinderPositioning;
            return this;
        }

        @Override
        protected ItemAttachment<T> createAttachment(ModContext modContext) {
            if (viewfinderPositioning == null) {
                viewfinderPositioning = () -> {
                    GL11.glScalef(3f, 3f, 3f);
                    GL11.glTranslatef(0.1f, 0.5f, 0.1f);
                };
            }
            withPostRender(new PerspectiveRenderer(viewfinderPositioning));

            ItemTablet<T> itemTablet = new ItemTablet<>(this);
            itemTablet.modContext = modContext;

            return itemTablet;
        }

        @Override
        public ItemAttachment<T> build(ModContext modContext) {
            return super.build(modContext);
        }
    }


    private ModContext modContext;
    private final Builder<T> builder;

    private ItemTablet(Builder<T> builder) {
        super(AttachmentCategory.SCOPE, builder.getModel(), builder.getTextureName(), null, null);
        this.builder = builder;

        setMaxStackSize(DEFAULT_MAX_STACK_SIZE);
    }

    @Override
    public void update(EntityPlayer player) {

    }

    @Override
    public PlayerTabletInstance createItemInstance(final EntityLivingBase entityLivingBase, final ItemStack itemStack, final int slot) {
        final PlayerTabletInstance instance = new PlayerTabletInstance(slot, entityLivingBase, itemStack);

        instance.setState(TabletState.READY);

        return instance;
    }
}
