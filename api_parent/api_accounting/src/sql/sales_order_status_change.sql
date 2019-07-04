-- ********************************************************************************
-- Common sales order status history change procedure
-- ********************************************************************************
-- Look for the sales order id(s) that are effected in the sales invoice table
select * from sales_invoice order by xact_id desc

-- Look for the sales order status history id's that will need to be deleted 
-- from the sales order status history table
select * from sales_order_status_hist where so_id in (so_id)

-- Delete latest sales order status history entry pertaining to the target sales order
delete from sales_order_status_hist where so_status_hist_id = (so_status_hist_id)

-- Update the previous sales order status history entry pertaining to the target sales 
-- order to be the current status
update sales_order_status_hist set end_date = null where so_status_hist_id = 3471


-- ********************************************************************************
-- If Changing a closed or cancelled sales order back to invoice, do these steps:
-- ********************************************************************************
update sales_order set invoiced = 1 where so_id in (so_id)

-- Look for customer activity id's related to the transaction(s) containing the negative amounts
select * from customer_activity where customer_id = customer_id
-- Delete the negative entries from customer activity tables
delete from customer_activity where customer_actv_id in (2358, 2359)

-- Look for transaction id's related to the sales order containing the negative amounts
select * from xact where xact_subtype_id = 888 order by xact_id desc
-- Delete the negative entries from transaction table
delete from xact where xact_id in (xact_id, xact_id)
-- Change the previous transaction from cancelled to invoiced.
update xact set xact_subtype_id = null where xact_id in (26997, 26998)



-- *****************************************************************************
-- If Changing a closed or invoiced sales order back to quote, do these steps:
-- *****************************************************************************
update sales_order set invoiced = 0 where so_id in (so_id)

-- Look for customer activity id's related to the transaction(s) containing the 
-- invoice and/or cash receipt amounts
select * from customer_activity where customer_id = customer_id
-- Delete the invoiced and/or cash receipt entries from customer activity tables
delete from customer_activity where customer_actv_id in (2358, 2359)

-- Look for transaction id's related to the sales order containing the 
-- invoiced and/or cash receipt amounts
select * from xact order by xact_id desc
-- Delete the invoiced and/or cash receipt entries from transaction table
delete from xact where xact_id in (xact_id, xact_id)

