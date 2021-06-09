# True Proof

## About/summary

True Proof is a beta android app pending approval in the App Store. It offers an intuitive calculator for calculating true proof's required by the US' Alcohol and Tobacco Tax and Trade Bureau to distill spirits. It also offer's a user friendly database that stores information about batches and measurements taken for those batches. Functionality for multiple users to contribute to batches from the same distillery coming soon!

[<img alt="Get it on Google Play" src="https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png" height="80">](https://play.google.com/store/apps/details?id=com.trueproof.trueproof)


Table of Contents |-
------------------|----
[Link to APK](trueproof_v1_0.apk) | -
[User Stories](docs/user-stories.md) | -
[Team Agreement](docs/team-agreement) | -
[Whiteboard](docs/whiteboard.jpg) | -
[Entity Relationship Diagram](docs/entity_relationship.jpg) | -

Team | ROLE/CONTRIBUTION
------------------|----
[Stephen Webber](https://github.com/offgridauthor) | ROLE/CONTRIBUTION
[Edward Hou](https://github.com/wordhou) | ROLE/CONTRIBUTION
[Seamus Brown](https://github.com/shaybrow) | ROLE/CONTRIBUTION
[Cristian Robles](github) | ROLE/CONTRIBUTION
[Barrett Nance](https://github.com/baxance) | ROLE/CONTRIBUTION

## Changelog

Time | Action
-----|-------
5/21/21 | Create whiteboard and project outline
5/24/21 | Generate all user activities, navigation menu, implement all necessary dependencies, implement AWS Amplify with Dynamo DB and cognito
5/25/21 | Users can sign in and sign up (cognito implemented), users can quickly calculate true proofs from the main activity without signing in.
5/26/21 | Date and times correctly display on measurements and batches,
5/27/21 | Batches can be updated with new names, numbers and identifiers, navigation between all points of the app, logout functionality, prevent logged out users from navigating to settings, batch and  measurement pages
6/08/21 | Adjusted and simplified logic for both caculators in the main and take measurement activities since really both use the same code. Adjustment where moving common view logic and view model logic out of the MainActivity and TakeMeasurementActivity into the MeasurementActivity abstract class and the MeasurementViewModel abstract class. It also fixes some small bugs we were having with the measurement taking interface when switching temperatures.
6/09/21 | Creationg of a new module within out project and linked the exist app to the new logic in the build.gradle file.