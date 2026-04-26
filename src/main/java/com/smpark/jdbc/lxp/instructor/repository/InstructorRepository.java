package com.smpark.jdbc.lxp.instructor.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.smpark.jdbc.lxp.instructor.model.Instructor;
import com.smpark.jdbc.lxp.instructor.model.InstructorDTO;
import com.smpark.jdbc.lxp.util.QueryUtil;

public class InstructorRepository {

	private final Connection connection;

	public InstructorRepository(Connection connection) {
		this.connection = connection;
	}

	public long create(Instructor instructor) {
		String sql = QueryUtil.getQuery("instructor.create");

		try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, instructor.getName());
			ps.setString(2, instructor.getIntroduction());

			ps.executeUpdate();

			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next()) {
					return rs.getLong(1);
				}
			}
			return 0L;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Instructor> findAll() {
		String sql = QueryUtil.getQuery("instructor.findAll");
		List<Instructor> list = new ArrayList<Instructor>();

		try (PreparedStatement ps = connection.prepareStatement(sql);
		     ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				Instructor instructor = new Instructor();
				instructor.setId(rs.getLong("id"));
				instructor.setName(rs.getString("name"));
				instructor.setIntroduction(rs.getString("introduction"));
				list.add(instructor);
			}

			return list;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Instructor findById(long id) {
		String sql = QueryUtil.getQuery("instructor.findById");

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, id);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Instructor instructor = new Instructor();
					instructor.setId(rs.getLong("id"));
					instructor.setName(rs.getString("name"));
					instructor.setIntroduction(rs.getString("introduction"));
					return instructor;
				}
				return null;
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean existsById(long id) {
		String sql = QueryUtil.getQuery("instructor.existsById");

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, id);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
				return false;
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void update(InstructorDTO dto) {
		String sql = QueryUtil.getQuery("instructor.update");

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, dto.getName());
			ps.setString(2, dto.getIntroduction());
			ps.setLong(3, dto.getId());

			ps.executeUpdate();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void softDelete(long id) {
		String sql = QueryUtil.getQuery("instructor.delete");

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}


