package cliente;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {
    public  static  void main (String[] args){
        System.out.println("Iniciando Cliente...");
        new Thread( new ClienteThread("konrad")).start();
        System.out.println("Cliente Iniciado...");
    }

}
