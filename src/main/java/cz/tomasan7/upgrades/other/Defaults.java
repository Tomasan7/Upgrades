package cz.tomasan7.upgrades.other;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Defaults
{
	public static void load (@NotNull ConfigurationSection config)
	{
		Menu.load(config.getConfigurationSection(ConfigKeys.Defaults.MENU));
		MenuItem.load(config.getConfigurationSection(ConfigKeys.Defaults.MENU_ITEM));
		MenuElement.load(config.getConfigurationSection(ConfigKeys.Defaults.MENU_ELEMENT));
		SubMenuElement.load(config.getConfigurationSection(ConfigKeys.Defaults.SUB_MENU_ELEMENT));
	}

	public static class Menu
	{
		private static final String TITLE = "Upgrades";
		private static final int ROWS = 6;
		private static final Material FILL = Material.AIR;

		private static String title;
		private static int rows;
		private static Material fill;

		public static void load (ConfigurationSection config)
		{
			title = config != null ? config.getString(ConfigKeys.Menu.TITLE, TITLE) : TITLE;
			rows = config != null ? config.getInt(ConfigKeys.Menu.ROWS, ROWS) : ROWS;
			fill = config != null ? Material.matchMaterial(config.getString(ConfigKeys.Menu.FILL, FILL.toString())) : FILL;
		}

		@NotNull
		public static String getTitle ()
		{
			return title;
		}

		public static int getRows ()
		{
			return rows;
		}

		@NotNull
		public static Material getFill ()
		{
			return fill;
		}
	}

	public static class MenuItem
	{
		private static final Material MATERIAL = Material.BLACK_STAINED_GLASS_PANE;
		private static final String DISPLAY_NAME = " ";
		private static final List<String> LORE = new ArrayList<>();
		private static final int AMOUNT = 1;
		private static final int SLOT = 0;

		private static Material material;
		private static String displayName;
		private static List<String> lore;
		private static int amount;
		private static int slot;

		public static void load (ConfigurationSection config)
		{
			material = config != null ? Material.matchMaterial(config.getString(ConfigKeys.Menu.MenuElement.MenuItem.MATERIAL, MATERIAL.toString())) : MATERIAL;
			displayName = config != null ? config.getString(ConfigKeys.Menu.MenuElement.MenuItem.DISPLAY_NAME, DISPLAY_NAME) : DISPLAY_NAME;
			lore = config != null ? ((List<String>) config.getList(ConfigKeys.Menu.MenuElement.MenuItem.LORE, LORE)) : LORE;
			amount = config != null ? config.getInt(ConfigKeys.Menu.MenuElement.MenuItem.AMOUNT, AMOUNT) : AMOUNT;
			slot = config != null ? config.getInt(ConfigKeys.Menu.MenuElement.MenuItem.SLOT, SLOT) : SLOT;
		}

		@NotNull
		public static Material getMaterial ()
		{
			return material;
		}

		@NotNull
		public static String getDisplayName ()
		{
			return displayName;
		}

		@NotNull
		public static List<String> getLore ()
		{
			return lore;
		}

		public static int getAmount ()
		{
			return amount;
		}

		public static int getSlot ()
		{
			return slot;
		}
	}

	public static class MenuElement
	{
		private static final cz.tomasan7.upgrades.menus.menuElements.MenuElement.MenuElementType TYPE = cz.tomasan7.upgrades.menus.menuElements.MenuElement.MenuElementType.NORMAL;

		private static cz.tomasan7.upgrades.menus.menuElements.MenuElement.MenuElementType type;

		public static void load (ConfigurationSection config)
		{
			type = config != null ? cz.tomasan7.upgrades.menus.menuElements.MenuElement.MenuElementType.getByConfigValue(config.getString(ConfigKeys.Menu.MenuElement.TYPE, TYPE.toString())) : TYPE;
		}

		@NotNull
		public static cz.tomasan7.upgrades.menus.menuElements.MenuElement.MenuElementType getType ()
		{
			return type;
		}
	}

	public static class SubMenuElement
	{
		private static final Map<String, String> MUST_HAVE_PERMS = new HashMap<>();
		private static final double PRICE = 99999;

		private static double price;
		private static Map<String, String> mustHavePerms;

		public static void load (ConfigurationSection config)
		{
			price = config != null ? config.getDouble(ConfigKeys.SubMenu.SubMenuElement.PRICE, PRICE) : PRICE;
			mustHavePerms = new HashMap<>();

			ConfigurationSection mustHavePermsSection = config != null ? config.getConfigurationSection(ConfigKeys.SubMenu.SubMenuElement.MUST_HAVE_PERMS) : null;

			if (mustHavePermsSection == null)
			{
				mustHavePerms = MUST_HAVE_PERMS;
				return;
			}

			for (String mustHavePerm : mustHavePermsSection.getKeys(false))
				mustHavePerms.put(Utils.formatText(mustHavePerm), mustHavePermsSection.getString(mustHavePerm));
		}

		@NotNull
		public static Map<String, String> getMustHavePerms ()
		{
			return mustHavePerms;
		}

		public static double getPrice ()
		{
			return price;
		}
	}
}
