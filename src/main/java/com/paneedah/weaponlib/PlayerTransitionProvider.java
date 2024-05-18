package com.paneedah.weaponlib;

import com.paneedah.weaponlib.animation.MultipartTransition;
import com.paneedah.weaponlib.animation.MultipartTransitionProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PlayerTransitionProvider implements MultipartTransitionProvider<RenderableState, Part, RenderContext<RenderableState>>  {

    public static class Builder {

        private final List<MultipartTransition<Part, RenderContext<RenderableState>>> proningTransitions = new ArrayList<>();
        
        private final List<MultipartTransition<Part, RenderContext<RenderableState>>> proningAimingTransitions = new ArrayList<>();

        public Builder withProningTransition(MultipartTransition<Part, RenderContext<RenderableState>> transition) {
            proningTransitions.add(transition);
            return this;
        }
        
        public Builder withProningAimingTransition(MultipartTransition<Part, RenderContext<RenderableState>> transition) {
            proningAimingTransitions.add(transition);
            return this;
        }

        public PlayerTransitionProvider build() {
            return new PlayerTransitionProvider(this);
        }
    }
    
    private final List<MultipartTransition<Part, RenderContext<RenderableState>>> normalTransitions =
            Collections.singletonList(
                    new MultipartTransition<Part, RenderContext<RenderableState>>(
                            Part.MAIN, renderContext -> {
                    }, 200, 0)
                            .withPartPositionFunction(Part.LEFT_HAND, rc -> {
                            })
                            .withPartPositionFunction(Part.RIGHT_HAND, rc -> {
                            })
                            .withPartPositionFunction(Part.LEFT_LEG, rc -> {
                            })
                            .withPartPositionFunction(Part.RIGHT_LEG, rc -> {
                            })
            );

    private Builder builder;

    private PlayerTransitionProvider(Builder builder) {
        this.builder = builder;
    }
    
    protected PlayerTransitionProvider() {}

    @Override
    public List<MultipartTransition<Part, RenderContext<RenderableState>>> getTransitions(RenderableState state) {
        if(state == RenderableState.PRONING) {
            return builder.proningTransitions;
        } else if(state == RenderableState.PRONING_AIMING) {
            return builder.proningAimingTransitions;
        } else {
            return normalTransitions;
        }
    }
}