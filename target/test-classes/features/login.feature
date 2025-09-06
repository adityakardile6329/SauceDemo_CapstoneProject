Feature: Authentication (Login/Logout)

  Scenario: TC001 Login with valid credentials
    Given User is on login page using "chrome"
    When User enters "standard_user" and "secret_sauce"
    Then User should be redirected to inventory page

  Scenario: TC002 Login with locked-out user
    Given User is on login page
    When User enters "locked_out_user" and "secret_sauce"
    Then Error message "Epic sadface: Sorry, this user has been locked out." should be displayed

  Scenario: TC003 Login with problem user
    Given User is on login page
    When User enters "problem_user" and "secret_sauce"
    Then User should be redirected to inventory page

  Scenario: TC004 Login with performance glitch user
    Given User is on login page
    When User enters "performance_glitch_user" and "secret_sauce"
    Then User should be redirected to inventory page

  Scenario: TC005 Login with invalid credentials
    Given User is on login page
    When User enters "wrong_user" and "wrong_pass"
    Then Error message "Epic sadface: Username and password do not match any user in this service" should be displayed

  Scenario: TC006 Login with blank username and password
    Given User is on login page
    When User enters "" and ""
    Then Error message "Epic sadface: Username is required" should be displayed

  Scenario: TC007 Verify error message for failed login
    Given User is on login page
    When User enters "standard_user" and ""
    Then Error message "Epic sadface: Password is required" should be displayed

  Scenario: TC008 Verify logout functionality
    Given User is logged in with "standard_user" and "secret_sauce"
    When User logs out
    Then User should be on login page
