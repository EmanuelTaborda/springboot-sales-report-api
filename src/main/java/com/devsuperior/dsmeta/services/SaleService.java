package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SummaryProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleMinDTO> getReport(String minDateStr, String maxDateStr, String name, Pageable pageable) {
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate maxDate = (maxDateStr == null || maxDateStr.isBlank())
				? today
				: LocalDate.parse(maxDateStr);
		LocalDate minDate = (minDateStr == null || minDateStr.isBlank())
				? maxDate.minusYears(1L)
				: LocalDate.parse(minDateStr);
		String sellerName = (name == null) ? "" : name;
		return repository.searchReport(minDate, maxDate, sellerName, pageable);
	}

	public Page<SummaryProjection> getSummary(String minDateStr, String maxDateStr, Pageable pageable){
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate maxDate = (maxDateStr == null || maxDateStr.isBlank())
				? today
				: LocalDate.parse(maxDateStr);
		LocalDate minDate = (minDateStr == null || minDateStr.isBlank())
				? maxDate.minusYears(1L)
				: LocalDate.parse(minDateStr);
		return repository.searchSummary(minDate, maxDate, pageable);
	}
}
