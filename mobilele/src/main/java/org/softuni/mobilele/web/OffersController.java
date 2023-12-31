package org.softuni.mobilele.web;

import jakarta.validation.Valid;
import org.softuni.mobilele.model.dto.AddOfferDTO;
import org.softuni.mobilele.model.dto.OfferDTO;
import org.softuni.mobilele.service.BrandService;
import org.softuni.mobilele.service.OfferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/offers")
public class OffersController {
    private final OfferService offerService;
    private final BrandService brandService;

    public OffersController(OfferService offerService, BrandService brandService) {
        this.offerService = offerService;
        this.brandService = brandService;
    }

    @ModelAttribute("addOfferDTO")
    public void initialiseUserModel(Model model) {
        model.addAttribute("addOfferDTO", new AddOfferDTO());
    }

    @GetMapping("/all")
    public String all(Model model) {
        model.addAttribute("listOfOffers", offerService.getAllOffers());
        return "offers";
    }

    @GetMapping("/add")
    public String add(Model model) {
        if (!model.containsAttribute("addOfferDTO")) {
            model.addAttribute("addOfferDTO", new AddOfferDTO());
        }
        model.addAttribute("brands", brandService.getAllBrandsWithModels());
        return "offer-add";
    }

    @PostMapping("/add")
    public String add(@Valid AddOfferDTO addOfferDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addOfferDTO", addOfferDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addOfferDTO", bindingResult);
            return "redirect:/offers/add";
        }
        offerService.addOffer(addOfferDTO);
        return "redirect:/offers/all";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model) {
        if (!model.containsAttribute("offerDTO")) {
            model.addAttribute("offerDTO", offerService.getOfferById(id));
        }
        return "details";
    }

    @GetMapping("/details/{id}/update")
    public String update(@PathVariable Long id, Model model) {
        if (!model.containsAttribute("offerDTO")) {
            model.addAttribute("offerDTO", offerService.getOfferById(id));
        }
        model.addAttribute("brands", brandService.getAllBrandsWithModels());
        return "update";
    }

    @PostMapping("/details/{id}/update")
    public String update(@Valid AddOfferDTO addOfferDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, OfferDTO offerDTO) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addOfferDTO", addOfferDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addOfferDTO", bindingResult);
            return "redirect:/offers/details/{id}/update";
        }
        offerService.update(addOfferDTO, offerDTO);
        return "redirect:/offers/details/{id}";
    }

    @GetMapping("/details/{id}/delete")
    public String delete(@PathVariable Long id, Model model, OfferDTO offerDTO) {
        if (model.containsAttribute("offerDTO")) {
            model.addAttribute("offerDTO", offerService.getOfferById(id));
        }
        model.addAttribute("brands", brandService.getAllBrandsWithModels());
        offerService.delete(offerDTO);
        return "redirect:/offers/all";
    }
}
