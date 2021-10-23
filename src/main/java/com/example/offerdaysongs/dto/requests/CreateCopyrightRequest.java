package com.example.offerdaysongs.dto.requests;

import com.example.offerdaysongs.model.Company;
import com.example.offerdaysongs.model.Recording;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class CreateCopyrightRequest {
    private Long id;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private Long royalty;
    private Company company;
    private Recording recording;
}
