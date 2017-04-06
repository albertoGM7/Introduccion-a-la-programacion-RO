import java.net.*; //Se importa la libreria java.net
import java.io.*; //Se importa la libreria java.io

public class Cliente {

	public static void main(String[] args) {

		try{	
			if (args.length !=3) {
				
				System.err.println("Formato cliente -(udp|tcp) ip_servidor puerto");
				System.exit(1);
			}

			/* Recibimos los 3 argumentos / CAPA DE TRANSPORTE / IP / PUERTO  */

			String transporte=args[0].substring(1);
			InetAddress IPServer = InetAddress.getByName(args[1]);  
			int portServer = Integer.parseInt(args[2]);


			/* CLIENTE BAJO UDP */

			if(transporte.equals("udp")){
				byte[] datos = new byte[1024];
				String sms="udp";
				System.out.println(sms);
				DatagramPacket datPaquete = new DatagramPacket(datos,1024);
				DatagramPacket paqueteToSend= new DatagramPacket(sms.getBytes(),sms.length(),IPServer,portServer);
						
				 
				DatagramSocket Datsocket= new DatagramSocket();
				Datsocket.send(paqueteToSend);
				
				
				DatagramPacket receivePacket = new DatagramPacket(datos, datos.length);
				Datsocket.receive(receivePacket);
				String modifiedSentence = new String(receivePacket.getData());
				System.out.println("FROM SERVER:" + modifiedSentence);
				Datsocket.close();

			}

			/*CLIENTE BAJO TCP */
			if(transporte.equals("tcp")){
				
				Socket socket=new Socket(IPServer,portServer);
				while(true)
					
				{
					DataInputStream entrada= new DataInputStream(socket.getInputStream());
					String mensaje= (entrada.readUTF());
					System.out.println(mensaje);
				}
				
				
			}
		}
		catch(IOException e){
			System.err.println(e);
			System.err.println("Killed");
		}
	}
}

