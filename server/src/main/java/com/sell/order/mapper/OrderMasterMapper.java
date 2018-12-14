package com.sell.order.mapper;

import com.sell.common.pojo.OrderMaster;
import com.sell.common.pojo.OrderMasterExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMasterMapper {
    int countByExample(OrderMasterExample example);

    int deleteByExample(OrderMasterExample example);

    int deleteByPrimaryKey(String orderId);

    int insert(OrderMaster record);

    int insertSelective(OrderMaster record);

    List<OrderMaster> selectByExample(OrderMasterExample example);

    OrderMaster selectByPrimaryKey(String orderId);

    int updateByExampleSelective(@Param("record") OrderMaster record, @Param("example") OrderMasterExample example);

    int updateByExample(@Param("record") OrderMaster record, @Param("example") OrderMasterExample example);

    int updateByPrimaryKeySelective(OrderMaster record);

    int updateByPrimaryKey(OrderMaster record);
}