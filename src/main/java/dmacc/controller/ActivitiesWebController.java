/**
 * @author Julie Burger - jaburger
 * CIS175 - Spring 2023
 * Apr 2, 2023
 */
package dmacc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import dmacc.beans.Activities;
import dmacc.repository.ActivitiesRepository;

@Controller
public class ActivitiesWebController {
	@Autowired
	ActivitiesRepository activitiesRepo;
	
	@GetMapping({"/", "/viewAll"})
	public String viewAllActivities(Model model)	{
		if(activitiesRepo.findAll().isEmpty()) {
			return addNewActivities(model);
		}
		model.addAttribute("activities", activitiesRepo.findAll());
		return "activitiesResults";
	}
	
	@GetMapping("/inputActivity")			//connects to the input action
	public String addNewActivities(Model model) {
		Activities a = new Activities();
		model.addAttribute("newActivity", a); //connects to the input pages object name
		return "activityInput";
	}
	
	@PostMapping("/inputActivity") 
	public String addNewActivities(@ModelAttribute Activities a, Model model) {
		activitiesRepo.save(a);
		return viewAllActivities(model);
	}
	
	@GetMapping("edit/{activityId}")
	public String showUpdateActivities(@PathVariable("activityId") long activityId, Model model) {
		Activities a = activitiesRepo.findById(activityId).orElse(null);
		model.addAttribute("newActivity", a);
		return "activityInput";
	}
	
	@PostMapping("/update/{activityId}")
	public String reviseActivities(Activities a, Model model) {
		activitiesRepo.save(a);
		return viewAllActivities(model);
	}
	
	@GetMapping("/delete/{activityId}")
	public String deleteUser(@PathVariable("activityId") long activityId, Model model) {
		Activities a = activitiesRepo.findById(activityId).orElse(null);
		activitiesRepo.delete(a);
		return viewAllActivities(model);
	}
}
