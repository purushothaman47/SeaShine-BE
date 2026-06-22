package seaShineMarine.SeaShinePvtLtd.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import seaShineMarine.SeaShinePvtLtd.model.ContactEntity;
import seaShineMarine.SeaShinePvtLtd.service.ContactService;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    public String submitContact(
            @RequestBody ContactEntity contact) {

        contactService.saveMessage(contact);

        return "Message Submitted Successfully";
    }

    @GetMapping
    public List<ContactEntity> getAllMessages() {
        return contactService.getAllMessages();
    }

    @GetMapping("/{id}")
    public ContactEntity getMessageById(
            @PathVariable Integer id) {

        return contactService.getMessageById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteMessage(
            @PathVariable Integer id) {

        contactService.deleteMessage(id);

        return "Message Deleted Successfully";
    }
}