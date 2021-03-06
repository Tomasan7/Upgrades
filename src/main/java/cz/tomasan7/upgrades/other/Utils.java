package cz.tomasan7.upgrades.other;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Set;

public class Utils {

    public static String formatText (String text)
    {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String formatText (String text, Player player)
    {
        return ChatColor.translateAlternateColorCodes('&', player == null ? text : PlaceholderAPI.setPlaceholders(player, text));
    }

    public static String formatText (String text, String placeHolder, double value)
    {
        String finalText = ChatColor.translateAlternateColorCodes('&', text);
        finalText = finalText.replaceAll(placeHolder, Double.toString(value));
        return finalText;
    }

    public static String formatText (String text, String placeholder, String value)
    {
        String finalText = ChatColor.translateAlternateColorCodes('&', text);
        finalText = finalText.replaceAll(placeholder, value);
        return finalText;
    }

    public static  String removeColors (String colorChar, String text)
    {
        StringBuilder finalText = new StringBuilder(text);

        while (finalText.toString().contains(colorChar))
        {
            int index = finalText.indexOf(colorChar);

            finalText.delete(index, index + 2);
        }
        return finalText.toString();
    }

    public static String[] fromSetToArray (Set<String> set)
    {
        String[] array = new String[set.size()];

        int i = 0;
        for (String string : set)
            array[i++] = string;

        return array;
    }

    public static ItemStack createItemStack (Material material, String displayName, List<String> lore, int amount)
    {
        ItemStack itemStack = new ItemStack(material, amount);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
