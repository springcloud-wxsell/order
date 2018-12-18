package com.sell.order.service;

import com.sell.common.pojo.OrderDetail;

import java.util.List;

/**
 * @author WangWei
 * @Title: OrderDetailService
 * @ProjectName order
 * @date 2018/12/18 17:52
 * @description:
 */
public interface OrderDetailService {

    List<OrderDetail> selectByOrderId(String orderId);
}
