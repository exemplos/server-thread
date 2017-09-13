package com.franciscocalaca.server;

import java.io.OutputStream;

public class Usuario {
   
   private boolean fezChamada;
   
   private OutputStream os;
   
   private String nome;
   
   public Usuario(OutputStream os) {
      this.os = os;
   }

   public boolean isFezChamada() {
      return fezChamada;
   }

   public void setFezChamada(boolean fezChamada) {
      this.fezChamada = fezChamada;
   }

   public OutputStream getOs() {
      return os;
   }

   public void setOs(OutputStream os) {
      this.os = os;
   }

   public String getNome() {
      return nome;
   }

   public void setNome(String nome) {
      this.nome = nome;
   }

}
