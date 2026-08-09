package com.hamzahuda.worldcupticketbooker.repository;

import com.hamzahuda.worldcupticketbooker.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {

}
