package cz.tomasan7.upgrades.other;

import org.bukkit.Sound;

public class Constants
{
	public static final int ROW_SIZE = 9;
	public static final PlayableSound CLICK_SOUND = new PlayableSound(Sound.UI_BUTTON_CLICK, 0.25f, 1f);
	public static final PlayableSound BUY_SUCCESS_SOUND = new PlayableSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.75f, 1f);
	public static final PlayableSound BUY_FAILURE_SOUND = new PlayableSound(Sound.UI_BUTTON_CLICK, 0.25f, 1f);
}
