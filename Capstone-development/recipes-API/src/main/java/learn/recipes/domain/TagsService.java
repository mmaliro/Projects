package learn.recipes.domain;

import learn.recipes.data.TagsRepository;
import learn.recipes.models.Food;
import learn.recipes.models.Tags;
import learn.recipes.validation.Result;
import learn.recipes.validation.ResultType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagsService {

    private final TagsRepository tagsRepository;

    public TagsService(TagsRepository tagsRepository) {
        this.tagsRepository = tagsRepository;
    }

    public List<Tags> findAll() {return tagsRepository.findAll(); }

    public Tags findById(int tagsId) {return tagsRepository.findById(tagsId).orElse(null);}

    public Result<Tags> save(Tags tag) {
        Result<Tags> result = validate(tag);
        if(!result.isSuccess()) {
            return result;
        }

        Tags payload = tagsRepository.save(tag);
        result.setPayload(payload);
        return result;

    }

    public boolean deleteById(int tagId) {

        if (tagId <= 0) {
            return false;
        }

        if (tagsRepository.findById(tagId).isEmpty()) {
            return false;
        }

        tagsRepository.deleteById(tagId);
        return true;
    }

    private Result<Tags> validate(Tags tag) {
        Result<Tags> result = new Result<>();

        if (tag == null) {
            result.addErr("", "tag cannot be null", ResultType.INVALID);
            return result;
        }

        if (tag.getTagId() > 0) {
            if (!tagsRepository.existsById(tag.getTagId())) {
                result.addErr("", "not found", ResultType.NOT_FOUND);
                return result;
            }
        }

        if(Validations.isNullOrBlank(tag.getTagName())) {
            result.addErr("", "tag name is required", ResultType.INVALID);
        }
        if(tagsRepository.findByTagName(tag.getTagName()) != null) {
            result.addErr("", "tag name must be unique", ResultType.ALREADY_EXISTS);
        }

        return result;
    }

}
