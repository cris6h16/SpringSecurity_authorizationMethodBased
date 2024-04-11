![img.png](img.png)  


### Each Annotation Has Its Own Method Interceptor
- `@PreAuthorize`
- `@PostAuthorize`
- `@PreFilter`
- `@PostFilter`
- `@Secured`



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