package cz.tomasan7.upgrades.menus.menuElements;

import cz.tomasan7.upgrades.Upgrades;
import cz.tomasan7.upgrades.menus.MainMenu;
import cz.tomasan7.upgrades.menus.Menu;
import cz.tomasan7.upgrades.menus.SubMenu;
import cz.tomasan7.upgrades.other.Config;
import cz.tomasan7.upgrades.other.Constants;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ReturnToMainMenuElement implements MenuElement
{
	public static final MenuElementType TYPE = MenuElementType.RETURN_TO_MAIN_MENU;

	private final SubMenu owningMenu;
	private final MenuItem menuItem;
	private final Player player;

	public ReturnToMainMenuElement (SubMenu owningMenu, ConfigurationSection config, Player player)
	{
		this.owningMenu = owningMenu;
		this.menuItem = new MenuItem(config);
		this.player = player;
	}

	@Override
	public void onClick (InventoryClickEvent event)
	{
		Player player = ((Player) event.getWhoClicked());

		Constants.CLICK_SOUND.play(player);
		owningMenu.getOwningMainMenu().open(player);
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
