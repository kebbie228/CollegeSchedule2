package org.itstep.controllers;

import org.itstep.model.Specialization;
import org.itstep.services.SpecializationService;
import org.itstep.util.SpecializationValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/specializations")
public class SpecializationController {

    private final SpecializationService specializationService;
   private  final SpecializationValidator specializationValidator;


    public SpecializationController(SpecializationService specializationService, SpecializationValidator specializationValidator) {
        this.specializationService = specializationService;
        this.specializationValidator = specializationValidator;
    }


    @GetMapping()
    public String show(Model model){
        model.addAttribute("specializations", specializationService.findAll());
        return "specializations/show";
    }

    @GetMapping("/{id}")
    public String index(@PathVariable("id") Long id, Model model, @ModelAttribute("specialization") Specialization specialization) {
        model.addAttribute("specialization", specializationService.findById(id));
        return "specializations/index";
    }
    @GetMapping("/new")
    public String newSpecialization(@ModelAttribute("specialization") Specialization specialization) {
        return "specializations/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("specialization") @Valid Specialization specialization,
                         BindingResult bindingResult) {
    specializationValidator.validate(specialization,bindingResult);
        if (bindingResult.hasErrors())
            return "specializations/new";
        specializationService.save(specialization);
        return "redirect:/specializations";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("specialization", specializationService.findById(id));
        return "specializations/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("specialization") @Valid Specialization specialization, BindingResult bindingResult,
                         @PathVariable("id") Long id) {
        specializationValidator.validate(specialization,bindingResult);
        if (bindingResult.hasErrors())
            return "specializations/edit";

        specializationService.update(id,specialization);
        return "redirect:/specializations";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        specializationService.delete(id);
        return "redirect:/specializations";
    }


}


