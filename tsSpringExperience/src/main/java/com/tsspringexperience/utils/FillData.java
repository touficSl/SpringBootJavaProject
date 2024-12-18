package com.tsspringexperience.utils;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tsspringexperience.entities.Booking;
import com.tsspringexperience.entities.Distributor;
import com.tsspringexperience.entities.Hotel;
import com.tsspringexperience.entities.Occupancy;
import com.tsspringexperience.entities.Room;
import com.tsspringexperience.entities.RoomRate;
import com.tsspringexperience.entities.Stay;
import com.tsspringexperience.jaxb.BookingRqRs;
import com.tsspringexperience.jaxb.HotelResponse;
import com.tsspringexperience.jaxb.ObjectFactory;
import com.tsspringexperience.jaxb.Response;

public class FillData {

	public static ResponseEntity<?> hotelResponse(ObjectFactory factory, Hotel hotel) {
		
		/* Creating hotel response object, in order to change the HotelResponse class you should change in the xsd file under src/main/xsd */
		final HotelResponse hotelResponse = factory.createHotelResponse();
		
		HotelResponse.Rooms hotelRooms = factory.createHotelResponseRooms();
		hotelRooms.setHotelId(hotel.getDedgeHotelId());
		hotelRooms.setHotelName(hotel.getHotelName());
		hotelRooms.setPriceModel(hotel.getDedgePriceModel());
		
		if (hotel.getRooms().size() == 0)
			return new ResponseEntity<Response>(Utils.GetXmlFormat(Constants.NO_ROOMS_14, null, "Hotel (" + hotel.getDedgeHotelId() + ") does not have rooms created or configured", null, null), HttpStatus.OK);

		for (Room room : hotel.getRooms()) {
			HotelResponse.Rooms.Room newRoom = factory.createHotelResponseRoomsRoom();
			newRoom.setCode(room.getCode());
			if (room.getMinOccupancy() > 0) newRoom.setMinOccupancy(room.getMinOccupancy()+"");
			if (room.getMaxOccupancy() > 0) newRoom.setMaxOccupancy(room.getMaxOccupancy()+"");
			newRoom.setName(room.getName());

			/* If no rates for a room the system will not show it */
			if (room.getRoomRate().size() > 0) {
				for (RoomRate roomRateDb : room.getRoomRate()) {

					/* Check if rate is active */
					if (roomRateDb.getRate().getActive() == 0) 
						continue;
					
					HotelResponse.Rooms.Room.Rate roomRate = factory.createHotelResponseRoomsRoomRate();
					roomRate.setCode(roomRateDb.getRate().getCode());
					roomRate.setName(roomRateDb.getRate().getName());
					roomRate.setRegime(roomRateDb.getRate().getRegime());
					
					newRoom.getRate().add(roomRate);
				}
	
				for (Occupancy occupency : room.getOccupancies()) {
					
					HotelResponse.Rooms.Room.Occupancy roomOccupancy = factory.createHotelResponseRoomsRoomOccupancy();
					roomOccupancy.setAdultCount(occupency.getAdultCount());
					roomOccupancy.setChildCount(occupency.getChildCount());
	
					newRoom.getOccupancy().add(roomOccupancy);
				}
	
				hotelRooms.getRoom().add(newRoom);
			}
		}

		if (hotelRooms.getRoom().size() == 0)
			return new ResponseEntity<Response>(Utils.GetXmlFormat(Constants.NO_RATES_15, null, "Hotel (" + hotel.getDedgeHotelId() + ") does not have rates created or configured", null, null), HttpStatus.OK);
		
		hotelResponse.setRooms(hotelRooms);
		return new ResponseEntity<HotelResponse>(hotelResponse, HttpStatus.OK);
	}

	public static ResponseEntity<?> bookingResponse(ObjectFactory factory, List<Booking> allBookings) throws JAXBException {
		
		if (allBookings == null || allBookings.size() == 0) {

			BookingRqRs bookingResponse = new BookingRqRs();
			bookingResponse.setSuccess("");
			return new ResponseEntity<BookingRqRs>(bookingResponse, HttpStatus.OK);
		}
		
		Map<String, List<Booking>> hotelBookingsMap = buildHotelBookingsMap(allBookings);

		BookingRqRs bookingResponse = factory.createBookingResponse();
		
		for (Entry<String, List<Booking>> entry : hotelBookingsMap.entrySet()) {
			String dedgeHotelId = entry.getKey();
			List<Booking> hotelBookings = entry.getValue();

			BookingRqRs.Bookings  bookingResponseBookings = factory.createBookingResponseBookings();
			bookingResponseBookings.setHotelId(dedgeHotelId);
			
			for (Booking booking : hotelBookings) {
				
				BookingRqRs.Bookings.Booking bookingResponseBookingsBooking = factory.createBookingResponseBookingsBooking(); 
				bookingResponseBookingsBooking.setId(booking.getId());
				bookingResponseBookingsBooking.setAction(booking.getAction());
				bookingResponseBookingsBooking.setAdultCount(booking.getAdultCount());
				bookingResponseBookingsBooking.setChildCount(booking.getChildCount());
				bookingResponseBookingsBooking.setCurrency(booking.getCurrency());
				
				BookingRqRs.Bookings.Booking.Customer customer = factory.createBookingResponseBookingsBookingCustomer();
				customer.setComment(booking.getCustomer().getComment());
				BookingRqRs.Bookings.Booking.Customer.Contact customerContact = factory.createBookingResponseBookingsBookingCustomerContact();
				BookingRqRs.Bookings.Booking.Customer.Contact.Address customerAddress = factory.createBookingResponseBookingsBookingCustomerContactAddress();
				customerAddress.setCity(booking.getCustomer().getCity() == null ? null : booking.getCustomer().getCity().getName());
				customerAddress.setCountry(booking.getCustomer().getCountry() == null ? null : booking.getCustomer().getCountry().getCode());
				customerAddress.setPostalCode(booking.getCustomer().getPostalCode());
				customerAddress.setValue(booking.getCustomer().getAddress());
				customerContact.setAddress(customerAddress);
				customerContact.setEmail(booking.getCustomer().getEmail());
				customerContact.setFax(booking.getCustomer().getFax());
				customerContact.setPhone(booking.getCustomer().getPhone());
				customer.setContact(customerContact);
				customer.setFirstName(booking.getCustomer().getFirstName());
				customer.setLastName(booking.getCustomer().getLastName());
				customer.setTitle(booking.getCustomer().getTitle());
				
				bookingResponseBookingsBooking.setCustomer(customer);
				bookingResponseBookingsBooking.setDate(booking.getDate());

				BookingRqRs.Bookings.Booking.Distributor bookingResponseBookingsBookingDistributor = factory.createBookingResponseBookingsBookingDistributor();
				
				for (Distributor distributor : booking.getDistributors()) {
					BookingRqRs.Bookings.Booking.Distributor.Comment distributorComment = factory.createBookingResponseBookingsBookingDistributorComment();
					distributorComment.setName(distributor.getName());
					distributorComment.setValue(distributor.getComment());
					bookingResponseBookingsBookingDistributor.getComment().add(distributorComment);
				}

				if (booking.getDistributors().size() > 0)
					bookingResponseBookingsBooking.setDistributor(bookingResponseBookingsBookingDistributor);
				
				bookingResponseBookingsBooking.setDueAmount(booking.getDueAmount());
				bookingResponseBookingsBooking.setInfantCount(booking.getInfantCount());
				bookingResponseBookingsBooking.setOrigin(booking.getOrigin());
				bookingResponseBookingsBooking.setPaidAmount(booking.getPaidAmount());
				bookingResponseBookingsBooking.setPaxCount(booking.getPaxCount());
				bookingResponseBookingsBooking.setPayableAmount(booking.getPayableAmount());
				
				BookingRqRs.Bookings.Booking.Rooms bookingResponseBookingsBookingRoom = factory.createBookingResponseBookingsBookingRooms();

				/* Fill booking room */
				BookingRqRs.Bookings.Booking.Rooms.Room bookingResponseBookingsBookingRoomsRoom = factory.createBookingResponseBookingsBookingRoomsRoom();
				bookingResponseBookingsBookingRoomsRoom.setId(booking.getRoomRate().getRoom().getCode());

				BookingRqRs.Bookings.Booking.Rooms.Room.Guests bookingResponseBookingsBookingRoomsRoomGuests = factory.createBookingResponseBookingsBookingRoomsRoomGuests();
				
				/* We only have 1 guest details = to customer */
				BookingRqRs.Bookings.Booking.Rooms.Room.Guests.Guest bookingResponseBookingsBookingRoomsRoomGuestsGuest = factory.createBookingResponseBookingsBookingRoomsRoomGuestsGuest();
				bookingResponseBookingsBookingRoomsRoomGuestsGuest.setFirstName(booking.getCustomer().getFirstName());
				bookingResponseBookingsBookingRoomsRoomGuestsGuest.setLastName(booking.getCustomer().getLastName());
				bookingResponseBookingsBookingRoomsRoomGuestsGuest.setTitle(booking.getCustomer().getTitle());
				bookingResponseBookingsBookingRoomsRoomGuests.getGuest().add(bookingResponseBookingsBookingRoomsRoomGuestsGuest);
				
				if (bookingResponseBookingsBookingRoomsRoomGuests.getGuest().size() > 0)
					bookingResponseBookingsBookingRoomsRoom.setGuests(bookingResponseBookingsBookingRoomsRoomGuests);

				BookingRqRs.Bookings.Booking.Rooms.Room.Stays bookingResponseBookingsBookingRoomsRoomStays = factory.createBookingResponseBookingsBookingRoomsRoomStays();
				for (Stay roomStay : booking.getStays()) {
					BookingRqRs.Bookings.Booking.Rooms.Room.Stays.Stay bookingResponseBookingsBookingRoomsRoomStaysStay = factory.createBookingResponseBookingsBookingRoomsRoomStaysStay();
					bookingResponseBookingsBookingRoomsRoomStaysStay.setDate(roomStay.getDate());
					bookingResponseBookingsBookingRoomsRoomStaysStay.setQuantity(roomStay.getQuantity());
					bookingResponseBookingsBookingRoomsRoomStaysStay.setRateCode(roomStay.getRateCode());
					bookingResponseBookingsBookingRoomsRoomStaysStay.setUnitPrice(roomStay.getUnitPrice());
					bookingResponseBookingsBookingRoomsRoomStays.getStay().add(bookingResponseBookingsBookingRoomsRoomStaysStay);
				}
				
				if (bookingResponseBookingsBookingRoomsRoomStays.getStay().size() > 0)
					bookingResponseBookingsBookingRoomsRoom.setStays(bookingResponseBookingsBookingRoomsRoomStays);
				
				bookingResponseBookingsBookingRoom.getRoom().add(bookingResponseBookingsBookingRoomsRoom);

				if (bookingResponseBookingsBookingRoom.getRoom().size() > 0)
					bookingResponseBookingsBooking.setRooms(bookingResponseBookingsBookingRoom);
				
				bookingResponseBookingsBooking.setTotalAmount(booking.getTotalAmount());
				bookingResponseBookings.getBooking().add(bookingResponseBookingsBooking);
			}
			
			bookingResponse.getBookings().add(bookingResponseBookings);
		} 
		
		bookingResponse.setSuccess("");
		
		/* Fix @XmlValue annotation in BookingResponse that creates value element in the body automatically */
		JAXBContext jc = JAXBContext.newInstance(BookingRqRs.class);
		Marshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		StringWriter sw = new StringWriter();	
		marshaller.marshal(bookingResponse, sw);
	
		return new ResponseEntity<String>(sw.toString(), HttpStatus.OK);
	}

	private static Map<String, List<Booking>> buildHotelBookingsMap(List<Booking> allBookings) {
		Map<String, List<Booking>> hotelBookingsMap = new HashMap<>();

		for (Booking booking : allBookings) {
			List<Booking> bookings = hotelBookingsMap.get(booking.getDedgeHotelId());
			if (bookings == null) 
				bookings = new ArrayList<>();
			
			bookings.add(booking);
			/* Override the existing bookings array */
			hotelBookingsMap.put(booking.getDedgeHotelId(), bookings);
		}
		
		return hotelBookingsMap;
	}

}
