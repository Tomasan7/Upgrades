package cz.tomasan7.upgrades.menus.menuElements;

import cz.tomasan7.upgrades.menus.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public interface MenuElement
{
	void onClick (InventoryClickEvent event);

	@NotNull
	MenuItem getMenuItem ();

	@NotNull Menu getOwningMenu ();

	@NotNull
	MenuElementType getType ();

	@Nullable
	Player getPlayer ();

	enum MenuElementType
	{
		NORMAL("normal"),
		RETURN_TO_MAIN_MENU("return"),
		BLANK("blank");

		private final String configValue;

		MenuElementType (String configValue)
		{
			this.configValue = configValue;
		}

		@Nullable
		public static MenuElementType getByConfigValue (String configValue)
		{
			return Arrays.stream(values()).filter(type -> type.getConfigValue().equalsIgnoreCase(configValue)).findAny().orElse(NORMAL);
		}

		public String getConfigValue ()
		{
			return configValue;
		}
	}
}
