package com.hotelmanagement.booking.utility;

public class Constants {
	
	public enum BookingStatus {
		APPROVED("Approved"),
		PENDING("Pending"),
		CANCEL("Cancel");
		
		
		private String status;

	    private BookingStatus(String status) {
	      this.status = status;
	    }

	    public String value() {
	      return this.status;
	    }    
	}
	
	public enum RoomStatus {
		FULL("Full"),
		AVAILABLE("Available");
		
		
		private String status;

	    private RoomStatus(String status) {
	      this.status = status;
	    }

	    public String value() {
	      return this.status;
	    }    
	}
	
	public enum ResponseCode {
		SUCCESS(0),
		FAILED(1);
		
		
		private int code;

	    private ResponseCode(int code) {
	      this.code = code;
	    }

	    public int value() {
	      return this.code;
	    }    
	}
	
}
