# Announcer

# Development will continue on [Badbird5907/Announcer](https://github.com/Badbird5907/Announcer)

# Authors
[Badbird5907](https://github.com/Badbird5907), SemiVanillaMC

# Config
Announcer uses [MiniMessage](https://docs.adventure.kyori.net/minimessage#format) to format messages.
There is an extra tag: `<animate>` which animates the gradient set in config.yml. This can be used in either the Title or Subtitle.

The `interval-time-unit` specified in config is a value from [TimeUnit](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/TimeUnit.html)

The title updates once a tick or every 50 milliseconds. 

The `name` under `join-sound` can either be a minecraft resource location (for example `ui.toast.challenge_complete`), or a value from [org.bukkit.Sound](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Sound.html)

The messages can be configured under `messages` in config.yml. Make sure to make a new entry for each message.
For example:
```yaml
messages:
  0:
    - '<gradient:yellow:red:yellow><strikethrough>                                                                 <reset>'
    - ''
    - 'Something'
    - ''
    - '<gradient:yellow:red:yellow><strikethrough>                                                                 <reset>'
  1:
    - 'Hello World!'
```

Gradient `color-1`, `color-2`, and `color-3` can be color names, hex values etc... they are used to create a gradient from [this](https://docs.adventure.kyori.net/minimessage#gradient)

If `random-message-order` is true, the messages will be randomly selected. Otherwise, they will be sent in order

`fade-in` and `fade-out` are in milliseconds

# Adding more animations
create a new class implemnenting [Animation](https://github.com/SemiVanilla-MC/Announcer/blob/master/src/main/java/com/semivanilla/announcer/animation/Animation.java) [here](https://github.com/SemiVanilla-MC/Announcer/tree/master/src/main/java/com/semivanilla/announcer/animation/impl)

You will need to modify [TitleManager](https://github.com/SemiVanilla-MC/Announcer/blob/7e0aad77b281c85623daebada69884ea8d82f56c/src/main/java/com/semivanilla/announcer/manager/TitleManager.java#L25) to add support for this animation as it dosen't support multiple animations currently.
