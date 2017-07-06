package org.rmt2.api.inventory;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.dao.mapping.orm.rmt2.VwVendorItems;
import org.dto.VendorItemDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modules.inventory.InventoryApi;
import org.modules.inventory.InventoryApiException;
import org.modules.inventory.InventoryApiFactory;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.api.BaseAccountingDaoTest;
import org.rmt2.dao.AccountingMockDataUtility;

import com.InvalidDataException;
import com.api.persistence.AbstractDaoClientImpl;
import com.api.persistence.db.orm.Rmt2OrmClientFactory;

/**
 * Tests Vendor Items entity query use cases belonging to the Inventory Api.
 * 
 * @author rterrell
 * 
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ AbstractDaoClientImpl.class, Rmt2OrmClientFactory.class,
        ResultSet.class })
public class VendorItemApiQueryTest extends BaseAccountingDaoTest {
    private List<VwVendorItems> mockSingleFetchResponse;
    private List<VwVendorItems> mockCriteriaFetchResponse;
    private List<VwVendorItems> mockFetchAllResponse;
    private List<VwVendorItems> mockNotFoundFetchResponse;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        APP_NAME = "accounting";
        super.setUp();
        this.mockSingleFetchResponse = this.createMockSingleFetchResponse();
        this.mockCriteriaFetchResponse = this
                .createMockFetchUsingCriteriaResponse();
        this.mockFetchAllResponse = this.createMockFetchAllResponse();
        this.mockNotFoundFetchResponse = this
                .createMockNotFoundSearchResultsResponse();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        super.tearDown();
        return;
    }

    private List<VwVendorItems> createMockNotFoundSearchResultsResponse() {
        List<VwVendorItems> list = null;
        return list;
    }

    private List<VwVendorItems> createMockSingleFetchResponse() {
        List<VwVendorItems> list = new ArrayList<VwVendorItems>();
        VwVendorItems p = AccountingMockDataUtility.createMockOrmVwVendorItems(
                100, "111-111-111", "11111111", 1234, "Item # 1", 5, 1.23);
        list.add(p);
        return list;
    }

    /**
     * Use for the following selection criteria: where last name begins with 'C'
     * 
     * @return
     */
    private List<VwVendorItems> createMockFetchUsingCriteriaResponse() {
        List<VwVendorItems> list = new ArrayList<VwVendorItems>();
        VwVendorItems p =  AccountingMockDataUtility.createMockOrmVwVendorItems(
                100, "111-111-111", "11111111", 1234, "Item # 1", 5, 1.23);
        list.add(p);

        p = AccountingMockDataUtility.createMockOrmVwVendorItems(
                200, "222-222-222", "22222222", 1234, "Item # 2", 15, 0.99);
        list.add(p);

        return list;
    }

    private List<VwVendorItems> createMockFetchAllResponse() {
        List<VwVendorItems> list = new ArrayList<VwVendorItems>();
        VwVendorItems p =  AccountingMockDataUtility.createMockOrmVwVendorItems(
                100, "111-111-111", "11111111", 1234, "Item # 1", 5, 1.23);
        list.add(p);

        p = AccountingMockDataUtility.createMockOrmVwVendorItems(
                200, "222-222-222", "22222222", 1234, "Item # 2", 15, 0.99);
        list.add(p);

        p = AccountingMockDataUtility.createMockOrmVwVendorItems(
                300, "333-333-333", "3333333", 1234, "Item # 3", 15, 4.55);
        list.add(p);

        p = AccountingMockDataUtility.createMockOrmVwVendorItems(
                400, "444-444-444", "4444444", 1234, "Item # 4", 100, 10.99);
        list.add(p);

        p = AccountingMockDataUtility.createMockOrmVwVendorItems(
                500, "555-555-555", "5555555", 1234, "Item # 5", 55, 32.99);
        list.add(p);
        return list;
    }

    @Test
    public void testFetchSingle() {
        try {
            when(this.mockPersistenceClient
                    .retrieveList(any(VwVendorItems.class)))
                            .thenReturn(this.mockSingleFetchResponse);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(
                    "Single Vendor Item fetch using criteria test case setup failed");
        }

        InventoryApiFactory f = new InventoryApiFactory();
        InventoryApi api = f.createApi(APP_NAME);
        VendorItemDto dto = null;
        try {
            dto = api.getVendorItem(1234, 100);
        } catch (InventoryApiException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(dto);
        Assert.assertEquals(100, dto.getItemId());
        Assert.assertEquals(100, dto.getEntityId());
        Assert.assertEquals(1234, dto.getVendorId());
        Assert.assertEquals("Item # 1", dto.getItemName());
        Assert.assertEquals("Item # 1", dto.getEntityName());
    }
    
   
 
    @Test
    public void testFetchSingleNotFound() {
        try {
            when(this.mockPersistenceClient
                    .retrieveList(any(VwVendorItems.class)))
                            .thenReturn(this.mockNotFoundFetchResponse);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(
                    "Single Vendor Item Not Found fetch using criteria test case setup failed");
        }

        InventoryApiFactory f = new InventoryApiFactory();
        InventoryApi api = f.createApi(APP_NAME);
        VendorItemDto dto = null;
        try {
            dto = api.getVendorItem(9999, 99);
        } catch (InventoryApiException e) {
            e.printStackTrace();
        }
        Assert.assertNull(dto);
    }

    @Test
    public void testFetchSingleWithNullVendorId() {
        InventoryApiFactory f = new InventoryApiFactory();
        InventoryApi api = f.createApi(APP_NAME);
        try {
            api.getVendorItem(null, 100);
            Assert.fail("Expected exception to be thrown due to null vendor id");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e instanceof InvalidDataException);
        }
    }

    @Test
    public void testFetchSingleWithNullItemId() {
        InventoryApiFactory f = new InventoryApiFactory();
        InventoryApi api = f.createApi(APP_NAME);
        try {
            api.getVendorItem(1234, null);
            Assert.fail("Expected exception to be thrown due to null item id");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e instanceof InvalidDataException);
        }
    }

    @Test
    public void testFetchAssignedItems() {
        try {
            when(this.mockPersistenceClient
                    .retrieveList(any(VwVendorItems.class)))
                            .thenReturn(this.mockFetchAllResponse);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(
                    "Vendor Assigned Item fetch using criteria test case setup failed");
        }

        InventoryApiFactory f = new InventoryApiFactory();
        InventoryApi api = f.createApi(APP_NAME);
        List<VendorItemDto> results = null;
        try {
            results = api.getVendorAssignItems(1234);
        } catch (InventoryApiException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(results);
        Assert.assertEquals(5, results.size());
        VendorItemDto dto = results.get(0);
        Assert.assertEquals(100, dto.getItemId());
        Assert.assertEquals(100, dto.getEntityId());
        Assert.assertEquals(1234, dto.getVendorId());
        Assert.assertEquals("Item # 1", dto.getItemName());
        Assert.assertEquals("Item # 1", dto.getEntityName());
    }

    @Test
    public void testFetchAssignedItemsWithNullVendorId() {
        InventoryApiFactory f = new InventoryApiFactory();
        InventoryApi api = f.createApi(APP_NAME);
        try {
            api.getVendorAssignItems(null);
            Assert.fail("Expected exception to be thrown due to null vendor id");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e instanceof InvalidDataException);
        }
    }
    
    @Test
    public void testFetchAssignedItemsNotFound() {
        try {
            when(this.mockPersistenceClient
                    .retrieveList(any(VwVendorItems.class)))
                            .thenReturn(this.mockNotFoundFetchResponse);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(
                    "Vendor Assigned Item Not Found fetch using criteria test case setup failed");
        }

        InventoryApiFactory f = new InventoryApiFactory();
        InventoryApi api = f.createApi(APP_NAME);
        List<VendorItemDto> results = null;
        try {
            results = api.getVendorAssignItems(9999);
        } catch (InventoryApiException e) {
            e.printStackTrace();
        }
        Assert.assertNull(results);
    }
    
    
    @Test
    // TODO: Implement after subsidiary module.
    public void testUpdateVendorItem() {
//        try {
//            when(this.mockPersistenceClient
//                    .retrieveList(any(VwVendorItems.class)))
//                            .thenReturn(this.mockNotFoundFetchResponse);
//        } catch (Exception e) {
//            e.printStackTrace();
//            Assert.fail(
//                    "Vendor Assigned Item Not Found fetch using criteria test case setup failed");
//        }
//
//        InventoryApiFactory f = new InventoryApiFactory();
//        InventoryApi api = f.createApi(APP_NAME);
//        List<VendorItemDto> results = null;
//        try {
//            results = api.getVendorAssignItems(9999);
//        } catch (InventoryApiException e) {
//            e.printStackTrace();
//        }
//        Assert.assertNull(results);
    }
}