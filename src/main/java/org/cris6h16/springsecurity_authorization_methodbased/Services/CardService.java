package org.cris6h16.springsecurity_authorization_methodbased.Services;

import org.cris6h16.springsecurity_authorization_methodbased.CustomAnnotations.OwnerInResponseSameToAuthenticationName;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service("MyCardService")
@Scope("singleton")
public class CardService {
    List<Card> cards;

    public CardService() {
        this.cards = List.of(
                new Card("cris6h16", "1234-5678-9012-3456"),
                new Card("juan", "1234-5678-9012-3457"),
                new Card("cris6h16", "1234-5678-9012-0058"));
    }

    public String hello() {
        return "Hello, from CardService!";
    }

    // ----- @PreAuthorize, @PostAuthorize.... works in any Spring-managed bean ----- \\


    @PreAuthorize("hasAuthority('permission:read')")
//    @PostAuthorize("returnObject.getBody().owner == authentication.name")
    @OwnerInResponseSameToAuthenticationName // same as above commented line
    public ResponseEntity<?> getByName(String owner) {
        return ResponseEntity.ok(cards.get(0));
    }

    /*
    ```

    @Autowired
    BankService bankService;

    @WithMockUser(username="cris6h16")
    @Test
    void readAccountWhenOwnedThenReturns() {
        Account account = this.bankService.readAccount("12345678");
        // ... assertions
    }

    @WithMockUser(username="wrong")
    @Test
    void readAccountWhenNotOwnedThenAccessDenied() {
        assertThatExceptionOfType(AccessDeniedException.class).isThrownBy(
            () -> this.bankService.readAccount("12345678"));
}
    ```
     */

    @PreFilter("filterObject.owner == authentication.name")
    // `filterObject` is the name of the variable that represents each element in the collection
    public Collection<Card> updateAllMine(Card... cards) {
        // ... `Cards` will only contain the Cards owned by the logged-in user
        for (int i = 0; i < cards.length; i++) {
            // ... update the Cards              PD: we can't is a List of Records --> Just Examples
        }
        return List.of(cards);
    }

    /*

    @Autowired
    BankService bankService;

    @WithMockUser(username="owner")
    @Test
    void updateAccountsWhenOwnedThenReturns() {
        Account ownedBy = ...
        Account notOwnedBy = ...
        Collection<Account> updated = this.bankService.updateAccounts(ownedBy, notOwnedBy);
        assertThat(updated).containsOnly(ownedBy);
    }

     */
    // supported types
    @PreFilter("filterObject.owner == authentication.name")
    public Collection<Card> updateCards(Card[] accounts) {
        return null;
    }

    @PreFilter("filterObject.owner == authentication.name")
    public Collection<Card> updateCards(Collection<Card> accounts) {
        return null;
    }

    @PreFilter("filterObject.value.owner == authentication.name")
    public Collection<Card> updateCards(Map<String, Card> accounts) {
        return null;
    }

    @PreFilter("filterObject.owner == authentication.name")
    public Collection<Card> updateCards(Stream<Card> accounts) {
        return null;
    }





    @PostFilter("filterObject.owner == authentication.name")
    public Collection<Card> readCards(String... ids) {
        // ... the return value will be filtered to only contain the cards owned by the logged-in user
        return cards;
    }
/*
    @Autowired
    BankService bankService;

    @WithMockUser(username="owner")
    @Test
    void readAccountsWhenOwnedThenReturns() {
        Collection<Account> accounts = this.bankService.updateAccounts("owner", "not-owner");
        assertThat(accounts).hasSize(1);
        assertThat(accounts.get(0).getOwner()).isEqualTo("owner");
    }
 */

    record Card(String owner, String number) {
    }
}
