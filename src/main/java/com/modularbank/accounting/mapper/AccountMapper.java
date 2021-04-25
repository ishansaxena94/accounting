package com.modularbank.accounting.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.modularbank.accounting.model.Account;

@Mapper
public interface AccountMapper {

	@Select("select * from account acc where  acc.customer_id = #{customerId}")
	public List<Account> getAccountByCustomerId(Long customerId);

}
