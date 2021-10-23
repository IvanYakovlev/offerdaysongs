package com.example.offerdaysongs.controller;

import com.example.offerdaysongs.dto.CompanyDto;
import com.example.offerdaysongs.dto.CopyrightDto;
import com.example.offerdaysongs.dto.RecordingDto;
import com.example.offerdaysongs.dto.SingerDto;
import com.example.offerdaysongs.dto.requests.CreateCopyrightRequest;
import com.example.offerdaysongs.model.Copyright;
import com.example.offerdaysongs.service.CopyrightService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/copyrights")
public class CopyrightController {

    private static final String ID = "id";
    private final CopyrightService copyrightService;

    public CopyrightController(CopyrightService copyrightService) {
        this.copyrightService = copyrightService;
    }

    @GetMapping("/")
    public List<CopyrightDto> getAll(){
        return copyrightService.getAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id:[\\d]+}")
    public CopyrightDto get(@PathVariable(ID) long id) {
        var copyright = copyrightService.getById(id);
        return convertToDto(copyright);
    }

    @GetMapping("/getByCompany/{id:[\\d]+}")
    public List<CopyrightDto> getByCompany(@PathVariable(ID) long id) {

        return copyrightService.getByCompanyId(id).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    @PostMapping("/")
    public CopyrightDto save(@RequestBody CreateCopyrightRequest request) {
        return convertToDto(copyrightService.save(request));
    }

    @DeleteMapping("/")
    public void delete(@RequestParam(value="id") Integer id){
        copyrightService.deleteById(id);
    }


    @GetMapping("/getByNameTitlePeriod")
    public List<CopyrightDto> getByNameTitlePeriod(@RequestParam(value="name") String name,
                                                   @RequestParam(value="title") String title,
                                                   @RequestParam(value="startDate") String startDate,
                                                   @RequestParam(value="endDate") String endDate){

        return copyrightService.getCopyrightByNameTitlePeriod(startDate, endDate, title, name).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/getRoyalty")
    public String getRoyaltyBySong(@RequestParam(value="name") String name,
                                   @RequestParam(value="title") String title,
                                   @RequestParam(value="currentDate") String currentDate){
        List<Copyright> copyrightList = copyrightService.getRoyaltyBySong(currentDate,title,name);
        long royalty=0;
        for (Copyright copyright:
             copyrightList) {
            royalty+=copyright.getRoyalty();
        }

        return "Стоимость прав на песню составит - " + royalty;
    }



    private CopyrightDto convertToDto(Copyright copyright){
        var company = copyright.getCompany();
        var recording = copyright.getRecording();
        return new CopyrightDto(copyright.getId(),
                copyright.getStartDate(),
                copyright.getEndDate(),
                copyright.getRoyalty(),
                company != null ? new CompanyDto(company.getId(), company.getName()) : null,
                recording !=null? new RecordingDto(recording.getId(),recording.getTitle(),recording.getVersion(),recording.getReleaseTime(),
                                                   recording.getSinger() != null ? new SingerDto(recording.getSinger().getId(), recording.getSinger().getName()) : null) : null
        );
    }
}
