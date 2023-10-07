package com.dmochowski.demo.rest;

import com.dmochowski.demo.entity.Code;
import com.dmochowski.demo.entity.Offer;
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
    public List<Offer> getAll(){
        return offerService.findAll();
    }

    @GetMapping("/{id}")
    public Offer getById(@PathVariable int id){
        return offerService.findById(id);
    }

    @GetMapping("/{category}")
    public List<Offer> getByCategory(@PathVariable String category){
        return offerService.findByCategory(category);
    }

    @DeleteMapping("/{id}")
    public boolean remove(@PathVariable int id, @RequestBody Code code){
        System.out.println(code);
        return offerService.delete(id, code.code());
    }

    @PostMapping()
    public Offer create(@RequestBody Offer offer){
        return offerService.add(offer);
    }
}
