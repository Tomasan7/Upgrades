package cz.tomasan7.upgrades.menus;

import cz.tomasan7.upgrades.menus.menuElements.*;
import cz.tomasan7.upgrades.other.ConfigKeys;
import cz.tomasan7.upgrades.other.Constants;
import cz.tomasan7.upgrades.Main;
import cz.tomasan7.upgrades.other.Defaults;
import cz.tomasan7.upgrades.other.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SubMenu extends Menu
{
	private final String name;

	public SubMenu (String name, ConfigurationSection config, Player player)
	{
		super(config, player);
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
	public static SubMenu newSubMenu (String name, Player player)
	{
		Plugin plugin = Main.getInstance();
		File file;
		FileConfiguration fileConfiguration;

		File subFolder = new File(plugin.getDataFolder().getPath() + System.getProperty("file.separator") + "SubMenus");
		if (!subFolder.exists())
			subFolder.mkdir();

		file = new File(plugin.getDataFolder().getPath() + "/SubMenus", name + ".yml");

		if (!file.exists())
		{
			try
			{
				file = new File(plugin.getDataFolder().getPath() + "/SubMenus", name + ".yml");
				InputStream link = (Main.getInstance().getClass().getResourceAsStream("/defaultSubMenu.yml"));
				Files.copy(link, file.getAbsoluteFile().toPath());
				file.createNewFile();
			}
			catch (IOException ignored) {}
		}

		fileConfiguration = YamlConfiguration.loadConfiguration(file);

		return new SubMenu(name, fileConfiguration, player);
	}

	@NotNull
	public String getName ()
	{
		return name;
	}
}
