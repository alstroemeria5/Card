package net.propagaion;

public class SingleRequest {
	private RequestType request_type;
	private String message;
	public SingleRequest(RequestType request_type,String message) {
		this.request_type=request_type;
		this.message=message;
	}
}
