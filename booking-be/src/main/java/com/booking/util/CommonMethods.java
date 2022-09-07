package com.booking.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;

import com.booking.enums.EUserRole;
import com.booking.model.dto.PaginationInfo;
import com.booking.model.dto.UserDTO;
import com.booking.security.UserPrincipal;

public class CommonMethods {

	private CommonMethods() {
	}

	/* Same User With multi-roles */
	public static UserDTO extractUserRoles(List<UserDTO> userDTOs) {
		List<EUserRole> roleList = new ArrayList<>();
		for (UserDTO dto : userDTOs) {
			roleList.add(dto.getRole());
		}
		UserDTO userDTO = userDTOs.get(0);
		userDTO.setRoles(roleList);
		userDTO.setRole(null);
		return userDTO;
	}

	public static UserPrincipal getUserPrincipal() {
		return (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	public static PaginationInfo getPaginationInfo(Page<?> page) {
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setTotal(page.getTotalElements());
		paginationInfo.setPageSize(page.getSize());
		paginationInfo.setPageNo(page.getNumber());
		paginationInfo.setTotalPages(page.getTotalPages());
		return paginationInfo;
	}

}
