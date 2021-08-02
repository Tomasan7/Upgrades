package cz.tomasan7.upgrades.menus;

import cz.tomasan7.upgrades.Main;
import cz.tomasan7.upgrades.menus.menuElements.BlankElement;
import cz.tomasan7.upgrades.menus.menuElements.MainMenuElement;
import cz.tomasan7.upgrades.menus.menuElements.MenuElement;
import cz.tomasan7.upgrades.other.Config;
import cz.tomasan7.upgrades.other.ConfigKeys;
import cz.tomasan7.upgrades.other.Constants;
import cz.tomasan7.upgrades.other.Defaults;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class MainMenu extends Menu
{
	private static final Map<String, FileConfiguration> subMenusConfigs = new HashMap<>();

	public MainMenu (ConfigurationSection config, Player player)
	{
		super(config, player);
	}

	@Override
	@NotNull
	public MenuElement parseMenuElement (ConfigurationSection config)
	{
		MenuElement.MenuElementType type = MenuElement.MenuElementType.getByConfigValue(config.getString(ConfigKeys.Menu.MenuElement.TYPE, Defaults.MenuElement.getType().toString()).toUpperCase());

		return type == MenuElement.MenuElementType.BLANK ? new BlankElement(this, config, player) : new MainMenuElement(this, config, player);
	}

	/**
	 * Loads configs of all submenus <b>used in MainMenu</b>. If there isn't a config for subMenu yet, it creates the default one.
	 */
	public static void loadSubMenusConfigs ()
	{
		subMenusConfigs.clear();

		Main main = Main.getInstance();

		ConfigurationSection mainMenuElementsSection = Config.getMainMenu().getConfigurationSection(ConfigKeys.Menu.MENU_ELEMENTS);

		File subMenusFolder = new File(main.getDataFolder().getPath() + File.separator + Constants.SUB_MENUS_FOLDER_NAME);

		if (!subMenusFolder.exists())
			subMenusFolder.mkdir();

		for (String mainMenuElement : mainMenuElementsSection.getKeys(false))
		{
			String subMenuName = mainMenuElementsSection.getString(mainMenuElement + "." + ConfigKeys.MainMenu.MainMenuElement.SUB_MENU);

			if (subMenuName == null)
				continue;

			File subMenuFile = new File(subMenusFolder, subMenuName + ".yml");

			if (!subMenuFile.exists())
			{
				InputStream defaultSubMenuStream = MainMenu.class.getClassLoader().getResourceAsStream(Constants.DEFAULT_SUB_MENU_NAME + ".yml");
				try
				{
					Files.copy(defaultSubMenuStream, new File(subMenusFolder, subMenuName + ".yml").toPath(), StandardCopyOption.REPLACE_EXISTING);
				}
				catch (IOException ignored) {}
			}

			subMenusConfigs.put(subMenuName, YamlConfiguration.loadConfiguration(subMenuFile));
		}
	}

	public SubMenu newSubMenu (String name)
	{
		FileConfiguration subMenuConfig = subMenusConfigs.get(name);

		if (subMenuConfig == null)
			throw new IllegalArgumentException("This subMenu does not exist.");

		return new SubMenu(this, name, subMenuConfig, player);
	}
}
