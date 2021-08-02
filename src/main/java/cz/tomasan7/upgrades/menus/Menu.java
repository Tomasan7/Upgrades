package cz.tomasan7.upgrades.menus;

import cz.tomasan7.upgrades.menus.menuElements.MenuElement;
import cz.tomasan7.upgrades.menus.menuElements.MenuItem;
import cz.tomasan7.upgrades.other.ConfigKeys;
import cz.tomasan7.upgrades.other.Constants;
import cz.tomasan7.upgrades.other.Defaults;
import cz.tomasan7.upgrades.other.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class Menu implements InventoryHolder
{
	protected final String title;
	protected final int rows;
	protected final Material fill;
	protected final Set<MenuElement> elements;
	protected final Player player;

	protected final Inventory inventory;

	public Menu (ConfigurationSection config, Player player)
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

		ConfigurationSection itemsSection = config.getConfigurationSection(ConfigKeys.Menu.MENU_ELEMENTS);

		for (String itemKey : itemsSection.getKeys(false))
		{
			MenuElement element = parseMenuElement(itemsSection.getConfigurationSection(itemKey));
			elements.add(element);
			inventory.setItem(element.getMenuItem().getSlot(), element.getMenuItem().getItemStack());
		}
	}

	public Menu (ConfigurationSection config)
	{
		this(config, null);
	}

	public void open (HumanEntity player)
	{
		player.openInventory(getInventory());
	}

	@NotNull
	protected  abstract MenuElement parseMenuElement (ConfigurationSection config);

	@NotNull
	public String getTitle ()
	{
		return title;
	}

	public int getRows ()
	{
		return rows;
	}

	@NotNull
	public Material getFill ()
	{
		return fill;
	}

	@NotNull
	public Set<MenuElement> getElements ()
	{
		return elements;
	}

	@NotNull
	@Override
	public Inventory getInventory ()
	{
		return inventory;
	}

	@Nullable
	public Player getPlayer ()
	{
		return player;
	}
}
