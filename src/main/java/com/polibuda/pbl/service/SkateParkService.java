package com.polibuda.pbl.service;

import java.util.List;

import com.polibuda.pbl.dto.SkateParkDTO;

public interface SkateParkService {

	List<SkateParkDTO> getAll();

	SkateParkDTO save(SkateParkDTO skateParkDto);

	boolean exists(Long skateParkId);
	
	SkateParkDTO getById(Long skateParkId);

	void delete(Long skateParkId);

}
