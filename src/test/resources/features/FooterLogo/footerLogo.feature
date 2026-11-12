Feature: Verify that user can able to clik on zlaata logo 

 @Sanity
 @logo
  @TC_UI_Zlaata_FL_01
  Scenario Outline: TC_UI_Zlaata_FL_01 |Verify that clicking the Zlaata logo in footer on the Zlaata India website redirects the user to the Zlaata India Home page.| "<TD_ID>"
    Given User on the hoame page of zlaata India

    Examples:
      | TD_ID                |
      | TD_UI_Zlaata_FL_01  |
      
    @Sanity
  @logo
  @TC_UI_Zlaata_FL_02
  Scenario Outline: TC_UI_Zlaata_FL_02 |Verify that clicking the Zlaata logo in footer on the Boss Lady  website redirects the user to the  Boss lady Home page.| "<TD_ID>"
    Given User on the home page of Boss Lady

    Examples:
      | TD_ID                |
      | TD_UI_Zlaata_FL_02  |
  
  
  
      @Sanity
  @logo
  @TC_UI_Zlaata_FL_03
  Scenario Outline: TC_UI_Zlaata_FL_03 |Verify that In lading page if user clik on zlaata Logo it re-diect ot Same page   | "<TD_ID>"
    Given User on the landing page 

    Examples:
      | TD_ID                |
      | TD_UI_Zlaata_FL_03  |
              