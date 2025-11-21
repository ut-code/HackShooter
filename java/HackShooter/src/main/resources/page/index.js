//ここはてきのUFOのつよさをきめているよ！
const HP_ENEMY = 4000;               //たいりょく
const ATTACK_ENEMY = 200;           //こうげきりょく
const DIFENCE_ENEMY = 100;          //ぼうぎょりょく
const SPEED_ENEMY = 400;            //UFOのスピード
const BULLET_SPEED_ENEMY = 500;     //たまのスピード

//ここはじぶんのうちゅうせんのつよさをきめているよ！
const HP_PLAYER = 300;                          //たいりょく
const ATTACK_PLAYER = 100;                      //こうげきりょく
const DIFENCE_PLAYER = 100;                     //ぼうぎょりょく
const SPEED_PLAYER = 500;                       //うちゅうせんのスピード
const BULLET_SPEED_PLAYER = 1000;               //たまのスピード
const COOLDOWN_PLAYER = 30;                     //たまをうったあとつぎをうてるようになるまでのじかん
const SPECIAL_GUAGE_PLAYER = 10000;              //ひっさつわざをためないといけないりょう
const SPECIAL_GUAGE_EFFICIENCY_PLAYER = 1;      //ひっさつわざのたまりやすさ

//ここはいんせきのつよさをきめているよ！
const ATTACK_ASTEROID = 1000;           //こうげきりょく
const SPEED_ASTEROID = 1000;            //スピード
const PROBABILITY_ASTEROID = 0.001;     //でやすさ(さいだい1)

//ここはかいふくアイテムのつよさをきめているよ！
const SPEED_RECOVERY_ITEM = 500;        //スピード
const PROBABILITY_RECOVERY_ITEM = 2e-4; //でやすさ(さいだい1)
const AMOUNT_RECOVERY_ITEM = 100;       //かいふくりょう

//ここはこうげきりょくアップアイテムのつよさをきめているよ！
const SPEED_BUFF_ITEM = 500;            //スピード
const PROBABILITY_BUFF_ITEM = 1e-3;     //でやすさ(さいだい1)
const MAGNIFICATION_BUFF_ITEM = 1.2;    //どれだけこうげきりょくをあげるか

//ここはひっさつわざチャージアイテムのつよさをきめているよ！
const SPEED_SPECIAL_CHARGE_ITEM = 500;              //スピード
const PROBABILITY_SPECIAL_CHARGE_ITEM = 0.001;      //でやすさ(さいだい1)
const AMOUNT_SPECIAL_CHARGE_ITEM = 3000;            //ひっさつわざをどれだけためるか

import { r as requirePhaser } from "./phaser.js";
requirePhaser();
class EnemyHP {
  background;
  hp;
  text;
  preload(graphics) {
    graphics.clear();
    graphics.fillStyle(3355443);
    graphics.fillRect(0, 0, 160, 40);
    graphics.generateTexture("enemy-hp-background", 160, 40);
    graphics.clear();
    graphics.fillStyle(16711680);
    graphics.fillRect(0, 0, 150, 30);
    graphics.generateTexture("enemy-hp", 150, 30);
  }
  create(scene) {
    this.background = scene.add.image(520, 510, "enemy-hp-background");
    this.background.setOrigin(0);
    this.hp = scene.add.image(525, 515, "enemy-hp");
    this.hp.setOrigin(0);
    this.text = scene.add.text(675, 575, "敵のHP");
    this.text.setOrigin(1);
  }
  setHP(maxHP, currentHP) {
    this.text?.setText(currentHP + " / " + maxHP);
    this.hp?.setDisplaySize(150 * currentHP / maxHP, 30);
  }
}
class Enemy {
  MAX_HP;
  hp;
  attack;
  difense;
  speed;
  bulletSpeed;
  hitstop = 0;
  savedVelocity = 0;
  stopped = false;
  sprite;
  constructor(hp, attack, difense, speed, bulletSpeed) {
    this.hp = hp;
    this.MAX_HP = hp;
    this.attack = attack;
    this.difense = difense;
    this.speed = speed;
    this.bulletSpeed = bulletSpeed;
  }
  create(scene) {
    this.reset();
    this.sprite = scene.physics.add.sprite(scene.cameras.main.width - 50, scene.cameras.main.height / 2, "enemy");
    this.sprite.setCollideWorldBounds(true);
    this.sprite.setVelocityY(this.speed);
    this.sprite.setBounce(1);
    this.sprite.setCircle(100).setOffset(120, 120);
    this.sprite.setScale(0.6);
  }
  update(enemyHP) {
    if (!this.sprite) return;
    if (Math.random() < 5e-3) {
      this.sprite.setVelocityY(-this.sprite.body.velocity.y);
    }
    enemyHP.setHP(this.MAX_HP, this.hp);
    if (this.hitstop > 0) {
      this.sprite.setTexture("enemy-attacked");
      if (!this.stopped) {
        this.stopped = true;
        this.savedVelocity = this.sprite.body.velocity.y;
      }
      this.sprite.setVelocityY(0);
    } else {
      this.sprite.setTexture("enemy");
      if (this.stopped) {
        this.stopped = false;
        this.sprite.setVelocityY(this.savedVelocity);
      }
    }
    if (this.hitstop > 0) this.hitstop--;
  }
  fire(bullets) {
    const bullet = bullets.group?.get(this.sprite?.x, this.sprite?.y, "enemy-bullet");
    bullet.setActive(true);
    bullet.setVisible(true);
    bullet.body.enable = true;
    bullet.setCollideWorldBounds(true);
    bullet.setBounce(1);
    bullet.setSize(30, 20);
    bullet.setScale(0.4);
    const angle = Phaser.Math.FloatBetween(Math.PI / 3, -Math.PI / 3);
    bullet.setVelocity(-this.bulletSpeed * Math.cos(angle), this.bulletSpeed * Math.sin(angle));
    let audio = new Audio("ショット.mp3");
    audio.volume = 0.5;
    audio.play();
  }
  damage(attack, hitstop) {
    let amountOfDamage = attack / this.difense * 10 * (0.8 + Math.random() * 0.4);
    if (amountOfDamage > 1) {
      amountOfDamage = Math.round(amountOfDamage);
    }
    this.hp -= amountOfDamage;
    if (hitstop) this.hitstop = hitstop;
    return amountOfDamage;
  }
  getRemainingHP() {
    return this.hp;
  }
  reset() {
    this.hp = this.MAX_HP;
    this.hitstop = 0;
  }
}
class PlayerBullets {
  group;
  scene;
  create(scene) {
    this.scene = scene;
    this.group = scene.physics.add.group({
      defaultKey: "player-bullet",
      maxSize: -1
    });
  }
  update() {
    this.group?.children.each((e) => {
      if (!this.scene) return null;
      const bullet = e;
      if (!bullet.body) return null;
      if (bullet.x <= 10 || bullet.x >= this.scene.cameras.main.width - 20) {
        bullet.disableBody(true, true);
      } else {
        bullet.angle = Phaser.Math.RadToDeg(Math.atan2(bullet.body.velocity.y, bullet.body.velocity.x));
      }
      return null;
    });
  }
}
class Player {
  MAX_HP;
  hp;
  attack_default;
  attack;
  difense;
  speed;
  bulletSpeed;
  cooldownLength;
  MAX_SPECIAL_GUAGE;
  specialGuageEfficiency;
  bulletangle = 0;
  cooldown = 0;
  hitstop = 0;
  specialGuage = 0;
  specialRemainingTime = 0;
  specialCharged = false;
  specialActive = false;
  sprite;
  scene;
  constructor(hp, attack, difense, speed, bulletSpeed, cooldownLength, specialGuage, specialGuageEfficiency) {
    this.attack_default = attack;
    this.attack = attack;
    this.hp = hp;
    this.MAX_HP = hp;
    this.bulletSpeed = bulletSpeed;
    this.difense = difense;
    this.speed = speed;
    this.cooldownLength = cooldownLength;
    this.MAX_SPECIAL_GUAGE = specialGuage;
    this.specialGuageEfficiency = specialGuageEfficiency;
  }
  create(scene) {
    this.reset();
    this.scene = scene;
    this.sprite = scene.physics.add.sprite(80, scene.cameras.main.height / 2, "player");
    this.sprite.setCollideWorldBounds(true);
    this.sprite.setSize(200, 70);
    this.sprite.setScale(0.6);
  }
  update(cursors, bullets, special) {
    if (this.hitstop <= 0) {
      this.sprite?.setTexture("player");
      if (cursors.space.isDown) {
        if (this.cooldown <= 0 && this.specialRemainingTime <= 0) {
          this.fire(bullets);
          this.cooldown = this.cooldownLength;
        }
      }
      if (cursors.shift.isDown) {
        if (this.specialGuage >= this.MAX_SPECIAL_GUAGE) {
          this.executeSpecial(special);
        }
      }
      if (cursors.left.isDown) {
        if (this.bulletangle > -30) {
          this.bulletangle -= 1;
          if (this.sprite) {
            this.sprite.angle = this.bulletangle;
          }
        }
      } else if (cursors.right.isDown) {
        if (this.bulletangle < 30) {
          this.bulletangle += 1;
          if (this.sprite) {
            this.sprite.angle = this.bulletangle;
          }
        }
      }
      if (cursors.up.isDown && this.specialRemainingTime <= 0) {
        this.sprite?.setVelocityY(-this.speed);
      } else if (cursors.down.isDown && this.specialRemainingTime <= 0) {
        this.sprite?.setVelocityY(this.speed);
      } else {
        this.sprite?.setVelocityY(0);
      }
    } else {
      this.sprite?.setTexture("player-attacked");
      this.sprite?.setVelocityY(0);
    }
    if (this.cooldown > 0) this.cooldown--;
    if (this.hitstop > 0) this.hitstop--;
    this.increaseSpecialGuage();
    if (this.specialRemainingTime > 0) {
      this.specialActive = this.specialActive || this.specialRemainingTime % 30 == 0;
      this.specialRemainingTime--;
    }
    if (this.specialRemainingTime <= 0) special.stop();
  }
  fire(bullets) {
    const bullet = bullets.group?.get(this.sprite?.x, this.sprite?.y, "player-bullet");
    bullet.setVisible(true);
    bullet.setActive(true);
    bullet.body.enable = true;
    bullet.setCollideWorldBounds(true);
    bullet.setBounce(1);
    bullet.setScale(0.4);
    bullet.setSize(30, 20);
    bullet.setVelocityX(this.bulletSpeed * Math.cos(Phaser.Math.DegToRad(this.bulletangle)));
    bullet.setVelocityY(this.bulletSpeed * Math.sin(Phaser.Math.DegToRad(this.bulletangle)));
    let audio = new Audio("ショット.mp3");
    audio.volume = 0.5;
    audio.play();
  }
  executeSpecial(special) {
    this.specialGuage = 0;
    this.specialRemainingTime = 240;
    special.stop();
    const sp = special.group?.create(this.sprite?.x, this.sprite?.y);
    sp.setOrigin(0, 0.5);
    sp.setScale(0.4);
    sp.angle = this.bulletangle;
    if (this.scene) sp.setOffset(0, (this.scene.cameras.main.width - 180) * Math.tan(this.bulletangle * Math.PI / 180) * 2.5);
    new Audio("雷魔法2.mp3").play();
    this.resetSpecialGuage();
  }
  isSpecialActive() {
    if (this.specialActive) {
      this.specialActive = false;
      return true;
    }
    return false;
  }
  recover(amount) {
    this.hp += amount;
    if (this.hp > this.MAX_HP) this.hp = this.MAX_HP;
  }
  buff(magnification) {
    this.attack *= magnification;
  }
  damage(attack, hitstop) {
    if (this.specialRemainingTime > 0) return 0;
    let amountOfDamage = attack / this.difense * 10 * (0.8 + Math.random() * 0.4);
    if (amountOfDamage > 1) {
      amountOfDamage = Math.round(amountOfDamage);
    }
    this.hp -= amountOfDamage;
    if (hitstop) this.hitstop = hitstop;
    return amountOfDamage;
  }
  getRemainingHP() {
    return this.hp;
  }
  reset() {
    this.bulletangle = 0;
    this.hp = this.MAX_HP;
    this.attack = this.attack_default;
    this.resetSpecialGuage();
  }
  increaseSpecialGuage(amount) {
    if (amount) this.specialGuage += this.specialGuageEfficiency * amount;
    else this.specialGuage += this.specialGuageEfficiency;
    if (this.specialGuage > this.MAX_SPECIAL_GUAGE) {
      this.specialGuage = this.MAX_SPECIAL_GUAGE;
      if (!this.specialCharged) {
        new Audio("シャキーン1.mp3").play();
        this.specialCharged = true;
      }
    }
  }
  resetSpecialGuage() {
    this.specialGuage = 0;
    this.specialCharged = false;
  }
}
class EnemyBullets {
  group;
  scene;
  create(scene) {
    this.scene = scene;
    this.group = scene.physics.add.group({
      defaultKey: "enemy-bullet",
      maxSize: -1
    });
  }
  update() {
    this.group?.children.each((e) => {
      if (!this.scene) return null;
      const bullet = e;
      if (!bullet.body) return null;
      if (bullet.x <= 10 || bullet.x >= this.scene.cameras.main.width - 20) {
        bullet.disableBody(true, true);
      }
      bullet.angle = Phaser.Math.RadToDeg(Math.atan2(bullet.body.velocity.y, bullet.body.velocity.x));
      return null;
    });
  }
}
class PlayerHP {
  background;
  hp;
  text;
  preload(graphics) {
    graphics.clear();
    graphics.fillStyle(3355443);
    graphics.fillRect(0, 0, 160, 40);
    graphics.generateTexture("player-hp-background", 160, 40);
    graphics.clear();
    graphics.fillStyle(16711680);
    graphics.fillRect(0, 0, 150, 30);
    graphics.generateTexture("player-hp", 150, 30);
  }
  create(scene) {
    this.background = scene.add.image(120, 510, "player-hp-background");
    this.background.setOrigin(0);
    this.hp = scene.add.image(125, 515, "player-hp");
    this.hp.setOrigin(0);
    this.text = scene.add.text(125, 575, "自分のHP");
    this.text.setOrigin(0, 1);
  }
  setHP(maxHP, currentHP) {
    this.text?.setText(currentHP + " / " + maxHP);
    this.hp?.setDisplaySize(150 * currentHP / maxHP, 30);
  }
}
class EnemyDamageTexts {
  scene;
  playerDamageTexts;
  constructor() {
    this.playerDamageTexts = new Array();
  }
  add(positionX, positionY, damage) {
    const text = this.scene?.add.text(positionX, positionY, damage.toString(), { font: "bold 48px Arial", fill: "#ff0000" }).setOrigin(1, 0.5);
    if (text) {
      for (let i = 0; i < this.playerDamageTexts.length; i++) {
        if (!this.playerDamageTexts[i]) {
          this.playerDamageTexts[i] = { text, remainingFrames: 120 };
          return;
        }
      }
      this.playerDamageTexts.push({ text, remainingFrames: 120 });
    }
  }
  create(scene) {
    this.scene = scene;
  }
  update() {
    for (let i = 0; i < this.playerDamageTexts.length; i++) {
      if (this.playerDamageTexts[i]) {
        this.playerDamageTexts[i].remainingFrames--;
        if (this.playerDamageTexts[i].remainingFrames <= 0) {
          this.playerDamageTexts[i].text.destroy();
          delete this.playerDamageTexts[i];
          break;
        }
        if (this.playerDamageTexts[i].remainingFrames < 60) {
          this.playerDamageTexts[i].text.setAlpha(this.playerDamageTexts[i].remainingFrames / 60);
        }
      }
    }
  }
}
class PlayerDamageTexts {
  scene;
  playerDamageTexts;
  constructor() {
    this.playerDamageTexts = new Array();
  }
  add(positionX, positionY, damage) {
    const text = this.scene?.add.text(positionX, positionY, damage.toString(), { font: "bold 48px Arial", fill: "#0000ff" }).setOrigin(1, 0.5);
    if (text) {
      for (let i = 0; i < this.playerDamageTexts.length; i++) {
        if (!this.playerDamageTexts[i]) {
          this.playerDamageTexts[i] = { text, remainingFrames: 120 };
          return;
        }
      }
      this.playerDamageTexts.push({ text, remainingFrames: 120 });
    }
  }
  create(scene) {
    this.scene = scene;
  }
  update() {
    for (let i = 0; i < this.playerDamageTexts.length; i++) {
      if (this.playerDamageTexts[i]) {
        this.playerDamageTexts[i].remainingFrames--;
        if (this.playerDamageTexts[i].remainingFrames <= 0) {
          this.playerDamageTexts[i].text.destroy();
          delete this.playerDamageTexts[i];
          break;
        }
        if (this.playerDamageTexts[i].remainingFrames < 60) {
          this.playerDamageTexts[i].text.setAlpha(this.playerDamageTexts[i].remainingFrames / 60);
        }
      }
    }
  }
}
class Items {
  group;
  scene;
  scale = 1;
  width = 100;
  height = 100;
  speed;
  probability;
  active = false;
  constructor(speed, probability) {
    this.speed = speed;
    if (probability) this.probability = probability;
    else this.probability = 1e-3;
  }
  create(scene, defaultKey) {
    this.scene = scene;
    this.group = scene.physics.add.group({
      defaultKey,
      maxSize: -1
    });
  }
  setScale(scale) {
    this.scale = scale;
  }
  setSize(width, height) {
    this.width = width;
    this.height = height;
  }
  update() {
    if (!this.group || !this.scene || !this.active) return;
    if (Math.random() < this.probability) {
      const item = this.group?.get(this.scene?.cameras.main.width, Math.random() * this.scene?.cameras.main.height);
      item.setVisible(true);
      item.setActive(true);
      item.body.enable = true;
      item.setCollideWorldBounds(false);
      item.setBounce(1);
      item.setScale(this.scale);
      item.setSize(this.width, this.height);
      item.setVelocity(-this.speed, 0);
    }
    this.group?.children.each((e) => {
      if (!this.scene) return null;
      const item = e;
      if (!item.body) return null;
      if (item.x <= 0 || item.x >= this.scene.cameras.main.width + 20) {
        item.disableBody(true, true);
      }
      return null;
    });
  }
  stop() {
    this.active = false;
  }
  start() {
    this.active = true;
  }
}
class TimeText {
  text;
  running = false;
  startTime = 0;
  now = 0;
  create(scene) {
    this.running = false;
    this.text = scene.add.text(20, 50, "0.000", { fontSize: "32px", color: "#ffff00" });
  }
  start() {
    this.running = true;
    this.startTime = Date.now();
    this.text?.setText("0.000");
  }
  update() {
    if (!this.running) return;
    this.now = Date.now();
    this.text?.setText(((this.now - this.startTime) / 1e3).toString());
  }
  stop() {
    this.running = false;
  }
  getCurrentTime() {
    return this.now - this.startTime;
  }
}
class PlayerSpecialGuage {
  background;
  guage;
  text;
  preload(graphics) {
    graphics.clear();
    graphics.fillStyle(3355443);
    graphics.fillRect(0, 0, 160, 20);
    graphics.generateTexture("player-special-guage-background", 160, 40);
    graphics.clear();
    graphics.fillStyle(65535);
    graphics.fillRect(0, 0, 150, 10);
    graphics.generateTexture("player-special-guage", 150, 30);
  }
  create(scene) {
    this.background = scene.add.image(120, 490, "player-special-guage-background");
    this.background.setOrigin(0);
    this.guage = scene.add.image(125, 495, "player-special-guage").setDisplaySize(0, 30);
    this.guage.setOrigin(0);
    this.text = scene.add.text(125, 480, "", { fontFamily: "Arial", fontSize: "24px", color: "#ffff00" });
    this.text.setOrigin(0, 1);
  }
  setSpecialGuage(maxSpecialGuage, currentSpecialGuage) {
    this.guage?.setDisplaySize(150 * currentSpecialGuage / maxSpecialGuage, 30);
    if (currentSpecialGuage >= maxSpecialGuage) this.text?.setText("Shiftキーで必殺技");
    else this.text?.setText("");
  }
}
class PlayerSpecial {
  group;
  scene;
  create(scene) {
    this.scene = scene;
    this.group = scene.physics.add.group({
      defaultKey: "player-special",
      maxSize: -1
    });
  }
  update() {
    this.group?.children.each((e) => {
      if (!this.scene) return null;
      const bullet = e;
      if (!bullet.body) return null;
      if (bullet.x <= 10 || bullet.x >= this.scene.cameras.main.width - 20) {
        bullet.disableBody(true, true);
      } else {
        bullet.angle = Phaser.Math.RadToDeg(Math.atan2(bullet.body.velocity.y, bullet.body.velocity.x));
      }
      return null;
    });
  }
  stop() {
    this.group?.children.each((e) => {
      e.destroy();
      return null;
    });
  }
}
class PlayerRecoveryTexts {
  scene;
  playerDamageTexts;
  constructor() {
    this.playerDamageTexts = new Array();
  }
  add(positionX, positionY, damage) {
    const text = this.scene?.add.text(positionX, positionY, damage.toString(), { font: "bold 48px Arial", fill: "#00ff00" }).setOrigin(1, 0.5);
    if (text) {
      for (let i = 0; i < this.playerDamageTexts.length; i++) {
        if (!this.playerDamageTexts[i]) {
          this.playerDamageTexts[i] = { text, remainingFrames: 120 };
          return;
        }
      }
      this.playerDamageTexts.push({ text, remainingFrames: 120 });
    }
  }
  create(scene) {
    this.scene = scene;
  }
  update() {
    for (let i = 0; i < this.playerDamageTexts.length; i++) {
      if (this.playerDamageTexts[i]) {
        this.playerDamageTexts[i].remainingFrames--;
        if (this.playerDamageTexts[i].remainingFrames <= 0) {
          this.playerDamageTexts[i].text.destroy();
          delete this.playerDamageTexts[i];
          break;
        }
        if (this.playerDamageTexts[i].remainingFrames < 60) {
          this.playerDamageTexts[i].text.setAlpha(this.playerDamageTexts[i].remainingFrames / 60);
        }
      }
    }
  }
}

class GameScene extends Phaser.Scene {
  cursors;
  gameOver = false;
  gameClear = false;
  finished = false;
  started = false;
  spaceClicked = false;
  enemy;
  player;
  enemyBullets;
  playerBullets;
  playerSpecial;
  asteroids;
  recoveryItems;
  buffItems;
  specialChargeItems;
  enemyHP;
  playerHP;
  playerSpecialGuage;
  enemyDamageTexts;
  playerDamageTexts;
  playerRecoveryTexts;
  timeText;
  clickSpaceToStartText;
  constructor() {
    super({ key: "GameScene" });
    this.enemy = new Enemy(HP_ENEMY, ATTACK_ENEMY, DIFENCE_ENEMY, SPEED_ENEMY, BULLET_SPEED_ENEMY);
    this.player = new Player(HP_PLAYER, ATTACK_PLAYER, DIFENCE_PLAYER, SPEED_PLAYER, BULLET_SPEED_PLAYER, COOLDOWN_PLAYER, SPECIAL_GUAGE_PLAYER, SPECIAL_GUAGE_EFFICIENCY_PLAYER);
    this.enemyBullets = new EnemyBullets();
    this.playerBullets = new PlayerBullets();
    this.playerSpecial = new PlayerSpecial();
    this.asteroids = new Items(SPEED_ASTEROID, PROBABILITY_ASTEROID);
    this.recoveryItems = new Items(SPEED_RECOVERY_ITEM, PROBABILITY_RECOVERY_ITEM);
    this.buffItems = new Items(SPEED_BUFF_ITEM, PROBABILITY_BUFF_ITEM);
    this.specialChargeItems = new Items(SPEED_SPECIAL_CHARGE_ITEM, PROBABILITY_SPECIAL_CHARGE_ITEM);
    this.enemyHP = new EnemyHP();
    this.playerHP = new PlayerHP();
    this.playerSpecialGuage = new PlayerSpecialGuage();
    this.enemyDamageTexts = new EnemyDamageTexts();
    this.playerDamageTexts = new PlayerDamageTexts();
    this.playerRecoveryTexts = new PlayerRecoveryTexts();
    this.timeText = new TimeText();
  }
  preload() {
    const graphics = this.make.graphics({ fillStyle: { color: 16711680 } });
    this.load.image("player", "/Player.png");
    this.load.image("player-attacked", "/Player_Attacked.png");
    this.load.image("enemy", "/Enemy.png");
    this.load.image("enemy-attacked", "Enemy_Attacked.png");
    this.load.image("player-bullet", "/Bullet.png");
    this.load.image("enemy-bullet", "/Bullet_Enemy.png");
    this.load.image("background", "/Background.jpg");
    this.load.image("obstacle1", "/Obstacle.png");
    this.load.image("obstacle2", "/Obstacle.png");
    this.load.image("player-special", "/Special.png");
    this.load.image("recovery", "/Recovery.png");
    this.load.image("special-charge", "/Special_Charge.png");
    this.load.image("buff", "Buff.png");
    this.enemyHP.preload(graphics);
    this.playerHP.preload(graphics);
    this.playerSpecialGuage.preload(graphics);
    graphics.destroy();
  }
  create() {
    this.add.image(400, 300, "background").setScale(0.4, 0.4);
    this.gameOver = false;
    this.gameClear = false;
    this.finished = false;
    this.started = false;
    this.spaceClicked = false;
    this.player.create(this);
    this.enemy.create(this);
    this.enemyBullets.create(this);
    this.playerBullets.create(this);
    this.playerSpecial.create(this);
    this.asteroids.create(this, "obstacle1");
    this.asteroids.setSize(60, 60);
    this.asteroids.stop();
    this.buffItems.create(this, "buff");
    this.buffItems.setSize(120, 120);
    this.buffItems.setScale(0.5);
    this.buffItems.stop();
    this.specialChargeItems.create(this, "special-charge");
    this.specialChargeItems.setSize(120, 120);
    this.specialChargeItems.setScale(0.5);
    this.specialChargeItems.stop();
    this.recoveryItems.create(this, "recovery");
    this.recoveryItems.setSize(120, 120);
    this.recoveryItems.setScale(0.5);
    this.recoveryItems.stop();
    this.enemyHP.create(this);
    this.playerHP.create(this);
    this.playerSpecialGuage.create(this);
    this.enemyDamageTexts.create(this);
    this.playerDamageTexts.create(this);
    this.playerRecoveryTexts.create(this);
    this.timeText.create(this);
    this.cursors = this.input.keyboard?.createCursorKeys();
    this.add.text(12, 12, "Reset", {
      font: "24px Arial",
      backgroundColor: "#444",
      padding: { x: 10, y: 5 }
    });
    const resetButton = this.add.text(10, 10, "Reset", {
      font: "24px Arial",
      fill: "#ffffff",
      backgroundColor: "#666666",
      padding: { x: 10, y: 5 }
    }).setInteractive({ useHandCursor: true }).on("pointerover", () => resetButton.setBackgroundColor("#888888")).on("pointerout", () => resetButton.setBackgroundColor("#666666")).on("pointerdown", () => {
      resetButton.setPosition(12, 12);
      this.scene.restart();
    });
    if (this.playerBullets.group && this.enemy.sprite)
      this.physics.add.overlap(this.enemy.sprite, this.playerBullets.group, this.bulletHitTarget, void 0, this);
    if (this.playerSpecial.group && this.enemy.sprite)
      this.physics.add.overlap(this.enemy.sprite, this.playerSpecial.group, this.specialHitTarget, void 0, this);
    if (this.enemyBullets.group && this.player.sprite)
      this.physics.add.overlap(this.player.sprite, this.enemyBullets.group, this.bulletHitEmitter, void 0, this);
    if (this.player.sprite && this.asteroids.group)
      this.physics.add.overlap(this.player.sprite, this.asteroids.group, this.asteroidHitPlayer, void 0, this);
    if (this.player.sprite && this.buffItems.group)
      this.physics.add.overlap(this.player.sprite, this.buffItems.group, this.buffItemHitPlayer, void 0, this);
    if (this.player.sprite && this.recoveryItems.group)
      this.physics.add.overlap(this.player.sprite, this.recoveryItems.group, this.recoveryItemHitPlayer, void 0, this);
    if (this.player.sprite && this.specialChargeItems.group)
      this.physics.add.overlap(this.player.sprite, this.specialChargeItems.group, this.specialChargeItemHitPlayer, void 0, this);
    this.physics.pause();
    this.clickSpaceToStartText = this.add.text(0, 150, "スペースキーを押してスタート", {
      font: "56px Arial",
      color: "#00ff00",
      padding: { x: 10, y: 5 }
    });
  }
  specialChargeItemHitPlayer(player, buffItem) {
    buffItem.disableBody(true, true);
    this.player.increaseSpecialGuage(AMOUNT_SPECIAL_CHARGE_ITEM);
    new Audio("気弾1.mp3").play();
  }
  recoveryItemHitPlayer(player, buffItem) {
    buffItem.disableBody(true, true);
    this.player.recover(AMOUNT_RECOVERY_ITEM);
    if (this.player.sprite) this.playerRecoveryTexts.add(this.player.sprite?.x, this.player.sprite?.y, AMOUNT_RECOVERY_ITEM);
    new Audio("回復魔法1.mp3").play();
  }
  buffItemHitPlayer(player, buffItem) {
    buffItem.disableBody(true, true);
    this.player.buff(MAGNIFICATION_BUFF_ITEM);
    new Audio("ステータス上昇魔法2.mp3").play();
  }
  //　ここをいじるとスペシャル攻撃が当たったときの処理が変わるよ！
  specialHitTarget(_target, special) {
    if (this.player.isSpecialActive()) {
      const damage = this.enemy.damage(this.player.attack * 4, 40);
      if (this.enemy.sprite) this.enemyDamageTexts.add(this.enemy.sprite.x, this.enemy.sprite.y, damage);
      new Audio("爆発1.mp3").play();
    }
    if (this.enemy.getRemainingHP() <= 0) this.gameClear = true;
  }
  //　ここをいじると隕石が当たったときの処理が変わるよ！
  asteroidHitPlayer(player, asteroid) {
    const damage = this.player.damage(ATTACK_ASTEROID, 30);
    asteroid.disableBody(true, true);
    if (this.player.sprite) this.playerDamageTexts.add(this.player.sprite.x, this.player.sprite.y, damage);
    new Audio("重いパンチ1.mp3").play();
    if (this.player.getRemainingHP() <= 0) this.gameOver = true;
  }
  //　ここをいじると敵の弾が当たったときの処理が変わるよ！
  bulletHitEmitter(_emitter, bullet) {
    const damage = this.player.damage(this.enemy.attack, 15);
    bullet.disableBody(true, true);
    if (this.player.sprite) this.playerDamageTexts.add(this.player.sprite.x, this.player.sprite.y, damage);
    new Audio("打撃8.mp3").play();
    if (this.player.getRemainingHP() <= 0) this.gameOver = true;
  }
  targetFireBullet() {
    if (this.gameOver || this.gameClear) {
      return;
    }
    this.enemy.fire(this.enemyBullets);
  }
  bulletHitTarget(_target, bullet) {
    const damage = this.enemy.damage(this.player.attack);
    bullet.disableBody(true, true);
    if (this.enemy.sprite) this.enemyDamageTexts.add(this.enemy.sprite.x, this.enemy.sprite.y, damage);
    this.player.increaseSpecialGuage(200);
    new Audio("爆発1.mp3").play();
    if (this.enemy.getRemainingHP() <= 0) this.gameClear = true;
  }
  fireBullet() {
    if (this.gameOver || this.gameClear) {
      return;
    }
    this.player.fire(this.playerBullets);
  }
  update() {
    if (!this.spaceClicked) {
      if (this.cursors?.space.isDown) {
        this.spaceClicked = true;
        this.clickSpaceToStartText?.destroy();
        const text1 = this.add.text(200, 150, "よーい", {
          font: "128px Arial",
          color: "#ff0000",
          padding: { x: 10, y: 5 }
        });
        this.time.delayedCall(1500, () => {
          text1.destroy();
          new Audio("エアーホーン.mp3").play();
          const text2 = this.add.text(100, 150, "スタート！", {
            font: "128px Arial",
            color: "#ff0000",
            padding: { x: 10, y: 5 }
          });
          this.time.delayedCall(500, () => {
            text2.destroy();
            this.time.addEvent({
              delay: 500,
              callback: this.targetFireBullet,
              callbackScope: this,
              loop: true
            });
            this.started = true;
            this.physics.resume();
            this.asteroids.start();
            this.buffItems.start();
            this.recoveryItems.start();
            this.specialChargeItems.start();
            this.timeText.start();
          });
        });
      }
      return;
    }
    if (!this.cursors || !this.started) {
      return;
    }
    if (this.gameClear) {
      if (this.finished) return;
      this.finished = true;
      this.physics.pause();
      this.add.text(this.cameras.main.width / 2, this.cameras.main.height / 2 - 100, "ゲームクリア！", { font: "96px Arial", fill: "#00ff00" }).setOrigin(0.5);
      this.add.text(this.cameras.main.width / 2, this.cameras.main.height / 2 + 30, "クリアタイム：" + (this.timeText.getCurrentTime() / 1e3).toString() + "秒", { font: "32px Arial", fill: "#00ff00" }).setOrigin(0.5);
      let starsText = "";
      if (this.timeText.getCurrentTime() <= 4e4) starsText = "評価：★★★★★ 最高！";
      else if (this.timeText.getCurrentTime() <= 5e4) starsText = "評価：★★★★☆ 40秒以内で次の評価";
      else if (this.timeText.getCurrentTime() <= 6e4) starsText = "評価：★★★☆☆ 50秒以内で次の評価";
      else if (this.timeText.getCurrentTime() <= 7e4) starsText = "評価：★★☆☆☆ 60秒以内で次の評価";
      else starsText = "評価：★☆☆☆☆ 70秒以内で次の評価";
      this.add.text(this.cameras.main.width / 2, this.cameras.main.height / 2 + 130, starsText, { font: "44px Arial", fill: "#cccc00" }).setOrigin(0.5);
      this.enemyHP.setHP(this.enemy.MAX_HP, this.enemy.hp);
      this.playerHP.setHP(this.player.MAX_HP, this.player.hp);
      new Audio("ラッパのファンファーレ.mp3").play();
      return;
    }
    if (this.gameOver) {
      if (this.finished) return;
      this.finished = true;
      this.physics.pause();
      this.add.text(this.cameras.main.width / 2, this.cameras.main.height / 2, "ゲームオーバー", { font: "64px Arial", fill: "#ff0000" }).setOrigin(0.5);
      this.enemyHP.setHP(this.enemy.MAX_HP, this.enemy.hp);
      this.playerHP.setHP(this.player.MAX_HP, this.player.hp);
      new Audio("呪いの旋律.mp3").play();
      return;
    }
    this.player.update(this.cursors, this.playerBullets, this.playerSpecial);
    this.enemy.update(this.enemyHP);
    this.enemyBullets.update();
    this.playerBullets.update();
    this.enemyDamageTexts.update();
    this.playerDamageTexts.update();
    this.playerRecoveryTexts.update();
    this.asteroids.update();
    this.buffItems.update();
    this.specialChargeItems.update();
    this.recoveryItems.update();
    this.enemyHP.setHP(this.enemy.MAX_HP, this.enemy.hp);
    this.playerHP.setHP(this.player.MAX_HP, this.player.hp);
    this.playerSpecialGuage.setSpecialGuage(this.player.MAX_SPECIAL_GUAGE, this.player.specialGuage);
    this.timeText.update();
  }
}
const config = {
  type: Phaser.AUTO,
  width: 800,
  height: 600,
  parent: "app",
  physics: {
    default: "arcade",
    arcade: {
      fps: 200,
      //ここをtrueにすると当たり判定が見えるよ
      debug: false
    }
  },
  fps: {
    target: 200,
    forceSetTimeOut: true
  },
  scene: [GameScene]
};
new Phaser.Game(config);
