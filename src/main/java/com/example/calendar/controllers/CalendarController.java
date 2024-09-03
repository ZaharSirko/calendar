package com.example.calendar.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CalendarController {

    @GetMapping("/calendar")
    public String getCalendar(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "interval", required = false, defaultValue = "30") int interval,
            Model model) {

        LocalDate startDate = startDateStr != null ?
                LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd")) :
                LocalDate.now();

        DayOfWeek firstDayOfWeek = DayOfWeek.MONDAY;

        LocalDate firstWeekStart = startDate.with(firstDayOfWeek);

        List<List<DayInfo>> weeks = new ArrayList<>();
        List<DayInfo> currentWeek = new ArrayList<>();

        LocalDate tempDate = firstWeekStart;
        while (tempDate.isBefore(startDate)) {
            currentWeek.add(null);
            tempDate = tempDate.plusDays(1);
        }

        for (int i = 0; i < interval; i++) {
            LocalDate currentDate = startDate.plusDays(i);
            boolean isWorkday = i % 3 == 0;
            DayInfo dayInfo = new DayInfo(currentDate, isWorkday);
            currentWeek.add(dayInfo);

            if (currentWeek.size() == 7) {
                weeks.add(currentWeek);
                currentWeek = new ArrayList<>();
            }
        }

        while (currentWeek.size() > 0 && currentWeek.size() < 7) {
            currentWeek.add(null);
        }
        if (!currentWeek.isEmpty()) {
            weeks.add(currentWeek);
        }

        model.addAttribute("weeks", weeks);
        model.addAttribute("startDate", startDate);
        model.addAttribute("interval", interval);

        return "calendar";
    }

    static class DayInfo {
        private final LocalDate date;
        private final boolean isWorkday;

        public DayInfo(LocalDate date, boolean isWorkday) {
            this.date = date;
            this.isWorkday = isWorkday;
        }

        public LocalDate getDate() {
            return date;
        }

        public boolean isWorkday() {
            return isWorkday;
        }
    }
}