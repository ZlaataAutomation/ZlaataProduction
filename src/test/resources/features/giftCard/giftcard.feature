Feature: This is Adding Gift card to cart Both before login & afetr login  feature

  #===========================================================================
  #Test case ID :: TC_UI_Zlaata_GC_01
  #===========================================================================
  #ScenarioDescription : Complete Adding Gift card to cart Both before login & afetr login
  #Expected: Gift card  sanity 
  #============================================================================
 @Sanity
 @TC_UI_Zlaata_GC_01
Scenario Outline: TC_UI_Zlaata_GC_01 |Verify that the user is able to add a Gift Card to the cart | "<TD_ID>"

    Given user is able to add the Gift Card to the cart

Examples:
    | TD_ID                |
    | TD_UI_Zlaata_GC_01  |