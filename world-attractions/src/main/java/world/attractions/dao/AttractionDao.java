package world.attractions.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import world.attractions.entity.Attraction;

//This is the DAO interface for the attraction table. The interface extends the JpaRepository interface that is
//used to perform CRUD operations on the attraction table. It generates the SQL statements using its interfaces. 

public interface AttractionDao extends JpaRepository<Attraction, Long> {

}
