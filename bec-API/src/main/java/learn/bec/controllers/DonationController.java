package learn.bec.controllers;

import jakarta.validation.Valid;
import learn.bec.domain.DonationService;
import learn.bec.models.Donation;
import learn.bec.validation.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static learn.bec.controllers.ErrMapper.mapErrs;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DonationController {

    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @GetMapping
    public List<Donation> findAll() {return donationService.findAll(); }


    @GetMapping("/{donationId}")
    public ResponseEntity<Donation> findById(@PathVariable int donationId) {
        Donation donation = donationService.findById(donationId);
        if(donation == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(donation, HttpStatus.OK);
    }

    @PostMapping("/donation")
    public ResponseEntity<?> add(@RequestBody @Valid Donation donation, BindingResult bindingResult) {
        Result<Donation> result = donationService.save(donation);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(mapErrs(bindingResult), HttpStatus.BAD_REQUEST);
        }

        if (donation.getDonationId() <= 0) {
            return new ResponseEntity<>(mapErrs("donationId", "donation ID has not been set"), HttpStatus.BAD_REQUEST);
        }

        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getErrs(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
    }
}
