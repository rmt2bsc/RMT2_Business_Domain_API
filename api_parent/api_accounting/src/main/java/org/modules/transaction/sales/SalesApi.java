package org.modules.transaction.sales;

import java.util.List;

import org.dto.SalesInvoiceDto;
import org.dto.SalesOrderDto;
import org.dto.SalesOrderItemDto;
import org.dto.SalesOrderStatusDto;
import org.dto.SalesOrderStatusHistDto;
import org.dto.XactDto;
import org.modules.transaction.XactApi;

/**
 * An API interface form managing sales related transactions.
 * 
 * Functional examples would be the creation and management of sales orders,
 * sales invoices, and cash receipts. Also provides various methods to create
 * and maintain historical occurrences of sales related transactions.
 * 
 * @author Roy Terrell
 * 
 */
public interface SalesApi extends XactApi {

    /**
     * Retrieves a sales order by sales order id.
     * 
     * @param salesOrderId
     *            the id of the sales order
     * @return an instance of {@link SalesOrderDto}
     * @throws SalesApiException
     */
    SalesOrderDto getSalesOrderById(int salesOrderId) throws SalesApiException;

    /**
     * Retrieves a sales order invoice by sales order id.
     * 
     * @param salesOrderId
     *            the id of the sales order
     * @return an instance of {@link SalesInvoiceDto}
     * @throws SalesApiException
     */
    SalesInvoiceDto getSalesOrderInvoiceBySalesOrder(int salesOrderId)
            throws SalesApiException;

    /**
     * Retrieves a sales order status by sales order status id.
     * 
     * @param statusId
     *            the id of the sales order status
     * @return an instance of {@link SalesOrderStatusDto}
     * @throws SalesApiException
     */
    SalesOrderStatusDto getSalesOrderStatusById(int statusId)
            throws SalesApiException;

    /**
     * Retrieves the current status of a sales order.
     * 
     * @param salesOrderId
     *            the id of the sales order
     * @return an instance of {@link SalesOrderStatusHistDto}
     * @throws SalesApiException
     */
    SalesOrderStatusHistDto getCurrentSalesOrderStatus(int salesOrderId)
            throws SalesApiException;

    /**
     * Generates an invoice number.
     * 
     * @param salesOrder
     * @return Invoice number
     * @throws SalesApiException
     */
    String createInvoiceNumber(SalesOrderDto salesOrder)
            throws SalesApiException;

    // /**
    // * Calculates the sales order total at retail.
    // *
    // * @param salesOrderId
    // * @return the sales order total amount
    // * @throws SalesApiException
    // */
    // double getSalesOrderTotal(int salesOrderId) throws SalesApiException;

    // /**
    // * Verifies whether or not a sales order can change its status to
    // * <i>newStatusId</i>.
    // *
    // * @param soId
    // * The id of sales order
    // * @param newStatusId
    // * The id of the status to apply to the sales order
    // * @return {@link SalesOrderStatusHistDto} object representing the current
    // * sales order status before any change.
    // * @throws SalesApiException
    // * <i>salesOrderId</i> is not a valid sales order,
    // * <i>newStatusId</i> is not a valid sales order status, or
    // * moving the sales order status to <i>newStatusId</i> would
    // * violate business rules.
    // */
    // SalesOrderStatusHistDto evaluateSalesOrderStatusChange(int salesOrderId,
    // int newStatusId) throws SalesApiException;

    /**
     * Creates a new or updates and existing sales order.
     * <p>
     * In order for the sales order to be persisted (created or modified)
     * successfully, the sales order must pass all validations as mandated by
     * the method, {@link SalesApi#validate(SalesOrderDto, int, List)}. Once the
     * <i>customerId</i> is deemed valid, the sales order is assoicated with the
     * customer by assinging <i>customerId</i> to <i>order</i>.
     * 
     * @param order
     *            an instance of {@link SalesOrderDto}.
     * @param customerId
     *            the unique identifier of the customer.
     * @param items
     *            a List of {@link SalesOrderItemDto} instances.
     * @return the id of the sales order created or zero when the sales order is
     *         updated.
     * @throws SalesApiException
     */
    int updateSalesOrder(SalesOrderDto order, int customerId,
            List<SalesOrderItemDto> items) throws SalesApiException;

    /**
     * Updates the status of one or more invoiced sales orders to "Closed" when
     * a payment is received.
     * <p>
     * The total amount of selected invoices must not exceed the amount of
     * payment received for the account.
     * 
     * @param order
     *            a List of orders covering the payment.
     * @param xact
     *            an isntance of {@link XactDto} representing the payment
     * @return the total number of orders processed.
     * @throws SalesApiException
     */
    int updateSalesOrderPaymentStatus(List<SalesOrderDto> orders, XactDto xact)
            throws SalesApiException;

    /**
     * Creates a sales invoice from a selected sales order.
     * 
     * @param order
     *            an instance of {@link SalesOrderDto}
     * @param items
     *            an instance of {@link SalesOrderItemDto}
     * @param receivePayment
     *            indicates whether or not a full payment transaction should be
     *            performed.
     * @return invoice id
     * @throws SalesApiException
     */
    int invoiceSalesOrder(SalesOrderDto order, List<SalesOrderItemDto> items,
            boolean receivePayment) throws SalesApiException;

    /**
     * Cancels a sales order.
     * 
     * @param salesOrderId
     *            The sales order id
     * @return the transaction id of the sales order cancellation.
     * @throws SalesApiException
     */
    int cancelSalesOrder(int salesOrderId) throws SalesApiException;

    /**
     * 
     * @param salesOrderId
     * @return
     * @throws SalesApiException
     */
    int refundSalesOrder(int salesOrderId) throws SalesApiException;

    /**
     * Deletes a sales order including its items.
     * <p>
     * The sales order must be currently in quote status in order to be deleted.
     * 
     * @param salesOrderId
     *            the id of the target sales order.
     * @return totall number of sales orders deleted
     * @throws SalesApiException
     */
    int deleteSalesOrder(int salesOrderId) throws SalesApiException;

    /**
     * Validte sales order.
     * <p>
     * The following rules must be met in order for a sales order to be
     * considered valid:
     * <ul>
     * <li><i>order</i> and <i>items</i> cannot be null.</li>
     * <li>The <i>customerId</i> property of <i>order</i> must be greater than
     * zero.</li>
     * <li>The customer must exist in the system.</li>
     * <li>There must be at least one sales order item contained in
     * <i>items</i>.</li>
     * <li>The item id property of each sales order item contained in
     * <i>items</i> must be greater than zero.</li>
     * <li>Each sales order item in <i>items</i> must exist in inventory either
     * as a service (soft) item type or as type merchandise.</li>
     * <li>The base sales order total must equal the sum of ssales order item
     * amounts.</li>
     * </ul>
     * 
     * @param order
     *            an instance of {@link SalesOrderDto}.
     * @param items
     *            a List of {@link SalesOrderItemDto} instances.
     * @throws SalesApiException
     *             when any one of the specified validations are not met.
     */
    void validate(SalesOrderDto order, List<SalesOrderItemDto> items)
            throws SalesApiException;

}