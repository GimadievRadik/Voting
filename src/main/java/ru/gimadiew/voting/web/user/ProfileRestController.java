package ru.gimadiew.voting.web.user;


import ru.gimadiew.voting.model.User;

import static ru.gimadiew.voting.web.SecurityUtil.authUserId;

//@RestController
//@RequestMapping(ProfileRestController.REST_URL)
public class ProfileRestController extends AbstractUserController {
//    static final String REST_URL = "/rest/profile";

//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public User get() {
//        return super.get(authUserId());
//    }

//    @DeleteMapping
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void delete() {
//        super.delete(authUserId());
//    }

//    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void update(@RequestBody User user) {
//        super.update(user, authUserId());
//    }
//    public void update(User user) {
//        super.update(user, authUserId());
//    }

//    @GetMapping(value = "/text")
    public String testUTF() {
        return "Русский текст";
    }
}