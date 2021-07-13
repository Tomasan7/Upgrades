package cz.tomasan7.upgrades.other;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public record PlayableSound (Sound sound, float volume, float pitch)
{
	public void play (Player player)
	{
		player.playSound(player.getLocation(), sound, volume, pitch);
	}
}
