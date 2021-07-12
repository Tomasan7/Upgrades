package cz.tomasan7.upgrades.menus;

import cz.tomasan7.upgrades.other.Utils;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MainMenuElement {

    public ItemStack item;
    public int slot;
    public String subMenu;

    public MainMenuElement (FileConfiguration config, String path)
    {
        String material = Utils.formatText(config.getString(path + "material"));                    //    Material
        String displayName = Utils.formatText(config.getString(path + "display-name"));             //    DisplayName
        List<String> lore = new ArrayList<>();                                                            //    --------------
        for (String eachLore : config.getStringList(path + "lore"))                                 //
        {                                                                                                 //        Lore
            lore.add(Utils.formatText(eachLore));                                                         //
        }                                                                                                 //    --------------
        int amount = config.getInt(path + "amount");                                                //    Amount
        this.slot = config.getInt(path + "slot");                                                   //    Slot
        this.subMenu = config.getString(path + "sub-menu");                                         //    SubMenu

        ItemStack item = new ItemStack(Material.getMaterial(material), amount);
        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);

        item.setItemMeta(itemMeta);
        this.item = item;
    }
}
