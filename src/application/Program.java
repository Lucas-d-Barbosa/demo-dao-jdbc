package application;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import model.dao.DAOFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program {
	public static void main(String[] args) throws ParseException {
		SellerDAO sellerDAO = DAOFactory.createSellerDAO();
		System.out.println("=== TEST 1: seller findById ===");
		Seller s1 = sellerDAO.findById(9);
		System.out.println(s1);
		System.out.println();
		System.out.println("=== TEST 2: seller findByIdDepartment ===");
		Department dep = new Department(1, null);
		List<Seller> sellers = sellerDAO.findByDepartment(dep);
		System.out.println(sellers);
		
		System.out.println();
		System.out.println("=== TEST 3: seller findAll ===");
		sellers = sellerDAO.findAll();
		System.out.println(sellers);
		
		System.out.println();
		System.out.println("=== TEST 4: seller insert ===");
		Seller seller = new Seller(null, "Lorenzo", "lorenzo@gmail.com", new Date(), 2000.0, dep);
		sellerDAO.insert(seller);
		System.out.println(seller);
		
		System.out.println();
		System.out.println("=== TEST 5: seller update ===");
		seller = sellerDAO.findById(1);
		seller.setName("Lucas Barbosa");
		sellerDAO.update(seller);
		System.out.println(seller);
		System.out.println("UPDATE COMPLETED");
	}
}	
