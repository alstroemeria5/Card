package net.entity;

import java.net.Socket;
import java.util.Collection;

public interface FunctionalInterface {	
	public default boolean query_socket_connectivity(Socket socket) {
		return (socket.isClosed()==false)&&(socket.isConnected()==true);
	}
	public default void remove_jeusockcap_from_ensemble(JeuSocketCapsulation jeusockcap,Collection<JeuSocketCapsulation> ensemble) {	
		ensemble.remove(jeusockcap);
	}
}
