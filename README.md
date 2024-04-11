![img.png](img.png)  


### Each Annotation Has Its Own Method Interceptor
#
### Annotations && Benefits
- Fine-grained authorization; depends on the method being called for authorization  
- Enforcing security at the service layer  
- easy to implement  
####
- `@PreAuthorize`
- `@PostAuthorize`
- `@PreFilter`
- `@PostFilter`
- `@Secured`  

ALL work on any Spring bean


```java
@Autowired
BankService bankService;

@WithMockUser(roles="ADMIN")
@Test
void readAccountWithAdminRoleThenInvokes() {
    Account account = this.bankService.readAccount("12345678");
    // ... assertions
}

@WithMockUser(roles="WRONG")
@Test
void readAccountWithWrongRoleThenAccessDenied() {
    assertThatExceptionOfType(AccessDeniedException.class).isThrownBy(
        () -> this.bankService.readAccount("12345678"));
}
```