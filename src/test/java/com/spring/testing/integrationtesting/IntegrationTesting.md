Perfect. 🚀 Welcome to **Part 6: Integration Testing with Spring Boot**.

According to our roadmap, Part 6 covers:

- ✅ `@SpringBootTest`
- ✅ `@AutoConfigureMockMvc`
- ✅ Testing the real application flow
- ✅ Difference between Unit Test and Integration Test in practice
- ✅ When to use mocks vs real Spring beans

---

# Part 6 — Integration Testing

Until now:

- **Part 1:** JUnit Basics
- **Part 2:** Advanced JUnit
- **Part 3:** Mockito (Unit Testing)
- **Part 4:** Controller Testing
- **Part 5:** Repository Testing (`@DataJpaTest`)

Now we connect everything together.

---

# What is Integration Testing?

Imagine your application:

```
Client
    │
    ▼
Controller
    │
    ▼
Service
    │
    ▼
Repository
    │
    ▼
Database
```

In **Unit Testing** we tested each layer separately.

Example:

``` Service │ (Mock Repository) ```

Only Service was real.

---

In **Integration Testing**

```
Controller
    │
    ▼
Service
    │
    ▼
Repository
    │
    ▼
H2 Database
```

Everything is real.

No mocked Service.

No mocked Repository.

Spring creates the complete application.

---

# Real World Example

Suppose we have

```text 
POST /students

↓

StudentController

↓

StudentService

↓

StudentRepository

↓

Database ```

Integration Test checks

> Does the whole request successfully travel through every layer?

instead of

> Does only Service work?

---

# Unit Test vs Integration Test

| Unit Test        | Integration Test                |
| ---------------- | ------------------------------- |
| Tests one class  | Tests multiple classes together |
| Uses Mockito     | Uses Spring Context             |
| Fast             | Slower                          |
| Fake Repository  | Real Repository                 |
| No database      | Real H2/Test Database           |
| Finds logic bugs | Finds wiring/configuration bugs |

---

# Why do we need Integration Tests?

Imagine

``` Service Unit Test ✔

Repository Test ✔

Controller Test ✔ ```

Still application crashes.

Why?

Maybe

``` @Autowired ```

failed.

Maybe Bean not created.

Maybe Mapping wrong.

Maybe Validation wrong.

Maybe Jackson serialization failed.

Maybe Transaction issue.

Unit Tests cannot detect these.

Integration Tests can.

---

# Main Annotation

```java
@SpringBootTest 
```

This tells Spring

> Start the complete Spring Boot application exactly like production (except using the test environment).

Spring loads

- Controller
- Service
- Repository
- Configuration
- Beans
- Security (unless disabled)
- Database configuration

Everything.

---

# What does `@SpringBootTest` actually do?

Think of pressing the **Run** button on your application.

``` ### Run Application

↓

### Spring Boot Starts

↓

### Creates Beans

↓

### Dependency Injection

↓

### Application Ready

```

`@SpringBootTest` does almost the same thing before your test runs.

---

# Why is it slower?

Because Spring creates

``` **100**+

Beans

Configurations

Repositories

Controllers

Services ```

instead of just one class.

---

# Example

```java @SpringBootTest class StudentApplicationTests {

    @Autowired
    StudentService service;

} ```

No mocks.

Real Service.

---

# What if Service depends on Repository?

Spring automatically injects

``` ### Real Repository ```

instead of

``` ### Mock Repository ```

Exactly like the running application.

---

# AutoConfigureMockMvc

Another important annotation

```java @AutoConfigureMockMvc ```

It creates

``` MockMvc ```

for the complete application.

So instead of calling controller methods directly

``` controller.saveStudent() ```

we send a real **HTTP** request.

``` **POST**

↓

DispatcherServlet

↓

Controller

↓

Service

↓

Repository

↓

Database ```

Almost identical to production, but without starting an actual web server.

---

# Typical Integration Test Setup

```java @SpringBootTest @AutoConfigureMockMvc class StudentIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

} ```

---

# What We'll Cover Next

In the next steps of Part 6, we'll build a complete integration test where:

- `**POST** /students` saves data into an H2 database.
- `**GET** /students/{id}` retrieves the saved student.
- We'll verify the full flow: **HTTP** request → Controller → Service → Repository → Database → **HTTP** response.

This is the style of integration testing commonly expected from Spring Boot developers and complements the unit, controller, and repository testing you've already learned.