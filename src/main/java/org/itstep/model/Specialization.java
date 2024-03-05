package org.itstep.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Specialization{
    @Id
    @Column(name="specialization_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "specialization_name")
    @NotEmpty(message = "Название специальности не доллжно быть пустым")
    @Size(min = 2, max = 6, message = "Название специальности не должно превышать 6 символов")
    private String specializationName;

    @OneToMany(mappedBy = "specialization")
    private List<Group> groups;
}
