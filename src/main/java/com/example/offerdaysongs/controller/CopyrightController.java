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

    @GetMapping("/") //получить весь список
    public List<CopyrightDto> getAll(){
        return copyrightService.getAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id:[\\d]+}") // получить права по id
    public CopyrightDto get(@PathVariable(ID) long id) {
        var copyright = copyrightService.getById(id);
        return convertToDto(copyright);
    }

    @GetMapping("/getByCompany/{id:[\\d]+}") // получить права принадлежащей компании(id)
    public List<CopyrightDto> getByCompany(@PathVariable(ID) long id) {

        return copyrightService.getByCompanyId(id).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    @PostMapping("/") // сохранить/обновить(если передается id)
    public CopyrightDto save(@RequestBody CreateCopyrightRequest request) {
        return convertToDto(copyrightService.save(request));
    }

    @DeleteMapping("/") //удалить права по id
    public void delete(@RequestParam(value="id") Integer id){
        copyrightService.deleteById(id);
    }


    @GetMapping("/getByNameTitlePeriod") //получить права песни за период
    public List<CopyrightDto> getByNameTitlePeriod(@RequestParam(value="name") String name,
                                                   @RequestParam(value="title") String title,
                                                   @RequestParam(value="startDate") String startDate,
                                                   @RequestParam(value="endDate") String endDate){

        return copyrightService.getCopyrightByNameTitlePeriod(startDate, endDate, title, name).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/getRoyalty") // получить цену прав по исполнителю названию и дате
    public String getRoyaltyBySong(@RequestParam(value="name") String name,
                                   @RequestParam(value="title") String title,
                                   @RequestParam(value="currentDate") String currentDate){

        return "Стоимость прав на песню составит - " + copyrightService.getRoyaltyBySong(currentDate,title,name);
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
