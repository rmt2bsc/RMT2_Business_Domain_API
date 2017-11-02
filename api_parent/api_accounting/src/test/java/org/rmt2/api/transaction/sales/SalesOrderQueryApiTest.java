package org.rmt2.api.transaction.sales;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.util.List;

import org.dao.mapping.orm.rmt2.SalesOrder;
import org.dao.mapping.orm.rmt2.SalesOrderItems;
import org.dao.mapping.orm.rmt2.VwSalesorderItemsBySalesorder;
import org.dao.transaction.sales.SalesOrderDaoException;
import org.dto.SalesOrderDto;
import org.dto.SalesOrderItemDto;
import org.dto.adapter.orm.transaction.sales.Rmt2SalesOrderDtoFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modules.transaction.sales.SalesApi;
import org.modules.transaction.sales.SalesApiException;
import org.modules.transaction.sales.SalesApiFactory;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.InvalidDataException;
import com.api.persistence.AbstractDaoClientImpl;
import com.api.persistence.DatabaseException;
import com.api.persistence.db.orm.Rmt2OrmClientFactory;

/**
 * Tests sales order / sales invoice transaction query Api.
 * 
 * @author rterrell
 * 
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ AbstractDaoClientImpl.class, Rmt2OrmClientFactory.class, ResultSet.class })
public class SalesOrderQueryApiTest extends SalesOrderApiTestData {

    private static final int TEST_SALES_ORDER_ID = 1000;
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        super.tearDown();
        return;
    }

    @Test
    public void testFetchAll_SalesOrders() {
        // Mock method call to get multiple sales orders
        SalesOrder so = new SalesOrder();
        try {
            when(this.mockPersistenceClient.retrieveList(eq(so)))
                    .thenReturn(this.mockSalesOrderAllResponse);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("All Sales order fetch test case setup failed");
        }

        // Perform test
        SalesApiFactory f = new SalesApiFactory();
        SalesApi api = f.createApi(mockDaoClient);
        SalesOrderDto criteria = Rmt2SalesOrderDtoFactory.createSalesOrderInstance(null);
        List<SalesOrderDto> results = null;
        try {
            results = api.getSalesOrder(criteria);
        } catch (SalesApiException e) {
            e.printStackTrace();
            Assert.fail("Test failed due to unexpected exception thrown");
        }
        Assert.assertNotNull(results);
        Assert.assertEquals(5, results.size());
    }

    @Test
    public void testFetchAll_Null_Criteria() {
        // Perform test
        SalesApiFactory f = new SalesApiFactory();
        SalesApi api = f.createApi(mockDaoClient);
        SalesOrderDto criteria = null;
        try {
            api.getSalesOrder(criteria);
            Assert.fail("Test failed due to exception was expected");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e instanceof InvalidDataException);
        }
    }
    
    @Test
    public void testFetchAll_Db_Exception() {
        // Mock method call to force databae exception
        SalesOrder so = new SalesOrder();
        try {
            when(this.mockPersistenceClient.retrieveList(eq(so)))
                    .thenThrow(DatabaseException.class);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("All Sales order fetch test case setup failed");
        }
        
        // Perform test
        SalesApiFactory f = new SalesApiFactory();
        SalesApi api = f.createApi(mockDaoClient);
        SalesOrderDto criteria = Rmt2SalesOrderDtoFactory.createSalesOrderInstance(null);
        try {
            api.getSalesOrder(criteria);
            Assert.fail("Test failed due to exception was expected");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e instanceof SalesApiException);
            Assert.assertTrue(e.getCause() instanceof SalesOrderDaoException);
            Assert.assertTrue(e.getCause().getCause() instanceof DatabaseException);
        }
    }
    
    @Test
    public void testFetchSingle_SalesOrders() {
        SalesOrder so = new SalesOrder();
        so.setSoId(TEST_SALES_ORDER_ID);
        try {
            when(this.mockPersistenceClient.retrieveList(eq(so)))
            .thenReturn(this.mockSalesOrderSingleResponse);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(
                    "Single slaes order fetch test case setup failed");
        }

        // Perform test
        SalesApiFactory f = new SalesApiFactory();
        SalesApi api = f.createApi(mockDaoClient);
        SalesOrderDto results = null;
        try {
            results = api.getSalesOrder(TEST_SALES_ORDER_ID);
        } catch (SalesApiException e) {
            e.printStackTrace();
            Assert.fail("Test failed due to unexpected exception thrown");
        }
        Assert.assertNotNull(results);
    }

    @Test
    public void testFetchSingle_Null_SalesOrderId() {
        // Perform test
        SalesApiFactory f = new SalesApiFactory();
        SalesApi api = f.createApi(mockDaoClient);
        Integer criteria = null;
        try {
            api.getSalesOrder(criteria);
            Assert.fail("Test failed due to exception was expected");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e instanceof InvalidDataException);
        }
    }
    
    @Test
    public void testFetchSingle_Zero_SalesOrderId() {
        // Perform test
        SalesApiFactory f = new SalesApiFactory();
        SalesApi api = f.createApi(mockDaoClient);
        Integer criteria = 0;
        try {
            api.getSalesOrder(criteria);
            Assert.fail("Test failed due to exception was expected");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e instanceof InvalidDataException);
        }
    }
    
    @Test
    public void testFetchSingle_Too_Many_Rows_Return() {
        SalesOrder so = new SalesOrder();
        so.setSoId(TEST_SALES_ORDER_ID);
        try {
            when(this.mockPersistenceClient.retrieveList(eq(so)))
            .thenReturn(this.mockSalesOrderAllResponse);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Single slaes order fetch test case setup failed");
        }
        
        // Perform test
        SalesApiFactory f = new SalesApiFactory();
        SalesApi api = f.createApi(mockDaoClient);
        try {
            api.getSalesOrder(TEST_SALES_ORDER_ID);
            Assert.fail("Test failed due to exception was expected");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e instanceof SalesApiException);
        }
    }
    
    @Test
    public void testFetchSingle_Db_Exception() {
        // Mock method call to force databae exception
        SalesOrder so = new SalesOrder();
        so.setSoId(TEST_SALES_ORDER_ID);
        try {
            when(this.mockPersistenceClient.retrieveList(eq(so)))
                    .thenThrow(DatabaseException.class);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("All Sales order fetch test case setup failed");
        }
        
        // Perform test
        SalesApiFactory f = new SalesApiFactory();
        SalesApi api = f.createApi(mockDaoClient);
        try {
            api.getSalesOrder(TEST_SALES_ORDER_ID);
            Assert.fail("Test failed due to exception was expected");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e instanceof SalesApiException);
            Assert.assertTrue(e.getCause() instanceof SalesApiException);
            Assert.assertTrue(e.getCause().getCause() instanceof SalesOrderDaoException);
            Assert.assertTrue(e.getCause().getCause().getCause() instanceof DatabaseException);
        }
    }
    
    @Test
    public void testFetchAll_SalesOrderItems() {
        SalesOrderItems so = new SalesOrderItems();
        so.setSoId(TEST_SALES_ORDER_ID);
        try {
            when(this.mockPersistenceClient.retrieveList(eq(so))).thenReturn(this.mockSalesOrderItemsAllResponse);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Single sales order items fetch test case setup failed");
        }

        // Perform test
        SalesApiFactory f = new SalesApiFactory();
        SalesApi api = f.createApi(mockDaoClient);
        List<SalesOrderItemDto> results = null;
        try {
            results = api.getLineItems(TEST_SALES_ORDER_ID);
        } catch (SalesApiException e) {
            e.printStackTrace();
            Assert.fail("Test failed due to unexpected exception thrown");
        }
        Assert.assertNotNull(results);
        Assert.assertEquals(5, results.size());
    }
    
    @Test
    public void testFetchAll_SalesOrderItems_Null_SalesOrderId() {
        // Perform test
        SalesApiFactory f = new SalesApiFactory();
        SalesApi api = f.createApi(mockDaoClient);
        try {
            api.getLineItems(null);
            Assert.fail("Test failed due to exception was expected");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e instanceof InvalidDataException);
        }
    }
    
    @Test
    public void testFetchAll_SalesOrderItems_Zero_SalesOrderId() {
        // Perform test
        SalesApiFactory f = new SalesApiFactory();
        SalesApi api = f.createApi(mockDaoClient);
        try {
            api.getLineItems(0);
            Assert.fail("Test failed due to exception was expected");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e instanceof InvalidDataException);
        }
    }
    
    @Test
    public void testFetchAll_SalesOrderItems_Db_Exception() {
        SalesOrderItems so = new SalesOrderItems();
        so.setSoId(TEST_SALES_ORDER_ID);
        try {
            when(this.mockPersistenceClient.retrieveList(eq(so))).thenThrow(DatabaseException.class);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Single sales order items fetch test case setup failed");
        }

        // Perform test
        SalesApiFactory f = new SalesApiFactory();
        SalesApi api = f.createApi(mockDaoClient);
        try {
            api.getLineItems(TEST_SALES_ORDER_ID);
            Assert.fail("Test failed due to exception was expected");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e instanceof SalesApiException);
            Assert.assertTrue(e.getCause() instanceof SalesOrderDaoException);
            Assert.assertTrue(e.getCause().getCause() instanceof DatabaseException);
        }
    }
    
    @Test
    public void testFetchAll_ExtSalesOrderItems() {
        VwSalesorderItemsBySalesorder so = new VwSalesorderItemsBySalesorder();
        so.setSoId(TEST_SALES_ORDER_ID);
        try {
            when(this.mockPersistenceClient.retrieveList(eq(so))).thenReturn(this.mockVwSalesorderItemsBySalesorderAllResponse);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Single Extended sales order items fetch test case setup failed");
        }

        // Perform test
        SalesApiFactory f = new SalesApiFactory();
        SalesApi api = f.createApi(mockDaoClient);
        List<SalesOrderItemDto> results = null;
        try {
            results = api.getLineItemsExt(TEST_SALES_ORDER_ID);
        } catch (SalesApiException e) {
            e.printStackTrace();
            Assert.fail("Test failed due to unexpected exception thrown");
        }
        Assert.assertNotNull(results);
        Assert.assertEquals(5, results.size());
    }
    
    @Test
    public void testFetchAll_ExtSalesOrderItems_Null_SalesOrderId() {
        // Perform test
        SalesApiFactory f = new SalesApiFactory();
        SalesApi api = f.createApi(mockDaoClient);
        try {
            api.getLineItemsExt(null);
            Assert.fail("Test failed due to exception was expected");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e instanceof InvalidDataException);
        }
    }
    
    @Test
    public void testFetchAll_ExtSalesOrderItems_Zero_SalesOrderId() {
        // Perform test
        SalesApiFactory f = new SalesApiFactory();
        SalesApi api = f.createApi(mockDaoClient);
        try {
            api.getLineItemsExt(0);
            Assert.fail("Test failed due to exception was expected");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e instanceof InvalidDataException);
        }
    }
    
    @Test
    public void testFetchAll_ExtSalesOrderItems_Db_Exception() {
        VwSalesorderItemsBySalesorder so = new VwSalesorderItemsBySalesorder();
        so.setSoId(TEST_SALES_ORDER_ID);
        try {
            when(this.mockPersistenceClient.retrieveList(eq(so))).thenThrow(DatabaseException.class);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Single Extended sales order items fetch test case setup failed");
        }

        // Perform test
        SalesApiFactory f = new SalesApiFactory();
        SalesApi api = f.createApi(mockDaoClient);
        try {
            api.getLineItemsExt(TEST_SALES_ORDER_ID);
            Assert.fail("Test failed due to exception was expected");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e instanceof SalesApiException);
            Assert.assertTrue(e.getCause() instanceof SalesOrderDaoException);
            Assert.assertTrue(e.getCause().getCause() instanceof DatabaseException);
        }
    }
    
    @Test
    public void testFetchSingle_SalesInvoice() {
        Assert.fail("Test Needs Implementation");
    }
    
    @Test
    public void testFetchSingle_SalesInvoice_Null_SalesOrderId() {
        Assert.fail("Test Needs Implementation");
    }
    
    @Test
    public void testFetchSingle_SalesInvoice_Zero_SalesOrderId() {
        Assert.fail("Test Needs Implementation");
    }
    
    @Test
    public void testFetchSingle_SalesInvoice_Db_Exception() {
        Assert.fail("Test Needs Implementation");
    }
    
    @Test
    public void testFetch_CurrentStatus() {
        Assert.fail("Test Needs Implementation");
    }
    
    @Test
    public void testFetch_CurrentStatus_Null_SalesOrderId() {
        Assert.fail("Test Needs Implementation");
    }
    
    @Test
    public void testFetch_CurrentStatus_Zero_SalesOrderId() {
        Assert.fail("Test Needs Implementation");
    }
    
    @Test
    public void testFetch_CurrentStatus_Db_Exception() {
        Assert.fail("Test Needs Implementation");
    }
    
    @Test
    public void testFetch_CurrentStatus_EmptyResults_Exception() {
        Assert.fail("Test Needs Implementation");
    }
    
    @Test
    public void testFetch_SalesOrderStatus() {
        Assert.fail("Test Needs Implementation");
    }
    
    @Test
    public void testFetch_SalesOrderStatus_Null_StatusId() {
        Assert.fail("Test Needs Implementation");
    }
    
    @Test
    public void testFetch_SalesOrderStatus_Zero_StatusId() {
        Assert.fail("Test Needs Implementation");
    }
    
    @Test
    public void testFetch_SalesOrderStatus_Too_Many_Rows_Return() {
        Assert.fail("Test Needs Implementation");
    }
}