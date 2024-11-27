package com.paneedah.mwc.groovyscript.script;

import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.api.documentation.annotations.*;
import com.cleanroommc.groovyscript.helper.recipe.AbstractRecipeBuilder;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import com.paneedah.mwc.ProjectConstants;
import com.paneedah.mwc.groovyscript.recipes.GSCrafting;
import com.paneedah.weaponlib.crafting.CraftingEntry;
import com.paneedah.weaponlib.crafting.CraftingGroup;
import com.paneedah.weaponlib.crafting.CraftingRegistry;
import com.paneedah.weaponlib.crafting.IModernCraftingRecipe;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import static com.paneedah.mwc.ProjectConstants.NAME;
import static com.paneedah.mwc.groovyscript.MWCGroovyPlugin.craftingStation;
import static com.paneedah.weaponlib.crafting.CraftingGroup.*;
import static com.paneedah.weaponlib.crafting.CraftingRegistry.*;

@RegistryDescription(linkGenerator = ProjectConstants.ID)
public class CraftingStation extends VirtualizedRegistry<IModernCraftingRecipe> {

    @Override
    public void onReload() {
        this.removeScripted().forEach(this::removeRecipe);
        this.restoreFromBackup().forEach(this::addRecipe);
    }

    /**
     * Remove ALL recipes. no matter the category.
     */
    @MethodDescription(priority = 4000)
    public void removeAll() {
        for (CraftingGroup list : craftingMap.keySet()) {
            this.removeInGroupWithFilter(e -> true, list);
        }
    }

    /**
     * Remove Recipe that outputs given Ingredient.
     *
     * @param ingredient Target Ingredient. any recipe with matching output will be removed.
     */
    @MethodDescription(example = @Example("ore('oreDiamond')"), priority = 2000)
    public void remove(IIngredient ingredient) {
        for (CraftingGroup list : craftingMap.keySet()) {
            this.removeInGroupWithFilter(ingredient, list);
        }
    }

    /**
     * Remove ALL recipes of Given Category.
     *
     * @param group CraftingGroup to remove all recipes inside. valid values = "GUN", "ATTACHMENT_NORMAL", "ATTACHMENT_MODIFICATION", "BULLET", "MAGAZINE"
     */
    @MethodDescription(example = @Example("'ATTACHMENT_NORMAL'"), priority = 3000)
    public void removeAllinGroup(String group) {
        if (Arrays.stream(CraftingGroup.values()).noneMatch(g -> g.name().equals(group))) {
            return;
        }
        removeAllinGroup(CraftingGroup.valueOf(group));
    }

    /**
     * Remove ALL recipes of Gun Category.
     */
    @MethodDescription()
    public void removeAllGun() {
        removeAllinGroup(GUN);
    }

    /**
     * Remove ALL recipes of Normal Attachment Category.
     */
    @MethodDescription()
    public void removeAllNormalAttachment() {
        removeAllinGroup(ATTACHMENT_NORMAL);
    }

    /**
     * Remove ALL recipes of Modification Attachment Category.
     */
    @MethodDescription()
    public void removeAllModificationAttachment() {
        removeAllinGroup(ATTACHMENT_MODIFICATION);
    }

    /**
     * Remove ALL recipes of Bullet Category.
     */
    @MethodDescription()
    public void removeAllBullet() {
        removeAllinGroup(BULLET);
    }

    /**
     * Remove ALL recipes of Magazine Category.
     */
    @MethodDescription()
    public void removeAllMagazine() {
        removeAllinGroup(MAGAZINE);
    }

    @MethodDescription(priority = 3000)
    public void removeAllinGroup(CraftingGroup group) {
        removeInGroupWithFilter((s) -> true, group);
    }

    /**
     * Remove recipe that outputs matching item of given `ingredient` from given category.
     *
     * @param ingredient Output item of the recipe. recipe with matching output will be removed.
     * @param group      CraftingGroup to remove matching recipes inside. valid values = "GUN", "ATTACHMENT_NORMAL", "ATTACHMENT_MODIFICATION", "BULLET", "MAGAZINE"
     */
    @MethodDescription(example = @Example("ore('oreDiamond'), 'ATTACHMENT_NORMAL'"))
    public void removeInGroup(IIngredient ingredient, String group) {
        if (Arrays.stream(CraftingGroup.values()).noneMatch(g -> g.name().equals(group))) {
            return;
        }
        removeInGroup(ingredient, CraftingGroup.valueOf(group));
    }

    /**
     * Removes recipe that outputs matching item of given `ingredient` from gun category.
     *
     * @param ingredient Output item of the recipe. recipe with matching output will be removed.
     */
    @MethodDescription(example = @Example("ore('oreDiamond')"))
    public void removeGun(IIngredient ingredient) {
        removeInGroup(ingredient, GUN);
    }

    /**
     * Removes recipe that outputs matching item of given `ingredient` from normal attachment category.
     *
     * @param ingredient Output item of the recipe. recipe with matching output will be removed.
     */
    @MethodDescription(example = @Example("ore('oreDiamond')"))
    public void removeNormalAttachment(IIngredient ingredient) {
        removeInGroup(ingredient, ATTACHMENT_NORMAL);
    }

    /**
     * Removes recipe that outputs matching item of given `ingredient` from modification attachment category.
     *
     * @param ingredient Output item of the recipe. recipe with matching output will be removed.
     */
    @MethodDescription(example = @Example("ore('oreDiamond')"))
    public void removeModificationAttachment(IIngredient ingredient) {
        removeInGroup(ingredient, ATTACHMENT_MODIFICATION);
    }

    /**
     * Removes recipe that outputs matching item of given `ingredient` from bullet category.
     *
     * @param ingredient Output item of the recipe. recipe with matching output will be removed.
     */
    @MethodDescription(example = @Example("ore('oreDiamond')"))
    public void removeBullet(IIngredient ingredient) {
        removeInGroup(ingredient, BULLET);
    }

    /**
     * Removes recipe that outputs matching item of given `ingredient` from magazine category.
     *
     * @param ingredient Output item of the recipe. recipe with matching output will be removed.
     */
    @MethodDescription(example = @Example("ore('oreDiamond')"))
    public void removeMagazine(IIngredient ingredient) {
        removeInGroup(ingredient, MAGAZINE);
    }

    public void removeInGroup(IIngredient ingredient, CraftingGroup group) {
        removeInGroupWithFilter(ingredient, group);
    }

    public void removeInGroupWithFilter(Predicate<ItemStack> ingredient, CraftingGroup group) {
        List<IModernCraftingRecipe> recipesToRemove = new ArrayList<>();
        for (IModernCraftingRecipe recipe : craftingMap.get(group)) {
            if (ingredient.test(recipe.getItemStack())) {
                recipesToRemove.add(recipe);
            }
        }
        for (IModernCraftingRecipe recipe : recipesToRemove) {
            removeRecipe(recipe);
        }
    }

    public void addRecipe(IModernCraftingRecipe crafting) {
        registerRecipe(crafting);
        this.addScripted(crafting);
    }

    public void removeRecipe(IModernCraftingRecipe crafting) {
        deleteRecipeRegistry(crafting);
        this.addBackup(crafting);
    }

    /**
     * Start Recipe Builder for GroovyScript.
     */
    @RecipeBuilderDescription(example = {
            @Example(".input(item('minecraft:clay')).output(item('minecraft:diamond'))"),
            @Example(".setYield(0.5).input(item('minecraft:gold_ingot')).output(item('minecraft:clay') * 2)")
    })
    public RecipeBuilder recipeBuilder() {
        return new RecipeBuilder();
    }

    @Property(property = "yield", comp = @Comp(gte = 0))
    @Property(property = "group", comp = @Comp(not = "null"))
    public static class RecipeBuilder extends AbstractRecipeBuilder<GSCrafting> {

        @Override
        public String getErrorMsg() {
            return "Error adding " + NAME + " Workbench Recipe: " + name;
        }

        @Override
        public void validate(GroovyLog.Msg msg) {
            // max input == container size of crafting station.
            this.validateItems(msg, 1, 27, 1, 1);
            msg.add(this.group == null, "group must not be null!");
            msg.add(!validateYields(), "yields can not be less than 0!");
        }

        private boolean validateYields() {
            for (double value : this.yields) {
                if (value < 0) {
                    return false;
                }
            }
            return this.yields.size() == this.input.size();
        }

        private final ArrayList<Double> yields = new ArrayList<>();

        @Property(defaultValue = "1.0", comp = @Comp(gte = 0))
        private double yield = 1.0;

        @Property(defaultValue = "GUN", comp = @Comp(not = "null"))
        private CraftingGroup group = GUN;

        /**
         * Add Ingredient of recipe With ItemStack
         *
         * @param ingredient crafting ingredient
         * @return AbstractRecipeBuilder
         */
        @Override
        public AbstractRecipeBuilder<GSCrafting> input(IIngredient ingredient) {
            this.yields.add(this.yield);
            return super.input(ingredient);
        }

        /**
         * Set return Yield of upcoming Ingredient.
         *
         * @param yield return Yield of upcoming ingredients
         * @return this RecipeBuilder
         */
        @RecipeBuilderMethodDescription(field = "yield")
        public RecipeBuilder setYield(double yield) {
            this.yield = yield;
            return this;
        }

        /**
         * Set Category of Recipe
         *
         * @param group name of the Group. valid values = "GUN", "ATTACHMENT_NORMAL", "ATTACHMENT_MODIFICATION", "BULLET", "MAGAZINE"
         * @return this RecipeBuilder
         */
        @RecipeBuilderMethodDescription(field = "group", priority = 2000)
        public RecipeBuilder setGroup(String group) {
            if (Arrays.stream(CraftingGroup.values()).noneMatch(g -> g.name().equals(group))) {
                return this;
            }
            return setGroup(CraftingGroup.valueOf(group));
        }

        @RecipeBuilderMethodDescription(field = "group")
        public RecipeBuilder setGroupGun() {
            return setGroup(GUN);
        }

        @RecipeBuilderMethodDescription(field = "group")
        public RecipeBuilder setGroupNormalAttachment() {
            return setGroup(ATTACHMENT_NORMAL);
        }

        @RecipeBuilderMethodDescription(field = "group")
        public RecipeBuilder setGroupModificationAttachment() {
            return setGroup(ATTACHMENT_MODIFICATION);
        }

        @RecipeBuilderMethodDescription(field = "group")
        public RecipeBuilder setGroupBullet() {
            return setGroup(BULLET);
        }

        @RecipeBuilderMethodDescription(field = "group")
        public RecipeBuilder setGroupMagazine() {
            return setGroup(MAGAZINE);
        }

        @RecipeBuilderMethodDescription(field = "group", priority = 3000)
        public RecipeBuilder setGroup(CraftingGroup group) {
            this.group = group;
            return this;
        }

        /**
         * Finalize the recipe and register it
         *
         * @return Finished Recipe
         */
        @Override
        @RecipeBuilderRegistrationMethod
        public GSCrafting register() {
            if (!this.validate()) {
                return null;
            }

            final ArrayList<CraftingEntry> entries = new ArrayList<>();

            for (int i = 0; i < this.input.size(); i++) {
                final IIngredient ingredient = this.input.get(i);
                entries.add(new CraftingEntry(ingredient.toMcIngredient(), ingredient.getAmount(), this.yields.get(i)));
            }

            final GSCrafting recipe = new GSCrafting(this.output.get(0), this.group, entries.toArray(new CraftingEntry[0]));
            craftingStation.addRecipe(recipe);
            return recipe;
        }
    }
}
