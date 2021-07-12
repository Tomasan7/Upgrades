package cz.tomasan7.upgrades;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin
{
    private static Main instance;

    public Main ()
    {
        instance = this;
    }

    @Override
	public void onEnable ()
	{
		Upgrades.onEnable();
	}

    public static Main getInstance ()
    {
        return instance;
    }
}
