package com.semivanilla.announcer.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JoinConfig {
    private boolean enableSound, enableTitle;

    private int titleDuration, bedrockDuration;
    private long fadeIn, fadeOut, fadeInBedrock, fadeOutBedrock;
    private String title, subtitle;

    private String soundName, source;
    private double pitch, volume;

    private boolean enableBedrockTitle;
    private String bedrockTitle, bedrockSubtitle;
}
