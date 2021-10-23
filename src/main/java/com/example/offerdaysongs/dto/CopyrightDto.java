package com.example.offerdaysongs.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class CopyrightDto {
    long id;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private Long royalty;
    private CompanyDto company;
    private RecordingDto recording;
}
