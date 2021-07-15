package cz.tomasan7.upgrades.menus;

import cz.tomasan7.upgrades.menus.menuElements.MenuElement;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public interface Menu extends InventoryHolder
{
	@NotNull
	String getTitle();

	int getRows();

	@NotNull
	Set<MenuElement> getElements();

	@NotNull
	Inventory getInventory();

	default void open (HumanEntity player)
	{
		player.openInventory(getInventory());
	}

	@Nullable
	Material getFill ();

	@NotNull
	MenuElement parseMenuElement (ConfigurationSection config);
}
