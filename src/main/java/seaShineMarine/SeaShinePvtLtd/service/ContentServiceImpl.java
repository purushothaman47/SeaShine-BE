package seaShineMarine.SeaShinePvtLtd.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seaShineMarine.SeaShinePvtLtd.model.ContentEntity;
import seaShineMarine.SeaShinePvtLtd.repo.ContentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {

    private final ContentRepository contentRepository;

    @Override
    public List<ContentEntity> getAllContent() {
        return contentRepository.getAllContent();
    }

    @Override
    public ContentEntity getContentById(Integer id) {
        return contentRepository.getContentById(id);
    }

    @Override
    public ContentEntity getContentBySection(String section) {
        return contentRepository.getContentBySection(section);
    }

    @Override
    public void addContent(ContentEntity content) {
        contentRepository.addContent(content);
    }

    @Override
    public void updateContent(ContentEntity content) {
        contentRepository.updateContent(content);
    }

    @Override
    public void deleteContent(Integer id) {
        contentRepository.deleteContent(id);
    }
}