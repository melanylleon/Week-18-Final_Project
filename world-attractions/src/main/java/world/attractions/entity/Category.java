package world.attractions.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

//JPA and Hibernate create the category table and this entity's relationship with the city entity.

//Tells JPA that this class is an entity that maps to a table.
@Entity
@Data
public class Category {
	@Id //Tells JPA that this is the primary key.
	//Tells JPA that MySQL will automatically generate the primary key values using the database's primary key column.
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long categoryId;
	
	private String categoryName;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	//Creates the many-to-many relationship between categories and cities. It is mapped using the categories field in the City class. 
	@ManyToMany(mappedBy = "categories")
	private Set<City> cities = new HashSet<>();
}
