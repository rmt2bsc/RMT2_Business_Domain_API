package org.modules.transaction;

import java.util.List;

import org.AccountingConst.SubsidiaryType;
import org.dao.subsidiary.SubsidiaryDaoException;
import org.dto.XactCategoryDto;
import org.dto.XactCodeDto;
import org.dto.XactCodeGroupDto;
import org.dto.XactDto;
import org.dto.XactTypeDto;
import org.dto.XactTypeItemActivityDto;
import org.dto.XactTypeItemDto;

import com.api.foundation.TransactionApi;

/**
 * A common transaction API interface that functions to manage base transaction,
 * category, type, lookup groups, lookup codes, and transaction type item
 * activity related data. Also provides various methods to create and maintain
 * transactions, and to maintain their historical occurrences.
 * 
 * @author Roy Terrell
 * 
 */
public interface XactApi extends TransactionApi {

    /**
     * Retrieves transaction data using transaction id
     * 
     * @param xactId
     * @return {@link Xact}
     * @throws XactApiException
     */
    XactDto getXactById(int xactId) throws XactApiException;

    /**
     * Retrieves a transaction category object using category id.
     * 
     * @return {@link XactCategoryView} object
     * @throws XactApiException
     */
    List<XactCategoryDto> getAllCategory() throws XactApiException;

    /**
     * Retrieves a transaction category object using category id.
     * 
     * @param catgId
     *            The id of the transacton category to retrieve.
     * @return {@link XactCategoryView} object
     * @throws XactApiException
     */
    XactCategoryDto getCategoryById(int catgId) throws XactApiException;

    /**
     * Retrieves a list of transaction code group data based custom criteria
     * supplied by the user. User is responsible for setting the base view and
     * class so that the API will know what data to retrieve.
     * 
     * @return ArrayList of unknown objects. The implementer of this method is
     *         responsible for applying the proper casting of each element
     *         contained in the returned results.
     * @throws XactApiException
     */
    List<XactCodeGroupDto> getAllGroups() throws XactApiException;

    /**
     * Retrieves a transaction code group record using its primary key value.
     * 
     * @param groupId
     *            The primary key valur of the code group to retrieve.
     * @return {@link XactCodeGroup} object
     * @throws XactApiException
     */
    XactCodeGroupDto getGroup(int groupId) throws XactApiException;

    /**
     * Retrieves a list of transaction code data based custom criteria supplied
     * by the user. User is responsible for setting the base view and class so
     * that the API will know what data to retrieve.
     * 
     * @return ArrayList of unknown objects. The implementer of this method is
     *         responsible for applying the proper casting of each element
     *         contained in the returned results.
     * @throws XactApiException
     */
    List<XactCodeDto> getAllCode() throws XactApiException;

    /**
     * Retrieves a specific transaction code object using the primary key value.
     * 
     * @param codeId
     *            The primary key value of the transaction code record.
     * @return {@link XactCodes} object.
     * @throws XactApiException
     */
    XactCodeDto getCode(int codeId) throws XactApiException;

    /**
     * Retrieves one or more transaction code objects using a transaction code
     * group.
     * 
     * @param gropuId
     *            The id of the transaction code group.
     * @return ArrayList of {@link XactCodes}
     * @throws XactApiException
     */
    List<XactCodeDto> getCodeByGroupId(int gropuId) throws XactApiException;

    /**
     * Retrieves an ArrayList of transaction type objects based custom criteria
     * supplied by the user.
     * 
     * @return ArrayList of {@link XactType}
     * @throws XactApiException
     */
    List<XactTypeDto> getAllXactType() throws XactApiException;

    /**
     * Retrieves a specific transaction type object using its primary key.
     * 
     * @param typeId
     *            The id of the transaction object to retrieve.
     * @return {@link XactType} object.
     * @throws XactApiException
     */
    XactTypeDto getXactType(int typeId) throws XactApiException;

    /**
     * Retrieves one or more transaction type objects using a transaction
     * category id.
     * 
     * @param catgId
     *            The id of the transaction category id.
     * @return ArrayList of {@link XactType} objects.
     * @throws XactApiException
     */
    List<XactTypeDto> getXactTypeByCatgId(int catgId) throws XactApiException;

    /**
     * Retrieves one or more transaction type item activity objects related to
     * xactId.
     * 
     * @param xactId
     *            The id of the transction.
     * @return ArrayList of one or more {@link XactTypeItemActivity} obejcts.
     * @throws XactApiException
     */
    List<XactTypeItemActivityDto> getXactTypeItemActivity(int xactId)
            throws XactApiException;

    /**
     * Retrieves one or more extended transaction type item activity objects
     * related to xactId.
     * 
     * @param xactId
     *            The id of the transction.
     * @return ArrayList of one or more {@link XactTypeItemActivity} obejcts.
     * @throws XactApiException
     */
    List<XactTypeItemActivityDto> getXactTypeItemActivityExt(int xactId)
            throws XactApiException;

    /**
     * Retrieves one or more transaction type items related to a transaction
     * type id.
     * 
     * @param xactTypeId
     *            The id of the transaction type.
     * @return ArrayList of {@link XactTypeItem}
     * @throws XactApiException
     */
    List<XactTypeItemDto> getXactTypeItemsByXactTypeId(int xactTypeId)
            throws XactApiException;

    /**
     * Gets transaction item collection.
     * 
     * @return ArrayList of {@link XactTypeItemActivity} objects
     */
    List<XactTypeItemActivityDto> getXactItems();

    /**
     * Sets the collection of transaction items.
     * 
     * @param value
     *            ArrayList of {@link XactTypeItemActivity} objects
     */
    void setXactItems(List<XactTypeItemActivityDto> value);

    /**
     * Driver for creating and applying a transaction to the database.
     * 
     * @param _xact
     *            The transaction object to be managed.
     * @param _xactItems
     *            A list of transaction Type Item Activity objects
     * @return The id of the newly formed transction.
     * @throws XactApiException
     */
    int update(XactDto xact, List<XactTypeItemActivityDto> xactItems)
            throws XactApiException;

    /**
     * Validates the transaction and its items.
     * 
     * @param xact
     *            An instance of {@link XactDto} serving as the base
     *            transaction.
     * @param xactItems
     *            A List of {@link XactTypeItemActivityDto} serving as the
     *            transaction items
     * @throws XactApiException
     */
    void validate(XactDto xact, List<XactTypeItemActivityDto> xactItems)
            throws XactApiException;

    /**
     * Reverses a transaction and its detail items.
     * 
     * @param xact
     *            The transaction that is being reversed.
     * @param xactItems
     *            Transaction items to be reversed.
     * @return New id of the reversed transaction.
     * @throws XactApiException
     */
    int reverse(XactDto xact, List<XactTypeItemActivityDto> xactItems)
            throws XactApiException;

    // /**
    // * Creates Customer activity (subsidiary) entry based on related
    // transaction
    // * (_xactId).
    // *
    // * @param customerId
    // * The id of the customer
    // * @param xactId
    // * The id of the transaction
    // * @param amount
    // * The transaction amount
    // * @return String - Confrimation number.
    // * @throws XactApiException
    // */
    // String createCustomerActivity(int customerId, int xactId, double amount)
    // throws XactApiException;
    //
    // /**
    // * Creates Creditor activity (subsidiary) entry based on related
    // transaction
    // * (_xactId).
    // *
    // * @param creditorId
    // * The id of the creditor
    // * @param xactId
    // * The id of the transaction
    // * @param amount
    // * The transaction amount
    // * @return The confirmation number.
    // * @throws XactApiException
    // */
    // String createCreditorActivity(int creditorId, int xactId, double amount)
    // throws XactApiException;

    /**
     * Creates transacction activity for a particular subsidiary.
     * 
     * @param subsidiaryId
     *            The id of the subsidiary account
     * @param xactId
     *            The id of the transaction
     * @param amount
     *            The transaction amount
     * @return The new unique identifier of the subsidiary transaction.
     * @throws XactApiException
     */
    int createSubsidiaryTransaction(int subsidiaryId, int xactId, double amount)
            throws XactApiException;

    /**
     * Determines if <i>xact</i> can modified or adjusted.
     * 
     * @param xact
     *            The transaction that is to be managed
     * @return true indicating the transaction is eligible to be changed, and
     *         false indicating change is not allowd.
     * @throws XactApiException
     *             when xact is invalid or null.
     */
    boolean isTransModifiable(XactDto xact) throws XactApiException;

    /**
     * This method flags the transaction, <i>xact</i>, as finalized.
     * 
     * @param xact
     *            Transaction object that is to finalized.
     * @throws XactApiException
     *             If transactio id or transaction type id are invalid, or when
     *             a database error occurs.
     */
    void finalizeXact(XactDto xact) throws XactApiException;

    /**
     * Determine the type of subsidiary account being managed.
     * 
     * @param subsidiaryId
     *            the unique id to use to lookup the subsidiary
     * @return an instance of {@link SubsidiaryType}
     * @throws SubsidiaryDaoException
     */
    SubsidiaryType evaluateSubsidiaryType(int subsidiaryId)
            throws SubsidiaryDaoException;
}