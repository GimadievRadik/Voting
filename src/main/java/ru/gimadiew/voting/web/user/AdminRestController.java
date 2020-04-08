package ru.gimadiew.voting.web.user;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gimadiew.voting.model.User;

import java.util.List;

@RestController
@RequestMapping(value = AdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestController extends AbstractUserController {

    static final String REST_URL = "/rest/admin/users";

    @GetMapping
    public List<User> getAll() {
        return super.getAll();
    }
//
//    @Override
//    @GetMapping("/{id}")
//    public User get(@PathVariable int id) {
//        return super.get(id);
//    }
//
//    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<User> createWithLocation(@RequestBody User user) {
//        User created = super.create(user);
//        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path(REST_URL + "/{id}")
//                .buildAndExpand(created.getId()).toUri();
//        return ResponseEntity.created(uriOfNewResource).body(created);
//    }
//
//    @Override
//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void delete(@PathVariable int id) {
//        super.delete(id);
//    }
//
//    @Override
//    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(value = HttpStatus.NO_CONTENT)
//    public void update(@RequestBody User user, @PathVariable int id) {
//        super.update(user, id);
//    }
//
//    @GetMapping("/by")
//    public User getByMail(@RequestParam String email) {
//        return super.getByMail(email);
//    }
}