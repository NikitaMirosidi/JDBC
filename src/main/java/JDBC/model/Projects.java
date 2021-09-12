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
@Entity(name = "projects")
public class Projects implements BaseModel {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "cost")
    private int cost;

    @Column(name = "creation_date")
    private String creationDate;

    @Column(name = "customer_id")
    private int customerId;

    @Column(name = "company_id")
    private int companyId;
}