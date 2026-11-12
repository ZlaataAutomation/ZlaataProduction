Feature: Verify that user on contact us section

@Sanity
 @logo
  @TC_UI_Zlaata_CT_01
  Scenario Outline: TC_UI_Zlaata_CT_01 |Verify that a validation message is displayed when the user clicks the Send Message button without entering the mandatory fields.| "<TD_ID>"
   Given that the user is on the Contact Us page and clicks the Send Message button.

    Examples:
      | TD_ID                |
      | TD_UI_Zlaata_CT_01  |

  @Sanity
 @logo
  @TC_UI_Zlaata_CT_02
  Scenario Outline: TC_UI_Zlaata_CT_02 |Verify that a validation message is displayed when the user enters fewer than the required number of characters in each text box.| "<TD_ID>"
   Given that the user is on the Contact Us page and enters fewer than the required number of characters.

    Examples:
      | TD_ID                |
      | TD_UI_Zlaata_CT_02  |

    
    
  @Sanity
@logo
@TC_UI_Zlaata_CT_03

Scenario Outline: TC_UI_Zlaata_CT_03 | Verify whether the character count decreases when the user enters text in the Queries field | "<TD_ID>"

Given that the user is on the Contact Us page and enters characters in the Queries field to verify whether the character count decreases.

Examples:
| TD_ID               |
| TD_UI_Zlaata_CT_03  |