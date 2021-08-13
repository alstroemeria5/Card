package net.entity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class SocketCapsulation implements FunctionalInterface{
	public Socket socket;
	public InputStream ins;
	public OutputStream outs;
	public InputStreamReader insreader;
	public OutputStreamWriter outswriter;
	public BufferedReader bufinsreader;
	public BufferedWriter bufoutwriter;
	public SocketCapsulation(Socket socket) throws IOException {
		this.socket=socket;
		if(query_socket_connectivity(this.socket)) {
			this.ins=socket.getInputStream();
			this.outs=socket.getOutputStream();
			this.insreader=new InputStreamReader(this.ins);
			this.outswriter=new OutputStreamWriter(this.outs);
			this.bufinsreader=new BufferedReader(this.insreader);
			this.bufoutwriter=new BufferedWriter(this.outswriter);
		}
		else{
			this.ins=null;
			this.outs=null;
			this.insreader=null;
			this.bufoutwriter=null;
			this.bufinsreader=null;
			this.bufoutwriter=null;
		}
	}
	public void close_all_stream_in_and_out() throws IOException {
		this.bufinsreader.close();
		this.bufoutwriter.close();
		this.insreader.close();
		this.outswriter.close();
		this.ins.close();
		this.outs.close();
		this.socket.close();
	}
}
