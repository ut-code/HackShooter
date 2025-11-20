package net.utcode.server;

import net.utcode.Initializer;

import javax.swing.*;
import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class ServerThread extends Thread{
    @Override
    public void run(){

        try(ServerSocket ss = new ServerSocket(80, 300);){
            while(true){
                Socket s = ss.accept();
                BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
                String request = br.readLine();
                String[] requestArray = request.split("\\s+");
                String requestedFileName = URLDecoder.decode(requestArray[1], StandardCharsets.UTF_8);
                OutputStream os = s.getOutputStream();
                PrintWriter pw = new PrintWriter(os);
                byte[] buff = new byte[8192];
                if(requestArray[1].equals("/")){
                    try(FileInputStream is = new FileInputStream("C:/Users/" + System.getProperty("user.name") + "/HackShooter/index.html")){
                        pw.println("HTTP/1.1 200 OK");
                        pw.println("content-type: text/html");
                        pw.println("Connection: close");
                        pw.println();
                        pw.flush();
                        while(true){
                            int byteNum = is.read(buff);
                            if(byteNum == -1) break;
                            os.write(buff, 0, byteNum);
                        }
                        os.flush();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    for(String fileName: Initializer.fileList){
                        if(requestedFileName.contains(fileName)){
                            pw.println("HTTP/1.1 200 OK");
                            if(fileName.endsWith(".js")){
                                pw.println("content-type: text/javascript");
                            }
                            pw.println("Connection: close");
                            pw.println();
                            pw.flush();
                            try(FileInputStream is = new FileInputStream("C:/Users/" + System.getProperty("user.name") + "/HackShooter/" + fileName)){
                                while(true){
                                    int byteNum = is.read(buff);
                                    if(byteNum == -1) break;
                                    os.write(buff, 0, byteNum);
                                }
                                os.flush();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        }
                    }
                }
                s.close();
            }
        } catch (BindException e) {
            JOptionPane.showMessageDialog(null, "80番ポートがすでに使われています", "エラー", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch (IOException e){
            JOptionPane.showMessageDialog(null, "エラーが発生しました。プログラムを終了します。", "エラー", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

    }
}
