package net.entity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Timer;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;

import org.json.JSONObject;

import net.propagaion.ResponseType;
import net.propagaion.SingleRequest;
import net.propagaion.SingleResponse;
import net.propagaion.ResponseType;


public class Server extends Thread{
	private BiConsumer<BufferedWriter,String> writeout=(BufferedWriter out,String str)->{try {
		out.write(str);
		out.newLine();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}};
	private static StateMachine state_machine;
	private static int port_de_server;
	public static int get_server_port() {
		return port_de_server;
	}
	public static void set_server_port(int server_port) {
		port_de_server=server_port;
	}
	static {
		set_server_port(1988);
		state_machine=new StateMachine();
	}
	public static void main(String[] args) {
		Server server=new Server();
		server.setPriority(MAX_PRIORITY);
		server.start();
		Collection<Timer> timer_queue=new CopyOnWriteArrayList<Timer>();

		while(true) {
			try {
				Socket socket=server.socket_de_server.accept();
				System.out.printf("Client from %s.\n",socket.getInetAddress().toString());
				SocketCapsulation sockcap=new SocketCapsulation(socket);
				JeuSocketCapsulation jeusockcap=new JeuSocketCapsulation(sockcap,JeuState.WaitForSocketAuthentication, 30000L, new Timer(), timer_queue,server.jeusockcap_ensemble);
				server.jeusockcap_ensemble.add(jeusockcap);
//				
////				byte[] b=new byte[1024];
////				int length;
//				String data="";
//				data=sockcap.bufinsreader.readLine();
//				if(data.equals("Hello World,RoyaumeEric!")) {
//					SingleResponse response=new SingleResponse(ResponseType.ClientGrant,"Welcome to RoyaumeEric!!!");
//					JSONObject json_response=new JSONObject(response);
//					server.writeout.accept(sockcap.bufoutwriter,json_response.toString()); 
//					sockcap.bufoutwriter.flush();
//					synchronized(server.socket_ensemble) {
//						server.socket_ensemble.add(sockcap.socket);
//					}
//				}
//				else {
//					sockcap.bufoutwriter.write("You don't know the passphrase. Get the fuckout???"+"\n");
//					sockcap.bufoutwriter.close();
//					sockcap.bufinsreader.close();
//					socket.close();
//				}
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	public ServerSocket socket_de_server;
	public CopyOnWriteArrayList<JeuSocketCapsulation> jeusockcap_ensemble;
	public Server() {
		this.jeusockcap_ensemble=new CopyOnWriteArrayList<JeuSocketCapsulation>();
		try {
			socket_de_server=new ServerSocket(get_server_port());
		}
		catch(IOException e) {
			System.out.printf("Server starting on port %d fails\n",Server.get_server_port());
			System.out.printf("IOException: %s\n",e.toString());
			e.printStackTrace();
		}
	}
	public void run() {
		while(true) {
			synchronized(jeusockcap_ensemble) {
				state_machine.interwine(jeusockcap_ensemble);
//				for(JeuSocketCapsulation jeusocketcap:jeusockcap_ensemble) {
//					try {
//						if(!socket.isClosed()) {
//							if(socket.getInputStream().available()>0) {
//								BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
//								String data=in.readLine();
//								BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//								writeout.accept(out, data);
//								out.flush();
//							}
//							
//						}
//						else {
//							continue;
//						}
//					}
//					catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}	
			}
		}
	}
}