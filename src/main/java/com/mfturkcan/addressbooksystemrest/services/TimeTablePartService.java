package com.mfturkcan.addressbooksystemrest.services;

import com.mfturkcan.addressbooksystemrest.models.TimeTablePart;
import com.mfturkcan.addressbooksystemrest.repositories.BookUserRepository;
import com.mfturkcan.addressbooksystemrest.repositories.TimeTablePartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimeTablePartService {
    private final TimeTablePartRepository timeTablePartRepository;
    private final BookUserRepository bookUserRepository;

    @Autowired
    public TimeTablePartService(TimeTablePartRepository timeTablePartRepository, BookUserRepository bookUserRepository) {
        this.timeTablePartRepository = timeTablePartRepository;
        this.bookUserRepository = bookUserRepository;
    }

    public List<TimeTablePart> getTimeTablePartsByUserId(int id){
        return timeTablePartRepository.findByBookUserId(id).orElse(new ArrayList<>());
    }

    public void addTimeTablePart(TimeTablePart timeTablePart, int userId){
        // TODO: throw not found
        var user = bookUserRepository.findById(userId).orElseThrow();
        timeTablePart.setBookUser(user);
        timeTablePartRepository.save(timeTablePart);
    }

    public void updateTimeTablePart(TimeTablePart timeTablePart, int id){
        // throw not found
        var timetable = timeTablePartRepository.findById(id).orElseThrow();
        timetable.setClassName(timeTablePart.getClassName());
        timetable.setHour(timeTablePart.getHour());
        timetable.setDayOfWeek(timeTablePart.getDayOfWeek());

        timeTablePartRepository.save(timetable);
    }

    public void deleteTimeTablePart(int id){
        timeTablePartRepository.deleteById(id);
    }
}