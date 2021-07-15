package cz.tomasan7.upgrades.menus.menuElements;

import cz.tomasan7.upgrades.menus.Menu;
import cz.tomasan7.upgrades.menus.SubMenu;
import cz.tomasan7.upgrades.other.Utils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlankElement implements MenuElement
{
	private static final MenuElementType TYPE = MenuElementType.BLANK;

	private final Menu owningMenu;
	private final MenuItem menuItem;
	private final Player player;

	public BlankElement (Menu owningMenu, ConfigurationSection config, Player player)
	{
		this.owningMenu = owningMenu;
		this.menuItem = new MenuItem(config, player);
		this.player = player;
	}

	@Override
	public void onClick (InventoryClickEvent event)
	{
	}

	@Override
	@NotNull
	public MenuElementType getType ()
	{
		return TYPE;
	}

	@Override
	@NotNull
	public Menu getOwningMenu ()
	{
		return owningMenu;
	}

	@Override
	@NotNull
	public MenuItem getMenuItem ()
	{
		return menuItem;
	}

	@Override
	@Nullable
	public Player getPlayer ()
	{
		return player;
	}
}
