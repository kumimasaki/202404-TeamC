package rp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import rp.com.services.ReportsService;

@Controller
@RequestMapping("/user/report")
public class UserReportHideController {

    @Autowired
    private ReportsService reportsService;

    @PostMapping("/hide")
    public String hideReport(@RequestParam("reportId") Long reportId) {
        reportsService.hideReportById(reportId);
        return "redirect:/user/report/list";
    }
}
