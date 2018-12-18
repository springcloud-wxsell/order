package com.sell.order.service.impl;

import com.sell.common.dto.DecreaseStockDTO;
import com.sell.common.dto.OrderDTO;
import com.sell.common.dto.ProductInfoDTO;
import com.sell.common.enums.OrderStatusEnum;
import com.sell.common.enums.ResultEnum;
import com.sell.common.exception.OrderException;
import com.sell.common.pojo.OrderDetail;
import com.sell.common.pojo.OrderDetailExample;
import com.sell.common.pojo.OrderMaster;
import com.sell.common.utils.KeyUtils;
import com.sell.order.mapper.OrderDetailMapper;
import com.sell.order.mapper.OrderMasterMapper;
import com.sell.order.service.OrderDetailService;
import com.sell.order.service.OrderMasterService;
import com.sell.product.client.ProductClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author WangWei
 * @Title: OrderMasterServiceImpl
 * @ProjectName order
 * @date 2018/12/3 18:25
 * @description:
 */
@Service
public class OrderMasterServiceImpl implements OrderMasterService {

    @Autowired
    private OrderMasterMapper orderMasterMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private ProductClient productClient;


    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtils.genUniqueKey();
        //  查询商品信息（调用商品服务)
        List<String> productIdList = orderDTO.getOrderDetailList().stream()
                .map(OrderDetail::getProductId)
                .collect(Collectors.toList());
        List<ProductInfoDTO> productInfoDTOList = productClient.listForOrder(productIdList);
        // 计算总价
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            for (ProductInfoDTO productInfoDTO : productInfoDTOList) {
                if (productInfoDTO.getProductId().equals(orderDetail.getProductId())) {
                    //单价*数量
                    orderAmount = productInfoDTO.getProductPrice()
                            .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(orderAmount);
                    BeanUtils.copyProperties(productInfoDTO, orderDetail);
                    orderDetail.setOrderId(orderId);
                    orderDetail.setDetailId(KeyUtils.genUniqueKey());
                    // 订单详情入库
                    orderDetailMapper.insert(orderDetail);
                }
            }
        }
        //  扣库存（调用商品服务）
        List<DecreaseStockDTO> decreaseStockDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new DecreaseStockDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productClient.descreaStock(decreaseStockDTOList);
        // 订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);

        orderMasterMapper.insert(orderMaster);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(String orderId) {
        // 1. 查询订单
        OrderMaster orderMaster = orderMasterMapper.selectByPrimaryKey(orderId);
        if (StringUtils.isEmpty(orderMaster)) {
            throw new OrderException(ResultEnum.ORDER_NOT_EXIST);
        }
        // 2. 判断订单状态
        if (!OrderStatusEnum.NEW.getCode().equals(orderMaster.getOrderStatus().intValue())) {
            throw new OrderException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 3. 修改订单状态
        orderMaster.setOrderStatus(OrderStatusEnum.FINISHED.getCode().byteValue());
        orderMasterMapper.updateByPrimaryKeySelective(orderMaster);

        // 4. 查询订单详情(将订单详情返回)
        OrderDetailExample example = new OrderDetailExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        List<OrderDetail> orderDetailList = orderDetailMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new OrderException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
