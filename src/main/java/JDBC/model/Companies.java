package JDBC.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "companies")
public class Companies implements BaseModel {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "registration_country")
    private String registrationCountry;
}