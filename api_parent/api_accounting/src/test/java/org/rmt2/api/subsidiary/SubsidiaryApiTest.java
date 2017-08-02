package org.rmt2.api.subsidiary;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dao.mapping.orm.rmt2.Creditor;
import org.dao.mapping.orm.rmt2.CreditorType;
import org.dao.mapping.orm.rmt2.Customer;
import org.dao.mapping.orm.rmt2.GlAccounts;
import org.dao.mapping.orm.rmt2.VwBusinessAddress;
import org.dao.mapping.orm.rmt2.VwCreditorXactHist;
import org.dao.mapping.orm.rmt2.VwCustomerXactHist;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.rmt2.api.BaseAccountingDaoTest;
import org.rmt2.dao.AccountingMockDataUtility;

/**
 * Common subsidiary testing facility that is mainly responsible for setting up mock data.
 * 
 * @author rterrell
 * 
 */
public class SubsidiaryApiTest extends BaseAccountingDaoTest {
    protected List<Customer> mockCustomerNotFoundFetchResponse;
    protected List<Customer> mockCustomerFetchAllResponse;
    protected List<Customer> mockCustomerFetchSingleResponse;
    protected List<Creditor> mockCreditorNotFoundFetchResponse;
    protected List<Creditor> mockCreditorFetchAllResponse;
    protected List<Creditor> mockCreditorFetchSingleResponse;
    protected List<VwBusinessAddress> mockBusinessContactFetchSingleResponse;
    protected List<VwBusinessAddress> mockBusinessContactFetchAllResponse;
    protected List<VwBusinessAddress> mockBusinessContactNotFoundResponse;
    protected List<CreditorType> mockCreditorTypeFetchAllResponse;
    protected List<CreditorType> mockCreditorTypeFetchSingleResponse;
    protected List<CreditorType> mockCreditorTypeNotFoundResponse;
    protected List<VwCreditorXactHist> mockCreditorXactHistoryResponse;
    protected List<VwCustomerXactHist> mockCustomerXactHistoryResponse;
    protected List<GlAccounts> mockSingleGLAccountFetchResponse;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        this.mockCustomerFetchAllResponse = this.createMockFetchAllCustomerResponse();
        this.mockCustomerFetchSingleResponse = this.createMockSingleCustomerFetchResponse();
        this.mockCustomerNotFoundFetchResponse = this.createMockCustomerNotFoundSearchResultsResponse();
        this.mockCreditorFetchAllResponse = this.createMockFetchAllCreditorResponse();
        this.mockCreditorNotFoundFetchResponse = this.createMockCreditorNotFoundSearchResultsResponse();
        this.mockCreditorFetchSingleResponse = this.createMockSingleCreditorFetchResponse();
        this.mockCreditorTypeFetchAllResponse = this.createMockFetchAllCreditorTypeResponse();
        this.mockCreditorTypeFetchSingleResponse = this.createMockFetchSingleCreditorTypeResponse();
        this.mockCreditorTypeNotFoundResponse = this.createMockFetchNotFoundCreditorTypeResponse();
        this.mockBusinessContactFetchSingleResponse = this.createMockSingleContactFetchResponse();
        this.mockBusinessContactFetchAllResponse = this.createMockFetchAllContactResponse();
        this.mockBusinessContactNotFoundResponse = this.createMockNotFoundContactFetchResponse();
        this.mockCreditorXactHistoryResponse = this.createMockFetchCreditorXactHistoryResponse();
        this.mockCustomerXactHistoryResponse = this.createMockFetchCustomerXactHistoryResponse();
        this.mockSingleGLAccountFetchResponse = this.createMockSingleGLAccountFetchResponse();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        super.tearDown();
        return;
    }

    protected List<Customer> createMockCustomerNotFoundSearchResultsResponse() {
        List<Customer> list = null;
        return list;
    }
    
    private List<Customer> createMockFetchAllCustomerResponse() {
        List<Customer> list = new ArrayList<Customer>();
        Customer o = AccountingMockDataUtility.createMockOrmCustomer(200, 1351, 0,
                333, "C1234589", "Customer 1");
        list.add(o);
        
        o = AccountingMockDataUtility.createMockOrmCustomer(201, 1400, 0,
                333, "C1400444", "Customer 2");
        list.add(o);
        
        o = AccountingMockDataUtility.createMockOrmCustomer(202, 1500, 0,
                333, "C747344", "Customer 3");
        list.add(o);
        
        o = AccountingMockDataUtility.createMockOrmCustomer(203, 1600, 0,
                333, "C5949493", "Customer 4");
        list.add(o);
        
        o = AccountingMockDataUtility.createMockOrmCustomer(204, 1700, 0,
                333, "C2229393", "Customer 5");
        list.add(o);
        return list;
    }
    
    private List<Customer> createMockSingleCustomerFetchResponse() {
        List<Customer> list = new ArrayList<Customer>();
        Customer o = AccountingMockDataUtility.createMockOrmCustomer(200, 1351, 0,
                333, "C1234589", "Customer 1");
        list.add(o);
        return list;
    }
    
    protected List<Creditor> createMockCreditorNotFoundSearchResultsResponse() {
        List<Creditor> list = null;
        return list;
    }

    private List<Creditor> createMockFetchAllCreditorResponse() {
        List<Creditor> list = new ArrayList<Creditor>();
        Creditor o = AccountingMockDataUtility.createMockOrmCreditor(200, 1351,
                333, "C1234589", "123-456-789", 22);
        list.add(o);
        
        o = AccountingMockDataUtility.createMockOrmCreditor(201, 1400,
                444, "C1400444", "7437437JDJD8484", 22);
        list.add(o);
        
        o = AccountingMockDataUtility.createMockOrmCreditor(202, 1500,
                555, "C1500555", "ABC123", 22);
        list.add(o);
        
        o = AccountingMockDataUtility.createMockOrmCreditor(203, 1600,
                666, "C1600666", "XYZ321", 22);
        list.add(o);
        
        o = AccountingMockDataUtility.createMockOrmCreditor(204, 1700,
                777, "C1700777", "7654312", 22);
        list.add(o);
        return list;
    }

    
    private List<Creditor> createMockSingleCreditorFetchResponse() {
        List<Creditor> list = new ArrayList<Creditor>();
        Creditor o = AccountingMockDataUtility.createMockOrmCreditor(200, 1351,
                333, "C1234589", "123-456-789", 22);
        list.add(o);
        return list;
    }

    private List<CreditorType> createMockFetchAllCreditorTypeResponse() {
        List<CreditorType> list = new ArrayList<CreditorType>();
        CreditorType o = AccountingMockDataUtility.createMockOrmCreditorType(100, "Creditor Type 1");
        list.add(o);
        
        o = AccountingMockDataUtility.createMockOrmCreditorType(200, "Creditor Type 2");
        list.add(o);
        
        o = AccountingMockDataUtility.createMockOrmCreditorType(300, "Creditor Type 3");
        list.add(o);
        
        o = AccountingMockDataUtility.createMockOrmCreditorType(400, "Creditor Type 4");
        list.add(o);
        
        o = AccountingMockDataUtility.createMockOrmCreditorType(500, "Creditor Type 5");
        list.add(o);
        return list;
    }
    
    private List<CreditorType> createMockFetchSingleCreditorTypeResponse() {
        List<CreditorType> list = new ArrayList<CreditorType>();
        CreditorType o = AccountingMockDataUtility.createMockOrmCreditorType(100, "Creditor Type 1");
        list.add(o);
        return list;
    }

    private List<CreditorType> createMockFetchNotFoundCreditorTypeResponse() {
        List<CreditorType> list = null;
        return list;
    }
    
    private List<VwBusinessAddress> createMockFetchAllContactResponse() {
        List<VwBusinessAddress> list = new ArrayList<VwBusinessAddress>();
        VwBusinessAddress p = AccountingMockDataUtility
                .createMockOrmBusinessContact(1351, "Company1", 2222,
                        "94393 Hall Ave.", "Building 123", "Suite 300",
                        "Room 45", "Dallas", "TX", 75232);
        list.add(p);

        p = AccountingMockDataUtility.createMockOrmBusinessContact(1400,
                "Company2", 4444, "9382 Frank St.", null, null, null, "Irving",
                "TX", 75240);
        list.add(p);

        p = AccountingMockDataUtility.createMockOrmBusinessContact(1500,
                "Company3", 5555, "6718 Bernard Dr", "Building 4353",
                "Suite 982", null, "Shreveport", "LA", 71118);
        list.add(p);

        p = AccountingMockDataUtility.createMockOrmBusinessContact(1600,
                "Company4", 6666, "9328 Forest Ave", "Building 854",
                "Suite 9212", "Room 555", "FLower Mound", "TX", 75028);
        list.add(p);

        p = AccountingMockDataUtility.createMockOrmBusinessContact(1700,
                "Company5", 7777, "8327 Spring Ave", null, null, null, "Dallas",
                "TX", 75232);
        list.add(p);
        return list;
    }

    private List<VwBusinessAddress> createMockSingleContactFetchResponse() {
        List<VwBusinessAddress> list = new ArrayList<VwBusinessAddress>();
        VwBusinessAddress p = AccountingMockDataUtility
                .createMockOrmBusinessContact(1351, "ABC Company", 2222,
                        "94393 Hall Ave.", "Building 123", "Suite 300",
                        "Room 45", "Dallas", "TX", 75232);
        list.add(p);
        return list;
    }
    
    private List<VwBusinessAddress> createMockNotFoundContactFetchResponse() {
        List<VwBusinessAddress> list = null;
        return list;
    }

    /**
     * Setup mocks to retrieve single matching creditor and common business
     * contact data.
     * 
     * @param busContactCriteria
     * @param creditorCriteria
     */
    protected void setupSingleSubsidiaryContactInfoFetch(VwBusinessAddress busContactCriteria, Creditor creditorCriteria) {
        try {
            when(this.mockPersistenceClient.retrieveList(eq(busContactCriteria))).thenReturn(
                            this.mockBusinessContactFetchSingleResponse);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Single Business Contact for creditor fetch test case setup failed");
        }

        try {
            when(this.mockPersistenceClient.retrieveList(eq(creditorCriteria)))
                    .thenReturn(this.mockCreditorFetchSingleResponse);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Single Creditor fetch test case setup failed");
        }
    }

    /**
     * Setup mocks to retrieve multiple matching creditor and common business
     * contact data.
     * 
     * @param busContactCriteria
     * @param creditorCriteria
     */
    protected void setupMultipleSubsidiaryContactInfoFetch(
            VwBusinessAddress busContactCriteria, Creditor creditorCriteria) {
        try {
            when(this.mockPersistenceClient
                    .retrieveList(eq(busContactCriteria))).thenReturn(
                            this.mockBusinessContactFetchAllResponse);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(
                    "Single Business Contact for creditor fetch test case setup failed");
        }

        try {
            when(this.mockPersistenceClient.retrieveList(eq(creditorCriteria)))
                    .thenReturn(this.mockCreditorFetchAllResponse);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Single Creditor fetch test case setup failed");
        }
    }
    
    protected void setupNotFoundSubsidiaryContactInfoFetch(
            VwBusinessAddress busContactCriteria, Creditor creditorCriteria) {
        try {
            when(this.mockPersistenceClient
                    .retrieveList(eq(busContactCriteria))).thenReturn(
                            this.mockBusinessContactNotFoundResponse);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(
                    "Single Business Contact for creditor fetch test case setup failed");
        }

        try {
            when(this.mockPersistenceClient.retrieveList(eq(creditorCriteria)))
                    .thenReturn(this.mockCreditorNotFoundFetchResponse);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Single Creditor fetch test case setup failed");
        }
    }

    /**
     * Setup mocks to retrieve single matching customer and common business
     * contact data.
     * 
     * @param busContactCriteria
     * @param customerCriteria
     */
    protected void setupSingleSubsidiaryContactInfoFetch(
            VwBusinessAddress busContactCriteria, Customer customerCriteria) {
        try {
            when(this.mockPersistenceClient
                    .retrieveList(eq(busContactCriteria))).thenReturn(
                            this.mockBusinessContactFetchSingleResponse);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(
                    "Single Business Contact for customer fetch test case setup failed");
        }

        try {
            when(this.mockPersistenceClient.retrieveList(eq(customerCriteria)))
                    .thenReturn(this.mockCustomerFetchSingleResponse);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Single Customer fetch test case setup failed");
        }
    }

    /**
     * Setup mocks to retrieve multiple matching customer and common business
     * contact data.
     * 
     * @param busContactCriteria
     * @param customerCriteria
     */
    protected void setupMultipleSubsidiaryContactInfoFetch(
            VwBusinessAddress busContactCriteria, Customer customerCriteria) {
        try {
            when(this.mockPersistenceClient
                    .retrieveList(eq(busContactCriteria))).thenReturn(
                            this.mockBusinessContactFetchAllResponse);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(
                    "Single Business Contact for customer fetch test case setup failed");
        }

        try {
            when(this.mockPersistenceClient.retrieveList(eq(customerCriteria)))
                    .thenReturn(this.mockCustomerFetchAllResponse);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Single Customer fetch test case setup failed");
        }
    }

    
    protected void setupNotFoundSubsidiaryContactInfoFetch(
            VwBusinessAddress busContactCriteria, Customer customerCriteria) {
        try {
            when(this.mockPersistenceClient
                    .retrieveList(eq(busContactCriteria))).thenReturn(
                            this.mockBusinessContactNotFoundResponse);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(
                    "Single Business Contact for customer fetch test case setup failed");
        }

        try {
            when(this.mockPersistenceClient.retrieveList(eq(customerCriteria)))
                    .thenReturn(this.mockCustomerNotFoundFetchResponse);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Single Customer fetch test case setup failed");
        }
    }

    private List<GlAccounts> createMockSingleGLAccountFetchResponse() {
        List<GlAccounts> list = new ArrayList<GlAccounts>();
        GlAccounts p = AccountingMockDataUtility.createMockOrmGlAccounts(1234, 2, 300, 1, "GL_200", "ACCT_PAY", "234",
                "Accounts Payable", 1);
        list.add(p);
        return list;
    }
    
    private List<VwCreditorXactHist> createMockFetchCreditorXactHistoryResponse() {
        List<VwCreditorXactHist> list = new ArrayList<VwCreditorXactHist>();
        VwCreditorXactHist o = AccountingMockDataUtility
                .createMockOrmCreditorXactHistory(1200, 100, "C8434", 1000.00,
                        new Date(), 1);
        list.add(o);

        o = AccountingMockDataUtility.createMockOrmCreditorXactHistory(1201,
                100, "C8434", 32.00, new Date(), 1);
        list.add(o);

        o = AccountingMockDataUtility.createMockOrmCreditorXactHistory(1202,
                100, "C8434", 1223.00, new Date(), 2);
        list.add(o);

        o = AccountingMockDataUtility.createMockOrmCreditorXactHistory(1203,
                100, "C8434", 25.67, new Date(), 1);
        list.add(o);

        o = AccountingMockDataUtility.createMockOrmCreditorXactHistory(1204,
                100, "C8434", 745.59, new Date(), 3);
        list.add(o);
        return list;
    }
    
    private List<VwCustomerXactHist> createMockFetchCustomerXactHistoryResponse() {
        List<VwCustomerXactHist> list = new ArrayList<VwCustomerXactHist>();
        VwCustomerXactHist o = AccountingMockDataUtility
                .createMockOrmCustomerXactHistory(1200, 100, 1351, 0, "C8434", 1000.00,
                        new Date(), 1);
        list.add(o);

        o = AccountingMockDataUtility.createMockOrmCustomerXactHistory(2345, 100, 1351, 0, "C8434", 1000.00,
                new Date(), 1);
        list.add(o);

        o = AccountingMockDataUtility.createMockOrmCustomerXactHistory(3333, 100, 1351, 0, "C8434", 2000.00,
                new Date(), 2);
        list.add(o);

        o = AccountingMockDataUtility.createMockOrmCustomerXactHistory(6543, 100, 1351, 0, "C8434", 3000.00,
                new Date(), 3);
        list.add(o);

        o = AccountingMockDataUtility.createMockOrmCustomerXactHistory(8765, 100, 1351, 0, "C8434", 4000.00,
                new Date(), 4);
        list.add(o);
        return list;
    }
}