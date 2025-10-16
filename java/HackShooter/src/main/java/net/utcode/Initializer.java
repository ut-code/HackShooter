package net.utcode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Initializer {
    public boolean copyPage(){
        try{
            Path htmlPath = Paths.get(getClass().getClassLoader().getResource("page/index.html").toURI());
            File appDirFile = new File("C:/Users/" + System.getProperty("user.name") + "/HackShooter/");
            if(!appDirFile.exists()){
                appDirFile.mkdirs();
            }
            Path appDir = Paths.get(appDirFile.toString() + "/index.html");
            Files.copy(htmlPath, appDir, StandardCopyOption.REPLACE_EXISTING);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
