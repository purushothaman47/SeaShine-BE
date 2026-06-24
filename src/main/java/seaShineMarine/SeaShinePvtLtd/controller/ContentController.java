package seaShineMarine.SeaShinePvtLtd.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import seaShineMarine.SeaShinePvtLtd.model.ContentEntity;
import seaShineMarine.SeaShinePvtLtd.service.ContentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/content")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ContentController {

    private final ContentService contentService;

    @GetMapping
    public List<ContentEntity> getAllContent() {
        return contentService.getAllContent();
    }

    @GetMapping("/{id}")
    public ContentEntity getById(@PathVariable Integer id) {
        return contentService.getContentById(id);
    }

    @GetMapping("/section/{sectionName}")
    public ContentEntity getBySection(
            @PathVariable String sectionName) {

        return contentService.getContentBySection(sectionName);
    }

    @PostMapping
    public String addContent(
            @RequestBody ContentEntity content) {

        contentService.addContent(content);

        return "Content Added Successfully";
    }

    @PutMapping("/{id}")
    public String updateContent(
            @PathVariable Integer id,
            @RequestBody ContentEntity content) {

        content.setId(id);

        contentService.updateContent(content);

        return "Content Updated Successfully";
    }

    @DeleteMapping("/{id}")
    public String deleteContent(
            @PathVariable Integer id) {

        contentService.deleteContent(id);

        return "Content Deleted Successfully";
    }
}