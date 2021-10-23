package com.example.offerdaysongs.model;

import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@Entity
public class Recording {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String title;
    String version;
    ZonedDateTime releaseTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "singer_id")
    Singer singer;
}
