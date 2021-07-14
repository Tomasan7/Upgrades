package cz.tomasan7.upgrades.menus;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface MenuElement
{
	String MATERIAL_KEY = "material";
	String DISPLAY_NAME_KEY = "display-name";
	String LORE_KEY = "lore";
	String AMOUNT_KEY = "amount";
	String PERMISSIONS_KEY = "permissions";
	String PERM_KEY = "perm";
	String PRICE_KEY = "price";
	String SLOT_KEY = "slot";
	String SPECIAL_KEY = "special";

	void onClick (InventoryClickEvent event);

	int getSlot();

	@NotNull
	ItemStack getItemStack ();

	@NotNull
	Menu getOwningMenu ();
}
