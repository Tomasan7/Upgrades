package cz.tomasan7.upgrades.events;

import cz.tomasan7.upgrades.menus.Menu;
import cz.tomasan7.upgrades.menus.menuElements.MenuElement;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class MenuHandler implements Listener
{
	@EventHandler
	public void onItemClick (InventoryClickEvent event)
	{
		ItemStack clickedItem = event.getCurrentItem();

		if (clickedItem == null || clickedItem.getType() == Material.AIR)
			return;

		Inventory clickedInventory = event.getClickedInventory();

		if (clickedInventory == null || clickedInventory.getHolder() == null)
			return;

		InventoryHolder holder = clickedInventory.getHolder();

		if (holder instanceof Menu menu)
		{
			event.setCancelled(true);

			Optional<MenuElement> clickedElement = menu.getElements().stream()
					.filter(element -> element.getMenuItem().getItemStack().equals(clickedItem))
					.findAny();

			clickedElement.ifPresent(menuElement -> menuElement.onClick(event));
		}
	}
}