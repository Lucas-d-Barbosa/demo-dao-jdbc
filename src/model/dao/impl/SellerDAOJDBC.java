package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = 
				"INSERT INTO seller(Name, Email, BirthDate, BaseSalary, DepartmentId)"
				+ " VALUES(?,?,?,?,?)";
		try {
			st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			int rowsAffected = st.executeUpdate();
			if(rowsAffected > 0) {
				rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				return;
			} else {
				throw new DbException("Unexpected error! No rows affected!");
			}
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}		
	}

	@Override
	public void update(Seller obj) {
		
	}

	@Override
	public void deleteById(Integer id) {

		
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = 
				"SELECT seller.*, department.Name as DepName FROM"
				+ " seller INNER JOIN department ON"
				+ " department.Id = seller.DepartmentId"
				+ " ORDER BY Name";
		try {
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			List<Seller> listSellers = new ArrayList<>(); 
			Map<Integer, Department> map = new HashMap<Integer, Department>();
			
			while(rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));
				if(dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Seller seller = instantiateSeller(rs, dep);
				listSellers.add(seller);
			}
			return listSellers;
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
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
	
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = 
				"SELECT seller.*, department.Name as DepName FROM"
				+ " seller INNER JOIN department ON"
				+ " department.Id = seller.DepartmentId"
				+ " WHERE DepartmentId = ?"
				+ " ORDER BY Name";
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, department.getId());
			rs = st.executeQuery();
			List<Seller> listSellers = new ArrayList<>(); 
			Map<Integer, Department> map = new HashMap<Integer, Department>();
			
			while(rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));
				if(dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Seller seller = instantiateSeller(rs, dep);
				listSellers.add(seller);
			}
			return listSellers;
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
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

	
}
