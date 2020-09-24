package custq;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * JPA entity representing the company details
 * @author regen
 *
 */
@Entity
public class Company {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@NotNull
	@Size(max=10, message="Company name can be no more that 10 characters")
    private String name;
	
	@NotNull
	private String description;
	
	private LocalDate createdDate;
	
	public Company() {
		this.createdDate = LocalDate.now();
	}

    public Company(final long id, final String name, final String description) {
        this.id = id;
        this.name = name;
        this.description = description;
     }

    public Long getId() {
        return id;
    }

	public String getName() {
		return name;
	}

	public String getDescription() {
		return this.description;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}
}
