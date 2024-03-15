package com.jsp.agro.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import com.jsp.agro.entity.Address;
import com.jsp.agro.repo.AddressRepo;

public class AddressDao {

	@Autowired
	private AddressRepo repo;

	public Address updatAddres(Address address) {
		Optional<Address> db = repo.findById(address.getId());
		if (db.isPresent()) {
			Address addressdb = db.get();
			if (address.getHousNum() == null) {
				address.setHousNum(address.getHousNum());
			}
			if (address.getStreet() == null) {
				address.setStreet(address.getStreet());
			}
			if (address.getLandMark() == null) {
				address.setLandMark(address.getLandMark());
			}
			if (address.getVillage() == null) {
				address.setVillage(address.getVillage());
			}
			if (address.getMandal() == null) {
				address.setMandal(address.getMandal());
			}
			if (address.getDistrict() == null) {
				address.setDistrict(address.getDistrict());
			}
			if (address.getState() == null) {
				address.setState(address.getState());
			}
			if (address.getPinCode() == 0) {
				address.setPinCode(address.getPinCode());
			}
			return repo.save(address);
		}
		return address;
	}

}
