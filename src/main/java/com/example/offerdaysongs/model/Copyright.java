package com.example.offerdaysongs.model;

import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@Entity
public class Copyright {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;
        ZonedDateTime startDate;
        ZonedDateTime endDate;
        Long royalty;
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "company_id")
        Company company;
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "recording_id")
        Recording recording;


}
