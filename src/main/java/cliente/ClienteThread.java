package cliente;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class ClienteThread implements Runnable{
    private static  final int  SERVER_PORT=5000;
    private  static  final String SERVER_IP="Localhost";
    private Socket socket;
    private String sendMessage;
    private DataOutputStream salidaServidor;
    private String name;
    public  ClienteThread(String name){
        this.name= name;
    }
    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try{
            InetAddress serverAddr =InetAddress.getByName(SERVER_IP);
            socket = new Socket(serverAddr,SERVER_PORT);
            //message_Send();
            SendJson();
            ReceivedJson();
        }catch (UnknownHostException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    public void message_Send() throws IOException {
        salidaServidor = new DataOutputStream(socket.getOutputStream());
        for (int i=0; i<2;i++){
            salidaServidor.writeUTF("Este es el mensaje "+ (i+1) +"\n");
        }

    }
    public void SendJson() throws IOException {
        String name=this.name;
        String sex="Male";
        int edad= 20;
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("nombre",name);
        map.put("sexo",sex);
        map.put("edad",edad);
        JSONObject json =new JSONObject(map);
        String jsonString = "";
        jsonString = json.toString();
        byte[] jsonByte = jsonString.getBytes();
        DataOutputStream outputStream = null;
        outputStream = new DataOutputStream(socket.getOutputStream());
        System.out.println ( "data length is made:" + jsonByte.length);
        outputStream.write(jsonByte);
        outputStream.flush();
        System.out.println ( "data transfer complete");
        System.out.println(jsonByte);

    }
    public  void ReceivedJson() throws IOException {

        PrintWriter printWriter=null;
        BufferedReader br =null;
        printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        br= new BufferedReader(new InputStreamReader(socket.getInputStream()));
        JSONTokener jsonTokener = new JSONTokener(br);
        JSONObject obj = new JSONObject(jsonTokener);
        Map<String,Object> map = obj.toMap();
        for (Map.Entry<String,Object> entry : map.entrySet()){
            System.out.println(entry.getKey()+ "= "+ entry.getValue());
        }
        System.out.println(" nomre: "+ obj.get("nombre"));
    }
}
