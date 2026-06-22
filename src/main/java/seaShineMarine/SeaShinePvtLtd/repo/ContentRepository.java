package seaShineMarine.SeaShinePvtLtd.repo;

import seaShineMarine.SeaShinePvtLtd.model.ContentEntity;

import java.util.List;

public interface ContentRepository {

    List<ContentEntity> getAllContent();

    ContentEntity getContentById(Integer id);

    ContentEntity getContentBySection(String sectionName);

    int addContent(ContentEntity content);

    int updateContent(ContentEntity content);

    int deleteContent(Integer id);
}