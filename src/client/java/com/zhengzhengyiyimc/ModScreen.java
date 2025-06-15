package com.zhengzhengyiyimc;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ModScreen {
    public Screen create(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
		.alwaysShowTabs()
		.setSavingRunnable(() -> {
			Improved_item.save();
		})
        .setTitle(Text.translatable("title.improved_item.config"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        ConfigCategory general = builder.getOrCreateCategory(Text.translatable("config.improved_item.general"));
        
        general.addEntry(entryBuilder.startIntField(Text.of("Moving hit damage"), Math.round(Improved_item.modConfig.movingHitDamage))
            .setSaveConsumer(v -> {Improved_item.modConfig.movingHitDamage = v; Improved_item.save();})
            .setDefaultValue(8)
            .build());

        return builder.build();
    }
}
