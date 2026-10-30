Feature: Login Popup verification for guest user actions before login





 @BL
  @Sanity
  @TC_UI_Zlaata_BL_1
  Scenario Outline: TC_UI_Zlaata_BL_1 |Verify login popup appears when guest user clicks Profile icon in the Landing Page| "<TD_ID>"

    Given user navigates to the Zlaata  India home page  as a guest user
   
    Examples:
      | TD_ID               |
      | TD_UI_Zlaata_BL_1  |



  @BL
  @Sanity
  @TC_UI_Zlaata_BL_01
  Scenario Outline: TC_UI_Zlaata_BL_01 |Verify login popup appears when guest user clicks Profile icon in header| "<TD_ID>"

    Given user navigates to the Zlaata homepage as a guest user
    When user clicks on the Profile icon in the header
    Then verify that the login popup is displayed successfully

    Examples:
      | TD_ID               |
      | TD_UI_Zlaata_BL_01  |


  @BL
  
  @Sanity
  @TC_UI_Zlaata_BL_02
  Scenario Outline: TC_UI_Zlaata_BL_02 |Verify login popup appears when guest user clicks Wishlist icon from header section| "<TD_ID>"

    Given user navigates to the Zlaata homepage as a guest user
    When user clicks on the Wishlist icon from the top right corner header section
    Then verify that the login popup is displayed successfully

    Examples:
      | TD_ID               |
      | TD_UI_Zlaata_BL_02  |


@Sanity
  @BL
  @TC_UI_Zlaata_BL_03
  Scenario Outline: TC_UI_Zlaata_BL_03 |Verify login popup appears when guest user clicks Wishlist icon on product listing page| "<TD_ID>"

    Given user navigates to the product listing page as a guest user
    When user clicks on the Wishlist icon on the product listing page
    Then verify that the login popup is displayed successfully

    Examples:
      | TD_ID               |
      | TD_UI_Zlaata_BL_03  |


@Sanity
  @BL
  @TC_UI_Zlaata_BL_04
  Scenario Outline: TC_UI_Zlaata_BL_04 |Verify login popup appears when guest user clicks Wishlist icon on product details page| "<TD_ID>"

    Given user navigates to the product details page as a guest user
    When user clicks on the Wishlist icon on the product details page
    Then verify that the login popup is displayed successfully on product details page

    Examples:
      | TD_ID               |
      | TD_UI_Zlaata_BL_04  |

@BL
@Sanity
  @TC_UI_Zlaata_BL_05
  Scenario Outline: TC_UI_Zlaata_BL_05 |Verify login popup appears when guest user clicks Move to Wishlist icon on cart page| "<TD_ID>"

    Given user navigates to the cart page as a guest user
    When user clicks on the Move to Wishlist icon on the cart page
    Then the login popup should be displayed successfully for Move to Wishlist action on cart page

    Examples:
      | TD_ID               |
      | TD_UI_Zlaata_BL_05  |

@BL
@Sanity
  @TC_UI_Zlaata_BL_06
  Scenario Outline: TC_UI_Zlaata_BL_06 |Verify login popup appears when guest user clicks View Coupons on cart page| "<TD_ID>"

    Given user navigates to the cart page as a guest user
    When user clicks on the View Coupons option on the cart page
    Then the login popup should be displayed successfully for View Coupons action on cart page

    Examples:
      | TD_ID               |
      | TD_UI_Zlaata_BL_06  |
@BL
@Sanity

  @TC_UI_Zlaata_BL_07
  Scenario Outline: TC_UI_Zlaata_BL_07 |Verify login popup appears when guest user clicks Apply button in coupon section on cart page| "<TD_ID>"

    Given user navigates to the cart page as a guest user
    When user clicks on the Apply button in the coupon section on the cart page
    Then the login popup should be displayed successfully for Apply Coupon action on cart page

    Examples:
      | TD_ID               |
      | TD_UI_Zlaata_BL_07  |

@BL
@Sanity
  @TC_UI_Zlaata_BL_08
  Scenario Outline: TC_UI_Zlaata_BL_08 |Verify login popup appears when guest user clicks Game icon| "<TD_ID>"

    Given user navigates to the Zlaata homepage as a guest user
    When user clicks on the Game icon
    Then the login popup should be displayed successfully after clicking the Game icon

    Examples:
      | TD_ID               |
      | TD_UI_Zlaata_BL_08  |

@BL
@Sanity
  @TC_UI_Zlaata_BL_09
  Scenario Outline: TC_UI_Zlaata_BL_09 |Verify login popup appears when guest user clicks Threads action buttons| "<TD_ID>"

    Given user navigates to the Threads page as a guest user
    When user clicks on any action button such as Sign Up Now, Enter DOB, Refer Now, or Rate Us
    Then the login popup should be displayed successfully for Threads action

    Examples:
      | TD_ID               |
      | TD_UI_Zlaata_BL_09  |

@BL
@Sanity
  @TC_UI_Zlaata_BL_10
  Scenario Outline: TC_UI_Zlaata_BL_10 |Verify login popup appears when guest user clicks Place Order button on cart page| "<TD_ID>"

    Given user navigates to the cart page as a guest user
    When user clicks on the Place Order button
    Then the login popup should be displayed successfully for Place Order action

    Examples:
      | TD_ID               |
      | TD_UI_Zlaata_BL_10  |

@BL
@Sanity
  @TC_UI_Zlaata_BL_11
  Scenario Outline: TC_UI_Zlaata_BL_11 |Verify validation message appears when guest user clicks Threads on cart page| "<TD_ID>"

    Given user navigates to the cart page as a guest user
    When user clicks on Threads option without logging in
    Then the validation message Please login to apply threads. should be displayed

    Examples:
      | TD_ID               |
      | TD_UI_Zlaata_BL_11  |


     @BL
@Sanity
  @TC_UI_Zlaata_BL_12
  Scenario Outline: TC_UI_Zlaata_BL_12 |Verify that the Login Popup is displayed when the user clicks the Notify Me when Available button on the Product Details Page.| "<TD_ID>"

    Given user navigates to the product details page 
   

    Examples:
      | TD_ID               |
      | TD_UI_Zlaata_BL_12  | 
      