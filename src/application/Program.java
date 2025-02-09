package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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
	}
}	
