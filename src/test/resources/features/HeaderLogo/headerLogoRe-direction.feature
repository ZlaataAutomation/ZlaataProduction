Feature: Verify that user can able to clik on zlaata logo  in Header Section

 @Sanity
@headLogo
  @TC_UI_Zlaata_HL_01
  Scenario Outline: TC_UI_Zlaata_HL_01 |Verify that clicking the Zlaata logo in the header section of the Zlaata India website redirects the user to the Zlaata India home page.| "<TD_ID>"
    Given User on the hoame page of zlaata India and clik header logo

    Examples:
      | TD_ID                |
      | TD_UI_Zlaata_HL_01  |
      
     @Sanity  
    @headLogo  
    @TC_UI_Zlaata_HL_02
  Scenario Outline: TC_UI_Zlaata_HL_02 |Verify that clicking the Zlaata logo in the header section of the Boss Lady website redirects the user to the Boss Lady home page.| "<TD_ID>"
    Given User on the hoame page of Boss lady and clik header logo

    Examples:
      | TD_ID                |
      | TD_UI_Zlaata_HL_02  |
   
    @Sanity
    @headLogo   
    @TC_UI_Zlaata_HL_03
  Scenario Outline: TC_UI_Zlaata_HL_03 |Verify that clicking the Zlaata logo in the header section of the landing page it re-direct to same page | "<TD_ID>"
    Given User on the landing page  and clik header logo

    Examples:
      | TD_ID                |
      | TD_UI_Zlaata_HL_03  |     