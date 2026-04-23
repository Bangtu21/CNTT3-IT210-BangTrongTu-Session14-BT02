package spring_boot.session14bt02.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    public void cancelOrder(Long orderId) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        //Tạo transaction
        Transaction tx = null;

        try {
            // Bắt đầu transaction
            tx = session.beginTransaction();

            Order order = session.get(Order.class, orderId);

            if (order == null) {
                throw new Exception("Đơn hàng không tồn tại!");
            }

            // Bước 1: Hủy đơn
            order.setStatus("CANCELLED");

            // Bước 2: Hoàn kho
            Product product = session.get(Product.class, order.getProductId());

            if (product == null) {
                throw new Exception("Sản phẩm không tồn tại!");
            }

            product.setStock(product.getStock() + order.getQuantity());

            // Commit nếu mọi thứ OK
            tx.commit();

        } catch (Exception e) {
            // Rollback nếu có lỗi
            if (tx != null) {
                tx.rollback();
            }

            System.out.println("Lỗi: " + e.getMessage());

        } finally {
            session.close();
        }
    }
}
