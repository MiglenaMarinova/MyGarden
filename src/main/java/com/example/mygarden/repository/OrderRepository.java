package com.example.mygarden.repository;

import com.example.mygarden.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o from Order  o " +
            "WHERE o.placedBy.id = :id AND o.isPlaced = false")
    Order findByPlacedBy(Long id);

    @Query("SELECT o FROM Order o " +
            "WHERE o.placedBy.id = :id AND o.isPlaced = false")
    List<Order> findAllOpenOrdersByUser(Long id);

    @Query("SELECT o FROM Order o " +
            "WHERE o.placedBy.id = :id AND o.isPlaced = true")
    List<Order> findAllPlacedOrdersByUser(Long id);


}
