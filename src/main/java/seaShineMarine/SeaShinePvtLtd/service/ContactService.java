package seaShineMarine.SeaShinePvtLtd.service;

import seaShineMarine.SeaShinePvtLtd.model.ContactEntity;

import java.util.List;

public interface ContactService {

    List<ContactEntity> getAllMessages();

    ContactEntity getMessageById(Integer id);

    void saveMessage(ContactEntity contact);

    void deleteMessage(Integer id);
}