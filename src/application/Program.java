package application;

import java.text.ParseException;
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
	}
}	
