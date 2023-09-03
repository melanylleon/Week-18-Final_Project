package world.attractions.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

//JPA and Hibernate create the city table and this entity's relationships with the other entities.

//Tells JPA that this class is an entity that maps to a table
@Entity 
@Data
public class City {
	@Id //Tells JPA that this is the primary key.
	//Tells JPA that MySQL will automatically generate the primary key values using the database's primary key column.
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cityId;
	private String cityName;
	private String country;
	private String language;
	private String currency;
	private String safetyLevel;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	//Creates the one-to-many relationship between a city and attractions. It is mapped using the city field in the Attraction class.
	@OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
	private Set<Attraction> attractions = new HashSet<>();

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	//Creates the many-to-many relationship between cities and categories.
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "city_category", 
			joinColumns = @JoinColumn(name = "city_id"),
			inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> categories = new HashSet<>();
}
