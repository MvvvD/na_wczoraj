package com.dmochowski.demo.rest;

import com.dmochowski.demo.entity.Code;
import com.dmochowski.demo.entity.Offer;
import com.dmochowski.demo.entity.OfferCodeless;
import com.dmochowski.demo.service.OfferService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/offers")
@RestController
@AllArgsConstructor
public class OfferController {
    private final OfferService offerService;

    @GetMapping()
    public List<OfferCodeless> getAll(){
        return offerService.findAllSortedCodeless();
    }

    @GetMapping("/id/{id}")
    public OfferCodeless getById(@PathVariable int id){
        return offerService.findByIdCodeless(id);
    }

    @GetMapping("/category")
    @ResponseBody
    public List<OfferCodeless> getByCategory(@RequestParam(value = "category") String category) {
        return offerService.findByCategory(category);
    }

    @PutMapping("/id/{id}")
    public OfferCodeless update(@PathVariable int id, @RequestBody Code code, Offer offer){
        return offerService.update(id, offer, code.code());
    }

    @PutMapping("/id/{id}/activate")
    public boolean activate(@PathVariable int id, @RequestBody Code code) {
        return offerService.activation(id, code.code());
    }

    @DeleteMapping("/id/{id}")
    public boolean remove(@PathVariable int id, @RequestBody Code code){
        return offerService.delete(id, code.code());
    }

    @PostMapping()
    public Offer create(@RequestBody Offer offer){
        return offerService.add(offer);
    }
}
