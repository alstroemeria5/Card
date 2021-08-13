package net.propagaion;

public class SingleRequest {
	private RequestType request_type;
	private String message;
	public SingleRequest(RequestType request_type,String message) {
		setSingleRequest(request_type,message);
	}
	public void setSingleRequest(RequestType request_type,String message) {
		setRequest_type(request_type);
		setMessage(message);
	}
	public RequestType getRequest_type() {
		return request_type;
	}
	public void setRequest_type(RequestType request_type) {
		this.request_type = request_type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
