package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
		Department obj = new Department("Books", 1);
		SellerDao sellerDao = DaoFactory.createSellerDao();
		Seller seller = new Seller(1, "Lucas", "lucas@gmail.com", sdf.parse("05/07/2002"), 2500.00, obj);
		System.out.println(obj);
		System.out.println(seller);
	}

}
