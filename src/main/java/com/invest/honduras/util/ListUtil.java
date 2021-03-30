package com.invest.honduras.util;


import java.util.Iterator;
import java.util.List;

import com.invest.honduras.domain.model.Role;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ListUtil {

	public static void removeSimilarRole(final List<Role> rolesOld, final List<Role> rolesNew) {
		try {

			for (Iterator<Role> roleOldIterator = rolesOld.iterator(); roleOldIterator.hasNext();) {

				Role roleOld = roleOldIterator.next();

				for (Iterator<Role> roleNewIterator = rolesNew.iterator(); roleNewIterator.hasNext();) {

					Role roleNew = roleNewIterator.next();

					if (roleOld.getCode().equals(roleNew.getCode())) {
						roleOldIterator.remove();
						roleNewIterator.remove();
					}
				}
			}

		} catch (Exception e) {
			log.error("ListUtil.removeSimilarRole", e);
		}
	}

}
