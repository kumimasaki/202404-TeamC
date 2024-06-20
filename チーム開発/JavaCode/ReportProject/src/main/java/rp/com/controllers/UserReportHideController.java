package rp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import rp.com.services.ReportsService;

@Controller
public class UserReportHideController {

    @Autowired
    private ReportsService reportsService;

    @PostMapping("/user/report/delete/{reportId}")
    public String hideReport(@PathVariable Long reportId) {
        reportsService.hideReportById(reportId);
        return "redirect:/user/report/list";
    }
}
