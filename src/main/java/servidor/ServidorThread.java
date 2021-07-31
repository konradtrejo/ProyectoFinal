package servidor;

import Request.PersonaRequest;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.swing.plaf.synth.SynthDesktopIconUI;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServidorThread implements  Runnable {
    public static final int SERVERPORT = 5000;
    List<Socket> sockets= new ArrayList<>();
    private  static final String CHARSET="UTF-8";
    private ServerSocket serverSocket=null;
    private DataOutputStream salidaCliente;
    private String mensajeServidor;
    private PersonaRequest personaRequest;
    public  ServidorThread(){
        try{
            serverSocket = new ServerSocket(SERVERPORT);
            System.out.println("Socket service stared successfully!");
        }catch (IOException e){
            System.out.println("Inialization of socket service exception:\n");
            e.printStackTrace();
        }
    }
    @Override
    public void run() {

      try{
          while (true) {

              Socket socket = serverSocket.accept();
              socket.setKeepAlive(true);
              String message =socket.getInetAddress().getHostAddress().toString();
              System.out.println("Client: "+ message+" Connected");

              recivedJson(socket);
              SendJson(socket);
              //SendMessageToClient(socket,message);
              sockets.add(socket);

          }
      }catch (Exception e){
          System.out.println("An exception occurred during Socket Operation:\n");
          e.printStackTrace();
      }

    }
    public void SendMessageToClient(Socket socket, String message) {
       try{
           salidaCliente= new DataOutputStream(socket.getOutputStream());
           salidaCliente.writeUTF("Peticion recibida y aceptada" );
           BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

           while ((mensajeServidor=entrada.readLine()) != null){
               System.out.println(mensajeServidor);
           }

       }catch (Exception e){
           System.out.println(e.getMessage());
       }

    }
    public void RecivMessageToClient(Socket socket, String message) {
        try{

            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while ((mensajeServidor=entrada.readLine()) != null){
                System.out.println(mensajeServidor);
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    public  void recivedJson(Socket socket){
        try{
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            JSONTokener jsonTokener = new JSONTokener(br);
            JSONObject obj = new JSONObject(jsonTokener);
            Map<String,Object> map = obj.toMap();
            personaRequest = new PersonaRequest();
            for (Map.Entry<String,Object> entry : map.entrySet()){
                System.out.println(entry.getKey()+ "= "+ entry.getValue());
            }
            personaRequest.nombre = (String) obj.get("nombre");
            personaRequest.sexo = (String) obj.get("sexo");
            personaRequest.edad = (int) obj.get("edad");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    public void SendJson(Socket socket){
        JSONObject jsonObject =new JSONObject();
        jsonObject.put("Message","Enviado del servidor");
        jsonObject.put("nombre", personaRequest.nombre);
        try(OutputStreamWriter out = new OutputStreamWriter(
                socket.getOutputStream(), StandardCharsets.UTF_8
        )){
            out.write(jsonObject.toString());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
