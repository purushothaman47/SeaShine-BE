package seaShineMarine.SeaShinePvtLtd.service;

import seaShineMarine.SeaShinePvtLtd.model.ContentEntity;

import java.util.List;

public interface ContentService {

    List<ContentEntity> getAllContent();

    ContentEntity getContentById(Integer id);

    ContentEntity getContentBySection(String section);

    void addContent(ContentEntity content);

    void updateContent(ContentEntity content);

    void deleteContent(Integer id);
}