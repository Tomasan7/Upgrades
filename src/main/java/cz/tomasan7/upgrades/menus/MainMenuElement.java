package cz.tomasan7.upgrades.menus;

import cz.tomasan7.upgrades.other.Utils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class MainMenuElement
{
	private final ItemStack item;
	private final int slot;
	private final String subMenu;

	public MainMenuElement (ConfigurationSection config)
	{
		Material material = Material.matchMaterial(config.getString("material"));
		String displayName = Utils.formatText(config.getString("display-name"));
		List<String> lore = config.getStringList("lore").stream().map(Utils::formatText).toList();
		int amount = config.getInt("amount");
		slot = config.getInt("slot");
		subMenu = config.getString("sub-menu");

		ItemStack item = new ItemStack(material, amount);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemMeta.setDisplayName(displayName);
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);
		this.item = item;
	}

	public ItemStack getItemStack ()
	{
		return item.clone();
	}

	public int getSlot ()
	{
		return slot;
	}

	public String getSubMenu ()
	{
		return subMenu;
	}
}