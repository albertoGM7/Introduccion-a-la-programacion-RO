import java.util.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
public class Client_Thread extends Thread {


Socket server;
Integer id;	
String proverbioTCP;
public Client_Thread(Socket server, String proverbioTCP){
	this.server=server;
	this.id=(int) this.getId();
	this.proverbioTCP=proverbioTCP;
}



public void run(){
try{
	
	while(true){
		DataOutputStream salida= new DataOutputStream(server.getOutputStream());
		//Esperamos 3 segundoS por cada mensaje generado
		Thread.sleep(3000);
		//Devolvemos la hora
		salida.writeUTF(Servidor.leerArc());
		
		}
	
}
catch(IOException e){
	System.out.println("Se cerro el hilo con ID: "+id);
} catch (InterruptedException e) {
	System.out.println(e);
	e.printStackTrace();
}
}
}

