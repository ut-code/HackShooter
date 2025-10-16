package net.utcode;

import net.utcode.ui.MainFrame;

import javax.swing.*;

//TIP コードを<b>実行</b>するには、<shortcut actionId="Run"/> を押すか
// ガターの <icon src="AllIcons.Actions.Execute"/> アイコンをクリックします。
public class Main {

    static final boolean IS_WINDOWS = System.getProperty("os.name").toLowerCase().startsWith("windows");
    static final boolean IS_LINUX = System.getProperty("os.name").toLowerCase().startsWith("linux");
    static final boolean IS_MAC = System.getProperty("os.name").toLowerCase().startsWith("mac");

    static JFrame mainFrame;

    static void main() {
        if(IS_WINDOWS) {
            System.loadLibrary("HackShooter");
            new Initializer().copyPage();
        }

        //TIP ハイライトされたテキストにキャレットがある状態で <shortcut actionId="ShowIntentionActions"/> を押すと
        // IntelliJ IDEA によるその修正案を確認できます。


        SwingUtilities.invokeLater(() -> {
            mainFrame = new MainFrame("HackShooter");
        });
    }
}
