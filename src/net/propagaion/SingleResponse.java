package net.propagaion;

public class SingleResponse {
	private ResponseType response_type;
	private String message;
	public SingleResponse(ResponseType response_type,String message) {
		setSingleResponse(response_type,message);
	}
	
	public void setSingleResponse(ResponseType response_type,String message) {
		setResponse_type(response_type);
		setMessage(message);
	}

	public ResponseType getResponse_type() {
		return response_type;
	}

	public void setResponse_type(ResponseType response_type) {
		this.response_type = response_type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
