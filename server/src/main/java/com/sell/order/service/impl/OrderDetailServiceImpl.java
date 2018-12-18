package com.sell.order.service.impl;

import com.sell.common.pojo.OrderDetail;
import com.sell.common.pojo.OrderDetailExample;
import com.sell.order.mapper.OrderDetailMapper;
import com.sell.order.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author WangWei
 * @Title: OrderDetailServiceImpl
 * @ProjectName order
 * @date 2018/12/18 17:53
 * @description:
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public List<OrderDetail> selectByOrderId(String orderId) {
        OrderDetailExample example = new OrderDetailExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        List<OrderDetail> orderDetailList = orderDetailMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(orderDetailList)) {
            return orderDetailList;
        }
        return null;
    }
}
