package org.rmt2.dao;

import org.dao.mapping.orm.rmt2.GlAccountTypes;
import org.dao.mapping.orm.rmt2.GlAccounts;
import org.dto.AccountDto;
import org.dto.adapter.orm.account.generalledger.Rmt2AccountDtoFactory;

public class AccountingMockDataUtility {

    public static final GlAccounts createMockOrmGlAccounts(int acctId, int acctTypeId,
            int acctCatgId, int acctSeq, String acctNo, String acctName,
            String acctCode, String acctDescription, int acctBalTypeId) {
        GlAccounts orm = new GlAccounts();
        orm.setAcctId(acctId);
        orm.setAcctTypeId(acctTypeId);
        orm.setAcctBaltypeId(acctBalTypeId);
        orm.setAcctCatgId(acctCatgId);
        orm.setAcctNo(acctNo);
        orm.setAcctSeq(acctSeq);
        orm.setCode(acctCode);
        orm.setDescription(acctDescription);
        orm.setName(acctName);
        return orm;
    }

    public static final AccountDto createMockDtoGlAccounts(int acctId, int acctTypeId,
            int acctCatgId, int acctSeq, String acctNo, String acctName,
            String acctCode, String acctDescription, int acctBalTypeId) {
        GlAccounts orm = AccountingMockDataUtility.createMockOrmGlAccounts(acctId,
                acctTypeId, acctCatgId, acctSeq, acctNo, acctName, acctCode,
                acctDescription, acctBalTypeId);
        AccountDto dto = Rmt2AccountDtoFactory.createAccountInstance(orm);
        return dto;
    }
    
    
    /**
     * 
     * @param id
     * @param acctBalTypeId
     * @return
     */
    public static final GlAccountTypes createMockOrmGlAccountTypes(int id, int acctBalTypeId, String description) {
        GlAccountTypes orm = new GlAccountTypes();
        orm.setAcctTypeId(id);
        orm.setAcctBaltypeId(acctBalTypeId);
        orm.setDescription(description);
        return orm;
    }
}
