package net.utcode;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class Initializer {
    public static List<String> fileList = new ArrayList<String>();
    static{
        fileList.add("Background.jpg");
        fileList.add("Buff.png");
        fileList.add("Bullet.png");
        fileList.add("Bullet_Enemy.png");
        fileList.add("Enemy.png");
        fileList.add("Enemy_Attacked.png");
        fileList.add("index.html");
        fileList.add("index.js");
        fileList.add("Obstacle.png");
        fileList.add("phaser.js");
        fileList.add("Player.png");
        fileList.add("Player_Attacked.png");
        fileList.add("Recovery.png");
        fileList.add("Special.png");
        fileList.add("Special_Charge.png");
        fileList.add("vite.svg");
        fileList.add("エアーホーン.mp3");
        fileList.add("シャキーン1.mp3");
        fileList.add("ショット.mp3");
        fileList.add("ステータス上昇魔法2.mp3");
        fileList.add("ナイフで突き刺す2.mp3");
        fileList.add("ラッパのファンファーレ.mp3");
        fileList.add("呪いの旋律.mp3");
        fileList.add("回復魔法1.mp3");
        fileList.add("打撃8.mp3");
        fileList.add("気弾1.mp3");
        fileList.add("爆発1.mp3");
        fileList.add("重いパンチ1.mp3");
        fileList.add("雷魔法2.mp3");
    }

    public static void reset(){
        copy(fileList);
    }

    public static void init(){
        if(!new File("C:/Users/" + System.getProperty("user.name") + "/HackShooter/index.html").exists())
            copy(fileList);
    }

    public static void copy(List<String> fileList){
        File appFolder = new File("C:/Users/" + System.getProperty("user.name") + "/HackShooter");
        if(!appFolder.exists()) appFolder.mkdirs();
        for(String fileName: fileList){
            Path targetFile = Paths.get(appFolder.toString() + "/" + fileName);
            InputStream is = Initializer.class.getClassLoader().getResourceAsStream("page/" + fileName);
            if(is != null){
                try{
                    Files.copy(is, targetFile, StandardCopyOption.REPLACE_EXISTING);
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
