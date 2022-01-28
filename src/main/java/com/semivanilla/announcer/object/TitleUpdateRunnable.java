package com.semivanilla.announcer.object;

import com.semivanilla.announcer.manager.TitleManager;
import org.bukkit.scheduler.BukkitRunnable;

public class TitleUpdateRunnable extends BukkitRunnable {
    @Override
    public void run() {
        TitleManager.getTitles().forEach((uuid, title) -> {
            TitleManager.update(title);
        });
    }
}
