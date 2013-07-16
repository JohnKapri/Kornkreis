package com.johnkapri.kornkreis.gui;

import java.util.Random;

import com.johnkapri.kornkreis.Game;
import com.johnkapri.kornkreis.level.block.Block;

public class Screen extends Bitmap {

	private Bitmap testBitmap;
	private Bitmap3D viewport;

	public Screen(int width, int height) {
		super(width, height);

		viewport = new Bitmap3D(width, height);

		Random random = new Random();
		testBitmap = new Bitmap(64, 64);
		for (int i = 0; i < 64 * 64; i++) {
			testBitmap.pixels[i] = random.nextInt() * (random.nextInt(5) / 4);
		}
	}

	int time = 0;

	public void render(Game game, boolean hasFocus) {
		if (game.level == null) {
			fill(0, 0, width, height, 0);
		} else {
				viewport.render(game);
				viewport.postProcess(game.level);

				Block block = game.level.getBlock((int) (game.player.x + 0.5), (int) (game.player.z + 0.5));
				if (block.messages != null && hasFocus) {
					for (int y = 0; y < block.messages.length; y++) {
						viewport.draw(block.messages[y], (width - block.messages[y].length() * 6) / 2, (viewport.height - block.messages.length * 8) / 2 + y * 8 + 1, 0x111111);
						viewport.draw(block.messages[y], (width - block.messages[y].length() * 6) / 2, (viewport.height - block.messages.length * 8) / 2 + y * 8, 0x555544);
					}
				}

				draw(viewport, 0, 0);
			}

		if (game.menu != null) {
			for (int i = 0; i < pixels.length; i++) {
				pixels[i] = (pixels[i] & 0xfcfcfc) >> 2;
			}			
			game.menu.render(this);
		}

		if (!hasFocus) {
			for (int i = 0; i < pixels.length; i++) {
				pixels[i] = (pixels[i] & 0xfcfcfc) >> 2;
			}
			if (System.currentTimeMillis() / 450 % 2 != 0) {
				String msg = "Click to focus!";
				draw(msg, (width - msg.length() * 6) / 2, height / 3 + 4, 0xffffff);
			}
		}
	}
}