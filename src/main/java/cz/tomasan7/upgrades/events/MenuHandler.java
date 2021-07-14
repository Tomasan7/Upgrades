package cz.tomasan7.upgrades.events;

import cz.tomasan7.upgrades.menus.Menu;
import cz.tomasan7.upgrades.menus.MenuElement;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class MenuHandler implements Listener
{
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

		if (holder instanceof Menu menu)
		{
			Optional<MenuElement> clickedElement = menu.getElements().stream()
					.filter(element -> element.getItemStack().equals(clickedItem))
					.findAny();

			if (clickedElement.isPresent())
			{
				clickedElement.get().onClick(event);
				event.setCancelled(true);
			}
		}
	}

	//	private void mainMenu (Player player, ItemStack clickedItem, MainMenu menu)
	//	{
	//		menu.getElements().stream()
	//				.filter(element -> element.getItemStack().equals(clickedItem))
	//				.findAny().ifPresent((element) ->
	//		{
	//			Constants.CLICK_SOUND.play(player);
	//			player.openInventory(SubMenu.newSubMenu(element.getSubMenu(), player).getInventory());
	//		});
	//	}

	//	private void subMenu (Player player, ItemStack clickedItem, SubMenu subMenu)
	//	{
	//		Economy economy = Upgrades.getEconomy();
	//
	//		SubMenuElement clickedElement = subMenu.getElements().stream()
	//				.filter(element -> element.getItemStack().equals(clickedItem))
	//				.findAny().orElse(null);
	//
	//		if (clickedElement == null)
	//			return;
	//
	//		Constants.CLICK_SOUND.play(player);
	//
	//		if (clickedElement.getPermissions().stream().allMatch(perm -> PermissionManager.checkPermission(player, perm, true)))
	//		{
	//			Messages.Message(player, Messages.getAlreadyHave());
	//			player.closeInventory();
	//			return;
	//		}
	//
	//		/* If player misses at least one mustHavePermission, then interrupt. */
	//		for (String key : clickedElement.getMustHavePerms().keySet())
	//		{
	//			if (!PermissionManager.checkPermission(player, PermissionManager.createPermissionNode(clickedElement.getMustHavePerms().get(key), Config.getDefaultContexts(), false), false))
	//			{
	//				Messages.Message(player, Messages.getDontHaveMustHavePerm() + Utils.formatText(key));
	//				player.closeInventory();
	//				return;
	//			}
	//		}
	//
	//		EconomyResponse response = economy.withdrawPlayer(player, clickedElement.getPrice());
	//
	//		if (response.transactionSuccess())
	//		{
	//			clickedElement.getPermissions().forEach(perm -> PermissionManager.addPermission(player, perm));
	//			Constants.BUY_SUCCESS_SOUND.play(player);
	//			Messages.Message(player, Messages.getSuccessfulBuy());
	//			player.openInventory(SubMenu.newSubMenu(subMenu.getName(), player).getInventory());
	//		}
	//		else
	//		{
	//			if (response.errorMessage.equals("Loan was not permitted"))
	//				Messages.Message(player, Messages.getNoMoney());
	//			else
	//				Messages.Message(player, response.errorMessage);
	//			player.closeInventory();
	//		}
	//	}
}