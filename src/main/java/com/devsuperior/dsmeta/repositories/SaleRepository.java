package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SummaryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(value = """ 
       SELECT new com.devsuperior.dsmeta.dto.SaleMinDTO(
       s.id,
       s.date,
       s.amount,
       s.seller.name
       )
       FROM Sale s
       WHERE s.date BETWEEN :minDate AND :maxDate
         AND LOWER(s.seller.name) LIKE LOWER(CONCAT('%', :sellerName, '%'))
       """)
    Page<SaleMinDTO> searchReport(@Param("minDate") LocalDate minDate,
                                  @Param("maxDate") LocalDate maxDate,
                                  @Param("sellerName") String sellerName, Pageable pageable);


    @Query(value = """
            SELECT sl.name AS sellerName, COALESCE(SUM(s.amount), 0) AS total
            FROM tb_sales s
            INNER JOIN tb_seller sl ON s.seller_id = sl.id
            WHERE s.date BETWEEN :minDate AND :maxDate
            GROUP BY sl.name
            """, nativeQuery = true)
    Page<SummaryProjection> searchSummary(@Param ("minDate") LocalDate minDate,
                                          @Param("maxDate") LocalDate maxDate,
                                          Pageable pageable);

}
