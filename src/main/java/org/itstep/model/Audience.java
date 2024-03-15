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
public class Audience {
    @Id
    @Column(name = "audience_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "audience_number")
    private int audienceNumber;

    @OneToMany(mappedBy = "audience")
    private List<Schedule> scheduleList;
}
