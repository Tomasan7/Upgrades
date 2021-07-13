package cz.tomasan7.upgrades.events;

import cz.tomasan7.upgrades.Main;
import cz.tomasan7.upgrades.Upgrades;
import cz.tomasan7.upgrades.menus.MainMenu;
import cz.tomasan7.upgrades.menus.MainMenuElement;
import cz.tomasan7.upgrades.menus.SubMenu;
import cz.tomasan7.upgrades.menus.SubMenuElement;
import cz.tomasan7.upgrades.other.Messages;
import cz.tomasan7.upgrades.other.PermissionManager;
import cz.tomasan7.upgrades.other.Utils;
import net.luckperms.api.node.types.PermissionNode;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class MenuHandler implements Listener
{
	private static FileConfiguration config;

	@EventHandler
	public void onItemClick (InventoryClickEvent event)
	{
	    ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem == null || clickedItem.getType() == Material.AIR)
            return;

        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory == null || clickedInventory.getHolder() == null)
            return;

        InventoryHolder holder = clickedInventory.getHolder();

        if (holder instanceof MainMenu menu)
        {
            MainMenu_menu((Player) event.getWhoClicked(), clickedItem, menu);
            event.setCancelled(true);
        }
        else if (holder instanceof SubMenu menu)
        {
            SubMenu_menu((Player) event.getWhoClicked(), clickedItem, menu);
            event.setCancelled(true);
        }

		/*if (event.getCurrentItem() != null)     // Check if player is holding something.
		{
			config = Main.getPlugin(Main.class).getConfig();
			Player player = (Player) event.getWhoClicked();
			String title = event.getView().getTitle();
			ItemStack clickedItem = event.getCurrentItem();
			boolean isMenu = false;

			if (title.equals(Utils.formatText(config.getString("MainMenu.title"))))
			{
				isMenu = true;
				MainMenu_menu(player, clickedItem);
			}
			else
			{
				for (SubMenu subMenu : MainMenu.subMenus)
				{
					if (Utils.formatText(subMenu.getConfig().getString("title")).equals(title))
					{
						isMenu = true;
						SubMenu_menu(player, clickedItem, subMenu);
						break;
					}
				}
			}

			if (isMenu)
			{
				event.setCancelled(true);
			}
		}*/
	}

	private void MainMenu_menu (Player player, ItemStack clickedItem, MainMenu menu)
	{
		String subMenuName = "";

		for (MainMenuElement element : MainMenu.elements)
		{
			if (element.item.equals(clickedItem))
			{
				player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.25F, 1F);
				subMenuName = element.subMenu;
				break;
			}
		}

		for (SubMenu subMenu : MainMenu.subMenus)
		{
			if (subMenu.name.equals(subMenuName))
			{
				player.openInventory(subMenu.getInventory(player));
				break;
			}
		}
	}

	private void SubMenu_menu (Player player, ItemStack clickedItem, SubMenu subMenu)
	{
		Economy economy = Upgrades.getEconomy();

		for (SubMenuElement element : subMenu.getElements(player))
		{
			if (!element.itemStack.equals(clickedItem))
				continue;

			player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.25F, 1F);

			boolean doesntHaveAll = false;

			for (PermissionNode permissionNode : element.permissions)
			{
				if (!PermissionManager.checkPermission(player, permissionNode, true))
					doesntHaveAll = true;
			}

			if (!doesntHaveAll)
			{
				Messages.Message(player, Messages.getAlreadyHave());
				player.closeInventory();
				return;
			}

			for (String key : element.mustHavePerms.keySet())
			{
				if (!PermissionManager.checkPermission(player, PermissionManager.createPermissionNode(element.mustHavePerms.get(key), null, null), false))
				{
					Messages.Message(player, Messages.getDontHaveMustHavePerm() + Utils.formatText(key));
					player.closeInventory();
					return;
				}
			}

			EconomyResponse response = economy.withdrawPlayer(player, element.price);

			if (response.transactionSuccess())
			{
				for (PermissionNode permissionNode : element.permissions)
				{
					PermissionManager.addPermission(player, permissionNode);
				}
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 75, 1);
				Messages.Message(player, Messages.getSuccessfulBuy());
				player.openInventory(subMenu.getInventory(player));
			}
			else
			{
				if (response.errorMessage.equals("Loan was not permitted"))
					Messages.Message(player, Messages.getNoMoney());
				else
					Messages.Message(player, response.errorMessage);
				player.closeInventory();
			}
		}
	}
}