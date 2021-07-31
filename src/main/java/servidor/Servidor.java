package servidor;

public class Servidor {
    public  static  void  main(String[] args){
        Thread serverThread = new Thread(new ServidorThread());
       // System.out.println("Iniciando servidor...");
        serverThread.start();
        //System.out.println("Servidor iniciado!");
    }
}

