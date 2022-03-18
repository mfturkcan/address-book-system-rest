package com.mfturkcan.addressbooksystemrest.controllers;

import com.mfturkcan.addressbooksystemrest.models.TimeTablePart;
import com.mfturkcan.addressbooksystemrest.services.TimeTablePartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timetable")
public class TimeTableController {
    private final TimeTablePartService timeTablePartService;

    @Autowired
    public TimeTableController(TimeTablePartService timeTablePartService){
        this.timeTablePartService = timeTablePartService;
    }

    // get timetableparts by userid
    @GetMapping(path = "{userId}")
    public ResponseEntity<List<TimeTablePart>> getTimeTable(@PathVariable int userId){
        return ResponseEntity.ok(timeTablePartService.getTimeTablePartsByUserId(userId));
    }

    // add timetablepart with userid
    @PostMapping(path = "{userId}")
    public ResponseEntity addTimeTable(@PathVariable int userId, @RequestBody TimeTablePart timeTablePart){
        timeTablePartService.addTimeTablePart(timeTablePart, userId);

        return ResponseEntity.ok().build();
    }

    // update timetablepart with userid
    @PatchMapping(path = "{id}")
    public ResponseEntity updateTimeTable(@PathVariable int id, @RequestBody TimeTablePart timeTablePart){
        timeTablePartService.updateTimeTablePart(timeTablePart, id);

        return ResponseEntity.ok().build();
    }

    // delete timetablepart with userid
    @DeleteMapping(path = "{id}")
    public ResponseEntity updateTimeTable(@PathVariable int id){
        timeTablePartService.deleteTimeTablePart(id);
        return ResponseEntity.ok().build();
    }
}