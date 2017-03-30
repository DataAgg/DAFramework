package com.dataagg.security.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class ScopService {
	public Set<String> generationByRole(List<String> resourceidList, List<String> rolecodeList) {
		// 先用角色代碼取出角色物件
		//		List<Role> roleList = roleRepository.findByCodeIn(rolecodeList);
		//		// 轉換成角色ID
		//		List<String> roleidList = roleList.stream().map(Role::getRoleid).collect(Collectors.toList());
		//		// 用角色ID 找出對應的 Scop 對應表
		//		List<RoleScop> roleScopList = roleScopRepository.findByRoleidIn(roleidList);
		//		// 取出 Scop ID
		//		List<String> scopidList = roleScopList.stream().map(RoleScop::getScopid).collect(Collectors.toList());
		//		// 去 Scop 表格找出可用 resourceid 跟 scopid
		//		List<Scop> scopList = scopRepository.findByResourceidInAndScopidIn(resourceidList, scopidList);
		// 轉成 scopcode
		//		Set<String> scopSet = scopList.stream().map(Scop::getScopcode).collect(Collectors.toSet());
		//		return scopSet;
		return null;
	}
}
