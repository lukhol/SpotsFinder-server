package com.polibuda.pbl.service;

import java.util.List;

import com.polibuda.pbl.dto.SkateParkDTO;

public interface SkateParkService {

	List<SkateParkDTO> getAll();

	SkateParkDTO add(SkateParkDTO skateParkDto);

	boolean exists(String skateParkId);

	void delete(String skateParkId);

}
