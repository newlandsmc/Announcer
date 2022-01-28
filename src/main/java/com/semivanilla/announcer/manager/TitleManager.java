package com.semivanilla.announcer.manager;

import com.semivanilla.announcer.animation.Animation;
import com.semivanilla.announcer.animation.impl.GradientAnimation;
import com.semivanilla.announcer.object.TitleInfo;
import com.semivanilla.announcer.util.UUIDUtil;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TitleManager {
    @Getter
    private static final Map<UUID, TitleInfo> titles = new ConcurrentHashMap<>();

    public static void showTitle(Player player, String title, String subtitle, long fadeIn, int stay, long fadeOut) {
        final String rawTitle = title, rawSubtitle = subtitle;
        Animation animation = new GradientAnimation();
        Component title1 = parseTitle(title, animation);
        Component subtitle1 = parseTitle(subtitle, animation);
        showTitle(player, title1, subtitle1, fadeIn, stay, fadeOut);
        long ticksLeft = stay * 20L;
        TitleInfo info = new TitleInfo(title, subtitle, rawTitle, rawSubtitle, animation, ticksLeft, player.getUniqueId(), stay, fadeIn, fadeOut);
        titles.put(player.getUniqueId(), info);
    }

    public static void update(TitleInfo info) {
        if (Bukkit.getPlayer(info.getUuid()) == null) {
            UUIDUtil.remove(titles, info.getUuid());
            return;
        }
        Player player = Bukkit.getPlayer(info.getUuid());
        if (info.getTicksLeft() <= 0) {
            UUIDUtil.remove(titles, info.getUuid());
            return;
        }
        info.setTicksLeft(info.getTicksLeft() - 1);
        Component title = parseTitle(info.getRawTitle(), info.getAnimation());
        Component subtitle = parseTitle(info.getRawSubtitle(), info.getAnimation());
        boolean last = info.getTicksLeft() <= 0;
        if (last) {
            showTitle(player, title, subtitle, 0, 0, info.getFadeOut());
        } else {
            showTitle(player, title, subtitle, 0, 1, 0);
        }
    }

    public static Component parseTitle(String title, Animation animation) {
        if (title.contains("<animate>")) {
            if (title.contains("</animate>"))
                title = title.replace("<animate>", "<gradient:" + ConfigManager.getColor1() + ":" + ConfigManager.getColor2() + ":" + ConfigManager.getColor3() + ":" + animation.nextValue() + ">").replace("</animate>", "</gradient>");
            else throw new RuntimeException("Missing closing animate tag (</animate> expected)");
        }
        return MiniMessage.get().parse(title);
    }

    public static void showTitle(Player player, Component title, Component subtitle, long fadeIn, int stay, long fadeOut) {
        Title.Times times = Title.Times.of(Duration.ofMillis(fadeIn), Duration.ofSeconds(stay), Duration.ofMillis(fadeOut));
        Title t = Title.title(title, subtitle, times);
        player.showTitle(t);
    }
}
