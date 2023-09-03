package world.attractions.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

//JPA and Hibernate create the attraction table and this entity's relationship with the city entity.

//Tells JPA that this class is an entity that maps to a table
@Entity
@Data
public class Attraction {
	@Id//Tells JPA that this is the primary key.
	//Tells JPA that MySQL will automatically generate the primary key values using the database's primary key column.
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long attractionId;
	
	private String attractionName;
	private String description;
	private String ticketPrice;
	private String visitorsYearly;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	//Creates the many-to-one relationship between attractions and a city. This is the same one-to-many relationship in the City class.
	@ManyToOne(cascade = CascadeType.ALL)
	//Specifies the foreign key column in the attraction table.
	@JoinColumn(name = "city_id", nullable = false)
	private City city;
}
