package net.satisfyu.meadow.fabric.rei.category;

import com.google.common.collect.Lists;
import net.satisfyu.meadow.Meadow;
import net.satisfyu.meadow.block.cookingPot.CookingPotBlockEntity;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.text.Text;
import net.satisfyu.meadow.fabric.rei.display.CookingPotDisplay;
import net.satisfyu.meadow.registry.ObjectRegistry;

import java.util.List;

public class CookingPotCategory implements DisplayCategory<CookingPotDisplay> {


    public static final CategoryIdentifier<CookingPotDisplay> COOKING_POT_DISPLAY = CategoryIdentifier.of(Meadow.MOD_ID, "cooking_pot_display");

    @Override
    public CategoryIdentifier<CookingPotDisplay> getCategoryIdentifier() {
        return COOKING_POT_DISPLAY;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("rei.meadow.cooking_pot_category");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ObjectRegistry.COOKING_POT.get());
    }

    @Override
    public int getDisplayWidth(CookingPotDisplay display) {
        return 150;
    }

    @Override
    public int getDisplayHeight() {
        return 99;
    }

    @Override
    public List<Widget> setupDisplay(CookingPotDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 55, bounds.getCenterY() - 13);
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createArrow(new Point(startPoint.x + 54, startPoint.y - 1)).animationDurationTicks(CookingPotBlockEntity.MAX_COOKING_TIME));
        widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 90, startPoint.y)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 90, startPoint.y)).entries(display.getOutputEntries().get(0)).disableBackground().markOutput());
        for(int i = 0; i < 6; i++){
            int x = i * 18;
            int y = -4;
            if(i > 2){
                x = (i - 3) * 18;
                y+=18;
            }
            x-=8;
            if(i >= display.getInputEntries().size() - 1) widgets.add(Widgets.createSlotBackground(new Point(startPoint.x + x, startPoint.y + y)));
            else widgets.add(Widgets.createSlot(new Point(startPoint.x + x, startPoint.y + y)).entries(display.getInputEntries().get(i + 1)).markInput());
        }
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 56, startPoint.y + 23)).entries(display.getInputEntries().get(0)).markInput());
        return widgets;
    }
}
