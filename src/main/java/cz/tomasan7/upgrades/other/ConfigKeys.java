package cz.tomasan7.upgrades.other;

public class ConfigKeys
{
	public static final String GLOBAL_CONTEXTS = "global-contexts";
	public static final String DEFAULTS = "defaults";
	public static final String ENCHANT_ON_HAVE = "enchant-on-have";
	public static final String COLOR_ON_HAVE = "color-on-have";
	public static final String HAVE_COLOR = "have-color";
	public static final String NOT_HAVE_COLOR = "not-have-color";
	public static final String MESSAGES = "messages";
	public static final String MAIN_MENU = "main-menu";

	public static class Defaults
	{
		public static String MENU = "menu";
		public static String MENU_ITEM = "menu-item";
		public static String MENU_ELEMENT = "menu-element";
		public static String SUB_MENU_ELEMENT = "sub-menu-element";
	}

	public static class Menu
	{
		public static final String TITLE = "title";
		public static final String ROWS = "rows";
		public static final String FILL = "fill";
		public static final String MENU_ELEMENTS = "items";

		public static class MenuElement
		{
			public static final String TYPE = "type";

			public static class MenuItem
			{
				public static final String MATERIAL = "material";
				public static final String DISPLAY_NAME = "display-name";
				public static final String LORE = "lore";
				public static final String AMOUNT = "amount";
				public static final String SLOT = "slot";
			}
		}
	}

	public static class MainMenu
	{
		public static class MainMenuElement
		{
			public static final String SUB_MENU = "sub-menu";
		}
	}

	public static class SubMenu
	{
		public static class SubMenuElement
		{
			public static final String TYPE = "type";
			public static final String PRICE = "price";
			public static final String PERMISSIONS = "permissions";
			public static final String MUST_HAVE_PERMS = "must-have-perms";

			public static class Permissions
			{
				String PERM = "perm";
			}
		}
	}

	public static class Messages
	{
		public static final String PLUGIN_PREFIX = "plugin-prefix";
		public static final String NO_PERMISSION = "no-permission";
		public static final String SUCCESSFUL_BUY = "successful-buy";
		public static final String ALREADY_HAVE = "already-have";
		public static final String NO_MONEY = "no-money";
		public static final String DONT_HAVE_MUST_HAVE_PERM = "dont-have-must-have-perm";
	}
}
