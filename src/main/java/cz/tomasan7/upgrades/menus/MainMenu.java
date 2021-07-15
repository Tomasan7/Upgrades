package cz.tomasan7.upgrades.menus;

import cz.tomasan7.upgrades.menus.menuElements.*;
import cz.tomasan7.upgrades.other.ConfigKeys;
import cz.tomasan7.upgrades.other.Constants;
import cz.tomasan7.upgrades.other.Defaults;
import cz.tomasan7.upgrades.other.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainMenu implements Menu
{
	private final String title;
	private final int rows;
	private final Material fill;
	private final Set<MenuElement> elements;
	private final Player player;

	private final Inventory inventory;

	public MainMenu (ConfigurationSection config, Player player)
	{
		this.player = player;
		title = Utils.formatText(config.getString(ConfigKeys.Menu.TITLE, Defaults.Menu.getTitle()), player);
		rows = config.getInt(ConfigKeys.Menu.ROWS, Defaults.Menu.getRows());
		fill = Material.matchMaterial(config.getString(ConfigKeys.Menu.FILL, Defaults.Menu.getFill().toString()));
		elements = new HashSet<>();
		inventory = Bukkit.createInventory(this, rows * Constants.ROW_SIZE, title);

		if (fill != null)
		{
			for (int slot = 0; slot < inventory.getSize(); slot++)
				inventory.setItem(slot, new MenuItem(fill, " ", new ArrayList<>(), 1, slot).getItemStack());
		}

		ConfigurationSection itemsSection = config.getConfigurationSection(ConfigKeys.Menu.MENU_ITEMS);

		for (String itemKey : itemsSection.getKeys(false))
		{
			MenuElement element = parseMenuElement(itemsSection.getConfigurationSection(itemKey));
			elements.add(element);
			inventory.setItem(element.getMenuItem().getSlot(), element.getMenuItem().getItemStack());
		}
	}

	@Override
	@NotNull
	public MenuElement parseMenuElement (ConfigurationSection config)
	{
		MenuElement.MenuElementType type = MenuElement.MenuElementType.getByConfigValue(config.getString(ConfigKeys.Menu.MenuElement.TYPE, Defaults.MenuElement.getType().toString()).toUpperCase());

		return type == MenuElement.MenuElementType.BLANK ? new BlankElement(this, config, player) : new MainMenuElement(this, config, player);
	}

	@Override
	@NotNull
	public String getTitle ()
	{
		return title;
	}

	@Override
	public int getRows ()
	{
		return rows;
	}

	@Override
	@Nullable
	public Material getFill ()
	{
		return fill;
	}

	@Override
	@NotNull
	public Set<MenuElement> getElements ()
	{
		return new HashSet<>(elements);
	}

	@Nullable
	public Player getPlayer ()
	{
		return player;
	}

	@Override
	@NotNull
	public Inventory getInventory ()
	{
		return inventory;
	}
}
