# User Stories

[Back to ReadMe](/README.md)

As a user, I want an Android app to enter my TDD information so that I can calculate it without consulting crusty old tables.

As a logged-in user I want to see my previous batch calculations.

As a logged-in user, I want to store my TDD measuring equipment so I can easily reference how measurements were made.

As a non-logged-in user, I want to be able to use the tables to calculate totals.

## Feature Tasks

- Guest users can view the table and use it to calculate data.
- Authenticated users can enter their measuring equipment and store it to their account.
- Authenticated users can save batch calculations.
- Users of other apps can access our API as a service.
- With a disclaimer, ensure the user is aware that our application is not guaranteed to be 100% accurate and they should verify their findings independently.

## Acceptance Tests

- Ensure that the batch calculations are correct according to the crusty old PDF by running several tests.
- Provide error messages if data entered does not match valid input parameters.

## Software Requirements

### Product Vision

This app gives users a better way to calculate and store TDD values, a vast improvement over the crusty old PDF and spreadsheet method.

No other viable competition exists that is also up-to-date and as feature-ful.

### Scope

This Android app will provide a means for users to enter and calculate TDD totals for distilled batches.

The user will be able to store their equipment used and previous batch data.

The user will be able to share/export their batch calculations as a file.

This app is not intended for homebrewers.

### MVP

User account with persistent data stored in DynamoDB.

TDD data is available and functional for calculations.

### Stretch Goals

Enable multiple access filters. Example: Supervisor has admin access to all batches grouped by DSP number. Employee has read-only access to batches grouped by DSP number.

Enable paid feature for API use.

### Functional Requirements

- Guest user can access chart data and submit/view calculated input.
- Authenticated user can save equipment and previous batch data to their account.
- Authenticated user can export/print batch data.

## Data Flow

Main Activity --> User is presented with option 1: login/signup
option 2: TDD form for guest-level data entry

TDD Activity --> User can input data and select Submit.

Dashboard --> Authenticated user can input and view saved equipment.
Authenticated user can view previous saved batches.
Authenticated user can navigate to enter TDD data (which will be saved to their account).
User can also sign out (if desired).

Batch Detail Activity --> Authenticated user can print/export the data.

### Non-Functional Requirements

1. User identity is managed with AWS Cognito
2. User data is stored by DynamoDB
3. Application is testable for accuracy of calculation using JUnit4.
4. Saved data is testable for consistency with that retrieved from the form using JUnit4.

[Back to ReadMe](/README.md)
