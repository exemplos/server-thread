package com.franciscocalaca.server;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class AppServerThread {
   public static void main(String[] args) {
      try {
         System.out.println("Iniciando servidor....");
         ServerSocket server = new ServerSocket(9000);
         List<Usuario> usuarios = new ArrayList<>();
         FileOutputStream fos = new FileOutputStream("chamada.txt");
         while (true) {
            Socket socket = server.accept(); 
            Runnable run = () -> {
               try {
                  Scanner scan = new Scanner(socket.getInputStream());
                  OutputStream osCon = socket.getOutputStream();
                  osCon.write("Bem vindo ao chat.\r\nPara registrar a chamada digite:\r\n \\chamada seu nome\r\n\r\n".getBytes());
                  Usuario usu = new Usuario(osCon);
                  usuarios.add(usu);
                  while (scan.hasNext()) {
                     String line = scan.nextLine();
                     System.out.printf("%s: %s\n", socket.getInetAddress(), line);
                     if(line.contains("\\chamada")){
                        if(!usu.isFezChamada()){
                           String nome = line.substring("\\chamada".length()).trim();
                           usu.setFezChamada(true);
                           usu.setNome(nome);
                           fos.write((nome + "\r\n").getBytes());
                           fos.flush();
                           osCon.write("Chamada registrada com sucesso\r\n".getBytes());
                        }else{
                           osCon.write("Voce ja fez a chamada....\r\n".getBytes());
                        }
                     }else{

                        List<Usuario> del = new ArrayList<>();
                        for(Usuario u : usuarios){
                           try {
                              u.getOs().write(((u.getNome() != null ? u.getNome() + ": " : "") + line + "\r\n").getBytes());
                           } catch (Exception e) {
                              del.add(u);
                           }
                        }
                        for(Usuario o : del){
                           usuarios.remove(del);
                           System.out.println("Removido usuario: " + o.getNome());
                        }
                     }
                  }
               } catch (IOException e) {
                  e.printStackTrace();
               } 
            };
            new Thread(run).start();
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
