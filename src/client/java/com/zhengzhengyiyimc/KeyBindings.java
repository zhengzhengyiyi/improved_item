package com.zhengzhengyiyimc;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    public static KeyBinding openConfigKey;
    
    public static void register() {
        openConfigKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.improved_item.open_config",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_O,
            "category.improved_item.keys"
        ));
    }
}