# HackShooter
## 開発
これは **HackShooter** のデスクトップアプリ部分のリポジトリです。主にJavaとC++で書かれています。が、Javaとかいうレガシー言語書いてらんねえよと思ったら比較的簡単にKotlinも導入できると思います。  
ド素人が作っているため多分慣例に従ってなかったりありえないくらいごちゃごちゃだったりして大変なことになっていると思います。  
Windowsでの実行しか想定していません。マルチプラットフォームも頑張れば不可能ではないと思いますが、~~めんどくさいのと~~Macを持っていないので諦めます。

## セットアップ
* 想定しているIDE
    * JetBrains IntelliJ IDEA Community版  
        https://www.jetbrains.com/ja-jp/idea/download/
    * Microsoft Visual Studio Community  
        https://visualstudio.microsoft.com/ja/free-developer-offers/
* PCにインストールするもの  
    * JDK25  
        https://www.oracle.com/jp/java/technologies/downloads/  
        **インストールの手順**  
        * 自分のOS、アーキテクチャに合ったCompressed Archiveをダウンロードし、適当な場所に解凍する。(自分はC:\ProgramFilesの下に入れてます)
        * (以下はWindows向けです、それ以外は持ってないのでわかりません)Windowsの設定→システム→バージョン情報→システムの詳細設定(デバイスの仕様の項目の下のほうにあります)→環境変数  
        を開き、システム環境変数の、
            * Pathを選択→編集→新規→(jdkを解凍した場所)\jdk-25\bin を追加
            * 新規→変数名:JAVA_HOME、変数値:(jdkを解凍した場所)\jdk-25 を追加  
        * 以上で設定は終わりだと思います

## 使い方
ルートのフォルダの下のフォルダについて説明していきます。
* java  
    その名の通りJavaのコードを書いていきます。この下のHackShooterフォルダをIntelliJ IDEAで何とかしてインポートしたら、コードをポチポチして実行しましょう。  
    gradle run でとりあえずアプリを実行でき、
    gradle jpackage で後述のbuildフォルダに動くアプリケーションを出力します。
* native  
    JNI(Java Native Interface)を使って、OS(Windows)の機能で実装する部分を書いていきます。  
    native\windows\HackShooter\HackShooter.slnをVisual Studioで起動できると思います。コードを書いたら実行ボタンを押してください。
* build  
    ビルドしたものが入ります。  
    build\app\windowsの下に完成したアプリ、
    build\bin\windowsの下にVisual Studioが生成したものとJarファイル、  
    build\jni-headersの下にJNIで生成されたヘッダファイルが入ります。この構成でよかったのか自信はありません。
### ビルドの順序    
1. IntelliJ側でgradle buildを実行します。
2. Visual Studio側で実行します。(うまくいってても有効なWin32アプリケーションではありませんとか文句言われます)  
3. IntelliJ側に戻ってgradle runをすれば多分動きます。gradle jpackageをすればアプリケーションが生成されると思います。

## 注意点
buildフォルダを直接いじるとgradle君がいうこと聞かなくなって操作がうまくいかなくなることがあります。**そうなっちゃったらjava\HackShooter\\.gradleを消してください。** これはフォルダの設計が下手な私が悪いです。詳しい方どうやったらよかったか教えてください。
    
