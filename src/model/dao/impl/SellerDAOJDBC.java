package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class SellerDAOJDBC implements SellerDAO{
	
	private Connection conn;
	
	public SellerDAOJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
				
	}

	@Override
	public void update(Seller obj) {
		
	}

	@Override
	public void deleteById(Integer id) {

		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "SELECT seller.*, department.Name as DepName FROM"
				+ " seller INNER JOIN department ON"
				+ " department.Id = seller.DepartmentId"
				+ " WHERE seller.Id = ?";
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				Department department = instantiateDepartment(rs);
				Seller seller = instantiateSeller(rs, department);
				
				return seller;
			}
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
		return null;
	}

	private Seller instantiateSeller(ResultSet rs, Department department) throws SQLException {
		Seller seller = new Seller();
		seller.setId(rs.getInt("Id"));
		seller.setName(rs.getString("Name"));
		seller.setEmail(rs.getString("Email"));
		seller.setBirthDate(rs.getDate("BirthDate"));
		seller.setBaseSalary(rs.getDouble("BaseSalary"));
		seller.setDepartment(department);
		return seller;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
			Department department = new Department();
			department.setId(rs.getInt("DepartmentId"));
			department.setName(rs.getNString("DepName"));
			return department;
	}

	@Override
	public List<Seller> findAll() {
		return null;
	}
	
}
