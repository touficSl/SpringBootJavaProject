package com.tsspringexperience.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsspringexperience.classes.AvailRequest;
import com.tsspringexperience.classes.AvailResponse;
import com.tsspringexperience.classes.AvailRequest.People;
import com.tsspringexperience.classes.AvailResponse.RoomStay;
import com.tsspringexperience.classes.AvailResponse.RoomStay.RoomType;
import com.tsspringexperience.classes.AvailResponse.RoomStay.RoomType.Image;
import com.tsspringexperience.classes.AvailResponse.RoomStay.RoomType.Rate;
import com.tsspringexperience.entities.Ammenities;
import com.tsspringexperience.entities.Hotel;
import com.tsspringexperience.entities.HotelServices;
import com.tsspringexperience.entities.ImageEntity;
import com.tsspringexperience.entities.PlanningOccupancy;
import com.tsspringexperience.entities.RatePlanning;
import com.tsspringexperience.entities.RatePolicy;
import com.tsspringexperience.entities.RoomAvailability;
import com.tsspringexperience.entities.RoomRate;
import com.tsspringexperience.entities.ServiceAction;
import com.tsspringexperience.services.AmmenitiesService;
import com.tsspringexperience.services.HotelService;
import com.tsspringexperience.services.ImageService;
import com.tsspringexperience.services.PlanningOccupancyService;
import com.tsspringexperience.services.RatePlanningService;
import com.tsspringexperience.services.RatePolicyService;
import com.tsspringexperience.services.RoomAvailabilityService;
import com.tsspringexperience.services.RoomRateService;
import com.tsspringexperience.utils.Constants;
import com.tsspringexperience.utils.Credentials;
import com.tsspringexperience.utils.SystemResponse;
import com.tsspringexperience.utils.Utils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Availability", description = "Everything about availabilities")
@RequestMapping(path = "/api/vfront1")
public class AvailabilityApi {

	@Autowired
	HotelService hotelService;

	@Autowired
	RoomAvailabilityService roomAvailabilityService;

	@Autowired
	RatePlanningService ratePlanningService;

	@Autowired
	RoomRateService roomRateService;

	@Autowired
	ImageService imageService;

	@Autowired
	AmmenitiesService ammenitiesService;
	
	@Autowired
	RatePolicyService ratePolicyService;

	@Autowired
	PlanningOccupancyService planningOccupancyService;
	
	@Operation(summary = "Retrieve Availability", description = "Retrieve availability.", tags = { "Availability" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "hotel availability.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SystemResponse.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(name = Constants.INTERNAL_ERROR, implementation = SystemResponse.class))) })
	
	@RequestMapping(value = "/avail", method = RequestMethod.POST)
	public ResponseEntity<?> hotelCityAvail(
			@RequestBody(required=true) @Valid AvailRequest availRequest) throws JSONException, JsonProcessingException {

		/* Required in order to return APPLICATION_JSON response */
	    HttpHeaders httpHeaders = new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	    
	    /* Response array */
		List<SystemResponse> peopleResponseList = new ArrayList<SystemResponse>();
		
		try {
			
			if (availRequest.getHotelIdList() == null || availRequest.getHotelIdList().size() == 0)
				return new ResponseEntity<String>(new ObjectMapper().writeValueAsString(Utils.GetJsonFormat(Constants.INTERNAL_ERROR_203, "HotelIdList is required.", null, null)), new HttpHeaders(), HttpStatus.OK);

			if (availRequest.getPeople() == null || availRequest.getPeople().size() == 0)
				return new ResponseEntity<String>(new ObjectMapper().writeValueAsString(Utils.GetJsonFormat(Constants.INTERNAL_ERROR_203, "People list is required.", null, null)), new HttpHeaders(), HttpStatus.OK);

			/* Loop requested people list */
			for (People people : availRequest.getPeople()) {

				List<SystemResponse> hotelResponseList = new ArrayList<SystemResponse>();
				
				/* Loop requested hotel id list */
				for (String HotelId : availRequest.getHotelIdList()) {
	
					/* HotelId validation check */
					Hotel hotel = hotelService.findByHotelId(HotelId);
					if (hotel == null) {
						hotelResponseList.add(Utils.GetJsonFormat(Constants.INTERNAL_ERROR_203, "Hotel(" + HotelId + ") is not configured or unknown.", null, null));
						continue;
					}
					
					/* Check if HotelId exists in hotel_services table and if authorized */
					HotelServices hotelServices = hotelService.findHotelServiceByHotelId(HotelId);
					if(hotelServices == null || !hotelServices.getService().getName().equals(Constants.SERVICE_NAME) || !hotelServices.isEnabled()) {
						hotelResponseList.add(Utils.GetJsonFormat(Constants.INTERNAL_ERROR_203, "Hotel(" + HotelId + ") is not authorized to use " + Constants.SERVICE_NAME + " service.", null, null));
						continue;
					}
						
					String actionPath = "/api/vfront1/avail";
					/* Check if HotelId exists in service_action table and if authorized to use this request */
					ServiceAction serviceAction = hotelService.findServiceActionByHotelId(HotelId, actionPath);
					if(serviceAction == null) {
						hotelResponseList.add(Utils.GetJsonFormat(Constants.INTERNAL_ERROR_203, "Hotel(" + HotelId + ") is not authorized to use " + actionPath + " request.", null, null));
						continue;
					}
				
					/* From to check */
					Date from = Utils.returnDateByFormat(availRequest.getDateIn(), Constants.DateFormat);
					Date to = Utils.returnDateByFormat(availRequest.getDateOut(), Constants.DateFormat);
					if (from == null || to == null) { 
						hotelResponseList.add(Utils.GetJsonFormat(Constants.INTERNAL_ERROR_203, "Invalid dateIn or dateOut format.", null, null));
						continue;
					}
					
					Date today = new Date();
					if (from.equals(to) || to.before(from) || from.before(today)) { 
						hotelResponseList.add(Utils.GetJsonFormat(Constants.INTERNAL_ERROR_203, "Invalid dateIn and dateOut.", null, null));
						continue;
					}
	
					/* Fetch roomAvailabilityList by hotelId, from/to dates, requested room number */
					List<RoomAvailability> roomAvailabilityList = new ArrayList<RoomAvailability>();
					int requestedRoomNbre = availRequest.getPeople().size();
					roomAvailabilityList = roomAvailabilityService.findAvailHotelRoomsFromToDatesRoomNbre(HotelId, from, to, requestedRoomNbre);
					
					if (roomAvailabilityList == null || roomAvailabilityList.size() == 0) {
						hotelResponseList.add(Utils.GetJsonFormat(Constants.INTERNAL_ERROR_203, "No availability found for Hotel(" + hotel.getHotelId() + ").", null, null, "No availability found."));
						continue;
					}

					/* room people check */
					AvailResponse availResponse = fillAvailData(availRequest, hotel, roomAvailabilityList, people, from, to);
				
					if (!availResponse.isSuccess()) {
						hotelResponseList.add(Utils.GetJsonFormat(Constants.INTERNAL_ERROR_204, availResponse.getErrorMessage(), null, null, availResponse.getUserMessage()));
						continue;
					} else {
						if (availResponse.getRoomstay() == null){
							hotelResponseList.add(Utils.GetJsonFormat(Constants.INTERNAL_ERROR_205, "No availability found for Hotel(" + hotel.getHotelId() + ").", null, null, "No availability found."));
							continue;
						}
					}
					
					SystemResponse sr = Utils.GetJsonFormat(Constants.SUCCESS, null, null, null);
					sr.setData(availResponse);
					hotelResponseList.add(sr);
				}
				
				SystemResponse hotelResponseArray = Utils.GetJsonFormat(Constants.SUCCESS, null, null, null);
				hotelResponseArray.setData(hotelResponseList);
				peopleResponseList.add(hotelResponseArray);
			}

		} catch (Exception e) {
			if (Credentials.isDebugMode())
				e.printStackTrace();

			return new ResponseEntity<String>(new ObjectMapper().writeValueAsString(Utils.GetJsonFormat(Constants.INTERNAL_ERROR_203, "availability request error message, " + e.getMessage(), null, null)), httpHeaders, HttpStatus.OK);
		}

		SystemResponse systemResponse = Utils.GetJsonFormat(Constants.SUCCESS, null, null, null);
		systemResponse.setData(peopleResponseList);
		return new ResponseEntity<String>(new ObjectMapper().writeValueAsString(systemResponse), httpHeaders, HttpStatus.OK);
	}
	
	
	public AvailResponse fillAvailData(@Valid AvailRequest availRequest, Hotel hotel, List<RoomAvailability> roomAvailabilityList, People people, Date from, Date to) {
		
		/* Init avail response */
		AvailResponse availResponse = new AvailResponse();
		availResponse.setSuccess(true);
		
		RoomStay roomStay = new RoomStay();
		
		List<Ammenities> amenityList = ammenitiesService.fetchAmenities(hotel.getHotelId());
		roomStay.fillData(hotel, amenityList);

		/* Fill Start/End dates */
		roomStay.getTimeSpan().setStart(availRequest.getDateIn());
		roomStay.getTimeSpan().setEnd(availRequest.getDateOut()); 

		/* Calculate difference between from/to dates */
	    long stay = Utils.calculateTwoDateDifference(from, to);
	    roomStay.getTimeSpan().setDuration(stay);

		/* Fill current requested people */
	    roomStay.setPeople(people);
		
		/* Fill Rooms list */
		for (RoomAvailability roomAvailability : roomAvailabilityList) {
			
			RoomType roomType = new RoomType();
			roomType.fillData(roomAvailability);
			
			if (roomStay.findIfRoomTypeExist(roomType.getRoomTypeCode()))
				continue;
			
			/* Fill Media and Main Image */
			List<ImageEntity> roomImageList = imageService.findImagesByCodeByEnum(roomAvailability.getRoom().getId()+"", ImageService.ROOM);
			int order = 0;
			for (ImageEntity imageDb : roomImageList) {
				order++;
				Image img = new Image(order, imageDb.getTitle(), imageDb.getName(), imageDb.getUrl().replace("/tsl/midd_data", ""), imageDb.getMimetype());
				if (imageDb != null && imageDb.getPrimary() != null && imageDb.getPrimary() == 1) roomType.setMainImage(img);
				else roomType.getMedia().add(img);
			}
			
			List<RoomRate> roomRateList = roomRateService.findByRoomId(roomAvailability.getRoom().getId());
			
			/* In case of no room rate list available means "the hotel has planning update without price nor restrictions" OR No availability */
			for (RoomRate roomRate : roomRateList) {
				
				List<RatePlanning> ratePlanningList = ratePlanningService.findByRoomRateIdBetweenFromToDatesStay(roomRate.getId(), from, to, stay);
				
				for (RatePlanning ratePlanning : ratePlanningList) {

					/* Fill AverageRate list */
					Rate rate = new Rate();
					rate.setRoomTypeCode(roomAvailability.getRoom().getCode());
					
					List<RatePolicy> ratePolicies = ratePolicyService.fetchRatePolicy(ratePlanning.getRoomRate().getRate().getId());
					rate.fillData(ratePlanning, ratePolicies);
				
					/* hotel.priceModel child / adult / room number check */
					if (hotel.getDedgePriceModel().equals(Constants.PRICEMODEL_PER_DAY)) 
						roomType.fillRatesData(rate, ratePlanning, null, ratePlanning.getDate());
						
					else { /* hotel.getDedgePriceModel() = Constants.PRICEMODEL_PER_PERSON OR Constants.PRICEMODEL_PER_OCCUPANCY */
						
						Integer personCount = Utils.parseStringToInt(people.getPerson());
						Integer adultsCount = Utils.parseStringToInt(people.getAdults());
						Integer childrenCount = Utils.parseStringToInt(people.getChildren());
						
						AvailResponse validatePeopleRequest = validatePeopleRequest(availResponse, hotel, people, personCount, adultsCount, childrenCount);
						if (validatePeopleRequest != null)
							return validatePeopleRequest;
						
						PlanningOccupancy planOcupancy = planningOccupancyService.findByPlanIdPersAdultChildCounts(ratePlanning.getId(), adultsCount, childrenCount, personCount);
						if (planOcupancy == null)  
							continue;
						
						roomType.fillRatesData(rate, ratePlanning, planOcupancy, ratePlanning.getDate());
					}
				}
			}
			
			roomType.setRates(checkNoAvailableRoomThatContainPeople(roomType, stay));
					 
			if (roomType.getRates().size() > 0)
				roomStay.getRoomTypes().add(roomType);
		}

		if (roomStay.getRoomTypes().size() > 0)
			availResponse.setRoomstays(roomStay);
		else 
			availResponse.fillErrorData("No availability found for Hotel(" + hotel.getHotelId() + ").", "No availability found.");
		
		return availResponse;
	}

	/* Check the case where there is no available room that can contain the mentioned number of people */
	private List<Rate> checkNoAvailableRoomThatContainPeople(RoomType roomType, long stay) {
		
		List<Rate> nightlyRates = new ArrayList<Rate>();
		
		for (Rate rate : roomType.getRates()) {
			if (rate.getNightlyRates().size() == stay)
				nightlyRates.add(rate);
		}
		
		return nightlyRates;
	}


	private AvailResponse validatePeopleRequest(AvailResponse availResponse, Hotel hotel, People people, Integer personCount, Integer adultsCount,
			Integer childrenCount) {
		/* Request body validation */
		if (hotel.getDedgePriceModel().equals(Constants.PRICEMODEL_PER_PERSON)) 
			if (people.getPerson() == null || personCount == null)
				return availResponse.fillErrorData("Required or Invalid people.person value.", "Required or Invalid people.person value.");
		else if (hotel.getDedgePriceModel().equals(Constants.PRICEMODEL_PER_OCCUPANCY)) 
			if (people.getAdults() == null || adultsCount == null ||
					people.getChildren() == null || childrenCount == null)
				return availResponse.fillErrorData("Required or Invalid people.adults and people.children values.", "Required or Invalid people.adults and people.children values.");
		else
			return availResponse.fillErrorData("No price model found for Hotel(" + hotel.getHotelId() + ").", "No price model found.");
		
		return null;
	}
}
