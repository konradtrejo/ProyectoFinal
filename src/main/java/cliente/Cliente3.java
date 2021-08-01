package cliente;

public class Cliente3 {
    public  static  void main (String[] args){
        System.out.println("Iniciando Cliente3 ...");
        new Thread( new ClienteThread("Trejo")).start();
        System.out.println("Cliente 3 Iniciado!");
    }

}



