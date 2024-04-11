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

@Service
public class CardAdminService {
    List<Card> cards;

    public CardAdminService() {
        this.cards = List.of(
                new Card("cris6h16", "1234-5678-9012-3456"),
                new Card("juan", "1234-5678-9012-3457"),
                new Card("cris6h16", "1234-5678-9012-0058"));
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> helloAdmin() {
        // ... is only invoked if the `Authentication` has the `ROLE_ADMIN` authority
        return ResponseEntity.ok("Hello Admin");
    }


    // you cannot place @PreAuthorize twice... use SpEL’s boolean support
    @PreAuthorize("hasAuthority('ADMIN')")    //    Hierarchy: USER > permission:read
    @OwnerInResponseSameToAuthenticationName // == @PostAuthorize("returnObject.getBody().owner == authentication.name")
    public ResponseEntity<Card> getByName(String owner) {
        return ResponseEntity.ok(
                cards.stream()
                        .filter(card -> card.owner().equals(owner))
                        .findFirst()
                        .orElse(null));
    }

    @PreFilter("filterObject.owner == authentication.name")
    // `filterObject` is the name of the variable that represents each element in the collection
    public Collection<Card> updateAllMine(Card... cards) {
        // ... `Cards` will only contain the Cards owned by the logged-in user
        for (int i = 0; i < cards.length; i++) {
            // ... update the Cards              PD: we can't is a List of Records --> Just Examples
        }
        return List.of(cards);

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
    }

    /*  PRE_FILTER
    @PreFilter("filterObject.owner == authentication.name")
    public Collection<Account> updateAccounts(Account[] accounts)
    public Collection<Account> updateAccounts(Collection<Account> accounts)
    public Collection<Account> updateAccounts(Map<String, Account> accounts)
    public Collection<Account> updateAccounts(Stream<Account> accounts)
        */

    @PostFilter("filterObject.owner == authentication.name")
    public Collection<Card> readCards(String... ids) { // remember format
        // ... the return value will be filtered to only contain the accounts owned by the logged-in user
        return cards;
    }
    /*  POST_FILTER
    @PostFilter("filterObject.owner == authentication.name")
    public Account[] readAccounts(String... ids)
    public Map<String, Account> readAccounts(String... ids)
    public Stream<Account> readAccounts(String... ids)
    */



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

    public record Card(String owner, String number) {
    }
}
