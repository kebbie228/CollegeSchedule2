package org.itstep.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Para {
    @Id
    @Column(name = "para_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "para_number")
    private int audienceNumber;

    @OneToMany(mappedBy = "para")
    private List<Schedule> scheduleList;
}
