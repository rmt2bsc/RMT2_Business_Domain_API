package org.modules.inventory;

import java.util.List;

import org.dto.ItemAssociationDto;
import org.dto.ItemMasterDto;
import org.dto.ItemMasterStatusDto;
import org.dto.ItemMasterStatusHistDto;
import org.dto.ItemMasterTypeDto;
import org.dto.VendorItemDto;

import com.api.foundation.TransactionApi;

/**
 * Interface that provides the method prototypes for querying, creating,
 * modifying, deleting, and general management of inventory system.
 * 
 * @author Roy Terrell
 * 
 */
public interface InventoryApi extends TransactionApi {

    /**
     * Retrieves an item object by item id.
     * 
     * @param itemId
     *            The id of the item
     * @return An instance of {@link ItemMasterDto}
     * @throws InventoryException
     */
    ItemMasterDto getItemById(int itemId) throws InventoryException;

    /**
     * Retrieves one or more items using item type id
     * 
     * @param itemTypeId
     *            The item type id
     * @return A List of arbitrary objects representing one or more items.
     * @throws InventoryException
     */
    List<ItemMasterDto> getItemByType(int itemTypeId) throws InventoryException;

    /**
     * Retrieves one or more items using the id of the vendor.
     * 
     * @param vendorId
     *            The id of Vendor
     * @return A List of arbitrary objects representing one or more items.
     * @throws InventoryException
     */
    List<ItemMasterDto> getItemByVendorId(int vendorId)
            throws InventoryException;

    /**
     * Retrieves one or more items using the vendor's item number
     * 
     * @param vendItemNo
     *            The vendor's verison of the item number.
     * @return A List of arbitrary objects representing one or more items.
     * @throws InventoryException
     */
    List<ItemMasterDto> getItemByVendorItemNo(String vendItemNo)
            throws InventoryException;

    /**
     * Retrieves one or more items using the item's serial number
     * 
     * @param serialNo
     *            The serial number of the item.
     * @return A List of arbitrary objects representing one or more items.
     * @throws InventoryException
     */
    List<ItemMasterDto> getItemBySerialNo(String serialNo)
            throws InventoryException;

    /**
     * Retrieves an ArrayList of of any inventory related object based on the
     * base view, base class, and custom criteria supplied by the user. User is
     * responsible for setting the base view and class so that the API will know
     * what data to retrieve.
     * 
     * @param criteria
     *            The selection criteria to apply to the query of data source.
     * @returnA List of arbitrary objects representing one or more items.
     * @throws InventoryException
     */
    List<ItemMasterDto> getItem(String criteria) throws InventoryException;

    /**
     * Retrieves Item Type data using item type id.
     * 
     * @param itemTypeId
     *            The id of the item type/
     * @return An arbitrary object representing an item type.
     * @throws InventoryException
     */
    ItemMasterTypeDto getItemTypeById(int itemTypeId) throws InventoryException;

    /**
     * Retrieve one or more item type objects using criteria.
     * 
     * @param itemName
     *            The name of the item
     * @return List of {@link ItemMasterTypeDto} objects representing one or
     *         more item types.
     * @throws InventoryException
     */
    List<ItemMasterTypeDto> getItemTypes(String itemName)
            throws InventoryException;

    /**
     * Retrieves one or more item status object using custom selection criteria.
     * 
     * @param statusName
     *            The name of status to retrieve
     * @return List of {@link ItemMasterStatusDto} objects representing one or
     *         more item statuses.
     * @throws InventoryException
     */
    List<ItemMasterStatusDto> getItemStatus(String statusName)
            throws InventoryException;

    /**
     * Retrieves Item master status object by primary key.
     * 
     * @param itemStatusId
     * @return An {@link ItemMasterStatusDto} object representing an item
     *         status.
     * @throws InventoryException
     */
    ItemMasterStatusDto getItemStatusById(int itemStatusId)
            throws InventoryException;

    /**
     * Retrieves one or more item statuses using an item id.
     * 
     * @param id
     *            The id of the item to retrieve its statuses.
     * @return List of arbitrary objects representing one or more item status
     *         history items.
     * @throws InventoryException
     */
    List<ItemMasterStatusHistDto> getItemStatusHistByItemId(int itemId)
            throws InventoryException;

    /**
     * Retrieves the current status of an item based on the item's id.
     * 
     * @param itemId
     *            The id of the item to retreive its current status.
     * @return An arbitrary object representing an item status history item.
     * @throws InventoryException
     */
    ItemMasterStatusHistDto getCurrentItemStatusHist(int itemId)
            throws InventoryException;

    /**
     * Retrieves one vendor item object using vendorId and itemId
     * 
     * @param vendorId
     *            The id of the vendor
     * @param itemId
     *            The id of the inventory item
     * @return An arbitrary object representing a vendor items item.
     * @throws InventoryException
     */
    VendorItemDto getVendorItem(int vendorId, int itemId)
            throws InventoryException;

    /**
     * Retrieves those inventory items that are assigned to a particular vendor
     * from the database.
     * 
     * @param vendorId
     *            The id of the target vendor.
     * @return List of arbitrary objects representing one or more vendor items.
     * @throws InventoryException
     */
    List<VendorItemDto> getVendorAssignItems(int vendorId)
            throws InventoryException;

    /**
     * Retrieves those inventory items that are not assigned to a particular
     * vendor.
     * 
     * @param vendorId
     *            The id of the target vendor.
     * @return List of {@link ItemMasterDto} objects representing one or more
     *         unassigned vendor items.
     * @throws InventoryException
     */
    List<ItemMasterDto> getVendorUnassignItems(int vendorId)
            throws InventoryException;

    /**
     * Fetch all the different entities an item is assoicated with.
     * 
     * @param itemId
     *            The id of the item to query.
     * @return An arbitrary item representing the assoications.
     * @throws InventoryException
     */
    List<ItemAssociationDto> getItemAssociations(int itemId)
            throws InventoryException;

    /**
     * Adds a new or modifies an existing inventory item. If the id of the item
     * object is zero, then an inventory item is created. Otherwise, the
     * inventory item is updated.
     * 
     * @param item
     *            the inventory item to create or update
     * @return The id of the item maintained.
     * @throws InventoryException
     */
    int updateItemMaster(ItemMasterDto item) throws InventoryException;

    /**
     * Adds a new or modifies an existing vendor item. If the id of the item
     * object is zero, then a vendor item is created. Otherwise, the vendor item
     * is updated.
     * 
     * @param item
     *            The {@link VendorItemDto} object to create or update.
     * @return The id of the vendor item maintained.
     * @throws InventoryException
     */
    int updateVendorItem(VendorItemDto item) throws InventoryException;

    /**
     * Removes ainventory item from the database.
     * 
     * @param itemId
     *            The id of the item to delete
     * @return 1 for success
     * @throws InventoryException
     *             when itemId is associated with one or more sales orders, or a
     *             database error occurred.
     */
    int deleteItemMaster(int itemId) throws InventoryException;

    /**
     * Increases the count of an item in inventory.
     * 
     * @param itemId
     *            The id of the target item
     * @param qty
     *            The quantity to increase the inventory item by.
     * @return The dollar value of the item's inventory after the quantity
     *         increase.
     * @throws InventoryException
     */
    double pushInventory(int itemId, int qty) throws InventoryException;

    /**
     * Decreases the count of an item in inventory.
     * 
     * @param itemId
     *            The id of the target item
     * @param qty
     *            The quantity to decrease the inventory item by.
     * @return The dollar value of the item's inventory after the quantity
     *         decrease.
     * @throws InventoryException
     */
    double pullInventory(int itemId, int qty) throws InventoryException;

    /**
     * Deactivates an inventory item.
     * 
     * @param itemId
     *            The id of an inventory item.
     * @return 1 for success.
     * @throws InventoryException
     *             itemId does not exist in the system or a database error
     *             occurred.
     */
    int deactivateItemMaster(int itemId) throws InventoryException;

    /**
     * Activates an inventory item.
     * 
     * @param itemId
     *            The id of an inventory item.
     * @return 1 for success.
     * @throws InventoryException
     *             itemId does not exist in the system or a database error
     *             occurred.
     */
    int activateItemMaster(int itemId) throws InventoryException;

    /**
     * Associates one or more inventory items with a vendor.
     * 
     * @param vendorId
     *            The id of the vendor
     * @param items
     *            A list inventory item id's
     * @return The number of items assigned to the vendor.
     * @throws InventoryException
     */
    int assignVendorItems(int vendorId, int items[]) throws InventoryException;

    /**
     * Disassociates one or more inventory items from a vendor.
     * 
     * @param vendorId
     *            The id of the vendor
     * @param items
     *            A list inventory item id's
     * @return The number of items unassigned from the vendor.
     * @throws InventoryException
     */
    int removeVendorItems(int vendorId, int items[]) throws InventoryException;

    // /**
    // * This method activates a vendor-item override targeting the inventory
    // * item, itemId. An override instructs the system to obtain pricing
    // * information for an inventory item from the vendor_items table instead
    // of
    // * the item_master table . This method puts this concept into effect.
    // *
    // * @param vendorId
    // * The id of the vendor that will be assoicated with an item in
    // * the item_master table.
    // * @param itemId
    // * The target item.
    // * @return The total number of rows effected by the database transaction.
    // * This is ususally 1.
    // * @throws InventoryException
    // */
    // int addInventoryOverride(int vendorId, int itemId)
    // throws InventoryException;

    /**
     * Changes the override flag to true for one or more of a vendor's items.
     * 
     * @param vendorId
     *            The id of the vendor that will be assoicated with each item
     *            id.
     * @param items
     *            Collection containing one or more item_master id's to
     *            override.
     * @return The total number of rows effected by the database transaction.
     * @throws InventoryException
     */
    int addInventoryOverride(int vendorId, int items[])
            throws InventoryException;

    // /**
    // * This method deactivates a vendor-item override targeting the inventory
    // * item, itemId. An override instructs the system to obtain pricing
    // * information for an inventory item from the vendor_items table instead
    // of
    // * the item_master table . This method renders this concept ineffective.
    // *
    // * @param vendorId
    // * The id of the vendor that will be disassoicated with the item
    // * id.
    // * @param itemId
    // * The target item.
    // * @return The total number of rows effected by the database transaction.
    // * This is ususally 1.
    // * @throws InventoryException
    // */
    // int removeInventoryOverride(int vendorId, int itemId)
    // throws InventoryException;

    /**
     * Changes the override flag to false for one or more of a vendor's items..
     * 
     * @param vendorId
     *            The id of the vendor that will be disassoicated with each item
     *            id.
     * @param items
     *            Collection containing one or more item_master id's to
     *            deactivate item overrides.
     * @return The total number of rows effected by the database transaction.
     * @throws InventoryException
     */
    int removeInventoryOverride(int vendorId, int items[])
            throws InventoryException;

    // /**
    // * Changes the status of an inventory item.
    // *
    // * @param item
    // * An instance of {@link ItemMasterDto} which is the item master
    // * object targeted for the satus change.
    // * @param newItemStatusId
    // * The id of the item status.
    // * @return The {@link ItemMasterStatusHistDto} object which represents
    // * newItemStatusId
    // * @throws InventoryException
    // * If newItemStatusId is out of sequence, if a database error
    // * occurs, or a system error occurs.
    // */
    // ItemMasterStatusHistDto changeItemStatus(ItemMasterDto item,
    // int newItemStatusId) throws InventoryException;
}