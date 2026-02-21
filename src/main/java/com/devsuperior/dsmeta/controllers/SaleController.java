package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SummaryProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<Page<SaleMinDTO>> getReport(@RequestParam(value = "minDate", required = false) String minDate,
													 @RequestParam(value = "maxDate", required = false) String maxDate,
													 @RequestParam(value = "name", required = false) String name,
													 Pageable pageable) {
		Page<SaleMinDTO> page = service.getReport(minDate, maxDate, name, pageable);
		return ResponseEntity.ok(page);
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<Page<SummaryProjection>> getSummary(@RequestParam(value = "minDate", required = false) String minDate,
															  @RequestParam(value = "maxDate", required = false) String maxDate,
															  Pageable pageable) {
		Page<SummaryProjection> page = service.getSummary(minDate, maxDate, pageable);
		return ResponseEntity.ok(page);
	}
}
