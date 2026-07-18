# spring-boot-testing
# 🧪 Spring Boot Testing

<p align="center">

![Java](https://img.shields.io/badge/Java-25-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.x-6DB33F?style=for-the-badge&logo=springboot)
![JUnit5](https://img.shields.io/badge/JUnit_5-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![Mockito](https://img.shields.io/badge/Mockito-78A641?style=for-the-badge)


</p>

---

## 📖 About

A comprehensive **Spring Boot Testing** project built to master:

- ✅ JUnit 5
- ✅ Mockito
- ✅ Service Layer Testing
- ✅ Controller Layer Testing
- ✅ Repository Layer Testing
- ✅ Integration Testing
- ✅ Exception Testing
- ✅ H2 Database Testing
- ✅ Code Coverage (JaCoCo)

This repository follows a **step-by-step learning approach**, where each commit introduces a new testing concept with real Spring Boot examples.

---
# 📚 Learning Roadmap

## ✅ Part 1 — JUnit Basics

- @Test✔️
- Assertions✔️
- assertEquals()✔️
- assertTrue()✔️
- assertFalse()✔️
- assertAll()✔️
- assertThrows()✔️
- assertDoesNotThrow()✔️
- @DisplayName✔️

---

## 🚀 Part 2 — Advanced JUnit

- @BeforeEach✔️
- @AfterEach✔️
- @BeforeAll✔️
- @AfterAll✔️
- @ParameterizedTest✔️
- @CsvSource✔️
- @ValueSource✔️

---

## 🚀 Part 3 — Mockito

- @Mock
- @InjectMocks
- @ExtendWith(MockitoExtension.class)
- when()
- thenReturn()
- verify()
- times()
- never()
- any()
- eq()
- Exception Testing

---

## 🚀 Part 4 — Controller Testing

- @WebMvcTest
- MockMvc
- @MockBean
- jsonPath()
- Response Assertions

---

## 🚀 Part 5 — Repository Testing

- @DataJpaTest
- H2 Database
- Custom Repository Methods

---

## 🚀 Part 6 — Integration Testing

- @SpringBootTest
- @AutoConfigureMockMvc
- Real Database Testing
- End-to-End Flow

---

## 🚀 Part 7 — Advanced Testing

- ArgumentCaptor
- @Spy
- doReturn()
- doThrow()
- doNothing()
- verifyNoInteractions()
- verifyNoMoreInteractions()
- JaCoCo Code Coverage
  
---

# 📌 Topics Covered

- ✔ Unit Testing
- ✔ Mockito
- ✔ Mocking
- ✔ Dependency Injection
- ✔ Repository Mocking
- ✔ Exception Testing
- ✔ Controller Testing
- ✔ Integration Testing
- ✔ Code Coverage
- ✔ Best Practices

---

# 💡 Purpose

This repository is created to:

- Learn Spring Boot Testing from scratch.
- Build interview-ready testing skills.
- Understand JUnit 5 and Mockito in depth.
- Practice real-world testing scenarios.
- Serve as a quick revision guide before interviews.

---

# ⭐ If you find this repository helpful

Please consider giving it a **Star ⭐**.

It motivates me to keep improving this project and adding more testing examples.

---

## The Biggest Doubt

**Q.** If we mock the Service/Repository response, then the real logic isn't running. So what are we actually testing?

**A.** We are **not testing the whole application**. We are testing **only one class (unit)** at a time.

The goal is to verify:

- Does it behave correctly for valid input?
- Does it handle missing data correctly?
- Does it throw the expected exception?
- Does every `if`, `else`, and validation work correctly?

---

# What is Mocking?

A **mock** is a fake object that replaces a real dependency.

Example:

```java
@Mock
private UserRepository repository;
```

This is **not** the real repository.

---

# What is Stubbing?

Stubbing means telling a mock what to return.

```java
when(repository.findById(1L))
        .thenReturn(Optional.of(user));
```

Meaning:

> "If `findById(1L)` is called, return `Optional.of(user)`."

The repository code **does not execute**.

---

# Why Mock?

Suppose the Service has three possible cases:

### Case 1

Repository returns a User.

Expected:

```text
Return UserResponse
```

### Case 2

Repository returns empty.

Expected:

```text
Throw UserNotFoundException
```

### Case 3

Repository returns an inactive User.

Expected:

```text
Throw UserInactiveException
```

Without mocking, creating these situations using a real database is slow and difficult.

With mocking, we simply control what the repository returns and verify whether the Service behaves correctly.

---

# What Actually Runs?

## Controller Test

Real:

```
Controller
```

Mock:

```
Service
```

Question:

> If the Service returns this response, does the Controller return the correct HTTP response?

---

## Service Test

Real:

```
Service
```

Mock:

```
Repository
```

Question:

> If the Repository returns this data, does the Service apply the business logic correctly?

The **Service logic runs**.

Only the Repository is fake.

---

## Repository Test

Real:

```
Repository
```

Real:

```
Database (H2/Test DB)
```

Question:

> Is my database query working correctly?

No mocking is used here.

---

# Why does `thenReturn()` use the Repository's return type?

Example:

```java
Optional<User> findById(Long id);
```

Then

```java
when(repository.findById(1L))
        .thenReturn(Optional.of(user));
```

must return

```java
Optional<User>
```

because that's what the repository method returns.

But

```java
UserResponse response = service.getUser(1L);
```

uses `UserResponse` because that's the **Service's return type**.

---

# Standard Unit Test Pattern

## 1. Arrange

Create input and stub dependencies.

```java
Request request = new Request(...);

when(repository.findById(...))
        .thenReturn(...);
```

---

## 2. Act

Call the method being tested.

```java
Response response = service.process(request);
```

---

## 3. Assert

Verify the expected result.

```java
assertEquals(...);
```

or

```java
assertThrows(...);
```

or

```java
verify(repository).findById(...);
```

---

# Which Layer is Mocked?

| Testing | Real | Mock |
|---------|------|------|
| Controller Test | Controller | Service |
| Service Test | Service | Repository |
| Repository Test | Repository | Nothing |

---

# Golden Rule

> **Mock the layer below the one you're testing.**

- Testing Controller → Mock Service
- Testing Service → Mock Repository
- Testing Repository → Use a Test Database

---

# Final Takeaway

A Unit Test is **not** proving that the whole application works.

It is proving that **one class behaves correctly for every possible scenario** by controlling the responses of its dependencies.

# Let's understand the (AAA) more clearly:

## 1. Arrange (Prepare)

In this step, we prepare everything required for the test.

- Create the input that will be passed to the method.
- Create the objects that the mocked dependency should return.
- Stub the dependency using `when(...).thenReturn(...)`.

Example:

```java
UserRequest request = new UserRequest(1L);

User user = new User(1L, "John");

when(repository.findById(1L))
        .thenReturn(Optional.of(user));
```

**Think of it as:**

> "I have prepared the input and I have already decided what response the mocked dependency should return."

---

## 2. Act

Call the method being tested.

```java
UserResponse response = service.getUser(request);
```

This is where the **actual logic of the class under test executes**.

---

## 3. Assert

Verify whether the result is what you expected.

```java
assertEquals("John", response.getName());
```

or

```java
assertThrows(UserNotFoundException.class,
        () -> service.getUser(request));
```

or

```java
verify(repository).findById(1L);
```