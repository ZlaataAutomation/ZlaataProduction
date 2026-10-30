Feature: GuestLogin

  #===========================================================================
  #Test case ID :: TC_UI_Zlaata_GC_01
  #===========================================================================
  #ScenarioDescription : Complete Adding Gift card to cart Both before login & afetr login
  #Expected: Gift card  sanity 
  #============================================================================
  
 	
  @Sanity
	@TC_UI_Zlaata_GL_01
Scenario Outline: TC_UI_Zlaata_GL_01 | Verify that the Guest Login button is available in the Login popup when clicking Place Order on the Checkout page | "<TD_ID>"

Given user is on the Checkout page and clicks on the Place Order button  

Examples:
| TD_ID |
| TD_UI_Zlaata_GL_01|

@guest
@Sanity

@TC_UI_Zlaata_GL_02

Scenario Outline: TC_UI_Zlaata_GL_02 | Verify that the phone number field is not auto-populated when the user accesses the Address page through Guest Login | "<TD_ID>"

Given the user is on the Address page through Guest Login

Examples:
| TD_ID               |
| TD_UI_Zlaata_GL_02   |


@Sanity
@guest
@TC_UI_Zlaata_GL_03

Scenario Outline: TC_UI_Zlaata_GL_03 | Verify that clicking the Save Address button without entering mandatory details on the Guest Address page displays validation messages | "<TD_ID>"

Given the user is on the Address page through Guest Login
When the user clicks the Save Address button without entering the mandatory details

Examples:
| TD_ID               |
| TD_UI_Zlaata_GL_03   |

@Sanity
@guest
@TC_UI_Zlaata_GL_04

Scenario Outline: TC_UI_Zlaata_GL_04 | Verify that the Place Order button remains disabled on the Guest Address page when the mandatory address details are not entered | "<TD_ID>"

Given the user is on the Address page through Guest Login
Then the Place Order button should remain disabled when the mandatory address details are empty

Examples:
| TD_ID               |
| TD_UI_Zlaata_GL_04   |


@guest
@Sanity

@TC_UI_Zlaata_GL_05

Scenario Outline: TC_UI_Zlaata_GL_05 |Verify that the user can enter valid address details on the Address page through Guest Login and the Estimated Delivery section is displayed| "<TD_ID>"

Given the user is on the Address page through Guest Login
When the user enters valid details in all mandatory address text boxes
Then the Estimated Delivery section should be displayed

Examples:
| TD_ID               |
| TD_UI_Zlaata_GL_05   |

@Sanity
@guest
@TC_UI_Zlaata_GL_06

Scenario Outline: TC_UI_Zlaata_GL_06 | Verify that the user can edit the already entered address through Guest Login | "<TD_ID>"

Given the user is on the Address page through Guest Login
Then the user should be able to edit the already entered address

Examples:

| TD_ID              |
| TD_UI_Zlaata_GL_06 |

