package cz.tomasan7.upgrades.menus;

import cz.tomasan7.upgrades.Constants;
import cz.tomasan7.upgrades.other.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class MainMenu implements InventoryHolder
{
	private final String title;
	private final int rows;
	private Set<MainMenuElement> elements;

	private final Inventory inventory;

	public MainMenu (ConfigurationSection config)
	{
		title = Utils.formatText(config.getString("title"));
		rows = config.getInt("rows");
		elements = new HashSet<>();
		inventory = Bukkit.createInventory(this, rows * Constants.ROW_SIZE, title);

		ConfigurationSection itemsSection = config.getConfigurationSection("items");

		for (String itemKey : itemsSection.getKeys(false))
		{
			MainMenuElement element = new MainMenuElement(itemsSection.getConfigurationSection(itemKey));
			elements.add(element);
			inventory.setItem(element.getSlot(), element.getItemStack());
		}
	}

	@NotNull
	@Override
	public Inventory getInventory ()
	{
		return inventory;
	}
}
