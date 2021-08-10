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
	private static int port_de_server;
	public static int get_server_port() {
		return port_de_server;
	}
	public static void set_server_port(int server_port) {
		port_de_server=server_port;
	}
	static {
		set_server_port(1988);
	}
	public static void main(String[] args) {
		Server server=new Server();
		server.setPriority(MAX_PRIORITY);
		server.start();
		

		while(true) {
			try {
				Socket socket=server.socket_de_server.accept();
				socket.setSoTimeout(30*1000);
				System.out.printf("Client from %s.\n",socket.getInetAddress().toString());
				BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
//				byte[] b=new byte[1024];
//				int length;
				String data="";
				data=in.readLine();
				if(data.equals("Hello World,RoyaumeEric!")) {
					BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					SingleResponse response=new SingleResponse(ResponseType.ClientGrant,"Welcome to RoyaumeEric!!!");
					JSONObject json_response=new JSONObject(response);
					server.writeout.accept(out,json_response.toString()); 
					out.flush();
					synchronized(server.socket_ensemble) {
						server.socket_ensemble.add(socket);
					}
				}
				else {
					BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
					out.write(("You don't know the passphrase. Get the fuckout???"+"\n").getBytes());
					out.close();
					in.close();
					socket.close();
				}
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	public ServerSocket socket_de_server;
	public CopyOnWriteArrayList<Socket> socket_ensemble;
	public Server() {
		socket_ensemble=new CopyOnWriteArrayList<Socket>();
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
			synchronized(socket_ensemble) {
				for(Socket socket:socket_ensemble) {
					try {
						if(socket.getInputStream().available()>0) {
							BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
							String data=in.readLine();
							BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
							writeout.accept(out, data);
							out.flush();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
}
