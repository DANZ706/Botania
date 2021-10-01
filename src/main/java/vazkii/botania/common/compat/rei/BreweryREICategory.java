/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.common.compat.rei;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.NotNull;

import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.lib.ResourceLocationHelper;

import java.util.ArrayList;
import java.util.List;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;

@Environment(EnvType.CLIENT)
public class BreweryREICategory implements DisplayCategory<BreweryREIDisplay> {
	private final EntryStack<ItemStack> brewery = EntryStacks.of(new ItemStack(ModBlocks.brewery));
	private final ResourceLocation BREWERY_OVERLAY = ResourceLocationHelper.prefix("textures/gui/nei_brewery.png");

	@Override
	public @NotNull CategoryIdentifier<BreweryREIDisplay> getCategoryIdentifier() {
		return BotaniaREICategoryIdentifiers.BREWERY;
	}

	@Override
	public @NotNull Renderer getIcon() {
		return this.brewery;
	}

	@Override
	public @NotNull Component getTitle() {
		return new TranslatableComponent("botania.nei.brewery");
	}

	@Override
	public @NotNull List<Widget> setupDisplay(BreweryREIDisplay display, Rectangle bounds) {
		List<Widget> widgets = new ArrayList<>();
		List<EntryIngredient> inputs = display.getInputEntries();
		Point center = new Point(bounds.getCenterX() - 8, bounds.getCenterY() - 4);

		widgets.add(CategoryUtils.drawRecipeBackground(bounds));
		widgets.add(Widgets.createDrawableWidget(((helper, matrices, mouseX, mouseY, delta) -> CategoryUtils.drawOverlay(helper, matrices, BREWERY_OVERLAY, center.x - 35, center.y - 20, 28, 6, 86, 55))));

		widgets.add(Widgets.createSlot(new Point(center.x - 24, center.y + 16)).entries(display.getContainers()));
		int posX = center.x - (inputs.size() - 1) * 9;
		for (EntryIngredient o : inputs) {
			widgets.add(Widgets.createSlot(new Point(posX, center.y - 22)).entries(o).disableBackground());
			posX += 18;
		}
		widgets.add(Widgets.createSlot(new Point(center.x + 24, center.y + 16)).entries(display.getOutputEntries().get(0)));

		return widgets;
	}

	@Override
	public int getDisplayHeight() {
		return 72;
	}
}