package cliente;

public class Cliente2 {
    public  static  void main (String[] args){
        System.out.println("Iniciando Cliente2 ...");
        new Thread( new ClienteThread("benjamin")).start();
        System.out.println("Cliente 2 Iniciado");
    }

}
