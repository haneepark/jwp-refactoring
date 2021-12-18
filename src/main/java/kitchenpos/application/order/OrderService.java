package kitchenpos.application.order;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kitchenpos.domain.order.Order;
import kitchenpos.domain.order.OrderLineItem;
import kitchenpos.domain.order.OrderRepository;
import kitchenpos.dto.order.OrderLineItemRequest;
import kitchenpos.dto.order.OrderRequest;
import kitchenpos.dto.order.OrderResponse;
import kitchenpos.utils.StreamUtils;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;

    public OrderService(final OrderRepository orderRepository, final OrderValidator orderValidator) {
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
    }

    @Transactional
    public OrderResponse create(final OrderRequest orderRequest) {
        List<OrderLineItem> orderLineItems = StreamUtils.mapToList(orderRequest.getOrderLineItems(),
                                                                   OrderLineItemRequest::toOrderLineItem);
        Order order = Order.of(orderRequest.getOrderTableId(), orderLineItems);

        orderValidator.validate(order);

        return OrderResponse.from(orderRepository.save(order));
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> list() {
        List<Order> order = orderRepository.findAll();
        return StreamUtils.mapToList(order, OrderResponse::from);
    }

    @Transactional
    public OrderResponse changeOrderStatus(final Long orderId, final OrderRequest orderRequest) {
        Order order = findOrders(orderId);
        order.changeOrderStatus(orderRequest.getOrderStatus());

        return OrderResponse.from(order);
    }

    private Order findOrders(Long orderId) {
        return orderRepository.findById(orderId)
                              .orElseThrow(EntityNotFoundException::new);
    }
}