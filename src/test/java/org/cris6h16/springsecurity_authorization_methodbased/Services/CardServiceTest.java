package org.cris6h16.springsecurity_authorization_methodbased.Services;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import java.io.IOException;
import org.springframework.security.access.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CardAdminServiceTest {

    @Autowired
    CardAdminService cardService;

    //----------------------------------------------------------------

    @Nested
    class helloAdmin {
        @WithMockUser(authorities = {"ADMIN"})
        @Test
        void responseNotNull() {
            ResponseEntity<?> res = cardService.helloAdmin();
            assertNotNull(res);
        }

        @WithMockUser(authorities = "ADMIN")
        @Test
        void responseOk() {
            ResponseEntity<?> res = cardService.helloAdmin();
            assertEquals(HttpStatus.OK, res.getStatusCode());
        }

        @WithMockUser(authorities = {"USER", "INVITED"}) // GrantedAuthority will be created for each
        @Test
        void isNotAnAdminThenAccessDeniedException() {
            assertThrows(AccessDeniedException.class, () -> cardService.helloAdmin());
        }

    }

    //----------------------------------------------------------------
    @Nested
    class getByName {
        @WithMockUser(authorities = "ADMIN", username = "cris6h16")
        @Test
        void responseAdminGetsItsCardThenCardOwnerIsSameToAuthenticationName() {
            ResponseEntity<CardAdminService.Card> res = cardService.getByName("cris6h16");
            assertEquals("cris6h16", res.getBody().owner());
        }

        @WithMockUser(authorities = "ADMIN", username = "cris6h16")
        @Test
        void responseAdminGetsCardOfOthersOwnersThenAccessDeniedException() throws IOException {
            assertThrows(AccessDeniedException.class, () -> cardService.getByName("juan"));
        }

        @WithMockUser(authorities = "USER")
        @Test
        void responseNotIsAdminThenAccessDeniedException() throws IOException {
            assertThrows(AccessDeniedException.class, () -> cardService.helloAdmin());
        }
    }

    //----------------------------------------------------------------

//    @Nested
//    class updateAllMine...
//    ..............
}