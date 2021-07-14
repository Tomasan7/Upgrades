package cz.tomasan7.upgrades.menus;

import cz.tomasan7.upgrades.other.Utils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BlankElement implements MenuElement
{
	public static final String SPECIAL_NAME = "blank";

	private final SubMenu owningMenu;

	private final ItemStack itemStack;
	private final int slot;

	public BlankElement (SubMenu owningMenu, ConfigurationSection config)
	{
		this.owningMenu = owningMenu;
		this.slot = config.getInt(SLOT_KEY);
		this.itemStack = createItemStack(config);
	}

	private ItemStack createItemStack (ConfigurationSection config)
	{
		Material material = Material.matchMaterial(config.getString(MATERIAL_KEY));
		int amount = config.getInt(AMOUNT_KEY);
		String displayName = Utils.formatText(config.getString(DISPLAY_NAME_KEY));
		List<String> lore = config.getStringList(LORE_KEY).stream()
				.map(Utils::formatText)
				.toList();

		return Utils.createItemStack(material, displayName, lore, amount);
	}

	@Override
	public void onClick (InventoryClickEvent event)
	{
	}

	@Override
	public int getSlot ()
	{
		return slot;
	}

	@Override
	@NotNull
	public ItemStack getItemStack ()
	{
		return itemStack;
	}

	@Override
	@NotNull
	public Menu getOwningMenu ()
	{
		return owningMenu;
	}
}
