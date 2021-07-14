package cz.tomasan7.upgrades.menus;

import cz.tomasan7.upgrades.other.Constants;
import cz.tomasan7.upgrades.Main;
import cz.tomasan7.upgrades.other.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

public class SubMenu implements Menu
{
	private final String name;
	private final String title;
	private final int rows;
	private final Set<MenuElement> elements;
	private final Player player;

	private final Inventory inventory;

	public SubMenu (String name, ConfigurationSection config, Player player)
	{
		this.name = name;
		this.player = player;
		title = Utils.formatText(config.getString("title"));
		rows = config.getInt("rows");
		elements = new HashSet<>();
		inventory = Bukkit.createInventory(this, rows * Constants.ROW_SIZE, title);

		ConfigurationSection itemsSection = config.getConfigurationSection("items");

		for (String itemKey : itemsSection.getKeys(false))
		{
			MenuElement element = createElement(itemsSection.getConfigurationSection(itemKey));
			elements.add(element);
			inventory.setItem(element.getSlot(), element.getItemStack());
		}
	}

	private MenuElement createElement (ConfigurationSection config)
	{
		if (!config.isString(MenuElement.SPECIAL_KEY))
			return new SubMenuElement(this, config, player);

		String typeString = config.getString(MenuElement.SPECIAL_KEY);

		return switch (typeString)
				{
					case ReturnToMainMenuElement.SPECIAL_NAME -> new ReturnToMainMenuElement(this, config);
					case BalanceElement.SPECIAL_NAME -> new BalanceElement(this, config, player);
					case BlankElement.SPECIAL_NAME -> new BlankElement(this, config);
					default -> null;
				};
	}

	@Override
	public @NotNull String getTitle ()
	{
		return title;
	}

	@Override
	public int getRows ()
	{
		return rows;
	}

	public Set<MenuElement> getElements ()
	{
		return new HashSet<>(elements);
	}

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

	public String getName ()
	{
		return name;
	}

	@NotNull
	@Override
	public Inventory getInventory ()
	{
		return inventory;
	}
}
