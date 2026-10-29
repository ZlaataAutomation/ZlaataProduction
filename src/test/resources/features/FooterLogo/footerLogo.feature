Feature: Verify that user can able to clik on zlaata logo 

 @Sanity
  @Footer
  @TC_UI_Zlaata_FL_01
  Scenario Outline: TC_UI_Zlaata_FL_01 |Verify that clicking the Zlaata logo in footer on the Zlaata India website redirects the user to the Zlaata India Home page.| "<TD_ID>"
    Given User on the hoame page of zlaata India

    Examples:
      | TD_ID                |
      | TD_UI_Zlaata_FL_01  |
      
      