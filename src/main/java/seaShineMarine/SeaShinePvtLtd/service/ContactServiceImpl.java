package seaShineMarine.SeaShinePvtLtd.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seaShineMarine.SeaShinePvtLtd.model.ContactEntity;
import seaShineMarine.SeaShinePvtLtd.repo.ContactRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl
        implements ContactService {

    private final ContactRepository contactRepository;

    @Override
    public List<ContactEntity> getAllMessages() {
        return contactRepository.getAllMessages();
    }

    @Override
    public ContactEntity getMessageById(Integer id) {
        return contactRepository.getMessageById(id);
    }

    @Override
    public void saveMessage(ContactEntity contact) {
        contactRepository.saveMessage(contact);
    }

    @Override
    public void deleteMessage(Integer id) {
        contactRepository.deleteMessage(id);
    }
}
