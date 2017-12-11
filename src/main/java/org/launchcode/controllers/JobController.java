package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(method = RequestMethod.GET)
    public String index(@RequestParam("id") int id, Model model) {

        model.addAttribute(jobData.findById(id));
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        if (errors.hasErrors()) {
            return "new-job";
        }

        String thisName = jobForm.getName();
        Employer thisEmployer = jobData.getEmployers().findById(jobForm.getEmployerId());
        Location thisLocation = jobData.getLocations().findById(jobForm.getLocationId());
        PositionType thisPositionType = jobData.getPositionTypes().findById(jobForm.getPositionTypeId());
        CoreCompetency thisCoreCompetency = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId());
        Job newJob = new Job(thisName, thisEmployer, thisLocation, thisPositionType, thisCoreCompetency);
        jobData.add(newJob);
        int jobId = newJob.getId();
        return "forward:/job?id=jobId";

    }
}
