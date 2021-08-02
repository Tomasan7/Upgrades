package cz.tomasan7.upgrades.menus;

import cz.tomasan7.upgrades.menus.menuElements.*;
import cz.tomasan7.upgrades.other.ConfigKeys;
import cz.tomasan7.upgrades.Main;
import cz.tomasan7.upgrades.other.Defaults;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class SubMenu extends Menu
{
	private final String name;
	private final MainMenu owningMainMenu;

	public SubMenu (MainMenu owningMainMenu, String name, ConfigurationSection config, Player player)
	{
		super(config, player);
		this.owningMainMenu = owningMainMenu;
		this.name = name;
	}

	@Override
	@NotNull
	public MenuElement parseMenuElement (ConfigurationSection config)
	{
		MenuElement.MenuElementType type = MenuElement.MenuElementType.getByConfigValue(config.getString(ConfigKeys.Menu.MenuElement.TYPE, Defaults.MenuElement.getType().toString()).toUpperCase());

		return switch (type)
				{
					default -> new SubMenuElement(this, config, player);
					case RETURN_TO_MAIN_MENU -> new ReturnToMainMenuElement(this, config, player);
					case BLANK -> new BlankElement(this, config, player);
				};
	}

	@NotNull
	public String getName ()
	{
		return name;
	}

	public MainMenu getOwningMainMenu ()
	{
		return owningMainMenu;
	}
}
