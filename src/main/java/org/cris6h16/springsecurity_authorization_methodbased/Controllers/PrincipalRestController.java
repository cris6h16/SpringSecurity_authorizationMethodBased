package org.cris6h16.springsecurity_authorization_methodbased.Controllers;

import jakarta.websocket.server.PathParam;
import org.cris6h16.springsecurity_authorization_methodbased.Services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.smartcardio.Card;

//@Controller, @ResponseBody == @RestController
@RestController
public class PrincipalRestController {
    CardService cardService;

    @Autowired
    public PrincipalRestController(@Qualifier("MyCardService") CardService myServiceExample) {
        this.cardService = myServiceExample;
    }

    //-----------------------------------
    /*
    - Fine-grained authorization; depends on the method being called for authorization
    - Enforcing security at the service layer
    - easy to implement
     */
    @GetMapping("/")
    public ResponseEntity<?> principal() {
        //...
        return ResponseEntity.ok("Hello");
    }

    // you cannot place @PreAuthorize twice... use SpEL’s boolean support -> "hasAuthority('permission:read') || hasRole('ADMIN')"
    @GetMapping("/")
    @PreAuthorize("hasAuthority('USER')") // Hierarchy: USER > permission:read
    public ResponseEntity<?> principal(@PathParam("name") String name) {
        return cardService.getByName("cris6h16");
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String principal(@PathVariable("id") String id,
                            @PathParam("name") String name) {
        // ... is only invoked if the `Authentication` has the `ROLE_ADMIN` authority
        return cardService.hello();
    }
    //-----------------------------------

    @GetMapping("/hello")
}
