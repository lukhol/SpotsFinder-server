package com.polibuda.pbl.service;

import java.util.List;

import com.polibuda.pbl.dto.SkateParkDto;

public interface SkateParkService {

	List<SkateParkDto> getAll();

	SkateParkDto add(SkateParkDto skateParkDto);

}
