package learn.recipes.domain;

import learn.recipes.TestHelper;
import learn.recipes.data.TagsRepository;
import learn.recipes.models.Tags;
import learn.recipes.validation.Result;
import learn.recipes.validation.ResultType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TagsServiceTest {

    @Autowired
    TagsService service;

    @MockBean
    TagsRepository repository;

    @Test
    void shouldAddTag() {
        Tags validNewTag = TestHelper.makeTag(0);

        when(repository.findByTagName(validNewTag.getTagName())).thenReturn(null);
        when(repository.save(validNewTag)).thenReturn(validNewTag);
        Result<Tags> result = service.save(validNewTag);

        assertTrue(result.isSuccess());
        assertEquals(validNewTag, result.getPayload());
    }

    @Test
    void shouldUpdateTag() {
        Tags validUpdateTag = TestHelper.makeTag(1);

        when(repository.existsById(validUpdateTag.getTagId())).thenReturn(true);
        when(repository.findByTagName(validUpdateTag.getTagName())).thenReturn(null);
        when(repository.save(validUpdateTag)).thenReturn(validUpdateTag);
        Result<Tags> result = service.save(validUpdateTag);

        assertTrue(result.isSuccess());
        assertEquals(validUpdateTag, result.getPayload());
    }

    @Test
    void shouldDeleteTag() {
        Tags tag = TestHelper.makeTag(2);
        when(repository.findById(2)).thenReturn(Optional.of(tag));

        assertTrue(service.deleteById(2));
    }

    @Test
    void shouldNotDeleteTagWithInvalidId() {
        assertFalse(service.deleteById(0));
    }

    @Test
    void shouldNotDeleteMissingTag() {
        Tags missingTag = TestHelper.makeTag(7);
        when(repository.findById(missingTag.getTagId())).thenReturn(Optional.empty());

        assertFalse(service.deleteById(missingTag.getTagId()));
    }

    @Test
    void shouldNotSaveNullTag() {
        Result<Tags> result = service.save(null);

        assertFalse(result.isSuccess());
        assertEquals("tag cannot be null", result.getErrs().get(0).getMessage());
    }

    @Test
    void shouldNotUpdateMissingTag() {
        Tags missingTag = TestHelper.makeTag(7);
        when(repository.existsById(missingTag.getTagId())).thenReturn(false);

        Result<Tags> result = service.save(missingTag);

        assertFalse(result.isSuccess());
        assertEquals("not found", result.getErrs().get(0).getMessage());
    }

    @Test
    void shouldNotSaveTagWithBlankName() {
        Tags blankNameTag = TestHelper.makeTag(0);
        blankNameTag.setTagName(" ");

        Result<Tags> blankResult = service.save(blankNameTag);

        assertFalse(blankResult.isSuccess());
        assertEquals("tag name is required", blankResult.getErrs().get(0).getMessage());
    }
    @Test
    void shouldNotSaveTagWithNullName() {
        Tags nullNameTag = TestHelper.makeTag(0);
        nullNameTag.setTagName(null);

        Result<Tags> nullResult = service.save(nullNameTag);

        assertFalse(nullResult.isSuccess());
        assertEquals("tag name is required", nullResult.getErrs().get(0).getMessage());
    }

    @Test
    void shouldNotSaveTagWithExistingName() {
        Tags existingNameTag = TestHelper.makeTag(0);
        existingNameTag.setTagName("existing name");
        when(repository.findByTagName(existingNameTag.getTagName())).thenReturn(existingNameTag);

        Result<Tags> result = service.save(existingNameTag);

        assertFalse(result.isSuccess());
        assertEquals("tag name must be unique", result.getErrs().get(0).getMessage());
    }

}
