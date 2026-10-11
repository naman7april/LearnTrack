# Design Notes

## Layered design

| Layer | Package | Responsibility |
|-------|---------|----------------|
| UI | `Main` | Show menus, read input, print results. No business rules. |
| Service | `service` | Validation and business rules (e.g. cannot enroll an inactive student). |
| Repository | `repository` | Stores objects in `ArrayList`s; find / save only. |
| Entity | `entity` | Plain data objects with private fields and getters/setters. |
| Support | `util`, `exception`, `constants`, `enums` | Helpers, custom exceptions, fixed values. |

Keeping these separate means a change in one place (for example, swapping the in-memory repository for a file or database later) does not ripple through the rest of the code.

## Why ArrayList instead of an array?

- An array has a **fixed size** chosen up front. Students, courses and enrollments grow while the program runs, so we would have to create a bigger array and copy elements by hand every time it fills up.
- `ArrayList` **resizes itself**, and gives ready-made methods such as `add`, `remove`, `size`, `isEmpty` and `contains`.
- It still gives fast access by index and works with the enhanced `for` loop.
- It is type-safe with generics (`List<Student>`), so the compiler stops us from storing the wrong type.

Repositories also return a **copy** (`new ArrayList<>(list)`) from `findAll()` so callers cannot accidentally change the stored list.

## Where static members are used and why

- **`IdGenerator`** has `private static int` counters and static methods such as `getNextStudentId()`. The counters must be shared by the whole application (one source of unique IDs), not belong to any single object, so `static` is the right fit.
- **`AppConstants`** and **`MenuOptions`** hold `public static final` constants: values that never change and do not need an object.
- **`InputValidator`** has only static helper methods because it keeps no state; calling `InputValidator.isValidEmail(...)` is simpler than creating an object.
- **`Main`** uses static fields for the scanner and services because `main` is static and is the single entry point.

## Where inheritance is used and what we gained

- `Person` is the base class (id, firstName, lastName, email, `getFullName()`, `getDisplayName()`).
- `Student extends Person` and `Trainer extends Person`.
- **Gains:**
  - **Code reuse**: common fields and getters/setters are written once in `Person`.
  - **`super(...)` constructor calls** pass shared data up to the parent.
  - **Method overriding / polymorphism**: `getDisplayName()` returns `"Student: Asha Verma (B1)"` for a `Student` and `"Trainer: ... (expertise)"` for a `Trainer`. Code that holds a `Person` reference automatically gets the right behavior.
  - Easy to add new kinds of people later (for example `Admin`) without touching existing classes.

## Other OOP and Java choices

- **Encapsulation**: all entity fields are `private` with public getters/setters.
- **Constructor overloading**: `Student` has a default constructor, one with email, and one without email (which delegates using `this(...)`). `StudentService.addStudent` is also overloaded (with / without email).
- **Access modifiers**: `public` for the API, `private` for fields and internal helpers (such as `EnrollmentService.updateStatus`).
- **Enum for status**: `EnrollmentStatus` (`ACTIVE`, `COMPLETED`, `CANCELLED`) prevents typos that a plain `String` would allow.
- **Soft delete**: students and courses are deactivated (`active = false`) instead of removed, so past enrollments still make sense. `removeStudent` exists in the service for completeness.
- **Custom exceptions**: `EntityNotFoundException` and `InvalidInputException` carry clear messages. `Main` catches them in each menu loop and prints a friendly message instead of crashing.
- **Clean code**: small methods with descriptive names (`findStudentById`, `markCompleted`, `hasActiveEnrollment`) and one responsibility per class.
