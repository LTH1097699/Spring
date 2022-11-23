package com.springbook.contanst;

public class Constant {	
	//order
	public static final int PENDING_STATUS = 0;
	public static final int COMPLETE_STATUS = 1;
	public static final int CANCEL_STATUS = 2;
	public static final int PROCESSING_STATUS = 3;	
//	public static final int BILL_STATUS = 4;
	public static final int SHIP_STATUS = 5;
	public static final int REFUND_STATUS = 6;

	//display on main web
	public static final short DISPLAY_STATUS  = 1;
	public static final short HIDE_STATUS = 0;
	
	
	public static final short PAYMENT_CASH_METHOD  = 0;
	public static final short PAYMENT_PAYPAL_METHOD = 1;
	
	public static final short SHIPMENT_COD_METHOD  = 0;
	public static final short SHIPMENT_AT_STORE_METHOD = 1;
	
	
	//search book
	public static final short ONLY_NAME =1;
	public static final short ONLY_CATEGORY =2;
	public static final short ONLY_AUTHOR =3;
	public static final short ONLY_PUBLISHER =4;
//	public static final short ONLY_NAME_AND_CATEGORY =5;
//	public static final short ONLY_NAME_AND_AUTHOR =6;
//	public static final short ONLY_NAME_AND_PUBLISHER =7;
//	public static final short ONLY_NAME_AND_CATEGORY_AND_AUTHOR =8;
//	public static final short ONLY_NAME_AND_PUBLISHER_AND_AUTHOR =9;
//	public static final short ONLY_NAME_AND_PUBLISHER_AND_CATEGORY =10;
	public static final short ONLY_CATEGORY_AND_PUBLISHER =11;
	public static final short ONLY_CATEGORY_AND_AUTHOR =12;
	public static final short ONLY_CATEGORY_AND_AUTHOR_AND_PUBLISHER =13;
	public static final short ONLY_AUTHOR_AND_PUBLISHER =14;
	public static final short ALL_SEARCH =15;
}
