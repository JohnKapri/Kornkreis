package com.johnkapri.kornkreis.level;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import com.johnkapri.kornkreis.Art;
import com.johnkapri.kornkreis.Game;
import com.johnkapri.kornkreis.entities.BatBossEntity;
import com.johnkapri.kornkreis.entities.BatEntity;
import com.johnkapri.kornkreis.entities.BossOgre;
import com.johnkapri.kornkreis.entities.BoulderEntity;
import com.johnkapri.kornkreis.entities.Entity;
import com.johnkapri.kornkreis.entities.EyeBossEntity;
import com.johnkapri.kornkreis.entities.EyeEntity;
import com.johnkapri.kornkreis.entities.GhostBossEntity;
import com.johnkapri.kornkreis.entities.GhostEntity;
import com.johnkapri.kornkreis.entities.Item;
import com.johnkapri.kornkreis.entities.OgreEntity;
import com.johnkapri.kornkreis.entities.Player;
import com.johnkapri.kornkreis.level.block.AltarBlock;
import com.johnkapri.kornkreis.level.block.BarsBlock;
import com.johnkapri.kornkreis.level.block.Block;
import com.johnkapri.kornkreis.level.block.ChestBlock;
import com.johnkapri.kornkreis.level.block.ColoredCeilBlock;
import com.johnkapri.kornkreis.level.block.DoorBlock;
import com.johnkapri.kornkreis.level.block.FinalUnlockBlock;
import com.johnkapri.kornkreis.level.block.IceBlock;
import com.johnkapri.kornkreis.level.block.LadderBlock;
import com.johnkapri.kornkreis.level.block.LockedDoorBlock;
import com.johnkapri.kornkreis.level.block.LootBlock;
import com.johnkapri.kornkreis.level.block.PitBlock;
import com.johnkapri.kornkreis.level.block.PressurePlateBlock;
import com.johnkapri.kornkreis.level.block.SolidBlock;
import com.johnkapri.kornkreis.level.block.SolidColoredBlock;
import com.johnkapri.kornkreis.level.block.SpiritWallBlock;
import com.johnkapri.kornkreis.level.block.SwitchBlock;
import com.johnkapri.kornkreis.level.block.TorchBlock;
import com.johnkapri.kornkreis.level.block.VanishBlock;
import com.johnkapri.kornkreis.level.block.WaterBlock;
import com.johnkapri.kornkreis.level.block.WinBlock;
import com.johnkapri.kornkreis.menu.GotLootMenu;

public class Level {
	public Block[] blocks;
	public int width, height;
	private Block solidWall = new SolidBlock();

	public int xSpawn;
	public int ySpawn;

	protected int wallCol = 0xB3CEE2;
	//protected int wallCol = 0xA25555;
	protected int floorCol = 0x508253;
	protected int ceilCol = 0x9CA09B;

	protected int wallTex = 0;
	protected int floorTex = 3 * 8;
	protected int ceilTex = -1;
	
	protected int ticks;

	public List<Entity> entities = new ArrayList<Entity>();
	protected Game game;
	public String name = "";

	public Player player;

	public void init(Game game, String name, int w, int h, int[] pixels) {
		this.game = game;

		player = game.player;

		solidWall.col = Art.getCol(wallCol);
		solidWall.tex = Art.getCol(wallTex);
		this.width = w;
		this.height = h;
		blocks = new Block[width * height];

		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				int col = pixels[x + y * w] & 0xffffff;
				int id = 255 - ((pixels[x + y * w] >> 24) & 0xff);

				Block block = getBlock(x, y, col);
				block.id = id;

				if (block.tex == -1) block.tex = wallTex;
				if (block.floorTex == -1) block.floorTex = floorTex;
				if (block.ceilTex == -1) block.ceilTex = ceilTex;
				if (block.col == -1) block.col = Art.getCol(wallCol);
				if (block.floorCol == -1) block.floorCol = Art.getCol(floorCol);
				if (block.ceilCol == -1) block.ceilCol = Art.getCol(ceilCol);

				blocks[x + y * w] = block;
				block.level = this;
				block.x = x;
				block.y = y;
			}
		}

		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				int col = pixels[x + y * w] & 0xffffff;
				decorateBlock(x, y, blocks[x + y * w], col);
			}
		}
	}

	public void addEntity(Entity e) {
		entities.add(e);
		e.level = this;
		e.updatePos();
	}

	public void removeEntityImmediately(Player player) {
		entities.remove(player);
		getBlock(player.xTileO, player.zTileO).removeEntity(player);
	}

	protected void decorateBlock(int x, int y, Block block, int col) {
		block.decorate(this, x, y);
		if (col == 0xFFFF00) {
			xSpawn = x;
			ySpawn = y;
		}
		if (col == 0xAA5500) addEntity(new BoulderEntity(x, y));
		if (col == 0xff0000) addEntity(new BatEntity(x, y));
		if (col == 0xff0001) addEntity(new BatBossEntity(x, y));
		if (col == 0xff0002) addEntity(new OgreEntity(x, y));
		if (col == 0xff0003) addEntity(new BossOgre(x, y));
		if (col == 0xff0004) addEntity(new EyeEntity(x, y));
		if (col == 0xff0005) addEntity(new EyeBossEntity(x, y));
		if (col == 0xff0006) addEntity(new GhostEntity(x, y));
		if (col == 0xff0007) addEntity(new GhostBossEntity(x, y));
		if (col == 0x1A2108 || col == 0xff0007) {
			block.floorTex = 7;
			block.ceilTex = 7;
		}

		if (col == 0xC6C6C6) block.col = Art.getCol(0xa0a0a0);
		if (col == 0xC6C697) block.col = Art.getCol(0xa0a0a0);
		if (col == 0x653A00) {
			block.floorCol = Art.getCol(0xB56600);
			block.floorTex = 3 * 8 + 1;
		}

		if (col == 0x93FF9B) {
			block.col = Art.getCol(0x2AAF33);
			block.tex = 8;
		}
	}

	protected Block getBlock(int x, int y, int col) {
		if (col == 0x93FF9B) return new SolidBlock();
		if (col == 0x009300) return new PitBlock();
		if (col == 0xFFFFFF) return new SolidBlock();
		if (col == 0x00FFFF) return new VanishBlock();
		if (col == 0xFFFF64) return new ChestBlock();
		if (col == 0x0000FF) return new WaterBlock();
		if (col == 0xFF3A02) return new TorchBlock();
		if (col == 0x4C4C4C) return new BarsBlock();
		if (col == 0xFF66FF) return new LadderBlock(false);
		if (col == 0x9E009E) return new LadderBlock(true);
		if (col == 0xC1C14D) return new LootBlock();
		if (col == 0xC6C6C6) return new DoorBlock();
		if (col == 0x00FFA7) return new SwitchBlock();
		if (col == 0x009380) return new PressurePlateBlock();
		if (col == 0xff0005) return new IceBlock();
		if (col == 0x3F3F60) return new IceBlock();
		if (col == 0xC6C697) return new LockedDoorBlock();
		if (col == 0xFFBA02) return new AltarBlock();
		if (col == 0x749327) return new SpiritWallBlock();
		if (col == 0x1A2108) return new Block();
		if (col == 0x00C2A7) return new FinalUnlockBlock();
		if (col == 0x000056) return new WinBlock();
		
		if (col == 0x00AAFF) return new SolidColoredBlock();
		if (col == 0xAAFF00) return new ColoredCeilBlock();

		return new Block();
	}

	public Block getBlock(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) {
			return solidWall;
		}
		return blocks[x + y * width];
	}

	public static Level loadLevel(Game game, String name) {

		try {
			BufferedImage img = ImageIO.read(new FileInputStream(name));

			int w = img.getWidth();
			int h = img.getHeight();
			int[] pixels = new int[w * h];
			img.getRGB(0, 0, w, h, pixels, 0, w);

			Level level = new Level();
			level.init(game, name, w, h, pixels);

			return level;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void findRandomSpawn() {
		Random rand = new Random();
		while(true) {
			int s = rand.nextInt(blocks.length);
			if(blocks[s].canStandOn()) {
				xSpawn = s % width;
				ySpawn = s / width;
			}
		}
	}

	public boolean containsBlockingEntity(double x0, double y0, double x1, double y1) {
		int xc = (int) (Math.floor((x1 + x0) / 2));
		int zc = (int) (Math.floor((y1 + y0) / 2));
		int rr = 2;
		for (int z = zc - rr; z <= zc + rr; z++) {
			for (int x = xc - rr; x <= xc + rr; x++) {
				List<Entity> es = getBlock(x, z).entities;
				for (int i = 0; i < es.size(); i++) {
					Entity e = es.get(i);
					if (e.isInside(x0, y0, x1, y1)) return true;
				}
			}
		}
		return false;
	}

	public boolean containsBlockingNonFlyingEntity(double x0, double y0, double x1, double y1) {
		int xc = (int) (Math.floor((x1 + x0) / 2));
		int zc = (int) (Math.floor((y1 + y0) / 2));
		int rr = 2;
		for (int z = zc - rr; z <= zc + rr; z++) {
			for (int x = xc - rr; x <= xc + rr; x++) {
				List<Entity> es = getBlock(x, z).entities;
				for (int i = 0; i < es.size(); i++) {
					Entity e = es.get(i);
					if (!e.flying && e.isInside(x0, y0, x1, y1)) return true;
				}
			}
		}
		return false;
	}

	public void tick() {
		ticks++;
		
		wallCol = (int) (Math.sin(((double) ticks) / 1000D) * (double)0xFFFFFF);
		
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick();
			e.updatePos();
			if (e.isRemoved()) {
				entities.remove(i--);
			}
		}

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				blocks[x + y * width].tick();
			}
		}
	}

	public void trigger(int id, boolean pressed) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Block b = blocks[x + y * width];
				if (b.id == id) {
					b.trigger(pressed);
				}
			}
		}
	}

	public void switchLevel(int id) {
	}

	public void findSpawn(int id) {
		findRandomSpawn();
//		for (int y = 0; y < height; y++) {
//			for (int x = 0; x < width; x++) {
//				Block b = blocks[x + y * width];
//				if (b.id == id && b instanceof LadderBlock) {
//					xSpawn = x;
//					ySpawn = y;
//				}
//			}
//		}
	}

	public void getLoot(int id) {
		if (id == 20) game.getLoot(Item.pistol);
		if (id == 21) game.getLoot(Item.potion);
	}

	public void win() {
		game.win(player);
	}

	public void lose() {
		game.lose(player);
	}

	public void showLootScreen(Item item) {
		game.setMenu(new GotLootMenu(item));
	}
	
	public int getColor() {
		return wallCol;
	}
}