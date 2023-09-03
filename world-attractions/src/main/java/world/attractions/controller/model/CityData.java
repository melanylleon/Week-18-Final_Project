package world.attractions.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import world.attractions.entity.Attraction;
import world.attractions.entity.Category;
import world.attractions.entity.City;

//A class where JSON will be converted to a data transfer object and vice versa.

@Data
@NoArgsConstructor
public class CityData {
	private Long cityId;
	private String cityName;
	private String country;
	private String language;
	private String currency;
	private String safetyLevel;
	private Set<CityAttraction> attractions = new HashSet<>();
	private Set<CityCategory> categories = new HashSet<>();

	//Assigns the values of the City class fields to the CityData class fields.
	public CityData(City city) {
		cityId = city.getCityId();
		cityName = city.getCityName();
		country = city.getCountry();
		language = city.getLanguage();
		currency = city.getCurrency();
		safetyLevel = city.getSafetyLevel();
		
		for (Attraction attraction : city.getAttractions()) {
			attractions.add(new CityAttraction(attraction));
		}
		
		for (Category category : city.getCategories()) {
			categories.add(new CityCategory(category));
		}
	}

	@Data
	@NoArgsConstructor
	public static class CityAttraction {
		private Long attractionId;
		private String attractionName;
		private String description;
		private String ticketPrice;
		private String visitorsYearly;
		
		//Assigns the values of the Attraction class fields to the CityAttraction class fields.
		public CityAttraction(Attraction attraction) {
			attractionId = attraction.getAttractionId(); 
			attractionName = attraction.getAttractionName();
			description = attraction.getDescription();
			ticketPrice = attraction.getTicketPrice();
			visitorsYearly = attraction.getVisitorsYearly();
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class CityCategory {
		private Long categoryId;
		private String categoryName;
		
		//Assigns the values of the Category class fields to the CityCategory class fields.
		public CityCategory(Category category) {
			categoryId = category.getCategoryId();
			categoryName = category.getCategoryName();
		}
	}
}
