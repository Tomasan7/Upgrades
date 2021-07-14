package cz.tomasan7.upgrades.menus;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

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
}
