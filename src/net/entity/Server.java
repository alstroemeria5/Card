package net.entity;


import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Timer;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;
import net.entity.StateMachine;


public class Server extends Thread{
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
			}
		}
	}
}