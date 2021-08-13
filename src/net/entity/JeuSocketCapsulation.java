package net.entity;

import java.io.IOException;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

public class JeuSocketCapsulation extends TimerTask implements FunctionalInterface{
	public SocketCapsulation sockcap;
	public JeuState jeustate;
	public long check_time;
	public int availability;
	public Timer timer;
	public Collection<Timer> timer_queue;
	public Collection<JeuSocketCapsulation> jeusockcap_ensemble;
	public JeuSocketCapsulation(SocketCapsulation sockcap,JeuState jeustate,long check_time,Timer timer,Collection<Timer> timer_queue,Collection<JeuSocketCapsulation> jeusockcap_ensemble) throws IOException{
		this.sockcap=sockcap;
		this.jeustate=jeustate;
		this.check_time=check_time;
		this.timer=timer;
		this.timer_queue=timer_queue;
		this.jeusockcap_ensemble=jeusockcap_ensemble;
		if(sockcap.socket.isConnected()) {
			this.availability=sockcap.socket.getInputStream().available();
			timer_queue.add(timer);
			timer.schedule(this,check_time,check_time);
		}
		else {
			this.availability=0;
		}
	}
	public void run() {
		int before=availability;
		int after=0;
		try {
			if(query_socket_connectivity(sockcap.socket)) {
				after=sockcap.ins.available();
				if(!((before==0)&&(after==0))){
					;
				}
				else {
					this.timer.cancel();
					timer_queue.remove(this.timer);
					sockcap.close_all_stream_in_and_out();
					remove_jeusockcap_from_ensemble(this,this.jeusockcap_ensemble);
					return;
					//remove SocketCapsulation
					//remove this 
				}
			}
			else {
				this.timer.cancel();
				timer_queue.remove(this.timer);
				remove_jeusockcap_from_ensemble(this,this.jeusockcap_ensemble);
				return;
				//remove SocketCapsulation
				//remove this
			}
			
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}
