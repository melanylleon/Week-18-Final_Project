package world.attractions.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import world.attractions.entity.City;

//This is the DAO interface for the city table. The interface extends the JpaRepository interface that is
//used to perform CRUD operations on the city table. It generates the SQL statements using its interfaces. 

public interface CityDao extends JpaRepository<City, Long> {

}
