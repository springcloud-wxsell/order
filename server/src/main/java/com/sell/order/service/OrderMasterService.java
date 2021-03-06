package com.sell.order.service;

import com.sell.common.dto.OrderDTO;

/**
 * @author WangWei
 * @Title: OrderMasterService
 * @ProjectName order
 * @date 2018/12/3 18:24
 * @description:
 */
public interface OrderMasterService {

    /**
     * 创建订单
     * @param orderDTO
     * @return
     */
    OrderDTO create(OrderDTO orderDTO);

    /**
     * 完结订单(只有卖家可以访问)
     * @param orderId
     * @return
     */
    OrderDTO finish(String orderId);

}
