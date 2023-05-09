package learn.bec.domain;

import learn.bec.data.DonationRepository;
import learn.bec.models.Donation;
import learn.bec.validation.Result;
import learn.bec.validation.ResultType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DonationService {

    private final DonationRepository donationRepository;

    public DonationService(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public List<Donation> findAll() {
        return donationRepository.findAll();
    }

    public Donation findById(int donationId) {
        return donationRepository.findById(donationId).orElse(null);
    }

    public Result<Donation> save(Donation donation) {
        Result<Donation> result = validate(donation);
        if(!result.isSuccess()) {
            return result;
        }

        Donation payload = donationRepository.save(donation);
        result.setPayload(payload);
        return result;

    }

    public Result<Donation> add(Donation donation) {
        Result<Donation> result = validate(donation);
        if (!result.isSuccess()) {
            return result;
        }

        donation = donationRepository.save(donation);

        result.setPayload(donation);
        return result;
    }



    private Result<Donation> validate(Donation donation) {
        Result<Donation> result = new Result<>();

        if (donation == null) {
            result.addErr("", "Donation cannot be null", ResultType.INVALID);
            return result;
        }

        if (donation.getAmount().compareTo(BigDecimal.valueOf(0)) <= 0) {
            result.addErr("", "Donation amount must be greater than zero", ResultType.INVALID);
        }

        if (donation.getNote() != null && donation.getNote().length() > 500) {
            result.addErr("", "Note cannot exceed 500 characters", ResultType.INVALID);
        }
        return result;
    }

}

