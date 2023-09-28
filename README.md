# Famous World Attractions
![GitHub contributors](https://img.shields.io/github/contributors/melanylleon/World-Attractions)
![GitHub commit activity (branch)](https://img.shields.io/github/commit-activity/t/melanylleon/World-Attractions)
![GitHub issues](https://img.shields.io/github/issues/melanylleon/World-Attractions)
![GitHub](https://img.shields.io/github/license/melanylleon/World-Attractions)
[![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-2.1-4baaaa.svg)](CODE_OF_CONDUCT.md)
##  :star2: About the Project
This is a program that lists some of the most famous attractions in the world. It lists the cities along with the attractions and categories for each city.

### :space_invader: Tech Stack
- Java
- MySQL
- Spring Boot

### :dart: Features

- Lists all of the cities along with the attractions and categories
- Lists all of the cities that belong to a particular category
- Prevents the user from deleting all of the cities on the list

## :toolbox: Getting Started

### :gear: Installation
- Lombok is a Java library that needs to be installed to run the project. There are different installation instructions depending on the tool. 
that you are using to run the project. Here is a [link](https://projectlombok.org/setup/) to the installation instructions.

- Any database management tool, such as [DBeaver](https://github.com/advanced-rest-client/arc-electron/releases), can be used along with this project. 

- Any API testing tool, such as the Advanced REST Client (ARC), can be used along with this project.
Click on this [link](https://github.com/advanced-rest-client/arc-electron/releases) to install an ARC package for your specific operating system.
For MacOS, install the arc-macos.dmg package. For Windows, install the arc-setup.exe package. 

### :running: Run Locally
Clone the project

``` bash
git clone https://github.com/melanylleon/World-Attractions.git
```
Run on the IDE

## :eyes: Usage
This project lists some of the most famous attractions in the world. It lists the most famous attractions for each city. It also lists categories of the types of attractions in a city. For example, the categories for Orlando, Florida can include amusement parks and outdoor activities.  

In this project, you can create, read, update, and delete a city and an attraction's information. A list of all the cities can be retrieved and the program also prevents the user from deleting all of the cities.


**Creates or updates a city in the database**
```java
public CityData saveCity(CityData cityData) {
		Long cityId = cityData.getCityId();
		City city = findOrCreateCity(cityId);

		setFieldsInCity(city, cityData);
		return new CityData(cityDao.save(city));
	}
```

</br>

The user can also create a category, add an existing category to another city, and retrieve all of the cities that belong to a particular category.

**Retrieves all cities that belong to a particular category**
```java
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
```
## :compass: Roadmap

* [ ] I would like to add other activities and places to the list. For example, I would like to add a list of events, tours, beaches, and more. 
* [ ] Create suggestions about places a user can visit based on the interests of the user. For instance, the user can pick a city and a specific category and the program would create a list of the attractions that match their interests. 


## :wave: Contributing
Please feel free to contribute to the project!  

Please see the `CONTRIBUTING.md` file for more information.

## :warning: License
Please see the `LICENSE.txt` file for more information.

# :handshake: Contact

Melany Landaverde Leon - melany.leon0199@gmail.com

Project Link: [https://github.com/melanylleon/World-Attractions.git]( https://github.com/melanylleon/World-Attractions.git)

