import java.net.*; //Se importa la libreria java.net
import java.util.ArrayList;
import java.io.*; //Se importa la libreria java.io

public class Servidor {
static String nombreArchivo;
	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Formato <Puerto> nombre_archivo");
			System.exit(1);
		}
		//Recibimos por argumentos el puerto del Servidor y el nombre del fichero
		int portServer = Integer.parseInt(args[0]);
		   nombreArchivo=args[1];
		   
		   //CREAMOS UN THREAD PARA ESCUCHAR SOCKET UDP
		Thread udplisten = new Thread(){
			public void run() {
				try{

					DatagramSocket datSocket = new DatagramSocket(portServer);

					byte[] receiveData = new byte[1024];
					byte[] sendData = new byte[1024];
					DatagramPacket UDPpaquete = new DatagramPacket(receiveData, receiveData.length);
					System.out.println("Esperando...");	
					while(true){
						datSocket.receive(UDPpaquete);
						System.out.println("Envio de proverbio bajo UDP");
						String proverbios= leerArc();

						sendData = proverbios.getBytes();
						InetAddress IPAddress = UDPpaquete.getAddress();
						int port = UDPpaquete.getPort();
						DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
						datSocket.send(sendPacket);

					}

				}
				catch(IOException e){

				}
			}

		};

		udplisten.start();

//CREAMOS UN THREAD PARA ESCUCHAR UN SOCKET TCP PERSISTENTE


		Thread tcpListen = new Thread(){
			public void run(){
				try{
					ServerSocket servSocket = new ServerSocket(portServer);

					while(true){
					Socket socket= servSocket.accept();
					String proverbioTCP=leerArc();
					Client_Thread thread = new Client_Thread(socket,proverbioTCP);
					thread.start();
					System.out.println("Se creó un nuevo hilo TCP persistente con ID:" +thread.getId());

					}
				}
				catch(IOException e){

					System.out.println(e);
				}
			}
		};
		tcpListen.start();



	}

	public static String leerArc(){

		ArrayList<String> lineas =new ArrayList();
		try {
			FileReader fr = new FileReader(nombreArchivo);
			BufferedReader br = new BufferedReader(fr);

			String linea;
			while((linea = br.readLine()) != null)
				lineas.add(linea);
			//System.out.println(linea);

			fr.close();

		}
		catch(Exception e) {
			System.out.println("Excepcion leyendo fichero  " + e);
		}

		Integer valorDado = (int) Math.floor(Math.random()*lineas.size());


		return lineas.get(valorDado);
	}
}


