package com.hotelmanagement.booking.controller;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotelmanagement.booking.dto.BookingDetailDto;
import com.hotelmanagement.booking.dto.BookingDto;
import com.hotelmanagement.booking.dto.CommanApiResponse;
import com.hotelmanagement.booking.dto.Hotel;
import com.hotelmanagement.booking.dto.HotelResponseDto;
import com.hotelmanagement.booking.dto.UpdateBookingStatusRequestDto;
import com.hotelmanagement.booking.dto.User;
import com.hotelmanagement.booking.dto.UsersResponseDto;
import com.hotelmanagement.booking.entity.Booking;
import com.hotelmanagement.booking.exception.BookingNotFoundException;
import com.hotelmanagement.booking.external.HotelService;
import com.hotelmanagement.booking.external.UserService;
import com.hotelmanagement.booking.service.BookingService;
import com.hotelmanagement.booking.utility.Constants.BookingStatus;
import com.hotelmanagement.booking.utility.Constants.ResponseCode;
import com.hotelmanagement.booking.utility.Helper;

@Transactional
@RestController
@RequestMapping("api/book/hotel")
//@CrossOrigin(origins = "http://localhost:3000")
public class BookingController {

	Logger LOG = LoggerFactory.getLogger(BookingController.class);

	@Autowired
	private BookingService bookingService;

	@Autowired
	private HotelService hotelService;

	@Autowired
	private UserService userService;

	@PostMapping("/")
	public ResponseEntity<?> register(Booking booking) {
		LOG.info("Recieved request for booking hotel");

		System.out.println(booking);

		CommanApiResponse response = new CommanApiResponse();

		if (booking == null) {
//			response.setResponseCode(ResponseCode.FAILED.value());
//			response.setResponseMessage("Hotel Booking Failed");
//			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);

			throw new BookingNotFoundException();
		}

		if (booking.getUserId() == 0) {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("User is not not looged in");
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}

		if (booking.getHotelId() == 0) {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("Hotel not found to Book");
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}

		HotelResponseDto hotelResponse = this.hotelService.fetchHotel(booking.getHotelId());

		if (hotelResponse == null) {
			throw new RuntimeException("hotel service is down!!!");
		}

		Hotel hotel = hotelResponse.getHotel();

		if (hotel == null) {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("No Hotel present with this Id");
		}

		booking.setStatus(BookingStatus.PENDING.value());

		booking.setBookingId(Helper.getAlphaNumericId());

		Booking bookedHotel = this.bookingService.bookHotel(booking);

		if (bookedHotel != null) {
			response.setResponseCode(ResponseCode.SUCCESS.value());
			response.setResponseMessage(
					"Hotel Booked Added Successfully, Please Check Approval Status on Booking Option");
			return new ResponseEntity(response, HttpStatus.OK);
		}

		else {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("Failed to Book Hotel");
			return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/fetch/all")
	public ResponseEntity<?> fetchAllHotelBooking() {
		LOG.info("Recieved request for fetch all booking");

		BookingDetailDto response = new BookingDetailDto();

		List<BookingDto> bookings = new ArrayList<>();

		List<Booking> allBookings = this.bookingService.getAllBookings();

		for (Booking booking : allBookings) {

			BookingDto dto = new BookingDto();

			dto.setBookingId(booking.getBookingId());
			dto.setCheckIn(booking.getCheckIn());
			dto.setCheckOut(booking.getCheckOut());

			UsersResponseDto customerResponse = this.userService.fetchUser(booking.getUserId());

			if (customerResponse == null) {
				throw new RuntimeException("user service is down!!!");
			}

			User customer = customerResponse.getUser();
			dto.setCustomerName(customer.getFirstName() + " " + customer.getLastName());

			HotelResponseDto hotelResponse = this.hotelService.fetchHotel(booking.getHotelId());

			if (hotelResponse == null) {
				throw new RuntimeException("hotel service is down!!!");
			}

			Hotel hotel = hotelResponse.getHotel();

			UsersResponseDto hotelUserResponse = this.userService.fetchUser(booking.getUserId());

			if (hotelUserResponse == null) {
				throw new RuntimeException("user service is down!!!");
			}

			User hotelUser = hotelUserResponse.getUser();

			dto.setHotelEmail(hotelUser.getEmailId());
			dto.setHotelContact(hotelUser.getContact());
			dto.setHotelId(hotel.getId());
			dto.setStatus(booking.getStatus());
			dto.setTotalDay(booking.getTotalDay());
			dto.setTotalRoom(booking.getTotalRoom());
			dto.setUserId(customer.getId());
			dto.setHotelName(hotel.getName());
			dto.setHotelImage(hotel.getImage1());
			dto.setCustomerContact(customer.getContact());
			dto.setTotalAmount(String.valueOf(hotel.getPricePerDay() * booking.getTotalRoom() * booking.getTotalDay()));
			dto.setId(booking.getId());

			bookings.add(dto);
		}

		response.setBookings(bookings);
		response.setResponseCode(ResponseCode.SUCCESS.value());
		response.setResponseMessage("Booking Fetched Successfully");
		return new ResponseEntity(response, HttpStatus.OK);

	}

	@GetMapping("/fetch")
	public ResponseEntity<?> fetchMyBooking(@RequestParam("userId") int userId) {
		LOG.info("Recieved request for fetch all booking");

		BookingDetailDto response = new BookingDetailDto();

		List<BookingDto> bookings = new ArrayList<>();

		List<Booking> allBookings = this.bookingService.getMyBookings(userId);

		for (Booking booking : allBookings) {

			BookingDto dto = new BookingDto();

			dto.setBookingId(booking.getBookingId());
			dto.setCheckIn(booking.getCheckIn());
			dto.setCheckOut(booking.getCheckOut());

			UsersResponseDto customerResponse = this.userService.fetchUser(booking.getUserId());

			if (customerResponse == null) {
				throw new RuntimeException("user service is down!!!");
			}

			User customer = customerResponse.getUser();
			dto.setCustomerName(customer.getFirstName() + " " + customer.getLastName());

			HotelResponseDto hotelResponse = this.hotelService.fetchHotel(booking.getHotelId());

			if (hotelResponse == null) {
				throw new RuntimeException("hotel service is down!!!");
			}

			Hotel hotel = hotelResponse.getHotel();

			UsersResponseDto hotelUserResponse = this.userService.fetchUser(booking.getUserId());

			if (hotelUserResponse == null) {
				throw new RuntimeException("user service is down!!!");
			}

			User hotelUser = hotelUserResponse.getUser();
			dto.setHotelEmail(hotelUser.getEmailId());
			dto.setHotelContact(hotelUser.getContact());
			dto.setHotelId(hotel.getId());
			dto.setStatus(booking.getStatus());
			dto.setTotalDay(booking.getTotalDay());
			dto.setTotalRoom(booking.getTotalRoom());
			dto.setUserId(customer.getId());
			dto.setHotelName(hotel.getName());
			dto.setHotelImage(hotel.getImage1());
			dto.setCustomerContact(customer.getContact());
			dto.setTotalAmount(String.valueOf(hotel.getPricePerDay() * booking.getTotalRoom() * booking.getTotalDay()));
			dto.setId(booking.getId());

			bookings.add(dto);
		}

		response.setBookings(bookings);
		response.setResponseCode(ResponseCode.SUCCESS.value());
		response.setResponseMessage("Booking Fetched Successfully");
		return new ResponseEntity(response, HttpStatus.OK);

	}

	@GetMapping("/fetch/id")
	public ResponseEntity<?> fetchBookingById(@RequestParam("bookingId") int bookingId) {
		LOG.info("Recieved request for fetch booking by Id");

		Booking booking = this.bookingService.getBookingById(bookingId);

		if (booking == null) {
			throw new BookingNotFoundException();
		}

		BookingDto dto = new BookingDto();

		dto.setBookingId(booking.getBookingId());
		dto.setCheckIn(booking.getCheckIn());
		dto.setCheckOut(booking.getCheckOut());

		UsersResponseDto customerResponse = this.userService.fetchUser(booking.getUserId());

		if (customerResponse == null) {
			throw new RuntimeException("user service is down!!!");
		}

		User customer = customerResponse.getUser();
		dto.setCustomerName(customer.getFirstName() + " " + customer.getLastName());

		HotelResponseDto hotelResponse = this.hotelService.fetchHotel(booking.getHotelId());

		if (hotelResponse == null) {
			throw new RuntimeException("hotel service is down!!!");
		}

		Hotel hotel = hotelResponse.getHotel();

		UsersResponseDto hotelUserResponse = this.userService.fetchUser(booking.getUserId());

		if (hotelUserResponse == null) {
			throw new RuntimeException("user service is down!!!");
		}

		User hotelUser = hotelUserResponse.getUser();
		dto.setHotelEmail(hotelUser.getEmailId());
		dto.setHotelContact(hotelUser.getContact());
		dto.setHotelId(hotel.getId());
		dto.setStatus(booking.getStatus());
		dto.setTotalDay(booking.getTotalDay());
		dto.setTotalRoom(booking.getTotalRoom());
		dto.setUserId(customer.getId());
		dto.setHotelName(hotel.getName());
		dto.setHotelImage(hotel.getImage1());
		dto.setCustomerContact(customer.getContact());
		dto.setTotalAmount(String.valueOf(hotel.getPricePerDay() * booking.getTotalRoom() * booking.getTotalDay()));
		dto.setId(booking.getId());

		return new ResponseEntity(dto, HttpStatus.OK);

	}

	@GetMapping("/fetch/bookings")
	public ResponseEntity<?> fetchMyHotelBooking(@RequestParam("hotelId") int hotelId) {
		LOG.info("Recieved request for fetch all booking");

		BookingDetailDto response = new BookingDetailDto();

		List<BookingDto> bookings = new ArrayList<>();

		List<Booking> allBookings = this.bookingService.getMyHotelBookings(hotelId);

		for (Booking booking : allBookings) {

			BookingDto dto = new BookingDto();

			dto.setBookingId(booking.getBookingId());
			dto.setCheckIn(booking.getCheckIn());
			dto.setCheckOut(booking.getCheckOut());

			UsersResponseDto customerResponse = this.userService.fetchUser(booking.getUserId());

			if (customerResponse == null) {
				throw new RuntimeException("user service is down!!!");
			}

			User customer = customerResponse.getUser();
			dto.setCustomerName(customer.getFirstName() + " " + customer.getLastName());

			HotelResponseDto hotelResponse = this.hotelService.fetchHotel(booking.getHotelId());

			if (hotelResponse == null) {
				throw new RuntimeException("hotel service is down!!!");
			}

			Hotel hotel = hotelResponse.getHotel();

			UsersResponseDto hotelUserResponse = this.userService.fetchUser(booking.getUserId());

			if (hotelUserResponse == null) {
				throw new RuntimeException("user service is down!!!");
			}

			User hotelUser = hotelUserResponse.getUser();
			dto.setHotelEmail(hotelUser.getEmailId());
			dto.setHotelContact(hotelUser.getContact());
			dto.setHotelId(hotel.getId());
			dto.setStatus(booking.getStatus());
			dto.setTotalDay(booking.getTotalDay());
			dto.setTotalRoom(booking.getTotalRoom());
			dto.setUserId(customer.getId());
			dto.setHotelName(hotel.getName());
			dto.setHotelImage(hotel.getImage1());
			dto.setCustomerContact(customer.getContact());
			dto.setTotalAmount(String.valueOf(hotel.getPricePerDay() * booking.getTotalRoom() * booking.getTotalDay()));
			dto.setId(booking.getId());

			bookings.add(dto);
		}

		response.setBookings(bookings);
		response.setResponseCode(ResponseCode.SUCCESS.value());
		response.setResponseMessage("Booking Fetched Successfully");
		return new ResponseEntity(response, HttpStatus.OK);

	}

	@GetMapping("/fetch/status")
	public ResponseEntity<?> fetchAllBookingStatus() {
		LOG.info("Recieved request for fetch all booking status");

		List<String> response = new ArrayList<>();

		for (BookingStatus status : BookingStatus.values()) {
			response.add(status.value());
		}

		return new ResponseEntity(response, HttpStatus.OK);

	}

	@PostMapping("/update/status")
	public ResponseEntity<?> updateHotelBookingStatus(@RequestBody UpdateBookingStatusRequestDto request) {

		LOG.info("Recieved request for updating the Hotel Booking Status");

		CommanApiResponse response = new CommanApiResponse();

		Booking b = this.bookingService.getBookingById(request.getBookingId());

		if (b == null) {
			throw new BookingNotFoundException();
		}

		if (request.getStatus().equals("") || request.getStatus() == null) {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("Booking Status can not be empty");
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}

		b.setStatus(request.getStatus());
		this.bookingService.bookHotel(b);

		response.setResponseCode(ResponseCode.SUCCESS.value());
		response.setResponseMessage("Booking Status Updated");
		return new ResponseEntity(response, HttpStatus.OK);

	}

}
