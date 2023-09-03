package world.attractions.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import world.attractions.controller.model.CityData;
import world.attractions.controller.model.CityData.CityAttraction;
import world.attractions.controller.model.CityData.CityCategory;
import world.attractions.service.CityService;

//This is the controller class. The HTTP requests are mapped to this class and responses 
//are sent back to the client.

//Tells Spring that this a controller and that it takes and returns JSON
@RestController
@Slf4j
public class CityController {

	//Injects the CityService bean into this field
	@Autowired
	private CityService cityService;

	//Maps POST requests to the method. The requests are sent to /city. 
	//The method creates/inserts a city's data into the database by calling the saveCity() method in the CityService class.
	@PostMapping("/city")
	public CityData insertCity(@RequestBody CityData cityData) {
		log.info("Creating city {}", cityData);
		return cityService.saveCity(cityData);
	}

	//Maps PUT requests to the method. The requests are sent to /city/{cityId}.
	//The method updates a city's data in the database by calling the saveCity() method in the CityService class.
	@PutMapping("/city/{cityId}")
	public CityData updateCity(@PathVariable Long cityId, @RequestBody CityData cityData) {
		cityData.setCityId(cityId);
		log.info("Updating city {}", cityData);
		return cityService.saveCity(cityData);
	}

	//Maps a GET request to the method. The request is sent to /city.
	//The method retrieves all city data from the database by calling the retrieveAllCities() method in the CityService class.
	@GetMapping("/city")
	public List<CityData> retrieveAllCities() {
		log.info("Retrieving all cities");
		return cityService.retrieveAllCities();
	}

	//Maps GET requests to the method. The requests are sent to /city/{cityId}. 
	//The method retrieves a city's data from the database by calling the retrieveCityById() method in the CityService class.
	@GetMapping("/city/{cityId}")
	public CityData retrieveCityById(@PathVariable Long cityId) {
		log.info("Retrieving city with ID={}", cityId);
		return cityService.retrieveCityById(cityId);
	}

	//Maps a DELETE request to the method. The request is sent to /city.
	//Throws an UnsupportedOperationException() to prevent the deletion of all of the cities in the database.
	@DeleteMapping("/city")
	public void deleteAllCities() {
		log.info("Attempting to delete all cities.");
		throw new UnsupportedOperationException("All cities cannot be deleted.");
	}

	//Maps DELETE requests to the method. The requests are sent to /city/{cityId}.
	//The method deletes a city's data from the database by calling the deleteCityById() method in the CityService class.
	@DeleteMapping("/city/{cityId}")
	public Map<String, String> deleteCityById(@PathVariable Long cityId) {
		log.info("Deleting city with ID={}", cityId);
		cityService.deleteCityById(cityId);

		return Map.of("message", "Deletion of city with ID=" + cityId + " was successful.");
	}
 
	//Maps POST requests to the method. The requests are sent to /city/{cityId}/attraction.
	//The method inserts an attraction's data for a particular city into the database. This is done by calling the saveAttraction() method in the CityService class.
	@PostMapping("/city/{cityId}/attraction")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CityAttraction insertAttraction(@PathVariable Long cityId, @RequestBody CityAttraction cityAttraction) {
		log.info("Creating attraction {} for city with ID=", cityAttraction, cityId);
	
		return cityService.saveAttraction(cityId, cityAttraction);
	}
	
	//Maps PUT requests to the method. The requests are sent to /city/{cityId}/attraction/{attractionId}.
	//The method updates an attraction's data in the database by calling the saveAttraction() method in the CityService class.
	@PutMapping("/city/{cityId}/attraction/{attractionId}")
	public CityAttraction updateAttraction(@PathVariable Long cityId, 
			@PathVariable Long attractionId, 
			@RequestBody CityAttraction cityAttraction) {
		
		cityAttraction.setAttractionId(attractionId);
		log.info("Updating attraction {} of city with ID=", cityAttraction, cityId);
	
		return cityService.saveAttraction(cityId, cityAttraction);
	}
	
	//Maps GET requests to the method. The requests are sent to /city/{cityId}/attraction/{attractionId}. 
	//The method retrieves an attraction's data for a particular city from the database. This is done by calling the retrieveAttractionById() method in the CityService class.
	@GetMapping("/city/{cityId}/attraction/{attractionId}")
	public CityAttraction retrieveAttractionById(@PathVariable Long cityId, 
			@PathVariable Long attractionId) {
		log.info("Retrieving attraction with ID={} of city with ID={}", attractionId, cityId);
	
		return cityService.retrieveAttractionById(cityId, attractionId);
	}
	
	//Maps DELETE requests to the method. The requests are sent to /attraction/{attractionId}.
	//The method deletes an attraction's data from the database by calling the deleteAttractionById() method in the CityService class.
	@DeleteMapping("/attraction/{attractionId}")
	public Map<String, String> deleteAttractionById(@PathVariable Long attractionId) {
		log.info("Deleting attraction with ID={}", attractionId);
		cityService.deleteAttractionById(attractionId);

		return Map.of("message", "Deletion of attraction with ID=" + attractionId + " was successful.");
	}
	
	//Maps POST requests to the method. The requests are sent to /city/{cityId}/category.
	//The method inserts a category's data for a particular city into the database. This is done by calling the saveCategory() method in the CityService class.
	@PostMapping("/city/{cityId}/category") 
	public CityCategory insertCategory(@PathVariable Long cityId, @RequestBody CityCategory cityCategory) {
		log.info("Creating category {} for city with ID={}", cityCategory, cityId);
		return cityService.saveCategory(cityId, cityCategory);
	}
	
	//Maps POST requests to the method. The requests are sent to /city/{cityId}/category/{categoryId}.
	//The method adds an existing category to a city in the database. This is done by calling the saveCategory() method in the CityService class.
	@PostMapping("/city/{cityId}/category/{categoryId}") 
	public CityCategory addExistingCategoryToCity(@PathVariable Long cityId, @PathVariable Long categoryId, @RequestBody CityCategory cityCategory) {
		cityCategory.setCategoryId(categoryId);
		log.info("Adding category with ID={} to city with ID={}", categoryId, cityId);
		return cityService.saveCategory(cityId, cityCategory);
	}
	
	//Maps GET requests to the method. The requests are sent to /category/{categoryId}.
	//The method retrieves all of the cities in a particular category from the database. This is done by calling the retrieveAllCitiesInCategory() method in the CityService class. 
	@GetMapping("/category/{categoryId}")
	public List<CityData> retrieveAllCitiesInCategory(@PathVariable Long categoryId) {
		log.info("Retrieving all cities in category with ID={}", categoryId);
		return cityService.retrieveAllCitiesInCategory(categoryId);
	}
}
