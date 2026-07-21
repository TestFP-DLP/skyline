package skyline;

import org.newdawn.slick.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;;
//123
/**
 * Actual game.
 * 
 * @author www.gametutorial.net
 */

public class Game {

	// Use this to generate a random number.
	private Random random;


	private boolean bossFight = false;
	private boolean bossCreate = true;
	private int random_enemy;
	private int UpDamage;
	
	private Music gameBg;
	private PlayerHelicopter player;
	private int level=1;
	private int levelGun=1;
	private long gametime1;
	private long timeToPlay;
	private ArrayList<EnemyHelicopter> enemyHelicopterList = new ArrayList<EnemyHelicopter>();
	private ArrayList<EnemyBoss> BossList = new ArrayList<EnemyBoss>();

	// Explosions
	private ArrayList<Animation> explosionsList;
	private BufferedImage explosionAnimImg;

	// List of all the machine gun bullets.
	private ArrayList<Bullet> bulletsList;
	private ArrayList<BulletEnemy> bulletsEnemyList;
	private ArrayList<BulletBoss> bulletsBossList;

	// List of all the rockets.
	private ArrayList<Rocket> rocketsList;
	// List of all the rockets smoke.
	private ArrayList<RocketSmoke> rocketSmokeList;


	// Images for white spot on the sky.
	
	private BufferedImage gameBackground;
	private BufferedImage groundImg;
	// Objects of moving images.

	private MovingBackground backGoundMoving;
	private MovingBackground groundMoving;

	private Font font;
	
	private boolean lastBossDefeat = false;
	// Statistics (destroyed enemies, run away enemies)
	private int runAwayEnemies;
	private int destroyedEnemies = 11;

	public Game() {
		Framework.gameState = Framework.GameState.GAME_CONTENT_LOADING;

		Thread threadForInitGame = new Thread() {
			@Override
			public void run() {
				// Sets variables and objects for the game.
				Initialize();
				// Load game files (images, sounds, ...)
				LoadContent();

				Framework.gameState = Framework.GameState.PLAYING;
			}
		};
		threadForInitGame.start();
	}

	/**
	 * Set variables and objects for the game.
	 */
	private void Initialize() {
		random = new Random();

		player = new PlayerHelicopter(Framework.frameWidth / 4, Framework.frameHeight / 4);

		enemyHelicopterList = new ArrayList<EnemyHelicopter>();
		BossList = new ArrayList<EnemyBoss>();

		explosionsList = new ArrayList<Animation>();

		bulletsList = new ArrayList<Bullet>();
		bulletsEnemyList = new ArrayList<BulletEnemy>();
		bulletsBossList = new ArrayList<BulletBoss>();
		rocketsList = new ArrayList<Rocket>();
		rocketSmokeList = new ArrayList<RocketSmoke>();

		backGoundMoving = new MovingBackground();
		groundMoving = new MovingBackground();
		
		try {
			gameBg = new Music("skyline/resources/audio/The Last Encounter (90s RPG Version) Short Loop.wav");
			enemyExplosion.explosion = new Sound("skyline/resources/audio/Explosion+7.wav");
			EnemyBoss.bossWarning = new Sound("skyline/resources/audio/Boss Battle Warning!.wav");
			enemyExplosion.bossDead = new Sound("skyline/resources/audio/Explosion+3.wav");
		} catch (SlickException e) {
			e.printStackTrace();
		}
        gameBg.loop();
        gameBg.setVolume((float) 0.1);
		font = new Font("monospaced", Font.BOLD, 18);

		runAwayEnemies = 0;
		destroyedEnemies = 0;
	}

	/**
	 * Load game files (images).
	 */
	private void LoadContent() {
		try {
			// Images of environment
			
			URL groundImgUrl = this.getClass().getResource("/skyline/resources/images/ground.png");
			groundImg = ImageIO.read(groundImgUrl);
			URL gameBackgroundUrl = this.getClass().getResource("/skyline/resources/images/skyBG.jpg");
			gameBackground = ImageIO.read(gameBackgroundUrl);

			// Load images for enemy helicopter
			URL enemyBodyImgImgUrl = this.getClass().getResource("/skyline/resources/images/enemyBot.png");
			EnemyHelicopter.enemyBodyImg = ImageIO.read(enemyBodyImgImgUrl);
			URL enemyDefend = this.getClass().getResource("/skyline/resources/images/enemyDefendBot.png");
			EnemyHelicopter.enemyDefendImg = ImageIO.read(enemyDefend);
			URL enemyUp = this.getClass().getResource("/skyline/resources/images/enemyUp.jpg");
			EnemyHelicopter.enemyUpImg = ImageIO.read(enemyUp);
			URL enemyHeal = this.getClass().getResource("/skyline/resources/images/enemyHeal.png");
			EnemyHelicopter.enemyHealImg = ImageIO.read(enemyHeal);
			URL enemyExploImgUrl = this.getClass().getResource("/skyline/resources/images/explosion_anim.png");
			explosionAnimImg = ImageIO.read(enemyExploImgUrl);
			
			URL bossSt1 = this.getClass().getResource("/skyline/resources/images/boss_st1.png");
			EnemyBoss.BossSt1 = ImageIO.read(bossSt1);
			
			URL bossSt1hit = this.getClass().getResource("/skyline/resources/images/boss_st1hit.png");
			EnemyBoss.BossSt1hit = ImageIO.read(bossSt1hit);
			
			URL bossSt2 = this.getClass().getResource("/skyline/resources/images/boss_st2.png");
            EnemyBoss.BossSt2 = ImageIO.read(bossSt2);
            URL bossSt2hit = this.getClass().getResource("/skyline/resources/images/boss_st2hit.png");
			EnemyBoss.BossSt2hit = ImageIO.read(bossSt2hit);
            URL bossSt3 = this.getClass().getResource("/skyline/resources/images/boss_st3.png");
            EnemyBoss.BossSt3 = ImageIO.read(bossSt3);
            URL bossSt3hit = this.getClass().getResource("/skyline/resources/images/boss_st3hit.png");
			EnemyBoss.BossSt3hit = ImageIO.read(bossSt3hit);
            URL bossSt4 = this.getClass().getResource("/skyline/resources/images/boss_st4.png");
            EnemyBoss.BossSt4 = ImageIO.read(bossSt4);
            URL bossSt4hit = this.getClass().getResource("/skyline/resources/images/boss_st4hit.png");
			EnemyBoss.BossSt4hit = ImageIO.read(bossSt4hit);
            
            
			// Images of rocket and its smoke.
			URL rocketImgUrl = this.getClass().getResource("/skyline/resources/images/beam.png");
			Rocket.rocketImg = ImageIO.read(rocketImgUrl);
			URL rocketSmokeImgUrl = this.getClass().getResource("/skyline/resources/images/beamplasma.png");
			RocketSmoke.smokeImg = ImageIO.read(rocketSmokeImgUrl);
			

			// Helicopter machine gun bullet.
			URL bulletImgUrl = this.getClass().getResource("/skyline/resources/images/bulletPlayer.png");
			Bullet.bulletImg1 = ImageIO.read(bulletImgUrl);
			
			URL bulletImgUr2 = this.getClass().getResource("/skyline/resources/images/bulletPlayer2.png");
			Bullet.bulletImg2 = ImageIO.read(bulletImgUr2);
			
			URL bulletImgUr3 = this.getClass().getResource("/skyline/resources/images/bulletPlayer3.png");
			Bullet.bulletImg3 = ImageIO.read(bulletImgUr3);
			
			URL bulletImgUr4 = this.getClass().getResource("/skyline/resources/images/bulletPlayer4.png");
			Bullet.bulletImg4 = ImageIO.read(bulletImgUr4);
			
			URL bulletImgUrl1 = this.getClass().getResource("/skyline/resources/images/bulletEnemy.png");
			BulletEnemy.bulletImg = ImageIO.read(bulletImgUrl1);
			URL bulletImgUrl2 = this.getClass().getResource("/skyline/resources/images/bulletEnemy.png");
			BulletBoss.bulletImg = ImageIO.read(bulletImgUrl1);
		} catch (IOException ex) {
			Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
		}

		// Now that we have images we initialize moving images.
//		cloudLayer1Moving.Initialize(cloudLayer1Img, -6, 0);
//		cloudLayer2Moving.Initialize(cloudLayer2Img, -2, 0);
		backGoundMoving.Initialize(gameBackground, -1,0);
		groundMoving.Initialize(groundImg, -1.2, Framework.frameHeight - groundImg.getHeight());
	}

	/**
	 * Restart game - reset some variables.
	 */
	public void RestartGame() {
		player.Reset(Framework.frameWidth / 4, Framework.frameHeight / 4);

		EnemyHelicopter.restartEnemy();
		EnemyBoss.lastBoss = false;
		lastBossDefeat = false;
		Bullet.timeOfLastCreatedBullet = 0;
		BulletBoss.timeOfLastCreatedBossBullet = 0;
		BulletEnemy.timeOfLastCreatedBullet = 0;
		Rocket.timeOfLastCreatedRocket = 0;
		level = 1;
		bossFight = false;
		bossCreate = true;
		// Empty all the lists.
		enemyHelicopterList.clear();
		BossList.clear();
		bulletsList.clear();
		bulletsEnemyList.clear();
		bulletsBossList.clear();
		rocketsList.clear();
		rocketSmokeList.clear();
		explosionsList.clear();

		// Statistics
		runAwayEnemies = 0;
		destroyedEnemies = 0;
	}

	/**
	 * Update game logic.
	 * 
	 * @param gameTime
	 *            The elapsed game time in nanoseconds.
	 * @param mousePosition
	 *            current mouse position.
	 */
	public void UpdateGame(long gameTime) {
		/* Player */
		// When player is destroyed and all explosions are finished showing we change
		// game status.
		if (!isPlayerAlive()) {
			Framework.gameState = Framework.GameState.GAMEOVER;
			gameBg.stop();
			return; // If player is destroyed, we don't need to do thing below.
		}
		if (isGameClear()) {
			gameBg.stop();
			Framework.gameState = Framework.GameState.GAME_CLEAR;
			return; // If player is destroyed, we don't need to do thing below.
		}
		 
		// If player is alive we update him.
		if (isPlayerAlive()) {
			isPlayerShooting(gameTime);
			isEnemyShooting(gameTime);
			isBossShooting(gameTime);
			didPlayerFiredRocket(gameTime);
			player.isMoving();
			player.Update(levelGun);
		}

		
		if(levelGun == 4)
		{
			if(gametime1 - timeToPlay == 0)
			{
				levelGun = 3;
			}
			else
				gametime1++;
		}
		
		
		/* Bullets */
		updateBullets();
		updateBulletsEnemy();
		updateBulletsBoss();

		/* Rockets */
		updateRockets(gameTime); // It also checks for collisions (if any of the rockets hit any of the enemy
									// helicopter).
		updateRocketSmoke(gameTime);

		/* Enemies */
		createEnemyHelicopter(gameTime);
		updateEnemies(gameTime);
		updateBoss(gameTime);

		/* Explosions */
		updateExplosions();
	}

	/**
	 * Draw the game to the screen.
	 * 
	 * @param g2d
	 *            Graphics2D
	 * @param mousePosition
	 *            current mouse position.
	 */
	public void Draw(Graphics2D g2d, long gameTime) {
		// Image for background sky color.
		g2d.drawImage(gameBackground, 0, 0, Framework.frameWidth, Framework.frameHeight, null);

		// Moving images.
		backGoundMoving.Draw(g2d);
		groundMoving.Draw(g2d);
//		cloudLayer2Moving.Draw(g2d);

		if (isPlayerAlive())
			player.Draw(g2d);

		// Draws all the enemies.
		for (int i = 0; i < enemyHelicopterList.size(); i++) {
			enemyHelicopterList.get(i).Draw(g2d);
		}

		for (int i = 0; i < BossList.size(); i++) {
			BossList.get(i).Draw(g2d);
		}

		// Draws all the bullets.
		for (int i = 0; i < bulletsList.size(); i++) {
			bulletsList.get(i).Draw(g2d);
		}
		
		for (int i = 0; i < bulletsEnemyList.size(); i++) {

			bulletsEnemyList.get(i).Draw(g2d);
		}
		
		for (int i = 0; i < bulletsBossList.size(); i++) {
			
			bulletsBossList.get(i).Draw(g2d);
		}
		// Draws all the rockets.
		for (int i = 0; i < rocketsList.size(); i++) {
			rocketsList.get(i).Draw(g2d);
		}
		// Draws smoke of all the rockets.
		for (int i = 0; i < rocketSmokeList.size(); i++) {
			rocketSmokeList.get(i).Draw(g2d);
		}

		// Draw all explosions.
		for (int i = 0; i < explosionsList.size(); i++) {
			explosionsList.get(i).Draw(g2d);
		}

		// Draw statistics
		g2d.setFont(font);
		g2d.setColor(Color.white);

		g2d.drawString(formatTime(gameTime), Framework.frameWidth / 2 - 45, 21);
		g2d.drawString("DESTROYED: " + destroyedEnemies, 10, 21);
		g2d.drawString("RUNAWAY: " + runAwayEnemies, 10, 41);
		g2d.drawString("ROCKETS: " + player.numberOfRockets, 10, 81);
		g2d.drawString("AMMO: " + player.numberOfAmmo, 10, 101);
		g2d.drawString("Boss: " + bossFight, 10, 115);
		g2d.drawString("heath: " + player.health, 10, 130);

		// Moving images. We draw this cloud in front of the helicopter.
//		cloudLayer1Moving.Draw(g2d);

		// Mouse cursor

	}

	/**
	 * Draws some game statistics when game is over.
	 * 
	 * @param g2d
	 *            Graphics2D
	 * @param gameTime
	 *            Elapsed game time.
	 */
	public void DrawStatistic(Graphics2D g2d, long gameTime) {
		g2d.drawString("Time: " + formatTime(gameTime), Framework.frameWidth / 2 - 50, Framework.frameHeight / 3 + 80);
		g2d.drawString("Rockets left: " + player.numberOfRockets, Framework.frameWidth / 2 - 55,
				Framework.frameHeight / 3 + 105);
		g2d.drawString("Ammo left: " + player.numberOfAmmo, Framework.frameWidth / 2 - 55,
				Framework.frameHeight / 3 + 125);
		g2d.drawString("Destroyed enemies: " + destroyedEnemies, Framework.frameWidth / 2 - 65,
				Framework.frameHeight / 3 + 150);
		g2d.drawString("Runaway enemies: " + runAwayEnemies, Framework.frameWidth / 2 - 65,
				Framework.frameHeight / 3 + 170);
		g2d.setFont(font);
		g2d.drawString("Statistics: ", Framework.frameWidth / 2 - 75, Framework.frameHeight / 3 + 60);
	}

	/**
	 * Format given time into 00:00 format.
	 * 
	 * @param time
	 *            Time that is in nanoseconds.
	 * @return Time in 00:00 format.
	 */
	private static String formatTime(long time) {
		// Given time in seconds.
		int sec = (int) (time / Framework.milisecInNanosec / 1000);

		// Given time in minutes and seconds.
		int min = sec / 60;
		sec = sec - (min * 60);

		String minString, secString;

		if (min <= 9)
			minString = "0" + Integer.toString(min);
		else
			minString = "" + Integer.toString(min);

		if (sec <= 9)
			secString = "0" + Integer.toString(sec);
		else
			secString = "" + Integer.toString(sec);

		return minString + ":" + secString;
	}

	/*
	 * 
	 * Methods for updating the game.
	 * 
	 */

	/**
	 * Check if player is alive. If not, set game over status.
	 * 
	 * @return True if player is alive, false otherwise.
	 */
	private boolean isPlayerAlive() {
		if (player.health <= 0)
			return false;

		return true;
	}
	private boolean isGameClear() {
		if (lastBossDefeat && explosionsList.size() == 0)
			return true;

		return false;
	}

	/**
	 * Checks if the player is shooting with the machine gun and creates bullets if
	 * he shooting.
	 * 
	 * @param gameTime
	 *            Game time.
	 */
	private void isPlayerShooting(long gameTime) {
		if (player.isShooting(gameTime)) {
			switch (levelGun) {
			case 1:
				Bullet.timeOfLastCreatedBullet = gameTime;
				Bullet b = new Bullet(player.machineGunXcoordinate, player.machineGunYcoordinate,levelGun,3);
				bulletsList.add(b);
				break;
			case 2:
				System.out.println("2");
				Bullet.timeOfLastCreatedBullet = gameTime;
				Bullet b2_1 = new Bullet(player.machineGunXcoordinate, player.machineGunYcoordinate,levelGun,3);
				bulletsList.add(b2_1);
				break;
			case 3:
				System.out.println("3");
				Bullet.timeOfLastCreatedBullet = gameTime;
				Bullet b3_1 = new Bullet(player.machineGunXcoordinate, player.machineGunYcoordinate,levelGun,1);
				Bullet b3_2 = new Bullet(player.machineGunXcoordinate, player.machineGunYcoordinate,levelGun,2);
				Bullet b3_3 = new Bullet(player.machineGunXcoordinate, player.machineGunYcoordinate,levelGun,3);
				bulletsList.add(b3_1);
				bulletsList.add(b3_2);
				bulletsList.add(b3_3);
				break;
			case 4:
				System.out.println("4");
				Bullet.timeOfLastCreatedBullet = gameTime;
				Bullet b4_1 = new Bullet(player.machineGunXcoordinate, player.machineGunYcoordinate,levelGun,1);
				Bullet b4_2 = new Bullet(player.machineGunXcoordinate, player.machineGunYcoordinate,levelGun,2);
				Bullet b4_3 = new Bullet(player.machineGunXcoordinate, player.machineGunYcoordinate,levelGun,3);
				bulletsList.add(b4_1);
				bulletsList.add(b4_2);
				bulletsList.add(b4_3);
				break;

			default:
				break;
			}
			
		}
	}

	private void isEnemyShooting(long gameTime) {
		for (int i = 0; i < enemyHelicopterList.size(); i++) {
			if (enemyHelicopterList.get(i).EnemyShooting(gameTime)) {
				switch(level)
				{
				case 1:
					BulletEnemy b1 = new BulletEnemy(enemyHelicopterList.get(i).machineGunEnemyXcoordinate,
							enemyHelicopterList.get(i).machineGunEnemyYcoordinate, level,3);
					b1.timeOfLastCreatedBullet = gameTime;
					bulletsEnemyList.add(b1);
					
					break;
				case 2:
					BulletEnemy b2_1 = new BulletEnemy(enemyHelicopterList.get(i).machineGunEnemyXcoordinate,
							enemyHelicopterList.get(i).machineGunEnemyYcoordinate, level,1);
					
					BulletEnemy b2_2 = new BulletEnemy(enemyHelicopterList.get(i).machineGunEnemyXcoordinate,
							enemyHelicopterList.get(i).machineGunEnemyYcoordinate, level,2);
					b2_1.timeOfLastCreatedBullet = gameTime;
					bulletsEnemyList.add(b2_1);
					
					b2_2.timeOfLastCreatedBullet = gameTime;
					bulletsEnemyList.add(b2_2);
					break;
				case 3:
					BulletEnemy b3_1 = new BulletEnemy(enemyHelicopterList.get(i).machineGunEnemyXcoordinate,
							enemyHelicopterList.get(i).machineGunEnemyYcoordinate, level,3);
					BulletEnemy b3_2 = new BulletEnemy(enemyHelicopterList.get(i).machineGunEnemyXcoordinate,
							enemyHelicopterList.get(i).machineGunEnemyYcoordinate, level,1);
					BulletEnemy b3_3 = new BulletEnemy(enemyHelicopterList.get(i).machineGunEnemyXcoordinate,
							enemyHelicopterList.get(i).machineGunEnemyYcoordinate, level,2);
					b3_1.timeOfLastCreatedBullet = gameTime;
					bulletsEnemyList.add(b3_1);
					
					b3_2.timeOfLastCreatedBullet = gameTime;
					bulletsEnemyList.add(b3_2);
					b3_3.timeOfLastCreatedBullet = gameTime;
					bulletsEnemyList.add(b3_3);
					break;
				}
				
			}
		}

	}
	private void isBossShooting(long gameTime) {
		for (int i = 0; i < BossList.size(); i++) {
			if (BossList.get(i).bossShooting(gameTime)) {
				
				switch (level) {
				case 1:// 1=���45ͧ�� 2=ŧ45ͧ�� 3= ��鹵ç
					BulletBoss b1 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate, BossList.get(i).machineGunBossYcoordinate, level,1,false);
					
					BulletBoss b2 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate1,
							BossList.get(i).machineGunBossYcoordinate1, level,2,false);
					
					BulletBoss b3 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate2,
							BossList.get(i).machineGunBossYcoordinate2, level,3,false);
//					
					b1.timeOfLastCreatedBossBullet = gameTime;
					
					bulletsBossList.add(b1);
					bulletsBossList.add(b2);
					bulletsBossList.add(b3);
					break;
				case 2:
					BulletBoss b2_1 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate, BossList.get(i).machineGunBossYcoordinate, level,3,false);
					
					BulletBoss b2_2 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate2,
							BossList.get(i).machineGunBossYcoordinate2, level,1,false);
					
					BulletBoss b2_3 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate2,
							BossList.get(i).machineGunBossYcoordinate2, level,2,false);
					BulletBoss b2_4 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate1, BossList.get(i).machineGunBossYcoordinate1, level,3,false);
//					
					b2_1.timeOfLastCreatedBossBullet = gameTime;
					
					bulletsBossList.add(b2_1);
					bulletsBossList.add(b2_2);
					bulletsBossList.add(b2_3);
					bulletsBossList.add(b2_4);
					break;
				case 3:
					BulletBoss b3_1 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate,
							BossList.get(i).machineGunBossYcoordinate, level,1);
					BulletBoss b3_2 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate,
							BossList.get(i).machineGunBossYcoordinate, level,3);
					BulletBoss b3_3 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate,
							BossList.get(i).machineGunBossYcoordinate, level,2);
					BulletBoss b3_4 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate1,
							BossList.get(i).machineGunBossYcoordinate1, level,1);
					BulletBoss b3_5 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate2,
							BossList.get(i).machineGunBossYcoordinate2, level,1);
//					BulletBoss b3_6 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate,
//							BossList.get(i).machineGunBossYcoordinate, level,1);
					b3_1.timeOfLastCreatedBossBullet = gameTime;
					bulletsBossList.add(b3_1);
					bulletsBossList.add(b3_2);
					bulletsBossList.add(b3_3);
					bulletsBossList.add(b3_4);
					bulletsBossList.add(b3_5);
//					bulletsBossList.add(b3_6);
				
					break;
				case 4:
					BulletBoss b4_1 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate,
							BossList.get(i).machineGunBossYcoordinate, level,4);
					BulletBoss b4_2 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate,
							BossList.get(i).machineGunBossYcoordinate, level,5);
					BulletBoss b4_3 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate,
							BossList.get(i).machineGunBossYcoordinate, level,6);
					BulletBoss b4_4 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate,
							BossList.get(i).machineGunBossYcoordinate, level,7);
					BulletBoss b4_5 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate,
							BossList.get(i).machineGunBossYcoordinate, level,8);
					BulletBoss b4_6 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate,
							BossList.get(i).machineGunBossYcoordinate, level,9);
					BulletBoss b4_7 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate,
							BossList.get(i).machineGunBossYcoordinate, level,10);
//					BulletBoss b4_8 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate1,
//							BossList.get(i).machineGunBossYcoordinate1, level,5);
//					BulletBoss b4_9 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate1,
//							BossList.get(i).machineGunBossYcoordinate1, level,7);
					b4_1.timeOfLastCreatedBossBullet = gameTime;
					bulletsBossList.add(b4_1);
					bulletsBossList.add(b4_2);
					bulletsBossList.add(b4_3);
					bulletsBossList.add(b4_4);
					bulletsBossList.add(b4_5);
					bulletsBossList.add(b4_6);
					bulletsBossList.add(b4_7);
//					bulletsBossList.add(b4_8);
//					bulletsBossList.add(b4_9);
					break;

				default:
					break;
				}
				
			}
		}

	}

	/**
	 * Checks if the player is fired the rocket and creates it if he did. It also
	 * checks if player can fire the rocket.
	 * 
	 * @param gameTime
	 *            Game time.
	 */
	private void didPlayerFiredRocket(long gameTime) {
		if (player.isFiredRocket(gameTime)) {
			Rocket.timeOfLastCreatedRocket = gameTime;
			player.numberOfRockets--;

			Rocket r = new Rocket();
			r.Initialize(player.rocketHolderXcoordinate, player.rocketHolderYcoordinate);
			rocketsList.add(r);
		}
	}

	/**
	 * Creates a new enemy if it's time.
	 * 
	 * @param gameTime
	 *            Game time.
	 */
	// ���ҧ�ͷ��������
	private void createEnemyHelicopter(long gameTime) {
		if (gameTime - EnemyHelicopter.timeOfLastCreatedEnemy >= EnemyHelicopter.timeBetweenNewEnemies
				&& bossFight != true) {
			EnemyHelicopter eh = new EnemyHelicopter();
			double ran = Math.random()*8;
			random_enemy = (int)ran;
			System.out.println("Num Random = "+random_enemy);
			
			int xCoordinate = Framework.frameWidth;
			if(random_enemy == 1) {
				int yCoordinate = random.nextInt(Framework.frameHeight - EnemyHelicopter.enemyUpImg.getHeight());
				eh.Initialize(xCoordinate, yCoordinate, level, random_enemy);
			}
			else if(random_enemy == 2) {
				int yCoordinate = random.nextInt(Framework.frameHeight - EnemyHelicopter.enemyDefendImg.getHeight());
				eh.Initialize(xCoordinate, yCoordinate, level, random_enemy);
			}
			else if(random_enemy == 3) {
				int yCoordinate = random.nextInt(Framework.frameHeight - EnemyHelicopter.enemyHealImg.getHeight());
				eh.Initialize(xCoordinate, yCoordinate, level, random_enemy);
			}
			else {
				int yCoordinate = random.nextInt(Framework.frameHeight - EnemyHelicopter.enemyBodyImg.getHeight());
				eh.Initialize(xCoordinate, yCoordinate, level, random_enemy);
			}
			// Add created enemy to the list of enemies.
			enemyHelicopterList.add(eh);

			// Speed up enemy speed and aperence.
			EnemyHelicopter.speedUp();

			// Sets new time for last created enemy.
			EnemyHelicopter.timeOfLastCreatedEnemy = gameTime;
		}

		if (bossFight == true && bossCreate == true && enemyHelicopterList.size() == 0) {
			EnemyBoss.bossWarning.play((float)1,(float)0.5);
			if(level == 1) {
				
                EnemyBoss bossSt1 = new EnemyBoss(level);
                int xCoordinate = Framework.frameWidth;
                int yCoordinate = 300;
                bossSt1.Initialize(xCoordinate, yCoordinate);
                BossList.add(bossSt1);
                bossCreate = false;
            }
            else if(level == 2) {
                EnemyBoss bossSt2 = new EnemyBoss(level);
                int xCoordinate = Framework.frameWidth;
                int yCoordinate = 300;
                bossSt2.Initialize(xCoordinate, yCoordinate);
                BossList.add(bossSt2);
                bossCreate = false;
            }
            else if(level == 3) {
                EnemyBoss bossSt3 = new EnemyBoss(level);
                int xCoordinate = Framework.frameWidth;
                int yCoordinate = 300;
                bossSt3.Initialize(xCoordinate, yCoordinate);
                BossList.add(bossSt3);
                bossCreate = false;
            }
            else if(level == 4) {
                EnemyBoss bossSt4 = new EnemyBoss(level);
                int xCoordinate = Framework.frameWidth;
                int yCoordinate = 300;
                bossSt4.Initialize(xCoordinate, yCoordinate);
                BossList.add(bossSt4);
                bossCreate = false;
            }
		}

	}

	/**
	 * Updates all enemies. Move the helicopter and checks if he left the screen.
	 * Updates helicopter animations. Checks if enemy was destroyed. Checks if any
	 * enemy collision with player.
	 */
	private void updateEnemies(long gametime) {
		for (int i = 0; i < enemyHelicopterList.size(); i++) {
			EnemyHelicopter eh = enemyHelicopterList.get(i);

			eh.Update();
			Rectangle playerRectangel = new Rectangle(player.xCoordinate, player.yCoordinate,
					player.playerImg.getWidth(), player.playerImg.getHeight());
			
			Rectangle enemyRectangel = new Rectangle(eh.xCoordinate, eh.yCoordinate,
					EnemyHelicopter.enemyBodyImg.getWidth(), EnemyHelicopter.enemyBodyImg.getHeight());
			if (playerRectangel.intersects(enemyRectangel)) {
				player.health -= 40;			
				enemyHelicopterList.remove(i);
				enemyExplosion.explosion.play((float)1, (float) 0.2);
				for(int exNum = 0; exNum < 3; exNum++){
                    Animation expAnim = new Animation(explosionAnimImg, 134, 134, 12, 45, false, player.xCoordinate + exNum*60, player.yCoordinate - random.nextInt(100), exNum * 200 +random.nextInt(100));
                    explosionsList.add(expAnim);
                }
				break;
			}

			// Check health.
			if (eh.health <= 0) {
				
				// Increase the destroyed enemies counter.
				destroyedEnemies++;
				if(eh.GetItem()==1) {
					UpDamage += 5;
					bulletsList.clear();
					if(levelGun < 4)
						levelGun++;
					if(levelGun == 4)
					{
						this.gametime1 = (int) (gametime / Framework.milisecInNanosec / 1000);
						timeToPlay = (int) (this.gametime1 + 100);
					}
						
				}
				else if(eh.GetItem() == 3) {
					player.health+=90;
				}
				enemyExplosion.explosion.play((float)1, (float) 0.2);
				enemyHelicopterList.remove(i);
//				enemyExplosion exAni = new enemyExplosion(eh.xCoordinate, eh.yCoordinate, gametime);
				for(int exNum = 0; exNum < 3; exNum++){
                    Animation expAnim = new Animation(explosionAnimImg, 134, 134, 12, 45, false, eh.xCoordinate + exNum*60, eh.yCoordinate - random.nextInt(100), exNum * 200 +random.nextInt(100));
                    explosionsList.add(expAnim);
                }
				
				if (destroyedEnemies == 4*level && bossFight == false) {
					bossFight = true;
					
					// Helicopter was destroyed so we can move to next helicopter.
					continue;
				}	
			}
			if (eh.isLeftScreen()) {
				
				enemyHelicopterList.remove(i);
				runAwayEnemies++;
			}
		}
	}

	private void updateBoss(long gametime) {
		for (int i = 0; i < BossList.size(); i++) {
			EnemyBoss boss = BossList.get(i);

			boss.Update();

			// Is chrashed with player?
			Rectangle playerRectangel = new Rectangle(player.xCoordinate, player.yCoordinate,
					player.playerImg.getWidth(), player.playerImg.getHeight());
			Rectangle enemyRectangel = new Rectangle(boss.xCoordinate, boss.yCoordinate, EnemyBoss.BossSt1.getWidth(),
					EnemyBoss.BossSt1.getHeight());
			Rectangle enemyRectange2 = new Rectangle(boss.xCoordinate, boss.yCoordinate, EnemyBoss.BossSt2.getWidth(),
                    EnemyBoss.BossSt2.getHeight());
            Rectangle enemyRectange3 = new Rectangle(boss.xCoordinate, boss.yCoordinate, EnemyBoss.BossSt3.getWidth(),
                    EnemyBoss.BossSt3.getHeight());
            Rectangle enemyRectange4 = new Rectangle(boss.xCoordinate, boss.yCoordinate, EnemyBoss.BossSt4.getWidth(),
                    EnemyBoss.BossSt4.getHeight());
			if (playerRectangel.intersects(enemyRectangel)) {
				player.health = 0;

				// Remove helicopter from the list.
				BossList.remove(i);
				for(int exNum = 0; exNum < 3; exNum++){
                    Animation expAnim = new Animation(explosionAnimImg, 134, 134, 12, 45, false, boss.xCoordinate + exNum*60, boss.yCoordinate - random.nextInt(100), exNum * 200 +random.nextInt(100));
                    explosionsList.add(expAnim);
                }
				break;
			}

			// Check health.
			if (boss.health <= 0) {
				
				if(boss.lastBoss == true)
				{
					lastBossDefeat = true;
					BossList.remove(i);
					enemyExplosion.bossDead.play((float)1, (float) 0.08);
					for(int exNum = 0; exNum < 3; exNum++){
	                    Animation expAnim = new Animation(explosionAnimImg, 134, 134, 12, 45, false, boss.xCoordinate + exNum*60, boss.yCoordinate - random.nextInt(100), exNum * 1000 +random.nextInt(100));
	                    explosionsList.add(expAnim);
	                }
					break;
				}
				
				BossList.remove(i);
				enemyExplosion.bossDead.play((float)1, (float) 0.08);
				for(int exNum = 0; exNum < 3; exNum++){
                    Animation expAnim = new Animation(explosionAnimImg, 134, 134, 12, 45, false, boss.xCoordinate + exNum*60, boss.yCoordinate - random.nextInt(100), exNum * 500 +random.nextInt(100));
                    explosionsList.add(expAnim);
                }
				bossCreate = true;
				level++;
				if (enemyHelicopterList.size() == 0) {
                    if(level == 4) {
                        bossFight = true;
                    }
                    else
                        bossFight = false;
                }
				continue;
			}
		}
	}

	/**
	 * Update bullets. It moves bullets. Checks if the bullet is left the screen.
	 * Checks if any bullets is hit any enemy.
	 */
	private void updateBullets() {
		for (int i = 0; i < bulletsList.size(); i++) {
			Bullet bullet = bulletsList.get(i);

			// Move the bullet.
			bullet.Update();

			// Is left the screen?
			if (bullet.isItLeftScreen()) {
				bulletsList.remove(i);
				// Bullet have left the screen so we removed it from the list and now we can
				// continue to the next bullet.
				continue;
			}

			switch (levelGun) {
			case 1:
				Rectangle bulletRectangle = new Rectangle((int) bullet.xCoordinate, (int) bullet.yCoordinate,
						Bullet.bulletImg1.getWidth(), Bullet.bulletImg1.getHeight());
				for (int j = 0; j < enemyHelicopterList.size(); j++) {
					EnemyHelicopter eh = enemyHelicopterList.get(j);

					// Current enemy rectangle.
					Rectangle enemyRectangel = new Rectangle(eh.xCoordinate, eh.yCoordinate,
							EnemyHelicopter.enemyBodyImg.getWidth(), EnemyHelicopter.enemyBodyImg.getHeight());

					// Is current bullet over currnet enemy?
					if (bulletRectangle.intersects(enemyRectangel)) {
						// Bullet hit the enemy so we reduce his health.
						eh.health -= Bullet.damagePower+UpDamage;

						// Bullet was also destroyed so we remove it.
						bulletsList.remove(i);

						// That bullet hit enemy so we don't need to check other enemies.
						break;
					}
				}
				for (int j = 0; j < BossList.size(); j++) {
					EnemyBoss boss = BossList.get(j);

					// Current enemy rectangle.
					Rectangle enemyRectangel = new Rectangle(boss.xCoordinate, boss.yCoordinate,
							EnemyBoss.BossSt1.getWidth(), EnemyBoss.BossSt1.getHeight());
					Rectangle enemyRectange2 = new Rectangle(boss.xCoordinate, boss.yCoordinate,
	                        EnemyBoss.BossSt2.getWidth(), EnemyBoss.BossSt2.getHeight());
	                Rectangle enemyRectange3 = new Rectangle(boss.xCoordinate, boss.yCoordinate,
	                        EnemyBoss.BossSt3.getWidth(), EnemyBoss.BossSt3.getHeight());
	                Rectangle enemyRectange4 = new Rectangle(boss.xCoordinate, boss.yCoordinate,
	                        EnemyBoss.BossSt4.getWidth(), EnemyBoss.BossSt4.getHeight());

					// Is current bullet over currnet enemy?
	                if (bulletRectangle.intersects(enemyRectangel)||bulletRectangle.intersects(enemyRectange2)||
	                        bulletRectangle.intersects(enemyRectange3)||bulletRectangle.intersects(enemyRectange4)) {
						// Bullet hit the enemy so we reduce his health.
						boss.health -= Bullet.damagePower+UpDamage;
						EnemyBoss.hitFade = true;
						// Bullet was also destroyed so we remove it.
						bulletsList.remove(i);

						// That bullet hit enemy so we don't need to check other enemies.
						break;
					}
				}//
				
				break;
			case 2:
				Rectangle bulletRectangle2 = new Rectangle((int) bullet.xCoordinate, (int) bullet.yCoordinate,
						Bullet.bulletImg2.getWidth(), Bullet.bulletImg2.getHeight());
				for (int j = 0; j < enemyHelicopterList.size(); j++) {
					EnemyHelicopter eh = enemyHelicopterList.get(j);

					// Current enemy rectangle.
					Rectangle enemyRectangel = new Rectangle(eh.xCoordinate, eh.yCoordinate,
							EnemyHelicopter.enemyBodyImg.getWidth(), EnemyHelicopter.enemyBodyImg.getHeight());

					// Is current bullet over currnet enemy?
					if (bulletRectangle2.intersects(enemyRectangel)) {
						// Bullet hit the enemy so we reduce his health.
						eh.health -= Bullet.damagePower+UpDamage;

						// Bullet was also destroyed so we remove it.
						bulletsList.remove(i);

						// That bullet hit enemy so we don't need to check other enemies.
						break;
					}
				}
				for (int j = 0; j < BossList.size(); j++) {
					EnemyBoss boss = BossList.get(j);

					// Current enemy rectangle.
					Rectangle enemyRectangel = new Rectangle(boss.xCoordinate, boss.yCoordinate,
							EnemyBoss.BossSt1.getWidth(), EnemyBoss.BossSt1.getHeight());
					Rectangle enemyRectange2 = new Rectangle(boss.xCoordinate, boss.yCoordinate,
	                        EnemyBoss.BossSt2.getWidth(), EnemyBoss.BossSt2.getHeight());
	                Rectangle enemyRectange3 = new Rectangle(boss.xCoordinate, boss.yCoordinate,
	                        EnemyBoss.BossSt3.getWidth(), EnemyBoss.BossSt3.getHeight());
	                Rectangle enemyRectange4 = new Rectangle(boss.xCoordinate, boss.yCoordinate,
	                        EnemyBoss.BossSt4.getWidth(), EnemyBoss.BossSt4.getHeight());

					// Is current bullet over currnet enemy?
	                if (bulletRectangle2.intersects(enemyRectangel)||bulletRectangle2.intersects(enemyRectange2)||
	                        bulletRectangle2.intersects(enemyRectange3)||bulletRectangle2.intersects(enemyRectange4)) {
						// Bullet hit the enemy so we reduce his health.
						boss.health -= Bullet.damagePower+UpDamage;
						EnemyBoss.hitFade = true;
						// Bullet was also destroyed so we remove it.
						bulletsList.remove(i);

						// That bullet hit enemy so we don't need to check other enemies.
						break;
					}
				}//
				break;
			case 3:
				Rectangle bulletRectangle3 = new Rectangle((int) bullet.xCoordinate, (int) bullet.yCoordinate,
						Bullet.bulletImg3.getWidth(), Bullet.bulletImg3.getHeight());
				for (int j = 0; j < enemyHelicopterList.size(); j++) {
					EnemyHelicopter eh = enemyHelicopterList.get(j);

					// Current enemy rectangle.
					Rectangle enemyRectangel = new Rectangle(eh.xCoordinate, eh.yCoordinate,
							EnemyHelicopter.enemyBodyImg.getWidth(), EnemyHelicopter.enemyBodyImg.getHeight());

					// Is current bullet over currnet enemy?
					if (bulletRectangle3.intersects(enemyRectangel)) {
						// Bullet hit the enemy so we reduce his health.
						eh.health -= Bullet.damagePower+UpDamage;

						// Bullet was also destroyed so we remove it.
						bulletsList.remove(i);

						// That bullet hit enemy so we don't need to check other enemies.
						break;
					}
				}
				for (int j = 0; j < BossList.size(); j++) {
					EnemyBoss boss = BossList.get(j);

					// Current enemy rectangle.
					Rectangle enemyRectangel = new Rectangle(boss.xCoordinate, boss.yCoordinate,
							EnemyBoss.BossSt1.getWidth(), EnemyBoss.BossSt1.getHeight());
					Rectangle enemyRectange2 = new Rectangle(boss.xCoordinate, boss.yCoordinate,
	                        EnemyBoss.BossSt2.getWidth(), EnemyBoss.BossSt2.getHeight());
	                Rectangle enemyRectange3 = new Rectangle(boss.xCoordinate, boss.yCoordinate,
	                        EnemyBoss.BossSt3.getWidth(), EnemyBoss.BossSt3.getHeight());
	                Rectangle enemyRectange4 = new Rectangle(boss.xCoordinate, boss.yCoordinate,
	                        EnemyBoss.BossSt4.getWidth(), EnemyBoss.BossSt4.getHeight());

					// Is current bullet over currnet enemy?
	                if (bulletRectangle3.intersects(enemyRectangel)||bulletRectangle3.intersects(enemyRectange2)||
	                        bulletRectangle3.intersects(enemyRectange3)||bulletRectangle3.intersects(enemyRectange4)) {
						// Bullet hit the enemy so we reduce his health.
						boss.health -= Bullet.damagePower+UpDamage;
						EnemyBoss.hitFade = true;
						// Bullet was also destroyed so we remove it.
						bulletsList.remove(i);

						// That bullet hit enemy so we don't need to check other enemies.
						break;
					}
				}//
				break;
			case 4:
				Rectangle bulletRectangle4 = new Rectangle((int) bullet.xCoordinate, (int) bullet.yCoordinate,
						Bullet.bulletImg4.getWidth(), Bullet.bulletImg4.getHeight());
				for (int j = 0; j < enemyHelicopterList.size(); j++) {
					EnemyHelicopter eh = enemyHelicopterList.get(j);

					// Current enemy rectangle.
					Rectangle enemyRectangel = new Rectangle(eh.xCoordinate, eh.yCoordinate,
							EnemyHelicopter.enemyBodyImg.getWidth(), EnemyHelicopter.enemyBodyImg.getHeight());

					// Is current bullet over currnet enemy?
					if (bulletRectangle4.intersects(enemyRectangel)) {
						// Bullet hit the enemy so we reduce his health.
						eh.health -= Bullet.damagePower+UpDamage;

						// Bullet was also destroyed so we remove it.
						bulletsList.remove(i);

						// That bullet hit enemy so we don't need to check other enemies.
						break;
					}
				}
				for (int j = 0; j < BossList.size(); j++) {
					EnemyBoss boss = BossList.get(j);

					// Current enemy rectangle.
					Rectangle enemyRectangel = new Rectangle(boss.xCoordinate, boss.yCoordinate,
							EnemyBoss.BossSt1.getWidth(), EnemyBoss.BossSt1.getHeight());
					Rectangle enemyRectange2 = new Rectangle(boss.xCoordinate, boss.yCoordinate,
	                        EnemyBoss.BossSt2.getWidth(), EnemyBoss.BossSt2.getHeight());
	                Rectangle enemyRectange3 = new Rectangle(boss.xCoordinate, boss.yCoordinate,
	                        EnemyBoss.BossSt3.getWidth(), EnemyBoss.BossSt3.getHeight());
	                Rectangle enemyRectange4 = new Rectangle(boss.xCoordinate, boss.yCoordinate,
	                        EnemyBoss.BossSt4.getWidth(), EnemyBoss.BossSt4.getHeight());

					// Is current bullet over currnet enemy?
	                if (bulletRectangle4.intersects(enemyRectangel)||bulletRectangle4.intersects(enemyRectange2)||
	                        bulletRectangle4.intersects(enemyRectange3)||bulletRectangle4.intersects(enemyRectange4)) {
						// Bullet hit the enemy so we reduce his health.
						boss.health -= Bullet.damagePower+UpDamage;
						EnemyBoss.hitFade = true;
						// Bullet was also destroyed so we remove it.
						bulletsList.remove(i);

						// That bullet hit enemy so we don't need to check other enemies.
						break;
					}
				}//
				break;
			default:
				break;
			}
		}
	}

	private void updateBulletsEnemy() {
		for (int i = 0; i < bulletsEnemyList.size(); i++) {
			BulletEnemy bulletEnemy = bulletsEnemyList.get(i);

			// Move the bullet.
			bulletEnemy.Update();

			// Is left the screen?
			if (bulletEnemy.isItLeftScreen()) {
				bulletsEnemyList.remove(i);
				// Bullet have left the screen so we removed it from the list and now we can
				// continue to the next bullet.
				continue;
			}

			// Did hit any enemy?
			// Rectangle of the bullet image.
			Rectangle bulletRectangle = new Rectangle((int) bulletEnemy.xCoordinate, (int) bulletEnemy.yCoordinate,
					bulletEnemy.bulletImg.getWidth(), bulletEnemy.bulletImg.getHeight());
			// Go trough all enemis.
			PlayerHelicopter eh = player;

			// Current enemy rectangle.
			Rectangle playerRectangel = new Rectangle(eh.xCoordinate, eh.yCoordinate, eh.playerImg.getWidth(),
					eh.playerImg.getHeight());

			// Is current bullet over currnet enemy?
			if (bulletRectangle.intersects(playerRectangel)) {
				// Bullet hit the enemy so we reduce his health.
				eh.health -= Bullet.damagePower;

				// Bullet was also destroyed so we remove it.
				bulletsEnemyList.remove(i);

				// That bullet hit enemy so we don't need to check other enemies.
				break;
			}
		}
	}
	
	private void updateBulletsBoss() {
		for (int i = 0; i < bulletsBossList.size(); i++) {
			BulletBoss bulletBoss = bulletsBossList.get(i);

			bulletBoss.Update(player.xCoordinate, player.yCoordinate);


			Rectangle bulletRectangle = new Rectangle((int) bulletBoss.xCoordinate, (int) bulletBoss.yCoordinate,bulletBoss.bulletImg.getWidth(), bulletBoss.bulletImg.getHeight());
			
			// Go trough all enemis.
			PlayerHelicopter eh = player;

			// Current enemy rectangle.
			Rectangle playerRectangel = new Rectangle(eh.xCoordinate, eh.yCoordinate, eh.playerImg.getWidth(),
					eh.playerImg.getHeight());

			 
			if (bulletRectangle.intersects(playerRectangel)) {
				// Bullet hit the enemy so we reduce his health.
				eh.health -= Bullet.damagePower;

				// Bullet was also destroyed so we remove it.
				bulletsBossList.remove(i);

				// That bullet hit enemy so we don't need to check other enemies.
				break;
			}
		}
	}

	/**
	 * Update rockets. It moves rocket and add smoke behind it. Checks if the rocket
	 * is left the screen. Checks if any rocket is hit any enemy.
	 * 
	 * @param gameTime
	 *            Game time.
	 */
	private void updateRockets(long gameTime) {
		for (int i = 0; i < rocketsList.size(); i++) {
			Rocket rocket = rocketsList.get(i);

			// Moves the rocket.
			rocket.Update();

			// Checks if it is left the screen.
			if (rocket.isItLeftScreen()) {
				rocketsList.remove(i);
				// Rocket left the screen so we removed it from the list and now we can continue
				// to the next rocket.
				continue;
			}

			// Checks if current rocket hit any enemy.
			if (checkIfRocketHitEnemy(rocket))
				// Rocket was also destroyed so we remove it.
				rocketsList.remove(i);
		}
	}

	/**
	 * Checks if the given rocket is hit any of enemy helicopters.
	 * 
	 * @param rocket
	 *            Rocket to check.
	 * @return True if it hit any of enemy helicopters, false otherwise.
	 */
	private boolean checkIfRocketHitEnemy(Rocket rocket) {
		boolean didItHitEnemy = false;

		// Current rocket rectangle. // I inserted number 2 insted of
		// rocketImg.getWidth() because I wanted that rocket
		// is over helicopter when collision is detected, because actual image of
		// helicopter isn't a rectangle shape. (We could calculate/make 3 areas where
		// helicopter can be hit and checks these areas, but this is easier.)
		Rectangle rocketRectangle = new Rectangle(rocket.xCoordinate, rocket.yCoordinate, Rocket.rocketImg.getWidth(),
				Rocket.rocketImg.getHeight());

		// Go trough all enemis.
		for (int j = 0; j < enemyHelicopterList.size(); j++) {
			EnemyHelicopter eh = enemyHelicopterList.get(j);

			// Current enemy rectangle.
			Rectangle enemyRectangel = new Rectangle(eh.xCoordinate, eh.yCoordinate,
					EnemyHelicopter.enemyBodyImg.getWidth(), EnemyHelicopter.enemyBodyImg.getHeight());

			// Is current rocket over currnet enemy?
			if (rocketRectangle.intersects(enemyRectangel)) {
				didItHitEnemy = true;

				// Rocket hit the enemy so we reduce his health.
				eh.health -= Rocket.damagePower;

				// Rocket hit enemy so we don't need to check other enemies.
				break;
			}
		}
		for (int j = 0; j < BossList.size(); j++) {
			EnemyBoss eh = BossList.get(j);

			// Current enemy rectangle.
			Rectangle bossRectangel = new Rectangle(eh.xCoordinate, eh.yCoordinate,
					EnemyBoss.BossSt1.getWidth(), EnemyBoss.BossSt1.getHeight());

			// Is current rocket over currnet enemy?
			if (rocketRectangle.intersects(bossRectangel)) {
				didItHitEnemy = true;

				// Rocket hit the enemy so we reduce his health.
				eh.health -= Rocket.damagePower;

				// Rocket hit enemy so we don't need to check other enemies.
				break;
			}
		}

		return didItHitEnemy;
	}

	/**
	 * Updates smoke of all the rockets. If the life time of the smoke is over then
	 * we delete it from list. It also changes a transparency of a smoke image, so
	 * that smoke slowly disappear.
	 * 
	 * @param gameTime
	 *            Game time.
	 */
	private void updateRocketSmoke(long gameTime) {
		for (int i = 0; i < rocketSmokeList.size(); i++) {
			RocketSmoke rs = rocketSmokeList.get(i);

			// Is it time to remove the smoke.
			if (rs.didSmokeDisapper(gameTime))
				rocketSmokeList.remove(i);

			// Set new transparency of rocket smoke image.
			rs.updateTransparency(gameTime);
		}
	}

	/**
	 * Updates all the animations of an explosion and remove the animation when is
	 * over.
	 */
	private void updateExplosions() {
		for (int i = 0; i < explosionsList.size(); i++) {
			// If the animation is over we remove it from the list.
			if (!explosionsList.get(i).active)
				explosionsList.remove(i);
		}
	}

}



//test2602

package skyline;

import org.newdawn.slick.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;;
//123
/**
 * Actual game.
 * 
 * @author www.gametutorial.net
 */

public class Game {

	// Use this to generate a random number.
	private Random random;


	private boolean bossFight = false;
	private boolean bossCreate = true;
	private int random_enemy;
	private int UpDamage;
	
	private Music gameBg;
	private PlayerHelicopter player;
	private int level=1;
	private int levelGun=1;
	private long gametime1;
	private long timeToPlay;
	private ArrayList<EnemyHelicopter> enemyHelicopterList = new ArrayList<EnemyHelicopter>();
	private ArrayList<EnemyBoss> BossList = new ArrayList<EnemyBoss>();

	// Explosions
	private ArrayList<Animation> explosionsList;
	private BufferedImage explosionAnimImg;

	// List of all the machine gun bullets.
	private ArrayList<Bullet> bulletsList;
	private ArrayList<BulletEnemy> bulletsEnemyList;
	private ArrayList<BulletBoss> bulletsBossList;

	// List of all the rockets.
	private ArrayList<Rocket> rocketsList;
	// List of all the rockets smoke.
	private ArrayList<RocketSmoke> rocketSmokeList;


	// Images for white spot on the sky.
	
	private BufferedImage gameBackground;
	private BufferedImage groundImg;
	// Objects of moving images.

	private MovingBackground backGoundMoving;
	private MovingBackground groundMoving;

	private Font font;
	
	private boolean lastBossDefeat = false;
	// Statistics (destroyed enemies, run away enemies)
	private int runAwayEnemies;
	private int destroyedEnemies = 11;

	public Game() {
		Framework.gameState = Framework.GameState.GAME_CONTENT_LOADING;

		Thread threadForInitGame = new Thread() {
			@Override
			public void run() {
				// Sets variables and objects for the game.
				Initialize();
				// Load game files (images, sounds, ...)
				LoadContent();

				Framework.gameState = Framework.GameState.PLAYING;
			}
		};
		threadForInitGame.start();
	}

	/**
	 * Set variables and objects for the game.
	 */
	private void Initialize() {
		random = new Random();

		player = new PlayerHelicopter(Framework.frameWidth / 4, Framework.frameHeight / 4);

		enemyHelicopterList = new ArrayList<EnemyHelicopter>();
		BossList = new ArrayList<EnemyBoss>();

		explosionsList = new ArrayList<Animation>();

		bulletsList = new ArrayList<Bullet>();
		bulletsEnemyList = new ArrayList<BulletEnemy>();
		bulletsBossList = new ArrayList<BulletBoss>();
		rocketsList = new ArrayList<Rocket>();
		rocketSmokeList = new ArrayList<RocketSmoke>();

		backGoundMoving = new MovingBackground();
		groundMoving = new MovingBackground();
		
		try {
			gameBg = new Music("skyline/resources/audio/The Last Encounter (90s RPG Version) Short Loop.wav");
			enemyExplosion.explosion = new Sound("skyline/resources/audio/Explosion+7.wav");
			EnemyBoss.bossWarning = new Sound("skyline/resources/audio/Boss Battle Warning!.wav");
			enemyExplosion.bossDead = new Sound("skyline/resources/audio/Explosion+3.wav");
		} catch (SlickException e) {
			e.printStackTrace();
		}
        gameBg.loop();
        gameBg.setVolume((float) 0.1);
		font = new Font("monospaced", Font.BOLD, 18);

		runAwayEnemies = 0;
		destroyedEnemies = 0;
	}

	/**
	 * Load game files (images).
	 */
	private void LoadContent() {
		try {
			// Images of environment
			
			URL groundImgUrl = this.getClass().getResource("/skyline/resources/images/ground.png");
			groundImg = ImageIO.read(groundImgUrl);
			URL gameBackgroundUrl = this.getClass().getResource("/skyline/resources/images/skyBG.jpg");
			gameBackground = ImageIO.read(gameBackgroundUrl);

			// Load images for enemy helicopter
			URL enemyBodyImgImgUrl = this.getClass().getResource("/skyline/resources/images/enemyBot.png");
			EnemyHelicopter.enemyBodyImg = ImageIO.read(enemyBodyImgImgUrl);
			URL enemyDefend = this.getClass().getResource("/skyline/resources/images/enemyDefendBot.png");
			EnemyHelicopter.enemyDefendImg = ImageIO.read(enemyDefend);
			URL enemyUp = this.getClass().getResource("/skyline/resources/images/enemyUp.jpg");
			EnemyHelicopter.enemyUpImg = ImageIO.read(enemyUp);
			URL enemyHeal = this.getClass().getResource("/skyline/resources/images/enemyHeal.png");
			EnemyHelicopter.enemyHealImg = ImageIO.read(enemyHeal);
			URL enemyExploImgUrl = this.getClass().getResource("/skyline/resources/images/explosion_anim.png");
			explosionAnimImg = ImageIO.read(enemyExploImgUrl);
			
			URL bossSt1 = this.getClass().getResource("/skyline/resources/images/boss_st1.png");
			EnemyBoss.BossSt1 = ImageIO.read(bossSt1);
			
			URL bossSt1hit = this.getClass().getResource("/skyline/resources/images/boss_st1hit.png");
			EnemyBoss.BossSt1hit = ImageIO.read(bossSt1hit);
			
			URL bossSt2 = this.getClass().getResource("/skyline/resources/images/boss_st2.png");
            EnemyBoss.BossSt2 = ImageIO.read(bossSt2);
            URL bossSt2hit = this.getClass().getResource("/skyline/resources/images/boss_st2hit.png");
			EnemyBoss.BossSt2hit = ImageIO.read(bossSt2hit);
            URL bossSt3 = this.getClass().getResource("/skyline/resources/images/boss_st3.png");
            EnemyBoss.BossSt3 = ImageIO.read(bossSt3);
            URL bossSt3hit = this.getClass().getResource("/skyline/resources/images/boss_st3hit.png");
			EnemyBoss.BossSt3hit = ImageIO.read(bossSt3hit);
            URL bossSt4 = this.getClass().getResource("/skyline/resources/images/boss_st4.png");
            EnemyBoss.BossSt4 = ImageIO.read(bossSt4);
            URL bossSt4hit = this.getClass().getResource("/skyline/resources/images/boss_st4hit.png");
			EnemyBoss.BossSt4hit = ImageIO.read(bossSt4hit);
            
            
			// Images of rocket and its smoke.
			URL rocketImgUrl = this.getClass().getResource("/skyline/resources/images/beam.png");
			Rocket.rocketImg = ImageIO.read(rocketImgUrl);
			URL rocketSmokeImgUrl = this.getClass().getResource("/skyline/resources/images/beamplasma.png");
			RocketSmoke.smokeImg = ImageIO.read(rocketSmokeImgUrl);
			

			// Helicopter machine gun bullet.
			URL bulletImgUrl = this.getClass().getResource("/skyline/resources/images/bulletPlayer.png");
			Bullet.bulletImg1 = ImageIO.read(bulletImgUrl);
			
			URL bulletImgUr2 = this.getClass().getResource("/skyline/resources/images/bulletPlayer2.png");
			Bullet.bulletImg2 = ImageIO.read(bulletImgUr2);
			
			URL bulletImgUr3 = this.getClass().getResource("/skyline/resources/images/bulletPlayer3.png");
			Bullet.bulletImg3 = ImageIO.read(bulletImgUr3);
			
			URL bulletImgUr4 = this.getClass().getResource("/skyline/resources/images/bulletPlayer4.png");
			Bullet.bulletImg4 = ImageIO.read(bulletImgUr4);
			
			URL bulletImgUrl1 = this.getClass().getResource("/skyline/resources/images/bulletEnemy.png");
			BulletEnemy.bulletImg = ImageIO.read(bulletImgUrl1);
			URL bulletImgUrl2 = this.getClass().getResource("/skyline/resources/images/bulletEnemy.png");
			BulletBoss.bulletImg = ImageIO.read(bulletImgUrl1);
		} catch (IOException ex) {
			Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
		}

		// Now that we have images we initialize moving images.
//		cloudLayer1Moving.Initialize(cloudLayer1Img, -6, 0);
//		cloudLayer2Moving.Initialize(cloudLayer2Img, -2, 0);
		backGoundMoving.Initialize(gameBackground, -1,0);
		groundMoving.Initialize(groundImg, -1.2, Framework.frameHeight - groundImg.getHeight());
	}

	/**
	 * Restart game - reset some variables.
	 */
	public void RestartGame() {
		player.Reset(Framework.frameWidth / 4, Framework.frameHeight / 4);

		EnemyHelicopter.restartEnemy();
		EnemyBoss.lastBoss = false;
		lastBossDefeat = false;
		Bullet.timeOfLastCreatedBullet = 0;
		BulletBoss.timeOfLastCreatedBossBullet = 0;
		BulletEnemy.timeOfLastCreatedBullet = 0;
		Rocket.timeOfLastCreatedRocket = 0;
		level = 1;
		bossFight = false;
		bossCreate = true;
		// Empty all the lists.
		enemyHelicopterList.clear();
		BossList.clear();
		bulletsList.clear();
		bulletsEnemyList.clear();
		bulletsBossList.clear();
		rocketsList.clear();
		rocketSmokeList.clear();
		explosionsList.clear();

		// Statistics
		runAwayEnemies = 0;
		destroyedEnemies = 0;
	}

	/**
	 * Update game logic.
	 * 
	 * @param gameTime
	 *            The elapsed game time in nanoseconds.
	 * @param mousePosition
	 *            current mouse position.
	 */
	public void UpdateGame(long gameTime) {
		/* Player */
		// When player is destroyed and all explosions are finished showing we change
		// game status.
		if (!isPlayerAlive()) {
			Framework.gameState = Framework.GameState.GAMEOVER;
			gameBg.stop();
			return; // If player is destroyed, we don't need to do thing below.
		}
		if (isGameClear()) {
			gameBg.stop();
			Framework.gameState = Framework.GameState.GAME_CLEAR;
			return; // If player is destroyed, we don't need to do thing below.
		}
		 
		// If player is alive we update him.
		if (isPlayerAlive()) {
			isPlayerShooting(gameTime);
			isEnemyShooting(gameTime);
			isBossShooting(gameTime);
			didPlayerFiredRocket(gameTime);
			player.isMoving();
			player.Update(levelGun);
		}

		
		if(levelGun == 4)
		{
			if(gametime1 - timeToPlay == 0)
			{
				levelGun = 3;
			}
			else
				gametime1++;
		}
		
		
		/* Bullets */
		updateBullets();
		updateBulletsEnemy();
		updateBulletsBoss();

		/* Rockets */
		updateRockets(gameTime); // It also checks for collisions (if any of the rockets hit any of the enemy
									// helicopter).
		updateRocketSmoke(gameTime);

		/* Enemies */
		createEnemyHelicopter(gameTime);
		updateEnemies(gameTime);
		updateBoss(gameTime);

		/* Explosions */
		updateExplosions();
	}

	/**
	 * Draw the game to the screen.
	 * 
	 * @param g2d
	 *            Graphics2D
	 * @param mousePosition
	 *            current mouse position.
	 */
	public void Draw(Graphics2D g2d, long gameTime) {
		// Image for background sky color.
		g2d.drawImage(gameBackground, 0, 0, Framework.frameWidth, Framework.frameHeight, null);

		// Moving images.
		backGoundMoving.Draw(g2d);
		groundMoving.Draw(g2d);
//		cloudLayer2Moving.Draw(g2d);

		if (isPlayerAlive())
			player.Draw(g2d);

		// Draws all the enemies.
		for (int i = 0; i < enemyHelicopterList.size(); i++) {
			enemyHelicopterList.get(i).Draw(g2d);
		}

		for (int i = 0; i < BossList.size(); i++) {
			BossList.get(i).Draw(g2d);
		}

		// Draws all the bullets.
		for (int i = 0; i < bulletsList.size(); i++) {
			bulletsList.get(i).Draw(g2d);
		}
		
		for (int i = 0; i < bulletsEnemyList.size(); i++) {

			bulletsEnemyList.get(i).Draw(g2d);
		}
		
		for (int i = 0; i < bulletsBossList.size(); i++) {
			
			bulletsBossList.get(i).Draw(g2d);
		}
		// Draws all the rockets.
		for (int i = 0; i < rocketsList.size(); i++) {
			rocketsList.get(i).Draw(g2d);
		}
		// Draws smoke of all the rockets.
		for (int i = 0; i < rocketSmokeList.size(); i++) {
			rocketSmokeList.get(i).Draw(g2d);
		}

		// Draw all explosions.
		for (int i = 0; i < explosionsList.size(); i++) {
			explosionsList.get(i).Draw(g2d);
		}

		// Draw statistics
		g2d.setFont(font);
		g2d.setColor(Color.white);

		g2d.drawString(formatTime(gameTime), Framework.frameWidth / 2 - 45, 21);
		g2d.drawString("DESTROYED: " + destroyedEnemies, 10, 21);
		g2d.drawString("RUNAWAY: " + runAwayEnemies, 10, 41);
		g2d.drawString("ROCKETS: " + player.numberOfRockets, 10, 81);
		g2d.drawString("AMMO: " + player.numberOfAmmo, 10, 101);
		g2d.drawString("Boss: " + bossFight, 10, 115);
		g2d.drawString("heath: " + player.health, 10, 130);

		// Moving images. We draw this cloud in front of the helicopter.
//		cloudLayer1Moving.Draw(g2d);

		// Mouse cursor

	}

	/**
	 * Draws some game statistics when game is over.
	 * 
	 * @param g2d
	 *            Graphics2D
	 * @param gameTime
	 *            Elapsed game time.
	 */
	public void DrawStatistic(Graphics2D g2d, long gameTime) {
		g2d.drawString("Time: " + formatTime(gameTime), Framework.frameWidth / 2 - 50, Framework.frameHeight / 3 + 80);
		g2d.drawString("Rockets left: " + player.numberOfRockets, Framework.frameWidth / 2 - 55,
				Framework.frameHeight / 3 + 105);
		g2d.drawString("Ammo left: " + player.numberOfAmmo, Framework.frameWidth / 2 - 55,
				Framework.frameHeight / 3 + 125);
		g2d.drawString("Destroyed enemies: " + destroyedEnemies, Framework.frameWidth / 2 - 65,
				Framework.frameHeight / 3 + 150);
		g2d.drawString("Runaway enemies: " + runAwayEnemies, Framework.frameWidth / 2 - 65,
				Framework.frameHeight / 3 + 170);
		g2d.setFont(font);
		g2d.drawString("Statistics: ", Framework.frameWidth / 2 - 75, Framework.frameHeight / 3 + 60);
	}

	/**
	 * Format given time into 00:00 format.
	 * 
	 * @param time
	 *            Time that is in nanoseconds.
	 * @return Time in 00:00 format.
	 */
	private static String formatTime(long time) {
		// Given time in seconds.
		int sec = (int) (time / Framework.milisecInNanosec / 1000);

		// Given time in minutes and seconds.
		int min = sec / 60;
		sec = sec - (min * 60);

		String minString, secString;

		if (min <= 9)
			minString = "0" + Integer.toString(min);
		else
			minString = "" + Integer.toString(min);

		if (sec <= 9)
			secString = "0" + Integer.toString(sec);
		else
			secString = "" + Integer.toString(sec);

		return minString + ":" + secString;
	}

	/*
	 * 
	 * Methods for updating the game.
	 * 
	 */

	/**
	 * Check if player is alive. If not, set game over status.
	 * 
	 * @return True if player is alive, false otherwise.
	 */
	private boolean isPlayerAlive() {
		if (player.health <= 0)
			return false;

		return true;
	}
	private boolean isGameClear() {
		if (lastBossDefeat && explosionsList.size() == 0)
			return true;

		return false;
	}

	/**
	 * Checks if the player is shooting with the machine gun and creates bullets if
	 * he shooting.
	 * 
	 * @param gameTime
	 *            Game time.
	 */
	private void isPlayerShooting(long gameTime) {
		if (player.isShooting(gameTime)) {
			switch (levelGun) {
			case 1:
				Bullet.timeOfLastCreatedBullet = gameTime;
				Bullet b = new Bullet(player.machineGunXcoordinate, player.machineGunYcoordinate,levelGun,3);
				bulletsList.add(b);
				break;
			case 2:
				System.out.println("2");
				Bullet.timeOfLastCreatedBullet = gameTime;
				Bullet b2_1 = new Bullet(player.machineGunXcoordinate, player.machineGunYcoordinate,levelGun,3);
				bulletsList.add(b2_1);
				break;
			case 3:
				System.out.println("3");
				Bullet.timeOfLastCreatedBullet = gameTime;
				Bullet b3_1 = new Bullet(player.machineGunXcoordinate, player.machineGunYcoordinate,levelGun,1);
				Bullet b3_2 = new Bullet(player.machineGunXcoordinate, player.machineGunYcoordinate,levelGun,2);
				Bullet b3_3 = new Bullet(player.machineGunXcoordinate, player.machineGunYcoordinate,levelGun,3);
				bulletsList.add(b3_1);
				bulletsList.add(b3_2);
				bulletsList.add(b3_3);
				break;
			case 4:
				System.out.println("4");
				Bullet.timeOfLastCreatedBullet = gameTime;
				Bullet b4_1 = new Bullet(player.machineGunXcoordinate, player.machineGunYcoordinate,levelGun,1);
				Bullet b4_2 = new Bullet(player.machineGunXcoordinate, player.machineGunYcoordinate,levelGun,2);
				Bullet b4_3 = new Bullet(player.machineGunXcoordinate, player.machineGunYcoordinate,levelGun,3);
				bulletsList.add(b4_1);
				bulletsList.add(b4_2);
				bulletsList.add(b4_3);
				break;

			default:
				break;
			}
			
		}
	}

	private void isEnemyShooting(long gameTime) {
		for (int i = 0; i < enemyHelicopterList.size(); i++) {
			if (enemyHelicopterList.get(i).EnemyShooting(gameTime)) {
				switch(level)
				{
				case 1:
					BulletEnemy b1 = new BulletEnemy(enemyHelicopterList.get(i).machineGunEnemyXcoordinate,
							enemyHelicopterList.get(i).machineGunEnemyYcoordinate, level,3);
					b1.timeOfLastCreatedBullet = gameTime;
					bulletsEnemyList.add(b1);
					
					break;
				case 2:
					BulletEnemy b2_1 = new BulletEnemy(enemyHelicopterList.get(i).machineGunEnemyXcoordinate,
							enemyHelicopterList.get(i).machineGunEnemyYcoordinate, level,1);
					
					BulletEnemy b2_2 = new BulletEnemy(enemyHelicopterList.get(i).machineGunEnemyXcoordinate,
							enemyHelicopterList.get(i).machineGunEnemyYcoordinate, level,2);
					b2_1.timeOfLastCreatedBullet = gameTime;
					bulletsEnemyList.add(b2_1);
					
					b2_2.timeOfLastCreatedBullet = gameTime;
					bulletsEnemyList.add(b2_2);
					break;
				case 3:
					BulletEnemy b3_1 = new BulletEnemy(enemyHelicopterList.get(i).machineGunEnemyXcoordinate,
							enemyHelicopterList.get(i).machineGunEnemyYcoordinate, level,3);
					BulletEnemy b3_2 = new BulletEnemy(enemyHelicopterList.get(i).machineGunEnemyXcoordinate,
							enemyHelicopterList.get(i).machineGunEnemyYcoordinate, level,1);
					BulletEnemy b3_3 = new BulletEnemy(enemyHelicopterList.get(i).machineGunEnemyXcoordinate,
							enemyHelicopterList.get(i).machineGunEnemyYcoordinate, level,2);
					b3_1.timeOfLastCreatedBullet = gameTime;
					bulletsEnemyList.add(b3_1);
					
					b3_2.timeOfLastCreatedBullet = gameTime;
					bulletsEnemyList.add(b3_2);
					b3_3.timeOfLastCreatedBullet = gameTime;
					bulletsEnemyList.add(b3_3);
					break;
				}
				
			}
		}

	}
	private void isBossShooting(long gameTime) {
		for (int i = 0; i < BossList.size(); i++) {
			if (BossList.get(i).bossShooting(gameTime)) {
				
				switch (level) {
				case 1:// 1=���45ͧ�� 2=ŧ45ͧ�� 3= ��鹵ç
					BulletBoss b1 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate, BossList.get(i).machineGunBossYcoordinate, level,1,false);
					
					BulletBoss b2 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate1,
							BossList.get(i).machineGunBossYcoordinate1, level,2,false);
					
					BulletBoss b3 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate2,
							BossList.get(i).machineGunBossYcoordinate2, level,3,false);
//					
					b1.timeOfLastCreatedBossBullet = gameTime;
					
					bulletsBossList.add(b1);
					bulletsBossList.add(b2);
					bulletsBossList.add(b3);
					break;
				case 2:
					BulletBoss b2_1 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate, BossList.get(i).machineGunBossYcoordinate, level,3,false);
					
					BulletBoss b2_2 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate2,
							BossList.get(i).machineGunBossYcoordinate2, level,1,false);
					
					BulletBoss b2_3 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate2,
							BossList.get(i).machineGunBossYcoordinate2, level,2,false);
					BulletBoss b2_4 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate1, BossList.get(i).machineGunBossYcoordinate1, level,3,false);
//					
					b2_1.timeOfLastCreatedBossBullet = gameTime;
					
					bulletsBossList.add(b2_1);
					bulletsBossList.add(b2_2);
					bulletsBossList.add(b2_3);
					bulletsBossList.add(b2_4);
					break;
				case 3:
					BulletBoss b3_1 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate,
							BossList.get(i).machineGunBossYcoordinate, level,1);
					BulletBoss b3_2 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate,
							BossList.get(i).machineGunBossYcoordinate, level,3);
					BulletBoss b3_3 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate,
							BossList.get(i).machineGunBossYcoordinate, level,2);
					BulletBoss b3_4 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate1,
							BossList.get(i).machineGunBossYcoordinate1, level,1);
					BulletBoss b3_5 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate2,
							BossList.get(i).machineGunBossYcoordinate2, level,1);
//					BulletBoss b3_6 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate,
//							BossList.get(i).machineGunBossYcoordinate, level,1);
					b3_1.timeOfLastCreatedBossBullet = gameTime;
					bulletsBossList.add(b3_1);
					bulletsBossList.add(b3_2);
					bulletsBossList.add(b3_3);
					bulletsBossList.add(b3_4);
					bulletsBossList.add(b3_5);
//					bulletsBossList.add(b3_6);
				
					break;
				case 4:
					BulletBoss b4_1 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate,
							BossList.get(i).machineGunBossYcoordinate, level,4);
					BulletBoss b4_2 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate,
							BossList.get(i).machineGunBossYcoordinate, level,5);
					BulletBoss b4_3 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate,
							BossList.get(i).machineGunBossYcoordinate, level,6);
					BulletBoss b4_4 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate,
							BossList.get(i).machineGunBossYcoordinate, level,7);
					BulletBoss b4_5 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate,
							BossList.get(i).machineGunBossYcoordinate, level,8);
					BulletBoss b4_6 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate,
							BossList.get(i).machineGunBossYcoordinate, level,9);
					BulletBoss b4_7 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate,
							BossList.get(i).machineGunBossYcoordinate, level,10);
//					BulletBoss b4_8 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate1,
//							BossList.get(i).machineGunBossYcoordinate1, level,5);
//					BulletBoss b4_9 = new BulletBoss(BossList.get(i).machineGunBossXcoordinate1,
//							BossList.get(i).machineGunBossYcoordinate1, level,7);
					b4_1.timeOfLastCreatedBossBullet = gameTime;
					bulletsBossList.add(b4_1);
					bulletsBossList.add(b4_2);
					bulletsBossList.add(b4_3);
					bulletsBossList.add(b4_4);
					bulletsBossList.add(b4_5);
					bulletsBossList.add(b4_6);
					bulletsBossList.add(b4_7);
//					bulletsBossList.add(b4_8);
//					bulletsBossList.add(b4_9);
					break;

				default:
					break;
				}
				
			}
		}

	}

	/**
	 * Checks if the player is fired the rocket and creates it if he did. It also
	 * checks if player can fire the rocket.
	 * 
	 * @param gameTime
	 *            Game time.
	 */
	private void didPlayerFiredRocket(long gameTime) {
		if (player.isFiredRocket(gameTime)) {
			Rocket.timeOfLastCreatedRocket = gameTime;
			player.numberOfRockets--;

			Rocket r = new Rocket();
			r.Initialize(player.rocketHolderXcoordinate, player.rocketHolderYcoordinate);
			rocketsList.add(r);
		}
	}

	/**
	 * Creates a new enemy if it's time.
	 * 
	 * @param gameTime
	 *            Game time.
	 */
	// ���ҧ�ͷ��������
	private void createEnemyHelicopter(long gameTime) {
		if (gameTime - EnemyHelicopter.timeOfLastCreatedEnemy >= EnemyHelicopter.timeBetweenNewEnemies
				&& bossFight != true) {
			EnemyHelicopter eh = new EnemyHelicopter();
			double ran = Math.random()*8;
			random_enemy = (int)ran;
			System.out.println("Num Random = "+random_enemy);
			
			int xCoordinate = Framework.frameWidth;
			if(random_enemy == 1) {
				int yCoordinate = random.nextInt(Framework.frameHeight - EnemyHelicopter.enemyUpImg.getHeight());
				eh.Initialize(xCoordinate, yCoordinate, level, random_enemy);
			}
			else if(random_enemy == 2) {
				int yCoordinate = random.nextInt(Framework.frameHeight - EnemyHelicopter.enemyDefendImg.getHeight());
				eh.Initialize(xCoordinate, yCoordinate, level, random_enemy);
			}
			else if(random_enemy == 3) {
				int yCoordinate = random.nextInt(Framework.frameHeight - EnemyHelicopter.enemyHealImg.getHeight());
				eh.Initialize(xCoordinate, yCoordinate, level, random_enemy);
			}
			else {
				int yCoordinate = random.nextInt(Framework.frameHeight - EnemyHelicopter.enemyBodyImg.getHeight());
				eh.Initialize(xCoordinate, yCoordinate, level, random_enemy);
			}
			// Add created enemy to the list of enemies.
			enemyHelicopterList.add(eh);

			// Speed up enemy speed and aperence.
			EnemyHelicopter.speedUp();

			// Sets new time for last created enemy.
			EnemyHelicopter.timeOfLastCreatedEnemy = gameTime;
		}

		if (bossFight == true && bossCreate == true && enemyHelicopterList.size() == 0) {
			EnemyBoss.bossWarning.play((float)1,(float)0.5);
			if(level == 1) {
				
                EnemyBoss bossSt1 = new EnemyBoss(level);
                int xCoordinate = Framework.frameWidth;
                int yCoordinate = 300;
                bossSt1.Initialize(xCoordinate, yCoordinate);
                BossList.add(bossSt1);
                bossCreate = false;
            }
            else if(level == 2) {
                EnemyBoss bossSt2 = new EnemyBoss(level);
                int xCoordinate = Framework.frameWidth;
                int yCoordinate = 300;
                bossSt2.Initialize(xCoordinate, yCoordinate);
                BossList.add(bossSt2);
                bossCreate = false;
            }
            else if(level == 3) {
                EnemyBoss bossSt3 = new EnemyBoss(level);
                int xCoordinate = Framework.frameWidth;
                int yCoordinate = 300;
                bossSt3.Initialize(xCoordinate, yCoordinate);
                BossList.add(bossSt3);
                bossCreate = false;
            }
            else if(level == 4) {
                EnemyBoss bossSt4 = new EnemyBoss(level);
                int xCoordinate = Framework.frameWidth;
                int yCoordinate = 300;
                bossSt4.Initialize(xCoordinate, yCoordinate);
                BossList.add(bossSt4);
                bossCreate = false;
            }
		}

	}

	/**
	 * Updates all enemies. Move the helicopter and checks if he left the screen.
	 * Updates helicopter animations. Checks if enemy was destroyed. Checks if any
	 * enemy collision with player.
	 */
	private void updateEnemies(long gametime) {
		for (int i = 0; i < enemyHelicopterList.size(); i++) {
			EnemyHelicopter eh = enemyHelicopterList.get(i);

			eh.Update();
			Rectangle playerRectangel = new Rectangle(player.xCoordinate, player.yCoordinate,
					player.playerImg.getWidth(), player.playerImg.getHeight());
			
			Rectangle enemyRectangel = new Rectangle(eh.xCoordinate, eh.yCoordinate,
					EnemyHelicopter.enemyBodyImg.getWidth(), EnemyHelicopter.enemyBodyImg.getHeight());
			if (playerRectangel.intersects(enemyRectangel)) {
				player.health -= 40;			
				enemyHelicopterList.remove(i);
				enemyExplosion.explosion.play((float)1, (float) 0.2);
				for(int exNum = 0; exNum < 3; exNum++){
                    Animation expAnim = new Animation(explosionAnimImg, 134, 134, 12, 45, false, player.xCoordinate + exNum*60, player.yCoordinate - random.nextInt(100), exNum * 200 +random.nextInt(100));
                    explosionsList.add(expAnim);
                }
				break;
			}

			// Check health.
			if (eh.health <= 0) {
				
				// Increase the destroyed enemies counter.
				destroyedEnemies++;
				if(eh.GetItem()==1) {
					UpDamage += 5;
					bulletsList.clear();
					if(levelGun < 4)
						levelGun++;
					if(levelGun == 4)
					{
						this.gametime1 = (int) (gametime / Framework.milisecInNanosec / 1000);
						timeToPlay = (int) (this.gametime1 + 100);
					}
						
				}
				else if(eh.GetItem() == 3) {
					player.health+=90;
				}
				enemyExplosion.explosion.play((float)1, (float) 0.2);
				enemyHelicopterList.remove(i);
//				enemyExplosion exAni = new enemyExplosion(eh.xCoordinate, eh.yCoordinate, gametime);
				for(int exNum = 0; exNum < 3; exNum++){
                    Animation expAnim = new Animation(explosionAnimImg, 134, 134, 12, 45, false, eh.xCoordinate + exNum*60, eh.yCoordinate - random.nextInt(100), exNum * 200 +random.nextInt(100));
                    explosionsList.add(expAnim);
                }
				
				if (destroyedEnemies == 4*level && bossFight == false) {
					bossFight = true;
					
					// Helicopter was destroyed so we can move to next helicopter.
					continue;
				}	
			}
			if (eh.isLeftScreen()) {
				
				enemyHelicopterList.remove(i);
				runAwayEnemies++;
			}
		}
	}

	private void updateBoss(long gametime) {
		for (int i = 0; i < BossList.size(); i++) {
			EnemyBoss boss = BossList.get(i);

			boss.Update();

			// Is chrashed with player?
			Rectangle playerRectangel = new Rectangle(player.xCoordinate, player.yCoordinate,
					player.playerImg.getWidth(), player.playerImg.getHeight());
			Rectangle enemyRectangel = new Rectangle(boss.xCoordinate, boss.yCoordinate, EnemyBoss.BossSt1.getWidth(),
					EnemyBoss.BossSt1.getHeight());
			Rectangle enemyRectange2 = new Rectangle(boss.xCoordinate, boss.yCoordinate, EnemyBoss.BossSt2.getWidth(),
                    EnemyBoss.BossSt2.getHeight());
            Rectangle enemyRectange3 = new Rectangle(boss.xCoordinate, boss.yCoordinate, EnemyBoss.BossSt3.getWidth(),
                    EnemyBoss.BossSt3.getHeight());
            Rectangle enemyRectange4 = new Rectangle(boss.xCoordinate, boss.yCoordinate, EnemyBoss.BossSt4.getWidth(),
                    EnemyBoss.BossSt4.getHeight());
			if (playerRectangel.intersects(enemyRectangel)) {
				player.health = 0;

				// Remove helicopter from the list.
				BossList.remove(i);
				for(int exNum = 0; exNum < 3; exNum++){
                    Animation expAnim = new Animation(explosionAnimImg, 134, 134, 12, 45, false, boss.xCoordinate + exNum*60, boss.yCoordinate - random.nextInt(100), exNum * 200 +random.nextInt(100));
                    explosionsList.add(expAnim);
                }
				break;
			}

			// Check health.
			if (boss.health <= 0) {
				
				if(boss.lastBoss == true)
				{
					lastBossDefeat = true;
					BossList.remove(i);
					enemyExplosion.bossDead.play((float)1, (float) 0.08);
					for(int exNum = 0; exNum < 3; exNum++){
	                    Animation expAnim = new Animation(explosionAnimImg, 134, 134, 12, 45, false, boss.xCoordinate + exNum*60, boss.yCoordinate - random.nextInt(100), exNum * 1000 +random.nextInt(100));
	                    explosionsList.add(expAnim);
	                }
					break;
				}
				
				BossList.remove(i);
				enemyExplosion.bossDead.play((float)1, (float) 0.08);
				for(int exNum = 0; exNum < 3; exNum++){
                    Animation expAnim = new Animation(explosionAnimImg, 134, 134, 12, 45, false, boss.xCoordinate + exNum*60, boss.yCoordinate - random.nextInt(100), exNum * 500 +random.nextInt(100));
                    explosionsList.add(expAnim);
                }
				bossCreate = true;
				level++;
				if (enemyHelicopterList.size() == 0) {
                    if(level == 4) {
                        bossFight = true;
                    }
                    else
                        bossFight = false;
                }
				continue;
			}
		}
	}

	/**
	 * Update bullets. It moves bullets. Checks if the bullet is left the screen.
	 * Checks if any bullets is hit any enemy.
	 */
	private void updateBullets() {
		for (int i = 0; i < bulletsList.size(); i++) {
			Bullet bullet = bulletsList.get(i);

			// Move the bullet.
			bullet.Update();

			// Is left the screen?
			if (bullet.isItLeftScreen()) {
				bulletsList.remove(i);
				// Bullet have left the screen so we removed it from the list and now we can
				// continue to the next bullet.
				continue;
			}

			switch (levelGun) {
			case 1:
				Rectangle bulletRectangle = new Rectangle((int) bullet.xCoordinate, (int) bullet.yCoordinate,
						Bullet.bulletImg1.getWidth(), Bullet.bulletImg1.getHeight());
				for (int j = 0; j < enemyHelicopterList.size(); j++) {
					EnemyHelicopter eh = enemyHelicopterList.get(j);

					// Current enemy rectangle.
					Rectangle enemyRectangel = new Rectangle(eh.xCoordinate, eh.yCoordinate,
							EnemyHelicopter.enemyBodyImg.getWidth(), EnemyHelicopter.enemyBodyImg.getHeight());

					// Is current bullet over currnet enemy?
					if (bulletRectangle.intersects(enemyRectangel)) {
						// Bullet hit the enemy so we reduce his health.
						eh.health -= Bullet.damagePower+UpDamage;

						// Bullet was also destroyed so we remove it.
						bulletsList.remove(i);

						// That bullet hit enemy so we don't need to check other enemies.
						break;
					}
				}
				for (int j = 0; j < BossList.size(); j++) {
					EnemyBoss boss = BossList.get(j);

					// Current enemy rectangle.
					Rectangle enemyRectangel = new Rectangle(boss.xCoordinate, boss.yCoordinate,
							EnemyBoss.BossSt1.getWidth(), EnemyBoss.BossSt1.getHeight());
					Rectangle enemyRectange2 = new Rectangle(boss.xCoordinate, boss.yCoordinate,
	                        EnemyBoss.BossSt2.getWidth(), EnemyBoss.BossSt2.getHeight());
	                Rectangle enemyRectange3 = new Rectangle(boss.xCoordinate, boss.yCoordinate,
	                        EnemyBoss.BossSt3.getWidth(), EnemyBoss.BossSt3.getHeight());
	                Rectangle enemyRectange4 = new Rectangle(boss.xCoordinate, boss.yCoordinate,
	                        EnemyBoss.BossSt4.getWidth(), EnemyBoss.BossSt4.getHeight());

					// Is current bullet over currnet enemy?
	                if (bulletRectangle.intersects(enemyRectangel)||bulletRectangle.intersects(enemyRectange2)||
	                        bulletRectangle.intersects(enemyRectange3)||bulletRectangle.intersects(enemyRectange4)) {
						// Bullet hit the enemy so we reduce his health.
						boss.health -= Bullet.damagePower+UpDamage;
						EnemyBoss.hitFade = true;
						// Bullet was also destroyed so we remove it.
						bulletsList.remove(i);

						// That bullet hit enemy so we don't need to check other enemies.
						break;
					}
				}//
				
				break;
			case 2:
				Rectangle bulletRectangle2 = new Rectangle((int) bullet.xCoordinate, (int) bullet.yCoordinate,
						Bullet.bulletImg2.getWidth(), Bullet.bulletImg2.getHeight());
				for (int j = 0; j < enemyHelicopterList.size(); j++) {
					EnemyHelicopter eh = enemyHelicopterList.get(j);

					// Current enemy rectangle.
					Rectangle enemyRectangel = new Rectangle(eh.xCoordinate, eh.yCoordinate,
							EnemyHelicopter.enemyBodyImg.getWidth(), EnemyHelicopter.enemyBodyImg.getHeight());

					// Is current bullet over currnet enemy?
					if (bulletRectangle2.intersects(enemyRectangel)) {
						// Bullet hit the enemy so we reduce his health.
						eh.health -= Bullet.damagePower+UpDamage;

						// Bullet was also destroyed so we remove it.
						bulletsList.remove(i);

						// That bullet hit enemy so we don't need to check other enemies.
						break;
					}
				}
				for (int j = 0; j < BossList.size(); j++) {
					EnemyBoss boss = BossList.get(j);

					// Current enemy rectangle.
					Rectangle enemyRectangel = new Rectangle(boss.xCoordinate, boss.yCoordinate,
							EnemyBoss.BossSt1.getWidth(), EnemyBoss.BossSt1.getHeight());
					Rectangle enemyRectange2 = new Rectangle(boss.xCoordinate, boss.yCoordinate,
	                        EnemyBoss.BossSt2.getWidth(), EnemyBoss.BossSt2.getHeight());
	                Rectangle enemyRectange3 = new Rectangle(boss.xCoordinate, boss.yCoordinate,
	                        EnemyBoss.BossSt3.getWidth(), EnemyBoss.BossSt3.getHeight());
	                Rectangle enemyRectange4 = new Rectangle(boss.xCoordinate, boss.yCoordinate,
	                        EnemyBoss.BossSt4.getWidth(), EnemyBoss.BossSt4.getHeight());

					// Is current bullet over currnet enemy?
	                if (bulletRectangle2.intersects(enemyRectangel)||bulletRectangle2.intersects(enemyRectange2)||
	                        bulletRectangle2.intersects(enemyRectange3)||bulletRectangle2.intersects(enemyRectange4)) {
						// Bullet hit the enemy so we reduce his health.
						boss.health -= Bullet.damagePower+UpDamage;
						EnemyBoss.hitFade = true;
						// Bullet was also destroyed so we remove it.
						bulletsList.remove(i);

						// That bullet hit enemy so we don't need to check other enemies.
						break;
					}
				}//
				break;
			case 3:
				Rectangle bulletRectangle3 = new Rectangle((int) bullet.xCoordinate, (int) bullet.yCoordinate,
						Bullet.bulletImg3.getWidth(), Bullet.bulletImg3.getHeight());
				for (int j = 0; j < enemyHelicopterList.size(); j++) {
					EnemyHelicopter eh = enemyHelicopterList.get(j);

					// Current enemy rectangle.
					Rectangle enemyRectangel = new Rectangle(eh.xCoordinate, eh.yCoordinate,
							EnemyHelicopter.enemyBodyImg.getWidth(), EnemyHelicopter.enemyBodyImg.getHeight());

					// Is current bullet over currnet enemy?
					if (bulletRectangle3.intersects(enemyRectangel)) {
						// Bullet hit the enemy so we reduce his health.
						eh.health -= Bullet.damagePower+UpDamage;

						// Bullet was also destroyed so we remove it.
						bulletsList.remove(i);

						// That bullet hit enemy so we don't need to check other enemies.
						break;
					}
				}
				for (int j = 0; j < BossList.size(); j++) {
					EnemyBoss boss = BossList.get(j);

					// Current enemy rectangle.
					Rectangle enemyRectangel = new Rectangle(boss.xCoordinate, boss.yCoordinate,
							EnemyBoss.BossSt1.getWidth(), EnemyBoss.BossSt1.getHeight());
					Rectangle enemyRectange2 = new Rectangle(boss.xCoordinate, boss.yCoordinate,
	                        EnemyBoss.BossSt2.getWidth(), EnemyBoss.BossSt2.getHeight());
	                Rectangle enemyRectange3 = new Rectangle(boss.xCoordinate, boss.yCoordinate,
	                        EnemyBoss.BossSt3.getWidth(), EnemyBoss.BossSt3.getHeight());
	                Rectangle enemyRectange4 = new Rectangle(boss.xCoordinate, boss.yCoordinate,
	                        EnemyBoss.BossSt4.getWidth(), EnemyBoss.BossSt4.getHeight());

					// Is current bullet over currnet enemy?
	                if (bulletRectangle3.intersects(enemyRectangel)||bulletRectangle3.intersects(enemyRectange2)||
	                        bulletRectangle3.intersects(enemyRectange3)||bulletRectangle3.intersects(enemyRectange4)) {
						// Bullet hit the enemy so we reduce his health.
						boss.health -= Bullet.damagePower+UpDamage;
						EnemyBoss.hitFade = true;
						// Bullet was also destroyed so we remove it.
						bulletsList.remove(i);

						// That bullet hit enemy so we don't need to check other enemies.
						break;
					}
				}//
				break;
			case 4:
				Rectangle bulletRectangle4 = new Rectangle((int) bullet.xCoordinate, (int) bullet.yCoordinate,
						Bullet.bulletImg4.getWidth(), Bullet.bulletImg4.getHeight());
				for (int j = 0; j < enemyHelicopterList.size(); j++) {
					EnemyHelicopter eh = enemyHelicopterList.get(j);

					// Current enemy rectangle.
					Rectangle enemyRectangel = new Rectangle(eh.xCoordinate, eh.yCoordinate,
							EnemyHelicopter.enemyBodyImg.getWidth(), EnemyHelicopter.enemyBodyImg.getHeight());

					// Is current bullet over currnet enemy?
					if (bulletRectangle4.intersects(enemyRectangel)) {
						// Bullet hit the enemy so we reduce his health.
						eh.health -= Bullet.damagePower+UpDamage;

						// Bullet was also destroyed so we remove it.
						bulletsList.remove(i);

						// That bullet hit enemy so we don't need to check other enemies.
						break;
					}
				}
				for (int j = 0; j < BossList.size(); j++) {
					EnemyBoss boss = BossList.get(j);

					// Current enemy rectangle.
					Rectangle enemyRectangel = new Rectangle(boss.xCoordinate, boss.yCoordinate,
							EnemyBoss.BossSt1.getWidth(), EnemyBoss.BossSt1.getHeight());
					Rectangle enemyRectange2 = new Rectangle(boss.xCoordinate, boss.yCoordinate,
	                        EnemyBoss.BossSt2.getWidth(), EnemyBoss.BossSt2.getHeight());
	                Rectangle enemyRectange3 = new Rectangle(boss.xCoordinate, boss.yCoordinate,
	                        EnemyBoss.BossSt3.getWidth(), EnemyBoss.BossSt3.getHeight());
	                Rectangle enemyRectange4 = new Rectangle(boss.xCoordinate, boss.yCoordinate,
	                        EnemyBoss.BossSt4.getWidth(), EnemyBoss.BossSt4.getHeight());

					// Is current bullet over currnet enemy?
	                if (bulletRectangle4.intersects(enemyRectangel)||bulletRectangle4.intersects(enemyRectange2)||
	                        bulletRectangle4.intersects(enemyRectange3)||bulletRectangle4.intersects(enemyRectange4)) {
						// Bullet hit the enemy so we reduce his health.
						boss.health -= Bullet.damagePower+UpDamage;
						EnemyBoss.hitFade = true;
						// Bullet was also destroyed so we remove it.
						bulletsList.remove(i);

						// That bullet hit enemy so we don't need to check other enemies.
						break;
					}
				}//
				break;
			default:
				break;
			}
		}
	}

	private void updateBulletsEnemy() {
		for (int i = 0; i < bulletsEnemyList.size(); i++) {
			BulletEnemy bulletEnemy = bulletsEnemyList.get(i);

			// Move the bullet.
			bulletEnemy.Update();

			// Is left the screen?
			if (bulletEnemy.isItLeftScreen()) {
				bulletsEnemyList.remove(i);
				// Bullet have left the screen so we removed it from the list and now we can
				// continue to the next bullet.
				continue;
			}

			// Did hit any enemy?
			// Rectangle of the bullet image.
			Rectangle bulletRectangle = new Rectangle((int) bulletEnemy.xCoordinate, (int) bulletEnemy.yCoordinate,
					bulletEnemy.bulletImg.getWidth(), bulletEnemy.bulletImg.getHeight());
			// Go trough all enemis.
			PlayerHelicopter eh = player;

			// Current enemy rectangle.
			Rectangle playerRectangel = new Rectangle(eh.xCoordinate, eh.yCoordinate, eh.playerImg.getWidth(),
					eh.playerImg.getHeight());

			// Is current bullet over currnet enemy?
			if (bulletRectangle.intersects(playerRectangel)) {
				// Bullet hit the enemy so we reduce his health.
				eh.health -= Bullet.damagePower;

				// Bullet was also destroyed so we remove it.
				bulletsEnemyList.remove(i);

				// That bullet hit enemy so we don't need to check other enemies.
				break;
			}
		}
	}
	
	private void updateBulletsBoss() {
		for (int i = 0; i < bulletsBossList.size(); i++) {
			BulletBoss bulletBoss = bulletsBossList.get(i);

			bulletBoss.Update(player.xCoordinate, player.yCoordinate);


			Rectangle bulletRectangle = new Rectangle((int) bulletBoss.xCoordinate, (int) bulletBoss.yCoordinate,bulletBoss.bulletImg.getWidth(), bulletBoss.bulletImg.getHeight());
			
			// Go trough all enemis.
			PlayerHelicopter eh = player;

			// Current enemy rectangle.
			Rectangle playerRectangel = new Rectangle(eh.xCoordinate, eh.yCoordinate, eh.playerImg.getWidth(),
					eh.playerImg.getHeight());

			 
			if (bulletRectangle.intersects(playerRectangel)) {
				// Bullet hit the enemy so we reduce his health.
				eh.health -= Bullet.damagePower;

				// Bullet was also destroyed so we remove it.
				bulletsBossList.remove(i);

				// That bullet hit enemy so we don't need to check other enemies.
				break;
			}
		}
	}

	/**
	 * Update rockets. It moves rocket and add smoke behind it. Checks if the rocket
	 * is left the screen. Checks if any rocket is hit any enemy.
	 * 
	 * @param gameTime
	 *            Game time.
	 */
	private void updateRockets(long gameTime) {
		for (int i = 0; i < rocketsList.size(); i++) {
			Rocket rocket = rocketsList.get(i);

			// Moves the rocket.
			rocket.Update();

			// Checks if it is left the screen.
			if (rocket.isItLeftScreen()) {
				rocketsList.remove(i);
				// Rocket left the screen so we removed it from the list and now we can continue
				// to the next rocket.
				continue;
			}

			// Checks if current rocket hit any enemy.
			if (checkIfRocketHitEnemy(rocket))
				// Rocket was also destroyed so we remove it.
				rocketsList.remove(i);
		}
	}

	/**
	 * Checks if the given rocket is hit any of enemy helicopters.
	 * 
	 * @param rocket
	 *            Rocket to check.
	 * @return True if it hit any of enemy helicopters, false otherwise.
	 */
	private boolean checkIfRocketHitEnemy(Rocket rocket) {
		boolean didItHitEnemy = false;

		// Current rocket rectangle. // I inserted number 2 insted of
		// rocketImg.getWidth() because I wanted that rocket
		// is over helicopter when collision is detected, because actual image of
		// helicopter isn't a rectangle shape. (We could calculate/make 3 areas where
		// helicopter can be hit and checks these areas, but this is easier.)
		Rectangle rocketRectangle = new Rectangle(rocket.xCoordinate, rocket.yCoordinate, Rocket.rocketImg.getWidth(),
				Rocket.rocketImg.getHeight());

		// Go trough all enemis.
		for (int j = 0; j < enemyHelicopterList.size(); j++) {
			EnemyHelicopter eh = enemyHelicopterList.get(j);

			// Current enemy rectangle.
			Rectangle enemyRectangel = new Rectangle(eh.xCoordinate, eh.yCoordinate,
					EnemyHelicopter.enemyBodyImg.getWidth(), EnemyHelicopter.enemyBodyImg.getHeight());

			// Is current rocket over currnet enemy?
			if (rocketRectangle.intersects(enemyRectangel)) {
				didItHitEnemy = true;

				// Rocket hit the enemy so we reduce his health.
				eh.health -= Rocket.damagePower;

				// Rocket hit enemy so we don't need to check other enemies.
				break;
			}
		}
		for (int j = 0; j < BossList.size(); j++) {
			EnemyBoss eh = BossList.get(j);

			// Current enemy rectangle.
			Rectangle bossRectangel = new Rectangle(eh.xCoordinate, eh.yCoordinate,
					EnemyBoss.BossSt1.getWidth(), EnemyBoss.BossSt1.getHeight());

			// Is current rocket over currnet enemy?
			if (rocketRectangle.intersects(bossRectangel)) {
				didItHitEnemy = true;

				// Rocket hit the enemy so we reduce his health.
				eh.health -= Rocket.damagePower;

				// Rocket hit enemy so we don't need to check other enemies.
				break;
			}
		}

		return didItHitEnemy;
	}

	/**
	 * Updates smoke of all the rockets. If the life time of the smoke is over then
	 * we delete it from list. It also changes a transparency of a smoke image, so
	 * that smoke slowly disappear.
	 * 
	 * @param gameTime
	 *            Game time.
	 */
	private void updateRocketSmoke(long gameTime) {
		for (int i = 0; i < rocketSmokeList.size(); i++) {
			RocketSmoke rs = rocketSmokeList.get(i);

			// Is it time to remove the smoke.
			if (rs.didSmokeDisapper(gameTime))
				rocketSmokeList.remove(i);

			// Set new transparency of rocket smoke image.
			rs.updateTransparency(gameTime);
		}
	}

	/**
	 * Updates all the animations of an explosion and remove the animation when is
	 * over.
	 */
	private void updateExplosions() {
		for (int i = 0; i < explosionsList.size(); i++) {
			// If the animation is over we remove it from the list.
			if (!explosionsList.get(i).active)
				explosionsList.remove(i);
		}
	}

}



//test2602-2

//testkey#123

//testetestsetset