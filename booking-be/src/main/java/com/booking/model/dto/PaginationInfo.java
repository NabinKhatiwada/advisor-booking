package com.booking.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaginationInfo {

	private Long total;
	private Integer pageNo;
	private Integer pageSize;
	private Integer totalPages;
}