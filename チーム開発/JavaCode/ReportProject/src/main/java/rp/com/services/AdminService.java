package rp.com.services;

import rp.com.models.entity.Admin;

public interface AdminService {

	Admin registerAdmin(Admin admin);

	Admin loginAdmin(String adminEmail, String adminPassword);

	boolean emailExists(String adminEmail);
}
