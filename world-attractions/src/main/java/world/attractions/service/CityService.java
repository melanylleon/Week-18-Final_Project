package world.attractions.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import world.attractions.controller.model.CityData;
import world.attractions.controller.model.CityData.CityAttraction;
import world.attractions.controller.model.CityData.CityCategory;
import world.attractions.dao.AttractionDao;
import world.attractions.dao.CategoryDao;
import world.attractions.dao.CityDao;
import world.attractions.entity.Attraction;
import world.attractions.entity.Category;
import world.attractions.entity.City;

//This is the service class. It is used to manage the transactions that are performed in the DAO interfaces.

//Tells Spring that this is a service class and creates the CityService bean 
@Service
public class CityService {

	//Injects the CityDao bean into this field
	@Autowired
	private CityDao cityDao;
	
	@Autowired
	private AttractionDao attractionDao;
	
	@Autowired
	private CategoryDao categoryDao;

	//Saves the created or updated city data in the city table by calling the save() method in the CityDao interface
	@Transactional(readOnly = false)
	public CityData saveCity(CityData cityData) {
		Long cityId = cityData.getCityId();
		City city = findOrCreateCity(cityId);

		setFieldsInCity(city, cityData);
		return new CityData(cityDao.save(city));
	}

	//Sets the values of the City fields to the values of the CityData fields
	private void setFieldsInCity(City city, CityData cityData) {
		city.setCityName(cityData.getCityName());
		city.setCountry(cityData.getCountry());
		city.setLanguage(cityData.getLanguage());
		city.setCurrency(cityData.getCurrency());
		city.setSafetyLevel(cityData.getSafetyLevel());
	}

	//If the city ID is null, it creates a city. If the ID is not null, it calls
	//the findCityById() method to retrieve the city data from the database. 
	private City findOrCreateCity(Long cityId) {
		City city;

		if (Objects.isNull(cityId)) {
			city = new City();
		} else {
			city = findCityById(cityId);
		}

		return city;
	}

	//Retrieves a city's data from the database. It calls the findById() method that is provided by the JpaRepository interface  
	//used in the CityDao interface. If the city ID that is passed to the method does not exist in the city table, it throws an exception.
	private City findCityById(Long cityId) {
		return cityDao.findById(cityId)
				.orElseThrow(() -> new NoSuchElementException("City with ID=" + cityId + " was not found."));
	}

	//Retrieves all of the city data and converts each into CityData objects 
	@Transactional(readOnly = true)
	public List<CityData> retrieveAllCities() {
		
		// @formatter:off
		return cityDao.findAll()
		.stream()
		.map(CityData::new)
		.toList();
		// @formatter:on
	}

	//Retrieves a city's data by calling the findCityById() method in the CityDao interface
	@Transactional(readOnly = true)
	public CityData retrieveCityById(Long cityId) {
		City city = findCityById(cityId);
		return new CityData(city);
	}

	//Deletes a city by calling the delete() method in the CityDao interface
	@Transactional(readOnly = false)
	public void deleteCityById(Long cityId) {
		City city = findCityById(cityId);
		cityDao.delete(city);
	}

	//Saves the attraction data in the attraction table by calling the save() method in the AttractionDao interface. 
	@Transactional(readOnly = false)
	public CityAttraction saveAttraction(Long cityId, CityAttraction cityAttraction) {
		City city = findCityById(cityId);
		
		Attraction attraction = findOrCreateAttraction(cityAttraction.getAttractionId());
		setAttractionFields(attraction, cityAttraction);
		
		attraction.setCity(city);
		city.getAttractions().add(attraction);
	
		Attraction dbAttraction = attractionDao.save(attraction);
		return new CityAttraction(dbAttraction);
	}

	//Sets the values of the Attraction fields to the values of the CityAttraction fields
	private void setAttractionFields(Attraction attraction, CityAttraction cityAttraction) {
		attraction.setAttractionId(cityAttraction.getAttractionId());
		attraction.setAttractionName(cityAttraction.getAttractionName());
		attraction.setDescription(cityAttraction.getDescription());
		attraction.setTicketPrice(cityAttraction.getTicketPrice());
		attraction.setVisitorsYearly(cityAttraction.getVisitorsYearly());
	}

	//If the attraction ID is null, it creates an attraction. If the ID is not null, it calls
	//the findAttractionById() method to retrieve the attraction data from the attraction table. 
	private Attraction findOrCreateAttraction(Long attractionId) {
		Attraction attraction;
		
		if (Objects.isNull(attractionId)) {
			attraction = new Attraction();
		} 
		else {
			attraction = findAttractionById(attractionId);	
		}
		
		return attraction;
	}

	//Retrieves attraction data from the database. It calls the findById() method that is provided by the JpaRepository interface  
	//used in the AttractionDao interface. If the attraction ID that is passed to the method does not exist in the attraction table, it throws an exception.
	private Attraction findAttractionById(Long attractionId) {
		return attractionDao.findById(attractionId)
				.orElseThrow(() -> new NoSuchElementException(
						"Attraction with ID=" + attractionId + " does not exist."));
	}

	//Retrieves an attraction's data from the attraction table. If the attraction is not in the city with the ID that was 
	//passed to the method, it throws an exception.
	@Transactional(readOnly = true)
	public CityAttraction retrieveAttractionById(Long cityId, Long attractionId) {
		Attraction attraction = findAttractionById(attractionId);
		
		if (attraction.getCity().getCityId() != cityId) {
			throw new IllegalStateException("Attraction with ID=" + attractionId + " is not in the city with ID=" + cityId);
		}
		
		return new CityAttraction(attraction);
	}

	//Deletes an attraction from the database by calling the delete() method in the AttractionDao class.
	@Transactional(readOnly = false)
	public void deleteAttractionById(Long attractionId) {
		Attraction attraction = findAttractionById(attractionId);
		attractionDao.delete(attraction);
	}

	//Saves the category data in the database by calling the save() method in the CategoryDao interface. 
	public CityCategory saveCategory(Long cityId, CityCategory cityCategory) {
		City city = findCityById(cityId);
		Category category = findOrCreateCategory(cityId, cityCategory.getCategoryId());
		copyCategoryFields(category, cityCategory);
		category.getCities().add(city);
		city.getCategories().add(category);
		
		Category dbCategory = categoryDao.save(category);
		
		return new CityCategory(dbCategory);
	}

	//Sets the values of the Category fields to the values of the CityCategory fields.
	private void copyCategoryFields(Category category, CityCategory cityCategory) {
		category.setCategoryId(cityCategory.getCategoryId());
		category.setCategoryName(cityCategory.getCategoryName()); 
	}

	private Category findOrCreateCategory(Long cityId, Long categoryId) {
		Category category;
		
		if (Objects.isNull(categoryId)) {
			category = new Category();
		} 
		else {
			category = findCategoryById(categoryId);
		}
		
		return category;
	}

	//Retrieves category data from the database. It calls the findById() method that is provided by the JpaRepository interface  
	//used in the CategoryDao interface. If the category ID that is passed to the method does not exist in the category table, it throws an exception.
	private Category findCategoryById(Long categoryId) {
		return categoryDao.findById(categoryId)
				.orElseThrow(() -> new NoSuchElementException(
						"Category with ID=" + categoryId + " does not exist."));
	}
	
	//Retrieves all of the cities in a category. It calls the findCategoryById() method to retrieve the category data.
	//It gets all of the city objects from the category object, converts them into CityData objects, and removes the 
	//attractions and categories to only return the List with the cities' data.
	public List<CityData> retrieveAllCitiesInCategory(Long categoryId) {
		Category category = findCategoryById(categoryId);
		List<CityData> categoryCities = new LinkedList<>(); 
		
		for (City city : category.getCities()) {
			CityData cityData = new CityData(city);
			
			cityData.getAttractions().clear();
			cityData.getCategories().clear();
			
			categoryCities.add(cityData);
		}
		
		return categoryCities;
	}
}
