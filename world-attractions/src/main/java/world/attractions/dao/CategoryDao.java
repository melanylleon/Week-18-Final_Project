package world.attractions.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import world.attractions.entity.Category;

//This is the DAO interface for the category table. The interface extends the JpaRepository interface that is
//used to perform CRUD operations on the category table. It generates the SQL statements using its interfaces. 

public interface CategoryDao extends JpaRepository<Category, Long> {

}
