package org.rmt2.api.transaction.purchases.vendor;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.AccountingConst;
import org.dao.mapping.orm.rmt2.Creditor;
import org.dao.mapping.orm.rmt2.CreditorActivity;
import org.dao.mapping.orm.rmt2.CreditorType;
import org.dao.mapping.orm.rmt2.ItemMaster;
import org.dao.mapping.orm.rmt2.ItemMasterType;
import org.dao.mapping.orm.rmt2.PurchaseOrder;
import org.dao.mapping.orm.rmt2.PurchaseOrderItems;
import org.dao.mapping.orm.rmt2.PurchaseOrderStatusHist;
import org.dao.mapping.orm.rmt2.VwXactList;
import org.dao.mapping.orm.rmt2.Xact;
import org.dao.mapping.orm.rmt2.XactTypeItemActivity;
import org.dao.transaction.purchases.vendor.VendorPurchasesConst;
import org.dao.transaction.purchases.vendor.VendorPurchasesDaoException;
import org.dto.PurchaseOrderDto;
import org.dto.PurchaseOrderItemDto;
import org.dto.adapter.orm.transaction.purchaseorder.Rmt2PurchaseOrderDtoFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.inventory.InventoryConst;
import org.modules.transaction.purchases.vendor.CannotChangePurchaseOrderStatusException;
import org.modules.transaction.purchases.vendor.PurchaseOrderItemValidationException;
import org.modules.transaction.purchases.vendor.PurchaseOrderValidationException;
import org.modules.transaction.purchases.vendor.VendorPurchasesApi;
import org.modules.transaction.purchases.vendor.VendorPurchasesApiException;
import org.modules.transaction.purchases.vendor.VendorPurchasesApiFactory;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.api.AccountingMockDataUtility;

import com.api.persistence.AbstractDaoClientImpl;
import com.api.persistence.DatabaseException;
import com.api.persistence.db.orm.Rmt2OrmClientFactory;

/**
 * Tests creditor purchases transaction query Api.
 * 
 * @author rterrell
 * 
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ AbstractDaoClientImpl.class, Rmt2OrmClientFactory.class, ResultSet.class })
public class VendorPurchaseUpdateApiTest extends VendorPurchaseApiTestData {
    private static final int TEST_PO_ID = 330;
    private static final int TEST_PO_ID_NEW = 1234567;
    private static final int TEST_PO_ITEM_ID = 8880;
    private static final int TEST_XACT_ID = 7000;
    private static final int TEST_PO_STATUS_HIST_ID_NEW = 7654321;
    private static final int TEST_CREDITOR_ID = 1111111;
    private static final double TEST_PURCHASE_ORDER_AMT = 12500.00;
    private static final String TEST_VENDOR_ITEM_NO = "111-111";
    
    private PurchaseOrderDto poDto;
    private List<PurchaseOrderItemDto> poItemsDto;
    
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
    
    private void setupNewPurchaseOrderDto() {
        this.poDto = Rmt2PurchaseOrderDtoFactory.createPurchaseOrderInstance(this.mockPurchaseOrder.get(0));
        this.poDto.setPoId(0);
        
        this.poItemsDto = new ArrayList<>();
        for (PurchaseOrderItems item : this.mockPurchaseOrderItems) {
            PurchaseOrderItemDto dto = Rmt2PurchaseOrderDtoFactory.createPurchaseOrderItemInstance(item);
            dto.setPoId(0);
            dto.setPoItemId(0);
            this.poItemsDto.add(dto);
        }
    }
    
    private void setupExistingPurchaseOrderDto() {
        this.poDto = Rmt2PurchaseOrderDtoFactory.createPurchaseOrderInstance(this.mockPurchaseOrder.get(0));
        
        this.poItemsDto = new ArrayList<>();
        for (PurchaseOrderItems item : this.mockPurchaseOrderItems) {
            PurchaseOrderItemDto dto = Rmt2PurchaseOrderDtoFactory.createPurchaseOrderItemInstance(item);
            this.poItemsDto.add(dto);
        }
    }
    
    private void setupCommonMocks() {
        // Mock method call to create vendor purchase order
        try {
            when(this.mockPersistenceClient.insertRow(isA(PurchaseOrder.class),
                    eq(true))).thenReturn(TEST_PO_ID_NEW);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Create vendor purchase order test case setup failed");
        }
        
        try {
            when(this.mockPersistenceClient.updateRow(isA(PurchaseOrder.class))).thenReturn(1);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("UPdate vendor purchase order test case setup failed");
        }
        
        PurchaseOrder mockCriteria = new PurchaseOrder();
        mockCriteria.setPoId(TEST_PO_ID);
        try {
            when(this.mockPersistenceClient.retrieveList(isA(PurchaseOrder.class)))
                    .thenReturn(this.mockPurchaseOrder);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Single vendor purchase orders fetch test case setup failed");
        }
        
        try {
            when(this.mockPersistenceClient.insertRow(isA(PurchaseOrderItems.class), eq(true)))
              .thenReturn(8880, 8881, 8882, 8883, 8884);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Create vendor purchase order test case setup failed");
        }
        
        PurchaseOrderItems mockPurchaseOrderItemCriteria = new PurchaseOrderItems();
        mockPurchaseOrderItemCriteria.setPoId(TEST_PO_ID);
        try {
            when(this.mockPersistenceClient.retrieveList(eq(mockPurchaseOrderItemCriteria)))
                    .thenReturn(this.mockPurchaseOrderItems);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("All vendor purchase order items fetch test case setup failed");
        }
    }
    
    private void setupMockCreditorCheck() {
        Creditor mockCreditorCriteria = new Creditor();
        mockCreditorCriteria.setCreditorId(TEST_CREDITOR_ID);
        try {
            this.mockCreditorFetchSingleResponse.get(0).setCreditorId(TEST_CREDITOR_ID);
            this.mockCreditorFetchSingleResponse.get(0).setCreditorTypeId(AccountingConst.CREDITORTYPE_VENDOR);
            when(this.mockPersistenceClient.retrieveObject(eq(mockCreditorCriteria)))
                            .thenReturn(this.mockCreditorFetchSingleResponse.get(0));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Fetch single creditor test case setup failed");
        }
        
        CreditorType mockCredTypeCriteria = new CreditorType();
        mockCredTypeCriteria.setCreditorTypeId(AccountingConst.CREDITORTYPE_VENDOR);
        try {
            this.mockCreditorTypeFetchSingleResponse.get(0).setCreditorTypeId(AccountingConst.CREDITORTYPE_VENDOR);
            when(this.mockPersistenceClient.retrieveList(eq(mockCredTypeCriteria))).thenReturn(
                            this.mockCreditorTypeFetchSingleResponse);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Single creditor type fetch test case setup failed");
        }
    }
    
    private void setupMockItemMasterCheck() {
        try {
            when(this.mockPersistenceClient.retrieveList(isA(ItemMaster.class)))
                            .thenReturn(this.mockItemMaster);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Single Inventory Item Master fetch using criteria test case setup failed");
        }
        
        try {
            when(this.mockPersistenceClient.retrieveList(isA(ItemMasterType.class)))
                    .thenReturn(this.mockItemMasterType);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Single Inventory Item Master Type fetch using criteria test case setup failed");
        }    
    }
    
    
    private void setupMockCurrentPurchaseOrderStatus(int poId, int currentStatus) {
        PurchaseOrderStatusHist mockPoStatusCriteria = new PurchaseOrderStatusHist();
        Set<String> customSql = new HashSet<>();
        customSql.add("\"end_date is null\"");
        mockPoStatusCriteria.setCustomCriteria(customSql);
        mockPoStatusCriteria.setPoId(poId);
        try {
            // Set the first item, which will be returned to the client, to the desired status
            this.mockPurchaseOrderCurrentStatusHistory.get(0).setPoStatusId(currentStatus);
            when(this.mockPersistenceClient.retrieveList(eq(mockPoStatusCriteria)))
                    .thenReturn(this.mockPurchaseOrderCurrentStatusHistory);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("All vendor purchase order current status fetch test case setup failed");
        }
        
       
    }
    
    private void setupMockModifyPurchaseOrderStatus() {
        try {
            when(this.mockPersistenceClient.updateRow(isA(PurchaseOrderStatusHist.class))).thenReturn(1);
            when(this.mockPersistenceClient.insertRow(isA(PurchaseOrderStatusHist.class), eq(true)))
                .thenReturn(TEST_PO_STATUS_HIST_ID_NEW);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("All vendor purchase order status insert/update test case setup failed");
        }
    }
    
    private void setupMockTransactionActivity() {
        try {
            when(this.mockPersistenceClient.insertRow(isA(XactTypeItemActivity.class), isA(Boolean.class)))
                            .thenReturn(500, 501, 502, 503, 504);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Setting up creditor purchases transaction item activity update case failed");
        }

        // Mock base transaction creation
        try {
            when(this.mockPersistenceClient.insertRow(isA(Xact.class), eq(true))).thenReturn(TEST_XACT_ID);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Setting up creditor purchases base transaction update case failed");
        }
        
        
        // Setup creditor mocking
        Creditor mockCriteria = new Creditor();
        mockCriteria.setCreditorId(TEST_CREDITOR_ID);
        try {
            when(this.mockPersistenceClient.retrieveList(isA(Creditor.class))).thenReturn(
                    this.mockCreditorFetchSingleResponse);
            when(this.mockPersistenceClient.retrieveObject(isA(Creditor.class))).thenReturn(
                    this.mockCreditorFetchSingleResponse.get(0));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Fetch single creditor test case setup failed");
        }

        // Mock transaction query
        VwXactList mockXactCriteria = new VwXactList();
        mockXactCriteria.setId(TEST_XACT_ID);
        try {
            when(this.mockPersistenceClient.retrieveList(eq(mockXactCriteria)))
                    .thenReturn(this.mockXactFetchSingleResponse);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Fetch single xact test case setup failed");
        }

        // Mock creditor transaction history post
        CreditorActivity mockCreditorActivity = new CreditorActivity();
        mockCreditorActivity.setCreditorId(TEST_CREDITOR_ID);
        mockCreditorActivity.setXactId(TEST_XACT_ID);
        mockCreditorActivity.setAmount(TEST_PURCHASE_ORDER_AMT);
        try {
            when(this.mockPersistenceClient.insertRow(eq(mockCreditorActivity), eq(true))).thenReturn(987654);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Setting up creditor activity insert case failed");
        }
    }

    @Test
    public void testCreate_PurchaseOrder_Success() {
        // Modify mock data to appear as a new purchase order     
        this.setupNewPurchaseOrderDto();
        this.setupCommonMocks();
        this.setupMockCreditorCheck();
        this.setupMockItemMasterCheck();
        
        this.setupMockCurrentPurchaseOrderStatus(TEST_PO_ID_NEW, VendorPurchasesConst.PURCH_STATUS_NEW);

        // Perform test
        VendorPurchasesApiFactory f = new VendorPurchasesApiFactory();
        VendorPurchasesApi api = f.createApi(mockDaoClient);
        int results = 0;
        try {
            results = api.updatePurchaseOrder(this.poDto, this.poItemsDto);
        } catch (VendorPurchasesApiException e) {
            e.printStackTrace();
            Assert.fail("Test failed due to unexpected exception thrown");
        }
        Assert.assertEquals(TEST_PO_ID_NEW, results);
    }

    @Test
    public void testValidation_Create_PurchaseOrder_Null_PO() {
        // Perform test
        VendorPurchasesApiFactory f = new VendorPurchasesApiFactory();
        VendorPurchasesApi api = f.createApi(mockDaoClient);
        try {
            api.updatePurchaseOrder(null, this.poItemsDto);
            Assert.fail("Test failed due to an exception was expected");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e instanceof PurchaseOrderValidationException);
        }
    }
    
    @Test
    public void testValidation_Create_PurchaseOrder_CreditorId_Zero() {
        // Perform test
        this.setupNewPurchaseOrderDto();
        VendorPurchasesApiFactory f = new VendorPurchasesApiFactory();
        VendorPurchasesApi api = f.createApi(mockDaoClient);
        try {
            this.poDto.setCreditorId(0);
            api.updatePurchaseOrder(this.poDto, this.poItemsDto);
            Assert.fail("Test failed due to an exception was expected");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e instanceof PurchaseOrderValidationException);
        }
    }
    
    @Test
    public void testValidation_Create_PurchaseOrder_CreditorId_Negative() {
        // Perform test
        this.setupNewPurchaseOrderDto();
        VendorPurchasesApiFactory f = new VendorPurchasesApiFactory();
        VendorPurchasesApi api = f.createApi(mockDaoClient);
        try {
            this.poDto.setCreditorId(-1234);
            api.updatePurchaseOrder(this.poDto, this.poItemsDto);
            Assert.fail("Test failed due to an exception was expected");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e instanceof PurchaseOrderValidationException);
        }
    }
    
    @Test
    public void testValidation_Create_PurchaseOrder_Creditor_Notfound() {
        // Perform test
        this.setupNewPurchaseOrderDto();
        this.setupCommonMocks();
        
        Creditor mockCreditorCriteria = new Creditor();
        mockCreditorCriteria.setCreditorId(TEST_CREDITOR_ID);
        try {
            this.mockCreditorFetchSingleResponse.get(0).setCreditorId(TEST_CREDITOR_ID);
            this.mockCreditorFetchSingleResponse.get(0).setCreditorTypeId(AccountingConst.CREDITORTYPE_VENDOR);
            when(this.mockPersistenceClient.retrieveObject(eq(mockCreditorCriteria))).thenReturn(null);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Fetch single creditor test case setup failed");
        }
        
        VendorPurchasesApiFactory f = new VendorPurchasesApiFactory();
        VendorPurchasesApi api = f.createApi(mockDaoClient);
        try {
            api.updatePurchaseOrder(this.poDto, this.poItemsDto);
            Assert.fail("Test failed due to an exception was expected");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e instanceof PurchaseOrderValidationException);
        }
    }
    
    @Test
    public void testValidation_Create_PurchaseOrder_CreditorType_Notfound() {
        // Perform test
        this.setupNewPurchaseOrderDto();
        this.setupCommonMocks();
        
        CreditorType mockCredTypeCriteria = new CreditorType();
        mockCredTypeCriteria.setCreditorTypeId(AccountingConst.CREDITORTYPE_VENDOR);
        try {
            this.mockCreditorTypeFetchSingleResponse.get(0).setCreditorTypeId(AccountingConst.CREDITORTYPE_VENDOR);
            when(this.mockPersistenceClient.retrieveList(eq(mockCredTypeCriteria))).thenReturn(null);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Single creditor type fetch test case setup failed");
        }
        
        VendorPurchasesApiFactory f = new VendorPurchasesApiFactory();
        VendorPurchasesApi api = f.createApi(mockDaoClient);
        try {
            this.poDto.setCreditorId(-1234);
            api.updatePurchaseOrder(this.poDto, this.poItemsDto);
            Assert.fail("Test failed due to an exception was expected");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e instanceof PurchaseOrderValidationException);
        }
    }
    
    @Test
    public void testValidation_Create_PurchaseOrder_CreditorType_Incorrect() {
        this.setupNewPurchaseOrderDto();
        this.setupCommonMocks();
        
        CreditorType mockCredTypeCriteria = new CreditorType();
        mockCredTypeCriteria.setCreditorTypeId(AccountingConst.CREDITORTYPE_VENDOR);
        try {
            this.mockCreditorTypeFetchSingleResponse.get(0).setCreditorTypeId(AccountingConst.CREDITORTYPE_CREDITOR);
            when(this.mockPersistenceClient.retrieveList(eq(mockCredTypeCriteria))).thenReturn(
                            this.mockCreditorTypeFetchSingleResponse);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Single creditor type fetch test case setup failed");
        }
        
        VendorPurchasesApiFactory f = new VendorPurchasesApiFactory();
        VendorPurchasesApi api = f.createApi(mockDaoClient);
        try {
            this.poDto.setCreditorId(-1234);
            api.updatePurchaseOrder(this.poDto, this.poItemsDto);
            Assert.fail("Test failed due to an exception was expected");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e instanceof PurchaseOrderValidationException);
        }
    }
    
    @Test
    public void testValidation_Create_PurchaseOrder_Items_Null() {
        // Perform test
        this.setupNewPurchaseOrderDto();
        VendorPurchasesApiFactory f = new VendorPurchasesApiFactory();
        VendorPurchasesApi api = f.createApi(mockDaoClient);
        try {
            api.updatePurchaseOrder(this.poDto, null);
            Assert.fail("Test failed due to an exception was expected");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e instanceof PurchaseOrderValidationException);
        }
    }
    
    @Test
    public void testValidation_Create_PurchaseOrder_Items_Empty() {
        // Perform test
        this.setupNewPurchaseOrderDto();
        VendorPurchasesApiFactory f = new VendorPurchasesApiFactory();
        VendorPurchasesApi api = f.createApi(mockDaoClient);
        try {
            List<PurchaseOrderItemDto> items = new ArrayList<>();
            api.updatePurchaseOrder(this.poDto, items);
            Assert.fail("Test failed due to an exception was expected");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e instanceof PurchaseOrderValidationException);
        }
    }
    
    @Test
    public void testError_Create_PurchaseOrder_DB_Error() {
        // Modify mock data to appear as a new purchase order     
        this.setupNewPurchaseOrderDto();
        this.setupCommonMocks();
        this.setupMockCreditorCheck();
        
        // Mock method call to create vendor purchase order 
        try {
            when(this.mockPersistenceClient.insertRow(isA(PurchaseOrder.class), eq(true)))
              .thenThrow(DatabaseException.class);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Create vendor purchase order test case setup failed");
        }
        

        // Perform test
        VendorPurchasesApiFactory f = new VendorPurchasesApiFactory();
        VendorPurchasesApi api = f.createApi(mockDaoClient);
        int results = 0;
        try {
            api.updatePurchaseOrder(this.poDto, this.poItemsDto);
            Assert.fail("Test failed due to an exception was expected");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e instanceof VendorPurchasesApiException);
            Assert.assertTrue(e.getCause() instanceof VendorPurchasesDaoException);
            Assert.assertTrue(e.getCause().getCause() instanceof DatabaseException);
        }
    }
    
    @Test
    public void testValidation_Create_PurchaseOrder_Null_PO_Item() {
        // Modify mock data to appear as a new purchase order     
        this.setupNewPurchaseOrderDto();
        this.setupCommonMocks();
        this.setupMockCreditorCheck();

        // Perform test
        VendorPurchasesApiFactory f = new VendorPurchasesApiFactory();
        VendorPurchasesApi api = f.createApi(mockDaoClient);
        try {
            PurchaseOrderItemDto o = null;
            this.poItemsDto.add(o);
            api.updatePurchaseOrder(this.poDto, this.poItemsDto);
            Assert.fail("Test failed due to an exception was expected");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e instanceof VendorPurchasesApiException);
            Assert.assertTrue(e.getCause() instanceof PurchaseOrderItemValidationException);
        }
    }
    
    @Test
    public void testValidation_Create_PurchaseOrder_Item_POId_Notfound() {
        // Modify mock data to appear as a new purchase order     
        this.setupNewPurchaseOrderDto();
        this.setupCommonMocks();
        this.setupMockCreditorCheck();
        
        PurchaseOrder mockCriteria = new PurchaseOrder();
        mockCriteria.setPoId(TEST_PO_ID);
        try {
            when(this.mockPersistenceClient.retrieveList(isA(PurchaseOrder.class))).thenReturn(null);
        } catch (Exception e) {
            Assert.fail("Single vendor purchase orders fetch test case setup failed");
        }
        
        // Perform test
        VendorPurchasesApiFactory f = new VendorPurchasesApiFactory();
        VendorPurchasesApi api = f.createApi(mockDaoClient);
        try {
            api.updatePurchaseOrder(this.poDto, this.poItemsDto);
            Assert.fail("Test failed due to an exception was expected");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e instanceof VendorPurchasesApiException);
            Assert.assertTrue(e.getCause() instanceof PurchaseOrderItemValidationException);
        }
    }
    
    @Test
    public void testValidation_Create_PurchaseOrder_Item_ItemMasterId_NotPositive() {
        // Modify mock data to appear as a new purchase order     
        this.setupNewPurchaseOrderDto();
        this.setupCommonMocks();
        this.setupMockCreditorCheck();
        
        // Perform test
        VendorPurchasesApiFactory f = new VendorPurchasesApiFactory();
        VendorPurchasesApi api = f.createApi(mockDaoClient);
        try {
            PurchaseOrderItems item = AccountingMockDataUtility.createPurchaseOrderItem(8880, 330, 0, 100.00, 11, 4, 0);
            PurchaseOrderItemDto o = Rmt2PurchaseOrderDtoFactory.createPurchaseOrderItemInstance(item);
            this.poItemsDto.add(o);
            api.updatePurchaseOrder(this.poDto, this.poItemsDto);
            Assert.fail("Test failed due to an exception was expected");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e instanceof VendorPurchasesApiException);
            Assert.assertTrue(e.getCause() instanceof PurchaseOrderItemValidationException);
        }
    }
    
    @Test
    public void testValidation_Create_PurchaseOrder_Item_ItemMaster_Notfound() {
        // Modify mock data to appear as a new purchase order     
        this.setupNewPurchaseOrderDto();
        this.setupCommonMocks();
        this.setupMockCreditorCheck();
        
        try {
            when(this.mockPersistenceClient.retrieveList(isA(ItemMaster.class))).thenReturn(null);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Single Inventory Item Master fetch using criteria test case setup failed");
        }

        // Perform test
        VendorPurchasesApiFactory f = new VendorPurchasesApiFactory();
        VendorPurchasesApi api = f.createApi(mockDaoClient);
        try {
            api.updatePurchaseOrder(this.poDto, this.poItemsDto);
            Assert.fail("Test failed due to an exception was expected");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e instanceof VendorPurchasesApiException);
            Assert.assertTrue(e.getCause() instanceof PurchaseOrderItemValidationException);
        }
    }
    
    @Test
    public void testValidation_Create_PurchaseOrder_Item_ItemMasterType_Notfound() {
        // Modify mock data to appear as a new purchase order     
        this.setupNewPurchaseOrderDto();
        this.setupCommonMocks();
        this.setupMockCreditorCheck();
        
        try {
            when(this.mockPersistenceClient.retrieveList(isA(ItemMasterType.class))).thenReturn(null);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Single Inventory Item Master Type fetch using criteria test case setup failed");
        }

        // Perform test
        VendorPurchasesApiFactory f = new VendorPurchasesApiFactory();
        VendorPurchasesApi api = f.createApi(mockDaoClient);
        try {
            api.updatePurchaseOrder(this.poDto, this.poItemsDto);
            Assert.fail("Test failed due to an exception was expected");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e instanceof VendorPurchasesApiException);
            Assert.assertTrue(e.getCause() instanceof PurchaseOrderItemValidationException);
        }
    }
    
    @Test
    public void testValidation_Create_PurchaseOrder_Item_ItemMasterType_NotMatcing() {
        // Modify mock data to appear as a new purchase order     
        this.setupNewPurchaseOrderDto();
        this.setupCommonMocks();
        this.setupMockCreditorCheck();
        this.setupMockItemMasterCheck();
        
        try {
            this.mockItemMaster.get(0).setItemTypeId(InventoryConst.ITEM_TYPE_SRVC);
            when(this.mockPersistenceClient.retrieveList(isA(ItemMaster.class))).thenReturn(this.mockItemMaster);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Single Inventory Item Master fetch using criteria test case setup failed");
        }
        
      PurchaseOrderStatusHist mockPoStatusCriteria = new PurchaseOrderStatusHist();
      Set<String> customSql = new HashSet<>();
      customSql.add("\"end_date is null\"");
      mockPoStatusCriteria.setCustomCriteria(customSql);
      mockPoStatusCriteria.setPoId(TEST_PO_ID_NEW);
      try {
          this.mockPurchaseOrderCurrentStatusHistory.get(0).setPoStatusId(VendorPurchasesConst.PURCH_STATUS_NEW);
          when(this.mockPersistenceClient.retrieveList(eq(mockPoStatusCriteria)))
                  .thenReturn(this.mockPurchaseOrderCurrentStatusHistory);
      } catch (Exception e) {
          e.printStackTrace();
          Assert.fail("All vendor purchase order current status fetch test case setup failed");
      }
        
        // Perform test
        VendorPurchasesApiFactory f = new VendorPurchasesApiFactory();
        VendorPurchasesApi api = f.createApi(mockDaoClient);
        try {
            api.updatePurchaseOrder(this.poDto, this.poItemsDto);
            Assert.fail("Test failed due to an exception was expected");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e instanceof VendorPurchasesApiException);
            Assert.assertTrue(e.getCause() instanceof PurchaseOrderItemValidationException);
            Assert.assertTrue(e.getCause().getCause() instanceof PurchaseOrderItemValidationException);
        }
    }
    
    @Test
    public void testValidation_Create_PurchaseOrder_Item_QtyOrdered_Zero() {
        // Modify mock data to appear as a new purchase order     
        this.setupNewPurchaseOrderDto();
        this.setupCommonMocks();
        this.setupMockCreditorCheck();

        // Perform test
        VendorPurchasesApiFactory f = new VendorPurchasesApiFactory();
        VendorPurchasesApi api = f.createApi(mockDaoClient);
        try {
            PurchaseOrderItems item = 
                    AccountingMockDataUtility.createPurchaseOrderItem(8880, 330, InventoryConst.ITEM_TYPE_MERCH, 100.00, 0, 4, 0);
            PurchaseOrderItemDto o = Rmt2PurchaseOrderDtoFactory.createPurchaseOrderItemInstance(item);
            this.poItemsDto.add(o);
            api.updatePurchaseOrder(this.poDto, this.poItemsDto);
            Assert.fail("Test failed due to an exception was expected");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e instanceof VendorPurchasesApiException);
            Assert.assertTrue(e.getCause() instanceof PurchaseOrderItemValidationException);
        }
    }
    
    @Test
    public void testValidation_Create_PurchaseOrder_Item_QtyOrdered_LessThan_QtyReceived() {
        // Modify mock data to appear as a new purchase order     
        this.setupNewPurchaseOrderDto();
        this.setupCommonMocks();
        this.setupMockCreditorCheck();
        
        // Perform test
        VendorPurchasesApiFactory f = new VendorPurchasesApiFactory();
        VendorPurchasesApi api = f.createApi(mockDaoClient);
        try {
            PurchaseOrderItems item = 
                    AccountingMockDataUtility.createPurchaseOrderItem(8880, 330, InventoryConst.ITEM_TYPE_MERCH, 100.00, 10, 40, 0);
            PurchaseOrderItemDto o = Rmt2PurchaseOrderDtoFactory.createPurchaseOrderItemInstance(item);
            this.poItemsDto.add(o);
            api.updatePurchaseOrder(this.poDto, this.poItemsDto);
            Assert.fail("Test failed due to an exception was expected");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e instanceof VendorPurchasesApiException);
            Assert.assertTrue(e.getCause() instanceof PurchaseOrderItemValidationException);
        }
    }
    
    @Test
    public void testModify_PurchaseOrder_Refresh_Success() {
        this.setupExistingPurchaseOrderDto();
        this.setupCommonMocks();
        this.setupMockCreditorCheck();
        this.setupMockItemMasterCheck();
        this.setupMockCurrentPurchaseOrderStatus(TEST_PO_ID, VendorPurchasesConst.PURCH_STATUS_QUOTE);
        
        // Perform test
        VendorPurchasesApiFactory f = new VendorPurchasesApiFactory();
        VendorPurchasesApi api = f.createApi(mockDaoClient);
        int results = 0;
        try {
            results = api.updatePurchaseOrder(this.poDto, this.poItemsDto);
        } catch (VendorPurchasesApiException e) {
            e.printStackTrace();
            Assert.fail("Test failed due to unexpected exception thrown");
        }
        Assert.assertEquals(VendorPurchasesConst.PO_UPDATE_SUCCESSFUL, results);
    }
    

    @Test
    public void testSubmit_PurchaseOrder_Success() {
        this.setupExistingPurchaseOrderDto();
        this.setupMockTransactionActivity();
        this.setupMockCurrentPurchaseOrderStatus(TEST_PO_ID, VendorPurchasesConst.PURCH_STATUS_QUOTE);        
        
        // Perform test
        VendorPurchasesApiFactory f = new VendorPurchasesApiFactory();
        VendorPurchasesApi api = f.createApi(mockDaoClient);
        
        VendorPurchasesApi apiSpy = Mockito.spy(api);
        try {
            doNothing().when(apiSpy).validatePurchaseOrder(isA(PurchaseOrderDto.class));
            doReturn(1).when(apiSpy).updatePurchaseOrder(isA(PurchaseOrderDto.class), isA(List.class));
            doReturn(TEST_PURCHASE_ORDER_AMT).when(apiSpy).calcPurchaseOrderTotal(isA(Integer.class));
        } catch (VendorPurchasesApiException e1) {
            e1.printStackTrace();
        }
        
        int results = 0;
        try {
            results = apiSpy.submitPurchaseOrder(this.poDto, this.poItemsDto);
        } catch (VendorPurchasesApiException e) {
            e.printStackTrace();
            Assert.fail("Test failed due to unexpected exception thrown");
        }
        Assert.assertEquals(TEST_XACT_ID, results);
    }
    
    @Test
    public void testError_Submit_PurchaseOrder_InvalidCurrentStatus() {
        this.setupExistingPurchaseOrderDto();
        this.setupMockTransactionActivity();
        this.setupMockCurrentPurchaseOrderStatus(TEST_PO_ID, VendorPurchasesConst.PURCH_STATUS_RETURN);        
        
        // Perform test
        VendorPurchasesApiFactory f = new VendorPurchasesApiFactory();
        VendorPurchasesApi api = f.createApi(mockDaoClient);
        
        VendorPurchasesApi apiSpy = Mockito.spy(api);
        try {
            doNothing().when(apiSpy).validatePurchaseOrder(isA(PurchaseOrderDto.class));
        } catch (VendorPurchasesApiException e1) {
            e1.printStackTrace();
        }
        
        try {
            apiSpy.submitPurchaseOrder(this.poDto, this.poItemsDto);
            Assert.fail("Test failed due to exception was expected to be thrown");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e instanceof CannotChangePurchaseOrderStatusException);
        }
    }
}