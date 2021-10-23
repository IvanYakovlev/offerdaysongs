package com.example.offerdaysongs.service;

import com.example.offerdaysongs.dto.requests.CreateCopyrightRequest;
import com.example.offerdaysongs.model.Company;
import com.example.offerdaysongs.model.Copyright;
import com.example.offerdaysongs.model.Recording;
import com.example.offerdaysongs.model.Singer;
import com.example.offerdaysongs.repository.CompanyRepository;
import com.example.offerdaysongs.repository.CopyrightRepository;
import com.example.offerdaysongs.repository.RecordingRepository;
import com.example.offerdaysongs.repository.SingerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CopyrightService {

    private final CopyrightRepository copyrightRepository;
    private final CompanyRepository companyRepository;
    private final RecordingRepository recordingRepository;
    private final SingerRepository singerRepository;

    public CopyrightService(CopyrightRepository copyrightRepository, CompanyRepository companyRepository,
    RecordingRepository  recordingRepository, SingerRepository singerRepository) {
        this.copyrightRepository = copyrightRepository;
        this.companyRepository = companyRepository;
        this.recordingRepository = recordingRepository;
        this.singerRepository = singerRepository;
    }

    public List<Copyright> getAll()
    {
        return copyrightRepository.findAll();
    }

    public Copyright getById(long id)
    {
        return copyrightRepository.getById(id);
    }

    public void deleteById(long id)
    {
         copyrightRepository.deleteById(id);
    }

    @Transactional
    public Copyright save(CreateCopyrightRequest request) {
        Copyright copyright = new Copyright();

        if (request.getId()!=null){
            copyright.setId(request.getId());
        }

        copyright.setStartDate(request.getStartDate());
        copyright.setEndDate(request.getEndDate());
        copyright.setRoyalty(request.getRoyalty());

        var companyDto = request.getCompany();
        if (companyDto != null) {
            var company = companyRepository.findById(companyDto.getId()).orElseGet(() -> {
                var temp = new Company();
                temp.setId(companyDto.getId());
                temp.setName(companyDto.getName());
                return companyRepository.save(temp);
            });
            copyright.setCompany(company);
        }

        var recordingDto = request.getRecording();
        if (recordingDto != null) {
            var recording = recordingRepository.findById(recordingDto.getId()).orElseGet(() -> {
                var temp = new Recording();
                temp.setId(recordingDto.getId());
                temp.setReleaseTime(recordingDto.getReleaseTime());
                temp.setTitle(recordingDto.getTitle());
                temp.setVersion(recordingDto.getVersion());
                var singerDto = recordingDto.getSinger();
                if (singerDto != null) {
                    var singer = singerRepository.findById(singerDto.getId()).orElseGet(() -> {
                        var temp1 = new Singer();
                        temp1.setId(singerDto.getId());
                        temp1.setName(singerDto.getName());
                        return singerRepository.save(temp1);
                    });
                    temp.setSinger(singer);
                }

                return recordingRepository.save(temp);
            });
            copyright.setRecording(recording);
        }

        return copyrightRepository.save(copyright);
    }

    public List<Copyright> getByCompanyId(long id) {
       return copyrightRepository.findAllByCompanyId(id);
    }

    public List<Copyright> getCopyrightByNameTitlePeriod(String startDate, String endDate, String title, String name) {
        return copyrightRepository.findCopyrightByNameTitlePeriod(startDate,endDate,
                                                                  title,name);
    }

    public  List<Copyright> getRoyaltyBySong(String currentDate, String title, String name) {

        return copyrightRepository.findCopyrightBySongAndDate(currentDate,title,name);
    }
}
