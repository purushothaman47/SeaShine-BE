package seaShineMarine.SeaShinePvtLtd.repo;

import seaShineMarine.SeaShinePvtLtd.model.ContactEntity;

import java.util.List;

public interface ContactRepository {

    List<ContactEntity> getAllMessages();

    ContactEntity getMessageById(Integer id);

    int saveMessage(ContactEntity contact);

    int deleteMessage(Integer id);
}
