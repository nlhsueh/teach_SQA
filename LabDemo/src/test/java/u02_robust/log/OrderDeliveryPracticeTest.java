package u02_robust.log;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("OrderDeliveryPractice 外送日誌實戰測試")
class OrderDeliveryPracticeTest {

    @Test
    @DisplayName("測試正常訂單建立與派送流程")
    void testNormalDeliveryFlow() {
        OrderDeliveryPractice service = new OrderDeliveryPractice();
        OrderDeliveryPractice.Order order = service.createOrder("ORD-001", "Nick", 300.0);

        assertEquals(OrderDeliveryPractice.OrderStatus.CREATED, order.getStatus());

        boolean accepted = service.restaurantAccept(order, 5);
        assertTrue(accepted);
        assertEquals(OrderDeliveryPractice.OrderStatus.ACCEPTED, order.getStatus());

        service.dispatchAndDeliver(order, false);
        assertEquals(OrderDeliveryPractice.OrderStatus.COMPLETED, order.getStatus());
    }

    @Test
    @DisplayName("測試餐廳庫存不足時拒單")
    void testRestaurantRejectsWhenOutOfStock() {
        OrderDeliveryPractice service = new OrderDeliveryPractice();
        OrderDeliveryPractice.Order order = service.createOrder("ORD-002", "Bob", 150.0);

        boolean accepted = service.restaurantAccept(order, 0);
        assertFalse(accepted);
        assertEquals(OrderDeliveryPractice.OrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    @DisplayName("測試非法訂單金額拋出例外")
    void testInvalidAmountThrowsException() {
        OrderDeliveryPractice service = new OrderDeliveryPractice();
        assertThrows(IllegalArgumentException.class, () -> service.createOrder("ORD-003", "Alice", -20.0));
    }
}
