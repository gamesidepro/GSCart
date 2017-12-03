/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gscart.utils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

/**
 *
 * @author Twelvee
 */
public class utils {
        public static void sendMessage(Player player, Object... args) {
		if (args.length != 0) {
			player.sendMessage(Text.of(args));
		}
    }
}
