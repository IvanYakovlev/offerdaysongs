package com.example.offerdaysongs.repository;

import com.example.offerdaysongs.model.Copyright;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CopyrightRepository extends JpaRepository<Copyright, Long>, JpaSpecificationExecutor<Copyright> {

    List<Copyright> findAllByCompanyId(long id);

    @Query(value = "select * from COPYRIGHT t0\n" +
            "           left join RECORDING t1 on t0.RECORDING_ID=t1.ID\n" +
            "           left join SINGER t2 on t1.SINGER_ID=t2.ID\n" +
            "           where ((? between START_DATE and END_DATE) or (? between START_DATE and END_DATE))\n" +
            "           and t1.TITLE like ?  and t2.NAME like ?\n",
            nativeQuery = true)
    List<Copyright> findCopyrightByNameTitlePeriod(String startDate, String endDate,
                                                   String title, String name);

    @Query(value = "select * from COPYRIGHT t0\n" +
            "           left join RECORDING t1 on t0.RECORDING_ID=t1.ID\n" +
            "           left join SINGER t2 on t1.SINGER_ID = t2.ID\n" +
            "           where (? between START_DATE and END_DATE) and t1.TITLE like ?  and t2.NAME like ?", nativeQuery = true)
    List<Copyright> findCopyrightBySongAndDate(String currentDate, String title, String name);
}
