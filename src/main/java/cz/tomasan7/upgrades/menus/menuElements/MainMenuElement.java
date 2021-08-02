package cz.tomasan7.upgrades.menus.menuElements;

import cz.tomasan7.upgrades.menus.MainMenu;
import cz.tomasan7.upgrades.menus.Menu;
import cz.tomasan7.upgrades.menus.SubMenu;
import cz.tomasan7.upgrades.other.ConfigKeys;
import cz.tomasan7.upgrades.other.Constants;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MainMenuElement implements MenuElement
{
	public static final MenuElementType TYPE = MenuElementType.NORMAL;

	private final MainMenu owningMenu;
	private final MenuItem menuItem;
	private final Player player;

	private final String subMenu;

	public MainMenuElement (MainMenu owningMenu, ConfigurationSection config, Player player)
	{
		this.owningMenu = owningMenu;
		this.menuItem = new MenuItem(config, player);
		this.player = player;

		subMenu = config.getString(ConfigKeys.MainMenu.MainMenuElement.SUB_MENU);
	}

	@Override
	public void onClick (InventoryClickEvent event)
	{
		Player player = ((Player) event.getWhoClicked());

		Constants.CLICK_SOUND.play(player);
		player.openInventory(owningMenu.newSubMenu(subMenu).getInventory());
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

	@NotNull
	public String getSubMenu ()
	{
		return subMenu;
	}
}