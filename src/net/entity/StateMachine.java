package net.entity;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.function.BiConsumer;

import org.json.JSONObject;

import net.propagaion.ResponseType;
import net.propagaion.SingleResponse;

public class StateMachine {
	private BiConsumer<BufferedWriter,String> writeout=(BufferedWriter out,String str)->{try {
		out.write(str);
		out.newLine();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}};
	public StateMachine() {
		
	}
	public void interwine(Collection<JeuSocketCapsulation> sockcap_ensemble) {
		for(JeuSocketCapsulation jeusockcap:sockcap_ensemble) {
			try {
				if((jeusockcap.sockcap.socket.isClosed()==false)&&(jeusockcap.sockcap.socket.isConnected()==true)) {
					if(jeusockcap.jeustate==JeuState.WaitForSocketAuthentication) {
						SingleResponse response=new SingleResponse(ResponseType.ClientGrant,"Welcome to RoyaumeEric!!!");
						JSONObject json_response=new JSONObject(response);
						writeout.accept(jeusockcap.sockcap.bufoutwriter,json_response.toString());
						jeusockcap.sockcap.bufoutwriter.flush();
						jeusockcap.jeustate=JeuState.GrantForSocketAuthentication;
					}
				}
				else {
					continue;
				}
			}
			catch(IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
