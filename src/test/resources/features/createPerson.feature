Feature: Person Functionalities

  Rule: Creation of person with required fields

  Scenario: Creation of a new person
    Given person with all required fields
      | id | firstName | lastName | email              | phoneNumber | date      |
      | 1  | desire    | NDJOG    | ndjogdesire@gmail  | 690134110   | 25/08/1997|
      | 2  | Diland    | ETUBA    | etuba@gmail.com    | 690134110   | 25/08/1992|
    When created and return persondto
    Then check if created person with required field


